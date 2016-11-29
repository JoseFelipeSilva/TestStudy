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
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class AlternativaDAO implements MetodosBasicos<Alternativa> {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO alternativa (id_questao, corpo_alternativa, certa_prova) VALUES"
			+ "((SELECT max(id_questao) FROM questao_prova), ?, ?)";
	private static final String BUSCAR_POR_QUESTAO = "select * from alternativa, questao_prova WHERE alternativa.id_questao"
			+ " = questao_prova.id_questao AND alternativa.id_questao = ?"; 
	private static final String BUSCAR_POR_PROVA = "select * from alternativa, questao_prova, disciplina, materia, professor, prova, prova_agendada, prova_questao 	WHERE prova_questao.id_questao = questao_prova.id_questao     AND prova_questao.id_prova = prova.id_prova    AND professor.id_professor = prova.id_professor    AND prova.id_prova = prova_agendada.id_prova    AND questao_prova.id_questao = alternativa.id_questao    AND questao_prova.disciplina_questao = disciplina.id_disciplina    AND materia.id_materia = questao_prova.materia_questao    AND prova_agendada.id_prova_agendada = 25;"; 

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

	public ProvaAgendada buscarProva(Integer idProvaAgendada) {
		// TODO Auto-generated method stub
		return null;
	}
}


































