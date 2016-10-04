<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alterar adm</title>
</head>
<body>
<form action="alteraADM" enctype="multipart/form-data" method="post">
	<p>
		<strong> </strong> <input type="hidden" name="idAdm"
			value="${buscaId.idAdm}" required /><br />
	</p>
	<p>
		<strong>Nome: </strong> <input type="text" name="nome"
			value="${buscaId.nome}" required /><br />
	</p>
	<p>
		<strong>Email: </strong> <input type="email" name="email"
			value="${buscaId.email }" required /><br />
	</p>
	<p>
		<strong>CPF: </strong> <input type="cpf" name="cpf"
			value="${buscaId.cpf }" required /><br />
	</p>
	
	<p>
		<strong>RG: </strong> <input type="rg" name="rg"
			value="${buscaId.rg }" required /><br />
	</p>
	<p>
		<strong>Data de nascimento: </strong> <input type="date" name="nascimento"
			value="${buscaId.nascimento }" required /><br />
	</p>
	<p>
		<strong>Sexo: </strong><br /> 
		<input type="radio" name="sexo" ${buscaId.sexo != 'Masc' ? 'checked' : '' }/> Masculino<br />
		 <input type="radio" name="sexo" ${buscaId.sexo != 'Fem' ? '' : 'checked' } value="Fem" />Feminino<br />
	</p>
	<input type="submit" value="enviar">
	</form>
</body>
</html>