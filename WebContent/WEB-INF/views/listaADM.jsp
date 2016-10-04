<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de administrador</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>E-mail</th>
			<th></th>
			<th></th>
			
			<c:forEach items="${listaADM}" var="adm">
				<tr>
					<td>${adm.idAdm }</td>
					<td>${adm.nome }</td>
					<td>${adm.email }</td>
					<td><a href="removeadm?idAdm=${adm.idAdm }"/>Remover </td>
					<td><a href="alterandoadm?id=${adm.idAdm }"/>alterarAdm </td>
				</tr>			
			</c:forEach>
		</tr>
	</table>
</body>
</html>