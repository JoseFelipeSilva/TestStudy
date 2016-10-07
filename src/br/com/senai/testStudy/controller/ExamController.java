package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.dao.ExaminadorDAO;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.QuestaoProva;

@Controller
public class ExamController {
	private final ExaminadorDAO dao;
	private DisciplinaDAO discdao;

	@Autowired
	public ExamController(ExaminadorDAO dao, DisciplinaDAO discdao) {
		this.dao = dao;
		this.discdao = discdao;
	}

	@RequestMapping("formExam")
	public String formAdd(Model model) {
		
		// TODO criar o m�todo listarPadrao e usar no lugar do listar
		model.addAttribute("disciplina", discdao.listar());
		return "formCadExam";
	}

	@RequestMapping("adicionaExam")
	public String addExam(Examinador exam, MultipartFile arquivo) {
		if (!arquivo.isEmpty()) {
			try {
				exam.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		dao.adicionar(exam);
		return "sucesso";
	}

	@RequestMapping("listandoExam")
	public String lista(Model model) {
		model.addAttribute("listaEXAM", dao.listar());
		return "listaEXAM";
	}

	@RequestMapping("alterandoexam")
	public String alterando(Model model, Examinador exam) {
		model.addAttribute("buscaId", dao.buscarID(exam.getIdExaminador()));
		return "formAlterEXAM";
	}

	@RequestMapping("alteraExam")
	public String alterar(Examinador exam) {
		dao.alterar(exam);
		return "sucesso";
	}

	@RequestMapping("removeexam")
	public String remover(Examinador exam) {
		dao.remover(exam);
		return "sucesso";
	}

	@RequestMapping("alteraPhotoExaminador")
	public String altPagePhotoExaminador(Model model, Examinador exam) {
		model.addAttribute("exam", dao.buscarID(exam.getIdExaminador()));
		return "alteraFotoExaminador";
	}

	@RequestMapping("alterandoFotoDeExaminador")
	public String alteraFotoExaminador(Model model, Examinador exam,
			MultipartFile arquivo) {
		if (!arquivo.isEmpty()) {
			try {
				exam.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		dao.alterarFoto(exam);
		model.addAttribute("listaEXAM", dao.listar());
		return "listaEXAM";
	}
	
	// M�TODO RESPONS�VEL POR LISTAR TODAS AS QUEST�ES A SEREM ANALISADAS PELO EXAMINADOR
	@RequestMapping("pendenciasExam")
	public String pendenciaExam(HttpSession sessao, Examinador exam, Model modelo){
		exam.setNome("Daniel");
		exam.setCpf("45336757861");
		exam.setEmail("daniel@daniel");
		sessao.setAttribute("examLogado", exam);
		modelo.addAttribute("pendencias", dao.listarPendencias());
		return"listaQuestoesPendentesEXAM"; 
	}
	// M�TODO RESPONS�VEL POR RETORNAR A P�GINA DE ALTERA��O DE STATUS DA QUEST�O
	@RequestMapping("alterarStatus")
	public String alteraStatus(QuestaoProva qp, Model modelo){
		modelo.addAttribute("infoQuestao",dao.buscarQuestao(qp.getIdQuestaoProva()));
		modelo.addAttribute("infoAlternativa",dao.buscarAlter(qp.getIdQuestaoProva()));
		return "formAlterStatusQP";
	}
}
