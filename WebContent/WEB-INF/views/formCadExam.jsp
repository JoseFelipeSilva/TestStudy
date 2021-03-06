<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de examinador</title>
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
	<form action="adicionaExam" enctype="multipart/form-data" method="post">
	<p>
		<strong>Nome: </strong> <input type="text" name="nome"
			placeholder="Insira nome completo" required /><br />
	</p>
	<p>
		<strong>Email: </strong> <input type="email" name="email"
			placeholder="exemplo@exemplo.com" required /><br />
	</p>
	<p>
		<strong>CPF: </strong> <input type="cpf" name="cpf"
			placeholder="Sem pontuação" required /><br />
	</p>
	
	<p>
		<strong>RG: </strong> <input type="rg" name="rg"
			placeholder="Sem pontuação" required /><br />
	</p>
	<p>
		<strong>Data de nascimento: </strong> <input id="datefield" min="1930-01-01" type="date" name="nascimento"
			placeholder="Sem pontuação" required /><br />
	</p>
	<p>
		<strong>Senha: </strong> <input type="password" name="senha"
			placeholder="" required /><br />
	</p>
	<p>
	<input type="file" name="arquivo" accept="image/jpeg" required>
	</p>
	<p>
		<strong>Sexo: </strong><br /> <input type="radio" name="sexo"
			value="Masc" required />Masculino<br /> <input type="radio"
			name="sexo" value="Fem" required />Feminino<br />
	</p>
	
		<strong>Disciplina</strong><select name="idDisciplina" >
						<c:forEach items="${disciplina}" var="disc">
							<option value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
						</c:forEach>
					</select>
	<input type="submit" value="enviar">
	</form>
	
	
	<br /><br />
	<a href="backToIndexAdm">Voltar</a>
</body>
</html>