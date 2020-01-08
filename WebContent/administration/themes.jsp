<%@page import="fr.eni_ecole.qcm.controleur.administration.ThemeControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Theme"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "themes");
%>

<script>
$(function() {
	$("#listeThemeAdmin").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/themes.jsp',                 
				data: 'id_theme='+ val,
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
	<label class="label_administration" for="listeThemeAdmin"> Thème:</label>
	<select class="input_select_administration" name="listeThemeAdmin" id="listeThemeAdmin">
			<option value="0">Nouveau thème</option>
			<%
				Theme themeSelectionne = null;
				if(request.getAttribute("id_theme")!=null)
				{
					themeSelectionne=ThemeControleur.getTheme((Integer)request.getAttribute("id_theme"));
				}
				else if(request.getParameter("id_theme")!=null)
				{
					themeSelectionne=ThemeControleur.getTheme(Integer.parseInt(request.getParameter("id_theme")));
				}
				
				if(themeSelectionne==null)
				{
					themeSelectionne=new Theme();
				}
				
				for(Theme t:ThemeControleur.getListeThemes())
				{
				%>
					<option value="<%=t.getId()%>" 
												<%
													if(t.getId()==themeSelectionne.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=t.getLibelle() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtLibelle">Libellé:</label><input class="input_text_administration" type="text" name="txtLibelle" id="txtLibelle" value="<% out.write((themeSelectionne.getLibelle()==null ? "" : themeSelectionne.getLibelle()));%>"/> 
</div>
<jsp:include page="./bouton.jsp"></jsp:include>