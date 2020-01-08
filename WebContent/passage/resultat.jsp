<%@page import="fr.eni_ecole.qcm.modele.passage.SectionEnCours"%>
<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.TestEnCours"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "resultat");
%>

<jsp:include page="./erreur.jsp"></jsp:include>

<%
	TestEnCours testEnCours = QCMSession.getTestEnCours();
	
%>
<div style="padding-left: 10px;">
	<h3>Resultat au test "<%=testEnCours.getLeTestAssocie().getLibelle() %>"</h3>
	<%
		for(SectionEnCours s: testEnCours.getListeSectionEnCours())
		{
	%>		
			<br/>
			<h4>Section "<%=s.getLaSectionAssociee().getLibelle() %>"</h4>
			R�sultat: <%=s.getNombreBonneReponse() %> bonnes r�ponses (<%=s.getPourcentageBonneReponse() %>%)
	<%
		}
	%>
	<br>
	<h3>R�sultat global: <%=testEnCours.getNombreBonneReponse() %>/<%=testEnCours.getNombreTotalQuestion() %> bonnes r�ponses (<%=testEnCours.getPourcentageBonneReponse() %>%)</h3>
	<br/>Pourcentage attendu: <%=testEnCours.getLeTestAssocie().getSeuilReussite() %>%
	<%
		if(testEnCours.getPourcentageBonneReponse()>=testEnCours.getLeTestAssocie().getSeuilReussite())
		{
			%>
				<h2 style="color: green;">Vous avez r�ussi</h2>
			<%
		}
		else
		{
			%>
				<h2 style="color: red;">Vous avez �chou�</h2>
			<%
		}
	%>
	
</div>

