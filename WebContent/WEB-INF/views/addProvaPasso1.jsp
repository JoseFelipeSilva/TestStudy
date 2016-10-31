<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADD PROVA</title>
</head>
<body>
	<br /><br />
	ADICIONAR PROVA
	<br /><br /><br />
	
	<form method="post" action="provaPasso2">
		<strong>Nome da prova:</strong><br />
		<input type="text" name="nomeProva" maxlength="50" required/>
		<br /><br />
		
		<strong>Numero de questões:</strong>
		<input type="number" min="10" max="99" name="nQuestoes" required />
		<br /><br />
		
		<input type="submit" value="próximo" />
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>