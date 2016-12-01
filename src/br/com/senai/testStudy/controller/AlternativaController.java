package br.com.senai.testStudy.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.AlternativaDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.Util;

@Controller
public class AlternativaController {

	AlternativaDAO DAO;
	private final LogDAO ldao;

	@Autowired
	public AlternativaController(AlternativaDAO DAO, LogDAO ldao) {
		this.DAO = DAO;
		this.ldao = ldao;
	}

	@RequestMapping("cadastrarAlternativa")
	public String cadastrarAlter(Alternativa a, QuestaoProva qp, HttpSession session) {
		String[] lista;
		lista = a.getCorpoAlternativa().split("59604679#");
		for (int i = 0; i < lista.length; i++) {
			if (i == 0) {
				a.setCerta("C");
				a.setCorpoAlternativa(lista[i]);
				DAO.adicionar(a);
			}
			if (i != 0) {
				a.setCerta("E");
				a.setCorpoAlternativa(lista[i]);
				DAO.adicionar(a);
			}
		}
		Util.addLog(session, ldao, this);
		return "sucesso";
	}
	// método responsável por buscar as alternativas para serem mostradas na página de cadastro de prova (addProvaPasso2)
	public void mostraAlternativa(List<QuestaoProva> qp){
		for (int i = 0; i < qp.size(); i++) {
			System.out.println(qp.get(i).getIdQuestaoProva());
			// se tem como mandar do controller da questão prova direto pro dao da alternativa... 
			//DAO.listarPorQuestao(qp.get(i).getIdQuestaoProva());
		}
	}
}
