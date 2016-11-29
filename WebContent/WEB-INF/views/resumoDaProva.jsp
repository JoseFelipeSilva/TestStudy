<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resumo da prova</title>
</head>
<body>
	<table>
		<tr>
			<th>Nome da prova</th>
			<th>Numero de questoes</th>
			<th>Duração</th>
			<th>questoes</th>
			<c:forEach items="${QuestoesDaProvaParaFazer }" var="qp">
			<th>Materias</th>
			</c:forEach>
			<th></th>
			<tr>
				<td>${provaParaFazer.prova.nomeProva }</td>
				<td>${provaParaFazer.prova.nQuestoes }</td>
				<td>${provaParaFazer.duracao }</td>
				<c:forEach items="${QuestoesDaProvaParaFazer }" var="qp">
					<th>${qp.materia.nomeMateria}</th>
				</c:forEach>
				<c:forEach items="${QuestoesDaProvaParaFazer }" var="qp">
					<th>${qp.materia.nomeMateria}</th>
				</c:forEach>
				<td><a href="alteraQuestao?idQuestaoProva=${qp.idQuestaoProva }">Alterar</a></td>
			</tr>
		</tr>
	</table>
	
	<table>
		<tr>
			<th>teste</th>
			<th></th>
			<tr>
				<c:forEach items="${provaParaFazer.prova.questoes }" var="qp">
					<th>${qp.corpoQuestao}</th>
				</c:forEach>
				</tr>
		</tr>
	</table>
</body>
</html>