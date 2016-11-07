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

import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ProvaDAO implements MetodosBasicos<Prova> {
	private final static String ADD = "INSERT INTO prova (nome_prova, dificuldade, n_questoes, id_professor, data_prova) VALUES (?, ?, ?, ?, "
			+ "DATE(now()))";
	private final static String EXCLUIR = "DELETE FROM prova WHERE id_prova = ?";
	private final static String ALTERAR = "UPDATE prova SET nome_prova = ?, dificuldade = ?, n_questoes = ?, id_professor = ? "
			+ "WHERE id_prova = ?";
	private final static String LISTAR = "SELECT p.nome_prova, p.dificuldade, p.n_questoes, p.id_professor, pr.nome_professor, pr.id_professor, "
			+ "p.id_prova, p.data_prova FROM prova AS p, professor AS pr WHERE p.id_professor = pr.id_professor";
	private final static String BUSCAR = "SELECT p.nome_prova, p.dificuldade, p.n_questoes, p.id_professor, pr.nome_professor, pr.id_professor, "
			+ "p.id_prova, p.data_prova FROM prova AS p, professor AS pr WHERE p.id_professor = pr.id_professor AND p.id_prova = ?";

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

	// LISTAR TODOS
	@Override
	public List<Prova> listar() {
		List<Prova> provas = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Professor prof = new Professor();
				prof.setIdProfessor(rs.getInt("id_professor"));
				prof.setNome(rs.getString("nome_professor"));

				Prova p = new Prova();
				p.setDificuldade(rs.getString("dificuldade"));
				p.setNomeProva(rs.getString("nome_prova"));
				p.setnQuestoes(rs.getInt("n_questoes"));
				p.setIdProva(rs.getInt("id_prova"));
				p.setCriacaoProva(rs.getDate("data_prova"));
				p.setProfessor(prof);

				provas.add(p);
			}
			rs.close();
			stmt.close();
			return provas;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Prova buscarID(Integer id) {
		Prova p = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Professor prof = new Professor();
				prof.setIdProfessor(rs.getInt("id_professor"));
				prof.setNome(rs.getString("nome_professor"));

				p = new Prova();
				p.setDificuldade(rs.getString("dificuldade"));
				p.setNomeProva(rs.getString("nome_prova"));
				p.setnQuestoes(rs.getInt("n_questoes"));
				p.setCriacaoProva(rs.getDate("data_prova"));
				p.setIdProva(rs.getInt("id_prova"));
				p.setProfessor(prof);

			}
			stmt.close();
			return p;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}
}
