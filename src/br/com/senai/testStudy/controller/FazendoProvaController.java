package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.FazendoProvaDAO;
import br.com.senai.testStudy.model.Alternativa;

@Controller
public class FazendoProvaController {
	private final FazendoProvaDAO DAO;
	
	@Autowired
	public FazendoProvaController(FazendoProvaDAO DAO){
		this.DAO = DAO;
	}
	
	@RequestMapping("salvaRespostas")
	public String salvaRespostas(Alternativa alt, Integer i, Model modelo){
		System.out.println(i);
		// TODO ESTOU AQUI, TENTANDO ARRUMAR O CONTADOR...
		return "resolucaoDeProva";
	}
}
