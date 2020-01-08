<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   <%
   	   	String typeUtilisateur="formateur";
	   	if(!(Boolean)request.getAttribute("is_formateur"))
		{
	   		typeUtilisateur="stagiaire";
		}
   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Un QCM - Authentification <%=typeUtilisateur %>
</title>
	<link type="text/css" href="./themes/start/jquery-ui-1.8.22.custom.css" rel="stylesheet" />
	<link type="text/css" href="./themes/start/qcm.css" rel="stylesheet" />
	<script type="text/javascript" src="./javascript/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./javascript/jquery-ui-1.8.22.custom.min.js"></script>
	
	<script>
	$(function() {
		$( "input:submit, button", ".centerDiv" ).button();
	});
	</script>
	
</head>
<body>
	<form method="post" action="./authentification">
		<div class="centerDiv">
			<h1 class="titre">Authentification <%=typeUtilisateur %></h1>
			<br/>
			<jsp:include page="erreur.jsp"></jsp:include>
			<label class="label_authentification" for="login">Login:</label><input name="login" id="login" type="text"/>
			<br/>
			<label class="label_authentification" for="mdp">Mot de passe:</label><input name="mdp" id="mdp" type="password"/>
			<br/>
			<input type="submit" name="valider_<%=typeUtilisateur %>" value="Valider">
			<input type="submit" name="demande_<%=typeUtilisateur %>" value="Annuler"/>
			<input type="submit" name="accueil" value="Accueil"/>
			<br/>
		</div>
			
			
		
	</form>
</body>
</html>