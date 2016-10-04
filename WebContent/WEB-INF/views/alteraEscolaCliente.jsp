<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ALTERA ESCOLA CLIENTE PAGE</title>
</head>
<body>
	<br /><br />
	ALTERAÇÃO DE DADOS DE ESCOLA CLIENTE
	<br /><br /><br />
	
	<form method="post" action="alteracaoEscolaCliente" >
		<input type="hidden" name="idEmp" value="${altEscola.idEmp}" />
	
		<strong>Nome da escola:</strong><br />
		<input type="text" maxlength="50" name="nomeEmp" value="${altEscola.nomeEmp}" required />
		<br /><br />
		
		<strong>Email da escola:</strong><br />
		<input type="email" maxlength="255" name="emailEmp" value="${altEscola.emailEmp}" required/>
		<br /><br />
		
		<strong>CNPJ da escola:</strong><br />
		<input type="text" maxlength="18" name="cnpjEmp" value="${altEscola.cnpjEmp}" required/>
		<br /><br />
		
		<strong>Telefone da escola:</strong><br />
		<input type="text" maxlength="14" name="telefoneEmp" value="${altEscola.telefoneEmp}" required/>
		<br /><br />
		
		<strong>Nome empresarial da escola:</strong><br />
		<input type="text" maxlength="100" name="nomeEmpresarialEmp" value="${altEscola.nomeEmpresarialEmp}" required/>
		<br /><br />
		
		<input type="submit" value="Alterar" />
	</form>
	
	<br /><br />
	<a href="backToListOfSchools">Cancelar</a>
</body>
</html>