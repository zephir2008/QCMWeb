<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.SectionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Section"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.ThemeControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Theme"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.QuestionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Question"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "sections");
%>

<script>
$(function() {
	$("#listeSectionAdmin").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la section
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/sections.jsp',                 
				data: 'id_section='+ val,
				dataType: 'html',                 
				success: function(html) 
				{ // je récupère la réponse du fichier
					var selected = $('#tabs').tabs('option', 'selected');
					$("#ui-tabs-"+selected).html(html);
				}
			});
		}     
	}); 

	$("#listeThemeAppartenance").on('change', function() 
	{
		var questions=$("#listeQuestionsAssociees").serialize();
		var section = $("#listeSectionAdmin").val();
		var val = $(this).val(); // on récupère l'id du theme
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/sections.jsp',                 
				data: 'id_section='+ section + '&id_theme='+val +'&' +questions,
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


<link rel="stylesheet" href="./themes/start/common.css" type="text/css" />
<link rel="stylesheet" href="./themes/start/ui.multiselect.css" type="text/css" />
<script type="text/javascript" src="./javascript/multiselect/plugins/localisation/jquery.localisation-min.js"></script>
<script type="text/javascript" src="./javascript/multiselect/plugins/scrollTo/jquery.scrollTo-min.js"></script>
<script type="text/javascript" src="./javascript/multiselect/ui.multiselect.js"></script>
<script type="text/javascript">
		$(function(){
			$.localise('ui-multiselect', {language: 'fr', path: 'js/locale/'});
			$(".multiselect").multiselect();
		});
</script>


<jsp:include page="./erreur.jsp"></jsp:include>

<div>
	<label class="label_administration" for="listeSectionAdmin"> Section:</label>
	<select class="input_select_administration" name="listeSectionAdmin" id="listeSectionAdmin">
			<option value="0">Nouvelle section</option>
			<%
				Section sectionSelectionnee = null;
				if(request.getAttribute("id_section")!=null)
				{
					sectionSelectionnee=SectionControleur.getSection((Integer)request.getAttribute("id_section"));
				}
				else if(request.getParameter("id_section")!=null)
				{
					sectionSelectionnee=SectionControleur.getSection(Integer.parseInt(request.getParameter("id_section")));
				}
				
				if(sectionSelectionnee==null)
				{
					sectionSelectionnee=new Section();
				}
				
				for(Section s:SectionControleur.getListSections())
				{
				%>
					<option value="<%=s.getId()%>" 
												<%
													if(s.getId()==sectionSelectionnee.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=s.getLibelle() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtLibelle">Libellé:</label><input class="input_text_administration" type="text" name="txtLibelle" id="txtLibelle" value="<% out.write((sectionSelectionnee.getLibelle()==null ? "" : sectionSelectionnee.getLibelle()));%>"/>
	<br/>
	<br/>
	<label class="label_administration" > Questions associées:</label>
	<br/>
	<label class="label_administration" for="listeThemeAppartenance"> Thèmes:</label>
	<select class="input_select_administration" name="listeThemeAppartenance" id="listeThemeAppartenance">
			<option value="0">Aucun thème sélectionné</option>
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
	<div style="margin-left: 160px;">
		<select id="listeQuestionsAssociees" class="multiselect" multiple="multiple" name="listeQuestionsAssociees">
	        <%
	        	List<Question> questionAssociees = new ArrayList<Question>(); 
	        	if(request.getParameterValues("listeQuestionsAssociees")!=null)
	        	{
	        		String[] idQuestions = request.getParameterValues("listeQuestionsAssociees");
	        		//ajout de toutes les questions déjà sélectionnées
		        	for(String id: idQuestions)
					{
		        		Question q = QuestionControleur.getQuestion(Integer.parseInt(id));
		        		questionAssociees.add(q);
				%>
						<option value="<%=q.getId()%>" selected="selected"><%=q.getEnonceRiche() %></option>
				<%
					}
		        	
	        	}
	        	else
	        	{
		        	//ajout de toutes les questions déjà associées à la section
		        	questionAssociees = QuestionControleur.getListeQuestions(sectionSelectionnee);
					for(Question q: questionAssociees)
					{
				%>
						<option value="<%=q.getId()%>" selected="selected"><%=q.getEnonceRiche() %></option>
				<%		
					}
	        	}
	        %>
	        <%
	        	//affichage des questions correspondant au thème sélectionné pour les proposer à l'ajout
	        	for(Question questionDuTheme : QuestionControleur.getListeQuestions(themeSelectionne))
	        	{
	        		boolean estDejaAjoute = false;
	        		for(Question questionDejaAssociee:questionAssociees)
	        		{
	        			if(questionDuTheme.getId()==questionDejaAssociee.getId())
	        			{
	        				estDejaAjoute=true;
	        			}
	        		}
	        		if(!estDejaAjoute)
	        		{
				        %>
				        	<option value="<%=questionDuTheme.getId()%>" ><%=questionDuTheme.getEnonceRiche() %></option>
				        <%
	        		}
	        	}
	        %>
      	</select>
	</div>
	
    <br/>
	 
</div>
<jsp:include page="./bouton.jsp"></jsp:include>