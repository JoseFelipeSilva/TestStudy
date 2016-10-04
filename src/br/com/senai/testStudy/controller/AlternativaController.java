package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.AlternativaDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.QuestaoProva;

@Controller
public class AlternativaController {
	
	AlternativaDAO DAO;
	
	@Autowired
	public AlternativaController(AlternativaDAO DAO) {
		this.DAO = DAO;
	}
	
	@RequestMapping("cadastrarAlternativa")
	public String cadastrarAlter( Alternativa a, QuestaoProva qp){
		DAO.adicionar(a);
		return "sucesso";
	}
}
