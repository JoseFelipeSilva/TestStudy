<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ALTERANDO PROVA</title>
</head>
<body>
	<br /><br />
	Alterando ${p.nomeProva}
	<br /><br /><br />
	
	<form method="post" action="alterandoProva">
		<input type="hidden" name="idProva" value="${p.idProva}" />
		
		<strong>Nome da prova:</strong><br />
		<input type="text" name="nomeProva" value="${p.nomeProva}" maxlength="50" required/>
		<br /><br />
		
		<strong>Numero de questões:</strong>
		<input type="number" min="10" max="99" value="${p.nQuestoes}" name="nQuestoes" required />
		<br /><br />
		
		<strong>Dificuldade da prova:</strong>
		<select name="dificuldade">
			<option value="facil" ${p.dificuldade=='facil'?'selected':''}>FACÍL</option>
			<option value="intermediario" ${p.dificuldade=='intermediario'?'selected':''}>INTERMEDIARIO</option>
			<option value="dificil" ${p.dificuldade=='dificil'?'selected':''}>DIFÍCIL</option>
		</select>
		<br /><br />
				
		<strong>Professor responsável pela prova:</strong>
		<select name="idProfessor">
			<option value="0">--SELECIONE UM PROFESSOR--</option>
		<c:forEach items="${LProfs}" var="prof">
			<option value="${prof.idProfessor}" ${prof.idProfessor==p.professor.idProfessor?'selected':''}>${prof.nome}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Alterar" />
	</form>
	
	<br /><br />
	<a href="backToListOfProvas">Voltar</a>
</body>
</html>