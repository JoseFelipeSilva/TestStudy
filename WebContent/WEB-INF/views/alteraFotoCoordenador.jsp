<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ALTERAR FOTO</title>
</head>
<body>
	<br /><br />
	ALTERANDO FOTO DE ${coordenador.nome}
	<br /><br /><br />
	
	<form method="post" enctype="multipart/form-data" action="alterandoFotoDeCoord">
		<input type="hidden" name="idCoord" value="${coordenador.idCoord}" />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
		
		<input type="submit" value="Alterar foto" />
	</form>

	<br /><br />
	<a href="backToListOfCoordenadores">Cancelar</a>
	
</body>
</html>