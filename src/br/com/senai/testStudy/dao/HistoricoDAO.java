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
import br.com.senai.testStudy.model.Historico;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class HistoricoDAO implements MetodosBasicos<Historico> {
	private final static String ADD = "INSERT INTO historico (id_aluno, id_prova, nota_prova, data_prova_historico, nota_simulado, "
			+ "data_simulado_historico) VALUES (?, ?, ?, ?, ?, ?)";
	private final static String ALTERAR = "UPDATE historico SET id_aluno = ?, id_prova = ?, nota_prova = ?, data_prova_historico = ?, "
			+ "nota_simulado = ?, data_simulado_historico = ? WHERE id_historico = ?";
	private final static String LISTAR = "SELECT h.id_historico, h.nota_prova, h.data_prova_historico, h.nota_simulado, h.data_simulado_historico, "
			+ "h.id_aluno, h.id_prova, p.nome_prova, p.id_prova, a.nome_aluno, a.id_aluno FROM historico AS h, prova AS p, aluno AS a WHERE "
			+ "h.id_prova = p.id_prova AND h.id_aluno = a.id_aluno";
	private final static String BUSCAR = "SELECT h.id_historico, h.nota_prova, h.data_prova_historico, h.nota_simulado, h.data_simulado_historico, "
			+ "h.id_aluno, h.id_prova, p.nome_prova, p.id_prova, a.nome_aluno, a.id_aluno FROM historico AS h, prova AS p, aluno AS a WHERE "
			+ "h.id_prova = p.id_prova AND h.id_aluno = a.id_aluno AND h.id_historico = ?";
	private final static String EXCLUIR = "DELETE FROM historico WHERE id_historico = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public HistoricoDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(Historico historico) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setInt(1, historico.getAluno().getIdAluno());
			stmt.setInt(2, historico.getProva().getIdProva());
			stmt.setDouble(3, historico.getNotaProva());
			stmt.setDate(4, historico.getDataProvaHistorico());
			stmt.setDouble(5, historico.getNotaSimulado());
			stmt.setDate(6, historico.getDataSimuladoHistorico());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// EXCLUIR
	@Override
	public void remover(Historico historico) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, historico.getIdHistorico());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(Historico historico) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setInt(1, historico.getAluno().getIdAluno());
			stmt.setInt(2, historico.getProva().getIdProva());
			stmt.setDouble(3, historico.getNotaProva());
			stmt.setDate(4, historico.getDataProvaHistorico());
			stmt.setDouble(5, historico.getNotaSimulado());
			stmt.setDate(6, historico.getDataSimuladoHistorico());
			stmt.setInt(7, historico.getIdHistorico());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<Historico> listar() {
		List<Historico> historicos = new ArrayList<>();

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Aluno a = new Aluno();
				a.setIdAluno(rs.getInt("id_aluno"));
				a.setNomeAluno(rs.getString("nome_aluno"));

				Prova p = new Prova();
				p.setIdProva(rs.getInt("id_prova"));
				p.setNomeProva(rs.getString("nome_prova"));

				Historico h = new Historico();
				h.setIdHistorico(rs.getInt("id_historico"));
				h.setDataProvaHistorico(rs.getDate("data_prova_historico"));
				h.setDataSimuladoHistorico(rs
						.getDate("data_simulado_historico"));
				h.setNotaProva(rs.getDouble("nota_prova"));
				h.setNotaSimulado(rs.getDouble("nota_simulado"));
				h.setAluno(a);
				h.setProva(p);

				historicos.add(h);
			}
			stmt.close();
			rs.close();
			return historicos;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}

	}

	// BUSCAR
	@Override
	public Historico buscarID(Integer id) {
		Historico h = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Aluno a = new Aluno();
				a.setIdAluno(rs.getInt("id_aluno"));
				a.setNomeAluno(rs.getString("nome_aluno"));

				Prova p = new Prova();
				p.setIdProva(rs.getInt("id_prova"));
				p.setNomeProva(rs.getString("nome_prova"));

				h = new Historico();
				h.setIdHistorico(rs.getInt("id_historico"));
				h.setDataProvaHistorico(rs.getDate("data_prova_historico"));
				h.setDataSimuladoHistorico(rs
						.getDate("data_simulado_historico"));
				h.setNotaProva(rs.getDouble("nota_prova"));
				h.setNotaSimulado(rs.getDouble("nota_simulado"));
				h.setAluno(a);
				h.setProva(p);
			}
			stmt.close();
			return h;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}
}
