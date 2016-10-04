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

import br.com.senai.testStudy.model.RealizacaoSimulado;
import br.com.senai.testStudy.model.Simulado;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class RealizacaoSimuladoDAO implements
		MetodosBasicos<RealizacaoSimulado> {
	private final static String ADD = "INSERT INTO realizacao_simulado (id_simulado, hora_termino, data_termino, hora_inicio, data_inicio) "
			+ "VALUES (?, ?, ?, TIME(now()), DATE(now()))";
	private final static String LISTAR = "SELECT r.id_realizacao_simulado, r.hora_termino, DATE_FORMAT(r.data_termino,'%d/%m/%Y'), r.hora_inicio, "
			+ "r.data_inicio, r.id_simulado, s.id_simulado FROM realizacao_simulado AS r, simulado AS s "
			+ "WHERE r.id_simulado = s.id_simulado";
	private final static String BUSCAR = "SELECT r.id_realizacao_simulado, r.hora_termino, DATE_FORMAT(r.data_termino,'%d/%m/%Y'), r.hora_inicio, "
			+ "r.data_inicio, r.id_simulado, s.id_simulado FROM realizacao_simulado AS r, simulado AS s "
			+ "WHERE r.id_simulado = s.id_simulado AND r.id_simulado = ?";
	private final static String ALTERAR = "UPDATE realizacao_simulado SET ";
	private final static String EXCLUIR = "DELETE FROM realizacao_simulado WHERE id_realizacao_simulado = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public RealizacaoSimuladoDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(RealizacaoSimulado realiza) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setInt(1, realiza.getIdSimulado().getIdSimulado());
			stmt.setTime(2, realiza.getHoraTermino());
			stmt.setDate(3, realiza.getDataTermino());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(RealizacaoSimulado realiza) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, realiza.getIdRealizacaoSimulado());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(RealizacaoSimulado realiza) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setInt(1, realiza.getIdSimulado().getIdSimulado());
			stmt.setTime(2, realiza.getHoraTermino());
			stmt.setDate(3, realiza.getDataTermino());
			stmt.setInt(4, realiza.getIdRealizacaoSimulado());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<RealizacaoSimulado> listar() {
		List<RealizacaoSimulado> realizacao = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Simulado s = new Simulado();
				s.setIdSimulado(rs.getInt("id_simulado"));

				RealizacaoSimulado realiza = new RealizacaoSimulado();
				realiza.setDataInicio(rs.getDate("data_inicio"));
				realiza.setDataTermino(rs.getDate("data_termino"));
				realiza.setHoraInicio(rs.getTime("hora_inicio"));
				realiza.setHoraTermino(rs.getTime("horario_termino"));
				realiza.setIdRealizacaoSimulado(rs
						.getInt("id_realizacao_simulado"));
				realiza.setIdSimulado(s);

				realizacao.add(realiza);
			}
			rs.close();
			stmt.close();
			return realizacao;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCA POR ID
	@Override
	public RealizacaoSimulado buscarID(Integer id) {
		RealizacaoSimulado realiza = new RealizacaoSimulado();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Simulado s = new Simulado();
				s.setIdSimulado(rs.getInt("id_simulado"));

				realiza = new RealizacaoSimulado();
				realiza.setDataInicio(rs.getDate("data_inicio"));
				realiza.setDataTermino(rs.getDate("data_termino"));
				realiza.setHoraInicio(rs.getTime("hora_inicio"));
				realiza.setHoraTermino(rs.getTime("horario_termino"));
				realiza.setIdRealizacaoSimulado(rs
						.getInt("id_realizacao_simulado"));
				realiza.setIdSimulado(s);
			}
			stmt.close();
			return realiza;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}
}
