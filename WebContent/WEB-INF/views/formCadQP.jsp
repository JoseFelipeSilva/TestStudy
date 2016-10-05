<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar quest�o</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.google.com/p/jquery-cascade"></script>

<script type="text/javascript">
	function consultaDisciplinas() {
		// Recupera o id da lista de selects de Assunto
		var idDisciplina = document
				.getElementById("idDisciplina");
		// Recupera o nome do valor selecionado na lista de Disciplina
		var selectedValue = idDisciplina.options[idDisciplina.selectedIndex].value;
		// Insere a disciplina selecionada em outro form para envi�-la e compar�-la no banco de dados
		var aux = document.getElementById("aux");
		aux.value = selectedValue;
		document.getElementById("botao").click();

	}
	
	function verifica() {

		// Recupera o id da lista de selects de Assunto
		var idMateria = document.getElementById("idMateria");
		// Recupera o nome do valor selecionado na lista de Assunto
		var valorSelecionadoAssunto = idMateria.options[idMateria.selectedIndex].value;
		// Recupera o id da lista de selects de Disciplina
		var idDisciplina = document
				.getElementById("idDisciplina");
		// Recupera o nome do valor selecionado na lista de Disciplina
		var valorSelecionadoDisciplina = idDisciplina.options[idDisciplina.selectedIndex].text;
		// Compara e faz a valida��o se a Disciplina n�o foi selecionada
		if (valorSelecionadoDisciplina == "Selecione uma Disciplina") {
			alert("Disciplina inv�lida");
			return false;
		}
		// Compara e faz a valida��o se o Assunto n�o foi selecionado
		if (valorSelecionadoAssunto == "Selecione um Assunto") {
			alert("Assunto inv�lido");
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
		
		<strong>Disciplina</strong><select name="idDisciplina" id="idDisciplina" onchange="consultaDisciplinas();">
							<option value="0">Selecione uma disciplina</option>
					<c:forEach items="${disc}" var="disc">
					<c:if test="${disc.idDisciplina == disciplinaSelecionada.disciplina.idDisciplina}">
						<option selected="selected" value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
					</c:if>
					<c:if test="${disc.idDisciplina != disciplinaSelecionada.disciplina.idDisciplina}">
						<option value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
					</c:if>
						</c:forEach>
					</select></br>
					
		
	
	<label>Materia</label> <select id="idMateria" name="idMateria" >
				<option value="0">Selecione um Assunto</option>
				<c:forEach items="${materia}" var="m">
					<option value="${m.idMateria}">${m.nomeMateria}</option>
				</c:forEach>
			</select>
	
					
		<strong>Dificuldade: </strong> <input type="text" name="dificuldade" required /><br />
		
		<strong>Tipo da quest�o: </strong><br /> <input type="radio" name="tipoQuestao"
			value="obj" required />Objetiva<br /> <input type="radio"
			name="tipoQuestao" value="diss" required />Dissertativa<br />
			
			
			<input type="submit" value="enviar">
	
	</form>
	<form action="consultarMateria">
		<input id="aux" type="text" name="idDisciplina" hidden="true"> <input
			id="botao" type="submit" hidden="true">
	</form>
</body>
</html>