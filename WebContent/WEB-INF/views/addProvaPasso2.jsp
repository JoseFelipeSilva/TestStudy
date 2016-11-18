<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adicionar Prova</title>
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="resources/normalize.css">
<link rel="stylesheet" href="resources/main.css">
<link rel="stylesheet" href="resources/jquery.steps.css">
<script src="resources/modernizr-2.6.2.min.js"></script>
<script src="resources/jquery-1.9.1.min.js"></script>
<script src="resources/jquery.cookie-1.3.1.js"></script>
<script src="resources/jquery.steps.js"></script>

<script type="text/javascript">
	function alterando(id,fazer,item) {
		if (item.checked) {
			if (fazer == "add") {
				document.getElementById(fazer+id).style="border: green medium solid;";
				var selecionadasAntigo = document.getElementById("questaoAdd").value;
				if (selecionadasAntigo == '') {
					selecionadasAntigo = ' ';
				}
				if (selecionadasAntigo == ' ') {
					var selecionadasNovo = selecionadasAntigo + id;
				} else {
					var selecionadasNovo =  selecionadasAntigo+ "," + id;
				}				
				document.getElementById("questaoAdd").value = selecionadasNovo;
			} else if(fazer == "rem"){
				document.getElementById(fazer+id).style="border: red medium solid;";
				var selecionadasAntigo = document.getElementById("questaoRem").value;
				if (selecionadasAntigo == '') {
					selecionadasAntigo = ' ';
				}
				if (selecionadasAntigo == ' ') {
					var selecionadasNovo = selecionadasAntigo + id;
				} else {
					var selecionadasNovo =  selecionadasAntigo+ "," + id;
				}
				document.getElementById("questaoRem").value = selecionadasNovo;
			}else {
				alert("error");
			}
		} else {
			if (fazer == "add") {
				document.getElementById(fazer+id).style="border: 1px aqua;";
				var	selecionadasAntigo = document.getElementById("questaoAdd").value.split(",");
				var	selecionadasNovo = [0];
				var aux2 = 0;
				for (var selecionada = 0; selecionada <	selecionadasAntigo.length; selecionada++) {
					if(selecionadasAntigo[selecionada] != item.value) {
						selecionadasNovo[aux2];
						aux2++;
					}
				}
				for (var i = aux2 -1; i > 0; i--) {
					if(i == 1){
						document.getElementById("questaoAdd").value = selecionadasNovo[i];
					}else{
						document.getElementById("questaoAdd").value = selecionadasNovo[i] + ",";
					}
					
				}
			} else if(fazer == "rem"){
				document.getElementById(fazer+id).style="border: 1px blue;";
				aux--;
				var	selecionadasAntigo = document.getElementById("questaoRem").value.split(",");
				var	selecionadasNovo = [0];
				var aux2 = 0;
				for (var selecionada = 0; selecionada <	selecionadasAntigo.length; selecionada++) {
					if(selecionadasAntigo[selecionada] != item.value) {
						selecionadasNovo[aux2];
						aux2++;
					}
				}
				for (var i = aux2 -1; i > 0; i--) {
					if(i == 1){
						document.getElementById("questaoRem").value = selecionadasNovo[i];
					}else{
						document.getElementById("questaoRem").value = selecionadasNovo[i] + ",";
					}
				}
			}else {
				alert("error");
			}
		}
	}
</script>
</head>
<body>
	<div style="float: left;">
		<table style="border: 1px blue;" border="1px">
			<tr>
				<th>ID</th>
				<th>titulo</th>
				<th></th>
				<c:forEach items="${questoes }" var="i">
					<c:forEach items="${i }" var="qp">
						<tr id="rem${qp.idQuestaoProva }" value="rem${qp.idQuestaoProva }">
							<td>${qp.idQuestaoProva }</td>
							<td>${qp.tituloQuestao }</td>
							<td>${qp.visualizacaoQuestao }</td>
							<td>${qp.usoQuestao }</td>
							<td><input type="checkbox"
								onchange="alterando(${qp.idQuestaoProva },'rem',this)">Remover</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tr>
		</table>
	</div>
	<div></div>
	<div style="float: left;">
		<table style="border: 1px aqua;" border="1px">
			<tr>
				<th>ID</th>
				<th>titulo</th>
				<th></th>
				<c:forEach items="${tetas }" var="qp2">
					<tr id="add${qp2.idQuestaoProva }">
						<td>${qp2.idQuestaoProva }</td>
						<td>${qp2.tituloQuestao }</td>
						<td>${qp2.visualizacaoQuestao }</td>
						<td>${qp2.usoQuestao }</td>
						<td><input type="checkbox"
							onchange="alterando(${qp2.idQuestaoProva },'add',this)">Add</td>

					</tr>

				</c:forEach>
			</tr>
		</table>


		
	</div>
	<form action="attQuestoesProva">
		<input type="hidden" id="questaoAdd" name="questaoAdd"> <input
			type="hidden" name="questaoRem" id="questaoRem"> <input
			type="submit" value="att">
	</form>
	<form action="salvarQuestoesNaProva">
		<input type="submit" value="Salvar">
	</form>
</body>
</html>