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
	private final static String BUSCAR_POR_ALUNO = "SELECT * FROM historico, aluno, prova, turma, escola_cliente, professor"
			+ " WHERE aluno.id_aluno = historico.id_aluno AND professor.id_professor = prova.id_professor AND historico.id_prova"
			+ " = prova.id_prova AND escola_cliente.id_escola_cliente = turma.id_escola AND turma.id_turma = aluno.id_turma AND aluno.id_aluno = ?";

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
	
	public List<Historico> buscaPorAluno(Historico h){
		List<Historico> historicos = new ArrayList<Historico>();
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR_POR_ALUNO);
			stmt.setInt(1, h.getAluno().getIdAluno());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Prova p = new Prova();
				Aluno a = new Aluno();
				Turma t = new Turma();
				Professor prof = new Professor();
				EscolaCliente e = new EscolaCliente();
				h = new Historico();
				
				e.setIdEmp(rs.getInt("id_escola_cliente"));
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setEmailEmp(rs.getString("email_emp"));
				e.setCnpjEmp(rs.getString("cnpj_emp"));
				e.setTelefoneEmp(rs.getString("telefone_emp"));
				e.setNomeEmpresarialEmp(rs.getString("razao_social_emp"));
				
				t.setIdTurma(rs.getInt("id_turma"));
				t.setNomeTurma(rs.getString("nome_turma"));
				t.setEscolaTurma(e);
				
				a.setIdAluno(rs.getInt("id_aluno"));
				a.setNomeAluno(rs.getString("nome_aluno"));
				a.setEmail(rs.getString("email_aluno"));
				a.setSenha(rs.getString("senha_aluno"));
				a.setSexoAluno(rs.getString("sexo_aluno"));
				a.setRgAluno(rs.getString("rg_aluno"));
				a.setNascAluno(rs.getDate("nascimento_aluno"));
				a.setFotoAluno(rs.getBytes("foto_aluno"));
				a.setTurmaAluno(t);
				
				prof.setIdProfessor(rs.getInt("id_professor"));
				prof.setNome(rs.getString("nome_professor"));
				prof.setEmail(rs.getString("email_professor"));
				prof.setSenha(rs.getString("senha_professor"));
				prof.setCpf(rs.getString("cpf_professor"));
				prof.setRg(rs.getString("rg_professor"));
				prof.setSexo(rs.getString("sexo_professor"));
				prof.setFoto(rs.getBytes("foto_professor"));
				prof.setNascimento(rs.getDate("nascimento_professor"));
				prof.setEscolaProfessor(e);
				
				p.setDificuldadeDE(rs.getInt("dificuldade"));
				p.setNomeProva(rs.getString("nome_prova"));
				p.setnQuestoes(rs.getInt("n_questoes"));
				p.setIdProva(rs.getInt("id_prova"));
				p.setCriacaoProva(rs.getDate("data_prova"));
				p.setProfessor(prof);
				
				h.setAluno(a);
				h.setNotaProva(rs.getDouble("nota_prova"));
				h.setProva(p);
				h.setNotaSimulado(rs.getDouble("nota_simulado"));
				h.setIdHistorico(rs.getInt("id_historico"));
				h.setDataProvaHistorico(rs.getTimestamp("data_simulado_historico").toLocalDateTime());
				h.setDataSimuladoHistorico(rs.getTimestamp("data_simulado_historico").toLocalDateTime());
				
				historicos.add(h);			
				
				
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return historicos;
	}
}