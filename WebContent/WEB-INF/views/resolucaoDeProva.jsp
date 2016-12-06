<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resolução de prova</title>

<script type="text/javascript">
	var tempo = new Number();
	// Tempo em segundos
	var tempo = ${duracao};
	function start() {
		if ((tempo - 1) >= 0) {
			// pega os minutos
			var min = parseInt(tempo/60);
			// pega os segundos
			
			 if(min < 10){
				min = "0"+min;
				min = min.substr(0, 2);
			}
			if(seg <=9){
				seg = "0"+seg;
			}

			var seg = tempo%60;
			tempo = tempo - 1;
			horaImprimivel = min + ':' + seg;
			document.getElementById("contador").value = horaImprimivel;
			setTimeout('start();', 1000);
		} else {
			tempo = 0;
		}

	}

	function botao(botaoSelecionado) {
		var escolha = botaoSelecionado.value;
		document.getElementById("escolhaBotao").value = escolha;
		
	}

	/* 
	 *
	 * FUNÇÃO PARA NÃO VOLTAR PÁGINAS
	 *
	 *
	 * */

	(function(window) {
		'use strict';

		var noback = {

			//globals
			version : '0.0.1',
			history_api : typeof history.pushState !== 'undefined',

			init : function() {
				window.location.hash = '#no-back';
				noback.configure();
			},

			hasChanged : function() {
				if (window.location.hash == '#no-back') {
					window.location.hash = ' ';
					//mostra mensagem que não pode usar o btn volta do browser
					if ($("#msgAviso").css('display') == 'none') {
						$("#msgAviso").slideToggle("slow");
					}
				}
			},

			checkCompat : function() {
				if (window.addEventListener) {
					window.addEventListener("hashchange", noback.hasChanged,
							false);
				} else if (window.attachEvent) {
					window.attachEvent("onhashchange", noback.hasChanged);
				} else {
					window.onhashchange = noback.hasChanged;
				}
			},

			configure : function() {
				if (window.location.hash == '#no-back') {
					if (this.history_api) {
						history.pushState(null, ' ', ' ');
					} else {
						window.location.hash = ' ';
						// mostra mensagem que não pode usar o btn volta do browser
						if ($("#msgAviso").css('display') == 'none') {
							$("#msgAviso").slideToggle("slow");
						}
					}
				}
				noback.checkCompat();
				noback.hasChanged();
			}
		};

		// AMD support 
		if (typeof define === 'function' && define.amd) {
			define(function() {
				return noback;
			});
		}
		// For CommonJS and CommonJS-like 
		else if (typeof module === 'object' && module.exports) {
			module.exports = noback;
		} else {
			window.noback = noback;
		}
		noback.init();
	}(window));

	
</script>


</head>
<body onload="start();">
	<form action="salvaRespostas">

		<div id="sessao">
			<p>Numero de questões: ${nQuestoes + 1}</p>
			<input type="hidden" value="l" id="escolhaBotao" name="escolhaBotao">
			<p>
			
			<input type="hidden" name="alternativaSelecionada">
				<strong>Corpo da questão</strong>
			<p>
			<p>${questao.corpoQuestao }</p>
			<p>
				<strong>Alternativas</strong>
			</p>
			<c:forEach items="${alternativas }" var="alt">
				<input type="radio" value="${alt.idAlternativa }"
					name="altSelecionada"> ${alt.corpoAlternativa }
			</c:forEach>
			<br> <input type="text" id="contador" name="cronometro">
			<input type="submit" value="proxima"
				onclick="botao(this);javascript:window.clear.history(0);"> <input
				type="submit" value="voltar" onClick="botao(this);">
		</div>
	</form>
</body>
</html>