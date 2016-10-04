<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>alterar questão</title>
</head>
<body>
<form action="alteraQP">
		<input type="hidden" name="idQuestaoProva" value="${qp.idQuestaoProva }">
		<strong>Titulo: </strong> <input type="text" name="tituloQuestao" value="${qp.tituloQuestao }"
		 required /><br />
		
		<strong>Corpo: </strong> <input type="text" name="corpoQuestao" required 
		value = "${qp.corpoQuestao }" /><br />
		
		<strong>Tipo da questão: </strong><br />
		 <input type="radio" name="tipoQuestao"	${qp.tipoQuestao != 'obj' ? '' : 'checked' } />	Objetiva<br />
		 <input type="radio" name="tipoQuestao" ${qp.tipoQuestao != 'diss' ? '' : 'checked' }  />Dissertativa<br />
			
			
			<input type="submit" value="enviar">
	
	</form>
</body>
</html>