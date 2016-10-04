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
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class DisciplinaDAO implements MetodosBasicos<Disciplina> {
	private static Connection CONEXAO;
	private static final String ADICIONAR = "INSERT INTO disciplina (nome_disciplina) VALUES (?)";
	private static final String ALTERAR = "UPDATE disciplina SET nome_disciplina=? WHERE id_disciplina=?";
	private static final String LISTAR = "SELECT * FROM disciplina";
	private static final String BUSCAR = "SELECT * FROM disciplina WHERE id_disciplina=?";
	private static final String EXCLUIR = "DELETE FROM disciplina WHERE id_disciplina=?";
	
	
	@Autowired
	public DisciplinaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
/*	public List<DisciplinaMateriaWS> disciplinaMateria(){
		List<DisciplinaMateriaWS> disciplinas = new ArrayList<DisciplinaMateriaWS>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_DISC_MAT_WS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/

	@Override
	public void adicionar(Disciplina disciplina) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, disciplina.getNomeDisciplina());
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

}