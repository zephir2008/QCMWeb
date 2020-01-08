<%@page import="fr.eni_ecole.qcm.modele.passage.ReponseTraitee"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.QuestionMarqueeIterator"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.QuestionNonRepondueIterator"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.QuestionIterator"%>
<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.SectionEnCours"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.QuestionSelectionnee"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	QuestionIterator qi = QCMSession.getQuestionIterator();
	QuestionSelectionnee qs = qi.questionCourante();
	SectionEnCours sectionEnCours = qi.sectionCourante();
	String numero= qi.getNumeroQuestionCourante()+" / "+qi.getNombreQuestion();
	
	String navigation="";
	if(qi instanceof QuestionNonRepondueIterator)
	{
		navigation="<label style=\"color: red;\">Liste des questions non répondues</label>";
	}
	else if(qi instanceof QuestionMarqueeIterator)
	{
		navigation="<label style=\"color:orange;\">Liste des questions marquées</label>";
	}
	else
	{
		navigation="<label style=\"color:green;\">Liste complète des questions</label>";
	}
		
		
%>
<h2 style="display: inline;">
	Test <label style="color:gray"><%=QCMSession.getTestEnCours().getLeTestAssocie().getLibelle() %></label>
	- Section <label style="color:gray"><%=sectionEnCours.getLaSectionAssociee().getLibelle() %></label>
	- <%=navigation %>
</h2>
<h3 class="ui-widget-header ui-corner-all">Question <label id="numero"><%=numero %></label></h3>



<div>

	<div>
<%		String marquee = "";
		if(qs.estMarquee()){
			marquee="checked=\"checked\"";
		}%>
		<h3 style="text-align: left;">Question&nbsp;:<input type="checkbox" name="marque" title="Marquer cette question" <%=marquee%>/></h3>
		<%=qs.getLaQuestionAssociee().getEnonceRiche()%>
	</div>


<%	if(qs.getLaQuestionAssociee().getIllustration()!=null && !qs.getLaQuestionAssociee().getIllustration().equals("")){%>
		<br/>
		<img style="border: solid 2px green;" src="<%=qs.getLaQuestionAssociee().getIllustration()%>"/>
		<br/>
<%	}%>
	<br/>
	<h3 style="text-align: left;">Réponses&nbsp;:</h3>

<%	String typeSelection="checkbox";
	if(qs.hasUneSeuleReponse()){
		typeSelection="radio";
	}
	for(ReponseTraitee rt:qs.getListeReponseTraitee()){
		String checked="";
		if(rt.estSelectionnee()){
			checked="checked=\"checked\"";
		}%>

		<div>
			<input type="<%=typeSelection%>" name="reponse" value="<%=rt.getLaReponseAssociee().getId()%>" <%=checked%>>
			<%=rt.getLaReponseAssociee().getEnonceRiche()%>
		</div>
		<br/>

<%	}%>

</div>
