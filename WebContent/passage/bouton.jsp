<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<div id="bouton_<%=request.getAttribute("type")%>" class="centerDivSansBord">
	<input type="hidden" name="type" value="<%=request.getAttribute("type") %>"/>
	<input type="hidden" name="action" id="action"/>
	
	<%
		String display="none";
		if(request.getAttribute("type")=="question")
		{
			display="inline";
		}
	%>
			<input type="submit" value="Précédente" id="precedente" style="display:<%=display%>;" onclick="jQuery.clickBouton('precedente',-1)"/>
			<input type="submit" value="Suivante" id="suivante" style="display:<%=display%>;" onclick="jQuery.clickBouton('suivante',1)"/>		
			<input type="submit" value="Récapitulatif" id="recapitulatif" style="display:<%=display%>;" onclick="jQuery.clickBouton('recapitulatif')"/>
	<%
		display="none";
		if(request.getAttribute("type")=="recapitulatif")
		{
			display="inline";
		}
	%>
			<input type="submit" value="Revoir toutes les questions" id="toutesLesQuestions" style="display:<%=display%>;" onclick="jQuery.clickBouton('toutesLesQuestions')"/>
			<%
				int nbNonRepondue=0;
				int nbMarquee=0;
				if(QCMSession.getTestEnCours()!=null)
				{
					nbNonRepondue=QCMSession.getTestEnCours().getNombreQuestionNonRepondue();
					nbMarquee=QCMSession.getTestEnCours().getNombreQuestionMarquee();
				}
			%>
			<input type="submit" value="Revoir les <%=nbNonRepondue %> question(s) non répondue(s)" id="lesQuestionsNonRepondues" style="display:<%=display%>;" onclick="jQuery.clickBouton('lesQuestionsNonRepondues')"/>
			<input type="submit" value="Revoir les <%=nbMarquee %> question(s) marquée(s)" id="lesQuestionsMarquees" style="display:<%=display%>;" onclick="jQuery.clickBouton('lesQuestionsMarquees')"/>
			<br/>
			<input type="submit" value="Valider" id="valider" style="display:<%=display%>;" onclick="jQuery.clickBouton('valider')"/>
	<%
		
		display="none";
		if(request.getAttribute("type")=="choixtest")
		{
			display="inline";	
		}
	%>
			<input type="submit" value="Valider" id="valider" style="display:<%=display%>;" onclick="jQuery.clickBouton('valider')"/>
</div>

<script>
$(function() {
	$( "input:submit", "#bouton_<%=request.getAttribute("type")%>").button();
	
	
	$.clickBouton =function (action,sensNavigation)
	{
		$("#action").val(action);
		if(sensNavigation!=undefined)
		{
			$.changerQuestion(sensNavigation);	
		}
	}
});


</script>