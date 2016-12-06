package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.FazendoProva;

@Repository
public class FazendoProvaDAO  {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO fazendo_prova (id_aluno, id_questao_prova, resposta, id_alternativa, id_prova_agendada)"
			+ "VALUES(?, ?, ?, ?, ?)";
	
	
	@Autowired
	public FazendoProvaDAO (DataSource dataSource){
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}


	public void adicionar(FazendoProva fazendoProva, Integer i) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setInt(1, fazendoProva.getAluno().getIdAluno());
			stmt.setInt(2, fazendoProva.getProvaAgendada().getProva().getQuestoes().get(i).getIdQuestaoProva());
			stmt.setString(3, fazendoProva.getResposta());
			stmt.setInt(4, fazendoProva.getAlternativa().getIdAlternativa());
			stmt.setInt(5, fazendoProva.getProvaAgendada().getIdProvaAgendada());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Deprecated
	public void remover(FazendoProva object) {
		// TODO Auto-generated method stub
		
	}
	
	@Deprecated
	public void alterar(FazendoProva object) {
		// TODO Auto-generated method stub
		
	}

	public List<FazendoProva> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public FazendoProva buscarID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
