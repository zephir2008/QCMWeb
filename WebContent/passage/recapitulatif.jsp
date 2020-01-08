<%@page import="fr.eni_ecole.qcm.web.session.QCMSession"%>
<%@page import="fr.eni_ecole.qcm.modele.passage.TestEnCours"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "recapitulatif");
%>

<jsp:include page="./erreur.jsp"></jsp:include>

<jsp:include page="./bouton.jsp"></jsp:include>