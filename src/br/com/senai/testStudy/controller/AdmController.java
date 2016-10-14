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
import br.com.senai.testStudy.dao.ProfessorDAO;
import br.com.senai.testStudy.model.Administrador;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.Professor;

@Controller
public class AdmController {
	private final AdministradorDAO ADMDAO;
	private final ProfessorDAO pdao;
	private final AlunoDAO adao;
	private final CoordenadorDAO cdao;
	private final ExaminadorDAO edao;

	@Autowired
	public AdmController(ProfessorDAO pdao, CoordenadorDAO cdao, AlunoDAO adao,
			ExaminadorDAO edao, AdministradorDAO ADMDAO) {
		this.ADMDAO = ADMDAO;
		this.adao = adao;
		this.edao = edao;
		this.cdao = cdao;
		this.pdao = pdao;
	}

	@RequestMapping("logar")
	public String logando(Examinador exam, Professor prof, Coordenador coord,
			Aluno aluno, Administrador adm, HttpSession session) {
		if (ADMDAO.existeADM(adm) != null) {
			session.setAttribute("admLogon", adm);
			return "indexAdm";
		} else if (adao.existeAluno(aluno) != null) {
			session.setAttribute("alunoLogon", aluno);
			return "indexAluno";
		} else if (edao.existeExaminador(exam) != null) {
			session.setAttribute("examLogon", exam);
			return "indexExaminador";
		} else if (cdao.existeCOORD(coord) != null) {
			session.setAttribute("coordLogon", exam);
			return "indexCoordenador";
		} else if (pdao.existeProf(prof) != null) {
			session.setAttribute("profLogon", prof);
			return "indexProfessor";
		} else {
			return "redirect:index.jsp";
		}
	}

	@RequestMapping("formADM")
	public String formAddADm() {
		return "formCadAdm";
	}

	@RequestMapping("adicionaAdm")
	public String addADM(Administrador adm, MultipartFile arquivo) {
		if (!arquivo.isEmpty()) {
			try {
				adm.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		ADMDAO.adicionar(adm);
		return "sucesso";
	}

	@RequestMapping("listandoADM")
	public String lista(Model model) {
		model.addAttribute("listaADM", ADMDAO.listar());
		return "listaADM";
	}

	@RequestMapping("alterandoadm")
	public String alterando(Integer id, Model model) {
		model.addAttribute("buscaId", ADMDAO.buscarID(id));
		return "formAlterADM";
	}

	@RequestMapping("alteraADM")
	public String alterar(Administrador adm) {
		ADMDAO.alterar(adm);
		return "sucesso";
	}

	@RequestMapping("removeadm")
	public String remover(Administrador adm) {
		ADMDAO.remover(adm);
		return "sucesso";
	}

	@RequestMapping("alteraPhotoADM")
	public String altPhotoPagaAdm(Model model, Administrador adm) {
		model.addAttribute("adm", ADMDAO.buscarID(adm.getIdAdm()));
		return "AlteraFotoAdministrador";
	}

	@RequestMapping("alterandoFotoDeAdm")
	public String alterarFotoAdm(Model model, MultipartFile arquivo,
			Administrador adm) {
		if (!arquivo.isEmpty()) {
			try {
				adm.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		ADMDAO.alterarFoto(adm);
		model.addAttribute("listaADM", ADMDAO.listar());
		return "listaADM";
	}
}
