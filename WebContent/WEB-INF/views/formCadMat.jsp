<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastrar matéria</title>
</head>
<body>
	<form action="cadMateria">
		<strong>Nome: </strong> <input type="text" name="nomeMateria" required /><br />
		
		<strong>Disciplina</strong><select name="idDisciplina" >
						<c:forEach items="${disc}" var="disc">
							<option value="${disc.idDisciplina}">${disc.nomeDisciplina}</option>
						</c:forEach>
					</select>
			<input type="submit" value="enviar">
	</form>
</body>
</html>