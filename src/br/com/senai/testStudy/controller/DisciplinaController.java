package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.EscolaCliente;

@Controller
public class DisciplinaController {
	private final DisciplinaDAO dao;
	@Autowired
	public DisciplinaController(DisciplinaDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("formDisciplina")
	public String formAdd(HttpSession sessao, Coordenador c){
		// SETANDO OS VALORES DA SESSÃO NA MÃO (QUANDO O LOGIN FOR EFETUADO ISSO SERÁ DINÂMICO, SUBSTITUÍDO POR UM MÉTODO NO DAO)
		EscolaCliente e = new EscolaCliente();
		e.setCnpjEmp("03.774.819/0001-02");
		e.setEmailEmp("empresa2@emp");
		e.setIdEmp(2);
		e.setNomeEmp("SENAI");
		e.setNomeEmpresarialEmp("serviço nacional de aprendizagem industrial");
		e.setTelefoneEmp("(11)0000-0000");
		
		c.setCpf("453.367.578.61");
		c.setEmail("coord1@coord");
		c.setEscola(e);
		c.setIdCoord(1);
		c.setNome("Coordenador1");
		c.setRg("50.773.139-6");
		c.setSenha("123");
		c.setSexo("masc");
		
		sessao.setAttribute("usuarioLogado", c);
		return "formCadDisc";
	}
	
	@RequestMapping("adicionaDisc")
	public String add(Disciplina disc, Coordenador c, HttpSession sessao){
		c = (Coordenador) sessao.getAttribute("usuarioLogado");
		disc.setPadraoDisciplina("privada");
		disc.setEscola(c.getEscola());
		dao.adicionar(disc);
		return "sucesso";
	}
	
	@RequestMapping("listandoDisciplina")
	public String listando(Model model){
		model.addAttribute("listaDISC", dao.listar());
		return "listaDISC";
	}
	
	@RequestMapping("alterandoDisc")
	public String alterando(Model model, Disciplina disciplina){
		model.addAttribute("infoDisc", dao.buscarID(disciplina.getIdDisciplina()));
		return "formAlterDISC";
		
	}
	@RequestMapping("alterarDISC")
	public String alterar(Disciplina disciplina){
		dao.alterar(disciplina);
		return "sucesso";
	}
	@RequestMapping("removendoDisc")
	public String remover(Disciplina disc){
		dao.remover(disc);
		return "sucesso";
	}
	
}
