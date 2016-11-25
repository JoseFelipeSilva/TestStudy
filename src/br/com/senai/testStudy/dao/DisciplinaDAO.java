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
import br.com.senai.testStudy.model.DisciplinaMateriaWS;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class DisciplinaDAO implements MetodosBasicos<Disciplina> {
	private final Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO disciplina(nome_disciplina, padrao_disciplina, escola_disciplina)VALUES(?,?,?)";
	private static final String ALTERAR = "UPDATE disciplina SET nome_disciplina=? WHERE id_disciplina=?";
	private static final String LISTAR = "SELECT * FROM disciplina WHERE escola_disciplina = 1";
	private static final String BUSCAR = "SELECT * FROM disciplina WHERE id_disciplina=?";
	private static final String EXCLUIR = "DELETE FROM disciplina WHERE id_disciplina=?";
	private static final String LISTAR_DISC_MAT_WS = "SELECT  materia.*, disciplina.*"
			+ " FROM materia, disciplina WHERE materia.id_disciplina = "
			+ "disciplina.id_disciplina AND materia.id_disciplina";
	private static final String LISTAR_DISC_PRIVADA = "SELECT * FROM disciplina WHERE escola_disciplina = ?";
	private static final String BUSCAR_POR_MATERIA = "SELECT * FROM materia,"
			+ " disciplina WHERE materia.id_disciplina = disciplina.id_disciplina AND materia.id_materia = ?";

	@Autowired
	public DisciplinaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Disciplina> listarDiscPrivada(Integer id){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_DISC_PRIVADA);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina disc = new Disciplina();
				disc.setIdDisciplina(rs.getInt("id_disciplina"));
				disc.setNomeDisciplina(rs.getString("nome_disciplina"));
				disciplinas.add(disc);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;
	}

	public List<DisciplinaMateriaWS> disciplinaMateria() {
		List<DisciplinaMateriaWS> disciplinas = new ArrayList<DisciplinaMateriaWS>();
		try {
			PreparedStatement stmt = CONEXAO
					.prepareStatement(LISTAR_DISC_MAT_WS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));

				Materia m = new Materia();
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				m.setDisciplina(d);

				DisciplinaMateriaWS ws = new DisciplinaMateriaWS();
				ws.setDisciplina(d);
				ws.setMateria(m);

				disciplinas.add(ws);

			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;
	}

	@Override
	public void adicionar(Disciplina disciplina) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, disciplina.getNomeDisciplina());
			stmt.setString(2, disciplina.getPadraoDisciplina());
			stmt.setInt(3, disciplina.getEscola().getIdEmp());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remover(Disciplina disciplina) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);
			stmt.setInt(1, disciplina.getIdDisciplina());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void alterar(Disciplina disciplina) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, disciplina.getNomeDisciplina());
			stmt.setInt(2, disciplina.getIdDisciplina());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Disciplina> listar() {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina disc = new Disciplina();
				disc.setIdDisciplina(rs.getInt("id_disciplina"));
				disc.setNomeDisciplina(rs.getString("nome_disciplina"));
				disciplinas.add(disc);
			}
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;
	}

	@Override
	public Disciplina buscarID(Integer id) {
		Disciplina disciplina = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				disciplina = new Disciplina();
				disciplina.setIdDisciplina(rs.getInt("id_disciplina"));
				disciplina.setNomeDisciplina(rs.getString("nome_disciplina"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplina;
	}
	
	public Disciplina buscaPorMateria(Integer idMateria){
		Disciplina disciplina = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_POR_MATERIA);
			stmt.setInt(1, idMateria);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				disciplina = new Disciplina();
				disciplina.setNomeDisciplina(rs.getString("nome_disciplina"));
				disciplina.setIdDisciplina(rs.getInt("id_disciplina"));
				disciplina.setPadraoDisciplina(rs.getString("padrao_disciplina"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplina;	
	}

}
