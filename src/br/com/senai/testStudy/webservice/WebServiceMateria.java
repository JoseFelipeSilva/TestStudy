package br.com.senai.testStudy.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.testStudy.dao.MateriaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;

@RestController
@RequestMapping("service/materia")
public class WebServiceMateria {
	
	@Autowired
	MateriaDAO dao;
	
	/*@RequestMapping(value = "/lista", method = RequestMethod.GET, headers = "accept=application/json;charset=utf-8")
	public List<Materia> lista(Model model) {
		model.addAttribute("disc",dao.listarDisc());
		return dao.disciplinaMateria();

	}*/

}
