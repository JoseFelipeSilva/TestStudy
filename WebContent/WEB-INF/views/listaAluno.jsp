<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br /><br />
	LISTA DE ALUNOS
	<br /><br /><br />
	
	<table border="1">
		<tr>
			<th>ID</th>
			<th>FOTO</th>
			<th>NOME</th>
			<th>EMAIL</th>
			<th>TURMA</th>
			<th>ESCOLA</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach items="${LAlunos}" var="a">
		<tr>
			<td>${a.idAluno}</td>
			<td><a href="alteraPhotoAluno?idAluno=${a.idAluno}"><img src="data:image/jpeg;base64, ${a.foto64}" id="fotoPerfil" height="100px" width="140px"></a></td>
			<td>${a.nomeAluno}</td>
			<td>${a.emailAluno}</td>
			<td>${a.turmaAluno.nomeTurma}</td>
			<td>${a.turmaAluno.escolaTurma.nomeEmp}</td>
			<td><a href="alterarAluno?idAluno=${a.idAluno}">Modificar</a></td>
			<td><a href="removerAluno?idAluno=${a.idAluno}">Remover</a></td>
		</tr>
		</c:forEach>
	</table>
	
	<br /><br />
	<a href="backToIndexCoordenador">Voltar</a>
</body>
</html>