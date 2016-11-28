<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>questão</title>
</head>
<script type="text/javascript">
function aparecerProvas() {
	document.getElementById("notify").style = "display: inline;";
}
</script>
<body>
	<br /><br />
	<h1>PAGINA ALUNO</h1>
	<br /><br /><br />
	<a onclick="aparecerProvas()">${notificacoes.size()}</a>
	<br /><br />
	<div id="notify" style="display: none;">
		<c:forEach items="${notificacoes}" var="i">
		<div>
		<a href="fazerProvaAgendada?idProvaAgendada=${i.idProvaAgendada}">${i.prova.nomeProva }</a>
		</div>
		</c:forEach>
	</div>
	<br /><br /><br />
	<a href="logoff">Sair</a>
</body>
</html>