<%@page import="fr.eni_ecole.qcm.controleur.administration.ThemeControleur"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.QuestionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Theme"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Question"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "questions");
%>

<script>
$(function() {
	$("#listeTheme").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/questions.jsp',                 
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
	
	$("#listeQuestion").on('change', function() 
	{         
		var theme=$("#listeTheme").val();
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/questions.jsp',                 
				data: 'id_theme='+theme+'&id_question='+ val,
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

<link rel="stylesheet" type="text/css" href="./javascript/editeurhtml/jquery.cleditor.css" />
<script type="text/javascript" src="./javascript/editeurhtml/jquery.cleditor.js"></script>    
<script type="text/javascript" src="./javascript/editeurhtml/jquery.cleditor.min.js"></script>    
<script type="text/javascript">      
$(document).ready(function() 
{        
	$("#txtEnonceRiche").cleditor(); 
	$("#txtReponse1").cleditor();      
	$("#txtReponse2").cleditor();
	$("#txtReponse3").cleditor();
	$("#txtReponse4").cleditor();
	$("#txtReponse5").cleditor();
	$("#txtReponse6").cleditor();
	
	$( "#btIllustration" ).button({
        icons: {
            primary: "ui-icon-search"
        }
	});
	$( "#btSupprimerIllustration" ).button({
        icons: {
            primary: "ui-icon-close"
        }
	});
	
	$("#btIllustration").on('click', function() 
			{         
				$('#dialogUpload').dialog('open');
				return false;
			});
	
	
	$("#btSupprimerIllustration").on('click', function() 
			{         
				$("#imgIllustration").attr("src","");
				$("#txtIllustration").attr("value","");
				return false;
			});
	
	// Dialog
	$('#dialogUpload').dialog({
		autoOpen: false,
		width: 600,
		buttons: {"Annuler": function() {
				$(this).dialog("close");
			}
		}
	});


});    
</script>
<link rel="stylesheet" type="text/css" href="./javascript/uploader/fileuploader.css" />
<script type="text/javascript" src="./javascript/uploader/fileuploader.js"></script>    
<script>
$(document).ready(function() 
{
	var uploader = new qq.FileUploader({
	    element: document.getElementById('file-uploader'),
	    action: './upload',
	    encoding:'multipart',
	    onComplete: function(id, fileName, responseJSON)
	    {
	    		if(responseJSON.success)
    			{
	    			$("#imgIllustration").attr("src","./upload/"+responseJSON.fichier);
	    			$("#txtIllustration").attr("value","./upload/"+responseJSON.fichier);
			    	$('#dialogUpload').dialog("close");
    			}
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
	<label class="label_administration" for="listeTheme"> Thème:</label>
	<select class="input_select_administration" name="listeTheme" id="listeTheme">
			<option value="0">Sans thème</option>
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
	<label class="label_administration" for="listeQuestion"> Question:</label>
	<select class="input_select_administration" name="listeQuestion" id="listeQuestion">
			<option value="0">Nouvelle question</option>
			<%
				Question questionSelectionnee = null;
				if(request.getAttribute("id_question")!=null)
				{
					questionSelectionnee=QuestionControleur.getQuestion((Integer)request.getAttribute("id_question"));
				}
				else if(request.getParameter("id_question")!=null)
				{
					questionSelectionnee=QuestionControleur.getQuestion(Integer.parseInt(request.getParameter("id_question")));
				}
				
				if(questionSelectionnee==null)
				{
					questionSelectionnee=new Question();
				}
				
				for(Question q:QuestionControleur.getListeQuestions(themeSelectionne))
				{
				%>
					<option value="<%=q.getId()%>" 
												<%
													if(q.getId()==questionSelectionnee.getId())
													{
														out.write("selected=\"selected\"");
													}
												%>>
						<%=q.getEnonceRiche() %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<label class="label_administration" for="txtEnonceRiche">Enoncé:</label>
	<div style="margin-left: 160px;">
			<textarea name="txtEnonceRiche" id="txtEnonceRiche" value="<% out.write((questionSelectionnee.getEnonceRiche()==null ? "" : questionSelectionnee.getEnonceRiche()));%>"><% out.write((questionSelectionnee.getEnonceRiche()==null ? "" : questionSelectionnee.getEnonceRiche()));%></textarea>
	</div>
	<br/>
	<label class="label_administration" for="btIllustration">Illustration:</label>
	<button name="btIllustration" id="btIllustration">Rechercher</button>
	<button name="btSupprimerIllustration" id="btSupprimerIllustration">Supprimer</button>
	<br/><br/>
	<label class="label_administration">Visualisation:</label>
	<input type="hidden" name="txtIllustration" id="txtIllustration" value="<%out.write(questionSelectionnee.getIllustration()!=null?"questionSelectionnee.getIllustration()":"");%>"/>
	<img name="imgIllustration" id="imgIllustration" alt="Aucune illustration associée" style="border: solid 2px green;" src="<%=questionSelectionnee.getIllustration()%>"/>
	<br/><br/>
	<div id="dialogUpload" title="Télécharger une illustration">
			<div id="file-uploader">

			</div>
	</div>
	
	
	<br/>
	<label class="label_administration" for="txtPoids">Poids:</label>
	
	<select class="input_select_administration" name="listePoids" id="listePoids">
		<%
			for(int i=1;i<=10;i++)
			{
		%>
				<option value="<%=i %>"
						<%
							if(i==questionSelectionnee.getPoids())
							{
								out.write("selected=\"selected\"");
							}
						%>>
					<%=i%>
				</option>
		<%
			}
		%>
	</select>
	
	<%
		for(int i=1;i<=6;i++)
		{
	%>
	
		<br/>
		<label class="label_textarea_administration" for="ckReponse<%=i %>">Réponse <%=i %>:</label><input type="checkbox" id="ckReponse<%=i %>" name="ckReponse<%=i %>" title="Est une bonne réponse" <%out.write((questionSelectionnee.getListeReponse().size()>(i-1) &&  questionSelectionnee.getListeReponse().get(i-1).estBonne()? "checked=\"checked\"" : ""));%>/>
		<div style="margin-left: 160px;">
			<textarea name="txtReponse<%=i %>" id="txtReponse<%=i %>" value="<%out.write((questionSelectionnee.getListeReponse().size()>(i-1) ? questionSelectionnee.getListeReponse().get(i-1).getEnonceRiche() : "")); %>"><%out.write((questionSelectionnee.getListeReponse().size()>(i-1) ? questionSelectionnee.getListeReponse().get(i-1).getEnonceRiche() : "")); %></textarea>
		</div>
	
	<%
		}
	%>
</div>
<br/>
	<label class="label_administration" for="listeThemesAssocies"> Thèmes associés:</label>
	<div style="margin-left: 160px;">
		<select id="listeThemesAssocies" class="multiselect" multiple="multiple" name="listeThemesAssocies">
	        <%
	        	for(Theme t : ThemeControleur.getListeThemes())
	        	{
	        		boolean estAssocie =false;
	        		for(Theme themeAssocie: QuestionControleur.getListeThemes(questionSelectionnee))
	        		{
	        			if(themeAssocie.getId()==t.getId())
	        			{
	        				estAssocie=true;
	        			}
	        		}
	        		if(t.getId()==themeSelectionne.getId())
        			{
        				estAssocie=true;
        			}
	        %>
	        	<option value="<%=t.getId()%>" <%out.write(estAssocie?"selected=\"selected\"":""); %>><%=t.getLibelle() %></option>
	        <%
	        	}
	        %>
      	</select>
	</div>
	
    <br/>
   

<jsp:include page="./bouton.jsp"></jsp:include>