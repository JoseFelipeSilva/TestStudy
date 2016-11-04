<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>jQeury.steps Demos</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="resources/normalize.css">
<link rel="stylesheet" href="resources/main.css">
<link rel="stylesheet" href="resources/jquery.steps.css">
<script src="resources/modernizr-2.6.2.min.js"></script>
<script src="resources/jquery-1.9.1.min.js"></script>
<script src="resources/jquery.cookie-1.3.1.js"></script>
<script src="resources/jquery.steps.js"></script>
</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->


	<div class="content">
		<h1>Basic Demo</h1>

		<script>
		var aux;
			function consultaMateria(materiaDisciplina, cbxSelecionado) {
				// Recupera o id da lista de selects de Assunto
				var idDisciplina = document.getElementById("idDisciplina");
				// Insere a disciplina selecionada em outro form para enviá-la e compará-la no banco de dados
				for (var i = 0; i < materiaDisciplina.length; i++) {
					if (materiaDisciplina[i] == cbxSelecionado.value) {
						i++;
						if (cbxSelecionado.checked) {
						document.getElementById("cbxMateria"+materiaDisciplina[i]).style = "display: list-item;";
						} else {
						document.getElementById("cbxMateria"+materiaDisciplina[i]).style = "display: none;";
						}
					}else{
						i++;
					}
				}
			}
			
			function materiasSelecionadas(att) {
				if (att.checked) {
					aux++;
					var selecionadasAntigo = document.getElementById("hvMETAL").value;
					if (selecionadasAntigo == undefined) {
						selecionadasAntigo = ' ';
					}
					var selecionadasNovo = selecionadasAntigo + att.value + ", ";
					document.getElementById("hvMETAL").value = selecionadasNovo;
				} else {
					aux--;
					var	selecionadasAntigo = document.getElementById("hvMETAL").value.split(",");
					var	selecionadasNovo = [0];
					var aux2 = 0;
					for (var selecionada = 0; selecionada <	selecionadasAntigo.length; selecionada++) {
						if(selecionada != att.value) {
							selecionadasNovo[aux2];
							aux2++;
						}
					}
					for (var i = aux2 - 1; i > 0; i--) {
						document.getElementById("hvMETAL").value = selecionadasNovo[i] + ", ";
					}
				}
			}
			
			$(function() {
				$("#wizard")
						.steps(
								{
									headerTag : "h2",
									bodyTag : "section",
									transitionEffect : "slideLeft",
									// ao finalizar todas as steps dá um submit na form salvaNaSessao
									onFinished : function() {
										$("#salvaNaSessao").submit()
									},
									onStepChanged : function() {
										// passa os valores de um section para o outro através de campos hidden
										document.getElementById("nomeProva1").value = document
												.getElementById("nomeProva").value;
										document.getElementById("nQuestoes1").value = document
												.getElementById("nQuestoes").value;
										// neste ponto começa a lógica para aparecer os filtros de disciplinas da prova!!
										// declaração da variável do numero de disciplinas da prova
										// declaração da variável onde consta a div que deve ser preenchida com os campos de filtro!
										var divFiltro = $(".input_filtro");
									}
								});
			});
		</script>

		<div id="wizard">
			<h2>First Step</h2>
			<section>
				<div class="form-group">
					<label class="col-xs-3 control-label">Numero de questões</label>
					<div class="col-xs-5">
						<input type="text" class="form-control" name="nQuestoes"
							id="nQuestoes" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">Nome da prova</label>
					<div class="col-xs-5">
						<input type="text" class="form-control" name="nomeProva"
							id="nomeProva" />
					</div>
				</div>

			</section>

			<h2>Second Step</h2>
			<section>
				<strong>Disciplina</strong>
				<div name="idDisciplina" id="idDisciplina" style="overflow-y: scroll; height: 50px">
					<c:forEach items="${disci}" var="disc">
						<c:if
							test="${disc.idDisciplina == disciplinaSelecionada.disciplina.idDisciplina}">
							<input type="checkbox" checked="checked"
								onchange="consultaMateria(${materia}, this);"
								value="${disc.idDisciplina}">${disc.nomeDisciplina}
						</c:if>
						<c:if
							test="${disc.idDisciplina != disciplinaSelecionada.disciplina.idDisciplina}">
							<div>
								<input type="checkbox"
									onchange="consultaMateria(${materia}, this);"
									value="${disc.idDisciplina}">${disc.nomeDisciplina}</div>
						</c:if>
					</c:forEach>
				</div>
				<strong>Materia</strong>
				<div id="idMateria" name="idMateria" style="overflow-y: scroll; height: 50px">
					<c:forEach items="${materia}" var="m">
						<div style="display: none;" id="cbxMateria${m.idMateria}">
							<input type="checkbox" onchange="materiasSelecionadas(this);"
								value="${m.idMateria}">${m.nomeMateria}</div>
					</c:forEach>
				</div>
				<br>
			</section>

			<h2>Third Step</h2>
			<section>
				<div class="input_filtro"></div>
			</section>

			<h2>Forth Step</h2>
			<section>
				<p>Quisque at sem turpis, id sagittis diam. Suspendisse
					malesuada eros posuere mauris vehicula vulputate. Aliquam sed sem
					tortor. Quisque sed felis ut mauris feugiat iaculis nec ac lectus.
					Sed consequat vestibulum purus, imperdiet varius est pellentesque
					vitae. Suspendisse consequat cursus eros, vitae tempus enim euismod
					non. Nullam ut commodo tortor.</p>
				<form id="salvaNaSessao">
					<div class="form-group">
						<div class="col-xs-5">
							<input type="hidden" class="form-control" name="nQuestoes"
								id="nQuestoes1" /> <input type="hidden" class="form-control"
								name="nomeProva" id="nomeProva1" /> <input type="hidden"
								class="form-control" name="hvMETAL" id="hvMETAL" />
						</div>
					</div>
				</form>
			</section>
		</div>
	</div>

</body>
</html>