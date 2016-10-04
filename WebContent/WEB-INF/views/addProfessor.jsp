<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD PROFESSOR</title>
</head>
<body>
	<br /><br />
	ADD PROFESSOR
	<br /><br /><br />
	
	<form method="post" enctype="multipart/form-data" action="adicionamentoProfessor">
		<strong>Nome do Professor:</strong><br />
		<input type="text" name="nome" maxlength="100" required/>
		<br /><br />
		
		<strong>Email do Professor:</strong><br />
		<input type="text" name="email" maxlength="255" required/>
		<br /><br />
		
		<strong>Senha do Professor:</strong><br />
		<input type="password" name="senha" maxlength="50" required/>
		<br /><br />
		
		<strong>CPF do Professor:</strong><br />
		<input type="text" name="cpf" maxlength="14" required/>
		<br /><br />
		
		<strong>RG do Professor:</strong><br />
		<input type="text" name="rg" maxlength="12" required/>
		<br /><br />
		
		<strong>Data de nascimento:</strong><br />
		<input type="date" name="nascimento" required/>
		<br /><br />
		
		<strong>Sexo do Professor:</strong><br />
		<input type="radio" name="sexo" value="masc" checked/>Masculino<br />
		<input type="radio" name="sexo" value="femi"/>Feminino
		<br /><br />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
				
		<strong>Escola atrelada:</strong>
		<select name="idEmp">
			<option value=0>--SELECIONE UMA ESCOLA--</option>
		<c:forEach items="${LEscola}" var="e">
			<option value="${e.idEmp}">${e.nomeEmp}</option>
		</c:forEach>
		</select>
		<br /><br />
	
		<input type="submit" value="Cadastrar" />
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>