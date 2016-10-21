package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.ExaminadorDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.Mensagem;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.util.EnviarEmail;

@Controller
public class ExamController {
	private final ExaminadorDAO dao;

	@Autowired
	public ExamController(ExaminadorDAO dao) {
		this.dao = dao;
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
	public String addExam(Examinador exam, MultipartFile arquivo, Disciplina d) {
		exam.setDisciplinaExaminador(d);
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
	public String alterStatus(QuestaoProva qp, HttpSession s, Examinador e, EnviarEmail send, Mensagem m) {
		e = (Examinador) s.getAttribute("examLogon");
		m.setDestinatario(qp.getAutorQuestao());
		m.setRemetente(e);
		if(m.getCorpoMensagem()!=null){
			send.enviarEmail(m);
		}
		
		dao.alteraStatus(qp, e.getIdExaminador());
		return "sucessoPage";
	}
	// MÉTODO RESPONSÁVEL POR LISTAR TODAS AS QUESTÕES ANTIGAS A SEREM ANALISADAS PELO
	// EXAMINADOR
	@RequestMapping("pendenciasAntigasExam")
	public String pendenciasAntigasExam(Examinador exam, HttpSession sessao, Model modelo){
		exam = (Examinador) sessao.getAttribute("examLogon");
		modelo.addAttribute("pendencias", dao.listarPendenciasAntigas(exam.getIdExaminador()));		
		return "listaQuestoesPendentesEXAM";
	}
	
	@RequestMapping("alterandoStatusAntigo")
	public String alterandoAntiga(QuestaoProva qp, Model modelo){
		modelo.addAttribute("infoAlternativa", dao.buscarAlter(qp.getIdQuestaoProva()));
		modelo.addAttribute("infoQuestao", dao.buscarQuestaoAntigaID(qp.getIdQuestaoProva()));
		return "formAlterStatusQP";
	}
}











