<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alterar status da questão</title>
</head>
<body>
	<strong>Título da questão</strong><br/>
	<p>${infoQuestao.tituloQuestao }</p><br/> <br/>
	<strong>Corpo da questão</strong>
	<p>${infoQuestao.corpoQuestao }</p><br/><br/>
	
	<strong>Alternativas</strong>
	<c:forEach items = "${infoAlternativa }" var="infoAlternativa">
	<p>${infoAlternativa.corpoAlternativa } || ${infoAlternativa.certa }</p>
	</c:forEach>
</body>
</html>