<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Un QCM - Accueil administration</title>
	<link type="text/css" href="./themes/start/jquery-ui-1.8.22.custom.css" rel="stylesheet" />
	<link type="text/css" href="./themes/start/qcm.css" rel="stylesheet" />
	<script type="text/javascript" src="./javascript/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./javascript/jquery-ui-1.8.22.custom.min.js"></script>
	
	
	<script>
	$(function() {
		function afficherStyleBouton()
		{
			$( "input:submit", "#bouton").button();
		};
		
		$( "#menu" ).buttonset();
		
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Impossible de charger la page..." );
				}
			},
			load: function()
			{
				$(".ui-tabs-hide").empty();
			}
		});
		
		$('#formulaire').on('submit', function() 
		{
			$.ajax
			({                 
				url: $(this).attr('action'), // le nom du fichier indiqué dans le formulaire
				type: $(this).attr('method'),// la méthode indiquée dans le formulaire (get ou post)
				data: $(this).serialize(), // je sérialise les données
				dataType: 'html',
				success: function(html) 
				{ // je récupère la réponse du fichier
					var selected = $('#tabs').tabs('option', 'selected');
					$("#ui-tabs-"+selected).html(html);
				}             
			});        
			return false; // j'empêche le navigateur de soumettre lui-même le formulaire
		});
	});
	</script>
	
</head>
<body>
	<form id="formulaire" method="post" action="./administration">
		<div class="leftDiv">
			<span class="ui-icon ui-icon-close" style="float:left;" title="Se déconnecter"></span>
			<%=QCMSession.getFormateur().getPrenom() %> <%=QCMSession.getFormateur().getNom() %> - Formateur
		</div>
		
		<br/>
		
		<div id="tabs">
			<ul>
				<li><a href="#tabAccueil">Accueil</a></li>
				<li><a href="./administration/formateurs.jsp">Formateurs</a></li>
				<li><a href="./administration/promotions.jsp">Promotions</a></li>
				<li><a href="./administration/stagiaires.jsp">Stagiaires</a></li>
				<li><a href="./administration/themes.jsp">Thèmes</a></li>
				<li><a href="./administration/questions.jsp">Questions</a></li>
				<li><a href="./administration/sections.jsp">Sections</a></li>
				<li><a href="./administration/tests.jsp">Tests</a></li>
				<li><a href="./administration/inscriptions.jsp">Inscriptions</a></li>
			</ul>
			<div id="tabAccueil">
				Bienvenue sur les pages d'administration des QCM...
			</div>
		</div>
	</form>
	
</body>
</html>