<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
window.onload=function(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	 if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 

	today = yyyy+'-'+mm+'-'+dd;
	document.getElementById("datefield").setAttribute("max", today);
	}
</script>

</head>
<body>
	<br /><br />
	ADD NOVO ALUNO
	<br /><br /><br />

	<form method="post" enctype="multipart/form-data" action="adicionarAluno">
		<strong>Nome do aluno:</strong><br />
		<input type="text" name="nomeAluno" maxlength="100" required/>
		<br /><br />
		
		<strong>Email do aluno:</strong><br />
		<input type="email" name="email" maxlength="255" required/>
		<br /><br />
		
		<strong>RG do aluno:</strong><br />
		<input type="text" name="rgAluno" maxlength="12" required/>
		<br /><br />
		
		<strong>Sexo do aluno:</strong><br />
		<input type="radio" name="sexoAluno" value="masc" checked/>Masculino<br />
		<input type="radio" name="sexoAluno" value="femi"/>Feminino
		<br /><br />
		
		<strong>Data de nascimento:</strong><br />
		<input id="datefield" type="date" name="nascAluno" min="1990-01-01" required/>
		<br /><br />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
				
		<strong>Turma do aluno:</strong>
		<select name="idTurma">
			<option value=0>--SELECIONE UMA TURMA--</option>
		<c:forEach items="${LTurmas}" var="t">
			<option value="${t.idTurma}">${t.nomeTurma}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />		
	</form>
	
	<br /><br />
	<a href="backToIndexCoordenador">Voltar</a>
</body>
</html>