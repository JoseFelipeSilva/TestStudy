<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de Examinador</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>FOTO</th>
			<th>Nome</th>
			<th>E-mail</th>
			<th></th>
			<th></th>
			
			<c:forEach items="${listaEXAM}" var="exam">
				<tr>
					<td>${exam.idExaminador }</td>
					<td><a href="alteraPhotoExaminador?idExaminador=${exam.idExaminador}"><img src="data:image/jpeg;base64,${exam.foto64}" id="fotoPerfil" height="100px" width="140px"></a></td>
					<td>${exam.nome }</td>
					<td>${exam.email }</td>
					<td><a href="removeexam?idExaminador=${exam.idExaminador}">Remover</a></td>
					<td><a href="alterandoexam?idExaminador=${exam.idExaminador}">alterar</a></td>
				</tr>			
			</c:forEach>
		</tr>
	</table>
	
	
	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
</body>
</html>