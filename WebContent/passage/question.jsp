<%@page import="fr.eni_ecole.qcm.modele.passage.SectionEnCours"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.QuestionSelectionnee"%>
<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "question");
%>
<style>
	.toggler, #slider { margin: 20px; }
	#effect { padding: 0.4em; position: relative; }
	#effect h3 { margin: 0; padding: 0.4em; text-align: center; }
</style>

<style>
	.classCompteur { position: absolute; top:5px;right:5px; }
	#idCompteur { padding: 0.4em; }
	#idTitreCompteur { margin: 0; padding: 0.4em; text-align: center;}
	#idTempsRestant { margin: 0; padding-top: 0.4em; text-align: center; }
</style>
<script>
	$(function()
	{
		//run the currently selected effect
		function runEffect() {
				// get effect type from 
				var selectedEffect = "slide";
				
				//pendant ce temps là, appel ajax au niveau de la page index.jsp pour changer le contenu de effect
				
				// most effect types need no options passed by default
				var options = {};
				// run the effect
				$( "#effect" ).hide( selectedEffect, options, 0, callback );
		};
		
		// callback function to bring a hidden box back
		function callback() {
			setTimeout(function() {
				// get effect type from 
				var selectedEffect = "slide";
				$( "#effect" ).show( selectedEffect);
			}, 0 );
		};
		
		
		$("html").keyup(function(event)
		{
			var pas=0;
			if(event.which==37 || event.which==40)
			{
				$.clickBouton('precedente',-1);
				$('#formulaire').submit();
			}
			else if(event.which==38 || event.which==39)
			{
				$.clickBouton('suivante',1);
				$('#formulaire').submit();
			}
			
		});
			
		
		$( "#slider" ).slider({
			value:1,
			min: 1,
			max: <%=QCMSession.getQuestionIterator().getNombreQuestion()%>,
			step: 0
		});
		$( "#slider" ).slider("disable");
		
		//
		$.changerQuestion =function (pas)
		{
			$( "#slider" ).slider("value",$("#slider" ).slider( "value" )+pas);
			runEffect();	
		};
		
		var delai=10000;
		$.tempsRestant =function ()
		{
			$.ajax
			({                 
				url: './temps',
				type: 'GET',
				dataType: 'text',
				success: function(html) 
				{ // je récupère la réponse du fichier
					$("#idTempsRestant").html(html);
					if(html!='FINI')
					{
						setTimeout(function(){$.tempsRestant();}, delai);
					}
				}             
			});
		};
		
		setTimeout(function(){$.tempsRestant();}, delai);
		
	});
</script>
<div id="slider"></div>

<jsp:include page="./erreur.jsp"></jsp:include>

<div class="toggler">
	<div id="effect" class="ui-widget-content ui-corner-all">
		<jsp:include page="./contenuquestion.jsp"></jsp:include>
	</div>
</div>

<div class="classCompteur">
	<div id="idCompteur" class="ui-widget-content ui-corner-all">
		<h3 id="idTitreCompteur" class="ui-widget-header ui-corner-all">Temps restant</h3>
		<h3 id="idTempsRestant">
			<%=QCMSession.getTestEnCours().getChronometre()%>
		</h3>
	</div>
</div>
<br/>
<jsp:include page="./bouton.jsp"></jsp:include>