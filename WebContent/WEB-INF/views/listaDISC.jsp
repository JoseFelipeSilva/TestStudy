<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de disciplina</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th></th>
			<th></th>
			
			<c:forEach items="${listaDISC }" var="disc">
				<tr>
					<td>${disc.idDisciplina }</td>
					<td>${disc.nomeDisciplina }</td>
					<td><a href="removendoDisc?idDisciplina=${disc.idDisciplina }" />Remover</td>
					<td><a href="alterandoDisc?idDisciplina=${disc.idDisciplina }" />Alterar</td>
				</tr>				
			</c:forEach>
		</tr>
	</table>
</body>
</html>