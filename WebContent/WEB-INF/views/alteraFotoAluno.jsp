<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ALTERAR FOTO</title>
</head>
<body>
	<br /><br />
	ALTERANDO FOTO DE ${al.nomeAluno}
	<br /><br /><br />
	
	<form method="post" enctype="multipart/form-data" action="alterandoFotoDeAluno">
		<input type="hidden" name="idAluno" value="${al.idAluno}" />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
		
		<input type="submit" value="Alterar foto" />
	</form>

	<br /><br />
	<a href="backToListAlunos">Cancelar</a>
	
</body>
</html>