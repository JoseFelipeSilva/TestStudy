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

import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.Simulado;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class SimuladoDAO implements MetodosBasicos<Simulado> {
	private final static String ADD = "INSERT INTO simulado (id_aluno, visualizacao_simulado) VALUES (?, ?)";
	private final static String EXCLUIR = "DELETE FROM simulado WHERE id_simulado = ?";
	private final static String ALTERAR = "UPDATE simulado SET id_aluno = ?, visualizacao_simulado = ? WHERE "
			+ "id_simulado = ?";
	private final static String LISTAR = "SELECT s.id_simulado, s.visualizacao_simulado, s.id_aluno, a.id_aluno, "
			+ "a.nome_aluno FROM simulado AS s, aluno AS a WHERE s.id_aluno = a.id_aluno";
	private final static String BUSCAR = "SELECT s.id_simulado, s.visualizacao_simulado, s.id_aluno, a.id_aluno, "
			+ "a.nome_aluno FROM simulado AS s, aluno AS a WHERE s.id_aluno = a.id_aluno AND s.id_simulado = ? "
			+ "AND s.id_simulado = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public SimuladoDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Simulado simulado) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setInt(1, simulado.getAlunoSimulado().getIdAluno());
			stmt.setString(2, simulado.getVisualizacaoSimulado());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(Simulado simulado) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, simulado.getIdSimulado());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Simulado simulado) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setInt(1, simulado.getAlunoSimulado().getIdAluno());
			stmt.setString(2, simulado.getVisualizacaoSimulado());
			stmt.setInt(3, simulado.getIdSimulado());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<Simulado> listar() {
		List<Simulado> simulados = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Aluno a = new Aluno();
				a.setNomeAluno(rs.getString("nome_aluno"));
				a.setIdAluno(rs.getInt("id_aluno"));

				Simulado s = new Simulado();
				s.setIdSimulado(rs.getInt("id_simulado"));
				s.setVisualizacaoSimulado(rs.getString("visualizacao_simulado"));
				s.setAlunoSimulado(a);

				simulados.add(s);
			}
			rs.close();
			stmt.close();
			return simulados;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Simulado buscarID(Integer id) {
		Simulado s = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Aluno a = new Aluno();
				a.setNomeAluno(rs.getString("nome_aluno"));
				a.setIdAluno(rs.getInt("id_aluno"));

				s = new Simulado();
				s.setIdSimulado(rs.getInt("id_simulado"));
				s.setVisualizacaoSimulado(rs.getString("visualizacao_simulado"));
				s.setAlunoSimulado(a);
			}
			stmt.close();
			return s;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}
}
