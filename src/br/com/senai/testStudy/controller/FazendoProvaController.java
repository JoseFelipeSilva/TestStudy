package br.com.senai.testStudy.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.AlternativaDAO;
import br.com.senai.testStudy.dao.FazendoProvaDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.FazendoProva;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.util.Util;

@Controller
public class FazendoProvaController {
	private final FazendoProvaDAO DAO;
	private final LogDAO LDAO;
	private final AlternativaDAO ADAO;
	List<String> certaOuErrada = new ArrayList<String>();
	@Autowired
	public FazendoProvaController(FazendoProvaDAO DAO, LogDAO LDAO,
			AlternativaDAO ADAO) {
		this.DAO = DAO;
		this.LDAO = LDAO;
		this.ADAO = ADAO;
	}

	@RequestMapping("salvaRespostas")
	public String salvaRespostas(Alternativa alt, Integer i, Model modelo,
			ProvaAgendada provaAgendada, HttpSession session,
			String cronometro, String escolhaBotao, Integer altSelecionada) {
		provaAgendada = (ProvaAgendada) session.getAttribute("provaParaFazer");
		Util.addLog(session, LDAO, this);
		String[] cronometroSeparado = cronometro.split(":");
		Integer min = Integer.valueOf(cronometroSeparado[0]);
		Integer seg = Integer.valueOf(cronometroSeparado[1]);
		
		Aluno a = new Aluno();
		a = (Aluno) session.getAttribute("alunoLogon");
		if (min > 0) {
			Integer count = (Integer) session.getAttribute("count");

			// verifica se o botão pressionado foi voltar ou avançar, pois em um
			// caso add +1 ao contador, no outro subtrai
			if (escolhaBotao.equals("voltar")) {
				if (count == 0) {

				} else {
					count = count - 1;
				}

			} else if (escolhaBotao.equals("proxima")) {				
				
				alt = ADAO.buscarID(altSelecionada);
				certaOuErrada.add(alt.getCerta());
				FazendoProva fp = new FazendoProva();
				fp.setAlternativa(alt);
				fp.setProvaAgendada(provaAgendada);
				fp.setAluno(a);
				DAO.adicionar(fp, count);

				count = count + 1;
			}
			while (provaAgendada.getProva().getQuestoes().size() > count) {
				session.setAttribute("questao", provaAgendada.getProva()
						.getQuestoes().get(count));
				session.setAttribute("alternativas", provaAgendada.getProva()
						.getQuestoes().get(count).getAlternativas());
				session.setAttribute("nQuestoes", provaAgendada.getProva()
						.getnQuestoes());
				session.setAttribute("count", count);
				session.setAttribute("duracao", (min * 60) + seg);
				return "resolucaoDeProva";
			}
			modelo.addAttribute("mensagemTermino",
					"Você finalizou a prova, clique para gerar o relatório da prova");
			session.setAttribute("atributosParaCorrecao", certaOuErrada);
			return "terminoDeProva";

		} else {
			modelo.addAttribute("mensagemErro", "Tempo de prova esgotado");
			return "erroAoFazerProva";
		}

	}

	@RequestMapping("gerarRelatorio")
	public String gerarRelatorioDaProva(HttpSession session) {
		List<String> certasOuErradas = (List<String>) session
				.getAttribute("atributosParaCorrecao");
		Double nota = 0.0;
		for (int i = 0; i < certasOuErradas.size(); i++) {
			if(certasOuErradas.get(i).equals("C")){
				nota ++;
			}
		}
		nota = nota/certasOuErradas.size();
		nota = nota*100;
		System.out.println(nota);
		return "";
	}

}
