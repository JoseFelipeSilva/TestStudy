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

import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class QuestaoProvaDAO implements MetodosBasicos<QuestaoProva> {
	Connection CONEXAO;
	
	private static final String ADICIONAR = "INSERT INTO questao_prova( visualizacao_questao,"
			+ " corpo_questao, titulo_questao, tipo_questao, disciplina_questao, dificuldade, materia_questao) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String LISTAR = "SELECT * FROM questao_prova";
	private static final String BUSCAR = "SELECT * FROM questao_prova WHERE id_questao = ?";
	private static final String ALTERAR = "UPDATE questao_prova SET corpo_questao=?, titulo_questao=?"
			+ ", tipo_questao=? WHERE id_questao=?";
	private static final String LISTAR_DISC = "SELECT * FROM disciplina";
	private static final String LISTAR_MAT = "SELECT materia.*, disciplina.* FROM materia,"
			+ " disciplina WHERE materia.id_materia = disciplina.id_disciplina AND materia.id_disciplina = ?";
	
	@Autowired
	public QuestaoProvaDAO(DataSource dataSource) {
		try {
			this.CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	@Override
	public void adicionar(QuestaoProva qp) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, "disp");
			stmt.setString(2, qp.getCorpoQuestao());
			stmt.setString(3, qp.getTituloQuestao());
			stmt.setString(4, qp.getTipoQuestao());
			stmt.setInt(5, qp.getMateria().getDisciplina().getIdDisciplina());
			stmt.setString(6, qp.getDificuldade());
			stmt.setInt(7, qp.getMateria().getIdMateria());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public List<Disciplina> listarDisc() {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_DISC);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
				disciplinas.add(d);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return disciplinas;
	}
	
	public List<Materia> listarMat(Integer id){
		List<Materia> materias = new ArrayList<Materia>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR_MAT);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Disciplina d = new Disciplina();
				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNomeDisciplina(rs.getString("nome_disciplina"));
				
				Materia m = new Materia();
				m.setDisciplina(d);
				m.setIdMateria(rs.getInt("id_materia"));
				m.setNomeMateria(rs.getString("nome_materia"));
				materias.add(m);
				
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return materias;
	}
	@Override
	public void remover(QuestaoProva object) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void alterar(QuestaoProva qp) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);
			stmt.setString(1, qp.getCorpoQuestao());
			stmt.setString(2, qp.getTituloQuestao());
			stmt.setString(3, qp.getTipoQuestao());
			stmt.setInt(4, qp.getIdQuestaoProva());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	@Override
	public List<QuestaoProva> listar() {
		List<QuestaoProva> questões = new ArrayList<QuestaoProva>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				QuestaoProva qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));
				questões.add(qp);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return questões;
	}
	@Override
	public QuestaoProva buscarID(Integer id) {
		QuestaoProva qp = null;
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				qp = new QuestaoProva();
				qp.setIdQuestaoProva(rs.getInt("id_questao"));
				qp.setTituloQuestao(rs.getString("titulo_questao"));
				qp.setCorpoQuestao(rs.getString("corpo_questao"));
				qp.setTipoQuestao(rs.getString("tipo_questao"));
				qp.setVisualizacaoQuestao(rs.getString("visualizacao_questao"));
				qp.setUsoQuestao(rs.getInt("uso_questao"));				
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return qp;
	}
	
	
}
