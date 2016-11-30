package br.com.senai.testStudy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import br.com.senai.testStudy.dao.AlternativaDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.Util;

@Controller
public class ProvaAgendadaController {
	private final ProvaAgendadaDAO dao;
	private final ProvaDAO pdao;
	private final TurmaDAO tdao;
	private final QuestaoProvaDAO qpdao;
	private final LogDAO ldao;
	private final AlternativaDAO idao;

	@Autowired
	public ProvaAgendadaController(QuestaoProvaDAO qpdao, TurmaDAO tdao,
			ProvaAgendadaDAO dao, ProvaDAO pdao, LogDAO ldao,
			AlternativaDAO idao) {
		this.dao = dao;
		this.pdao = pdao;
		this.tdao = tdao;
		this.qpdao = qpdao;
		this.ldao = ldao;
		this.idao = idao;
	}

	@RequestMapping("newProvaAgendada")
	public String addPageProvaAgendada(Model model, HttpSession session,
			Professor p) {
		p = (Professor) session.getAttribute("profLogon");
		model.addAttribute("LProvas", pdao.listar(p.getIdProfessor()));
		model.addAttribute("LTurmas", tdao.listar());
		return "addProvaAgendada";
	}

	@RequestMapping("fazerProvaAgendada")
	public String fazerProva(Model model, HttpSession session,
			ProvaAgendada provaAgendada) {
		List<ProvaAgendada> notificacao = (List<ProvaAgendada>) session
				.getAttribute("notificacoes");
		List<QuestaoProva>questoes = new ArrayList<QuestaoProva>();
		for (ProvaAgendada provaAgendada2 : notificacao) {			
				if (provaAgendada2.getIdProvaAgendada() == provaAgendada.getIdProvaAgendada()) {
	  				provaAgendada = dao.buscarID(provaAgendada.getIdProvaAgendada());
	 				session.setAttribute("provaParaFazer", provaAgendada);
	 				questoes = qpdao.listarQuestoesDaProva(provaAgendada.getProva().getIdProva());
	 				model.addAttribute("QuestoesDaProvaParaFazer", questoes);
	 				provaAgendada.getProva().setQuestoes(questoes);
	 				for (int i = 0; i < questoes.size(); i++) {
	 						provaAgendada.getProva().getQuestoes().get(i).setAlternativas(idao.listarPorQuestao(questoes.get(i).getIdQuestaoProva()));
	 				}
	 				
	  			} 
				session.setAttribute("provaParaFazer", provaAgendada);
			

		}
		Util.addLog(session, ldao, this);
		return "resumoDaProva";
	}

	@RequestMapping("adicionarProvaAgendada")
	public String adicionarProvaAgendada(Turma t, Prova p, ProvaAgendada pa,
			HttpSession session) {
		pa.setProva(p);
		pa.setTurma(t);
		dao.adicionar(pa);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}
	
	@RequestMapping("resolverProva")
	public String resolverProva(Model modelo, HttpSession session, ProvaAgendada provaAgendada){
		provaAgendada = (ProvaAgendada) session.getAttribute("provaparaFazer");		
		
		modelo.addAttribute("questao", provaAgendada.getProva().getQuestoes().get(0));
		if (provaAgendada.getProva().getQuestoes().get(0).getAlternativas() == null) {
			// TODO volta pra cá
		}
		
		
		return "";
	}
}






































/*provaAgendada = idao.buscarProva(provaAgendada.getIdProvaAgendada());
List<QuestaoProva>questoesNaoRepetidas = new ArrayList<>();
questoesNaoRepetidas.add(provaAgendada.getProva().getQuestoes().get(0));
Integer count = 0;
for (int i = 0; i < provaAgendada.getProva().getQuestoes().size(); i++) {
	if (questoesNaoRepetidas.size() > 0) {
		for (int j = 0; j < questoesNaoRepetidas.size(); j++) {
			if (provaAgendada.getProva().getQuestoes().get(i).equals(questoesNaoRepetidas.get(j))) {
				count += 1;
			}
			if (j == (questoesNaoRepetidas.size() - 1)) {
				if (count == 0) {
					questoesNaoRepetidas.add(provaAgendada.getProva().getQuestoes().get(i));
				}else{
					count = 0;
				}
			}
		}
	} else{
		questoesNaoRepetidas.add(provaAgendada.getProva().getQuestoes().get(i));
	}
}

for (int i = 0; i < provaAgendada.getProva().getQuestoes().size(); i++) {
	for (int j = 0; j < provaAgendada.getProva().getQuestoes().size(); j++) {
		if (!provaAgendada.getProva().getQuestoes().get(i).getIdQuestaoProva().equals(questoesNaoRepetidas.get(j).getIdQuestaoProva())) {
			questoesNaoRepetidas.add(provaAgendada.getProva().getQuestoes().get(j));
		}
		
	}
}


provaAgendada.getProva().getQuestoes().clear();
for (int i = 0; i < questoesNaoRepetidas.size(); i++) {
	provaAgendada.getProva().getQuestoes().add(questoesNaoRepetidas.get(i));
	System.out.println(questoesNaoRepetidas.get(i).getIdQuestaoProva());
}
*/
