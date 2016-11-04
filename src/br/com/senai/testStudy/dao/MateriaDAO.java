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
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class MateriaDAO implements MetodosBasicos<Materia> {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO materia (id_disciplina, nome_materia) VALUES"
			+ "(?, ?) ";
	private static final String LISTAR_DISC = "SELECT * FROM disciplina WHERE escola_disciplina = 1 OR escola_disciplina = ?";
	private static final String LISTAR = "SELECT materia.*, disciplina.* FROM materia,"
			+ " disciplina WHERE materia.id_disciplina = disciplina.id_disciplina order by materia.id_materia";
	private static final String BUSCAR = "SELECT materia.*, disciplina.*"
			+ " FROM materia, disciplina WHERE materia.id_disciplina ="
			+ " disciplina.id_disciplina AND materia.id_materia = ?";
	private static final String ALTERAR = "UPDATE materia SET id_disciplina = ?,"
			+ " nome_materia = ? where id_materia = ?";
	private static final String LISTAR_MATERIA = "SELECT materia.*, disciplina.*"
			+ " FROM materia,disciplina WHERE materia.id_disciplina = disciplina.id_disciplina AND materia.id_disciplina = ?";

	@Autowired
	public MateriaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Materia> listarMateria(Disciplina disc) {
		List<Materia> materias = new ArrayList<Materia>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_MATERIA);
			stmt.setInt(1, disc.getIdDisciplina());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);
				materias.add(m);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return materias;
	}

	public List<Disciplina> listarDisc(Integer id) {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_DISC);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				disciplinas.add(d);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;

	}

	@Override
	public void adicionar(Materia m) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setInt(1, m.getDisciplina().getIdDisciplina());
			stmt.setString(2, m.getNomeMateria());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remover(Materia object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alterar(Materia m) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setInt(1, m.getDisciplina().getIdDisciplina());
			stmt.setString(2, m.getNomeMateria());
			stmt.setInt(3, m.getIdMateria());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Materia> listar() {
		List<Materia> materias = new ArrayList<Materia>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);
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
	public Materia buscarID(Integer id) {
		Disciplina d = null;
		Materia m = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_MATERIA);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return m;
	}

}
