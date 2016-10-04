<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD PROVA</title>
</head>
<body>
	<br /><br />
	ADICIONAR PROVA
	<br /><br /><br />
	
	<form method="post" action="adicionarProva">
		<strong>Nome da prova:</strong><br />
		<input type="text" name="nomeProva" maxlength="50" required/>
		<br /><br />
		
		<strong>Numero de questões:</strong>
		<input type="number" min="10" max="99" name="nQuestoes" required />
		<br /><br />
		
		<strong>Dificuldade da prova:</strong>
		<select name="dificuldade">
			<option value="facil">FACÍL</option>
			<option value="intermediario">INTERMEDIARIO</option>
			<option value="dificil">DIFÍCIL</option>
		</select>
		<br /><br />
				
		<strong>Professor responsável pela prova:</strong>
		<select name="idProfessor">
			<option value="0">--SELECIONE UM PROFESSOR--</option>
		<c:forEach items="${LProfs}" var="p">
			<option value="${p.idProfessor}">${p.nome}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>