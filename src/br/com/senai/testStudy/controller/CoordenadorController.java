package br.com.senai.testStudy.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.CoordenadorDAO;
import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.EscolaCliente;

@Controller
public class CoordenadorController {
	private final CoordenadorDAO dao;
	private final EscolaClienteDAO edao;

	@Autowired
	public CoordenadorController(EscolaClienteDAO edao, CoordenadorDAO dao) {
		this.dao = dao;
		this.edao = edao;
	}

	@RequestMapping("newCoordenador")
	public String addPageCoordenador(Model model) {
		model.addAttribute("LEscola", edao.listar());
		return "addCoordenador";
	}

	@RequestMapping("adicionamentoCoordenador")
	public String addCoordenador(EscolaCliente escola, Coordenador coord,
			MultipartFile arquivo) {
		if (!arquivo.isEmpty()) {
			try {
				coord.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		coord.setEscola(escola);
		dao.adicionar(coord);
		return "sucessoPage";
	}
	
	@RequestMapping("backToIndexCoordenador")
	public String backIndexAdm(){
		return "indexCoordenador";
	}

	@RequestMapping("listagemCoordenador")
	public String listaOfCoordenadores(Model model) {
		model.addAttribute("LCoord", dao.listar());
		return "listaCoordenador";
	}

	@RequestMapping("removerCoordenador")
	public String removerCoordenador(Model model, Coordenador coord) {
		dao.remover(coord);
		model.addAttribute("LCoord", dao.listar());
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
			EscolaCliente escola) {
		coord.setEscola(escola);
		dao.alterar(coord);
		model.addAttribute("LCoord", dao.listar());
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
			Coordenador coord) {
		if (!arquivo.isEmpty()) {
			try {
				coord.setFoto(arquivo.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.alterarPhoto(coord);
		model.addAttribute("LCoord", dao.listar());
		return "listaCoordenador";
	}
}
