package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.AlunoDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.Util;

@Controller
public class AlunoController {
	private final AlunoDAO dao;
	private final TurmaDAO tdao;
	private final LogDAO ldao;

	@Autowired
	public AlunoController(AlunoDAO dao, TurmaDAO tdao, LogDAO ldao) {
		this.dao = dao;
		this.tdao = tdao;
		this.ldao = ldao;
	}

	@RequestMapping("newAluno")
	public String addPageAluno(Model model) {
		model.addAttribute("LTurmas", tdao.listar());
		return "addAluno";
	}

	@RequestMapping("adicionarAluno")
	public String adicionarAluno(Aluno aluno, Turma turma, MultipartFile arquivo, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				aluno.setFotoAluno(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		aluno.setTurmaAluno(turma);
		dao.adicionar(aluno);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}

	@RequestMapping("ListagemAluno")
	public String listarAlunos(Model model, HttpSession session) {
		model.addAttribute("LAlunos", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaAluno";
	}

	@RequestMapping("removerAluno")
	public String removerAluno(Aluno aluno, Model model, HttpSession session) {
		dao.adicionaMorto(aluno.getIdAluno());
		dao.remover(aluno);
		Util.addLog(session, ldao, this);
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}

	@RequestMapping("alterarAluno")
	public String altPageAluno(Model model, Aluno aluno) {
		model.addAttribute("al", dao.buscarID(aluno.getIdAluno()));
		model.addAttribute("LTurmas", tdao.listar());
		return "alteraAluno";
	}

	@RequestMapping("backToListAlunos")
	public String backListAlunos(Model model) {
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}

	@RequestMapping("alteracaoAluno")
	public String alteracaoAluno(Model model, Aluno aluno, Turma turma, HttpSession session) {
		aluno.setTurmaAluno(turma);
		dao.alterar(aluno);
		model.addAttribute("LAlunos", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaAluno";
	}

	@RequestMapping("alteraPhotoAluno")
	public String altPageFotoAluno(Model model, Aluno aluno) {
		model.addAttribute("al", dao.buscarID(aluno.getIdAluno()));
		return "alteraFotoAluno";
	}

	@RequestMapping("alterandoFotoDeAluno")
	public String alterarFotoDoAluno(MultipartFile arquivo, Aluno aluno,
			Model model, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				aluno.setFotoAluno(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.alterarPhoto(aluno);
		Util.addLog(session, ldao, this);
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}
}



















