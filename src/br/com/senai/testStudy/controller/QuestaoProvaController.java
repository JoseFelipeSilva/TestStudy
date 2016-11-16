package br.com.senai.testStudy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
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
		modelo.addAttribute("disc",
				DAO.listarDisc(p.getEscolaProfessor().getIdEmp()));
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
		modelo.addAttribute("qp", DAO.listarLocais(p.getIdProfessor()));
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

	@RequestMapping("pegandoQuestoes")
	private String aqui(HttpSession modelo, Integer nQuestoes,
			String materiaSelecionada, String diss1, String alt1,
			Integer dificuldadeDE, Integer dificuldadeATE, HttpSession session,
			String disp1, String priv1) {
		// cria um vetor com as matérias que o cara quer criar a prova
		String[] materias = materiaSelecionada.trim().split(",");
		// cria uma lista com a lista das questões com as materias que o cara
		// quer
		List<List<QuestaoProva>> questoes = new ArrayList<List<QuestaoProva>>();
		// laço para preencher com as questões das matérias que o cara quer e
		// salvar no array de cima
		for (int i = 0; i < materias.length; i++) {
			// SE AS DUA ESTIVEREM SELECIONADAS OU VAZIAS ELE TRAS TRUE, SE
			// APENAS UMA ESTIVER SELECIONADA
			// ELE TRAZ O SEU RESPECTIVO VALOR
			questoes.add(DAO.listarqp(dificuldadeDE, dificuldadeATE,
					(disp1 == null ? (priv1 == null ? "true" : priv1)
							: ((priv1 == null ? disp1 : "true"))),
					((Professor) session.getAttribute("profLogon"))
							.getIdProfessor(), Integer.valueOf(materias[i])));
		}
		Collections.shuffle(questoes);
		List<QuestaoProva> teste = new ArrayList<QuestaoProva>();
		for (List<QuestaoProva> list : questoes) {
			for (QuestaoProva questaoProva : list) {
				teste.add(questaoProva);
			}
		}
		modelo.setAttribute("tetas", teste);
		Integer aux = 0;
		for (List<QuestaoProva> list : questoes) {
			aux = (aux + list.size());
		}
		if (aux < nQuestoes) {
			throw new RuntimeException("CADASTRE MAIS QUESTOES");
		}

		if (questoes.size() != 0) {
			while (aux != nQuestoes) {
				if (aux < nQuestoes) {
					throw new RuntimeException("se fodeu" + this.toString());
				}
				List<Integer> integers = new ArrayList<Integer>();
				for (List<QuestaoProva> list : questoes) {
					integers.add(list.size());
				}
				Collections.sort(integers);
				for (List<QuestaoProva> list : questoes) {
					if (integers.get(integers.size() - 1) == list.size()) {
						list.remove(list.size() - 1);
						break;
					}
				}
				aux--;
			}
		} else {
			while (aux != nQuestoes) {
				Collections.shuffle(questoes.get(0));
				questoes.get(0).remove(questoes.get(0).size() - 1);
				aux--;
			}
		}

		Collections.shuffle(questoes);
		modelo.setAttribute("questoes", questoes);
		/*for (List<QuestaoProva> list : questoes) {
			for (QuestaoProva questaoProva : list) {
				if (teste.contains(questaoProva)) {
					teste.remove(questaoProva);
				}
			}
		}*/
		return "addProvaPasso2";
	}

	@RequestMapping("attQuestoesProva")
	private String attQuestoesProva(HttpSession session, String questaoAdd,
			String questaoRem) {
		String[] questoesAdd = questaoAdd.trim().split(",");
		System.out.println("questoesAdd Sozinho:"+questoesAdd.toString());
		String[] questoesRem = questaoRem.trim().split(",");
		List<List<QuestaoProva>> questoesProva = (List<List<QuestaoProva>>) session
				.getAttribute("questoes");
		List<QuestaoProva> questoes = (List<QuestaoProva>) session
				.getAttribute("tetas");
		
		// TODO é necessário mudar os laços for e tirar o teste if. Porque o for deve testar apenas quantas questões estão selecionadas
		// ex: se tiver 3 questões selecionadas ele simplesmente vai adicionar em uma lista e remover na outra 3 vezes...
		
		for (int i = 0; i < questoesAdd.length; i++) {
			
		}
		
		for (int i = 0; i < questoesRem.length; i++) {
			String string = questoesRem[i];
			for (List<QuestaoProva> list : questoesProva) {
				for (QuestaoProva questaoProva : list) {
					if (string.equals(questaoProva.getIdQuestaoProva().toString())) {
						questoes.add(questaoProva);
						list.remove(questaoProva);
						break;
					}
				}
			}
		}
		
		session.setAttribute("questoes", questoesProva);
		session.setAttribute("tetas", questoes);
		return "addProvaPasso2";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
