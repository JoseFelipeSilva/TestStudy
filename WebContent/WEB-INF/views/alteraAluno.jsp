<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ALTERAR ALUNO</title>

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
	ALTERAR DADOS DE ${al.nomeAluno}
	<br /><br /><br />

	<form method="post" action="alteracaoAluno">
		<input type="hidden" name="idAluno" value="${al.idAluno}" />
	
		<strong>Nome do aluno:</strong><br />
		<input type="text" value="${al.nomeAluno}" name="nomeAluno" maxlength="100" required/>
		<br /><br />
		
		<strong>Email do aluno:</strong><br />
		<input type="email" name="emailAluno" value="${al.emailAluno}" maxlength="255" required/>
		<br /><br />
		
		<strong>RG do aluno:</strong><br />
		<input type="text" name="rgAluno" value="${al.rgAluno}" maxlength="12" required/>
		<br /><br />
		
		<strong>Senha do aluno:</strong><br />
		<input type="text" name="senhaAluno" value="${al.senhaAluno}" maxlength="50" required/>
		<br /><br />
		
		<strong>Sexo do aluno:</strong><br />
		<input type="radio" name="sexoAluno" value="masc" ${al.sexoAluno=='masc'?'checked':''}/>Masculino<br />
		<input type="radio" name="sexoAluno" value="femi" ${al.sexoAluno=='femi'?'checked':''}/>Feminino
		<br /><br />
		
		<strong>Data de nascimento:</strong><br />
		<input id="datefield" type="date" value="${al.nascAluno}" min="1990-01-01" name="nascAluno" required/>
		<br /><br />
	
				
		<strong>Escola atrelada:</strong>
		<select name="idTurma">
			<option value=0>--SELECIONE UMA TURMA--</option>
		<c:forEach items="${LTurmas}" var="t">
			<option value="${t.idTurma}" ${t.idTurma==al.turmaAluno.escolaTurma.idEmp?'selected':''}>${t.nomeTurma}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Alterar" />		
	</form>
	
	<br /><br />
	<a href="backToListAlunos">Cancelar</a>
</body>
</html>