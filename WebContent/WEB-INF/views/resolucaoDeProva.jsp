<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resolu��o de prova</title>

<script type="text/javascript">
var tempo = new Number();
// Tempo em segundos
tempo = 300;
function startCountdown(){
    // Se o tempo n�o for zerado
    if((tempo - 1) >= 0){
        // Pega a parte inteira dos minutos
        var min = parseInt(tempo/60);
        // Calcula os segundos restantes
        var seg = tempo%60;
        // Formata o n�mero menor que dez, ex: 08, 07, ...
        if(min < 10){
            min = "0"+min;
            min = min.substr(0, 2);
        }
        if(seg <=9){
            seg = "0"+seg;
        }
        // Cria a vari�vel para formatar no estilo hora/cron�metro
        horaImprimivel = '00:' + min + ':' + seg;
        //JQuery pra setar o valor
        $("#sessao").html(horaImprimivel);
        // Define que a fun��o ser� executada novamente em 1000ms = 1 segundo
        setTimeout('startCountdown()',1000);
        // diminui o tempo
        tempo--;
    // Quando o contador chegar a zero faz esta a��o
    } else {
        window.open('../controllers/logout.php', '_self');
    }

}

// Chama a fun��o ao carregar a tela

startCountdown();

</script>


</head>
<body onload="startCountdown();">
	<form action="salvaRespostas">
	<!-- Campo hidden que � um contador para aparecer uma quest�o diferente -->
	<c:set var="i" ></c:set>

	<input type="hidden" name="i" value="${i+1 }">
		<div id="sessao">
			<p>Numero de quest�es: ${nQuestoes + 1}</p>
		
			<p><strong>Corpo da quest�o</strong><p>
			<p>${questao.corpoQuestao }</p>
			<p><strong>Alternativas</strong></p>
			<c:forEach items="${alternativas }" var="alt">
				<input type="radio" value="${alt.idAlternativa }" name="idAlternativa"> ${alt.corpoAlternativa }
			</c:forEach>
		<input type="submit" value="pr�xima">
	</div>
	</form>
</body>
</html>