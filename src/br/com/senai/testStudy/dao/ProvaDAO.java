package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ProvaDAO implements MetodosBasicos<Prova> {
	private final static String ADD = "INSERT INTO prova (nome_prova, dificuldade, n_questoes, id_professor) VALUES (?, ?, ?, ?)";
	private final static String EXCLUIR = "DELETE FROM prova WHERE id_prova = ?";
	private final static String ALTERAR = "UPDATE prova SET nome_prova = ?, dificuldade = ?, n_questoes = ?, id_professor = ? "
			+ "WHERE id_prova = ?";
	private final static String LISTAR = "";
	private final static String BUSCAR = "";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public ProvaDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Prova prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);
			stmt.setString(1, prova.getNomeProva());
			stmt.setString(2, prova.getDificuldade());
			stmt.setInt(3, prova.getnQuestoes());
			stmt.setInt(4, prova.getProfessor().getIdProfessor());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(Prova prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, prova.getIdProva());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Prova prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, prova.getNomeProva());
			stmt.setString(2, prova.getDificuldade());
			stmt.setInt(3, prova.getnQuestoes());
			stmt.setInt(4, prova.getProfessor().getIdProfessor());
			stmt.setInt(5, prova.getIdProva());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	@Override
	public List<Prova> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prova buscarID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
