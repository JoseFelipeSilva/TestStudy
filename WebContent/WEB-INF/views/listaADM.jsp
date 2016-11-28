<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de administrador</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>FOTO</th>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Sexo</th>
			<th></th>
			<th></th>
			
			<c:forEach items="${listaADM}" var="adm">
				<tr>
					<td>${adm.idAdm }</td>
					<td><a href="alteraPhotoADM?idAdm=${adm.idAdm}"><img src="data:image/jpeg;base64, ${adm.foto64}" id="fotoPerfil" height="100px" width="140px"></a></td>
					<td>${adm.nome }</td>
					<td>${adm.email }</td>
					<td>${adm.sexo }</td>
					<td><a href="removeadm?idAdm=${adm.idAdm }">Remover</a></td>
					<td><a href="alterandoadm?id=${adm.idAdm }">alterarAdm</a></td>
				</tr>			
			</c:forEach>
		</tr>
	</table>
	
	
	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
</body>
</html>