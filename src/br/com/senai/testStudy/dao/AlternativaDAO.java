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

import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class AlternativaDAO implements MetodosBasicos<Alternativa> {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO alternativa (id_questao, corpo_alternativa, certa_prova) VALUES"
			+ "((SELECT max(id_questao) FROM questao_prova), ?, ?)";
	private static final String BUSCAR_POR_QUESTAO = "select * from alternativa, questao_prova WHERE alternativa.id_questao"
			+ " = questao_prova.id_questao AND alternativa.id_questao = ?"; 
	private static final String BUSCAR_POR_PROVA = "select * from alternativa, questao_prova, prova, prova_agendada, prova_questao, disciplina, materia, professor "
			+ "WHERE prova.id_prova = prova_agendada.id_prova AND prova_questao.id_prova = prova_agendada.id_prova AND prova_questao.id_questao = questao_prova.id_questao "
			+ "AND questao_prova.disciplina_questao = disciplina.id_disciplina AND materia.id_materia = questao_prova.materia_questao "
			+ "AND questao_prova.id_questao = alternativa.id_questao AND prova_agendada.id_turma = turma.id_turma"
			+ " AND prova.id_professor = professor.id_professor AND turma.id_escola = escola_cliente.id_escola_cliente"
			+ " AND prova_agendada.id_prova_agendada = ? group by alternativa.id_alternativa;"; 

	@Autowired
	public AlternativaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void adicionar(Alternativa a) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, a.getCorpoAlternativa());
			stmt.setString(2, a.getCerta());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	@Deprecated
	@Override
	public void remover(Alternativa object) {
		// TODO Auto-generated method stub

	}
	@Deprecated
	@Override
	public void alterar(Alternativa object) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Alternativa> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alternativa buscarID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// método responsável por buscar as alternativas de acordo com o id da questão
	public List<Alternativa> listarPorQuestao(Integer idQuestao){
		List<Alternativa> alternativas = new ArrayList<Alternativa>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_POR_QUESTAO);
			stmt.setInt(1, idQuestao);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestaoProva qp = new QuestaoProva();
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setDificuldade(rs.getInt("dificuldade"));
				
				Alternativa a = new Alternativa();
				a.setIdAlternativa(rs.getInt("id_alternativa"));
				a.setCerta(rs.getString("certa_prova"));
				a.setCorpoAlternativa(rs.getString("corpo_alternativa"));
			//	a.setQuestaoAlternativa(qp); TODO
				alternativas.add(a);
			}
			stmt.close();
			rs.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return alternativas;		
	}

	public ProvaAgendada buscarProva(Integer idProvaAgendada) {
		ProvaAgendada pa = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_POR_PROVA);
			stmt.setInt(1, idProvaAgendada);
			ResultSet rs = stmt.executeQuery();
				List<QuestaoProva> lqp = new ArrayList<QuestaoProva>();
				List<Alternativa> lalt = new ArrayList<Alternativa>();
				while (rs.next()) {
					EscolaCliente escola = new EscolaCliente();
					escola.setIdEmp(rs.getInt("id_escola_cliente"));
					escola.setNomeEmp(rs.getString("nome_emp"));
					escola.setEmailEmp(rs.getString("email_emp"));
					escola.setCnpjEmp(rs.getString("cnpj_emp"));
					escola.setTelefoneEmp(rs.getString("telefone_emp"));
					escola.setNomeEmpresarialEmp(rs.getString("razao_social_emp"));
					
					Turma t = new Turma();
					t.setIdTurma(rs.getInt("id_turma"));
					t.setNomeTurma(rs.getString("nome_turma"));
					t.setEscolaTurma(escola);
					
					Alternativa a = new Alternativa();
					a.setIdAlternativa(rs.getInt("id_alternativa"));
					a.setCerta(rs.getString("certa_prova"));
					a.setCorpoAlternativa(rs.getString("corpo_alternativa"));
					lalt.add(a);
					
					Disciplina d = new Disciplina();
					d.setIdDisciplina(rs.getInt("id_disciplina"));
					d.setNomeDisciplina(rs.getString("nome_disciplina"));
					d.setPadraoDisciplina(rs.getString("padrao_disciplina"));
					
					Materia m = new Materia();
					m.setDisciplina(d);
					m.setIdMateria(rs.getInt("id_materia"));
					m.setNomeMateria(rs.getString("nome_materia"));
									
					
					QuestaoProva qp = new QuestaoProva();
					qp.setIdQuestaoProva(rs.getInt("id_questao"));
					qp.setTituloQuestao(rs.getString("titulo_questao"));
					qp.setCorpoQuestao(rs.getString("corpo_questao"));
					qp.setTipoQuestao(rs.getString("tipo_questao"));
					qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
					qp.setUsoQuestao(rs.getInt("uso_questao"));
					qp.setMateria(m);
					if (lqp.contains(qp)) {						
						qp = lqp.get(lqp.indexOf(qp));
						lqp.remove(qp);
						qp.setAlternativas(lalt);
					}else {
						qp.setAlternativas(lalt);
					}					
							
				
				Professor prof = new Professor();
				prof.setNome(rs.getString("nome_professor"));
				prof.setEmail(rs.getString("email_professor"));
				prof.setIdProfessor(rs.getInt("id_professor"));
				prof.setSexo(rs.getString("sexo_professor"));
				
				Prova p = new Prova();
				p.setNomeProva(rs.getString("nome_prova"));
				p.setIdProva(rs.getInt("id_prova"));
				p.setDificuldadeATE(rs.getInt("dificuldadeATE"));
				p.setDificuldadeDE(rs.getInt("dificuldadeDE"));
				p.setnQuestoes(rs.getInt("n_questoes"));
				p.setProfessor(prof);
				
				pa = new ProvaAgendada();
				pa.setIdProvaAgendada(rs.getInt("id_prova_agendada"));
				pa.setDataInicio(rs.getTimestamp("data_inicio").toLocalDateTime());
				pa.setDataTermino(rs.getTimestamp("data_termino").toLocalDateTime());
				pa.setDuracao(rs.getInt("duracao_prova"));
				pa.setProva(p);
				pa.setTurma(t);
				}
			stmt.close();
			rs.close();
			return pa;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
}


































