<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>alterar</title>
</head>
<body>
	<form action="alterarDISC">
	<strong> </strong> <input type="hidden" name="idDisciplina"
			value="${infoDisc.idDisciplina }" required /><br />
			
		<strong>Nome: </strong> <input type="text" name="nomeDisciplina"
			value="${infoDisc.nomeDisciplina }" required /><br />
			<input type="submit" value="enviar">
	</form>
</body>
</html>