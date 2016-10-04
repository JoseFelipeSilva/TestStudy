<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ALTERAR TURMA</title>
</head>
<body>
	<br /><br />
	ADD TURMA ${t.nomeTurma}
	<br /><br /><br />
	
	<form method="post" action="alteracaoTurma">
		<input type="hidden" value="${t.idTurma}" name="idTurma" />
	
		<strong>Nome da turma:</strong><br />
		<input type="text" name="nomeTurma" value="${t.nomeTurma}" maxlength="50" />
		<br /><br />
		
		<strong>Escola atrelada:</strong>
		<select name="idEmp">
			<option value=0>--SELECIONE UMA ESCOLA--</option>
		<c:forEach items="${LEscola}" var="e">
			<option value="${e.idEmp}" ${e.idEmp==t.escolaTurma.idEmp?'selected':''}>${e.nomeEmp}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Alterar" />
	</form>
	
	<br /><br />
	<a href="backToListOfTurma">Cancelar</a>
</body>
</html>