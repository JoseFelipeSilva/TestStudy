<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alterar Materia</title>
</head>
<body>
	<form action="alterarMat">
		<input type="hidden" name="idMateria" value="${buscaId.idMateria }" required />
		<strong>Nome: </strong> <input type="text" name="nomeMateria" value="${buscaId.nomeMateria }" required /><br />
		
		<strong>Disciplina</strong><select name="idDisciplina" >
						<c:forEach items="${lista}" var="disc">
							<option value="${disc.disciplina.idDisciplina}" ${disc.disciplina.idDisciplina == buscaId.disciplina.idDisciplina ? 'selected' : '' }>${disc.disciplina.nomeDisciplina}</option>
						</c:forEach>
					</select>
			<input type="submit" value="enviar">
	</form>
</body>
</html>