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

import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ProfessorDAO implements MetodosBasicos<Professor> {
	private final static String ADD = "INSERT INTO professor (nome_professor, email_professor, sexo_professor, senha_professor, "
			+ "rg_professor, cpf_professor, nascimento_professor, foto_professor, id_escola_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String EXCLUIR = "DELETE FROM professor WHERE id_professor = ?";
	private final static String LISTAR = "SELECT p.id_professor, p.id_escola_cliente, p.sexo_professor, p.email_professor, p.foto_professor, p.rg_professor, "
			+ "p.cpf_professor,	p.nascimento_professor, p.nome_professor, p.senha_professor, e.nome_emp, "
			+ "e.id_escola_cliente FROM professor AS p, escola_cliente AS e WHERE e.id_escola_cliente = p.id_escola_cliente ORDER BY p.id_professor ASC";
	private final static String ALTERAR = "UPDATE professor SET nome_professor = ?, email_professor = ?, sexo_professor = ?, senha_professor = ?, "
			+ "rg_professor = ?, cpf_professor = ?, nascimento_professor = ?, id_escola_cliente = ? WHERE id_professor = ?";
	private final static String BUSCAR = "SELECT p.id_professor, p.id_escola_cliente, p.sexo_professor, p.email_professor, p.foto_professor, p.rg_professor, "
			+ "p.cpf_professor,	p.nascimento_professor, p.nome_professor, p.senha_professor, e.nome_emp, "
			+ "e.id_escola_cliente FROM professor AS p, escola_cliente AS e WHERE e.id_escola_cliente = p.id_escola_cliente AND "
			+ "p.id_professor = ?";
	private final static String ALT_FOTO = "UPDATE professor SET foto_professor = ? WHERE id_professor = ?";
	private final static String LOGIN = "SELECT p.id_professor, p.id_escola_cliente, p.sexo_professor, p.email_professor, p.foto_professor, p.rg_professor, "
			+ "p.cpf_professor,	p.nascimento_professor, p.nome_professor, p.senha_professor, e.nome_emp, "
			+ "e.id_escola_cliente FROM professor AS p, escola_cliente AS e WHERE p.senha_professor = ? AND p.email_professor = ?";
	private final static String ADD_MORTO = "INSERT INTO professor_morto (nome_professor_morto, id_professor_antigo, sexo_professor_antigo,"
			+ " nascimento_professor_morto, email_professor_morto, foto_professor_morto, rg_professor_morto, cpf_professor_morto, senha_professor_morto)"
			+ " SELECT nome_professor, id_professor, sexo_professor, nascimento_professor, email_professor, foto_professor, rg_professor,"
			+ " cpf_professor, senha_professor FROM professor WHERE id_professor = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public ProfessorDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// METODO DE LOGIN
	public Professor existeProf(Professor prof) {
		if (prof == null) {
			throw new IllegalArgumentException(
					"NÃO EXISTE ESSE PROFESSOR, NÃO FOI POSSIVEL FAZER LOGIN");
		}
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LOGIN);

			stmt.setString(1, prof.getSenha());
			stmt.setString(2, prof.getEmail());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setIdEmp(rs.getInt("id_escola_cliente"));
				escola.setNomeEmp(rs.getString("nome_emp"));

				prof = new Professor();
				prof.setIdProfessor(rs.getInt("id_professor"));
				prof.setNome(rs.getString("nome_professor"));
				prof.setEmail(rs.getString("email_professor"));
				prof.setSenha(rs.getString("senha_professor"));
				prof.setCpf(rs.getString("cpf_professor"));
				prof.setRg(rs.getString("rg_professor"));
				prof.setSexo(rs.getString("sexo_professor"));
				prof.setFoto(rs.getBytes("foto_professor"));
				prof.setNascimento(rs.getDate("nascimento_professor"));
				prof.setEscolaProfessor(escola);
			} else {
				prof = null;
			}
			rs.close();
			stmt.close();
			return prof;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Professor prof) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, prof.getNome());
			stmt.setString(2, prof.getEmail());
			stmt.setString(3, prof.getSexo());
			stmt.setString(4, prof.getSenha());
			stmt.setString(5, prof.getRg());
			stmt.setString(6, prof.getCpf());
			stmt.setDate(7, prof.getNascimento());
			stmt.setBlob(8, (prof.getFoto() == null) ? null
					: new ByteArrayInputStream(prof.getFoto()));
			stmt.setInt(9, prof.getEscolaProfessor().getIdEmp());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(Professor prof) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, prof.getIdProfessor());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Professor prof) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setString(1, prof.getNome());
			stmt.setString(2, prof.getEmail());
			stmt.setString(3, prof.getSexo());
			stmt.setString(4, prof.getSenha());
			stmt.setString(5, prof.getRg());
			stmt.setString(6, prof.getCpf());
			stmt.setDate(7, prof.getNascimento());
			stmt.setInt(8, prof.getEscolaProfessor().getIdEmp());
			stmt.setInt(9, prof.getIdProfessor());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}

	}

	// LISTAR TODOS
	@Override
	public List<Professor> listar() {
		List<Professor> professores = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setNomeEmp(rs.getString("nome_emp"));
				escola.setIdEmp(rs.getInt("id_escola_cliente"));

				Professor p = new Professor();
				p.setIdProfessor(rs.getInt("id_professor"));
				p.setNome(rs.getString("nome_professor"));
				p.setEmail(rs.getString("email_professor"));
				p.setSenha(rs.getString("senha_professor"));
				p.setCpf(rs.getString("cpf_professor"));
				p.setRg(rs.getString("rg_professor"));
				p.setSexo(rs.getString("sexo_professor"));
				p.setFoto(rs.getBytes("foto_professor"));
				p.setNascimento(rs.getDate("nascimento_professor"));
				p.setEscolaProfessor(escola);

				professores.add(p);
			}
			rs.close();
			stmt.close();
			return professores;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Professor buscarID(Integer id) {
		Professor p = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setIdEmp(rs.getInt("id_escola_cliente"));
				escola.setNomeEmp(rs.getString("nome_emp"));

				p = new Professor();
				p.setIdProfessor(rs.getInt("id_professor"));
				p.setNome(rs.getString("nome_professor"));
				p.setEmail(rs.getString("email_professor"));
				p.setSenha(rs.getString("senha_professor"));
				p.setCpf(rs.getString("cpf_professor"));
				p.setRg(rs.getString("rg_professor"));
				p.setSexo(rs.getString("sexo_professor"));
				p.setFoto(rs.getBytes("foto_professor"));
				p.setNascimento(rs.getDate("nascimento_professor"));
				p.setEscolaProfessor(escola);
			}
			stmt.close();
			return p;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR FOTO DE PERFIL
	public void alterarFoto(Professor prof) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (prof.getFoto() == null) ? null
					: new ByteArrayInputStream(prof.getFoto()));
			stmt.setInt(2, prof.getIdProfessor());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

}
