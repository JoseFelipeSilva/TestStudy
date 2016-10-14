package br.com.senai.testStudy.util;

import java.util.List;

public interface MetodosBasicos<Obj> {
	public void adicionar(Obj object);

	public void remover(Obj object);

	public void alterar(Obj object);

	public List<Obj> listar();

	public Obj buscarID(Integer id);

}
