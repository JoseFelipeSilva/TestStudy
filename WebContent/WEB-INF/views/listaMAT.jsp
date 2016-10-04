<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de matérias</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>Disciplina</th>
			<th></th>
			<c:forEach items="${infoMAT }" var="mat">
				<tr>
					<td>${mat.idMateria }</td>
					<td>${mat.nomeMateria }</td>
					<td>${mat.disciplina.nomeDisciplina }</td>
					<td><a href="alterandoMateria?idMateria=${mat.idMateria }" />Alterar</td>
				</tr>
			</c:forEach>
		</tr>
	</table>
</body>
</html>