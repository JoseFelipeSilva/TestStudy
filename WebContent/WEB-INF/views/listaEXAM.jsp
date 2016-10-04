<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Examinador</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>E-mail</th>
			<th></th>
			<th></th>
			
			<c:forEach items="${listaEXAM}" var="exam">
				<tr>
					<td>${exam.idExaminador }</td>
					<td>${exam.nome }</td>
					<td>${exam.email }</td>
					<td><a href="removeexam?idExaminador=${exam.idExaminador }"/>Remover </td>
					<td><a href="alterandoexam?idExaminador=${exam.idExaminador }"/>alterar </td>
				</tr>			
			</c:forEach>
		</tr>
	</table>
</body>
</html>