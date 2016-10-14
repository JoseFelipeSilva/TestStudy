package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.senai.testStudy.dao.LogDAO;

@Controller
public class LogController {
	private final LogDAO DAO;
	
	@Autowired
	public LogController(LogDAO DAO) {
		this.DAO = DAO;
	}
	
	
}
