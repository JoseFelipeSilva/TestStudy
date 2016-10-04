package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class AlternativaDAO implements MetodosBasicos<Alternativa> {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO alternativa (id_questao, corpo_alternativa, certa_prova) VALUES"
			+ "((SELECT max(id_questao) FROM questao_prova), ?, ?)";

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
			stmt.setString(2, "c");
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remover(Alternativa object) {
		// TODO Auto-generated method stub

	}

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
}
