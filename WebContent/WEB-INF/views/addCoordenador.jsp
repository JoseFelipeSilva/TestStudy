<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADD COORDENADOR</title>


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
	ADD COORDENADOR 
	<br /><br /><br />
	
	<form method="post" enctype="multipart/form-data" action="adicionamentoCoordenador">
		<strong>Nome do Coordenador:</strong><br />
		<input type="text" name="nome" maxlength="100" required/>
		<br /><br />
		
		<strong>Email do Coordenador:</strong><br />
		<input type="text" name="email" maxlength="255" required/>
		<br /><br />
		
		<strong>Senha do Coordenador:</strong><br />
		<input type="password" name="senha" maxlength="50" required/>
		<br /><br />
		
		<strong>CPF do Coordenador:</strong><br />
		<input type="text" name="cpf" maxlength="14" required/>
		<br /><br />
		
		<strong>RG do Coordenador:</strong><br />
		<input type="text" name="rg" maxlength="12" required/>
		<br /><br />
		
		<strong>Data de nascimento:</strong><br />
		<input id="datefield" type="date" name="nascimento" min="1930-01-01" required/>
		<br /><br />
		
		<strong>Sexo do Coordenador:</strong><br />
		<input type="radio" name="sexo" value="masc" checked/>Masculino<br />
		<input type="radio" name="sexo" value="femi"/>Feminino
		<br /><br />
		
		<strong>Foto de perfil:</strong><br />
		<input type="file" name="arquivo" accept="image/jpeg" required/>
		<br /><br />
				
		<strong>Escola atrelada:</strong>
		<select name="idEmp">
			<option value=0>--SELECIONE UMA ESCOLA--</option>
		<c:forEach items="${LEscola}" var="e">
			<option value="${e.idEmp}">${e.nomeEmp}</option>
		</c:forEach>
		</select>
		<br /><br />
	
		<input type="submit" value="Cadastrar" />
	</form>
	
	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
</body>
</html>