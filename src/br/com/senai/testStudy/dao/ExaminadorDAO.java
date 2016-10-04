package br.com.senai.testStudy.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ExaminadorDAO implements MetodosBasicos<Examinador> {
	private final Connection CONEXAO;
	private static final String ADD = "INSERT INTO examinador"
			+ " (sexo_examinador, email_examinador, foto_examinador,"
			+ " nascimento_examinador, cpf_examinador, rg_examinador,"
			+ " nome_examinador, senha_examinador) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String LISTAR = "SELECT * FROM examinador";
	private static final String BUSCAR = "SELECT * FROM examinador WHERE id_examinador =?";
	private static final String ALTERAR = "UPDATE examinador SET sexo_examinador=?, email_examinador=?,"
			+ "nascimento_examinador=?, cpf_examinador=?, rg_examinador=?, nome_examinador=?"
			+ " WHERE id_examinador=?";
	private static final String EXCLUIR = "DELETE FROM examinador WHERE id_examinador = ?";
	private static final String ALT_FOTO = "UPDATE examinador SET foto_examinador = ? WHERE id_examinador = ?";

	@Autowired
	public ExaminadorDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void adicionar(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, examinador.getSexo());
			stmt.setString(2, examinador.getEmail());
			stmt.setBlob(3, (examinador.getFoto() == null) ? null
					: new ByteArrayInputStream(examinador.getFoto()));
			stmt.setDate(4, examinador.getNascimento());
			stmt.setString(5, examinador.getCpf());
			stmt.setString(6, examinador.getRg());
			stmt.setString(7, examinador.getNome());
			stmt.setString(8, examinador.getSenha());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remover(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);
			stmt.setInt(1, examinador.getIdExaminador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void alterar(Examinador examinador) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, examinador.getSexo());
			stmt.setString(2, examinador.getEmail());
			stmt.setDate(3, examinador.getNascimento());
			stmt.setString(4, examinador.getCpf());
			stmt.setString(5, examinador.getRg());
			stmt.setString(6, examinador.getNome());
			stmt.setInt(7, examinador.getIdExaminador());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Examinador> listar() {
		List<Examinador> examinadores = new ArrayList<Examinador>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Examinador exam = new Examinador();
				exam.setIdExaminador(rs.getInt("id_examinador"));
				exam.setSexo(rs.getString("sexo_examinador"));
				exam.setEmail(rs.getString("email_examinador"));
				exam.setFoto(rs.getBytes("foto_examinador"));
				exam.setCpf(rs.getString("cpf_examinador"));
				exam.setRg(rs.getString("rg_examinador"));
				exam.setNascimento(rs.getDate("nascimento_examinador"));
				exam.setNome(rs.getString("nome_examinador"));
				exam.setSenha(rs.getString("senha_examinador"));

				examinadores.add(exam);
			}
			stmt.close();
			rs.close();
			return examinadores;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Examinador buscarID(Integer id) {
		Examinador exam = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				exam = new Examinador();
				exam.setIdExaminador(rs.getInt("id_examinador"));
				exam.setSexo(rs.getString("sexo_examinador"));
				exam.setEmail(rs.getString("email_examinador"));
				exam.setFoto(rs.getBytes("foto_examinador"));
				exam.setCpf(rs.getString("cpf_examinador"));
				exam.setRg(rs.getString("rg_examinador"));
				exam.setNascimento(rs.getDate("nascimento_examinador"));
				exam.setNome(rs.getString("nome_examinador"));
				exam.setSenha(rs.getString("senha_examinador"));
			}
			stmt.close();
			return exam;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ALTERA FOTO
	public void alterarFoto(Examinador exam) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (exam.getFoto() == null) ? null
					: new ByteArrayInputStream(exam.getFoto()));
			stmt.setInt(2, exam.getIdExaminador());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

}
