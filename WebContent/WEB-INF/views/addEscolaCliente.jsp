<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD ESCOLA CLIENTE PAGE</title>

</head>
<body>
	<br /><br />
	ADD ESCOLA CLIENTE
	<br /><br /><br />
	
	<form method="post" action="adicionamentoEscolaCliente">
		<strong>Nome da escola:</strong><br />
		<input type="text" maxlength="50" name="nomeEmp" required />
		<br /><br />
		
		<strong>Email da escola:</strong><br />
		<input type="email" maxlength="255" name="emailEmp" required/>
		<br /><br />
		
		<strong>CNPJ da escola:</strong><br />
		<input type="text" maxlength="18" name="cnpjEmp" required/>
		<br /><br />
		
		<strong>Telefone da escola:</strong><br />
		<input type="text" maxlength="14" name="telefoneEmp" required/>
		<br /><br />
		
		<strong>Nome empresarial da escola:</strong><br />
		<input type="text" maxlength="100" name="nomeEmpresarialEmp" required/>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />
	</form>

	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
	
</body>
</html>