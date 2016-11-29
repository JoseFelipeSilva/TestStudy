package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.AdministradorDAO;
import br.com.senai.testStudy.dao.AlunoDAO;
import br.com.senai.testStudy.dao.CoordenadorDAO;
import br.com.senai.testStudy.dao.ExaminadorDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.ProfessorDAO;
import br.com.senai.testStudy.model.Administrador;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.util.Util;

@Controller
public class AdmController {
	private final AdministradorDAO ADMDAO;
	private final ProfessorDAO pdao;
	private final AlunoDAO adao;
	private final CoordenadorDAO cdao;
	private final ExaminadorDAO edao;
	private final LogDAO ldao;

	@Autowired
	public AdmController(ProfessorDAO pdao, CoordenadorDAO cdao, AlunoDAO adao,
			ExaminadorDAO edao, AdministradorDAO ADMDAO, LogDAO ldao) {
		this.ADMDAO = ADMDAO;
		this.adao = adao;
		this.edao = edao;
		this.cdao = cdao;
		this.pdao = pdao;
		this.ldao = ldao;
	}

	@RequestMapping("logar")
	public String logando(Examinador exam, Professor prof, Coordenador coord,
			Aluno aluno, Administrador adm, HttpSession session) {
		if (ADMDAO.existeADM(adm) != null) {
			adm = ADMDAO.existeADM(adm);
			session.setAttribute("admLogon", adm);
			Util.addLog(session, ldao, this);
			return "indexAdm";
		} else if (adao.existeAluno(aluno) != null) {
			aluno = adao.existeAluno(aluno);
			session.setAttribute("alunoLogon", aluno);
			Util.acessandoNotificacoes(session);
			Util.addLog(session, ldao, this);
			return "indexAluno";
		} else if (cdao.existeCOORD(coord) != null) {
			coord = cdao.existeCOORD(coord);
			session.setAttribute("coordLogon", coord);
			Util.addLog(session, ldao, this);
			return "indexCoordenador";
		} else if (pdao.existeProf(prof) != null) {
			prof = pdao.existeProf(prof);
			session.setAttribute("profLogon", prof);
			Util.addLog(session, ldao, this);
			return "indexProfessor";
		} else if (edao.existeExaminador(exam) != null) {
			exam = edao.existeExaminador(exam);
			session.setAttribute("examLogon", exam);
			Util.addLog(session, ldao, this);
			return "indexExaminador";
		}else {
			return "redirect:index.jsp";
		}
	}

	@RequestMapping("logoff")
	public String sair(HttpSession session) {
		Util.addLog(session, ldao, this);
		session.invalidate();
		return "redirect:index.jsp";
	}

	@RequestMapping("formADM")
	public String formAddADm() {
		return "formCadAdm";
	}

	@RequestMapping("backToListAdm")
	public String backListaAdm(Model model) {
		model.addAttribute("listaADM", ADMDAO.listar());
		return "listaADM";
	}

	@RequestMapping("adicionaAdm")
	public String addADM(Administrador adm, MultipartFile arquivo, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				adm.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		Util.addLog(session, ldao, this);
		ADMDAO.adicionar(adm);
		return "sucesso";
	}

	@RequestMapping("backToIndexAdm")
	public String backIndexAdm() {
		return "indexAdm";
	}

	@RequestMapping("listandoADM")
	public String lista(Model model, HttpSession session) {
		model.addAttribute("listaADM", ADMDAO.listar());
		Util.addLog(session, ldao, this);
		return "listaADM";
	}

	@RequestMapping("alterandoadm")
	public String alterando(Integer id, Model model) {
		model.addAttribute("buscaId", ADMDAO.buscarID(id));
		return "formAlterADM";
	}

	@RequestMapping("alteraADM")
	public String alterar(Administrador adm, HttpSession session) {
		ADMDAO.alterar(adm);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("removeadm")
	public String remover(Administrador adm, HttpSession session) {
		ADMDAO.adicionaMorto(adm);
		ADMDAO.remover(adm);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("alteraPhotoADM")
	public String altPhotoPagaAdm(Model model, Administrador adm) {
		model.addAttribute("adm", ADMDAO.buscarID(adm.getIdAdm()));
		return "AlteraFotoAdministrador";
	}

	@RequestMapping("alterandoFotoDeAdm")
	public String alterarFotoAdm(Model model, MultipartFile arquivo,
			Administrador adm, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				adm.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		ADMDAO.alterarFoto(adm);
		Util.addLog(session, ldao, this);
		model.addAttribute("listaADM", ADMDAO.listar());
		return "listaADM";
	}
}
