<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resolução de prova</title>
</head>
<body>
	<form action="salvaRespostas">
	<!-- Campo hidden que é um contador para aparecer uma questão diferente -->
	<c:if test='${i == null }'>
	<c:set var="i" value="${1 }"></c:set>
	</c:if>
	<input type="hidden" name="i" value="${i+1 }">
		<div>
			<p>Numero de questões: ${nQuestoes }</p>
		
			<p><strong>Corpo da questão</strong><p>
			<p>${questao.corpoQuestao }</p>
			<p><strong>Alternativas</strong></p>
			<c:forEach items="${alternativas }" var="alt">
				<input type="radio" value="${alt.idAlternativa }" name="idAlternativa"> ${alt.corpoAlternativa }
			</c:forEach>
		<input type="submit" value="próxima">
	</div>
	</form>
</body>
</html>