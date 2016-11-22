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

import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class ProvaAgendadaDAO implements MetodosBasicos<ProvaAgendada> {
	private final static String ADD = "INSERT INTO prova_agendada (id_prova, id_turma, data_termino, data_inicio, data_realizacao, hora_termino, horario_inicio) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final static String EXCLUIR = "DELETE FROM prova_agendada WHERE id_prova_agendada = ?";
	private final static String LISTAR = "SELECT pr.id_prova_agendada, pr.id_prova, pr.id_turma, pr.hora_termino, pr.horario_inicio, pr.data_realizacao, "
			+ "pr.data_termino, pr.data_inicio, p.id_prova, p.nome_prova, t.id_turma, t.nome_turma FROM prova_agendada AS pr, prova AS p, turma AS t "
			+ "WHERE pr.id_prova = p.id_prova AND pr.id_turma = t.id_turma";
	private final static String BUSCAR = "SELECT pr.id_prova_agendada, pr.id_prova, pr.id_turma, pr.hora_termino, pr.horario_inicio, pr.data_realizacao, "
			+ "pr.data_termino, pr.data_inicio, p.id_prova, p.nome_prova, t.id_turma, t.nome_turma FROM prova_agendada AS pr, prova AS p, turma AS t "
			+ "WHERE pr.id_prova = p.id_prova AND pr.id_turma = t.id_turma AND pr.id_prova_agendada = ?";
	private final static String ALTERAR = "UPDATE prova_agendada SET id_prova = ?, id_turma = ?. data_termino = ?, hora_termino = ?, data_inicio = ? "
			+ "WHERE id_prova_agendada = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public ProvaAgendadaDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(ProvaAgendada prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setInt(1, prova.getProva().getIdProva());
			stmt.setInt(2, prova.getTurma().getIdTurma());
			stmt.setDate(3, prova.getDataTermino());
			stmt.setDate(4, prova.getDataInicio());
			stmt.setDate(5, prova.getDataRealizacao());
			stmt.setTime(6, prova.getHoraTermino());
			stmt.setTime(7, prova.getHoraInicio());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// REMOVER
	@Override
	public void remover(ProvaAgendada prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXCLUIR);

			stmt.setInt(1, prova.getIdProvaAgendada());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(ProvaAgendada prova) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setInt(1, prova.getProva().getIdProva());
			stmt.setInt(2, prova.getTurma().getIdTurma());
			stmt.setDate(3, prova.getDataTermino());
			stmt.setTime(4, prova.getHoraTermino());
			stmt.setDate(5, prova.getDataInicio());
			stmt.setInt(6, prova.getIdProvaAgendada());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TODOS
	@Override
	public List<ProvaAgendada> listar() {
		List<ProvaAgendada> provas = new ArrayList<>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Prova p = new Prova();
				p.setNomeProva(rs.getString("nome_prova"));
				p.setIdProva(rs.getInt("id_prova"));

				Turma t = new Turma();
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setIdTurma(rs.getInt("id_turma"));

				ProvaAgendada pa = new ProvaAgendada();
				pa.setIdProvaAgendada(rs.getInt("id_prova_agendada"));
				pa.setDataInicio(rs.getDate("data_inicio"));
				pa.setHoraTermino(rs.getTime("hora_termino"));
				pa.setHoraInicio(rs.getTime("horario_inicio"));
				pa.setDataRealizacao(rs.getDate("data_realizacao"));
				pa.setDataTermino(rs.getDate("data_termino"));
				pa.setTurma(t);
				pa.setProva(p);

				provas.add(pa);
			}
			rs.close();
			stmt.close();
			return provas;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCAR POR ID
	@Override
	public ProvaAgendada buscarID(Integer id) {
		ProvaAgendada pa = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Prova p = new Prova();
				p.setNomeProva(rs.getString("nome_prova"));
				p.setIdProva(rs.getInt("id_prova"));

				Turma t = new Turma();
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setIdTurma(rs.getInt("id_turma"));

				pa = new ProvaAgendada();
				pa.setIdProvaAgendada(rs.getInt("id_prova_agendada"));
				pa.setDataInicio(rs.getDate("data_inicio"));
				pa.setHoraTermino(rs.getTime("hora_termino"));
				pa.setHoraInicio(rs.getTime("horario_inicio"));
				pa.setDataRealizacao(rs.getDate("data_realizacao"));
				pa.setDataTermino(rs.getDate("data_termino"));
				pa.setTurma(t);
				pa.setProva(p);
			}
			stmt.close();
			return pa;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
}
