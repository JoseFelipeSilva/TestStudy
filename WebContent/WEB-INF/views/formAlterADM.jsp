<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alterar adm</title>

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
		<strong>Data de nascimento: </strong> <input id="datefield" min="1930-01-01" type="date" name="nascimento"
			value="${buscaId.nascimento }" required /><br />
	</p>
	<p>
		<strong>Sexo: </strong><br /> 
		<input type="radio" name="sexo" ${buscaId.sexo != 'Masc' ? 'checked' : '' }/> Masculino<br />
		 <input type="radio" name="sexo" ${buscaId.sexo != 'Fem' ? '' : 'checked' } value="Fem" />Feminino<br />
	</p>
	<input type="submit" value="enviar">
	</form>
	
	<br /><br />
	<a href="backToListAdm">Voltar</a>
</body>
</html>