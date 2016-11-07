AÇÃO REALIZADA COM SUCESSO !
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
document.getElementById("teste").innerHTML = "<c:import url='/TESTEtransa2'></c:import>"
</script>
<div id="teste">
<c:forEach items="${questoes }" var="a">
	${a.idQuestao }
</c:forEach>
	
</div>