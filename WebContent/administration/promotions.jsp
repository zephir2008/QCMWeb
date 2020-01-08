<%@page import="fr.eni_ecole.qcm.controleur.administration.PromotionControleur"%>
<%@page import="fr.eni_ecole.qcm.modele.administration.Promotion"%>
<%@page import="java.util.List"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	request.setAttribute("type", "promotions");
%>

<script>
$(function() {
	$("#listePromotionAdmin").on('change', function() 
	{         
		var val = $(this).val(); // on récupère l'id de la promotion
		if(val != '') 
		{
			$.ajax
			({                 
				url: './administration/promotions.jsp',                 
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
});
</script>

<jsp:include page="./erreur.jsp"></jsp:include>

<div>
	<label class="label_administration" for="listePromotionAdmin"> Promotion:</label>
	<select class="input_select_administration" name="listePromotionAdmin" id="listePromotionAdmin">
			<option value="0">Nouvelle promotion</option>
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
	<label class="label_administration" for="txtLibelle">Libellé:</label><input class="input_text_administration" type="text" name="txtLibelle" id="txtLibelle" value="<% out.write((promotionSelectionnee.getLibelle()==null ? "" : promotionSelectionnee.getLibelle()));%>"/> 
</div>
<jsp:include page="./bouton.jsp"></jsp:include>