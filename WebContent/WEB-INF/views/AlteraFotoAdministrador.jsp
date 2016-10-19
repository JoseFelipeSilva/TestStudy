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
	ALTERANDO FOTO DE ${adm.nome}
	<br /><br /><br />
	
	<form method="post" enctype="multipart/form-data" action="alterandoFotoDeAdm">
		<input type="hidden" name="idAdm" value="${adm.idAdm}" />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
		
		<input type="submit" value="Alterar foto" />
	</form>
	
</body>
</html>