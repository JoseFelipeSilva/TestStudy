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

</head>
<body>
	<form action="adicionaQP">
		<strong>Titulo: </strong> <input type="text" name="tituloQuestao" required /><br />
		
		<strong>Corpo: </strong> <input type="text" name="corpoQuestao" required /><br />
		
		<strong>Disciplina</strong><select name="idDisciplina" id="idDisciplina">
						<c:forEach items="${disc}" var="disc">
							<option value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
						</c:forEach>
					</select></br>
	
		<select name="idMateria" disabled></select>
					
		<strong>Dificuldade: </strong> <input type="text" name="dificuldadeQuestao" required /><br />
		
		<strong>Tipo da questão: </strong><br /> <input type="radio" name="tipoQuestao"
			value="obj" required />Objetiva<br /> <input type="radio"
			name="tipoQuestao" value="diss" required />Dissertativa<br />
			
			
			<input type="submit" value="enviar">
	
	</form>
</body>
</html>