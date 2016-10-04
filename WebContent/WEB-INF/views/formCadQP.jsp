<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar questão</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.google.com/p/jquery-cascade"></script>

	<script type="text/javascript">	
		
		$(document).ready(function () {
			$.getJSON('http://localhost:8080/TestStudy/service/materia/lista', function (data) {
				var items = [];
				var options = '<option value="">escolha uma disciplina</option>';	
				
				$.each(data, function (key, val) {
					options += '<option value="' + val.disciplina.idDisciplina + '">' + val.disciplina.nomeDisciplina + '</option>';
				});					
				$("#idDisciplina").html(options);				
				
				$("#idDisciplina").change(function () {				
			
					var options_cidades = '';
					var str = "";					
					
					$("#idDisciplina option:selected").each(function () {
						str += $(this).text();
						
					});
					
					$.each(data, function (key, val) {
						if(val.nome == str) {							
							$.each(val.materia, function (key_city, val_city) {
								options_cidades += '<option value="' + val_city + '">' + val_city + '</option>';
							});							
						}
					});

					$("#idMateria").html(options_cidades);
					
				}).change();		
			
			});
		
		});
		
	</script>		
	
</head>
<body>
	<form action="adicionaQP">
		<strong>Titulo: </strong> <input type="text" name="tituloQuestao" required /><br />
		
		<strong>Corpo: </strong> <input type="text" name="corpoQuestao" required /><br />
		
		<strong>Disciplina</strong><select name="nomeDisciplina" id="idDisciplina">
					</select></br>
	
		<select name="nomeMateria" id="idMateria" ></select></br>
					
		<strong>Dificuldade: </strong> <input type="text" name="dificuldadeQuestao" required /><br />
		
		<strong>Tipo da questão: </strong><br /> <input type="radio" name="tipoQuestao"
			value="obj" required />Objetiva<br /> <input type="radio"
			name="tipoQuestao" value="diss" required />Dissertativa<br />
			
			
			<input type="submit" value="enviar">
	
	</form>
</body>
</html>