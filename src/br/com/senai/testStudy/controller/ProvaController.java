package br.com.senai.testStudy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.MateriaDAO;
import br.com.senai.testStudy.dao.ProfessorDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.QuestaoProva;

@Controller
public class ProvaController {
	private final ProvaDAO dao;
	private final ProfessorDAO pdao;
	private final QuestaoProvaDAO qdao;
	private final MateriaDAO mdao;

	@Autowired
	public ProvaController(ProvaDAO dao, ProfessorDAO pdao, QuestaoProvaDAO qdao, MateriaDAO mdao) {
		this.dao = dao;
		this.pdao = pdao;
		this.qdao = qdao;
		this.mdao = mdao;
	}

	@RequestMapping("teste")
	public String teste(Prova p){
		System.out.println(p.getNomeProva() + p.getnQuestoes());
		return "sucesso";
	}
	
	@RequestMapping("newProva")
	public String addPageProva(Prova p,Professor professor, HttpSession sessao, Model modelo) {
		// UTILIZAR ISTO AUTOMATICAMENTE... (O PROFESSOR SÓ PODERÁ CRIAR A PROVA PRA ELE, OBVIAMENTE KKKKK) model.addAttribute("LProfs", pdao.listar());
		professor = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("materia", mdao.listar());
		modelo.addAttribute("disci", qdao.listarDisc(professor.getEscolaProfessor().getIdEmp()));
		modelo.addAttribute("continuando", false);
		return "addProvaPasso1";
	}
	
	@RequestMapping("provaPasso2")
	public String provaPasso2(Prova p,Professor professor, HttpSession sessao, Model modelo){
		professor = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("disci", qdao.listarDisc(professor.getEscolaProfessor().getIdEmp()));
		return "addProvaPasso2";
	}
	
	@RequestMapping("consultarMateriaAddProva")
	public String consultarMateriaAddProva(Professor p, Disciplina d, Model modelo, HttpSession sessao){
		p = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("materia", mdao.listarMateria(d));
		modelo.addAttribute("disci", mdao.listarDisc(p.getEscolaProfessor().getIdEmp()));
		modelo.addAttribute("disciplinaSelecionada",
				mdao.buscarID(d.getIdDisciplina()));
		return "addProvaPasso1";
	}

	@RequestMapping("adicionarProva")
	public String addPagina(Professor prof, Prova prova) {
		prova.setProfessor(prof);
		dao.adicionar(prova);
		return "sucessoPage";
	}

	@RequestMapping("ListagemProva")
	public String listaDeProvas(Model model) {
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("removerProva")
	public String removerProva(Prova prova, Model model) {
		dao.remover(prova);
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("alterarProva")
	public String altPageProva(Prova prova, Model model) {
		model.addAttribute("p", dao.buscarID(prova.getIdProva()));
		model.addAttribute("LProfs", pdao.listar());
		return "alteraProva";
	}

	@RequestMapping("backToListOfProvas")
	public String backToListOfProvas(Model model) {
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("alterandoProva")
	public String alterandoProva(Model model, Prova prova, Professor prof) {
		prova.setProfessor(prof);
		dao.alterar(prova);
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}
	
	@RequestMapping("salvarQuestoesNaProva")
	public String salvarQuestoesNaProva(HttpSession session){
		List<List<QuestaoProva>> questoesProva = (List<List<QuestaoProva>>) session
				.getAttribute("questoes");
		
		// TODO eu parei aqui, salvando as questões na tabela associativa, tem que pensar em como dar um split aí...
		for (int i = 0; i < questoesProva.size(); i++) {
			for (int j = 0; j < questoesProva.get(i).size(); j++) {
				
			}
		}
		return "sucesso";
	}
	
}
