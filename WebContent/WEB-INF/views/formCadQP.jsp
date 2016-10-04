<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar questão</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.google.com/p/jquery-cascade"></script>

<script type="text/javascript">
	function consultaDisciplinas() {
		// Recupera o id da lista de selects de Assunto
		var idDisciplina = document
				.getElementById("idDisciplina");
		// Recupera o nome do valor selecionado na lista de Disciplina
		var selectedValue = idDisciplina.options[idDisciplina.selectedIndex].text;
		// Insere a disciplina selecionada em outro form para enviá-la e compará-la no banco de dados
		var aux = document.getElementById("aux");
		aux.value = selectedValue;
		document.getElementById("botao").click();

	}
	
	function verifica() {

		// Recupera o id da lista de selects de Assunto
		var idMateria = document.getElementById("idMateria");
		// Recupera o nome do valor selecionado na lista de Assunto
		var valorSelecionadoAssunto = idMateria.options[idMateria.selectedIndex].text;
		// Recupera o id da lista de selects de Disciplina
		var idDisciplina = document
				.getElementById("selectBoxDisciplina");
		// Recupera o nome do valor selecionado na lista de Disciplina
		var valorSelecionadoDisciplina = idDisciplina.options[idDisciplina.selectedIndex].text;
		// Compara e faz a validação se a Disciplina não foi selecionada
		if (valorSelecionadoDisciplina == "Selecione uma Disciplina") {
			alert("Disciplina inválida");
			return false;
		}
		// Compara e faz a validação se o Assunto não foi selecionado
		if (valorSelecionadoAssunto == "Selecione um Assunto") {
			alert("Assunto inválido");
			return false;
		}
		return false;
	}
</script>
</head>
<body>
	<form action="adicionaQP">
		<strong>Titulo: </strong> <input type="text" name="tituloQuestao" required /><br />
		
		<strong>Corpo: </strong> <input type="text" name="corpoQuestao" required /><br />
		
		<strong>Disciplina</strong><select name="nomeDisciplina" id="idDisciplina" onchange="consultaDisciplinas();">
					<c:forEach items="${disc}" var="disc">
							<option value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
						</c:forEach>
					</select></br>
					
		<form action="consultarAssuntos">
		<input id="aux" type="text" name="nome" hidden="true"> <input
			id="botao" type="submit" hidden="true">
	</form>
	
	<label>Assunto</label> <select id="idMateria">
				<option value="${assunto.id}">Selecione um Assunto</option>
				<c:forEach items="${assuntos}" var="assunto">
					<option value="${assunto.id}">${assunto.nome}</option>
				</c:forEach>
			</select>
	
					
		<strong>Dificuldade: </strong> <input type="text" name="dificuldadeQuestao" required /><br />
		
		<strong>Tipo da questão: </strong><br /> <input type="radio" name="tipoQuestao"
			value="obj" required />Objetiva<br /> <input type="radio"
			name="tipoQuestao" value="diss" required />Dissertativa<br />
			
			
			<input type="submit" value="enviar">
	
	</form>
</body>
</html>