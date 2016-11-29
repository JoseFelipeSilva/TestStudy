package br.com.senai.testStudy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.testStudy.model.Log;

@Repository
public class LogDAO {
	private final Connection CONEXAO;
	
	private static final String ADICIONAR = "INSERT INTO `log`(nome_usuario, tipo_usuario, email_usuario, acao_usuario, data_acao) VALUES (?, ?, ?, ?, now())";
	
	@Autowired
	public LogDAO(DataSource dataSource){
		try {
			CONEXAO = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void adicionareAcao(Log log){
		try {
			PreparedStatement stmt = CONEXAO.prepareStatement(ADICIONAR);
			stmt.setString(1, log.getNomeUsuario());
			stmt.setString(2, log.getTipoUsuario());
			stmt.setString(3, log.getEmailUsuario());
			stmt.setString(4, log.getAcaoUsuario());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
