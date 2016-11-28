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

import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class AlunoDAO implements MetodosBasicos<Aluno> {
	private final static String ADD = "INSERT INTO aluno (nome_aluno, email_aluno, rg_aluno, sexo_aluno"
			+ ", nascimento_aluno, foto_aluno, id_turma, senha_aluno) VALUES (?, ?, ?, ?, ?, ?, ?, rg_aluno)";
	private final static String EXCLUIR = "DELETE FROM aluno WHERE id_aluno = ?";
	private final static String LISTAR = "SELECT a.id_aluno, a.nome_aluno, a.email_aluno, a.senha_aluno, a.sexo_aluno, a.rg_aluno, "
			+ "a.foto_aluno, a.nascimento_aluno, a.id_turma, t.nome_turma, t.id_turma, t.id_escola, e.nome_emp, "
			+ "e.id_escola_cliente FROM aluno AS a, escola_cliente AS e, turma AS t WHERE t.id_turma = a.id_turma AND "
			+ "t.id_escola = e.id_escola_cliente";
	private final static String BUSCAR = "SELECT a.id_aluno, a.nome_aluno, a.email_aluno, a.senha_aluno, a.sexo_aluno, a.rg_aluno, "
			+ "a.foto_aluno, a.nascimento_aluno, a.id_turma, t.nome_turma, t.id_turma, t.id_escola, e.nome_emp, "
			+ "e.id_escola_cliente FROM aluno AS a, escola_cliente AS e, turma AS t WHERE t.id_turma = a.id_turma AND "
			+ "t.id_escola = e.id_escola_cliente AND a.id_aluno = ?";
	private final static String ALTERAR = "UPDATE aluno SET nome_aluno = ?, email_aluno = ?, senha_aluno = ?, rg_aluno = ?, "
			+ "sexo_aluno = ?, nascimento_aluno = ?, id_turma = ? WHERE id_aluno = ?";
	private final static String ALT_FOTO = "UPDATE aluno SET foto_aluno = ? WHERE id_aluno = ?";
	private final static String LOGIN = "SELECT a.id_aluno, a.nome_aluno, a.email_aluno, a.senha_aluno, a.sexo_aluno, a.rg_aluno, "
			+ "a.foto_aluno, a.nascimento_aluno, a.id_turma, t.nome_turma, t.id_turma, t.id_escola, e.nome_emp, "
			+ "e.id_escola_cliente FROM aluno AS a, escola_cliente AS e, turma AS t WHERE a.senha_aluno = ? AND a.email_aluno = ?";
	private final static String ADICIONA_MORTO = "INSERT INTO aluno_morto (email_aluno_morto, id_aluno_antigo, sexo_aluno_morto,"
			+ " id_turma_morto, foto_aluno_morto, nome_aluno_morto, rg_aluno_morto, nascimento_aluno_morto, senha_aluno_morto) "
			+ "SELECT email_aluno, id_aluno, sexo_aluno, id_turma, foto_aluno, nome_aluno, rg_aluno, nascimento_aluno, senha_aluno FROM aluno"
			+ " WHERE id_aluno = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public AlunoDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// METODO DE LOGIN
	public Aluno existeAluno(Aluno aluno) {
		if (aluno == null) {
			throw new IllegalArgumentException(
					"NÃO EXISTE ESSE ALUNO, NÃO FOI POSSIVEL FAZER LOGIN");
		}
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LOGIN);

			stmt.setString(1, aluno.getSenha());
			stmt.setString(2, aluno.getEmail());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				EscolaCliente e = new EscolaCliente();
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setIdEmp(rs.getInt("id_escola_cliente"));

				Turma t = new Turma();
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setIdTurma(rs.getInt("id_turma"));
				t.setEscolaTurma(e);

				aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setEmail(rs.getString("email_aluno"));
				aluno.setSenha(rs.getString("senha_aluno"));
				aluno.setSexoAluno(rs.getString("sexo_aluno"));
				aluno.setRgAluno(rs.getString("rg_aluno"));
				aluno.setNascAluno(rs.getDate("nascimento_aluno"));
				aluno.setFotoAluno(rs.getBytes("foto_aluno"));
				aluno.setTurmaAluno(t);
			} else {
				aluno = null;
			}
			rs.close();
			stmt.close();
			return aluno;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Aluno aluno) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, aluno.getNomeAluno());
			stmt.setString(2, aluno.getEmail());
			stmt.setString(3, aluno.getRgAluno());
			stmt.setString(4, aluno.getSexoAluno());
			stmt.setDate(5, aluno.getNascAluno());
			stmt.setBlob(6, (aluno.getFotoAluno() == null) ? null
					: new ByteArrayInputStream(aluno.getFotoAluno()));
			stmt.setInt(7, aluno.getTurmaAluno().getIdTurma());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(Aluno aluno) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, aluno.getIdAluno());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Aluno aluno) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setString(1, aluno.getNomeAluno());
			stmt.setString(2, aluno.getEmail());
			stmt.setString(3, aluno.getSenha());
			stmt.setString(4, aluno.getRgAluno());
			stmt.setString(5, aluno.getSexoAluno());
			stmt.setDate(6, aluno.getNascAluno());
			stmt.setInt(7, aluno.getTurmaAluno().getIdTurma());
			stmt.setInt(8, aluno.getIdAluno());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<Aluno> listar() {
		List<Aluno> alunos = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente e = new EscolaCliente();
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setIdEmp(rs.getInt("id_escola_cliente"));

				Turma t = new Turma();
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setIdTurma(rs.getInt("id_turma"));
				t.setEscolaTurma(e);

				Aluno a = new Aluno();
				a.setIdAluno(rs.getInt("id_aluno"));
				a.setNomeAluno(rs.getString("nome_aluno"));
				a.setEmail(rs.getString("email_aluno"));
				a.setSenha(rs.getString("senha_aluno"));
				a.setSexoAluno(rs.getString("sexo_aluno"));
				a.setRgAluno(rs.getString("rg_aluno"));
				a.setNascAluno(rs.getDate("nascimento_aluno"));
				a.setFotoAluno(rs.getBytes("foto_aluno"));
				a.setTurmaAluno(t);

				alunos.add(a);
			}
			rs.close();
			stmt.close();
			return alunos;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Aluno buscarID(Integer id) {
		Aluno aluno = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente e = new EscolaCliente();
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setIdEmp(rs.getInt("id_escola_cliente"));

				Turma t = new Turma();
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setIdTurma(rs.getInt("id_turma"));
				t.setEscolaTurma(e);

				aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setEmail(rs.getString("email_aluno"));
				aluno.setSenha(rs.getString("senha_aluno"));
				aluno.setSexoAluno(rs.getString("sexo_aluno"));
				aluno.setRgAluno(rs.getString("rg_aluno"));
				aluno.setNascAluno(rs.getDate("nascimento_aluno"));
				aluno.setFotoAluno(rs.getBytes("foto_aluno"));
				aluno.setTurmaAluno(t);
			}
			stmt.close();
			return aluno;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR FOTO
	public void alterarPhoto(Aluno aluno) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALT_FOTO);

			stmt.setBlob(1, (aluno.getFotoAluno() == null) ? null
					: new ByteArrayInputStream(aluno.getFotoAluno()));
			stmt.setInt(2, aluno.getIdAluno());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	public void adicionaMorto(Integer idAluno) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONA_MORTO);
			stmt.setInt(1, idAluno);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
