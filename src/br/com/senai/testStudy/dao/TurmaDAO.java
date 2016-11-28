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

import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class TurmaDAO implements MetodosBasicos<Turma> {
	private final static String ADD = "INSERT INTO turma (nome_turma, id_escola) VALUES (?, ?)";
	private final static String ALTERAR = "UPDATE turma SET nome_turma = ?, id_escola = ? WHERE id_turma = ?";
	private final static String EXCLUIR = "DELETE FROM turma WHERE id_turma = ?";
	private final static String LISTAR = "SELECT t.id_turma, t.id_escola, t.nome_turma, e.id_escola_cliente, "
			+ "e.nome_emp FROM turma AS t, escola_cliente AS e WHERE t.id_escola = e.id_escola_cliente";
	private final static String BUSCAR = "SELECT t.id_turma, t.id_escola, t.nome_turma, e.id_escola_cliente, "
			+ "e.nome_emp FROM turma AS t, escola_cliente AS e WHERE t.id_escola = e.id_escola_cliente AND "
			+ "t.id_turma = ?";
	private final static String ADD_MORTO = "INSERT INTO turma_morto (id_turma_antigo, id_escola_morto, nome_turma_morto)"
			+ " SELECT id_turma, id_escola, nome_turma FROM turma WHERE id_turma = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public TurmaDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Turma turma) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, turma.getNomeTurma());
			stmt.setInt(2, turma.getEscolaTurma().getIdEmp());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
	
	public void adicionarMorto(Integer idTurma){
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD_MORTO);
			stmt.setInt(1, idTurma);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// REMOVER
	@Override
	public void remover(Turma turma) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, turma.getIdTurma());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Turma turma) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setString(1, turma.getNomeTurma());
			stmt.setInt(2, turma.getEscolaTurma().getIdEmp());
			stmt.setInt(3, turma.getIdTurma());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<Turma> listar() {
		List<Turma> turmas = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente e = new EscolaCliente();
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setIdEmp(rs.getInt("id_escola_cliente"));

				Turma t = new Turma();
				t.setIdTurma(rs.getInt("id_turma"));
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setEscolaTurma(e);

				turmas.add(t);
			}
			rs.close();
			stmt.close();
			return turmas;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public Turma buscarID(Integer id) {
		Turma t = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente e = new EscolaCliente();
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setIdEmp(rs.getInt("id_escola_cliente"));

				t = new Turma();
				t.setIdTurma(rs.getInt("id_turma"));
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setEscolaTurma(e);
			}
			stmt.close();
			return t;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
}
