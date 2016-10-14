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
		
		<c:forEach items="${LCoord}" var="c">
		<tr>
			<td>${c.idCoord}</td>
			<td><a href="alteraPhoto?idCoord=${c.idCoord}"><img src="data:image/jpeg;base64, ${c.foto64}" id="fotoPerfil" height="100px" width="140px"></a></td>
			<td>${c.nome}</td>
			<td>${c.email}</td>
			<td>${c.escola.nomeEmp}</td>
			<td><a href="alterarCoordenador?idCoord=${c.idCoord}">Modificar</a></td>
			<td><a href="removerCoordenador?idCoord=${c.idCoord}">Remover</a></td>
		</tr>
		</c:forEach>
	</table>
	
	
	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
</body>
</html>