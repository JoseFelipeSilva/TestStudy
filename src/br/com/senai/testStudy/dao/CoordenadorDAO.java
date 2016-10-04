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

import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class CoordenadorDAO implements MetodosBasicos<Coordenador> {
	private final static String ADD = "INSERT INTO coordenador (nome_coord, email_coord, senha_coord, sexo_coord, rg_coord, cpf_coord, "
			+ "nascimento_coord, foto_coord, id_escola) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String ALTERAR = "UPDATE coordenador SET nome_coord = ?, email_coord = ?, senha_coord = ?, sexo_coord = ?, rg_coord = ?,"
			+ " cpf_coord = ?, nascimento_coord = ?, id_escola = ? WHERE id_coord = ?";
	private final static String LISTAR = "SELECT c.id_coord, c.nome_coord, c.email_coord, c.senha_coord, c.foto_coord, c.sexo_coord, "
			+ "c.nascimento_coord, c.cpf_coord, c.rg_coord, e.nome_emp, e.id_escola_cliente FROM coordenador AS c, escola_cliente AS e "
			+ "WHERE id_escola_cliente = id_escola ORDER BY c.id_coord ASC";	
	private final static String BUSCAR = "SELECT c.id_coord, c.nome_coord, c.email_coord, c.senha_coord, c.foto_coord, c.sexo_coord, "
			+ "c.nascimento_coord, c.cpf_coord, c.rg_coord, e.nome_emp, e.id_escola_cliente FROM coordenador AS c, escola_cliente AS e "
			+ "WHERE id_escola_cliente = id_escola AND id_coord = ?";
	private final static String EXCLUIR = "DELETE FROM coordenador WHERE id_coord =?";
	private final static String ALT_FOTO = "UPDATE coordenador SET foto_coord =?  WHERE id_coord = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public CoordenadorDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Coordenador coord) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, coord.getNome());
			stmt.setString(2, coord.getEmail());
			stmt.setString(3, coord.getSenha());
			stmt.setString(4, coord.getSexo());
			stmt.setString(5, coord.getRg());
			stmt.setString(6, coord.getCpf());
			stmt.setDate(7, coord.getNascimento());
			stmt.setBlob(8, (coord.getFoto() == null) ? null : // COLOCAR UMA
																// IMAGEM PADRÃO
																// CASO A FT N
																// SEJA
																// SELECIONADA
					new ByteArrayInputStream(coord.getFoto()));
			stmt.setInt(9, coord.getEscola().getIdEmp());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(Coordenador coord) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, (Integer) coord.getIdCoord());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Coordenador coord) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setString(1, coord.getNome());
			stmt.setString(2, coord.getEmail());
			stmt.setString(3, coord.getSenha());
			stmt.setString(4, coord.getSexo());
			stmt.setString(5, coord.getRg());
			stmt.setString(6, coord.getCpf());
			stmt.setDate(7, coord.getNascimento());
			stmt.setInt(8, coord.getEscola().getIdEmp());
			stmt.setInt(9, coord.getIdCoord());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TUDO
	@Override
	public List<Coordenador> listar() {
		List<Coordenador> listCoord = new ArrayList<>();

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setNomeEmp(rs.getString("nome_emp"));
				escola.setIdEmp(rs.getInt("id_escola_cliente"));

				Coordenador c = new Coordenador();
				c.setIdCoord(rs.getInt("id_coord"));
				c.setNome(rs.getString("nome_coord"));
				c.setEmail(rs.getString("email_coord"));
				c.setSenha(rs.getString("senha_coord"));
				c.setNascimento(rs.getDate("nascimento_coord"));
				c.setCpf(rs.getString("cpf_coord"));
				c.setRg(rs.getString("rg_coord"));
				c.setSexo(rs.getString("sexo_coord"));
				c.setFoto(rs.getBytes("foto_coord"));
				c.setEscola(escola);

				listCoord.add(c);
			}
			rs.close();
			stmt.close();
			return listCoord;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Coordenador buscarID(Integer id) {
		Coordenador c = null;

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setIdEmp(rs.getInt("id_escola_cliente"));
				escola.setNomeEmp(rs.getString("nome_emp"));

				c = new Coordenador();
				c.setIdCoord(rs.getInt("id_coord"));
				c.setNome(rs.getString("nome_coord"));
				c.setEmail(rs.getString("email_coord"));
				c.setSenha(rs.getString("senha_coord"));
				c.setNascimento(rs.getDate("nascimento_coord"));
				c.setCpf(rs.getString("cpf_coord"));
				c.setRg(rs.getString("rg_coord"));
				c.setSexo(rs.getString("sexo_coord"));
				c.setFoto(rs.getBytes("foto_coord"));
				c.setEscola(escola);
			}
			stmt.close();
			return c;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR FOTO DE PERFIL
	public void alterarPhoto(Coordenador coord) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (coord.getFoto() == null) ? null
					: new ByteArrayInputStream(coord.getFoto()));
			stmt.setInt(2, coord.getIdCoord());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
}