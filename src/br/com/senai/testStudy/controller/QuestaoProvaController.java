package br.com.senai.testStudy.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.QuestaoProva;

@Controller
public class QuestaoProvaController {
	private final QuestaoProvaDAO DAO;
	private final ProvaDAO PDAO;
	private final DisciplinaDAO DDAO;
	

	@Autowired
	public QuestaoProvaController(DisciplinaDAO DDAO, QuestaoProvaDAO DAO, ProvaDAO PDAO) {
		this.DAO = DAO;
		this.PDAO = PDAO;
		this.DDAO = DDAO;
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
			String disp1, String priv1, String nomeProva, Prova prova) {		
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
		// TODO código para trazer a alternativa
		/*
		List<List<Alternativa>> alternativas = new ArrayList<List<Alternativa>>();
		for (int i = 0; i < questoes.size(); i++) {
			for (int j = 0; j < questoes.get(i).size(); j++) {
				System.out.println(questoes.get(i).get(j).getIdQuestaoProva());
				alternativas.add(ADAO.listarPorQuestao(questoes.get(i).get(j).getIdQuestaoProva()));
			}
		}
		modelo.setAttribute("alternativas", alternativas);
		*/
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

		for (List<QuestaoProva> list : questoes) {
			for (QuestaoProva questaoProva : list) {
				if (teste.contains(questaoProva)) {
					teste.remove(questaoProva);
				}
			}
		}
		// TODO eu preciso pegar a prova, questão a matéria e aí sim só a disciplina
		
		
		
		
		// AQUI ELE JÁ ESTÁ SALVANDO A PROVA... (SISTEMA APRESSADO)
		prova.setDificuldadeDE(dificuldadeDE);
		prova.setDificuldadeATE(dificuldadeATE);
		prova.setNomeProva(nomeProva);
		prova.setnQuestoes(nQuestoes);
		// PDAO.adicionar(prova);
		modelo.setAttribute("prova", prova);
		return "addProvaPasso2";
	}

	@RequestMapping("attQuestoesProva")
	private String attQuestoesProva(HttpSession session, String questaoAdd,
			String questaoRem) {
		String[] questoesAdd = questaoAdd.trim().split(",");
		String[] questoesRem = questaoRem.trim().split(",");
		List<List<QuestaoProva>> questoesProva = (List<List<QuestaoProva>>) session
				.getAttribute("questoes");
		List<QuestaoProva> questoes = (List<QuestaoProva>) session
				.getAttribute("tetas");

		// lista auxiliar onde todas as questões da prova se transformarão em
		// apenas uma lista
		List<QuestaoProva> aux = new ArrayList<QuestaoProva>();
		for (int i = 0; i < questoesProva.size(); i++) {
			for (int j = 0; j < questoesProva.get(i).size(); j++) {
				aux.add(questoesProva.get(i).get(j));
			}
		}

		// parte do método que adiciona uma questão de fora na prova
		for (int i = 0; i < questoesAdd.length; i++) {
			// esta string corresponde a uma questão selecionada para adição...
			String string = questoesAdd[i];
			// percorre o numero de posições da lista das questões possíveis...
			for (int j = 0; j < questoes.size(); j++) {
				// caso a questão que ele achou na lista for igual a questão que
				// está na string, adiciona em uma lista e remove na outra...
				if (questoes.get(j).getIdQuestaoProva().toString()
						.equals(string)) {
					aux.add(questoes.get(j));
					questoesProva.clear();
					questoesProva.add(aux);
					questoes.remove(questoes.get(j));
				}
			}
		}
		// parte do método que tira uma questão da prova
		for (int i = 0; i < questoesRem.length; i++) {
			String string = questoesRem[i];
			for (List<QuestaoProva> list : questoesProva) {
				for (QuestaoProva questaoProva : list) {
					if (string.equals(questaoProva.getIdQuestaoProva()
							.toString())) {
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
