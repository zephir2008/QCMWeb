<%@page import="fr.eni_ecole.qcm.controleur.administration.StagiaireControleur"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Stagiaire"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.PromotionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Promotion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.eni_ecole.qcm.controleur.administration.TestControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Test"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "inscriptions");
%>

<script>
$(function() {
	$("#listeTest").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id du test
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/inscriptions.jsp',                 
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
	
	$("#listePromotion").on('change', function() 
		{
			var stagiaires=$("#listeStagiairesAssocies").serialize();
			var test = $("#listeTest").val();
			var val = $(this).val(); // on récupère l'id du theme
			if(val != '') 
			{
				$.ajax
				({                 
					url: './administration/inscriptions.jsp',                 
					data: 'id_test='+ test + '&id_promotion='+val +'&' +stagiaires,
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
	<label class="label_administration" for="listeTest"> Test:</label>
	<select class="input_select_administration" style="width:500px" name="listeTest" id="listeTest">
			<option value="0">Aucun test sélectionné</option>
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
						<%
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						%>
						<%=t.getLibelle() %> du <%=sdf.format(t.getDateDebutDisponibilite())%> au <%=sdf.format(t.getDateFinDisponibilite()) %>
					</option>
				<%
				}
			%>
	</select>
	<br/>
	<br/>
	<br/>
	<label class="label_administration" > Les inscrits:</label>
	<br/>
	<label class="label_administration" for="listePromotion"> Promotion:</label>
	<select class="input_select_administration" name="listePromotion" id="listePromotion">
			<option value="0">Aucune promotion sélectionnée</option>
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
	<div style="margin-left: 160px;">
		<select id="listeStagiairesAssocies" class="multiselect" multiple="multiple" name="listeStagiairesAssocies">
	        <%
	        	List<Stagiaire> stagiairesAssocies = new ArrayList<Stagiaire>(); 
	        	if(request.getParameterValues("listeStagiairesAssocies")!=null)
	        	{
	        		String[] idStagiaires = request.getParameterValues("listeStagiairesAssocies");
	        		//ajout de tous les stagiaires déjà sélectionnés
		        	for(String id: idStagiaires)
					{
		        		Stagiaire s = StagiaireControleur.getStagiaire(Integer.parseInt(id));
		        		stagiairesAssocies.add(s);
				%>
						<option value="<%=s.getId()%>" selected="selected"><%=s.getPrenom() %> <%=s.getNom() %></option>
				<%
					}
		        	
	        	}
	        	else
	        	{
		        	//ajout de tous les stagiaires déjà associés au test
		        	stagiairesAssocies = StagiaireControleur.getListeStagiaires(testSelectionne);
					for(Stagiaire s: stagiairesAssocies)
					{
				%>
						<option value="<%=s.getId()%>" selected="selected"><%=s.getPrenom() %> <%=s.getNom() %></option>
				<%		
					}
	        	}
	        %>
	        <%
	        	//affichage des stagiaires correspondant à la promotion sélectionnée pour les proposer à l'ajout
	        	for(Stagiaire stagiaireDeLaPromo : StagiaireControleur.getListeStagiaires(promotionSelectionnee))
	        	{
	        		boolean estDejaAjoute = false;
	        		for(Stagiaire stagiaireDejaAssocie:stagiairesAssocies)
	        		{
	        			if(stagiaireDeLaPromo.getId()==stagiaireDejaAssocie.getId())
	        			{ 
	        				estDejaAjoute=true;
	        			}
	        		}
	        		if(!estDejaAjoute)
	        		{
				        %>
				        	<option value="<%=stagiaireDeLaPromo.getId()%>" ><%=stagiaireDeLaPromo.getPrenom() %> <%=stagiaireDeLaPromo.getNom() %></option>
				        <%
	        		}
	        	}
	        %>
      	</select>
	</div>
	
    <br/>
	 
</div>
<jsp:include page="./bouton.jsp"></jsp:include>