<%@page import="fr.eni_ecole.qcm.controleur.administration.PromotionControleur"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.StagiaireControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Promotion"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Stagiaire"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "stagiaires");
%>

<script>
$(function() {
	$("#listePromotionAdminStagiaire").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/stagiaires.jsp',                 
				data: 'id_promotion='+ val,
				dataType: 'html',                 
				success: function(html) 
				{ // je récupère la réponse du fichier
					var selected = $('#tabs').tabs('option', 'selected');
					$("#ui-tabs-"+selected).html(html);
				}
			});
		}     
	});
	
	$("#listeStagiaire").on('change', function() 
	{         
		var promo=$("#listePromotionAdminStagiaire").val();
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/stagiaires.jsp',                 
				data: 'id_promotion='+promo+'&id_stagiaire='+ val,
				dataType: 'html',                 
				success: function(html) 
				{ // je récupère la réponse du fichier
					var selected = $('#tabs').tabs('option', 'selected');
					$("#ui-tabs-"+selected).html(html);
				}
			});
		}     
	}); 
});
</script>

<jsp:include page="./erreur.jsp"></jsp:include>

<div>
	<label class="label_administration" for="listePromotionAdminStagiaire"> Promotion:</label>
	<select class="input_select_administration" name="listePromotionAdminStagiaire" id="listePromotionAdminStagiaire">
			<option value="0">Sans promotion</option>
			<%
				Promotion promotionSelectionnee = null;
				if(request.getAttribute("id_promotion")!=null)
				{
					promotionSelectionnee=PromotionControleur.getPromotion((Integer)request.getAttribute("id_promotion"));
				}
				else if(request.getParameter("id_promotion")!=null)
				{
					promotionSelectionnee=PromotionControleur.getPromotion(Integer.parseInt(request.getParameter("id_promotion")));
				}
				
				if(promotionSelectionnee==null)
				{
					promotionSelectionnee=new Promotion();
				}
				
				for(Promotion p:PromotionControleur.getListPromotions())
				{
				%>
					<option value="<%=p.getId()%>" 
												<%
													if(p.getId()==promotionSelectionnee.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=p.getLibelle() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="listeStagiaire"> Stagiaire:</label>
	<select class="input_select_administration" name="listeStagiaire" id="listeStagiaire">
			<option value="0">Nouveau stagiaire</option>
			<%
				Stagiaire stagiaireSelectionne = null;
				if(request.getAttribute("id_stagiaire")!=null)
				{
					stagiaireSelectionne=StagiaireControleur.getStagiaire((Integer)request.getAttribute("id_stagiaire"));
				}
				else if(request.getParameter("id_stagiaire")!=null)
				{
					stagiaireSelectionne=StagiaireControleur.getStagiaire(Integer.parseInt(request.getParameter("id_stagiaire")));
				}
				
				if(stagiaireSelectionne==null)
				{
					stagiaireSelectionne=new Stagiaire();
				}
				
				for(Stagiaire s:StagiaireControleur.getListeStagiaires(promotionSelectionnee))
				{
				%>
					<option value="<%=s.getId()%>" 
												<%
													if(s.getId()==stagiaireSelectionne.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=s.getNom() %> <%=s.getPrenom() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtNom">Nom:</label><input class="input_text_administration" type="text" name="txtNom" id="txtNom" value="<% out.write((stagiaireSelectionne.getNom()==null ? "" : stagiaireSelectionne.getNom()));%>"/> 
	<br/>
	<label class="label_administration" for="txtPrenom">Prénom:</label><input class="input_text_administration" type="text" name="txtPrenom" id="txtPrenom" value="<%out.write((stagiaireSelectionne.getPrenom()==null ? "" : stagiaireSelectionne.getPrenom())); %>"/>
	<br/>
	<label class="label_administration" for="listePromotionAppartenance"> Promotion:</label>
	<select class="input_select_administration" name="listePromotionAppartenance" id="listePromotionAppartenance">
			<option value="0">Sans promotion</option>
			<%
				for(Promotion p:PromotionControleur.getListPromotions())
				{
				%>
					<option value="<%=p.getId()%>" 
												<%
													if(p.getId()==promotionSelectionnee.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=p.getLibelle() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtCourriel">Courriel:</label><input class="input_text_administration" type="text" name="txtCourriel" id="txtCourriel" value="<%out.write((stagiaireSelectionne.getCourriel()==null ? "" : stagiaireSelectionne.getCourriel()));%>"/>
	<br/>
	<label class="label_administration" for="txtLogin">Login:</label><input class="input_text_administration" type="text" name="txtLogin" id="txtLogin" value="<%out.write((stagiaireSelectionne.getLogin()==null ? "" : stagiaireSelectionne.getLogin()));%>"/>
	<br/>
	<label class="label_administration" for="txtMotDePasse">Mot de passe:</label><input class="input_text_administration" type="password" name="txtMotDePasse" id="txtMotDePasse" value="<%out.write((stagiaireSelectionne.getMotDePasse()==null ? "" : stagiaireSelectionne.getMotDePasse()));%>"/>
	
</div>
<jsp:include page="./bouton.jsp"></jsp:include>