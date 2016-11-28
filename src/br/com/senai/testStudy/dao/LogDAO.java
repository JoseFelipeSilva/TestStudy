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
	
	private static final String ADICIONAR = "";
	
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
