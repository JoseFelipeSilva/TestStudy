<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LISTA DE COORDENADOR</title>
</head>
<body>
	<br /><br />
	LISTA DE COORDENADORES
	<br /><br /><br />
	
	<table border="1">
		<tr>
			<th>ID</th>
			<th>FOTO</th>
			<th>NOME</th>
			<th>EMAIL</th>
			<th>ESCOLA ATRELADA</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach items="${LProfs}" var="p">
		<tr>
			<td>${p.idProfessor}</td>
			<td><a href="alteraPhotoProfessor?idProfessor=${p.idProfessor}"><img src="data:image/jpeg;base64, ${p.foto64}" id="fotoPerfil" height="100px" width="140px"></a></td>
			<td>${p.nome}</td>
			<td>${p.email}</td>
			<td>${p.escolaProfessor.nomeEmp}</td>
			<td><a href="alterarProfessor?idProfessor=${p.idProfessor}">Modificar</a></td>
			<td><a href="removerProfessor?idProfessor=${p.idProfessor}">Remover</a></td>
		</tr>
		</c:forEach>
	</table>
	
	
	<br /><br />
	<a href="backToIndexCoordenador">Voltar</a>
</body>
</html>