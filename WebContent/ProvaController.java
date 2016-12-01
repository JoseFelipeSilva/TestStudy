package br.com.buweis.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.buweis.dao.AlternativaDAO;
import br.com.buweis.dao.AlunoDAO;
import br.com.buweis.dao.AlunoProvaDAO;
import br.com.buweis.dao.AssuntoDAO;
import br.com.buweis.dao.DisciplinaDAO;
import br.com.buweis.dao.DocenteDAO;
import br.com.buweis.dao.ProvaAssuntoDAO;
import br.com.buweis.dao.ProvaDAO;
import br.com.buweis.dao.ProvaDisciplinaDAO;
import br.com.buweis.dao.ProvaQuestaoDAO;
import br.com.buweis.dao.ProvaTurmaDAO;
import br.com.buweis.dao.QuestaoDAO;
import br.com.buweis.dao.RespostaDAO;
import br.com.buweis.dao.TurmaDAO;
import br.com.buweis.model.Alternativa;
import br.com.buweis.model.Aluno;
import br.com.buweis.model.AlunoProva;
import br.com.buweis.model.Assunto;
import br.com.buweis.model.Coordenador;
import br.com.buweis.model.Disciplina;
import br.com.buweis.model.Docente;
import br.com.buweis.model.Prova;
import br.com.buweis.model.ProvaAssunto;
import br.com.buweis.model.ProvaDisciplina;
import br.com.buweis.model.ProvaQuestao;
import br.com.buweis.model.ProvaTurma;
import br.com.buweis.model.Questao;
import br.com.buweis.model.Resposta;
import br.com.buweis.model.Turma;

@Controller
public class ProvaController {
	private final DisciplinaDAO disciplinaDAO;
	private final AssuntoDAO assuntoDAO;
	private final ProvaDAO provaDAO;
	private final QuestaoDAO questaoDAO;
	private final ProvaDisciplinaDAO provaDisciplinaDAO;
	private final ProvaAssuntoDAO provaAssuntoDAO;
	private final ProvaQuestaoDAO provaQuestaoDAO;
	private final ProvaTurmaDAO provaTurmaDAO;
	private final AlunoProvaDAO alunoProvaDAO;
	private final TurmaDAO turmaDAO;
	private final AlunoDAO alunoDAO;
	private final AlternativaDAO alternativaDAO;
	private final DocenteDAO docenteDAO;
	private final RespostaDAO respostaDAO;

	/**
	 * 
	 * CONSTRUTOR DA CLASSE ProvaController
	 * 
	 * @param disciplinaDAO
	 * @param assuntoDAO
	 * @param provaDAO
	 * @param questaoDAO
	 * @param provaDisciplinaDAO
	 * @param provaAssuntoDAO
	 * @param provaQuestaoDAO
	 */
	@Autowired
	public ProvaController(DisciplinaDAO disciplinaDAO, AssuntoDAO assuntoDAO,
			ProvaDAO provaDAO, QuestaoDAO questaoDAO,
			ProvaDisciplinaDAO provaDisciplinaDAO,
			ProvaAssuntoDAO provaAssuntoDAO, ProvaQuestaoDAO provaQuestaoDAO,
			TurmaDAO turmaDAO, AlunoDAO alunoDAO, ProvaTurmaDAO provaTurmaDAO,
			AlunoProvaDAO alunoProvaDAO, AlternativaDAO alternativaDAO,
			DocenteDAO docenteDAO, RespostaDAO respostaDAO) {
		this.disciplinaDAO = disciplinaDAO;
		this.assuntoDAO = assuntoDAO;
		this.provaDAO = provaDAO;
		this.questaoDAO = questaoDAO;
		this.provaDisciplinaDAO = provaDisciplinaDAO;
		this.provaAssuntoDAO = provaAssuntoDAO;
		this.provaQuestaoDAO = provaQuestaoDAO;
		this.turmaDAO = turmaDAO;
		this.alunoDAO = alunoDAO;
		this.provaTurmaDAO = provaTurmaDAO;
		this.alunoProvaDAO = alunoProvaDAO;
		this.alternativaDAO = alternativaDAO;
		this.docenteDAO = docenteDAO;
		this.respostaDAO = respostaDAO;
	}

	/**
	 * 
	 * P�GINA PARA RETORNAR AS OP��ES DE PROVA (MANUAL E AUTOM�TICA)
	 * 
	 * @return
	 */
	@RequestMapping("opcaoProva")
	public String opcaoProva() {
		return "opcaoProva";
	}

	/**
	 * 
	 * P�GINA CASO CLIQUE EM PROVA AUTOM�TICA
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("provaAutomatica")
	public String provaAutomatica(Model model) {
		// enviar models de disciplinas, assuntos, os ids dos assuntos e motivo
		// Obs.: Motivo servir� para informar os erros na hora de cadastrar uma
		// prova
		model.addAttribute("disciplinas", disciplinaDAO.listar());
		model.addAttribute("assuntos", assuntoDAO.listar());
		model.addAttribute("motivo", null);
		String idAssuntos = "";
		for (int i = 0; i < assuntoDAO.listar().size(); i++) {
			// armazena os assuntos na vari�vel
			idAssuntos += assuntoDAO.listar().get(i).getIdAssunto() + " ";
		}
		// Retira o espa�o no final da String
		idAssuntos = idAssuntos.substring(0, (idAssuntos.length() - 1));
		model.addAttribute("idAssuntos", idAssuntos);

		String idDisciplinas = "";
		for (int i = 0; i < disciplinaDAO.listar().size(); i++) {
			// armazena os assuntos na vari�vel
			idDisciplinas += disciplinaDAO.listar().get(i).getIdDisciplina()
					+ " ";
		}
		// Retira o espa�o no final da String
		idDisciplinas = idDisciplinas
				.substring(0, (idDisciplinas.length() - 1));
		model.addAttribute("idDisciplinas", idDisciplinas);
		return "formularioProvaAutomatica";
	}

	/**
	 * 
	 * P�GINA CASO CLIQUE EM PROVA MANUAL
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("provaManual")
	public String provaManual(Model model) {

		String idAssuntos = "";
		for (int i = 0; i < assuntoDAO.listar().size(); i++) {
			// armazena os assuntos na vari�vel
			idAssuntos += assuntoDAO.listar().get(i).getIdAssunto() + " ";
		}
		// Retira o espa�o no final da String
		idAssuntos = idAssuntos.substring(0, (idAssuntos.length() - 1));
		model.addAttribute("idAssuntos", idAssuntos);

		String idDisciplinas = "";
		for (int i = 0; i < disciplinaDAO.listar().size(); i++) {
			// armazena os assuntos na vari�vel
			idDisciplinas += disciplinaDAO.listar().get(i).getIdDisciplina()
					+ " ";
		}
		// Retira o espa�o no final da String
		idDisciplinas = idDisciplinas
				.substring(0, (idDisciplinas.length() - 1));
		model.addAttribute("idDisciplinas", idDisciplinas);
		// enviar models de disciplinas, assuntos, os ids dos assuntos e motivo
		// Obs.: Motivo servir� para informar os erros na hora de cadastrar uma
		// prova
		model.addAttribute("disciplinas", disciplinaDAO.listar());
		model.addAttribute("assuntos", assuntoDAO.listar());
		model.addAttribute("motivo", null);

		return "formularioProvaManual";
	}

	/**
	 * 
	 * CADASTRO DE PROVA AUTOM�TICA E SUAS VALIDA��ES
	 * 
	 * @param prova
	 * @param provaDisciplina
	 * @param provaAssunto
	 * @param provaQuestao
	 * @param docente
	 * @param checkDisciplina
	 * @param checkAssunto
	 * @param dataDaProva
	 * @param horaDaProva
	 * @param tempoDeProva
	 * @param arquivo
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("provaAdicionaAutomatica")
	public String provaAdiciona(Prova prova, ProvaDisciplina provaDisciplina,
			ProvaAssunto provaAssunto, ProvaQuestao provaQuestao,
			Docente docente, String checkAssunto, String dataDaProva,
			String horaDaProva, String tempoDeProva, MultipartFile arquivo,
			HttpSession session, Model model) {

		try {

			/* CADASTRAR PROVA */

			// Se uma imagem n�o est� vazia, faz um set da imagem na model de
			// prova
			if (!arquivo.isEmpty()) {
				try {
					prova.setCabecalhoProva(arquivo.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// Recupera o docente da sess�o
			docente = (Docente) session.getAttribute("usuarioLogado");
			// Seta o docente em prova
			prova.setDocente(docente);

			// Cria o dateFormat para formatar a data
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			// Cria Date
			Date date;
			// Transforma a String em Date
			date = dateFormat.parse(dataDaProva);
			// Seta a data em prova
			prova.setDataRealizacaoProva(date);

			// Cria o LocalTime com a hora da prova
			LocalTime hour = LocalTime.parse(horaDaProva);
			// Seta a hora em prova
			prova.setHoraRealizacaoProva(hour);
			// Cria o LocalTime com o tempo da prova
			LocalTime time = LocalTime.parse(tempoDeProva);
			// Seta o tempo em prova
			prova.setTempoProva(time);
			// Caso queira mostrar a data em uma String, utilize esta linha
			// abaixo
			// String output = dateFormat.format(date);

			/* ASSUNTOS */

			// Faz um vetor para os assuntos que foram selecionados
			String[] vetor2 = checkAssunto.split(",");

			// total que quest�es que foram indicadas para o cadastro
			int totalQuestoes = prova.getQntdQuestoesProva();
			// Todos os assuntos selecionados
			int assuntosSelecionados = vetor2.length;
			// quantidade de quest�es por assunto
			int questoesPorAssunto = Integer
					.parseInt(String.valueOf(
							Math.floor(totalQuestoes / assuntosSelecionados))
							.substring(
									0,
									String.valueOf(
											Math.floor(totalQuestoes
													/ assuntosSelecionados))
											.length() - 2));

			// Lista do model ProvaAssunto
			List<ProvaAssunto> listaProvaAssunto = new ArrayList<ProvaAssunto>();

			// For de todos os assuntos selecionados para cadastrar
			for (int i = 0; i < vetor2.length; i++) {
				// Faz a inst�ncia de listas de quest�es
				List<Questao> questaoAux = new ArrayList<Questao>();
				List<Questao> questaoDisponivel = new ArrayList<Questao>();
				// seta quest�es dentro de quest�oAux
				questaoAux = questaoDAO.listarPorIdAssunto(Long
						.parseLong(vetor2[i]));
				// Fa um la�o de questaoAux
				for (int j = 0; j < questaoAux.size(); j++) {
					// Caso a quest�o ainda n�o tenha sido usada em uma prova
					if (String.valueOf(questaoAux.get(j).getUltimoUsoQuestao())
							.contains("null")) {
						// Adiciona a quest�o numa lista
						questaoDisponivel.add(questaoAux.get(j));
						// Caso a quest�o tenha sido usada em uma prova
					} else {
						// Faz a inst�ncia da classe Calendar e SImpleDateFormat
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat dataFormatada = new SimpleDateFormat(
								"yyyy-MM-dd");
						// Recupera a diferen�a em dias do �ltimo uso da quest�o
						// com a data atual
						int diferencaData = Integer.parseInt(questaoDAO
								.compararDatas(dataFormatada.format(cal
										.getTime()), String.valueOf(questaoAux
										.get(j).getUltimoUsoQuestao())));
						// Caso seja ou tenha se passado mais de 30 dias
						if (diferencaData >= 30) {
							// Adiciona a quest�o numa lista
							questaoDisponivel.add(questaoAux.get(j));
						}
					}
				}

				// Caso n�o exista nenhuma quest�o cadastrada com o assunto
				// informado ( size == 0)
				if (questaoDisponivel.size() == 0) {
					// motivo do n�o cadastramento
					model.addAttribute(
							"motivo",
							"N�o h� nenhuma quest�o de "
									+ assuntoDAO
											.buscarPorId(
													Long.parseLong(vetor2[i]))
											.getNomeAssunto().toLowerCase()
									+ ". Por favor, cadastre ou valide uma ou mais quest�es");
					return "N�o h� nenhuma quest�o de "
							+ assuntoDAO.buscarPorId(Long.parseLong(vetor2[i]))
									.getNomeAssunto().toLowerCase()
							+ ". Por favor, cadastre ou valide uma ou mais quest�es";

					// Caso n�o haja quest�es suficientes com o assunto
					// informado (menor do que cada quest�o por assunto)
				} else if (questaoDisponivel.size() < questoesPorAssunto) {
					// motivo do n�o cadastramento
					model.addAttribute(
							"motivo",
							"N�o h� quest�es suficientes para o assunto "
									+ assuntoDAO
											.buscarPorId(
													Long.parseLong(vetor2[i]))
											.getNomeAssunto().toLowerCase()
									+ ". Por favor, cadastre ou valide uma ou mais quest�es");
					return "N�o h� quest�es suficientes para o assunto "
							+ assuntoDAO.buscarPorId(Long.parseLong(vetor2[i]))
									.getNomeAssunto().toLowerCase()
							+ ". Por favor, cadastre ou valide uma ou mais quest�es";

				} else if (questoesPorAssunto == 0) {
					model.addAttribute(
							"motivo",
							"N�o h� quest�es suficientes por assunto. Por favor, cadastre/valide uma ou mais quest�es ou aumente a quantidade de quest�es");
					return "N�o h� quest�es suficientes por assunto. Por favor, cadastre/valide uma ou mais quest�es ou aumente a quantidade de quest�es";

				} else {
					// Lista de quest�es por assunto
					List<Questao> listaQuestao = questaoDisponivel;
					// vari�vel auxiliar para contagem de quest�es com a
					// dificuldade selecionada
					int aux = 0;
					// faz um la�o for para comparar se a dificuldade
					// selecionada cont�m no banco em quantidade suficiente
					for (int j = 0; j < listaQuestao.size(); j++) {
						if (listaQuestao.get(j).getDificuldadeQuestao()
								.equals(prova.getDificuldadeProva())) {
							aux += 1;
						}

					}
					// Se n�o existir quest�es suficientes da dificuldade
					// informada
					if (aux < questoesPorAssunto) {
						// motivo do n�o cadastramento
						model.addAttribute(
								"motivo",
								"N�o h� quest�es suficientes de dificuldade  "
										+ prova.getDificuldadeProva()
												.toLowerCase()
										+ " do assunto "
										+ assuntoDAO
												.buscarPorId(
														Long.parseLong(vetor2[i]))
												.getNomeAssunto().toLowerCase()
										+ ". Por favor, cadastre");
						return "N�o h� quest�es suficientes de dificuldade  "
								+ prova.getDificuldadeProva().toLowerCase()
								+ " do assunto "
								+ assuntoDAO
										.buscarPorId(Long.parseLong(vetor2[i]))
										.getNomeAssunto().toLowerCase()
								+ ". Por favor, cadastre";
					}
					// vari�vel auxiliar para contagem de quest�es com o tipo
					// selecionado
					int aux2 = 0;
					// faz um la�o for para comparar se o tipo
					// selecionado cont�m no banco em quantidade suficiente
					for (int j = 0; j < listaQuestao.size(); j++) {
						// Caso a prova n�o tenha sido marcada como misto
						if (!prova.getTipoProva().equals("Misto")) {
							if (listaQuestao.get(j).getTipoQuestao()
									.equals(prova.getTipoProva())) {
								aux2 += 1;
							}
							// Caso tenha sido marcada como misto
						} else {
							aux2 = questoesPorAssunto;
						}

					}
					// Se n�o existir quest�es suficientes do tipo informado
					if (aux2 < questoesPorAssunto) {
						// motivo do n�o cadastramento
						model.addAttribute(
								"motivo",
								"N�o h� quest�es suficientes do tipo  "
										+ prova.getTipoProva().toLowerCase()
										+ " do assunto "
										+ assuntoDAO
												.buscarPorId(
														Long.parseLong(vetor2[i]))
												.getNomeAssunto().toLowerCase()
										+ ". Por favor, cadastre");
						return "N�o h� quest�es suficientes do tipo  "
								+ prova.getTipoProva().toLowerCase()
								+ " do assunto "
								+ assuntoDAO
										.buscarPorId(Long.parseLong(vetor2[i]))
										.getNomeAssunto().toLowerCase()
								+ ". Por favor, cadastre";
					} else {

						// Instanciar ProvaAssunto
						provaAssunto = new ProvaAssunto();
						// Setar prova
						provaAssunto.setProva(prova);
						// Setar o assunto selecionado na p�gina
						provaAssunto.setAssunto(assuntoDAO.buscarPorId(Long
								.parseLong(vetor2[i])));
						// Setar o objeto dentro de uma lista de ProvaAssunto
						listaProvaAssunto.add(provaAssunto);

					}

				}
			}

			// Cria uma lista de Quest�es
			List<Questao> listaQuestao = new ArrayList<Questao>();
			// Faz um la�o for para ProvaAssunto no qual h� a prova e os
			// assuntos selecionados
			for (int i = 0; i < listaProvaAssunto.size(); i++) {
				// Faz a inst�ncia de ProvaDisciplina
				provaDisciplina = new ProvaDisciplina();

				// listaProvaAssunto.get(i).setProva(prova);

				// Seta a prova dentro de provaDisciplina
				provaDisciplina.setProva(listaProvaAssunto.get(i).getProva());
				// Seta a disciplina dentro de provaDisciplina
				provaDisciplina.setDisciplina(listaProvaAssunto.get(i)
						.getAssunto().getDisciplinaAssunto());

				// Cria uma lista de quest�es
				List<Questao> listaDeQuestoes = new ArrayList<Questao>();
				// Caso a prova seja do tipo Mista
				if (prova.getTipoProva().equals("Misto")) {
					// Seta quest�es mistas na lista de quest�es
					listaDeQuestoes = questaoDAO.listarLimitadoMisto(
							listaProvaAssunto.get(i).getAssunto()
									.getIdAssunto(),
							prova.getDificuldadeProva(), prova.getTipoProva(),
							questoesPorAssunto);
					// Caso n�o seja do tipo mista
				} else {
					// Setar valores de um �nico tipo
					listaDeQuestoes = questaoDAO.listarLimitado(
							listaProvaAssunto.get(i).getAssunto()
									.getIdAssunto(),
							prova.getDificuldadeProva(), prova.getTipoProva(),
							questoesPorAssunto);
				}

				// Para cada item da lista de quest�es
				for (int k = 0; k < listaDeQuestoes.size(); k++) {
					// Seta uma disciplina inteira nas quest�es
					listaDeQuestoes.get(k).setDisciplinaQuestao(
							disciplinaDAO.buscarPorId(listaDeQuestoes.get(k)
									.getDisciplinaQuestao().getIdDisciplina()));
					// Seta um assunto inteiro nas quest�es
					listaDeQuestoes.get(k).setAssuntoQuestao(
							assuntoDAO.buscarPorId(listaDeQuestoes.get(k)
									.getAssuntoQuestao().getIdAssunto()));
					// Buscar cada listas de quest�es de determinado assunto e
					// coloc�-la dentro de 'listaQuestao'
					listaQuestao.add(listaDeQuestoes.get(k));
				}

			}

			if (listaQuestao.size() < prova.getQntdQuestoesProva()) {
				model.addAttribute(
						"motivo",
						"N�o h� quest�es suficientes para serem utilizadas. Por favor, cadastre ou valide uma ou mais quest�es");
				return "N�o h� quest�es suficientes para serem utilizadas. Por favor, cadastre ou valide uma ou mais quest�es";
			}

			// Para cada quest�o dentro desta lista
			for (int i = 0; i < listaQuestao.size(); i++) {
				// Seta a prova dentro de provaQuestao
				provaQuestao.setProva(prova);
				// Seta a quest�o dentro de provaQuestao
				provaQuestao.setQuestao(listaQuestao.get(i));
				// provaQuestaoDAO.adicionar(provaQuestao);
			}

			// Setar valor na sess�o com conte�do de prova
			session.setAttribute("prova", prova);
			// Setar valor na sess�o com conte�do de listaProvaAssunto
			session.setAttribute("listaProvaAssunto", listaProvaAssunto);
			// Setar valor na sess�o com conte�do de listaQuestao
			session.setAttribute("listaQuestao", listaQuestao);
			// Setar valor na sess�o com conte�do de questoesPorAssunto
			session.setAttribute("questoesPorAssunto", questoesPorAssunto);
			// Setar valor na model com conte�do de listaQuestao
			model.addAttribute("listaQuestao", listaQuestao);
			// Setar valor na model com conte�do do size de listaQuestao
			model.addAttribute("listaQuestaoSize", listaQuestao.size());

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return "formularioProvaAutomatica2";
	}

	/**
	 * 
	 * CADASTRO DA PROVA INFORMANDO O PESO DE CADA QUEST�O
	 * 
	 * @param prova
	 * @param provaDisciplina
	 * @param provaAssunto
	 * @param provaQuestao
	 * @param questaoPeso
	 * @param session
	 * @return
	 */
	@RequestMapping("provaAdicionaAutomatica2")
	public String provaAdicionaAutomatica2(HttpSession session, Model model,
			Docente docente, String questaoPeso) {

		// Guarda os dados do usu�rio logado em docente
		docente = (Docente) session.getAttribute("usuarioLogado");

		// Coloca os pesos das quest�es numa sess�o
		session.setAttribute("questaoPeso", questaoPeso);

		// Caso n�o haja turmas cadastradas
		if (turmaDAO.listar().size() == 0) {
			// Retorna um model como motivo
			model.addAttribute("motivo",
					"N�o h� nenhuma turma dispon�vel. Por favor, cadastre");
			return "N�o h� nenhuma turma dispon�vel. Por favor, cadastre";
		}
		// Armazena num model as turmas e a quantidade de turmas
		model.addAttribute("turmas", turmaDAO.listarPorInstituicao(docente
				.getInstituicaoDocente().getIdInstituicao()));
		model.addAttribute(
				"turmasSize",
				turmaDAO.listarPorInstituicao(
						docente.getInstituicaoDocente().getIdInstituicao())
						.size());

		// Cria uma string de ids de turmas
		String turmasIds = "";
		for (int i = 0; i < turmaDAO.listarPorInstituicao(
				docente.getInstituicaoDocente().getIdInstituicao()).size(); i++) {
			// Armazena cada turma na string e separa por espa�o
			turmasIds += turmaDAO
					.listarPorInstituicao(
							docente.getInstituicaoDocente().getIdInstituicao())
					.get(i).getIdTurma()
					+ " ";
		}
		// Retira a v�rgula da �ltima linha da string
		turmasIds = turmasIds.substring(0, turmasIds.length() - 1);

		// Setar valor na model com conte�do de turmas
		model.addAttribute("turmasIds", turmasIds);

		return "formularioProvaAutomatica3";
	}

	/**
	 * 
	 * CADASTRO DA PROVA INFORMANDO AS TURMAS DE CADA PROVA
	 * 
	 * @param selecionadosTurma
	 * @param session
	 * @param model
	 * @param docente
	 * @return
	 */
	@RequestMapping("provaAdicionaAutomatica3")
	public String provaAdicionaAutomatica3(String selecionadosTurma,
			HttpSession session, Model model, Docente docente) {

		// Setar usu�rio logado dentro de docente
		docente = (Docente) session.getAttribute("usuarioLogado");

		// Criar uma lista de turmas
		List<Long> turmas = new ArrayList<Long>();
		// Recuperar as turmas selecionadas num vetor
		String[] vetor = selecionadosTurma.split(",");

		for (int i = 0; i < vetor.length; i++) {
			// Adiciona cada turma selecionada numa lista
			turmas.add(Long.parseLong(vetor[i]));
		}

		// Salvar a lista de turmas numa sess�o
		session.setAttribute("listaTurmasIds", turmas);

		// Caso n�o haja alunos cadastrados
		if (alunoDAO.listar().size() == 0) {
			model.addAttribute("motivo",
					"N�o h� nenhum aluno dispon�vel. Por favor, cadastre");
			return "N�o h� nenhum aluno dispon�vel. Por favor, cadastre";
		}

		// Cria uma lista de alunos e de alunos da turma
		List<Aluno> alunos = new ArrayList<Aluno>();
		List<Aluno> alunosTurma = null;

		for (int i = 0; i < turmas.size(); i++) {
			// Faz a inst�ncia de alunosTurma
			alunosTurma = new ArrayList<Aluno>();
			// Listar v�rios alunos por turma
			alunosTurma = alunoDAO.listarPorTurma(turmas.get(i).longValue(),
					docente.getInstituicaoDocente().getIdInstituicao());
			for (int j = 0; j < alunosTurma.size(); j++) {
				// Adicionar cada aluno na lista
				alunos.add(alunosTurma.get(j));
			}
		}

		for (int i = 0; i < alunos.size(); i++) {
			alunos.get(i).setTurmaAluno(
					turmaDAO.buscarPorId(alunos.get(i).getTurmaAluno()
							.getIdTurma()));
		}

		// Setar alunos e a quantidade deles no model
		model.addAttribute("alunos", alunos);
		model.addAttribute("alunosSize", alunos.size());

		// Criar string de ids de alunos
		String alunosIds = "";
		// Se existirem alunos
		if (alunos.size() != 0) {
			// La�o for da quantidade de alunos
			for (int i = 0; i < alunos.size(); i++) {
				// Armazena os ids dos alunos numa string
				alunosIds += alunos.get(i).getIdAluno() + " ";
			}

			// Retira o espa�o do �ltimo caractere
			alunosIds = alunosIds.substring(0, alunosIds.length() - 1);
		}

		// Seta os alunos dentro de um model
		model.addAttribute("alunosIds", alunosIds);

		return "formularioProvaAutomatica4";
	}

	/**
	 * 
	 * CADASTRO DA PROVA INFORMANDO OS ALUNOS DE CADA PROVA
	 * 
	 * 
	 * @param prova
	 * @param provaDisciplina
	 * @param provaAssunto
	 * @param provaQuestao
	 * @param provaTurma
	 * @param alunoProva
	 * @param docente
	 * @param questaoPeso
	 * @param selecionadosAluno
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("provaAdicionaAutomatica4")
	public String provaAdicionaAutomatica4(Prova prova,

	ProvaDisciplina provaDisciplina, ProvaAssunto provaAssunto,
			ProvaQuestao provaQuestao, ProvaTurma provaTurma,
			AlunoProva alunoProva, Docente docente, String questaoPeso,
			String selecionadosAluno, HttpSession session, Model model) {

		// Cria as turmas com o valor salvo na sess�o listaTurmasIds
		List<Long> listaTurmasIds = (List<Long>) session
				.getAttribute("listaTurmasIds");

		// Cria os alunos com o valor salvo do formul�rio
		List<Long> listaAlunosIds = new ArrayList<Long>();
		// Separa os ids dos alunos por v�rgula
		String[] vetorAlunosIds = selecionadosAluno.split(",");
		// Seta cada Id dentro de uma lista com la�o for
		for (int i = 0; i < vetorAlunosIds.length; i++) {
			listaAlunosIds.add(Long.parseLong(vetorAlunosIds[i]));
		}

		// Faz a inst�ncia de docente
		docente = (Docente) session.getAttribute("usuarioLogado");

		questaoPeso = (String) session.getAttribute("questaoPeso");

		// Faz a inst�ncia de listaProvaAssunto
		List<ProvaAssunto> listaProvaAssunto = new ArrayList<ProvaAssunto>();
		// Faz a inst�ncia de listaQuestao
		List<Questao> listaQuestao = new ArrayList<Questao>();

		// Cria prova com o valor salvo na sess�o prova = (Prova)
		prova = (Prova) session.getAttribute("prova");
		// Cria listaProvaAssunto com o valor salvo na sess�o
		listaProvaAssunto = (List<ProvaAssunto>) session
				.getAttribute("listaProvaAssunto");
		// Cria listaQuestao com o valor salvo na sess�o
		listaQuestao = (List<Questao>) session.getAttribute("listaQuestao");

		// Remover todos os valores salvos na sess�o depois de usado
		session.removeAttribute("prova");
		session.removeAttribute("listaProvaAssunto");
		session.removeAttribute("listaQuestao");
		session.removeAttribute("listaTurmasIds");

		// Adicionar prova
		prova.setAvaliativaProva(true);
		provaDAO.adicionar(prova);

		// Faz um for de cada item na listaProvaAssunto
		for (int i = 0; i < listaProvaAssunto.size(); i++) {
			// Faz a inst�ncia de provaDisciplina
			provaDisciplina = new ProvaDisciplina();

			// Setar prova
			listaProvaAssunto.get(i).setProva(prova);

			// Adiciona provaAssunto
			provaAssuntoDAO.adicionar(listaProvaAssunto.get(i));

			// Seta prova em ProvaDisciplina
			provaDisciplina.setProva(listaProvaAssunto.get(i).getProva());
			// Seta disciplina em provaDisciplina
			provaDisciplina.setDisciplina(listaProvaAssunto.get(i).getAssunto()
					.getDisciplinaAssunto()); // Adiciona
												// provaDisciplina
			provaDisciplinaDAO.adicionar(provaDisciplina);

		}

		// Cria uma lista de listaProvaQuestao
		List<ProvaQuestao> listaProvaQuestao = new ArrayList<ProvaQuestao>();

		// Recupera todos os valores dos pesos das quest�es
		String[] vetorPeso = questaoPeso.split(",");

		// Faz um for de cada item na listaQuestao
		for (int i = 0; i < listaQuestao.size(); i++) {
			// Faz a inst�ncia de provaQuestao
			provaQuestao = new ProvaQuestao();
			// Seta prova em provaQuestao
			provaQuestao.setProva(prova);
			// Seta quest�o em listaQuestao
			provaQuestao.setQuestao(listaQuestao.get(i));
			// Seta o peso de cada quest�o em pesoQuestao
			provaQuestao.setPesoQuestao(Double.parseDouble(vetorPeso[i]));

			// Cria uma string de alternativas
			String stringAlternativas = "";
			// Caso a prova seja Misto ou Objetiva
			if (provaQuestao.getQuestao().getTipoQuestao().equals("Misto")
					|| provaQuestao.getQuestao().getTipoQuestao()
							.equals("Objetiva")) {
				// Se existirem alternativas dentro de uma quest�o
				if (provaQuestao.getQuestao().getQntdAlternativaQuestao() > 0) {
					// Cria uma lista de alternativas e as alternativas
					// Selecionadas para a prova
					List<Alternativa> alternativas = new ArrayList<Alternativa>();
					List<Alternativa> alternativasSelecionadas = new ArrayList<Alternativa>();
					alternativas = alternativaDAO.listarPorQuestao(listaQuestao
							.get(i).getIdQuestao());

					// Embaralha as alternativas
					java.util.Collections.shuffle(alternativas);
					// Cria um contador
					int count = 0;
					for (int j = 0; j < alternativas.size(); j++) {
						// Se a alternativa for a correta
						if (alternativas.get(j).isCorretaAlternativa()) {
							// Adiciona dentro de uma lista
							alternativasSelecionadas.add(alternativas.get(j));
						}
						// Caso n�o tenha atingido o limite de alternativas por
						// quest�o
						if (count < prova.getQntdAlternativasQuestaoProva() - 1) {
							// Caso a alternativa n�o seja a correta
							if (!alternativas.get(j).isCorretaAlternativa()) {
								alternativasSelecionadas.add(alternativas
										.get(j));
								count += 1;
							}
						}
					}

					for (int j = 0; j < alternativasSelecionadas.size(); j++) {
						// Seta as alternativas dentro de uma string
						stringAlternativas += alternativasSelecionadas.get(j)
								.getIdAlternativa() + ",";
					}

					// Retira a v�rgula do �ltimo caractere
					stringAlternativas = stringAlternativas.substring(0,
							stringAlternativas.length() - 1);
				}
			}

			// Caso haja conte�do dentro da string
			if (!stringAlternativas.equals("")) {
				// Seta dentro de provaQUestao
				provaQuestao.setAlternativaQuestao(stringAlternativas);
			}

			// Adicionar provaQuestao dentro de uma lista de ProvaQuestao
			listaProvaQuestao.add(provaQuestao);
		}

		// Embaralhar a ordem das quest�es
		java.util.Collections.shuffle(listaProvaQuestao);
		// Fazer um la�o for para listaProvaQuestao
		for (int i = 0; i < listaProvaQuestao.size(); i++) {
			// Adiciona provaQuestao
			provaQuestaoDAO.adicionar(listaProvaQuestao.get(i));
		}

		// For para adicionar cada turma na tabela de provaTurma
		for (int i = 0; i < listaTurmasIds.size(); i++) {
			provaTurma = new ProvaTurma();
			provaTurma.setProva(prova);
			provaTurma.setTurma(turmaDAO.buscarPorId(listaTurmasIds.get(i)
					.longValue()));
			provaTurmaDAO.adicionar(provaTurma);
		}

		// For para adicionar cada aluno na tabela de provaAluno
		for (int i = 0; i < listaAlunosIds.size(); i++) {
			alunoProva = new AlunoProva();
			alunoProva.setProva(prova);
			alunoProva.setAluno(alunoDAO.buscarPorId(listaAlunosIds.get(i)));
			alunoProvaDAO.adicionar(alunoProva);
		}

		return "opcaoProva";

	}

	/**
	 * 
	 * CADASTRO DE PROVA MANUAL E SUAS VALIDA��ES
	 * 
	 * @param prova
	 * @param provaDisciplina
	 * @param provaAssunto
	 * @param provaQuestao
	 * @param docente
	 * @param checkAssunto
	 * @param dataDaProva
	 * @param horaDaProva
	 * @param tempoDeProva
	 * @param arquivo
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("provaAdicionaManual")
	public String provaAdicionaManual(Prova prova,
			ProvaDisciplina provaDisciplina, ProvaAssunto provaAssunto,
			ProvaQuestao provaQuestao, Docente docente, String checkAssunto,
			String dataDaProva, String horaDaProva, String tempoDeProva,
			MultipartFile arquivo, HttpSession session, Model model) {
		try {

			/* CADASTRAR PROVA */

			// Se uma imagem n�o est� vazia, faz um set da imagem na model de
			// prova
			if (!arquivo.isEmpty()) {
				try {
					prova.setCabecalhoProva(arquivo.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			// Recupera o docente da sess�o
			docente = (Docente) session.getAttribute("usuarioLogado");
			// Seta o docente em prova
			prova.setDocente(docente);

			// Cria o dateFormat para formatar a data
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			// Cria Date
			Date date;
			// Transforma a String em Date
			date = dateFormat.parse(dataDaProva);
			// Seta a data em prova
			prova.setDataRealizacaoProva(date);

			// Cria o LocalTime com a hora da prova
			LocalTime hour = LocalTime.parse(horaDaProva);
			// Seta a hora em prova
			prova.setHoraRealizacaoProva(hour);
			// Cria o LocalTime com o tempo da prova
			LocalTime time = LocalTime.parse(tempoDeProva);
			// Seta o tempo em prova
			prova.setTempoProva(time);
			// Caso queira mostrar a data em uma String, utilize esta linha
			// abaixo
			// String output = dateFormat.format(date);

			/* ASSUNTOS */

			// Faz um vetor para os assuntos que foram selecionados
			String[] vetor2 = checkAssunto.split(",");

			List<Questao> questaoDisponivel = new ArrayList<Questao>();

			// For de todos os assuntos selecionados para cadastrar
			for (int i = 0; i < vetor2.length; i++) {

				// Faz a inst�ncia de listas de quest�es
				List<Questao> questaoAux = new ArrayList<Questao>();
				// seta quest�es dentro de quest�oAux
				questaoAux = questaoDAO.listarPorIdAssunto(Long
						.parseLong(vetor2[i]));
				// Fa um la�o de questaoAux
				for (int j = 0; j < questaoAux.size(); j++) {
					// Caso a quest�o ainda n�o tenha sido usada em uma prova
					if (String.valueOf(questaoAux.get(j).getUltimoUsoQuestao())
							.contains("null")) {
						// Adiciona a quest�o numa lista
						questaoDisponivel.add(questaoAux.get(j));
						// Caso a quest�o tenha sido usada em uma prova
					} else {
						// Faz a inst�ncia da classe Calendar e SImpleDateFormat
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat dataFormatada = new SimpleDateFormat(
								"yyyy-MM-dd");
						// Recupera a diferen�a em dias do �ltimo uso da quest�o
						// com a data atual
						int diferencaData = Integer.parseInt(questaoDAO
								.compararDatas(dataFormatada.format(cal
										.getTime()), String.valueOf(questaoAux
										.get(j).getUltimoUsoQuestao())));
						// Caso seja ou tenha se passado mais de 30 dias
						if (diferencaData >= 30) {
							// Adiciona a quest�o numa lista
							questaoDisponivel.add(questaoAux.get(j));
						}
					}
				}

				// Caso n�o exista nenhuma quest�o cadastrada com o assunto
				// informado ( size == 0)
				if (questaoDisponivel.size() == 0) {
					// motivo do n�o cadastramento
					model.addAttribute(
							"motivo",
							"N�o h� nenhuma quest�o de "
									+ assuntoDAO
											.buscarPorId(
													Long.parseLong(vetor2[i]))
											.getNomeAssunto().toLowerCase()
									+ ". Por favor, cadastre ou valide uma ou mais quest�es");
					return "N�o h� nenhuma quest�o de "
							+ assuntoDAO.buscarPorId(Long.parseLong(vetor2[i]))
									.getNomeAssunto().toLowerCase()
							+ ". Por favor, cadastre ou valide uma ou mais quest�es";

					// Caso n�o haja quest�es suficientes com o assunto
					// informado (menor do que cada quest�o por assunto)
				}

			}

			// Cria uma lista de quest�es que servir�o para fazer a prova manual
			List<Questao> questoesManual = new ArrayList<Questao>();

			// Cria uma lista de quest�es para cada assunto
			List<Questao> questaoAssunto = new ArrayList<Questao>();

			// Cria uma lista de quest�es para cada assunto
			List<Questao> questoesManualFinal = new ArrayList<Questao>();

			// Seta uma lista de quest�es
			questaoAssunto = questaoDisponivel;

			// Cria uma lista de questoes
			List<Questao> questoes = new ArrayList<Questao>();
			questoes = questaoDAO.listar();
			// Cria contadores
			int countDificuldade = 0;
			int countTipo = 0;

			for (int i = 0; i < questoes.size(); i++) {
				// Caso tenha quest�es da mesma dificuldade da prova

				if (prova.getDificuldadeProva().equals(
						questoes.get(i).getDificuldadeQuestao())) {
					countDificuldade += 1;
				}
				// Caso tenha quest�es do mesmo tipo da prova
				if (prova.getTipoProva().equals(
						questoes.get(i).getTipoQuestao())) {
					countTipo += 1;
					// Caso a prova for do tipo Misto
				} else if (prova.getTipoProva().equals("Misto")) {
					countTipo += 1;
				}
			}
			// Caso n�o tenha quest�es com a dificuldade selecionada
			if (countDificuldade == 0) {
				model.addAttribute(
						"motivo",
						"N�o h� nenhuma quest�o de dificuldade "
								+ prova.getDificuldadeProva().toLowerCase()
								+ ". Por favor, cadastre ou valide uma ou mais quest�es");
				return "N�o h� nenhuma quest�o de dificuldade "
						+ prova.getDificuldadeProva().toLowerCase()
						+ ". Por favor, cadastre ou valide uma ou mais quest�es";
			} else {
				for (int i = 0; i < questaoDisponivel.size(); i++) {
					// Caso a dificuldade n�o seja igual a da prova
					if (!questaoDisponivel.get(i).getDificuldadeQuestao()
							.equals(prova.getDificuldadeProva())) {
						// Remove
						questaoAssunto.remove(i);
					}
				}
				questoesManual = questaoAssunto;
				questoesManualFinal = questoesManual;
			}
			// Caso n�o tenha quest�es do tipo selecionado e a prova seja do
			// tipo Misto
			if (countTipo == 0 && prova.getTipoProva().equals("Misto")) {

				model.addAttribute(
						"motivo",
						"N�o h� nenhuma quest�o de ambos os tipos. Por favor, cadastre ou valide uma ou mais quest�es");
				return "N�o h� nenhuma quest�o de ambos os tipos. Por favor, cadastre ou valide uma ou mais quest�es";
				// Caso o n�o haja somente quest�es do tipo selecionaso
			} else if (countTipo == 0) {
				model.addAttribute(
						"motivo",
						"N�o h� nenhuma quest�o de tipo "
								+ prova.getTipoProva().toLowerCase()
								+ ". Por favor, cadastre ou valide uma ou mais quest�es");
				return "N�o h� nenhuma quest�o de tipo "
						+ prova.getTipoProva().toLowerCase()
						+ ". Por favor, cadastre ou valide uma ou mais quest�es";
			} else {
				// Caso a prova n�o seja do tipo Misto
				if (!prova.getTipoProva().equals("Misto")) {
					for (int i = 0; i < questoesManual.size(); i++) {
						// Caso a prova n�o seja do tipo selecionado
						if (!questoesManual.get(i).getTipoQuestao()
								.equals(prova.getTipoProva())) {
							// Remove
							questoesManualFinal.remove(i);
						}
					}
				}

			}

			// Cria ums tring com os ids das quest�es
			String questoesIds = "";

			for (int i = 0; i < questoesManualFinal.size(); i++) {
				// Seta todos os ids dentro de uma string
				questoesIds += questoesManualFinal.get(i).getIdQuestao() + " ";
			}
			// Reita o espa�o do �ltimo caractere
			questoesIds = questoesIds.substring(0, questoesIds.length() - 1);

			// Coloca uma prova dentro da sess�o
			session.setAttribute("prova", prova);
			// Seta os ids da quest�o dentro de um model
			model.addAttribute("questoesManualIds", questoesIds);
			// Adiciona as quest�es manual e a quantidade dentro de um model
			model.addAttribute("questoesManual", questoesManualFinal);
			model.addAttribute("questoesManualSize", questoesManualFinal.size());

			// Seta prova dentro de uma model
			model.addAttribute("prova", prova);

		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return "formularioProvaManual2";
	}

	@RequestMapping("provaAdicionaManual2")
	public String provaAdicionaManual4(HttpSession session, Model model,
			Docente docente, String selecionadosQuestaoManual) {

		// Cria uma lista de quest�es
		List<Questao> questoes = new ArrayList<Questao>();
		// Recupera as quest�es selecionadas dentro de um vetor
		String[] vetor = selecionadosQuestaoManual.split(",");
		for (int i = 0; i < vetor.length; i++) {
			// Adiciona os valores selecionados dentro de uma lista
			questoes.add(questaoDAO.buscarPorId(Long.parseLong(vetor[i])));
		}

		// Para cada item da lista de quest�es
		for (int k = 0; k < questoes.size(); k++) {
			// Seta uma disciplina inteira nas quest�es
			questoes.get(k).setDisciplinaQuestao(
					disciplinaDAO.buscarPorId(questoes.get(k)
							.getDisciplinaQuestao().getIdDisciplina()));
			// Seta um assunto inteiro nas quest�es
			questoes.get(k).setAssuntoQuestao(
					assuntoDAO.buscarPorId(questoes.get(k).getAssuntoQuestao()
							.getIdAssunto()));
		}

		// Seta a lista de quest�es na sess�o
		session.setAttribute("questoes", questoes);
		// Seta as quest�es em um model
		model.addAttribute("questoes", questoes);

		return "formularioProvaManual3";

	}

	/*	*//**
	 * 
	 * CADASTRO DA PROVA INFORMANDO AS QUEST�ES SELECIONADAS
	 * 
	 * @param session
	 * @param model
	 * @param docente
	 * @param selecionadosQuestaoManual
	 * @return
	 */

	@RequestMapping("provaAdicionaManual3")
	public String provaAdicionaManual3(HttpSession session, Model model,
			Docente docente, String selecionadosQuestaoManual,
			String pesoQuestaoManual) {

		// Recupera o docente da sess�o
		docente = (Docente) session.getAttribute("usuarioLogado");

		// Seta os pesos das quest�es em uma sess�o
		session.setAttribute("pesoQuestaoManual", pesoQuestaoManual);

		// Seta as quest�es selecionadas em uma sess�o
		session.setAttribute("selecionadosQuestaoManual",
				selecionadosQuestaoManual);

		// Se n�o houver turmas cadastradas
		if (turmaDAO.listar().size() == 0) {
			model.addAttribute("motivo",
					"N�o h� nenhuma turma dispon�vel. Por favor, cadastre");
			return "N�o h� nenhuma turma dispon�vel. Por favor, cadastre";
		}

		// Setar turmas e as quantidades dentro de um model
		model.addAttribute("turmas", turmaDAO.listarPorInstituicao(docente
				.getInstituicaoDocente().getIdInstituicao()));
		model.addAttribute(
				"turmasSize",
				turmaDAO.listarPorInstituicao(
						docente.getInstituicaoDocente().getIdInstituicao())
						.size());

		// Cria uma string com os ids de turmas
		String turmasIds = "";
		for (int i = 0; i < turmaDAO.listarPorInstituicao(
				docente.getInstituicaoDocente().getIdInstituicao()).size(); i++) {
			// Seta todos os ids dentro da string
			turmasIds += turmaDAO
					.listarPorInstituicao(
							docente.getInstituicaoDocente().getIdInstituicao())
					.get(i).getIdTurma()
					+ " ";
		}
		// Retira o espa�o do �ltimo caractere
		turmasIds = turmasIds.substring(0, turmasIds.length() - 1);

		// Seta os ids das turmas dentro de um model
		model.addAttribute("turmasIds", turmasIds);

		return "formularioProvaManual4";
	}

	/**
	 * 
	 * CADASTRO DE PROVA INFORMANDO AS TURMAS SELECIONADAS
	 * 
	 * @param selecionadosTurma
	 * @param session
	 * @param model
	 * @param docente
	 * @return
	 */

	@RequestMapping("provaAdicionaManual4")
	public String provaAdicionaManual4(String selecionadosTurma,
			HttpSession session, Model model, Docente docente) {

		// Seta o docente pegando de uma sess�o
		docente = (Docente) session.getAttribute("usuarioLogado");
		// Cria uma lista de turmas
		List<Long> turmas = new ArrayList<Long>();
		// Cria um vetor das turmas selecionadas
		String[] vetor = selecionadosTurma.split(",");

		for (int i = 0; i < vetor.length; i++) {
			// Adiciona todas as turmas selecionadas em uma lista
			turmas.add(Long.parseLong(vetor[i]));
		}

		// Seta a lista de turmas em uma sess�o
		session.setAttribute("listaTurmasIds", turmas);

		// Caso n�o haja alunos
		if (alunoDAO.listar().size() == 0) {
			model.addAttribute("motivo",
					"N�o h� nenhum aluno dispon�vel. Por favor, cadastre");
			return "N�o h� nenhum aluno dispon�vel. Por favor, cadastre";
		}

		// Cria listas de alunos e alunosTurma
		List<Aluno> alunos = new ArrayList<Aluno>();
		List<Aluno> alunosTurma = null;

		for (int i = 0; i < turmas.size(); i++) {
			// Seta os alunos de cada turma
			alunosTurma = new ArrayList<Aluno>();
			alunosTurma = alunoDAO.listarPorTurma(turmas.get(i).longValue(),
					docente.getInstituicaoDocente().getIdInstituicao());
			for (int j = 0; j < alunosTurma.size(); j++) {
				// Adiciona todos os alunos dentro de uma lista
				alunos.add(alunosTurma.get(j));
			}
		}

		for (int i = 0; i < alunos.size(); i++) {
			alunos.get(i).setTurmaAluno(
					turmaDAO.buscarPorId(alunos.get(i).getTurmaAluno()
							.getIdTurma()));
		}

		// Seta os alunos e a sua quantidade dentro de um model
		model.addAttribute("alunos", alunos);
		model.addAttribute("alunosSize", alunos.size());

		// Cria uma string com os ids dos alunos
		String alunosIds = "";
		// Caso os haja alunos
		if (alunos.size() != 0) {
			for (int i = 0; i < alunos.size(); i++) {
				// Seta os ids dos alunos dentro de uma string
				alunosIds += alunos.get(i).getIdAluno() + " ";
			}

			// Retira o espa�o do �ltimo caractere
			alunosIds = alunosIds.substring(0, alunosIds.length() - 1);
		}

		// Seta os ids dos alunos dentro de um model
		model.addAttribute("alunosIds", alunosIds);

		return "formularioProvaManual5";
	}

	/**
	 * 
	 * CADASTRO DA PROVA INFORMANDO OS ALUNOS DE CADA PROVA
	 * 
	 * @param prova
	 * @param provaDisciplina
	 * @param provaAssunto
	 * @param provaQuestao
	 * @param provaTurma
	 * @param alunoProva
	 * @param docente
	 * @param questaoPeso
	 * @param selecionadosAluno
	 * @param session
	 * @param model
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("provaAdicionaManual5")
	public String provaAdicionaManual5(Prova prova,
			ProvaDisciplina provaDisciplina, ProvaAssunto provaAssunto,
			ProvaQuestao provaQuestao, ProvaTurma provaTurma,
			AlunoProva alunoProva, Docente docente, String questaoPeso,
			String selecionadosAluno, HttpSession session, Model model) {

		// Seta prova recuperada pela sess�o
		prova = (Prova) session.getAttribute("prova");
		// Remove prova da sess�o
		session.removeAttribute("prova");

		// Seta os pesos das quest�es da sess�o

		questaoPeso = (String) session.getAttribute("pesoQuestaoManual");
		// Remove questaoPeso da sess�o
		session.removeAttribute("pesoQuestaoManual");
		// Insere os pesos em um vetor
		String[] vetorPeso = questaoPeso.split(",");

		// Cria as turmas com o valor salvo na sess�o listaTurmasIds
		List<Long> listaTurmasIds = (List<Long>) session
				.getAttribute("listaTurmasIds");

		// Cria os alunos com o valor salvo do formul�rio
		List<Long> listaAlunosIds = new ArrayList<Long>();
		// Separa os ids dos alunos por v�rgula
		String[] vetorAlunosIds = selecionadosAluno.split(",");
		// Seta cada Id dentro de uma lista com la�o for
		for (int i = 0; i < vetorAlunosIds.length; i++) {
			listaAlunosIds.add(Long.parseLong(vetorAlunosIds[i]));
		}

		// Cria uma lista de quest�es da sess�o
		List<Questao> questoes = (List<Questao>) session
				.getAttribute("questoes");
		// Remove as quest�es da sess�o
		session.removeAttribute("questoes");

		// Seta a quantidade de quest�es em prova
		prova.setQntdQuestoesProva(questoes.size());

		prova.setAvaliativaProva(true);
		// Adicionar prova
		provaDAO.adicionar(prova);

		// For para adicionar cada turma na tabela de provaTurma
		for (int i = 0; i < listaTurmasIds.size(); i++) {
			provaTurma = new ProvaTurma();
			provaTurma.setProva(prova);
			provaTurma.setTurma(turmaDAO.buscarPorId(listaTurmasIds.get(i)
					.longValue()));
			provaTurmaDAO.adicionar(provaTurma);
		}

		// For para adicionar cada aluno na tabela de provaAluno
		for (int i = 0; i < listaAlunosIds.size(); i++) {
			alunoProva = new AlunoProva();
			alunoProva.setProva(prova);
			alunoProva.setAluno(alunoDAO.buscarPorId(listaAlunosIds.get(i)));
			alunoProvaDAO.adicionar(alunoProva);
		}

		// For para adicionar cada questao na tabela de provaQuestao
		for (int i = 0; i < questoes.size(); i++) {
			provaQuestao = new ProvaQuestao();
			provaQuestao.setQuestao(questoes.get(i));
			provaQuestao.setProva(prova);
			provaQuestao.setPesoQuestao(Double.parseDouble(vetorPeso[i]));

			// Cria uma string com as alternativas
			String stringAlternativas = "";
			// Caso a prova seja do tipo Misto ou Objetiva
			if (provaQuestao.getQuestao().getTipoQuestao().equals("Misto")
					|| provaQuestao.getQuestao().getTipoQuestao()
							.equals("Objetiva")) {
				// Caso existam alternativas em provaQuestao
				if (provaQuestao.getQuestao().getQntdAlternativaQuestao() > 0) {

					// Cria listas de alternativas e alternativasSelecionadas
					List<Alternativa> alternativas = new ArrayList<Alternativa>();
					List<Alternativa> alternativasSelecionadas = new ArrayList<Alternativa>();
					alternativas = alternativaDAO.listarPorQuestao(questoes
							.get(i).getIdQuestao());

					// Embaralha as alternativas
					java.util.Collections.shuffle(alternativas);
					int count = 0;
					for (int j = 0; j < alternativas.size(); j++) {
						// Caso a alternativa seja correta
						if (alternativas.get(j).isCorretaAlternativa()) {
							// Adiciona numa lista
							alternativasSelecionadas.add(alternativas.get(j));
						}
						// Caso N�o atinja a quantidade de alternativas por
						// quest�o informadas
						if (count < prova.getQntdAlternativasQuestaoProva() - 1) {
							// Caso a alternativa n�o seja correta
							if (!alternativas.get(j).isCorretaAlternativa()) {
								// Adiciona numa lista
								alternativasSelecionadas.add(alternativas
										.get(j));
								count += 1;
							}
						}
					}

					for (int j = 0; j < alternativasSelecionadas.size(); j++) {
						// Adiciona todas as alternativas dentro de uma string
						stringAlternativas += alternativasSelecionadas.get(j)
								.getIdAlternativa() + ",";
					}

					// Se n�o for vazia
					if (stringAlternativas.length() > 0) {
						// Retira a v�rgula do �ltimo caractere
						stringAlternativas = stringAlternativas.substring(0,
								stringAlternativas.length() - 1);
					}

				}
			}

			// Caso a string n�o esteja vazia
			if (!stringAlternativas.equals("")) {
				provaQuestao.setAlternativaQuestao(stringAlternativas);
			}

			// Adiciona provaQuestao
			provaQuestaoDAO.adicionar(provaQuestao);
		}

		// Cria uma lista de assuntosDaQuestao
		List<Long> assuntosDaQuestao = new ArrayList<Long>();
		List<Questao> questoesAssuntosDisponiveis = new ArrayList<Questao>();
		int count = 0;
		for (int i = 0; i < questoes.size(); i++) {
			if (questoesAssuntosDisponiveis.size() > 0) {

				for (int j = 0; j < questoesAssuntosDisponiveis.size(); j++) {
					if (questoes
							.get(i)
							.getAssuntoQuestao()
							.getNomeAssunto()
							.equals(questoesAssuntosDisponiveis.get(j)
									.getAssuntoQuestao().getNomeAssunto())) {
						count += 1;
					}
					if (j == (questoesAssuntosDisponiveis.size() - 1)) {
						if (count == 0) {
							questoesAssuntosDisponiveis.add(questoes.get(i));
						} else {
							count = 0;
						}
					}

				}

			} else {
				questoesAssuntosDisponiveis.add(questoes.get(i));
			}
		}

		for (int i = 0; i < questoesAssuntosDisponiveis.size(); i++) {
			assuntosDaQuestao.add(questoesAssuntosDisponiveis.get(i)
					.getAssuntoQuestao().getIdAssunto());
		}

		// For para adicionar cada questao na tabela de provaAssunto
		for (int i = 0; i < assuntosDaQuestao.size(); i++) {
			provaAssunto.setProva(prova);
			provaAssunto.setAssunto(assuntoDAO.buscarPorId(assuntosDaQuestao
					.get(i).longValue()));
			provaAssuntoDAO.adicionar(provaAssunto);
		}

		// Cria uma lista de disciplinasDaQuestao
		List<Long> disciplinasDaQuestao = new ArrayList<Long>();
		count = 0;
		for (int i = 0; i < assuntosDaQuestao.size(); i++) {
			// Caso a lista esteja vazia
			if (disciplinasDaQuestao.size() > 0) {
				for (int j = 0; j < disciplinasDaQuestao.size(); j++) {
					// Caso n�o tenha as disciplinas das quest�es dentro da
					// lista
					if (assuntoDAO
							.buscarPorId(assuntosDaQuestao.get(i).longValue())
							.getDisciplinaAssunto().getIdDisciplina()
							.equals(disciplinasDaQuestao.get(j).longValue())) {

						count += 1;
					}
					if (j == (disciplinasDaQuestao.size() - 1)) {
						if (count == 0) {
							disciplinasDaQuestao.add(assuntoDAO
									.buscarPorId(
											assuntosDaQuestao.get(i)
													.longValue())
									.getDisciplinaAssunto().getIdDisciplina());
						} else {
							count = 0;
						}
					}
				}
			} else {
				// Adiciona os ids dentro da lista
				disciplinasDaQuestao.add(assuntoDAO
						.buscarPorId(assuntosDaQuestao.get(i).longValue())
						.getDisciplinaAssunto().getIdDisciplina());
			}

		}

		// For para adicionar cada questao na tabela de provaDisciplina
		for (int i = 0; i < disciplinasDaQuestao.size(); i++) {
			provaDisciplina.setProva(prova);
			provaDisciplina.setDisciplina(disciplinaDAO
					.buscarPorId(disciplinasDaQuestao.get(i).longValue()));
			provaDisciplinaDAO.adicionar(provaDisciplina);
		}

		return "opcaoProva";

	}

	@RequestMapping("listaProvas")
	public String listaProvas(HttpSession session, Model model,
			Docente docente, Assunto assunto) {
		// Recupera o docente da sess�o
		docente = (Docente) session.getAttribute("usuarioLogado");
		// Recupera uma lista de provas da instituicao
		List<Prova> provas = provaDAO.listarPorInstituicaoDocente(docente
				.getInstituicaoDocente().getIdInstituicao(), docente
				.getIdDocente());
		List<Prova> provasDisponiveis = new ArrayList<Prova>();
		List<AlunoProva> alunosProvas = null;

		for (int i = 0; i < provas.size(); i++) {
			alunosProvas = new ArrayList<AlunoProva>();
			alunosProvas = alunoProvaDAO.listarPorProva(provas.get(i)
					.getIdProva());
			if (alunosProvas.size() > 0) {

				for (int j = 0; j < alunosProvas.size(); j++) {

					if (alunosProvas.get(j).getDataRealizacaoProva() == null) {
						provasDisponiveis.add(provas.get(i));
					}
				}

			}
		}

		// Cria uma lista de assuntos
		List<String> assuntos = new ArrayList<String>();
		// Cria uma lista de ProvaAssunto
		List<ProvaAssunto> provasAssunto;
		// Cria uma lista de datasProva
		List<Date> datasProva = new ArrayList<Date>();
		// Cria uma lista de tiposProva
		List<String> tiposProva = new ArrayList<String>();
		// Cria uma lista de ids da prova
		List<String> idsProva = new ArrayList<String>();

		// Adiciona os nomes dos assuntos dentro de uma string
		String assuntos2 = "";
		// Caso tenha conte�do dentro da prova
		if (provasDisponiveis.size() > 0) {
			// Faz um la�o da prova
			for (int i = 0; i < provasDisponiveis.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia do assunto
				assunto = new Assunto();
				// Faz a inst�ncia de ProvaAssunto
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Lista os assuntos pela prova
				provasAssunto = provaAssuntoDAO
						.listarPorProva(provasDisponiveis.get(i).getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto completo enviando o seu id
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome do assunto em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Envia o nome para a string assuntos2
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Se tiver conte�do dentro da string
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string na lista
				assuntos.add(assuntos2);

				// Adiciona a data dentro da lista
				datasProva.add(provasDisponiveis.get(i)
						.getDataRealizacaoProva());
				// Adiciona o tipo dentro da lista
				tiposProva.add(provasDisponiveis.get(i).getTipoProva());
				// Adicionaos ids da prova dentro da lista
				idsProva.add(String.valueOf(provasDisponiveis.get(i)
						.getIdProva()));

			}
		}

		// Envia os valores para a p�gina jsp
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("provas", provasDisponiveis);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("provaSize", provasDisponiveis.size());

		return "listaProvas";
	}

	@RequestMapping("listaProvasDocentes")
	public String listaProvasDocentes(HttpSession session, Model model,
			Coordenador coordenador, Assunto assunto) {
		// Recupera o docente da sess�o
		coordenador = (Coordenador) session.getAttribute("usuarioLogado");
		// Recupera uma lista de provas da instituicao
		List<Prova> provas = provaDAO.listarPorInstituicao(coordenador
				.getInstituicaoCoordenador().getIdInstituicao());
		List<Prova> provasDisponiveis = new ArrayList<Prova>();
		List<AlunoProva> alunosProvas = null;

		for (int i = 0; i < provas.size(); i++) {
			alunosProvas = new ArrayList<AlunoProva>();
			alunosProvas = alunoProvaDAO.listarPorProva(provas.get(i)
					.getIdProva());
			if (alunosProvas.size() > 0) {

				for (int j = 0; j < alunosProvas.size(); j++) {

					if (alunosProvas.get(j).getDataRealizacaoProva() == null) {
						provasDisponiveis.add(provas.get(i));
					}
				}

			}
		}

		// Cria uma lista de assuntos
		List<String> assuntos = new ArrayList<String>();
		// Cria uma lista de ProvaAssunto
		List<ProvaAssunto> provasAssunto;
		// Cria uma lista de datasProva
		List<Date> datasProva = new ArrayList<Date>();
		// Cria uma lista de tiposProva
		List<String> tiposProva = new ArrayList<String>();
		// Cria uma lista de ids da prova
		List<String> idsProva = new ArrayList<String>();

		// Adiciona os nomes dos assuntos dentro de uma string
		String assuntos2 = "";
		// Caso tenha conte�do dentro da prova
		if (provasDisponiveis.size() > 0) {
			// Faz um la�o da prova
			for (int i = 0; i < provasDisponiveis.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia do assunto
				assunto = new Assunto();
				// Faz a inst�ncia de ProvaAssunto
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Lista os assuntos pela prova
				provasAssunto = provaAssuntoDAO
						.listarPorProva(provasDisponiveis.get(i).getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto completo enviando o seu id
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome do assunto em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Envia o nome para a string assuntos2
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Se tiver conte�do dentro da string
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string na lista
				assuntos.add(assuntos2);

				// Adiciona a data dentro da lista
				datasProva.add(provasDisponiveis.get(i)
						.getDataRealizacaoProva());
				// Adiciona o tipo dentro da lista
				tiposProva.add(provasDisponiveis.get(i).getTipoProva());
				// Adicionaos ids da prova dentro da lista
				idsProva.add(String.valueOf(provasDisponiveis.get(i)
						.getIdProva()));

			}
		}

		// Envia os valores para a p�gina jsp
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("provas", provasDisponiveis);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("provaSize", provasDisponiveis.size());

		return "listaProvasDocentes";
	}

	@RequestMapping("mostraProva")
	public String mostraProva(Prova prova, ProvaDisciplina provaDisciplina,
			ProvaAssunto provaAssunto, ProvaTurma provaTurma,
			AlunoProva alunoProva, ProvaQuestao provaQuestao, Model model,
			HttpSession session) {

		// Recupera uma prova
		prova = provaDAO.buscarPorId(prova.getIdProva());
		// Seta um docente na prova
		prova.setDocente(docenteDAO.buscarPorId(prova.getDocente()
				.getIdDocente()));

		// Faz lista de quest�es, alternativas, provasDisciplinas,
		// provasAssuntos, provasTurmas, alunosProvas e provasQuestoes
		List<Questao> questoes = new ArrayList<Questao>();
		List<Alternativa> alternativas = new ArrayList<Alternativa>();
		List<ProvaDisciplina> provasDisciplinas = new ArrayList<ProvaDisciplina>();
		List<ProvaAssunto> provasAssuntos = new ArrayList<ProvaAssunto>();
		List<ProvaTurma> provasTurmas = new ArrayList<ProvaTurma>();
		List<AlunoProva> alunosProvas = new ArrayList<AlunoProva>();
		List<ProvaQuestao> provasQuestoes = new ArrayList<ProvaQuestao>();

		// Recupera as listas pela prova
		provasDisciplinas = provaDisciplinaDAO.listarPorProva(prova
				.getIdProva());

		provasAssuntos = provaAssuntoDAO.listarPorProva(prova.getIdProva());

		provasTurmas = provaTurmaDAO.listarPorProva(prova.getIdProva());

		alunosProvas = alunoProvaDAO.listarPorProva(prova.getIdProva());

		provasQuestoes = provaQuestaoDAO.listarPorProva(prova.getIdProva());

		// Faz um la�o for
		for (int i = 0; i < provasQuestoes.size(); i++) {
			// Adiciona as questoes na lista
			questoes.add(questaoDAO.buscarPorId(provasQuestoes.get(i)
					.getQuestao().getIdQuestao()));

			// Caso tenha alternativas
			if (provasQuestoes.get(i).getAlternativaQuestao() != null) {
				// Divide todas as alternativas pela v�rgula e armazena-as em um
				// vetor
				String[] vetor = provasQuestoes.get(i).getAlternativaQuestao()
						.split(",");
				// Faz um la�o for do vetor
				for (int j = 0; j < vetor.length; j++) {
					// Adiciona cada alternativa numa lista
					alternativas.add(alternativaDAO.buscarPorId(Long
							.parseLong(vetor[j])));
				}
			}
		}

		// Faz um la�o for de quest�es
		for (int i = 0; i < questoes.size(); i++) {
			// Seta as disciplinas em quest�es
			questoes.get(i).setDisciplinaQuestao(
					disciplinaDAO.buscarPorId(questoes.get(i)
							.getDisciplinaQuestao().getIdDisciplina()));
			// Seta os assuntos em quest�es
			questoes.get(i).setAssuntoQuestao(
					assuntoDAO.buscarPorId(questoes.get(i).getAssuntoQuestao()
							.getIdAssunto()));
		}

		// Seta a prova, provasAssuntos, quest�es na sess�o
		session.setAttribute("prova", prova);
		session.setAttribute("provasAssuntos", provasAssuntos);
		session.setAttribute("questoes", questoes);

		// Seta prova, alternativas, quest�es, provasDisciplinas,
		// provasAssuntos, provasTurmas, alunosProvas e provasQuestoes e
		// envia-as para a jsp
		model.addAttribute("prova", prova);
		model.addAttribute("alternativas", alternativas);
		model.addAttribute("questoes", questoes);
		model.addAttribute("provasDisciplinas", provasDisciplinas);
		model.addAttribute("provasAssuntos", provasAssuntos);
		model.addAttribute("provasTurmas", provasTurmas);
		model.addAttribute("alunosProvas", alunosProvas);
		model.addAttribute("provasQuestoes", provasQuestoes);

		return "mostraProva";
	}

	@RequestMapping("listaFazerProva")
	public String listaFazerProva(HttpSession session, Model model,
			AlunoProva alunoProva, Aluno aluno, Assunto assunto,
			Disciplina disciplina) {

		// Recupera o aluno logado
		aluno = (Aluno) session.getAttribute("usuarioLogado");
		// Faz a inst�ncia de uma lista de alunoProvas
		List<AlunoProva> alunoProvas = new ArrayList<AlunoProva>();
		// Faz a inst�ncia de uma lista de provas
		List<Prova> provas = new ArrayList<Prova>();
		List<Prova> provasTotal = new ArrayList<Prova>();

		// lista as provas que o aluno deve fazer
		alunoProvas = alunoProvaDAO.listarPorAlunoDataNull(aluno.getIdAluno());
		// Faz um la�o for de alunoProvas
		for (int i = 0; i < alunoProvas.size(); i++) {
			// Adiciona numa lista as provas
			provasTotal.add(provaDAO.buscarPorId(alunoProvas.get(i).getProva()
					.getIdProva()));
		}

		provas = provasTotal;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < provasTotal.size(); i++) {
			int diferencaData = Integer
					.parseInt(provaDAO.compararDatas(sdf.format(calendar
							.getTime()), String.valueOf(provasTotal.get(i)
							.getDataRealizacaoProva())));
			if (diferencaData > 0) {
				provas.remove(i);
			}

		}

		// Cria listas de assuntos, disciplinas, provasAssunto,
		// provasDisciplina, datasProva, tiposProva, idsProva
		List<String> assuntos = new ArrayList<String>();
		List<String> disciplinas = new ArrayList<String>();
		List<ProvaAssunto> provasAssunto;
		List<ProvaDisciplina> provasDisciplina;
		List<Date> datasProva = new ArrayList<Date>();
		List<String> tiposProva = new ArrayList<String>();
		List<String> idsProva = new ArrayList<String>();
		List<String> docenteProva = new ArrayList<String>();
		// Cria strings para armazenar os nomes dos assuntos e disciplinas
		String assuntos2 = "";
		String disciplinas2 = "";
		// Caso a prova tenha conte�do
		if (provas.size() > 0) {
			// faz um la�o de provas
			for (int i = 0; i < provas.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia de assunto e provaAssunto
				assunto = new Assunto();
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Seta uma lista de provasAssunto por prova
				provasAssunto = provaAssuntoDAO.listarPorProva(provas.get(i)
						.getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Concatena os nomes dos assuntos numa vari�vel
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Caso os assuntos tenham conte�do
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o da string
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string numa lista
				assuntos.add(assuntos2);

				disciplinas2 = "";
				// Faz a inst�ncia de assunto e provaDisciplina
				disciplina = new Disciplina();
				provasDisciplina = new ArrayList<ProvaDisciplina>();
				// Seta uma lista de provasAssunto por prova
				provasDisciplina = provaDisciplinaDAO.listarPorProva(provas
						.get(i).getIdProva());
				// Faz um la�o de provasDisciplina
				for (int j = 0; j < provasDisciplina.size(); j++) {
					// Recupera a disciplina
					disciplina = disciplinaDAO.buscarPorId(provasDisciplina
							.get(j).getDisciplina().getIdDisciplina());
					// Seta o nome em provasDisciplina
					provasDisciplina.get(j).getDisciplina()
							.setNomeDisciplina(disciplina.getNomeDisciplina());
					// Concatena os nomes das disciplinas numa vari�vel
					disciplinas2 += provasDisciplina.get(j).getDisciplina()
							.getNomeDisciplina()
							+ ";";
				}

				// Caso as disciplinas tenham conte�do
				if (disciplinas2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o da string
					disciplinas2 = disciplinas2.substring(0,
							disciplinas2.length() - 1);
				}
				// Adiciona a string numa lista
				disciplinas.add(disciplinas2);
				// Adiciona datas em uma lista
				datasProva.add(provas.get(i).getDataRealizacaoProva());
				// Adiciona tipos em uma lista
				tiposProva.add(provas.get(i).getTipoProva());
				// Adiciona os ids da prova em uma lista
				idsProva.add(String.valueOf(provas.get(i).getIdProva()));

				// Adiciona os docentes da prova em uma lista
				provas.get(i).setDocente(
						docenteDAO.buscarPorId(provas.get(i).getDocente()
								.getIdDocente()));
				docenteProva.add(provas.get(i).getDocente().getNomeDocente());

			}
		}

		// Seta idsProvas, provas, datas, tipos, assuntos, disciplinas,
		// provaSize e envia para o jsp
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("provas", provas);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("docentes", docenteProva);
		model.addAttribute("provaSize", provas.size());

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("dataAtual",
				String.valueOf(dataFormatada.format(cal.getTime())));

		return "listaFazerProva";
	}

	@RequestMapping("realizarProva")
	public String realizarProva(HttpSession session, Prova prova,
			ProvaQuestao provaQuestao, Alternativa alternativa) {
		// Recupera uma prova
		prova = provaDAO.buscarPorId(prova.getIdProva());
		// Faz a inst�ncia de ProvaQuestao e Alternativa
		List<ProvaQuestao> provaQuestoes = new ArrayList<ProvaQuestao>();
		List<Alternativa> alternativasQuestao = new ArrayList<Alternativa>();
		// cria a lista de provaQuestoes por Prova
		provaQuestoes = provaQuestaoDAO.listarPorProva(prova.getIdProva());

		// Embaralha provaQuestoes
		Collections.shuffle(provaQuestoes);

		// Faz um la�o de provaQuestoes
		for (int i = 0; i < provaQuestoes.size(); i++) {
			// Seta a prova
			provaQuestoes.get(i).setProva(prova);
			// Seta a quest�o
			provaQuestoes.get(i).setQuestao(
					questaoDAO.buscarPorId(provaQuestoes.get(i).getQuestao()
							.getIdQuestao()));
		}
		// Caso contenha alternativas
		if (provaQuestoes.get(0).getAlternativaQuestao() != null) {
			// Cria um vetor de string de alternativas
			String[] alternativas = provaQuestoes.get(0)
					.getAlternativaQuestao().split(",");
			// Para cada posi��o no vetor
			for (int i = 0; i < alternativas.length; i++) {
				// Adicione um valor numa lista
				alternativasQuestao.add(alternativaDAO.buscarPorId(Long
						.parseLong(alternativas[i])));
			}
		}

		// Embaralha alternativasQuestao
		Collections.shuffle(alternativasQuestao);

		// Seta idProva, quantidadeQUestoes, posicaoQuestao, provaQuestoes,
		// idQuestao, quantidadeQuestoes e alternativas numa sess�o
		session.setAttribute("idProva", prova.getIdProva());
		session.setAttribute("quantidadeQuestoes", provaQuestoes.size());
		session.setAttribute("posicaoQuestao", 0);
		session.setAttribute("provaQuestoes", provaQuestoes);
		session.setAttribute("idQuestao", provaQuestoes.get(0).getQuestao()
				.getIdQuestao());
		session.setAttribute("quantidadeQuestoes", provaQuestoes.size());
		session.setAttribute("alternativas", alternativasQuestao);

		return "opcaoTempoProva";
	}

	/* PAREI DE COMENTAR AQUI */
	@SuppressWarnings("unchecked")
	@RequestMapping("realizarPorTempoProva")
	public String realizarPorTempoProva(HttpSession session, Model model,
			Prova prova, ProvaQuestao provaQuestao, Alternativa alternativa) {

		// Faz a inst�ncia de prova
		prova = provaDAO.buscarPorId((Long) session.getAttribute("idProva"));

		// Faz a inst�ncia de provaQuestoes e alternativasQuestao recuperando da
		// sess�o
		List<ProvaQuestao> provaQuestoes = (List<ProvaQuestao>) session
				.getAttribute("provaQuestoes");
		List<Alternativa> alternativasQuestao = (List<Alternativa>) session
				.getAttribute("alternativas");

		// Seta os valores de posicaoQuestao, quantidadeQuestoes, provaQuestao,
		// alternativas, idQuestao para enviar � jsp
		model.addAttribute("posicaoQuestao", 0);
		model.addAttribute("quantidadeQuestoes", provaQuestoes.size());
		model.addAttribute("provaQuestao", provaQuestoes.get(0));
		model.addAttribute("alternativas", alternativasQuestao);
		model.addAttribute("idQuestao", provaQuestoes.get(0).getQuestao()
				.getIdQuestao());
		model.addAttribute("tempoDaProva", prova.getTempoProva().toString()
				+ ":00");

		return "formularioRealizarProva";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("realizarPorTempoQuestao")
	public String realizarPorTempoQuestao(HttpSession session, Model model,
			Prova prova, ProvaQuestao provaQuestao, Alternativa alternativa) {

		// Faz a inst�ncia de prova
		prova = provaDAO.buscarPorId((Long) session.getAttribute("idProva"));

		// Faz a inst�ncia de provaQuestoes e alternativasQuestao recuperando da
		// sess�o
		List<ProvaQuestao> provaQuestoes = (List<ProvaQuestao>) session
				.getAttribute("provaQuestoes");
		List<Alternativa> alternativasQuestao = (List<Alternativa>) session
				.getAttribute("alternativas");

		String[] tempoDaProva = (prova.getTempoProva().toString() + ":00")
				.split(":");
		int hora = 0;
		int minuto = 0;
		int segundo = 0;
		int totalSegundos = 0;
		hora = Integer.parseInt(tempoDaProva[0]) * 3600;
		minuto = Integer.parseInt(tempoDaProva[1]) * 60;
		segundo = Integer.parseInt(tempoDaProva[2]);
		totalSegundos = hora + minuto + segundo;

		NumberFormat doubleformat = NumberFormat.getInstance();
		doubleformat.setMinimumFractionDigits(0);
		doubleformat.setMaximumFractionDigits(0);

		String aux = String
				.valueOf(Math.floor(totalSegundos / provaQuestoes.size()))
				.substring(
						0,
						String.valueOf(
								Math.floor(totalSegundos / provaQuestoes.size()))
								.indexOf("."));

		int horaFinal = Integer.parseInt(aux);
		int minutoFinal = Integer.parseInt(aux);
		int segundoFinal = Integer.parseInt(aux);
		if (Integer.parseInt(aux) >= 3600) {
			minutoFinal = 0;
			segundoFinal = 0;
			horaFinal = Integer.valueOf(String.valueOf(
					Math.floor(Integer.parseInt(aux) / 3600)).substring(
					0,
					String.valueOf(Math.floor(Integer.parseInt(aux) / 3600))
							.indexOf(".")));

			minutoFinal = (Integer.parseInt(aux) % 3600);
			if (minutoFinal >= 60) {
				segundoFinal = 0;
				minutoFinal = Integer.valueOf(String.valueOf(
						Math.floor(minutoFinal / 60)).substring(
						0,
						String.valueOf(Math.floor(minutoFinal / 60)).indexOf(
								".")));
				segundoFinal = (minutoFinal % 60) / 60;
			} else {
				minutoFinal = 0;
				segundoFinal = Integer.valueOf(String.valueOf(
						Math.floor(minutoFinal / 60)).substring(0,
						String.valueOf(Math.floor(minutoFinal)).indexOf(".")));
			}

		} else if (Integer.parseInt(aux) >= 60) {
			horaFinal = 0;
			segundoFinal = 0;
			minutoFinal = Integer.valueOf(String.valueOf(
					Math.floor(Integer.parseInt(aux) / 60)).substring(
					0,
					String.valueOf(Math.floor(Integer.parseInt(aux) / 60))
							.indexOf(".")));
			segundoFinal = (Integer.parseInt(aux) % 60) / 60;

		} else {
			horaFinal = 0;
			minutoFinal = 0;
			segundoFinal = Integer.valueOf(String.valueOf(
					Math.floor(Integer.parseInt(aux))).substring(
					0,
					String.valueOf(Math.floor(Integer.parseInt(aux))).indexOf(
							".")));
		}

		String horaS = "";
		String minutoS = "";
		String segundoS = "";

		if (horaFinal < 10) {
			horaS = "0" + horaFinal;
		} else {
			horaS = String.valueOf(horaFinal);
		}

		if (minutoFinal < 10) {
			minutoS = "0" + minutoFinal;
		} else {
			minutoS = String.valueOf(minutoFinal);
		}

		if (segundoFinal < 10) {
			segundoS = "0" + segundoFinal;
		} else {
			segundoS = String.valueOf(segundoFinal);
		}

		// Seta os valores de posicaoQuestao, quantidadeQuestoes, provaQuestao,
		// alternativas, idQuestao para enviar � jsp
		model.addAttribute("provaPorQuestao", true);
		model.addAttribute("posicaoQuestao", 0);
		model.addAttribute("quantidadeQuestoes", provaQuestoes.size());
		model.addAttribute("provaQuestao", provaQuestoes.get(0));
		model.addAttribute("alternativas", alternativasQuestao);
		model.addAttribute("idQuestao", provaQuestoes.get(0).getQuestao()
				.getIdQuestao());
		model.addAttribute("tempoDaProva", horaS + ":" + minutoS + ":"
				+ segundoS);

		// return "formularioRealizarProva";
		return "quase indo...";
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping("salvarQuestao")
	public String salvarQuestao(HttpSession session, Model model,
			String respostaDissertativa, String respostaAlternativa,
			String tempoDaProva, Prova prova, Questao questao, Aluno aluno,
			Resposta objeto, Alternativa alternativa, Assunto assunto,
			Disciplina disciplina, AlunoProva alunoProva,
			ProvaQuestao provaQuestao, String anteriorOuProximo) {

		if (anteriorOuProximo.equals("Pr�ximo")) {
			// Recupera o aluno logado da sess�o
			aluno = (Aluno) session.getAttribute("usuarioLogado");
			// Recupera a prova da sess�o
			prova = provaDAO
					.buscarPorId((Long) session.getAttribute("idProva"));
			// Recupera a quest�o respondida
			questao = questaoDAO.buscarPorId(questao.getIdQuestao());
			// Recupera a posi��o da quest�o na sess�o
			int posicaoQuestao = (int) session.getAttribute("posicaoQuestao");
			// Recupera a quantidade de quest�es da sess�o
			int quantidadeQuestoes = (int) session
					.getAttribute("quantidadeQuestoes");
			// Faz a inst�ncia de ProvaQuestao e Alternativa
			List<ProvaQuestao> provaQuestoes = (List<ProvaQuestao>) session
					.getAttribute("provaQuestoes");
			List<Alternativa> alternativasQuestao = new ArrayList<Alternativa>();

			int vazia;
			if (respostaDissertativa == null || respostaDissertativa == "") {
				vazia = 0;
			} else {
				vazia = 1;
			}

			// Caso n�o tenha atingido o total de quest�es da prova
			// CASO FOR UMA QUEST�O DE DISSERTA��O
			if (vazia == 1) {

				// Salvar quest�o na sess�o
				session.setAttribute("idQuestao" + posicaoQuestao,
						questao.getIdQuestao());

				// Salvar disserta��o da quest�o
				session.setAttribute("resposta" + posicaoQuestao,
						respostaDissertativa);

				// Caso n�o tenha atingido o limite de quest�es respondidas
				if (posicaoQuestao < (quantidadeQuestoes - 1)) {
					// Caso a pr�xima quest�o tenha alternativas
					if (provaQuestoes.get(posicaoQuestao + 1)
							.getAlternativaQuestao() != null) {
						// Separa as alternativas salavr em um vetor de string
						String[] alternativas = provaQuestoes
								.get(posicaoQuestao + 1)
								.getAlternativaQuestao().split(",");
						// Faz um la�o para cada alternativa
						for (int i = 0; i < alternativas.length; i++) {
							// Adiciona cada alternativa numa lista
							alternativasQuestao.add(alternativaDAO
									.buscarPorId(Long
											.parseLong(alternativas[i])));
						}
					}

					model.addAttribute(
							"respostaD",
							session.getAttribute("resposta"
									+ (posicaoQuestao + 1)));
					model.addAttribute(
							"respostaA",
							session.getAttribute("resposta"
									+ (posicaoQuestao + 1)));

					// Salvar posi��o da quest�o
					session.setAttribute("posicaoQuestao", posicaoQuestao + 1);
					// Setar a posicaoQuestao, quantidadeQuestoes, provaQuestao,
					// alternativas, idQuestao na jsp
					model.addAttribute("posicaoQuestao", posicaoQuestao + 1);
					model.addAttribute("quantidadeQuestoes",
							provaQuestoes.size());
					model.addAttribute("provaQuestao",
							provaQuestoes.get(posicaoQuestao + 1));
					model.addAttribute("alternativas", alternativasQuestao);
					model.addAttribute("idQuestao",
							provaQuestoes.get(posicaoQuestao + 1).getQuestao()
									.getIdQuestao());

					String[] tempoProva = tempoDaProva.split(":");
					String hora = tempoProva[0];
					String minuto = tempoProva[1];
					String segundo = tempoProva[2];
					String valorFinal = "";
					if (Integer.parseInt(segundo) > 0) {

						if (Integer.parseInt(segundo) < 10) {
							segundo = "0" + segundo;
						}
						valorFinal = hora + ":" + minuto + ":" + segundo;
						model.addAttribute("tempoDaProva", valorFinal);
						return "formularioRealizarProva";
					}
					if (Integer.parseInt(segundo) == 0) {
						if (Integer.parseInt(minuto) > 0) {
							minuto = String
									.valueOf(Integer.parseInt(minuto) - 1);
							minuto = "0" + minuto;
							segundo = "60";

							/*
							 * if (Integer.parseInt(minuto) < 10) { minuto = "0"
							 * + minuto; }
							 */

							valorFinal = hora + ":" + minuto + ":" + segundo;
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
						if (Integer.parseInt(minuto) == 0) {
							if (Integer.parseInt(hora) > 0) {
								hora = String
										.valueOf(Integer.parseInt(hora) - 1);
								hora = "0" + hora;
								minuto = "59";
								segundo = "60";
								valorFinal = hora + ":" + minuto + ":"
										+ segundo;
								model.addAttribute("tempoDaProva", valorFinal);
								return "formularioRealizarProva";
							}
							if (Integer.parseInt(hora) == 0) {
								valorFinal = "00:00:00";
								model.addAttribute("tempoDaProva", valorFinal);
								return "formularioRealizarProva";
							}
						}
					}

					// Caso tenha atingido o limite de quest�es respondidas
				} else if (posicaoQuestao == (quantidadeQuestoes - 1)) {

					// Faz um la�o de provaQuestoes
					for (int i = 0; i < provaQuestoes.size(); i++) {
						if (session.getAttribute("idQuestao" + i) != null
								&& session.getAttribute("resposta" + i) != null) {
							// Faz a inst�ncia do objeto Resposta
							objeto = new Resposta();
							// Recupera o idQuestao da sess�o
							Long idQuestao = (Long) session
									.getAttribute("idQuestao" + i);
							// Recupera a resposta da sess�o
							String resposta = (String) session
									.getAttribute("resposta" + i);
							// Seta o aluno, prova e a quest�o que foi
							// respondida
							objeto.setAlunoResposta(aluno);
							objeto.setProvaResposta(prova);
							objeto.setQuestaoResposta(questaoDAO
									.buscarPorId(idQuestao));
							// Se existirem alternativas
							if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
								// Busca a alternativa correta daquela quest�o
								alternativa = alternativaDAO
										.buscarAlternativaCorreta(idQuestao);

								if (alternativa != null) {

									// Caso a alternativa seja respondida
									if (resposta != null) {
										// Caso a resposta seja correta
										if (alternativa.getIdAlternativa() == Integer
												.parseInt(resposta)) {
											// Salve como verdadeiro
											objeto.setCorretaAlternativaQuestao(true);
											// Caso a aalternativa n�o seja
											// respondida
										} else {
											objeto.setCorretaAlternativaQuestao(false);
										}
									}
								}
								// Se n�o existirem alternativas
							} else {
								// Caso a respoata n�o esteja em branco
								if (!resposta.isEmpty()) {
									// Salvar resposta dissertativa
									objeto.setDissertacaoQuestao(resposta);
								}
							}

							// Salvar resposta
							respostaDAO.adicionar(objeto);
						}
					}
				}

				// CASO FOR UMA QUEST�O DE ALTERNATIVA
			} else {

				// Salvar quest�o na sess�o
				session.setAttribute("idQuestao" + posicaoQuestao,
						questao.getIdQuestao());

				// Salvar disserta��o da quest�o
				session.setAttribute("resposta" + posicaoQuestao,
						respostaAlternativa);

				// Caso n�o tenha atingido o limite de quest�es respondidas
				if (posicaoQuestao < (quantidadeQuestoes - 1)) {
					// Caso a pr�xima quest�o tenha alternativas
					if (provaQuestoes.get(posicaoQuestao + 1)
							.getAlternativaQuestao() != null) {
						// Separa as alternativas salavr em um vetor de string
						String[] alternativas = provaQuestoes
								.get(posicaoQuestao + 1)
								.getAlternativaQuestao().split(",");
						// Faz um la�o para cada alternativa
						for (int i = 0; i < alternativas.length; i++) {
							// Adiciona cada alternativa numa lista
							alternativasQuestao.add(alternativaDAO
									.buscarPorId(Long
											.parseLong(alternativas[i])));
						}
					}

					model.addAttribute(
							"respostaD",
							session.getAttribute("resposta"
									+ (posicaoQuestao + 1)));
					model.addAttribute(
							"respostaA",
							session.getAttribute("resposta"
									+ (posicaoQuestao + 1)));

					// Salvar posi��o da quest�o
					session.setAttribute("posicaoQuestao", posicaoQuestao + 1);
					// Setar a posicaoQuestao, quantidadeQuestoes, provaQuestao,
					// alternativas, idQuestao na jsp
					model.addAttribute("posicaoQuestao", posicaoQuestao + 1);
					model.addAttribute("quantidadeQuestoes",
							provaQuestoes.size());
					model.addAttribute("provaQuestao",
							provaQuestoes.get(posicaoQuestao + 1));
					model.addAttribute("alternativas", alternativasQuestao);
					model.addAttribute("idQuestao",
							provaQuestoes.get(posicaoQuestao + 1).getQuestao()
									.getIdQuestao());
					String[] tempoProva = tempoDaProva.split(":");
					String hora = tempoProva[0];
					String minuto = tempoProva[1];
					String segundo = tempoProva[2];
					String valorFinal = "";
					if (Integer.parseInt(segundo) > 0) {

						if (Integer.parseInt(segundo) < 10) {
							segundo = "0" + segundo;
						}
						valorFinal = hora + ":" + minuto + ":" + segundo;
						model.addAttribute("tempoDaProva", valorFinal);
						return "formularioRealizarProva";
					}
					if (Integer.parseInt(segundo) == 0) {
						if (Integer.parseInt(minuto) > 0) {
							minuto = String
									.valueOf(Integer.parseInt(minuto) - 1);
							minuto = "0" + minuto;
							segundo = "60";

							/*
							 * if (Integer.parseInt(minuto) < 10) { minuto = "0"
							 * + minuto; }
							 */

							valorFinal = hora + ":" + minuto + ":" + segundo;
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
						if (Integer.parseInt(minuto) == 0) {
							if (Integer.parseInt(hora) > 0) {
								hora = String
										.valueOf(Integer.parseInt(hora) - 1);
								hora = "0" + hora;
								minuto = "59";
								segundo = "60";
								valorFinal = hora + ":" + minuto + ":"
										+ segundo;
								model.addAttribute("tempoDaProva", valorFinal);
								return "formularioRealizarProva";
							}
							if (Integer.parseInt(hora) == 0) {
								valorFinal = "00:00:00";
								model.addAttribute("tempoDaProva", valorFinal);
								return "formularioRealizarProva";
							}
						}
					}

					// Caso tenha atingido o limite de quest�es respondidas
				} else if (posicaoQuestao == (quantidadeQuestoes - 1)) {
					// Faz um la�o de provaQuestoes
					for (int i = 0; i < provaQuestoes.size(); i++) {
						if (session.getAttribute("idQuestao" + i) != null
								&& session.getAttribute("resposta" + i) != null) {
							// Faz a inst�ncia do objeto Resposta
							objeto = new Resposta();
							// Recupera o idQuestao da sess�o
							Long idQuestao = (Long) session
									.getAttribute("idQuestao" + i);
							// Recupera a resposta da sess�o
							String resposta = (String) session
									.getAttribute("resposta" + i);
							// Seta o aluno, prova e a quest�o que foi
							// respondida
							objeto.setAlunoResposta(aluno);
							objeto.setProvaResposta(prova);
							objeto.setQuestaoResposta(questaoDAO
									.buscarPorId(idQuestao));
							// Se existirem alternativas
							if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
								// Busca a alternativa correta daquela quest�o
								alternativa = alternativaDAO
										.buscarAlternativaCorreta(idQuestao);
								// Caso a alternativa seja respondida
								if (resposta != null) {
									if (alternativa != null) {
										// Caso a resposta seja correta
										if (alternativa.getIdAlternativa() == Integer
												.parseInt(resposta)) {
											// Salve como verdadeiro
											objeto.setCorretaAlternativaQuestao(true);
											// Caso a aalternativa n�o seja
											// respondida
										} else {
											objeto.setCorretaAlternativaQuestao(false);
										}
									}
								}
								// Se n�o existirem alternativas
							} else {
								// Caso a respoata n�o esteja em branco
								if (!resposta.isEmpty()) {
									// Salvar resposta dissertativa
									objeto.setDissertacaoQuestao(resposta);
								}
							}

							// Salvar resposta
							respostaDAO.adicionar(objeto);
						}
					}

				}

			}

			// Recupera o aluno logado
			aluno = (Aluno) session.getAttribute("usuarioLogado");
			alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
			alunoProvaDAO.salvarFinalizacaoProva(alunoProva);

			List<AlunoProva> alunosProva = alunoProvaDAO
					.listarProvasFinalizadas(prova.getIdProva());
			int contador = 0;
			if (alunosProva.size() > 0) {
				List<AlunoProva> listaAlunosProva = new ArrayList<AlunoProva>();
				listaAlunosProva = alunoProvaDAO.listarPorProva(alunoProva
						.getProva().getIdProva());
				for (int j = 0; j < listaAlunosProva.size(); j++) {
					if (listaAlunosProva.get(j).getDataRealizacaoProva() != null) {
						contador += 1;
					}
				}
				if (contador <= 1) {
					for (int i = 0; i < provaQuestoes.size(); i++) {
						questao = provaQuestoes.get(i).getQuestao();
						questao.setQntdUsoQuestao(provaQuestoes.get(i)
								.getQuestao().getQntdUsoQuestao() + 1);
						questaoDAO.salvarFinalizacaoQuestao(questao);
					}
				}

			}

			int count = 0;
			for (int i = 0; i < provaQuestoes.size(); i++) {
				if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
					count = count + 1;
				}
			}

			// Caso a prova for totalmente objetiva
			if (provaQuestoes.size() == count) {
				List<Resposta> respostas = new ArrayList<Resposta>();
				List<Resposta> questoesRespondidas = new ArrayList<Resposta>();
				respostas = respostaDAO.listarPorProva(prova.getIdProva());
				for (int i = 0; i < provaQuestoes.size(); i++) {
					for (int j = 0; j < respostas.size(); j++) {
						if (provaQuestoes
								.get(i)
								.getQuestao()
								.getIdQuestao()
								.equals(respostas.get(j).getQuestaoResposta()
										.getIdQuestao())) {
							questoesRespondidas.add(respostas.get(j));
						}
					}

				}

				if (questoesRespondidas.size() > 0) {
					for (int i = 0; i < questoesRespondidas.size(); i++) {
						if (questoesRespondidas.get(i)
								.isCorretaAlternativaQuestao()) {
							provaQuestao = provaQuestaoDAO
									.buscarPorProvaQuestao(questoesRespondidas
											.get(i).getProvaResposta()
											.getIdProva(), questoesRespondidas
											.get(i).getQuestaoResposta()
											.getIdQuestao());

							questoesRespondidas.get(i).setNotaAlternativa(
									provaQuestao.getPesoQuestao());
						}
					}
				}

				double notaProva = 0;
				for (int i = 0; i < questoesRespondidas.size(); i++) {
					notaProva += questoesRespondidas.get(i)
							.getNotaAlternativa();
				}

				NumberFormat doubleformat = NumberFormat.getInstance();
				doubleformat.setMinimumFractionDigits(2);
				doubleformat.setMaximumFractionDigits(2);
				alunoProva = alunoProvaDAO.buscarPorAlunoProva(
						aluno.getIdAluno(), prova.getIdProva());
				alunoProva.setNotaAlunoProva(Double.parseDouble(doubleformat
						.format(notaProva).replace(",", ".")));
				alunoProvaDAO.alterar(alunoProva);

			}

			session.removeAttribute("idProva");
			session.removeAttribute("quantidadeQuestoes");
			session.removeAttribute("posicaoQuestao");
			session.removeAttribute("provaQuestoes");
			session.removeAttribute("idQuestao");
			session.removeAttribute("quantidadeQuestoes");
			session.removeAttribute("alternativas");
			session.removeAttribute("posicaoQuestao");
			session.removeAttribute("resposta");

			return "homeAluno";
		}
		if (anteriorOuProximo.equals("Anterior")) {

			// Recupera o aluno logado da sess�o
			aluno = (Aluno) session.getAttribute("usuarioLogado");
			// Recupera a prova da sess�o
			prova = provaDAO
					.buscarPorId((Long) session.getAttribute("idProva"));
			// Recupera a quest�o respondida
			questao = questaoDAO.buscarPorId(questao.getIdQuestao());
			// Recupera a posi��o da quest�o na sess�o
			int posicaoQuestao = (int) session.getAttribute("posicaoQuestao");
			// Faz a inst�ncia de ProvaQuestao e Alternativa
			List<ProvaQuestao> provaQuestoes = (List<ProvaQuestao>) session
					.getAttribute("provaQuestoes");
			List<Alternativa> alternativasQuestao = new ArrayList<Alternativa>();

			int vazia;
			if (respostaDissertativa == null || respostaDissertativa == "") {
				vazia = 0;
			} else {
				vazia = 1;
			}

			// Caso n�o tenha atingido o total de quest�es da prova
			// CASO FOR UMA QUEST�O DE DISSERTA��O
			if (vazia == 1) {
				// Salvar quest�o na sess�o
				session.setAttribute("idQuestao" + posicaoQuestao,
						questao.getIdQuestao());

				// Salvar disserta��o da quest�o
				session.setAttribute("resposta" + posicaoQuestao,
						respostaDissertativa);

				// Caso a pr�xima quest�o tenha alternativas
				if (provaQuestoes.get(posicaoQuestao - 1)
						.getAlternativaQuestao() != null) {
					// Separa as alternativas salavr em um vetor de string
					String[] alternativas = provaQuestoes
							.get(posicaoQuestao - 1).getAlternativaQuestao()
							.split(",");
					// Faz um la�o para cada alternativa
					for (int i = 0; i < alternativas.length; i++) {
						// Adiciona cada alternativa numa lista
						alternativasQuestao.add(alternativaDAO.buscarPorId(Long
								.parseLong(alternativas[i])));
					}
				}

				model.addAttribute("respostaD",
						session.getAttribute("resposta" + (posicaoQuestao - 1)));
				model.addAttribute("respostaA",
						session.getAttribute("resposta" + (posicaoQuestao - 1)));

				// Salvar posi��o da quest�o
				session.setAttribute("posicaoQuestao", posicaoQuestao - 1);

				// Setar a posicaoQuestao, quantidadeQuestoes, provaQuestao,
				// alternativas, idQuestao na jsp
				model.addAttribute("posicaoQuestao", posicaoQuestao - 1);
				model.addAttribute("quantidadeQuestoes", provaQuestoes.size());
				model.addAttribute("provaQuestao",
						provaQuestoes.get(posicaoQuestao - 1));
				model.addAttribute("alternativas", alternativasQuestao);
				model.addAttribute("idQuestao",
						provaQuestoes.get(posicaoQuestao - 1).getQuestao()
								.getIdQuestao());
				String[] tempoProva = tempoDaProva.split(":");
				String hora = tempoProva[0];
				String minuto = tempoProva[1];
				String segundo = tempoProva[2];
				String valorFinal = "";
				if (Integer.parseInt(segundo) > 0) {

					if (Integer.parseInt(segundo) < 10) {
						segundo = "0" + segundo;
					}
					valorFinal = hora + ":" + minuto + ":" + segundo;
					model.addAttribute("tempoDaProva", valorFinal);
					return "formularioRealizarProva";
				}
				if (Integer.parseInt(segundo) == 0) {
					if (Integer.parseInt(minuto) > 0) {
						minuto = String.valueOf(Integer.parseInt(minuto) - 1);
						minuto = "0" + minuto;
						segundo = "60";

						/*
						 * if (Integer.parseInt(minuto) < 10) { minuto = "0" +
						 * minuto; }
						 */

						valorFinal = hora + ":" + minuto + ":" + segundo;
						model.addAttribute("tempoDaProva", valorFinal);
						return "formularioRealizarProva";
					}
					if (Integer.parseInt(minuto) == 0) {
						if (Integer.parseInt(hora) > 0) {
							hora = String.valueOf(Integer.parseInt(hora) - 1);
							hora = "0" + hora;
							minuto = "59";
							segundo = "60";
							valorFinal = hora + ":" + minuto + ":" + segundo;
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
						if (Integer.parseInt(hora) == 0) {
							valorFinal = "00:00:00";
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
					}
				}

				// CASO FOR UMA QUEST�O DE ALTERNATIVA
			} else {
				// Salvar quest�o na sess�o
				session.setAttribute("idQuestao" + posicaoQuestao,
						questao.getIdQuestao());

				// Salvar disserta��o da quest�o
				session.setAttribute("resposta" + posicaoQuestao,
						respostaAlternativa);

				// Caso a pr�xima quest�o tenha alternativas
				if (provaQuestoes.get(posicaoQuestao - 1)
						.getAlternativaQuestao() != null) {
					// Separa as alternativas salavr em um vetor de string
					String[] alternativas = provaQuestoes
							.get(posicaoQuestao - 1).getAlternativaQuestao()
							.split(",");
					// Faz um la�o para cada alternativa
					for (int i = 0; i < alternativas.length; i++) {
						// Adiciona cada alternativa numa lista
						alternativasQuestao.add(alternativaDAO.buscarPorId(Long
								.parseLong(alternativas[i])));
					}
				}

				model.addAttribute("respostaD",
						session.getAttribute("resposta" + (posicaoQuestao - 1)));
				model.addAttribute("respostaA",
						session.getAttribute("resposta" + (posicaoQuestao - 1)));

				// Salvar posi��o da quest�o
				session.setAttribute("posicaoQuestao", posicaoQuestao - 1);
				// Setar a posicaoQuestao, quantidadeQuestoes, provaQuestao,
				// alternativas, idQuestao na jsp
				model.addAttribute("posicaoQuestao", posicaoQuestao - 1);
				model.addAttribute("quantidadeQuestoes", provaQuestoes.size());
				model.addAttribute("provaQuestao",
						provaQuestoes.get(posicaoQuestao - 1));
				model.addAttribute("alternativas", alternativasQuestao);
				model.addAttribute("idQuestao",
						provaQuestoes.get(posicaoQuestao - 1).getQuestao()
								.getIdQuestao());
				String[] tempoProva = tempoDaProva.split(":");
				String hora = tempoProva[0];
				String minuto = tempoProva[1];
				String segundo = tempoProva[2];
				String valorFinal = "";
				if (Integer.parseInt(segundo) > 0) {

					if (Integer.parseInt(segundo) < 10) {
						segundo = "0" + segundo;
					}
					valorFinal = hora + ":" + minuto + ":" + segundo;
					model.addAttribute("tempoDaProva", valorFinal);
					return "formularioRealizarProva";
				}
				if (Integer.parseInt(segundo) == 0) {
					if (Integer.parseInt(minuto) > 0) {
						minuto = String.valueOf(Integer.parseInt(minuto) - 1);
						minuto = "0" + minuto;
						segundo = "60";

						/*
						 * if (Integer.parseInt(minuto) < 10) { minuto = "0" +
						 * minuto; }
						 */

						valorFinal = hora + ":" + minuto + ":" + segundo;
						model.addAttribute("tempoDaProva", valorFinal);
						return "formularioRealizarProva";
					}
					if (Integer.parseInt(minuto) == 0) {
						if (Integer.parseInt(hora) > 0) {
							hora = String.valueOf(Integer.parseInt(hora) - 1);
							hora = "0" + hora;
							minuto = "59";
							segundo = "60";
							valorFinal = hora + ":" + minuto + ":" + segundo;
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
						if (Integer.parseInt(hora) == 0) {
							valorFinal = "00:00:00";
							model.addAttribute("tempoDaProva", valorFinal);
							return "formularioRealizarProva";
						}
					}
				}

			}

			// Recupera o aluno logado
			aluno = (Aluno) session.getAttribute("usuarioLogado");
			alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
			alunoProvaDAO.salvarFinalizacaoProva(alunoProva);

			List<AlunoProva> alunosProva = alunoProvaDAO
					.listarProvasFinalizadas(prova.getIdProva());
			if (alunosProva.size() == 1) {
				for (int i = 0; i < provaQuestoes.size(); i++) {
					questao = provaQuestoes.get(i).getQuestao();
					questao.setQntdUsoQuestao(provaQuestoes.get(i).getQuestao()
							.getQntdUsoQuestao() + 1);
					questaoDAO.salvarFinalizacaoQuestao(questao);
				}
			}

			int count = 0;
			for (int i = 0; i < provaQuestoes.size(); i++) {
				if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
					count = count + 1;
				}
			}

			// Caso a prova for totalmente objetiva
			if (provaQuestoes.size() == count) {

				List<Resposta> respostas = new ArrayList<Resposta>();
				List<Resposta> questoesRespondidas = new ArrayList<Resposta>();
				respostas = respostaDAO.listarPorProva(prova.getIdProva());
				for (int i = 0; i < provaQuestoes.size(); i++) {
					for (int j = 0; j < respostas.size(); j++) {
						if (provaQuestoes
								.get(i)
								.getQuestao()
								.getIdQuestao()
								.equals(respostas.get(j).getQuestaoResposta()
										.getIdQuestao())) {
							questoesRespondidas.add(respostas.get(j));
						}
					}

				}

				if (questoesRespondidas.size() > 0) {
					for (int i = 0; i < questoesRespondidas.size(); i++) {
						if (questoesRespondidas.get(i)
								.isCorretaAlternativaQuestao()) {
							provaQuestao = provaQuestaoDAO
									.buscarPorProvaQuestao(questoesRespondidas
											.get(i).getProvaResposta()
											.getIdProva(), questoesRespondidas
											.get(i).getQuestaoResposta()
											.getIdQuestao());

							questoesRespondidas.get(i).setNotaAlternativa(
									provaQuestao.getPesoQuestao());
						}
					}
				}

				double notaProva = 0;
				for (int i = 0; i < questoesRespondidas.size(); i++) {
					notaProva += questoesRespondidas.get(i)
							.getNotaAlternativa();
				}

				NumberFormat doubleformat = NumberFormat.getInstance();
				doubleformat.setMinimumFractionDigits(2);
				doubleformat.setMaximumFractionDigits(2);
				alunoProva = alunoProvaDAO.buscarPorAlunoProva(
						aluno.getIdAluno(), prova.getIdProva());
				alunoProva.setNotaAlunoProva(Double.parseDouble(doubleformat
						.format(notaProva).replace(",", ".")));
				alunoProvaDAO.alterar(alunoProva);

			}

			session.removeAttribute("idProva");
			session.removeAttribute("quantidadeQuestoes");
			session.removeAttribute("posicaoQuestao");
			session.removeAttribute("provaQuestoes");
			session.removeAttribute("idQuestao");
			session.removeAttribute("quantidadeQuestoes");
			session.removeAttribute("alternativas");
			session.removeAttribute("posicaoQuestao");
			session.removeAttribute("resposta");

			return "homeAluno";
		}
		return "homeAluno";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("salvarQuestoesInacabadas")
	public String salvarQuestoesInacabadas(HttpSession session, Model model,
			Resposta objeto, Aluno aluno, Prova prova, Alternativa alternativa,
			Disciplina disciplina, Assunto assunto, AlunoProva alunoProva,
			Questao questao, ProvaQuestao provaQuestao) {

		List<ProvaQuestao> provaQuestoes = (List<ProvaQuestao>) session
				.getAttribute("provaQuestoes");
		// Recupera o aluno logado da sess�o
		aluno = (Aluno) session.getAttribute("usuarioLogado");
		// Recupera a prova da sess�o
		prova = provaDAO.buscarPorId((Long) session.getAttribute("idProva"));

		for (int i = 0; i < provaQuestoes.size(); i++) {
			if (session.getAttribute("idQuestao" + i) != null
					&& session.getAttribute("resposta" + i) != null) {
				// Faz a inst�ncia do objeto Resposta
				objeto = new Resposta();
				// Recupera o idQuestao da sess�o
				Long idQuestao = (Long) session.getAttribute("idQuestao" + i);
				// Recupera a resposta da sess�o
				String resposta = (String) session.getAttribute("resposta" + i);
				// Seta o aluno, prova e a quest�o que foi respondida
				objeto.setAlunoResposta(aluno);
				objeto.setProvaResposta(prova);
				objeto.setQuestaoResposta(questaoDAO.buscarPorId(idQuestao));
				// Se existirem alternativas
				if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
					// Busca a alternativa correta daquela quest�o
					alternativa = alternativaDAO
							.buscarAlternativaCorreta(idQuestao);
					// Caso a alternativa seja respondida
					if (resposta != null) {
						// Caso a resposta seja correta
						if (alternativa.getIdAlternativa() == Integer
								.parseInt(resposta)) {
							// Salve como verdadeiro
							objeto.setCorretaAlternativaQuestao(true);
							// Caso a aalternativa n�o seja respondida
						} else {
							objeto.setCorretaAlternativaQuestao(false);
						}
					}
					// Se n�o existirem alternativas
				} else {
					// Caso a respoata n�o esteja em branco
					if (!resposta.isEmpty()) {
						// Salvar resposta dissertativa
						objeto.setDissertacaoQuestao(resposta);
					}
				}

				// Salvar resposta
				respostaDAO.adicionar(objeto);
			}
		}
		// Recupera o aluno logado
		aluno = (Aluno) session.getAttribute("usuarioLogado");
		// Buscar alunoProva
		alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
				prova.getIdProva());
		// Salvar data e hora de finaliza��o da prova
		alunoProvaDAO.salvarFinalizacaoProva(alunoProva);
		// Faz a inst�ncia de alunosProva
		List<AlunoProva> alunosProva = alunoProvaDAO
				.listarProvasFinalizadas(prova.getIdProva());
		// Caso seja a primeira vez que � utilizada a quest�o para uma prova,
		// salva a quantidade de uso e a �ltima utiliza��o da quest�o
		if (alunosProva.size() == 1) {
			for (int i = 0; i < provaQuestoes.size(); i++) {
				questao = new Questao();
				questao = provaQuestoes.get(i).getQuestao();
				questao.setQntdUsoQuestao(provaQuestoes.get(i).getQuestao()
						.getQntdUsoQuestao() + 1);
				// Salvar data e hora da quest�o
				questaoDAO.salvarFinalizacaoQuestao(questao);
			}
		}

		// Recupera o aluno logado
		aluno = (Aluno) session.getAttribute("usuarioLogado");
		alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
				prova.getIdProva());
		alunoProvaDAO.salvarFinalizacaoProva(alunoProva);

		List<AlunoProva> listaAlunosProva = alunoProvaDAO
				.listarProvasFinalizadas(prova.getIdProva());
		if (listaAlunosProva.size() == 1) {
			for (int i = 0; i < provaQuestoes.size(); i++) {
				questao = provaQuestoes.get(i).getQuestao();
				questao.setQntdUsoQuestao(provaQuestoes.get(i).getQuestao()
						.getQntdUsoQuestao() + 1);
				questaoDAO.salvarFinalizacaoQuestao(questao);
			}
		}

		int count = 0;
		for (int i = 0; i < provaQuestoes.size(); i++) {
			if (provaQuestoes.get(i).getAlternativaQuestao() != null) {
				count = count + 1;
			}
		}

		// Caso a prova for totalmente objetiva
		if (provaQuestoes.size() == count) {

			List<Resposta> respostas = new ArrayList<Resposta>();
			List<Resposta> questoesRespondidas = new ArrayList<Resposta>();
			respostas = respostaDAO.listarPorProva(prova.getIdProva());
			for (int i = 0; i < provaQuestoes.size(); i++) {
				for (int j = 0; j < respostas.size(); j++) {
					if (provaQuestoes
							.get(i)
							.getQuestao()
							.getIdQuestao()
							.equals(respostas.get(j).getQuestaoResposta()
									.getIdQuestao())) {
						questoesRespondidas.add(respostas.get(j));
					}
				}

			}

			if (questoesRespondidas.size() > 0) {
				for (int i = 0; i < questoesRespondidas.size(); i++) {
					if (questoesRespondidas.get(i)
							.isCorretaAlternativaQuestao()) {
						provaQuestao = provaQuestaoDAO.buscarPorProvaQuestao(
								questoesRespondidas.get(i).getProvaResposta()
										.getIdProva(),
								questoesRespondidas.get(i).getQuestaoResposta()
										.getIdQuestao());

						questoesRespondidas.get(i).setNotaAlternativa(
								provaQuestao.getPesoQuestao());
					}
				}
			}

			double notaProva = 0;
			for (int i = 0; i < questoesRespondidas.size(); i++) {
				notaProva += questoesRespondidas.get(i).getNotaAlternativa();
			}

			alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
			alunoProva.setNotaAlunoProva(notaProva);
			alunoProvaDAO.alterar(alunoProva);
		}

		session.removeAttribute("idProva");
		session.removeAttribute("quantidadeQuestoes");
		session.removeAttribute("posicaoQuestao");
		session.removeAttribute("provaQuestoes");
		session.removeAttribute("idQuestao");
		session.removeAttribute("quantidadeQuestoes");
		session.removeAttribute("alternativas");
		session.removeAttribute("posicaoQuestao");

		return "homeAluno";
	}

	@RequestMapping("removerProva")
	public String removerProva(HttpSession session, Model model, Prova prova,
			ProvaDisciplina provaDisciplina, ProvaAssunto provaAssunto,
			ProvaQuestao provaQuestao, AlunoProva alunoProva,
			ProvaTurma provaTurma, Assunto assunto, Docente docente) {

		prova = (Prova) session.getAttribute("prova");

		// Faz a inst�ncia de provaDisciplinas e busca por prova
		List<ProvaDisciplina> provaDisciplinas = new ArrayList<ProvaDisciplina>();
		provaDisciplinas = provaDisciplinaDAO
				.listarPorProva(prova.getIdProva());
		// Faz a inst�ncia de provaAssuntos e busca por prova
		List<ProvaAssunto> provaAssuntos = new ArrayList<ProvaAssunto>();
		provaAssuntos = provaAssuntoDAO.listarPorProva(prova.getIdProva());
		// Faz a inst�ncia de provaQuestoes e busca por prova
		List<ProvaQuestao> provaQuestoes = new ArrayList<ProvaQuestao>();
		provaQuestoes = provaQuestaoDAO.listarPorProva(prova.getIdProva());
		// Faz a inst�ncia de alunosProva e busca por prova
		List<AlunoProva> alunosProva = new ArrayList<AlunoProva>();
		alunosProva = alunoProvaDAO.listarPorProva(prova.getIdProva());
		// Faz a inst�ncia de provaTurmas e busca por prova
		List<ProvaTurma> provaTurmas = new ArrayList<ProvaTurma>();
		provaTurmas = provaTurmaDAO.listarPorProva(prova.getIdProva());

		// Remove todas as provaDisciplinas
		for (int i = 0; i < provaDisciplinas.size(); i++) {
			provaDisciplinaDAO.remover(provaDisciplinas.get(i));
		}

		// Remove todas as provaAssuntos
		for (int i = 0; i < provaAssuntos.size(); i++) {
			provaAssuntoDAO.remover(provaAssuntos.get(i));
		}

		// Remove todas as provaQuestoes
		for (int i = 0; i < provaQuestoes.size(); i++) {
			provaQuestaoDAO.remover(provaQuestoes.get(i));
		}

		// Remove todas as alunosProva
		for (int i = 0; i < alunosProva.size(); i++) {
			alunoProvaDAO.remover(alunosProva.get(i));
		}

		// Remove todas as provaTurmas
		for (int i = 0; i < provaTurmas.size(); i++) {
			provaTurmaDAO.remover(provaTurmas.get(i));
		}

		provaDAO.remover(prova);

		// Recupera o docente da sess�o
		docente = (Docente) session.getAttribute("usuarioLogado");
		// Recupera uma lista de provas da instituicao
		List<Prova> provas = provaDAO.listarPorInstituicaoDocente(docente
				.getInstituicaoDocente().getIdInstituicao(), docente
				.getIdDocente());
		List<Prova> provasDisponiveis = new ArrayList<Prova>();
		List<AlunoProva> alunosProvas = new ArrayList<AlunoProva>();

		for (int i = 0; i < provas.size(); i++) {
			alunosProvas = alunoProvaDAO.listarPorProva(provas.get(i)
					.getIdProva());
			for (int j = 0; j < alunosProvas.size(); j++) {
				if (String
						.valueOf(alunosProvas.get(j).getDataRealizacaoProva()) == "null") {
					provasDisponiveis.add(provas.get(i));
				}
			}
		}

		// Cria uma lista de assuntos
		List<String> assuntos = new ArrayList<String>();
		// Cria uma lista de ProvaAssunto
		List<ProvaAssunto> provasAssunto;
		// Cria uma lista de datasProva
		List<Date> datasProva = new ArrayList<Date>();
		// Cria uma lista de tiposProva
		List<String> tiposProva = new ArrayList<String>();
		// Cria uma lista de ids da prova
		List<String> idsProva = new ArrayList<String>();

		// Adiciona os nomes dos assuntos dentro de uma string
		String assuntos2 = "";
		// Caso tenha conte�do dentro da prova
		if (provasDisponiveis.size() > 0) {
			// Faz um la�o da prova
			for (int i = 0; i < provasDisponiveis.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia do assunto
				assunto = new Assunto();
				// Faz a inst�ncia de ProvaAssunto
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Lista os assuntos pela prova
				provasAssunto = provaAssuntoDAO
						.listarPorProva(provasDisponiveis.get(i).getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto completo enviando o seu id
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome do assunto em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Envia o nome para a string assuntos2
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Se tiver conte�do dentro da string
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string na lista
				assuntos.add(assuntos2);

				// Adiciona a data dentro da lista
				datasProva.add(provasDisponiveis.get(i)
						.getDataRealizacaoProva());
				// Adiciona o tipo dentro da lista
				tiposProva.add(provasDisponiveis.get(i).getTipoProva());
				// Adicionaos ids da prova dentro da lista
				idsProva.add(String.valueOf(provasDisponiveis.get(i)
						.getIdProva()));

			}
		}

		// Envia os valores para a p�gina jsp
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("provas", provasDisponiveis);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("provaSize", provasDisponiveis.size());

		return "listaProvas";
	}

	@RequestMapping("listaCorrigirProvas")
	public String listaCorrigirProvas(HttpSession session, Model model,
			Docente docente, Assunto assunto) {
		docente = (Docente) session.getAttribute("usuarioLogado");
		// TODO ver porque o syso na linha 2804 d� '0.0'
		List<AlunoProva> alunosProva = new ArrayList<AlunoProva>();
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = alunoDAO.listarPorInstituicao(docente.getInstituicaoDocente()
				.getIdInstituicao());

		for (int i = 0; i < alunos.size(); i++) {
			List<AlunoProva> alunosProvaDisponiveis = new ArrayList<AlunoProva>();
			alunosProvaDisponiveis = alunoProvaDAO.listarPorAluno(alunos.get(i)
					.getIdAluno());

			for (int j = 0; j < alunosProvaDisponiveis.size(); j++) {
				if (alunos
						.get(i)
						.getIdAluno()
						.equals(alunosProvaDisponiveis.get(j).getAluno()
								.getIdAluno())) {
					alunosProva.add(alunosProvaDisponiveis.get(j));
				}
			}

		}

		List<Prova> provas = new ArrayList<Prova>();
		if (alunosProva.size() > 0) {
			for (int i = 0; i < alunosProva.size(); i++) {
				if (alunosProva.get(i).getDataRealizacaoProva() != null
						&& alunosProva.get(i).getNotaAlunoProva() == 0) {
					provas.add(provaDAO.buscarPorId(alunosProva.get(i)
							.getProva().getIdProva()));
				}
			}
		}

		// Cria uma lista de assuntos
		List<String> assuntos = new ArrayList<String>();
		// Cria uma lista de ProvaAssunto
		List<ProvaAssunto> provasAssunto;
		// Cria uma lista de datasProva
		List<Date> datasProva = new ArrayList<Date>();
		// Cria uma lista de tiposProva
		List<String> tiposProva = new ArrayList<String>();
		// Cria uma lista de ids da prova
		List<String> idsProva = new ArrayList<String>();

		// Adiciona os nomes dos assuntos dentro de uma string
		String assuntos2 = "";
		// Caso tenha conte�do dentro da prova
		if (provas.size() > 0) {
			// Faz um la�o da prova
			for (int i = 0; i < provas.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia do assunto
				assunto = new Assunto();
				// Faz a inst�ncia de ProvaAssunto
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Lista os assuntos pela prova
				provasAssunto = provaAssuntoDAO.listarPorProva(provas.get(i)
						.getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto completo enviando o seu id
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome do assunto em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Envia o nome para a string assuntos2
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Se tiver conte�do dentro da string
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string na lista
				assuntos.add(assuntos2);

				// Adiciona a data dentro da lista
				datasProva.add(provas.get(i).getDataRealizacaoProva());
				// Adiciona o tipo dentro da lista
				tiposProva.add(provas.get(i).getTipoProva());
				// Adicionaos ids da prova dentro da lista
				idsProva.add(String.valueOf(provas.get(i).getIdProva()));

			}
		}

		// Envia os valores para a p�gina jsp
		model.addAttribute("provas", provas);
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("provaSize", provas.size());

		return "listaCorrigirProvas";
	}

	@RequestMapping("listaTurmasCorrigirProva")
	public String listaTurmasCorrigirProva(HttpSession session, Model model,
			Prova prova) {
		List<AlunoProva> alunosProva = new ArrayList<AlunoProva>();
		alunosProva = alunoProvaDAO.listarPorProva(prova.getIdProva());
		List<Aluno> alunos = new ArrayList<Aluno>();
		List<Turma> turmas = new ArrayList<Turma>();
		for (int i = 0; i < alunosProva.size(); i++) {
			if (alunosProva.get(i).getDataRealizacaoProva() != null) {
				alunosProva.get(i).setAluno(
						alunoDAO.buscarPorId(alunosProva.get(i).getAluno()
								.getIdAluno()));
				alunosProva.get(i).setProva(
						provaDAO.buscarPorId(alunosProva.get(i).getProva()
								.getIdProva()));
				alunos.add(alunosProva.get(i).getAluno());
			}
		}
		int contador = 0;
		if (alunos.size() > 0) {
			for (int i = 0; i < alunos.size(); i++) {
				if (turmas.size() > 0) {
					for (int j = 0; j < turmas.size(); j++) {
						if (alunos.get(i).getTurmaAluno().getIdTurma()
								.equals(turmas.get(j).getIdTurma())) {
							contador += 1;
						}
						if (j == (turmas.size() - 1)) {
							if (contador == 0) {
								turmas.add(turmaDAO.buscarPorId(alunos.get(i)
										.getTurmaAluno().getIdTurma()));
							} else {
								contador = 0;
							}
						}
					}
				} else {
					turmas.add(turmaDAO.buscarPorId(alunos.get(i)
							.getTurmaAluno().getIdTurma()));
				}
			}
		}

		session.setAttribute("idProva", prova.getIdProva());
		model.addAttribute("turmas", turmas);

		return "listaTurmasCorrigirProva";
	}

	@RequestMapping("listaAlunosCorrigirProva")
	public String listaAlunosCorrigirProva(HttpSession session, Model model,
			Turma turma, Docente docente, Prova prova, AlunoProva alunoProva) {
		prova = provaDAO.buscarPorId((Long) session.getAttribute("idProva"));
		docente = (Docente) session.getAttribute("usuarioLogado");
		List<Aluno> alunos = new ArrayList<Aluno>();
		List<Aluno> alunosAux = new ArrayList<Aluno>();

		alunos = alunoDAO.listarPorTurma(turma.getIdTurma(), docente
				.getInstituicaoDocente().getIdInstituicao());
		alunosAux = alunos;
		for (int i = 0; i < alunosAux.size(); i++) {
			alunoProva = new AlunoProva();
			alunoProva = alunoProvaDAO.buscarPorAlunoProva(alunosAux.get(i)
					.getIdAluno(), prova.getIdProva());
			if (alunoProva == null) {
				alunos.remove(i);
			} else if (alunoProva.getDataRealizacaoProva() == null) {
				alunos.remove(i);
			}

		}

		for (int i = 0; i < alunos.size(); i++) {
			alunos.get(i).setTurmaAluno(
					turmaDAO.buscarPorId(alunos.get(i).getTurmaAluno()
							.getIdTurma()));
		}

		model.addAttribute("alunos", alunos);
		return "listaAlunosCorrigirProva";

	}

	@RequestMapping("mostraCorrigirProva")
	public String mostraCorrigirProva(HttpSession session, Model model,
			Aluno aluno, Prova prova) {
		prova = provaDAO.buscarPorId((Long) session.getAttribute("idProva"));
		prova.setDocente(docenteDAO.buscarPorId(prova.getDocente()
				.getIdDocente()));
		List<Questao> questoes = new ArrayList<Questao>();
		List<Questao> questoesAux = new ArrayList<Questao>();
		List<ProvaQuestao> provasQuestoes = new ArrayList<ProvaQuestao>();
		List<Alternativa> alternativas = new ArrayList<Alternativa>();
		List<Resposta> respostas = new ArrayList<Resposta>();
		List<Resposta> respostasAux = new ArrayList<Resposta>();

		provasQuestoes = provaQuestaoDAO.listarPorProva(prova.getIdProva());
		for (int i = 0; i < provasQuestoes.size(); i++) {
			// Caso tenha alternativas
			if (provasQuestoes.get(i).getAlternativaQuestao() != null) {
				// Divide todas as alternativas pela v�rgula e armazena-as em um
				// vetor
				String[] vetor = provasQuestoes.get(i).getAlternativaQuestao()
						.split(",");
				// Faz um la�o for do vetor
				for (int j = 0; j < vetor.length; j++) {
					// Adiciona cada alternativa numa lista
					alternativas.add(alternativaDAO.buscarPorId(Long
							.parseLong(vetor[j])));
				}
			}
		}

		respostas = respostaDAO.listarPorAlunoProva(aluno.getIdAluno(),
				prova.getIdProva());
		respostasAux = respostas;
		for (int i = 0; i < respostasAux.size(); i++) {
			if (respostasAux.get(i).getNotaDissertacao() > 0) {
				respostas.remove(i);
			}
		}
		for (int i = 0; i < respostas.size(); i++) {
			questoes.add(questaoDAO.buscarPorId(respostas.get(i)
					.getQuestaoResposta().getIdQuestao()));
		}

		questoesAux = questoes;

		for (int i = (questoesAux.size() - 1); i > -1; i--) {
			if (questoesAux.get(i).getTipoQuestao().equals("Objetiva")) {
				questoes.remove(i);
			}
		}
		questoesAux = questoes;

		// Faz um la�o for de quest�es
		for (int i = 0; i < questoes.size(); i++) {
			// Seta as disciplinas em quest�es
			questoes.get(i).setDisciplinaQuestao(
					disciplinaDAO.buscarPorId(questoes.get(i)
							.getDisciplinaQuestao().getIdDisciplina()));
			// Seta os assuntos em quest�es
			questoes.get(i).setAssuntoQuestao(
					assuntoDAO.buscarPorId(questoes.get(i).getAssuntoQuestao()
							.getIdAssunto()));
		}

		model.addAttribute("aluno", alunoDAO.buscarPorId(aluno.getIdAluno()));
		model.addAttribute("prova", prova);
		model.addAttribute("respostas", respostas);
		model.addAttribute("provasQuestoes", provasQuestoes);
		model.addAttribute("questoes", questoes);
		return "mostraCorrigirProva";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("salvarCorrigirProva")
	public String salvarCorrigirProva(HttpSession session, Model model,
			Prova prova, Questao questao, Resposta resposta,
			ProvaQuestao provaQuestao, AlunoProva alunoProva, Docente docente,
			Aluno aluno, Assunto assunto, String notaDissertativa) {

		List<Questao> questoes = new ArrayList<Questao>();
		List<Questao> questoesAux = new ArrayList<Questao>();
		List<ProvaQuestao> provasQuestoes = new ArrayList<ProvaQuestao>();
		provasQuestoes = provaQuestaoDAO.listarPorProva(prova.getIdProva());

		resposta = new Resposta();
		resposta = respostaDAO.buscarPorAlunoProvaQuestao(aluno.getIdAluno(),
				prova.getIdProva(), questao.getIdQuestao());

		resposta.setNotaDissertacao(Double.parseDouble(notaDissertativa));
		respostaDAO.alterar(resposta);
		List<Resposta> respostas = null;
		if (session.getAttribute("respostas") == null) {
			respostas = new ArrayList<Resposta>();
			respostas = respostaDAO.listarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
		} else {
			respostas = (List<Resposta>) session.getAttribute("respostas");
		}

		List<Resposta> respostasAux = new ArrayList<Resposta>();

		respostasAux = respostas;

		for (int i = 0; i < respostasAux.size(); i++) {
			if (respostasAux.get(i).getQuestaoResposta().getIdQuestao()
					.equals(questao.getIdQuestao())) {
				respostas.remove(i);
			}
		}

		int count = 0;
		for (int i = 0; i < respostas.size(); i++) {
			if (questaoDAO
					.buscarPorId(
							respostas.get(i).getQuestaoResposta()
									.getIdQuestao()).getTipoQuestao()
					.equals("Dissertativa")) {
				count += 1;
			}
		}

		if (count > 0) {
			session.setAttribute("respostas", respostas);
		} else {
			alunoProva = new AlunoProva();
			// TODO corrigir o resto das alternativas
			for (int i = 0; i < respostas.size(); i++) {
				provaQuestao = new ProvaQuestao();
				provaQuestao = provaQuestaoDAO.buscarPorProvaQuestao(
						prova.getIdProva(), respostas.get(i)
								.getQuestaoResposta().getIdQuestao());
				if (respostas.get(i).isCorretaAlternativaQuestao()) {
					respostas.get(i).setNotaAlternativa(
							provaQuestao.getPesoQuestao());
					respostaDAO.alterar(respostas.get(i));
				}
			}

			// TODO salvar nota
			List<Resposta> notaRespostas = new ArrayList<>();
			notaRespostas = respostaDAO.listarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
			double nota = 0;
			for (int i = 0; i < notaRespostas.size(); i++) {
				nota += notaRespostas.get(i).getNotaAlternativa();
				nota += notaRespostas.get(i).getNotaDissertacao();
			}

			alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
					prova.getIdProva());
			alunoProva.setNotaAlunoProva(nota);
			alunoProvaDAO.alterar(alunoProva);

			model.addAttribute("idAluno", alunoProva.getAluno().getIdAluno());
			model.addAttribute("idProva", alunoProva.getProva().getIdProva());

			return "mostraConsideracaoCorrigirProva";

		}

		for (int i = 0; i < respostas.size(); i++) {
			questoes.add(questaoDAO.buscarPorId(respostas.get(i)
					.getQuestaoResposta().getIdQuestao()));
		}

		questoesAux = questoes;
		for (int i = (questoesAux.size() - 1); i > -1; i--) {
			if (questoesAux.get(i).getTipoQuestao().equals("Objetiva")) {
				questoes.remove(i);
			}
		}
		questoesAux = questoes;

		model.addAttribute("aluno", alunoDAO.buscarPorId(aluno.getIdAluno()));
		model.addAttribute("prova", prova);
		model.addAttribute("respostas", respostas);
		model.addAttribute("provasQuestoes", provasQuestoes);
		model.addAttribute("questoes", questoes);

		return "mostraCorrigirProva";
	}

	@RequestMapping("salvarConsideracao")
	public String salvarConsideracao(HttpSession session, Model model,
			Prova prova, Aluno aluno, Docente docente, AlunoProva alunoProva,
			Assunto assunto, String consideracaoProva) {
		alunoProva = alunoProvaDAO.buscarPorAlunoProva(aluno.getIdAluno(),
				prova.getIdProva());
		alunoProva.setConsideracaoProva(consideracaoProva);
		alunoProvaDAO.alterar(alunoProva);

		// INSERIR DADOS PARA A LISTAGEM DE PROVAS RESTANTES PARA SEREM
		// CORRIGIDAS

		docente = (Docente) session.getAttribute("usuarioLogado");
		List<AlunoProva> alunosProva = new ArrayList<AlunoProva>();
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunos = alunoDAO.listarPorInstituicao(docente.getInstituicaoDocente()
				.getIdInstituicao());

		for (int i = 0; i < alunos.size(); i++) {
			List<AlunoProva> alunosProvaDisponiveis = new ArrayList<AlunoProva>();
			alunosProvaDisponiveis = alunoProvaDAO.listarPorAluno(alunos.get(i)
					.getIdAluno());

			for (int j = 0; j < alunosProvaDisponiveis.size(); j++) {
				if (alunos
						.get(i)
						.getIdAluno()
						.equals(alunosProvaDisponiveis.get(j).getAluno()
								.getIdAluno())) {
					alunosProva.add(alunosProvaDisponiveis.get(j));
				}
			}

		}

		List<Prova> provas = new ArrayList<Prova>();
		if (alunosProva.size() > 0) {
			for (int i = 0; i < alunosProva.size(); i++) {
				if (alunosProva.get(i).getDataRealizacaoProva() != null
						&& alunosProva.get(i).getNotaAlunoProva() == 0) {
					provas.add(provaDAO.buscarPorId(alunosProva.get(i)
							.getProva().getIdProva()));
				}
			}
		}

		// Cria uma lista de assuntos
		List<String> assuntos = new ArrayList<String>();
		// Cria uma lista de ProvaAssunto
		List<ProvaAssunto> provasAssunto;
		// Cria uma lista de datasProva
		List<Date> datasProva = new ArrayList<Date>();
		// Cria uma lista de tiposProva
		List<String> tiposProva = new ArrayList<String>();
		// Cria uma lista de ids da prova
		List<String> idsProva = new ArrayList<String>();

		// Adiciona os nomes dos assuntos dentro de uma string
		String assuntos2 = "";
		// Caso tenha conte�do dentro da prova
		if (provas.size() > 0) {
			// Faz um la�o da prova
			for (int i = 0; i < provas.size(); i++) {
				assuntos2 = "";
				// Faz a inst�ncia do assunto
				assunto = new Assunto();
				// Faz a inst�ncia de ProvaAssunto
				provasAssunto = new ArrayList<ProvaAssunto>();
				// Lista os assuntos pela prova
				provasAssunto = provaAssuntoDAO.listarPorProva(provas.get(i)
						.getIdProva());
				// Faz um la�o de provasAssunto
				for (int j = 0; j < provasAssunto.size(); j++) {
					// Recupera o assunto completo enviando o seu id
					assunto = assuntoDAO.buscarPorId(provasAssunto.get(j)
							.getAssunto().getIdAssunto());
					// Seta o nome do assunto em provasAssunto
					provasAssunto.get(j).getAssunto()
							.setNomeAssunto(assunto.getNomeAssunto());
					// Envia o nome para a string assuntos2
					assuntos2 += provasAssunto.get(j).getAssunto()
							.getNomeAssunto()
							+ ";";
				}

				// Se tiver conte�do dentro da string
				if (assuntos2.length() > 0) {
					// Retira a v�rgula da �ltima posi��o
					assuntos2 = assuntos2.substring(0, assuntos2.length() - 1);
				}
				// Adiciona a string na lista
				assuntos.add(assuntos2);

				// Adiciona a data dentro da lista
				datasProva.add(provas.get(i).getDataRealizacaoProva());
				// Adiciona o tipo dentro da lista
				tiposProva.add(provas.get(i).getTipoProva());
				// Adicionaos ids da prova dentro da lista
				idsProva.add(String.valueOf(provas.get(i).getIdProva()));

			}
		}

		// Envia os valores para a p�gina jsp
		model.addAttribute("provas", provas);
		model.addAttribute("idsProvas", idsProva);
		model.addAttribute("datas", datasProva);
		model.addAttribute("tipos", tiposProva);
		model.addAttribute("assuntos", assuntos);
		model.addAttribute("provaSize", provas.size());

		return "listaCorrigirProvas";
	}

}