<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<br /><br />
	ADD NOVO ALUNO
	<br /><br /><br />

	<form method="post" enctype="multipart/form-data" action="adicionarAluno">
		<strong>Nome do aluno:</strong><br />
		<input type="text" name="nomeAluno" maxlength="100" required/>
		<br /><br />
		
		<strong>Email do aluno:</strong><br />
		<input type="email" name="emailAluno" maxlength="255" required/>
		<br /><br />
		
		<strong>RG do aluno:</strong><br />
		<input type="text" name="rgAluno" maxlength="12" required/>
		<br /><br />
		
		<strong>Sexo do aluno:</strong><br />
		<input type="radio" name="sexoAluno" value="masc" checked/>Masculino<br />
		<input type="radio" name="sexoAluno" value="femi"/>Feminino
		<br /><br />
		
		<strong>Data de nascimento:</strong><br />
		<input type="date" name="nascAluno" required/>
		<br /><br />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
				
		<strong>Escola atrelada:</strong>
		<select name="idTurma">
			<option value=0>--SELECIONE UMA TURMA--</option>
		<c:forEach items="${LTurmas}" var="t">
			<option value="${t.idTurma}">${t.nomeTurma}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />		
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>