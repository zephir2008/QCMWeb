<%@page import="fr.eni_ecole.qcm.controleur.administration.QuestionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Question"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.SectionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Section"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.TestControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Test"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "tests");
%>

<script>
$(function() {
	$("#listeTestAdmin").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/tests.jsp',                 
				data: 'id_test='+ val,
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

//Datepicker
$(function() {
		$( "#txtDateDebutDisponibilite" ).datepicker({
			dateFormat: "dd/mm/yy",
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			onSelect: function( selectedDate ) {
				$( "#txtDateFinDisponibilite" ).datepicker( "option", "minDate", selectedDate );
			}
		});
		$( "#txtDateFinDisponibilite" ).datepicker({
			dateFormat: "dd/mm/yy",
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			onSelect: function( selectedDate ) {
				$( "#txtDateDebutDisponibilite" ).datepicker( "option", "maxDate", selectedDate );
			}
		});
	});
</script>


<style>
	#listeSectionsDisponibles { list-style-type: none; margin: 0; padding: 0;margin-right: 10px; background: #eee; padding: 5px; width: 300px;}
	#listeSectionsAssociees { list-style-type: none; margin: 0; padding: 0;margin-right: 10px; background: #acdd4a; padding: 5px; width: 300px;}
	#listeSectionsDisponibles li, #listeSectionsAssociees li { margin: 5px; padding: 5px; font-size: 1.2em; width: 275px; }
</style>
<script>
	$(function() {
		$( "ul.droptrue" ).sortable({
			connectWith: "ul",
			update: function(event, ui) 
			{
				$("#txtOrdre").val($("#listeSectionsAssociees").sortable('serialize',{key:'ordre'}));
			},
			create: function(event, ui) 
			{
				$("#txtOrdre").val($("#listeSectionsAssociees").sortable('serialize',{key:'ordre'}));
			}
		});
	});
</script>




<jsp:include page="./erreur.jsp"></jsp:include>

	<label class="label_administration" for=listeTestAdmin> Test:</label>
	<select class="input_select_administration" name="listeTestAdmin" id="listeTestAdmin">
			<option value="0">Nouveau test</option>
			<%
				Test testSelectionne = null;
				if(request.getAttribute("id_test")!=null)
				{
					testSelectionne=TestControleur.getTest((Integer)request.getAttribute("id_test"));
				}
				else if(request.getParameter("id_test")!=null)
				{
					testSelectionne=TestControleur.getTest(Integer.parseInt(request.getParameter("id_test")));
				}
				
				if(testSelectionne==null)
				{
					testSelectionne=new Test();
				}
				
				for(Test t:TestControleur.getListTests())
				{
				%>
					<option value="<%=t.getId()%>" 
												<%
													if(t.getId()==testSelectionne.getId())
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
	<label class="label_administration" for="txtLibelle">Libellé:</label><input class="input_text_administration" type="text" name="txtLibelle" id="txtLibelle" value="<% out.write((testSelectionne.getLibelle()==null ? "" : testSelectionne.getLibelle()));%>"/> 
	<br/>
	<label class="label_administration" for="listeSeuilReussite">Seuil de réussite:</label>
	<select name="listeSeuilReussite" id="listeSeuilReussite">
		<%
			for(int i=5;i<=100;i+=5)
			{
		%>
				<option value="<%=i%>" <% out.write(testSelectionne.getSeuilReussite()==i?"selected=\"selected\"":""); %>><%=i%></option>
		<%
			}
		%>
	</select>%
	<br/>
	<label class="label_administration" for="listeTempsPassage">Temps de passage:</label>
	<select name="listeTempsPassage" id="listeTempsPassage">
		<%
			for(int i=30;i<=240;i+=30)
			{
		%>
				<option value="<%=i%>" <% out.write(testSelectionne.getTempsPassage()==i?"selected=\"selected\"":""); %>><%=i%></option>
		<%
			}
		%>
	</select>min
	<br/>
	<label class="label_administration" for="txtDateDebutDisponibilite">Disponibilité de</label>
	<input type="text" id="txtDateDebutDisponibilite" name="txtDateDebutDisponibilite" value="<% out.write(testSelectionne.getDateDebutDisponibilite()!=null?new SimpleDateFormat("dd/MM/yyyy").format(testSelectionne.getDateDebutDisponibilite()):""); %>"/>
	<label for="txtFinDisponibilite">à</label>
	<input type="text" id="txtDateFinDisponibilite" name="txtDateFinDisponibilite"  value="<% out.write(testSelectionne.getDateFinDisponibilite()!=null?new SimpleDateFormat("dd/MM/yyyy").format(testSelectionne.getDateFinDisponibilite()):""); %>"/>inclus
	<br/>
	<label class="label_administration" > Sections associées:</label>
	<br/>
	<div style="margin-left:160px;">
		<input type="hidden" id="txtOrdre" name="txtOrdre"/>
		<ul id="listeSectionsAssociees" class='droptrue'>
			<%
				//ajout de toutes les sections déjà associées au test
	    		List<Section> sectionsAssociees = SectionControleur.getListSections(testSelectionne);
			%>
			<%
				for(Section s: sectionsAssociees)
				{
			%>
					<li class="ui-state-default" id="section_<%=s.getId()%>">
						<%=s.getLibelle() %>
						<select id="nombreQuestionSection_<%=s.getId()%>" name="nombreQuestionSection_<%=s.getId()%>">
							<%
								List<Question> listeQuestion = QuestionControleur.getListeQuestions(s);
								for(int i=0;i<=listeQuestion.size();i++)
								{
							%>
								<option value="<%=i%>"  <% out.write(s.getNombreQuestionAUtiliser()==i?"selected=\"selected\"":""); %>><%=i%></option>
							<%
								}
							%>
						</select> Question(s)/<%=listeQuestion.size() %>
					</li>
			<%
				}
			%>
		</ul>
	</div>
	<label class="label_administration" > Sections disponibles:</label>
	<br/>
	<div style="margin-left:160px;">
		<ul id="listeSectionsDisponibles" class='droptrue'>
			<%
				//ajout des sections restantes
	    		List<Section> sectionsRestantes = SectionControleur.getListSectionsNonAssociees(testSelectionne);
			%>
			<%
				for(Section s: sectionsRestantes)
				{
			%>
					<li class="ui-state-default" id="section_<%=s.getId()%>">
						<%=s.getLibelle() %>
						<select id="nombreQuestionSection_<%=s.getId()%>" name="nombreQuestionSection_<%=s.getId()%>">
							<%
								List<Question> listeQuestion = QuestionControleur.getListeQuestions(s);
								for(int i=0;i<=listeQuestion.size();i++)
								{
							%>
								<option value="<%=i%>"><%=i%></option>
							<%
								}
							%>
						</select> Question(s)/<%=listeQuestion.size() %>
					</li>
			<%
				}
			%>
		</ul>
	</div>
	<br/>
	<br/>
<jsp:include page="./bouton.jsp"></jsp:include>