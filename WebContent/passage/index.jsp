<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Un QCM - Accueil passage d'un test</title>
	<link type="text/css" href="./themes/start/jquery-ui-1.8.22.custom.css" rel="stylesheet" />
	<link type="text/css" href="./themes/start/qcm.css" rel="stylesheet" />
	<script type="text/javascript" src="./javascript/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./javascript/jquery-ui-1.8.22.custom.min.js"></script>
	<script>
	$(function() {
		$('#formulaire').on('submit', function() 
		{
			var cible="#contenu";
			if($("#action").val()=="suivante" || $("#action").val()=="precedente")
			{
				cible="#effect";
			}
			
			$.ajax
			({                 
				url: $(this).attr('action'), // le nom du fichier indiqu� dans le formulaire
				type: $(this).attr('method'),// la m�thode indiqu�e dans le formulaire (get ou post)
				data: $(this).serialize(), // je s�rialise les donn�es
				dataType: 'html',
				success: function(html) 
				{ // je r�cup�re la r�ponse du fichier
					$(cible).html(html);
				}             
			}); 
			return false; // j'emp�che le navigateur de soumettre lui-m�me le formulaire
		});
	});
	</script>
	
</head>
<body>
	<form id="formulaire" method="post" action="./passage">
		<div class="leftDiv">
			<span class="ui-icon ui-icon-close" style="float:left;" title="Se d�connecter"></span>
			<%=QCMSession.getStagiaire().getPrenom() %> <%=QCMSession.getStagiaire().getNom() %> - <%=QCMSession.getStagiaire().getPromotion().getLibelle()%>
		</div>
		<br/>
		<div class="leftDiv">
			<div id="contenu">
				<jsp:include page="./choixtest.jsp"></jsp:include>
			</div>
		</div>
		
	</form>
</body>
</html>