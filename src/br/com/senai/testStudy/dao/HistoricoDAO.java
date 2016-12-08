package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Historico;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class HistoricoDAO implements MetodosBasicos<Historico> {
	private final static String ADD = "INSERT INTO historico (id_aluno, id_prova, nota_prova, data_prova_historico, nota_simulado, "
			+ "data_simulado_historico) VALUES (?, ?, ?, now(), ?, now())";
	private final static String ALTERAR = "UPDATE historico SET id_aluno = ?, id_prova = ?, nota_prova = ?, data_prova_historico = ?, "
			+ "nota_simulado = ?, data_simulado_historico = ? WHERE id_historico = ?";
	private final static String LISTAR = "SELECT h.id_historico, h.nota_prova, h.data_prova_historico, h.nota_simulado, h.data_simulado_historico, "
			+ "h.id_aluno, h.id_prova, p.nome_prova, p.id_prova, a.nome_aluno, a.id_aluno FROM historico AS h, prova AS p, aluno AS a WHERE "
			+ "h.id_prova = p.id_prova AND h.id_aluno = a.id_aluno";
	private final static String BUSCAR = "SELECT h.id_historico, h.nota_prova, h.data_prova_historico, h.nota_simulado, h.data_simulado_historico, "
			+ "h.id_aluno, h.id_prova, p.nome_prova, p.id_prova, a.nome_aluno, a.id_aluno FROM historico AS h, prova AS p, aluno AS a WHERE "
			+ "h.id_prova = p.id_prova AND h.id_aluno = a.id_aluno AND h.id_historico = ?";
	private final static String EXCLUIR = "DELETE FROM historico WHERE id_historico = ?";
	private final static String BUSCA_MAIOR_50 = "select count((nota_prova)) from historico where nota_prova > 50";
	private final static String BUSCA_MENOR_50 = "select count((nota_prova)) from historico where nota_prova < 50";
	private final static String BUSCA_ESTE_ANO = "select count((nota_prova)) from historico where (year(data_prova_historico)=year(now()))";
	private final static String BUSCA_TOTAIS = "select count((nota_prova)) from historico";

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
			stmt.setDouble(4, historico.getNotaSimulado());

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
			Timestamp dT = Timestamp.valueOf(historico.getDataProvaHistorico());
			Timestamp dI = Timestamp.valueOf(historico.getDataSimuladoHistorico());
			stmt.setInt(1, historico.getAluno().getIdAluno());
			stmt.setInt(2, historico.getProva().getIdProva());
			stmt.setDouble(3, historico.getNotaProva());
			stmt.setTimestamp(4, dT);
			stmt.setDouble(5, historico.getNotaSimulado());
			stmt.setTimestamp(6, dI);
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
				h.setDataProvaHistorico(rs.getTimestamp("data_prova_historico").toLocalDateTime());
				h.setDataSimuladoHistorico(rs
						.getTimestamp("data_simulado_historico").toLocalDateTime());
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
				h.setDataProvaHistorico(rs.getTimestamp("data_prova_historico").toLocalDateTime());
				h.setDataSimuladoHistorico(rs
						.getTimestamp("data_simulado_historico").toLocalDateTime());
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
	
	public Integer buscarMaior(){
		Integer notaMaior50 = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCA_MAIOR_50);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				notaMaior50 = rs.getInt("count((nota_prova))");
			}
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return notaMaior50;
	}
	
	public Integer buscarMenor(){
		Integer notaMenor50 = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCA_MENOR_50);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				notaMenor50 = rs.getInt("count((nota_prova))");
			}
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return notaMenor50;
	}
	
	public Integer buscaProvasEsteAno(){
		Integer provasEsteAno = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCA_TOTAIS);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				provasEsteAno = rs.getInt("count((nota_prova))");
			}
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return provasEsteAno;
	}
	
	public Integer buscarProvasTotais(){
		Integer provasTotais = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCA_ESTE_ANO);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				provasTotais = rs.getInt("count((nota_prova))");
			}
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return provasTotais;
	}
}