package br.com.senai.testStudy.controller;

import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.Turma;

@Controller
public class ProvaAgendadaController {
	private final ProvaAgendadaDAO dao;
	private final ProvaDAO pdao;
	private final TurmaDAO tdao;

	@Autowired
	public ProvaAgendadaController(TurmaDAO tdao, ProvaAgendadaDAO dao,
			ProvaDAO pdao) {
		this.dao = dao;
		this.pdao = pdao;
		this.tdao = tdao;
	}

	@RequestMapping("newProvaAgendada")
	public String addPageProvaAgendada(Model model, HttpSession session, Professor p) {
		p = (Professor)session.getAttribute("profLogon");
		model.addAttribute("LProvas", pdao.listar(p.getIdProfessor()));
		model.addAttribute("LTurmas", tdao.listar());
		return "addProvaAgendada";
	}

	@RequestMapping("adicionarProvaAgendada")
	public String adicionarProvaAgendada(String dataInicio, String dataTermino, 
			String dataRealizacao,Turma t, Prova p, String horaInicioJSP, String horaTerminoJSP) {
		ProvaAgendada pa = new ProvaAgendada();
		if (!horaInicioJSP.isEmpty()&&!horaTerminoJSP.isEmpty()&&!dataRealizacao.isEmpty()) {		
		String[] s = horaInicioJSP.split(":");
		Time time = new Time(Integer.valueOf(s[0]),Integer.valueOf(s[1]),0);
		String[] r = horaTerminoJSP.split(":");
		Time time2 = new Time(Integer.valueOf(r[0]),Integer.valueOf(r[1]),0);
		String[] i = dataRealizacao.split("-");
		Date date = new Date(Integer.valueOf(i[0]),Integer.valueOf(i[1]),Integer.valueOf(i[2]));
		pa.setDataRealizacao(date);
		pa.setHoraTermino(time2);
		pa.setHoraInicio(time);
		}else if (!dataInicio.isEmpty()&&!dataTermino.isEmpty()) {
			String[] n = dataInicio.split("-");
			Date date2 = new Date(Integer.valueOf(n[0]),Integer.valueOf(n[1]),Integer.valueOf(n[2]));
			String[] g = dataTermino.split("-");
			Date date3 = new Date(Integer.valueOf(g[0]),Integer.valueOf(g[1]),Integer.valueOf(g[2]));
			pa.setDataInicio(date2);
			pa.setDataTermino(date3);
		}else {
			throw new RuntimeException("ADD PROVA AGEND  "+this);
		}
		
		pa.setProva(p);
		pa.setTurma(t);
		dao.adicionar(pa);
		return "sucessoPage";
	}
}
