package br.com.senai.testStudy.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.testStudy.dao.AdministradorDAO;
import br.com.senai.testStudy.model.Administrador;

@Controller
public class AdmController {
	private final AdministradorDAO ADMDAO;

	@Autowired
	public AdmController(AdministradorDAO ADMDAO) {
		this.ADMDAO = ADMDAO;
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
