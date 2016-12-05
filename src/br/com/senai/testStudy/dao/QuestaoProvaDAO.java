package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class QuestaoProvaDAO implements MetodosBasicos<QuestaoProva> {
	Connection CONEXAO;

	private static final String ADICIONAR = "INSERT INTO questao_prova( visualizacao_questao,"
			+ " corpo_questao, titulo_questao, tipo_questao, disciplina_questao, dificuldade, materia_questao, disponibilidade_questao, status_questao, examinador_responsavel_questao"
			+ ", autor_questao)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String LISTAR = "SELECT * FROM materia, disciplina, questao_prova, professor WHERE materia.id_disciplina = disciplina.id_disciplina "
			+ "AND questao_prova.materia_questao = materia.id_materia AND questao_prova.dificuldade BETWEEN ? AND ? "
			+ "AND materia.id_materia=? AND professor.id_professor=?";
	private static final String LISTARLOCAIS = "SELECT * FROM materia, disciplina, questao_prova, professor WHERE materia.id_disciplina = disciplina.id_disciplina "
			+ "AND questao_prova.materia_questao = materia.id_materia AND professor.id_professor=?";
	private static final String BUSCAR = "SELECT * FROM questao_prova WHERE id_questao = ?";
	private static final String ALTERAR = "UPDATE questao_prova SET corpo_questao=?, titulo_questao=?"
			+ ", tipo_questao=? WHERE id_questao=?";
	private static final String LISTAR_DISC = "SELECT * FROM disciplina WHERE escola_disciplina = 1 OR escola_disciplina = ?";
	private static final String LISTAR_MAT = "SELECT materia.*, disciplina.* FROM materia,"
			+ " disciplina WHERE materia.id_materia = disciplina.id_disciplina AND materia.id_disciplina = ?";
	private static final String ADICIONAR_NA_PROVA = "INSERT INTO prova_questao (id_questao, id_prova) VALUES (?, ?)";
	private static final String LISTAR_QUESTOES_DA_PROVA = "select * from prova_questao,"
			+ " questao_prova, professor, materia, disciplina where questao_prova.id_questao"
			+ " = prova_questao.id_questao AND materia.id_disciplina = disciplina.id_disciplina"
			+ " AND questao_prova.materia_questao = materia.id_materia AND questao_prova.autor_questao"
			+ " = professor.id_professor AND prova_questao.id_prova = ?";
	@Autowired
	public QuestaoProvaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void adicionar(QuestaoProva qp) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, "disp");
			stmt.setString(2, qp.getCorpoQuestao());
			stmt.setString(3, qp.getTituloQuestao());
			stmt.setString(4, qp.getTipoQuestao());
			stmt.setInt(5, qp.getMateria().getDisciplina().getIdDisciplina());
			stmt.setInt(6, qp.getDificuldade());
			stmt.setInt(7, qp.getMateria().getIdMateria());
			stmt.setString(8, qp.getDisponibilidadeQuestao());
			stmt.setString(9, qp.getStatusQuestao());
			stmt.setInt(10, 3);
			stmt.setInt(11, qp.getAutorQuestao().getIdProfessor());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<QuestaoProva> listarQuestoesDaProva(Integer id){
		List<QuestaoProva>questoesDaProva = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_QUESTOES_DA_PROVA);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				d.setPadraoDisciplina(rs.getString("padrao_disciplina"));
				
				Materia m = new Materia();
				m.setDisciplina(d);
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				
				Professor p = new Professor();
				p.setIdProfessor(rs.getInt("id_professor"));
				p.setNome(rs.getString("nome_professor"));
				p.setSexo(rs.getString("sexo_professor"));
				p.setSenha(rs.getString("sexo_professor"));
				p.setCpf(rs.getString("cpf_professor"));
				p.setEmail(rs.getString("email_professor"));
				p.setNascimento(rs.getDate("nascimento_professor"));
				
				
				QuestaoProva qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				qp.setAutorQuestao(p);
				qp.setMateria(m);
				questoesDaProva.add(qp);
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return questoesDaProva;
	}
	
	public void adicionarQuestaoNaProva(Integer idProva, Integer idQuestao){
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR_NA_PROVA);
			stmt.setInt(1, idQuestao);
			stmt.setInt(2, idProva);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Disciplina> listarDisc(Integer id) {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_DISC);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				disciplinas.add(d);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;
	}

	public List<Materia> listarMat(Integer id) {
		List<Materia> materias = new ArrayList<Materia>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_MAT);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				Materia m = new Materia();
				m.setDisciplina(d);
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				materias.add(m);

			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return materias;
	}

	@Override
	public void remover(QuestaoProva object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(QuestaoProva qp) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, qp.getCorpoQuestao());
			stmt.setString(2, qp.getTituloQuestao());
			stmt.setString(3, qp.getTipoQuestao());
			stmt.setInt(4, qp.getIdQuestaoProva());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Deprecated
	@Override
	public List<QuestaoProva> listar() {
		List<QuestaoProva> questões = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);//TODO SE DER ERRO FOI AQUI
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestaoProva qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				questões.add(qp);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return questões;
	}
	
	public List<QuestaoProva> listarLocais(Integer id) {
		List<QuestaoProva> questões = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTARLOCAIS);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestaoProva qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				questões.add(qp);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return questões;
	}
	
	public List<QuestaoProva> listarqp(Integer de, Integer ate, String disp, Integer idProfessor,Integer id) {
		// SE pegaAlt for true ele vai pro controller da alternativa para pegar as alternativas correspondentes a questão
		String listar = LISTAR.toString();
		Boolean teste = (!disp.equals("true"));
		if (teste) {
			listar += " AND questao_prova.disponibilidade_questao = ?";
		}
		List<QuestaoProva> questoes = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(listar);
			stmt.setInt(1, de);
			stmt.setInt(2, ate);
			stmt.setInt(3, id);
			stmt.setInt(4, idProfessor);
			if (teste) {
				stmt.setString(5, disp);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestaoProva qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				questoes.add(qp);
			}
			stmt.close();
			rs.close();
			// se pega alt for verdadeiro vai buscar as alternativas das questões
			/*
			if (pegaAlt) {
				final AlternativaDAO dao = null;
				AlternativaController alt = new AlternativaController(dao);
				alt.mostraAlternativa(questoes);
			}*/
		} catch (SQLException e) {
			throw new RuntimeException(e.toString());
		}
		java.util.Collections.shuffle(questoes);
		return questoes;
	}

	@Override
	public QuestaoProva buscarID(Integer id) {
		QuestaoProva qp = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return qp;
	}

}
