package br.com.senai.testStudy.dao;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.sql.DataSource;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Administrador;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class AdministradorDAO implements MetodosBasicos<Administrador> {
	// conexão com o banco
	private final Connection CONEXAO;
	// Comandos do banco
	private static final String ADD = "INSERT INTO administrador (sexo_adm, email_adm, foto_adm, nascimento_adm, "
			+ "cpf_adm, rg_adm, nome_adm, senha_adm) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String EXCLUIR = "DELETE FROM administrador WHERE id_adm = ?";
	private static final String ALTERAR = "UPDATE administrador SET sexo_adm=?, email_adm=?,"
			+ "nascimento_adm=?, cpf_adm=?, rg_adm=?, nome_adm=? WHERE id_adm=?";
	private static final String LISTAR = "SELECT * FROM administrador";
	private static final String BUSCAR = "SELECT * FROM administrador WHERE id_adm=?";
	private static final String ALT_FOTO = "UPDATE administrador SET foto_adm = ? WHERE id_adm = ?";
	private static final String LOGIN = "SELECT * FROM administrador WHERE senha_adm = ? AND email_adm = ?";
	private static final String ADD_MORTO = "INSERT INTO adm_morto (id_adm_antigo, sexo_adm_antigo, email_adm_antigo, foto_adm_antigo,"
			+ " nascimento_adm_antigo,cpf_adm_amtigo, rg_adm_antigo, nome_adm_antigo, senha_adm_morto) SELECT id_adm, sexo_adm, email_adm,"
			+ " foto_adm, nascimento_adm, cpf_adm, rg_adm, nome_adm, senha_adm FROM administrador WHERE id_adm = ?";

	@Autowired
	public AdministradorDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// METODO DE LOGIN
	public Administrador existeADM(Administrador adm) {
		if (adm == null) {
			throw new IllegalArgumentException(
					"ADMINISTRADOR É NULO, NÃO É POSSIVEL LOGAR");
		}
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LOGIN);

			stmt.setString(1, adm.getSenha());
			stmt.setString(2, adm.getEmail());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				adm.setIdAdm(rs.getInt("id_adm"));
				adm.setNome(rs.getString("nome_adm"));
				adm.setEmail(rs.getString("email_adm"));
				adm.setCpf(rs.getString("cpf_adm"));
				adm.setRg(rs.getString("rg_adm"));
				adm.setNascimento(rs.getDate("nascimento_adm"));
				adm.setSexo(rs.getString("sexo_adm"));
				adm.setFoto(rs.getBytes("foto_adm"));
			} else {
				adm = null;
			}
			rs.close();
			stmt.close();
			return adm;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
	
	public void adicionaMorto (Administrador adm){
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD_MORTO);
			stmt.setInt(1, adm.getIdAdm());
			stmt.execute();
			stmt.close();
		} catch (SQLException e1) {
			throw new RuntimeException(e1.getMessage());

		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Administrador adm) {

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, adm.getSexo());
			stmt.setString(2, adm.getEmail());
			stmt.setBlob(3, (adm.getFoto() == null) ? null
					: new ByteArrayInputStream(adm.getFoto()));
			stmt.setDate(4, adm.getNascimento());
			stmt.setString(5, adm.getCpf());
			stmt.setString(6, adm.getRg());
			stmt.setString(7, adm.getNome());
			stmt.setString(8, adm.getSenha());
			stmt.execute();
			stmt.close();
		} catch (SQLException e1) {
			throw new RuntimeException(e1.getMessage());

		}

	}

	// REMOVER
	@Override
	public void remover(Administrador adm) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);
			stmt.setInt(1, adm.getIdAdm());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ALTERAR
	@Override
	public void alterar(Administrador adm) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, adm.getSexo());
			stmt.setString(2, adm.getEmail());
			stmt.setDate(3, adm.getNascimento());
			stmt.setString(4, adm.getCpf());
			stmt.setString(5, adm.getRg());
			stmt.setString(6, adm.getNome());
			stmt.setInt(7, adm.getIdAdm());
			stmt.execute();
			stmt.close();
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}

	}

	// LISTAR TODOS
	@Override
	public List<Administrador> listar() {
		try {
			List<Administrador> administrador = new ArrayList<Administrador>();
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Administrador adm = new Administrador();

				adm.setIdAdm(rs.getInt("id_adm"));
				adm.setSexo(rs.getString("sexo_adm"));
				adm.setEmail(rs.getString("email_adm"));
				adm.setFoto(rs.getBytes("foto_adm"));
				adm.setNascimento(rs.getDate("nascimento_adm"));
				adm.setCpf(rs.getString("cpf_adm"));
				adm.setRg(rs.getString("rg_adm"));
				adm.setNome(rs.getString("nome_adm"));
				adm.setSenha(rs.getString("senha_adm"));

				administrador.add(adm);
			}
			rs.close();
			stmt.close();
			return administrador;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// BUSCAR POR ID
	@Override
	public Administrador buscarID(Integer id) {
		Administrador adm = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				adm = new Administrador();
				adm.setIdAdm(rs.getInt("id_adm"));
				adm.setNome(rs.getString("nome_adm"));
				adm.setEmail(rs.getString("email_adm"));
				adm.setCpf(rs.getString("cpf_adm"));
				adm.setRg(rs.getString("rg_adm"));
				adm.setNascimento(rs.getDate("nascimento_adm"));
				adm.setSexo(rs.getString("sexo_adm"));
				adm.setFoto(rs.getBytes("foto_adm"));
			}
			stmt.close();
			return adm;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ALTERAR FOTO ADM
	public void alterarFoto(Administrador adm) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (adm.getFoto() == null) ? null
					: new ByteArrayInputStream(adm.getFoto()));
			stmt.setInt(2, adm.getIdAdm());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

}
