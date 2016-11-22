<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADD PROVA AGENDADA</title>

<!-- DATA MINIMA JAVASCRIPT-->
<script type="text/javascript">
window.onload=function(){
	var hoje = new Date();
	var dia = hoje.getDate();
	var mes = hoje.getMonth()+1; //January is 0!
	var ano = hoje.getFullYear();
	 if(dia<10){
		 dia='0'+dia
	    } 
	    if(mes<10){
	    	mes='0'+mes
	    } 

	hoje = ano+'-'+mes+'-'+dia;
	document.getElementById("minDateTwo").setAttribute("min", hoje);
	}
	
function dataAtual(){
	var hoje = new Date();
	var dia = hoje.getDate();
	var mes = hoje.getMonth()+1; //January is 0!
	var ano = hoje.getFullYear();
	 if(dia<10){
		 dia='0'+dia
	    } 
	    if(mes<10){
	    	mes='0'+mes
	    } 

	hoje = ano+'-'+mes+'-'+dia;
	document.getElementById("minDate").setAttribute("min", hoje);
	}
	
function mudarInputs(aqui) {
	if (aqui.options[aqui.selectedIndex].value == 1) {
		document.getElementById("dataInicio").style = "display: inline;";
		document.getElementById("dataTermino").style = "display: inline;";
		document.getElementById("horaInicio").style = "display: none;";
		document.getElementById("horaTermino").style = "display: none";
		document.getElementById("dataRealizacao").style = "display: none";
	} else {
		document.getElementById("dataInicio").style = "display: none;";
		document.getElementById("dataTermino").style = "display: none;";
		document.getElementById("horaInicio").style = "display: inline;";
		document.getElementById("horaTermino").style = "display: inline";
		document.getElementById("dataRealizacao").style = "display: inline";
	}
}

	
</script>
<!-- FIM DATA MINIMA JAVASCRIPT-->

</head>
<body>
	<br /><br />
	ADD PROVA AGENDADA
	<br /><br /><br />

	<form method="get" action="adicionarProvaAgendada" novalidate>
		<select onchange="mudarInputs(this)">
			<option value="0">Escolha um método de aplicação</option>
			<option value="1">por data</option>
			<option value="2">por dia</option>
		</select><br><br>
		<div id="dataInicio" style="display: none;">Data de inicio:<br />
		<input id="minDateTwo" type="date" name="dataInicio" max="2050-01-01" />
		<br /><br /></div>
		
		<div id="dataTermino" style="display: none;">Data de finalização:<br />
		<input onclick="dataAtual()" id="minDate" type="date" name="dataTermino" max="2050-01-01" />
		<br /><br /></div>
		
		<div id="horaInicio" style="display: none;">hora de inicio:<br />
		<input type="time" name="horaInicioJSP" onblur="converte(this);"/>
		<br /><br /></div>
		
		<div id="horaTermino" style="display: none;">hora de Termino:<br />
		<input type="time" name="horaTerminoJSP" onblur="converte2(this);"/>
		<br /><br /></div>
		
		
		<div id="dataRealizacao" style="display: none;">Data de realização:<br />
		<input onclick="dataAtual()" id="minDate" type="date" name="dataRealizacao" max="2050-01-01" />
		<br /><br /></div>
		
		<strong>Turma a ser aplicada:</strong>
		<select name="idTurma">
			<option value=0>--SELECIONE UMA TURMA--</option>
		<c:forEach items="${LTurmas}" var="t">
			<option value="${t.idTurma}">${t.nomeTurma}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<strong>Prova a ser aplicada:</strong>
		<select name="idProva">
			<option value="0">--SELECIONE UMA PROVA--</option>
		<c:forEach items="${LProvas}" var="p">
			<option value="${p.idProva}">${p.nomeProva}</option>
		</c:forEach>
		</select>
		<br /><br />
		
		<input type="submit" value="Cadastrar" />
	</form>
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>