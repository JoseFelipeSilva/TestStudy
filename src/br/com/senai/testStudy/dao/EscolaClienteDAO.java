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
import br.com.senai.testStudy.util.MetodosBasicos;

@Repository
public class EscolaClienteDAO implements MetodosBasicos<EscolaCliente> {
	private final static String LISTAR = "SELECT * FROM escola_cliente";
	private final static String ADD = "INSERT INTO escola_cliente (nome_emp, email_emp, telefone_emp, cnpj_emp, razao_social_emp)"
			+ "VALUES (?, ?, ?, ?, ?)";
	private final static String EXLUIR = "DELETE FROM escola_cliente WHERE id_escola_cliente = ?";
	private final static String BUSCAR = "SELECT * FROM escola_cliente WHERE id_escola_cliente = ?";
	private final static String ALTERAR = " UPDATE escola_cliente SET nome_emp = ?, email_emp = ?, telefone_emp = ?, cnpj_emp = ?, razao_social_emp = ?"
			+ "WHERE id_escola_cliente = ?";
	private final static String ADD_MORTO = "INSERT INTO escola_cliente_morto (cnpj_emp_morto, id_escola_antigo, razao_social_morto,"
			+ " telefone_emp_morto, email_emp_morto, nome_emp_morto) SELECT cnpj_emp, id_escola_cliente, razao_social_emp, telefone_emp,"
			+ " email_emp, nome_emp FROM escola_cliente WHERE id_escola_cliente = ?";

	// CONEXAO
	private final Connection CONEXAO;

	@Autowired
	public EscolaClienteDAO(DataSource dtSource) {
		try {
			this.CONEXAO = dtSource.getConnection();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}
	
	public void adicionaMorto(Integer idEscola){
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD_MORTO);
			stmt.setInt(1, idEscola);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ADICIONAR
	@Override
	public void adicionar(EscolaCliente escola) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADD);

			stmt.setString(1, escola.getNomeEmp());
			stmt.setString(2, escola.getEmailEmp());
			stmt.setString(3, escola.getTelefoneEmp());
			stmt.setString(4, escola.getCnpjEmp());
			stmt.setString(5, escola.getNomeEmpresarialEmp());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// EXCLUIR
	@Override
	public void remover(EscolaCliente escola) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(EXLUIR);

			stmt.setInt(1, escola.getIdEmp());

			stmt.execute();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// ALTERAR
	@Override
	public void alterar(EscolaCliente escola) {
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ALTERAR);

			stmt.setString(1, escola.getNomeEmp());
			stmt.setString(2, escola.getEmailEmp());
			stmt.setString(3, escola.getTelefoneEmp());
			stmt.setString(4, escola.getCnpjEmp());
			stmt.setString(5, escola.getNomeEmpresarialEmp());
			stmt.setInt(6, escola.getIdEmp());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// LISTAR TUDO
	@Override
	public List<EscolaCliente> listar() {
		List<EscolaCliente> listEscola = new ArrayList<>();

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(LISTAR);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				EscolaCliente escola = new EscolaCliente();
				escola.setIdEmp(rs.getInt("id_escola_cliente"));
				escola.setNomeEmp(rs.getString("nome_emp"));
				escola.setEmailEmp(rs.getString("email_emp"));
				escola.setCnpjEmp(rs.getString("cnpj_emp"));
				escola.setTelefoneEmp(rs.getString("telefone_emp"));
				escola.setNomeEmpresarialEmp(rs.getString("razao_social_emp"));

				listEscola.add(escola);
			}
			rs.close();
			stmt.close();
			return listEscola;
		} catch (SQLException erro) {
			throw new RuntimeException(erro);
		}
	}

	// BUSCA POR ID
	@Override
	public EscolaCliente buscarID(Integer id) {
		EscolaCliente e = null;

		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(BUSCAR);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				e = new EscolaCliente();
				e.setIdEmp(rs.getInt("id_escola_cliente"));
				e.setNomeEmp(rs.getString("nome_emp"));
				e.setEmailEmp(rs.getString("email_emp"));
				e.setCnpjEmp(rs.getString("cnpj_emp"));
				e.setTelefoneEmp(rs.getString("telefone_emp"));
				e.setNomeEmpresarialEmp(rs.getString("razao_social_emp"));
			}
			stmt.close();
			return e;
		} catch (Exception erro) {
			throw new RuntimeException(erro);
		}
	}
}
