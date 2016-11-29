package br.com.senai.testStudy.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.CoordenadorDAO;
import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.util.Util;

@Controller
public class CoordenadorController {
	private final CoordenadorDAO dao;
	private final EscolaClienteDAO edao;
	private final LogDAO ldao;

	@Autowired
	public CoordenadorController(EscolaClienteDAO edao, CoordenadorDAO dao, LogDAO ldao) {
		this.dao = dao;
		this.edao = edao;
		this.ldao = ldao;
	}

	@RequestMapping("newCoordenador")
	public String addPageCoordenador(Model model) {
		model.addAttribute("LEscola", edao.listar());
		return "addCoordenador";
	}

	@RequestMapping("adicionamentoCoordenador")
	public String addCoordenador(EscolaCliente escola, Coordenador coord,
			MultipartFile arquivo, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				coord.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		coord.setEscola(escola);
		dao.adicionar(coord);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}
	
	@RequestMapping("backToIndexCoordenador")
	public String backIndexAdm(){
		return "indexCoordenador";
	}

	@RequestMapping("listagemCoordenador")
	public String listaOfCoordenadores(Model model, HttpSession session) {
		model.addAttribute("LCoord", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaCoordenador";
	}

	@RequestMapping("removerCoordenador")
	public String removerCoordenador(Model model, Coordenador coord, HttpSession session) {
		dao.adicionaMorto(coord.getIdCoord());
		dao.remover(coord);
		model.addAttribute("LCoord", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaCoordenador";
	}

	@RequestMapping("alterarCoordenador")
	public String altPageCoord(Model model, Coordenador coord,
			EscolaCliente escola) {
		model.addAttribute("coord", dao.buscarID(coord.getIdCoord()));
		model.addAttribute("LSchools", edao.listar());
		
		return "alteraCoordenador";
	}

	@RequestMapping("alterandoCoordenador")
	public String alterarCoordenador(Model model, Coordenador coord,
			EscolaCliente escola, HttpSession session) {
		coord.setEscola(escola);
		dao.alterar(coord);
		model.addAttribute("LCoord", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaCoordenador";
	}

	@RequestMapping("backToListOfCoordenadores")
	public String backToListOfCoords(Model model) {
		model.addAttribute("LCoord", dao.listar());
		return "listaCoordenador";
	}

	@RequestMapping("alteraPhoto")
	public String pageAltPhoto(Model model, Coordenador coord) {
		model.addAttribute("coordenador", dao.buscarID(coord.getIdCoord()));
		return "alteraFotoCoordenador";
	}

	@RequestMapping("alterandoFotoDeCoord")
	public String alterandoPhotoCoordenador(Model model, MultipartFile arquivo,
			Coordenador coord, HttpSession session) {
		if (!arquivo.isEmpty()) {
			try {
				coord.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.alterarPhoto(coord);
		Util.addLog(session, ldao, this);
		model.addAttribute("LCoord", dao.listar());
		return "listaCoordenador";
	}
}
