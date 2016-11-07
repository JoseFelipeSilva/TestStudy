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
					var selecionadasAntigo = document.getElementById("materiaSelecionada").value;
					if (selecionadasAntigo == undefined) {
						selecionadasAntigo = ' ';
					}
					var selecionadasNovo = selecionadasAntigo + att.value + ",";
					document.getElementById("materiaSelecionada").value = selecionadasNovo;
				} else {
					aux--;
					var	selecionadasAntigo = document.getElementById("materiaSelecionada").value.split(",");
					var	selecionadasNovo = [0];
					var aux2 = 0;
					for (var selecionada = 0; selecionada <	selecionadasAntigo.length; selecionada++) {
						if(selecionada != att.value) {
							selecionadasNovo[aux2];
							aux2++;
						}
					}
					for (var i = aux2 - 1; i > 0; i--) {
						document.getElementById("materiaSelecionada").value = selecionadasNovo[i] + ",";
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
									onStepChanged : function(event, currentIndex) {
										// passa os valores de um section para o outro através de campos hidden
										document.getElementById("nomeProva1").value = document
												.getElementById("nomeProva").value;
										document.getElementById("nQuestoes1").value = document
												.getElementById("nQuestoes").value;
										document.getElementById("diss1").value = document.getElementById("diss").value;
										document.getElementById("alt1").value = document.getElementById("alt").value;
										for (var d = 0; d < 10; d++) {
											if (document.getElementById("d"+d).checked) {	
											document.getElementById("dificuldadeDE").value = document.getElementById("d"+d).value;
											}
											if (document.getElementById("a"+d).checked) {												
												document.getElementById("dificuldadeATE").value = document.getElementById("a"+d).value;
												}
										}
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

				<strong>Tipos de questões</strong>
				<div id="tipoQuestoes" name="idTipoQuestoes">
					<input type="checkbox" value="diss" id="diss">Dissertativa
					<input type="checkbox" value="alt" id="alt">Alternativa
				</div>


				<div id="radios" style="float: left;">
					<strong>Dificuldade da questão DE: </strong><br /> <input
						type="radio" name="dificuldaded" id="d0" value="1"
						checked="checked" />1 <input type="radio" name="dificuldaded"
						id="d1" value="2" />2 <input type="radio" name="dificuldaded"
						id="d2" value="3" />3 <input type="radio" name="dificuldaded"
						id="d3" value="4" />4 <input type="radio" name="dificuldaded"
						id="d4" value="5" />5 <input type="radio" name="dificuldaded"
						id="d5" value="6" />6 <input type="radio" name="dificuldaded"
						id="d6" value="7" />7 <input type="radio" name="dificuldaded"
						id="d7" value="8" />8 <input type="radio" name="dificuldaded"
						id="d8" value="9" />9 <input type="radio" name="dificuldaded"
						id="d9" value="10" />10<br />
				</div>



				<div id="radios" style="float: left;">
					<strong>Dificuldade da questão ATÉ: </strong><br /> <input
						type="radio" name="dificuldadea" id="a0" value="1"
						checked="checked" />1 <input type="radio" name="dificuldadea"
						id="a1" value="2" />2 <input type="radio" name="dificuldadea"
						id="a2" value="3" />3 <input type="radio" name="dificuldadea"
						id="a3" value="4" />4 <input type="radio" name="dificuldadea"
						id="a4" value="5" />5 <input type="radio" name="dificuldadea"
						id="a5" value="6" />6 <input type="radio" name="dificuldadea"
						id="a6" value="7" />7 <input type="radio" name="dificuldadea"
						id="a7" value="8" />8 <input type="radio" name="dificuldadea"
						id="a8" value="9" />9 <input type="radio" name="dificuldadea"
						id="a9" value="10" />10<br />
				</div>
			</section>

			<h2>Second Step</h2>
			<section>
				<strong>Disciplina</strong>
				<div id="idDisciplina" style="overflow-y: scroll; height: 80px">
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
				<div id="idMateria" style="overflow-y: scroll; height: 80px">
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
			<div id="ThirdStep">
			<c:import url='TESTEtransa'></c:import>
			</div>
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
								class="form-control" name="materiaSelecionada"
								id="materiaSelecionada" value=", " /> <input type="hidden"
								name="diss1" id="diss1"> <input type="hidden"
								name="alt1" id="alt1"> <input type="hidden"
								id="dificuldadeDE" name="dificuldadeDE" value="0"> <input
								type="hidden" id="dificuldadeATE" name="dificuldadeATE"
								value="0">
						</div>
					</div>
				</form>
			</section>
		</div>
	</div>

</body>
</html>