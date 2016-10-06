package br.com.senai.testStudy.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.model.DisciplinaMateriaWS;

@RestController
@RequestMapping("service/materia")
public class WebServiceMateria {

	@Autowired
	DisciplinaDAO dao;

	@RequestMapping(value = "/lista", method = RequestMethod.GET, headers = "accept=application/json;charset=utf-8")
	public List<DisciplinaMateriaWS> lista(Model model) {
		model.addAttribute("disc", dao.disciplinaMateria());
		return dao.disciplinaMateria();

	}

}
