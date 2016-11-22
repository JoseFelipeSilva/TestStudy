package br.com.senai.testStudy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.AlunoDAO;
import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.Turma;

@Controller
public class AlunoController {
	private final AlunoDAO dao;
	private final TurmaDAO tdao;
	private final ProvaAgendadaDAO pdao;

	@Autowired
	public AlunoController(ProvaAgendadaDAO pdao, AlunoDAO dao, TurmaDAO tdao) {
		this.dao = dao;
		this.tdao = tdao;
		this.pdao = pdao;
	}

	@RequestMapping("newAluno")
	public String addPageAluno(Model model) {
		model.addAttribute("LTurmas", tdao.listar());
		return "addAluno";
	}

	@RequestMapping("adicionarAluno")
	public String adicionarAluno(Aluno aluno, Turma turma, MultipartFile arquivo) {
		if (!arquivo.isEmpty()) {
			try {
				aluno.setFotoAluno(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		aluno.setTurmaAluno(turma);
		dao.adicionar(aluno);
		return "sucessoPage";
	}

	@RequestMapping("ListagemAluno")
	public String listarAlunos(Model model) {
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}

	@RequestMapping("removerAluno")
	public String removerAluno(Aluno aluno, Model model) {
		dao.remover(aluno);
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
	public String alteracaoAluno(Model model, Aluno aluno, Turma turma) {
		aluno.setTurmaAluno(turma);
		dao.alterar(aluno);
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}

	@RequestMapping("alteraPhotoAluno")
	public String altPageFotoAluno(Model model, Aluno aluno) {
		model.addAttribute("al", dao.buscarID(aluno.getIdAluno()));
		return "alteraFotoAluno";
	}

	@RequestMapping("alterandoFotoDeAluno")
	public String alterarFotoDoAluno(MultipartFile arquivo, Aluno aluno,
			Model model) {
		if (!arquivo.isEmpty()) {
			try {
				aluno.setFotoAluno(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.alterarPhoto(aluno);
		model.addAttribute("LAlunos", dao.listar());
		return "listaAluno";
	}
	
	@RequestMapping("acessandoNotificacoes")
	public String acessandoNotificacoes(HttpSession sessao, Model modelo){
		// o objetivo aqui � criar duas listas, e comparar se tem prova marcada na turma do aluno que est� logado,
		// se sim exibe no painel de notifica��es...
		List<ProvaAgendada> provasAgendadasNotificacao = new ArrayList<ProvaAgendada>();
		
		List<ProvaAgendada> provasAgendadas = new ArrayList<ProvaAgendada>();
		provasAgendadas = pdao.listar();
		
		Aluno aluno = (Aluno) sessao.getAttribute("alunoLogon");
		// TODO j� que vc ta a� vai na pagina "paginaNotificacoes" e faz o foreach
		// pra popular a tabela l� kkkkkkkk
		for (int i = 0; i < provasAgendadas.size(); i++) {
				if (aluno.getTurmaAluno().equals(provasAgendadas.get(i).getTurma())) {
					provasAgendadasNotificacao.add(provasAgendadas.get(i));
				}			
		}
		modelo.addAttribute("notificacoes", provasAgendadasNotificacao);
		return "paginaNotificacoes";
	}
	
}



















