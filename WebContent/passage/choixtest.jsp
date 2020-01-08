<%@page import="java.util.List"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Test"%>
<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@page import="fr.eni_ecole.qcm.controleur.passage.TestControleur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "choixtest");
%>
<script>
	$(function() {
		$( "#radio" ).buttonset();
	});
</script>
<jsp:include page="./erreur.jsp"></jsp:include>
<div id="radio" style="padding:20px;text-align: center">
	<h3>Veuillez sélectionner un test à passer puis validez</h3>
	<%
		List<Test> listeTestsDisponibles = TestControleur.getListTests(QCMSession.getStagiaire());
		for(Test t: listeTestsDisponibles)
		{
	%>
				<input type="radio" id="testChoisi<%=t.getId() %>" name="testChoisi" value="<%=t.getId()%>"/><label for="testChoisi<%=t.getId() %>" ><%=t.getLibelle() %></label>
	<%
		}
	%>
</div>
<br/>
<jsp:include page="./bouton.jsp"></jsp:include>
