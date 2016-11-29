package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.ProfessorDAO;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.util.Util;

@Controller
public class ProfessorController {
	private final ProfessorDAO dao;
	private final EscolaClienteDAO edao;
	private final LogDAO ldao;

	@Autowired
	public ProfessorController(ProfessorDAO dao, EscolaClienteDAO edao, LogDAO ldao) {
		this.dao = dao;
		this.edao = edao;
		this.ldao = ldao;
	}

	@RequestMapping("newProfessor")
	public String addPageProfessor(Model model) {
		model.addAttribute("LEscola", edao.listar());
		return "addProfessor";
	}

	@RequestMapping("adicionamentoProfessor")
	public String addProfessor(MultipartFile arquivo, Professor prof,
			EscolaCliente escola, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				prof.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		prof.setEscolaProfessor(escola);
		dao.adicionar(prof);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}

	@RequestMapping("ListagemProfessor")
	public String listaOfProfessores(Model model, HttpSession session) {
		model.addAttribute("LProfs", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaProfessor";
	}

	@RequestMapping("backToListProfessores")
	public String backListaProfessor(Model model) {
		model.addAttribute("LProfs", dao.listar());
		return "listaProfessor";
	}

	@RequestMapping("removerProfessor")
	public String removerProfessor(Professor prof, HttpSession session) {
		dao.adicionarMorto(prof.getIdProfessor());
		dao.remover(prof);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}

	@RequestMapping("alteraPhotoProfessor")
	public String altPageFoto(Professor prof, Model model) {
		model.addAttribute("prof", dao.buscarID(prof.getIdProfessor()));
		return "alteraFotoProfessor";
	}

	@RequestMapping("alterandoFotoDeProf")
	public String alterarFotoProfessor(MultipartFile arquivo, Professor prof,
			Model model, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				prof.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.alterarFoto(prof);
		Util.addLog(session, ldao, this);
		model.addAttribute("LProfs", dao.listar());
		return "listaProfessor";
	}

	@RequestMapping("alterarProfessor")
	public String altPageProf(Model model, Professor prof, EscolaCliente escola) {
		model.addAttribute("prof", dao.buscarID(prof.getIdProfessor()));
		model.addAttribute("LEscola", edao.listar());
		return "alteraProfessor";
	}

	@RequestMapping("alterandoProfessor")
	public String alteraProfessor(Professor prof, EscolaCliente escola,
			Model model, HttpSession session) {
		prof.setEscolaProfessor(escola);
		dao.alterar(prof);
		model.addAttribute("LProfs", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaProfessor";
	}
}
