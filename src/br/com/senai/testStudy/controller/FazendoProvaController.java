package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.FazendoProvaDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.ProvaAgendada;

@Controller
public class FazendoProvaController {
	private final FazendoProvaDAO DAO;
	
	@Autowired
	public FazendoProvaController(FazendoProvaDAO DAO){
		this.DAO = DAO;
	}
	
	@RequestMapping("salvaRespostas")
	public String salvaRespostas(Alternativa alt, Integer i, Model modelo, ProvaAgendada provaAgendada, HttpSession session){
		provaAgendada = (ProvaAgendada) session.getAttribute("provaParaFazer");	
		Integer count = (Integer) session.getAttribute("count");
		count = count + 1;
		session.setAttribute("questao", provaAgendada.getProva().getQuestoes().get(count));		
		session.setAttribute("alternativas", provaAgendada.getProva().getQuestoes().get(count).getAlternativas());
		session.setAttribute("nQuestoes", provaAgendada.getProva().getnQuestoes());
		session.setAttribute("count", count);
		return "resolucaoDeProva"; 
	}
}
