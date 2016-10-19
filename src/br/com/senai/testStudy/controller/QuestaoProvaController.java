package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.QuestaoProva;

@Controller
public class QuestaoProvaController {
	private final QuestaoProvaDAO DAO;

	@Autowired
	public QuestaoProvaController(QuestaoProvaDAO DAO) {
		this.DAO = DAO;
	}

	@RequestMapping("formQP")
	public String formQP(Model modelo, Professor p, HttpSession sessao) {
		p = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("disc", DAO.listarDisc(p.getEscolaProfessor().getIdEmp()));
		return "formCadQP";
	}

	@RequestMapping("filtrarMateria")
	public void filtrar(Disciplina disc, Model modelo) {
		modelo.addAttribute("mat", DAO.listarMat(disc.getIdDisciplina()));
	}

	/*
	 * @RequestMapping("formCadQP") public String FormCadQP(){ return
	 * "formCadQP"; }
	 */

	@RequestMapping("adicionaQP")
	public String adicionaQP(QuestaoProva qp, Materia m, Disciplina d,
			HttpSession sessao, Professor p) {
		p = (Professor) sessao.getAttribute("profLogon");
		
		m.setDisciplina(d);
		qp.setMateria(m);
		qp.setAutorQuestao(p);
		if (qp.getDisponibilidadeQuestao().equals("disp")) {
			qp.setStatusQuestao("enviado");
		} else if (qp.getDisponibilidadeQuestao().equals("priv")) {
			qp.setStatusQuestao("privada");
		}
		DAO.adicionar(qp);
		if (qp.getTipoQuestao().equals("obj")) {

			return "formCadALT";
		}
		return "sucesso";
	}

	@RequestMapping("listandoQP")
	public String listandoQP(Model modelo, Professor p, HttpSession sessao) {
		p = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("qp", DAO.listarqp(p.getIdProfessor()));
		return "listaQP";
	}

	@RequestMapping("alteraQuestao")
	public String alteraQP(Model model, QuestaoProva qp) {
		model.addAttribute("qp", DAO.buscarID(qp.getIdQuestaoProva()));
		return "formAlterQP";
	}

	@RequestMapping("alteraQP")
	public String alterarQP(QuestaoProva qp) {
		DAO.alterar(qp);
		return "sucesso";
	}
}
