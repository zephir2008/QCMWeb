<%@page import="fr.eni_ecole.qcm.controleur.administration.FormateurControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Formateur"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "formateurs");
%>

<script>
$(function() {
	$("#listeFormateur").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id du formateur
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/formateurs.jsp',                 
				data: 'id_formateur='+ val, // on envoie $_GET['id_region']
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
	<label class="label_administration" for="listeFormateur"> Formateur:</label>
	<select class="input_select_administration" name="listeFormateur" id="listeFormateur">
			<option value="0">Nouveau formateur</option>
			<%
				Formateur formateurSelectionne = null;
				if(request.getAttribute("id_formateur")!=null)
				{
					formateurSelectionne=FormateurControleur.getFormateur((Integer)request.getAttribute("id_formateur"));
				}
				else if(request.getParameter("id_formateur")!=null)
				{
					formateurSelectionne=FormateurControleur.getFormateur(Integer.parseInt(request.getParameter("id_formateur")));
				}
				
				if(formateurSelectionne==null)
				{
					formateurSelectionne=new Formateur();
				}
				
				for(Formateur f:FormateurControleur.getListFormateurs())
				{
				%>
					<option value="<%=f.getId()%>" 
												<%
													if(f.getId()==formateurSelectionne.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=f.getPrenom() %> <%=f.getNom() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtNom">Nom:</label><input class="input_text_administration" type="text" name="txtNom" id="txtNom" value="<% out.write((formateurSelectionne.getNom()==null ? "" : formateurSelectionne.getNom()));%>"/> 
	<br/>
	<label class="label_administration" for="txtPrenom">Prénom:</label><input class="input_text_administration" type="text" name="txtPrenom" id="txtPrenom" value="<%out.write((formateurSelectionne.getPrenom()==null ? "" : formateurSelectionne.getPrenom())); %>"/>
	<br/>
	<label class="label_administration" for="txtCourriel">Courriel:</label><input class="input_text_administration" type="email" name="txtCourriel" id="txtCourriel" value="<%out.write((formateurSelectionne.getCourriel()==null ? "" : formateurSelectionne.getCourriel()));%>"/>
	<br/>
	<label class="label_administration" for="txtLogin">Login:</label><input class="input_text_administration" type="text" name="txtLogin" id="txtLogin" value="<%out.write((formateurSelectionne.getLogin()==null ? "" : formateurSelectionne.getLogin()));%>"/>
	<br/>
	<label class="label_administration" for="txtMotDePasse">Mot de passe:</label><input class="input_text_administration" type="password" name="txtMotDePasse" id="txtMotDePasse" value="<%out.write((formateurSelectionne.getMotDePasse()==null ? "" : formateurSelectionne.getMotDePasse()));%>"/>
	<br/>
	<label class="label_administration" for="ckEstResponsable">Est responsable:</label><input type="checkbox" name="ckEstResponsable" id="ckEstResponsable" <%out.write((formateurSelectionne.estResponsable() ? "checked=\"checked\"" : ""));%>/>

</div>

<jsp:include page="./bouton.jsp"></jsp:include>