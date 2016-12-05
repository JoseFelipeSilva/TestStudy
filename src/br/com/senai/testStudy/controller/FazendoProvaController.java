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
	public String salvaRespostas(Alternativa alt, Integer i, Model modelo, ProvaAgendada provaAgendada, HttpSession session, String cronometro, String escolhaBotao){
		provaAgendada = (ProvaAgendada) session.getAttribute("provaParaFazer");	
		String[] cronometroSeparado = cronometro.split(":");
		Integer min = Integer.valueOf(cronometroSeparado[0]);
		Integer seg = Integer.valueOf(cronometroSeparado[1]);
		if(min > 0){
			Integer count = (Integer) session.getAttribute("count");
			// verifica se o botão pressionado foi voltar ou avançar, pois em um caso add +1 ao contador, no outro subtrai
			if (escolhaBotao.equals("voltar")) {
				if (count == 0) {
					
				}else{
					count = count - 1;
				}
				
			}else if(escolhaBotao.equals("proxima")){
				count = count + 1;
			}while (provaAgendada.getProva().getQuestoes().size() > count) {
				session.setAttribute("questao", provaAgendada.getProva().getQuestoes().get(count));		
				session.setAttribute("alternativas", provaAgendada.getProva().getQuestoes().get(count).getAlternativas());
				session.setAttribute("nQuestoes", provaAgendada.getProva().getnQuestoes());
				session.setAttribute("count", count);
				session.setAttribute("duracao", (min * 60) + seg);
				return "resolucaoDeProva"; 
			}
			modelo.addAttribute("mensagemErro", "Você finalizou a prova");
			return "erroAoFazerProva";
			
		}else{
			modelo.addAttribute("mensagemErro", "Tempo de prova esgotado");
			return "erroAoFazerProva";
		}
		
	}
}
