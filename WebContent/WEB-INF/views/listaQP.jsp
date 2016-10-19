<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de questões</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>titulo</th>
			<th>visualização</th>
			<th>Uso da questão</th>
			<th></th>
			<c:forEach items="${qp }" var="qp">
			<tr>
				<td>${qp.idQuestaoProva }</td>
				<td>${qp.tituloQuestao }</td>
				<td>${qp.visualizacaoQuestao }</td>
				<td>${qp.usoQuestao }</td>
				<td><a href="alteraQuestao?idQuestaoProva=${qp.idQuestaoProva }">Alterar</a></td>
			</tr>
			</c:forEach>
		</tr>
	</table>
</body>
</html>