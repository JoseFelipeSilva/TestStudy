package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.ExaminadorDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.Util;

@Controller
public class ExamController {
	private final ExaminadorDAO dao;
	private final LogDAO ldao;

	@Autowired
	public ExamController(ExaminadorDAO dao, LogDAO ldao) {
		this.dao = dao;
		this.ldao = ldao;
	}

	@RequestMapping("formExam")
	public String formAdd(Model model) {

		// MÉTODO RESPONSÁVEL POR LISTAR AS DISCIPLINAS PADRÃO PARA SEREM
		// ASSOCIADAS AO EXAMINADOR
		model.addAttribute("disciplina", dao.discPadrao());
		return "formCadExam";
	}
	
	@RequestMapping("backToListExamanidor")
	public String backListaExaminador(Model model){
		model.addAttribute("listaEXAM", dao.listar());
		return "listaEXAM";
	}

	@RequestMapping("adicionaExam")
	public String addExam(Examinador exam, MultipartFile arquivo, Disciplina d, HttpSession session) {
		exam.setDisciplinaExaminador(d);
		if (!arquivo.isEmpty()) {
			try {
				exam.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		dao.adicionar(exam);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("listandoExam")
	public String lista(Model model, HttpSession session) {
		model.addAttribute("listaEXAM", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaEXAM";
	}

	@RequestMapping("alterandoexam")
	public String alterando(Model model, Examinador exam) {
		model.addAttribute("buscaId", dao.buscarID(exam.getIdExaminador()));
		return "formAlterEXAM";
	}

	@RequestMapping("alteraExam")
	public String alterar(Examinador exam, HttpSession session) {
		dao.alterar(exam);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("removeexam")
	public String remover(Examinador exam, HttpSession session) {
		dao.adicionarMorto(exam.getIdExaminador());
		dao.remover(exam);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("alteraPhotoExaminador")
	public String altPagePhotoExaminador(Model model, Examinador exam) {
		model.addAttribute("exam", dao.buscarID(exam.getIdExaminador()));
		return "alteraFotoExaminador";
	}

	@RequestMapping("alterandoFotoDeExaminador")
	public String alteraFotoExaminador(Model model, Examinador exam,
			MultipartFile arquivo, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				exam.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		dao.alterarFoto(exam);
		Util.addLog(session, ldao, this);
		model.addAttribute("listaEXAM", dao.listar());
		return "listaEXAM";
	}

	// MÉTODO RESPONSÁVEL POR LISTAR TODAS AS QUESTÕES A SEREM ANALISADAS PELO
	// EXAMINADOR
	@RequestMapping("pendenciasExam")
	public String pendenciaExam(HttpSession sessao, Examinador exam,
			Model modelo) {
		exam = (Examinador) sessao.getAttribute("examLogon");
		modelo.addAttribute("pendencias", dao.listarPendencias(exam
				.getDisciplinaExaminador().getIdDisciplina()));
		return "listaQuestoesPendentesEXAM";
	}

	// MÉTODO RESPONSÁVEL POR RETORNAR A PÁGINA DE ALTERAÇÃO DE STATUS DA
	// QUESTÃO
	@RequestMapping("alterandoStatus")
	public String alteraStatus(QuestaoProva qp, Model modelo, Examinador e, HttpSession sessao) {		
		modelo.addAttribute("infoAlternativa",dao.buscarAlter(qp.getIdQuestaoProva()));
		modelo.addAttribute("infoQuestao",dao.buscarQuestao(qp.getIdQuestaoProva()));
		e = (Examinador) sessao.getAttribute("examLogon");
		modelo.addAttribute("infoExam", e);
		return "formAlterStatusQP";
	}

	// MÉTODO RESPONSÁVEL POR ALTERAR O STATUS DA QUESTÃO
	@RequestMapping("alteraStatus")
	public String alterStatus(QuestaoProva qp, HttpSession s, Examinador e, HttpSession session) {
		e = (Examinador) s.getAttribute("examLogon");		
		dao.alteraStatus(qp, e.getIdExaminador());
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}
	// MÉTODO RESPONSÁVEL POR LISTAR TODAS AS QUESTÕES ANTIGAS A SEREM ANALISADAS PELO
	// EXAMINADOR
	@RequestMapping("pendenciasAntigasExam")
	public String pendenciasAntigasExam(Examinador exam, HttpSession sessao, Model modelo){
		exam = (Examinador) sessao.getAttribute("examLogon");
		modelo.addAttribute("pendencias", dao.listarPendenciasAntigas(exam.getIdExaminador()));		
		Util.addLog(sessao, ldao, this);
		return "listaQuestoesPendentesEXAM";
	}
	
	@RequestMapping("alterandoStatusAntigo")
	public String alterandoAntiga(QuestaoProva qp, Model modelo, HttpSession session){
		modelo.addAttribute("infoAlternativa", dao.buscarAlter(qp.getIdQuestaoProva()));
		modelo.addAttribute("infoQuestao", dao.buscarQuestaoAntigaID(qp.getIdQuestaoProva()));
		Util.addLog(session, ldao, this);
		return "formAlterStatusQP";
	}
}











