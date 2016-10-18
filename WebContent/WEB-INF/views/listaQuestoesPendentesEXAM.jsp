<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LISTA DE QUESTÕES</title>
</head>
<body>
	<br /><br />
	LISTA DE QUESTÕES
	<br /><br /><br />
	
	<table border="1">
		<tr>
			<th>ID</th>
			<th>TITULO</th>
			<th>CORPO</th>
			<th>STATUS</th>
			<th>DIFICULDADE</th>
			<th>DISCIPLINA</th>
			<th>MATERIA</th>
			<th></th>
		</tr>
		
		<c:forEach items="${pendencias}" var="p">
		<tr>
			<td>${p.idQuestaoProva}</td>
			<td>${p.tituloQuestao}</td>
			<td>${p.corpoQuestao}</td>
			<td>${p.statusQuestao}</td>
			<td>${p.dificuldade}</td>
			<td>${p.materia.disciplina.idDisciplina}</td>
			<td>${p.materia.idMateria}</td>
			<!-- SE O STATUS DA QUESTÃO É 'ENVIADO' SIGNIFICA QUE É UMA QUESTÃO SEM ALTERAÇÕES, E DEVE IR PARA NOVAS PENDENCIAS -->
			<c:if test="${p.statusQuestao  == 'enviado'}">
			<td><a href="alterandoStatus?idQuestaoProva=${p.idQuestaoProva}">Alterar status</a></td>
			<!-- SE O STATUS DA QUESTÃO É 'EM ABERTO' SIGNIFICA QUE É UMA QUESTÃO QUE JÁ FOI SELECIONADA POR ALGUM EXAMINADOR -->
			</c:if><c:if test="${p.statusQuestao  == 'Em aberto'}">
			<td><a href="alterandoStatusAntigo?idQuestaoProva=${p.idQuestaoProva }">Alterar status</a></td>
			</c:if>
		</tr>
		</c:forEach>
	</table>
	
	
	<br /><br />
	<a href="backToIndex">Voltar</a>
</body>
</html>