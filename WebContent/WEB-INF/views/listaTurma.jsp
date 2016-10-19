<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LISTA DE TURMAS</title>
</head>
<body>
	<br /><br />
	Lista de turmas
	<br /><br /><br />
	
	<table border="1">
		<tr>
			<th>ID</th>
			<th>TURMA</th>
			<th>ESCOLA ATRELADA</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach items="${LTurmas}" var="t">
		<tr>
			<td>${t.idTurma}</td>
			<td>${t.nomeTurma}</td>
			<td>${t.escolaTurma.nomeEmp}</td>
			<td><a href="alterarTurma?idTurma=${t.idTurma}">Modificar</a></td>
			<td><a href="removerTurma?idTurma=${t.idTurma}">Remover</a></td>
		</tr>
		</c:forEach>
	</table>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>