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
	window.onload = function() {
		var hoje = new Date();
		var dia = hoje.getDate();
		var mes = hoje.getMonth() + 1; //January is 0!
		var ano = hoje.getFullYear();
		var hora = hoje.getHours();
		var minutos = hoje.getMinutes();
		
		if(hora == "1" || hora == "2" 
			   || hora == "3" || hora == "4"
			   || hora == "5" || hora == "6"
			   || hora == "7" || hora == "8"
			   || hora == "9" || hora == "0"){
			hora = "0" + hora;
		}
		
		if(minutos == "1" || minutos == "2" 
			   || minutos == "3" || minutos == "4"
			   || minutos == "5" || minutos == "6"
			   || minutos == "7" || minutos == "8"
			   || minutos == "9" || minutos == "0"){
			minutos = "0" + minutos;
		}
		if (dia < 10) {
			dia = '0' + dia
		}
		if (mes < 10) {
			mes = '0' + mes
		}

		hoje = ano + '-' + mes + '-' + dia;
		document.getElementById("minDateTwo").setAttribute("min", hoje);
		document.getElementById("minDateTwo").value = ano + "-" + mes + "-"
				+ dia + "T" + hora + ":" + minutos + ":01";
		document.getElementById("minDate").value = ano + "-" + mes + "-" + dia
				+ "T" + hora + ":" + minutos + ":01";
	}

	function dataAtual() {
		var hoje = new Date();
		var dia = hoje.getDate();
		var mes = hoje.getMonth() + 1; //January is 0!
		var ano = hoje.getFullYear();
		if (dia < 10) {
			dia = '0' + dia
		}
		if (mes < 10) {
			mes = '0' + mes
		}

		hoje = ano + '-' + mes + '-' + dia;
		document.getElementById("minDate").setAttribute("min", hoje);
	}
</script>
<!-- FIM DATA MINIMA JAVASCRIPT-->

</head>
<body>
	<br />
	<br /> ADD PROVA AGENDADA
	<br />
	<br />
	<br />
	<form action="adicionarProvaAgendada">
		<div id="dataInicio">
			Data de inicio:<br /> <input id="minDateTwo" type="datetime-local"
				value="2016-11-29T00:00:01" name="dataInicio" /> <br /> <br />
		</div>

		<div id="dataTermino">
			Data de finalização:<br /> <input onclick="dataAtual()" id="minDate"
				type="datetime-local" value="2016-11-01T00:01:11" name="dataTermino" />
			<br /> <br />
		</div>

		<div>
			duração da prova:<br /> <input type="number" name="duracao" /> <br />
			<br />
		</div>


		<strong>Turma a ser aplicada:</strong> <select name="idTurma">
			<option value=0>--SELECIONE UMA TURMA--</option>
			<c:forEach items="${LTurmas}" var="t">
				<option value="${t.idTurma}">${t.nomeTurma}</option>
			</c:forEach>
		</select> <br /> <br /> <strong>Prova a ser aplicada:</strong> <select
			name="idProva">
			<option value="0">--SELECIONE UMA PROVA--</option>
			<c:forEach items="${LProvas}" var="p">
				<option value="${p.idProva}">${p.nomeProva}</option>
			</c:forEach>
		</select> <br /> <br /> <input type="submit" value="Cadastrar" />
	</form>

	<br />
	<br />
	<a href="backToIndex">Voltar</a>
</body>
</html>