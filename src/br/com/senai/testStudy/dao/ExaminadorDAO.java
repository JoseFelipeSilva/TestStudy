package br.com.senai.testStudy.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ExaminadorDAO implements MetodosBasicos<Examinador> {
	private final Connection CONEXAO;
	private static final String ADD = "INSERT INTO examinador"
			+ " (sexo_examinador, email_examinador, foto_examinador,"
			+ " nascimento_examinador, cpf_examinador, rg_examinador,"
			+ " nome_examinador, senha_examinador) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String LISTAR = "SELECT * FROM examinador";
	private static final String BUSCAR = "SELECT * FROM examinador WHERE id_examinador =?";
	private static final String ALTERAR = "UPDATE examinador SET sexo_examinador=?, email_examinador=?,"
			+ "nascimento_examinador=?, cpf_examinador=?, rg_examinador=?, nome_examinador=?"
			+ " WHERE id_examinador=?";
	private static final String EXCLUIR = "DELETE FROM examinador WHERE id_examinador = ?";
	private static final String ALT_FOTO = "UPDATE examinador SET foto_examinador = ? WHERE id_examinador = ?";
	private static final String LISTAR_QUESTOES_PENDENTES = "SELECT questao_prova.*, disciplina.*, materia.* FROM "
			+ "questao_prova, materia, disciplina WHERE questao_prova.disponibilidade_questao"
			+ " = 'disp' AND status_questao = 'enviado' AND questao_prova.disciplina_questao = "
			+ "disciplina.id_disciplina AND questao_prova.materia_questao = materia.id_materia;";
	// COMANDO RESPONSÁVEL POR BUSCAR UMA QUESTÃO PARA TER O STATUS ALTERADO
	private static final String BUSCAR_QUESTAO_ID = "SELECT questao_prova.*, disciplina.*, materia.*"
			+ " FROM questao_prova, materia, disciplina WHERE questao_prova.disponibilidade_questao"
			+ " = 'disp' AND status_questao = 'enviado' AND questao_prova.disciplina_questao ="
			+ " disciplina.id_disciplina AND questao_prova.materia_questao = materia.id_materia AND questao_prova.id_questao = ?";
	// COMANDO RESPONSÁVEL POR BUSCAR AS ALTERNATIVAS DA QUESTÃO PARA TER O STATUS ALTERADO
	private static final String BUSCAR_ALT_ID = "select * from alternativa, questao_prova, disciplina"
			+ ", materia WHERE questao_prova.id_questao = alternativa.id_questao"
			+ " AND alternativa.id_questao = ? AND questao_prova.disciplina_questao = "
			+ "disciplina.id_disciplina AND materia.id_materia = questao_prova.materia_questao";

	@Autowired
	public ExaminadorDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Alternativa> buscarAlter(Integer id){
		List<Alternativa> alternativas = new ArrayList<Alternativa>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_ALT_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);
				
				
				QuestaoProva qp = new QuestaoProva();
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setDificuldade(rs.getInt("dificuldade"));
				qp.setDisponibilidadeQuestao(rs.getString("disponibilidade_questao"));
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setMateria(m);
				qp.setStatusQuestao(rs.getString("status_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setUltimoUsoQuestao(rs.getDate("ultimo_uso_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				
				Alternativa a = new Alternativa();
				a.setCerta(rs.getString("certa_prova"));
				a.setCorpoAlternativa(rs.getString("corpo_alternativa"));
				a.setIdAlternativa(rs.getInt("id_alternativa"));
				a.setQuestaoAlternativa(qp);
				
				alternativas.add(a);
			}
			
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return alternativas;
	}
	
	public QuestaoProva buscarQuestao(Integer id){
		QuestaoProva qp = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_QUESTAO_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);
				
				qp = new QuestaoProva();
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setDificuldade(rs.getInt("dificuldade"));
				qp.setDisponibilidadeQuestao(rs.getString("disponibilidade_questao"));
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setMateria(m);
				qp.setStatusQuestao(rs.getString("status_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setUltimoUsoQuestao(rs.getDate("ultimo_uso_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return qp;
	}
	
	public List<QuestaoProva> listarPendencias(){
		List<QuestaoProva> questoes = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_QUESTOES_PENDENTES);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);
				
				QuestaoProva q = new QuestaoProva();
				q.setCorpoQuestao(rs.getString("corpo_questao"));
				q.setDificuldade(rs.getInt("dificuldade"));
				q.setDisponibilidadeQuestao(rs.getString("disponibilidade_questao"));
				q.setIdQuestaoProva(rs.getInt("id_questao"));
				q.setMateria(m);
				q.setStatusQuestao(rs.getString("status_questao"));
				q.setTipoQuestao(rs.getString("tipo_questao"));
				q.setTituloQuestao(rs.getString("titulo_questao"));
				q.setUltimoUsoQuestao(rs.getDate("ultimo_uso_questao"));
				q.setUsoQuestao(rs.getInt("uso_questao"));
				q.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				questoes.add(q);
				
			}
			
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return questoes;
	}

	@Override
	public void adicionar(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, examinador.getSexo());
			stmt.setString(2, examinador.getEmail());
			stmt.setBlob(3, (examinador.getFoto() == null) ? null
					: new ByteArrayInputStream(examinador.getFoto()));
			stmt.setDate(4, examinador.getNascimento());
			stmt.setString(5, examinador.getCpf());
			stmt.setString(6, examinador.getRg());
			stmt.setString(7, examinador.getNome());
			stmt.setString(8, examinador.getSenha());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remover(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);
			stmt.setInt(1, examinador.getIdExaminador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void alterar(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, examinador.getSexo());
			stmt.setString(2, examinador.getEmail());
			stmt.setDate(3, examinador.getNascimento());
			stmt.setString(4, examinador.getCpf());
			stmt.setString(5, examinador.getRg());
			stmt.setString(6, examinador.getNome());
			stmt.setInt(7, examinador.getIdExaminador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Examinador> listar() {
		List<Examinador> examinadores = new ArrayList<Examinador>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Examinador exam = new Examinador();
				exam.setIdExaminador(rs.getInt("id_examinador"));
				exam.setSexo(rs.getString("sexo_examinador"));
				exam.setEmail(rs.getString("email_examinador"));
				exam.setFoto(rs.getBytes("foto_examinador"));
				exam.setCpf(rs.getString("cpf_examinador"));
				exam.setRg(rs.getString("rg_examinador"));
				exam.setNascimento(rs.getDate("nascimento_examinador"));
				exam.setNome(rs.getString("nome_examinador"));
				exam.setSenha(rs.getString("senha_examinador"));

				examinadores.add(exam);
			}
			stmt.close();
			rs.close();
			return examinadores;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Examinador buscarID(Integer id) {
		Examinador exam = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				exam = new Examinador();
				exam.setIdExaminador(rs.getInt("id_examinador"));
				exam.setSexo(rs.getString("sexo_examinador"));
				exam.setEmail(rs.getString("email_examinador"));
				exam.setFoto(rs.getBytes("foto_examinador"));
				exam.setCpf(rs.getString("cpf_examinador"));
				exam.setRg(rs.getString("rg_examinador"));
				exam.setNascimento(rs.getDate("nascimento_examinador"));
				exam.setNome(rs.getString("nome_examinador"));
				exam.setSenha(rs.getString("senha_examinador"));
			}
			stmt.close();
			return exam;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ALTERA FOTO
	public void alterarFoto(Examinador exam) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (exam.getFoto() == null) ? null
					: new ByteArrayInputStream(exam.getFoto()));
			stmt.setInt(2, exam.getIdExaminador());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

}
