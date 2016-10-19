<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADICIONAR NOVA TURMA</title>
</head>
<body>
	<br /><br />
	ADD TURMA
	<br /><br /><br />
	
	<form method="post" action="adicionamentoTurma">
		<strong>Nome da turma:</strong><br />
		<input type="text" name="nomeTurma" maxlength="50" />
		<br /><br />
		
		<strong>Escola atrelada:</strong>
		<select name="idEmp">
			<option value=0>--SELECIONE UMA ESCOLA--</option>
		<c:forEach items="${LEscola}" var="e">
			<option value="${e.idEmp}">${e.nomeEmp}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>