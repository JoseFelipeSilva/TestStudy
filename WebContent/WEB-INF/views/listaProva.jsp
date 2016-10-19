<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LISTA DE PROVAS</title>
</head>
<body>
	<br /><br />
	LISTA DE PROVAS
	<br /><br /><br />
	
	<table border="1">
		<tr>
			<th>ID</th>
			<th>NOME</th>
			<th>REPONSAVEL</th>
			<th>DATA DE CRIAÇÃO</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach items="${LProvas}" var="p">
		<tr>
			<td>${p.idProva}</td>
			<td>${p.nomeProva}</td>
			<td>${p.professor.nome}</td>
			<td>${p.dataProva}</td>
			<td><a href="alterarProva?idProva=${p.idProva}">Modificar</a></td>
			<td><a href="removerProva?idProva=${p.idProva}">Remover</a></td>
		</tr>
		</c:forEach>
	</table>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>