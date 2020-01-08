<div id="bouton_<%=request.getAttribute("type")%>" class="centerDivSansBord">
	<input type="hidden" name="type" value="<%=request.getAttribute("type") %>"/>
	<input type="hidden" name="action" id="action"/>
	<%
		String display="none";
		if(request.getAttribute("type")=="questions") 
		{
			display="inline";
		}
	%>
	<input type="submit" value="Previsualiser" name="previsualiser" style="display:<%=display%>;" onclick="clickBouton('previsualiser')"/>
	<input type="submit" value="Valider" id="valider" onclick="clickBouton('valider')"/>		
	<%
		display="none";
		if(request.getAttribute("type")=="tests") 
		{
			display="inline";
		}
	%>
	<input type="submit" value="Dupliquer" name="dupliquer" style="display:<%=display%>;" onclick="clickBouton('dupliquer')"/>
	<%
		if(request.getAttribute("type")!="inscriptions") 
		{
	%>
		<input type="submit" value="Supprimer" id="supprimer" onclick="clickBouton('supprimer')"/>
	<%	
		} 
	%>	
	<input type="submit" value="Annuler" id="annuler" onclick="clickBouton('annuler')"/>
</div>

<script>
$(document).ready(function() 
{        
	$( "input:submit", "#bouton_<%=request.getAttribute("type")%>").button();
	
	/*$("#valider").click(function() {
			$("#action").val("valider");
		});
	$("#annuler").click(function() {
		  	$("#action").val("annuler");
		});
	$("#supprimer").click(function() {
		  	$("#action").val("supprimer");
		});
	$("#previsualiser").click(function() {
			$("#action").val("previsualiser");
		});
	$("#dupliquer").click(function() {
			$("#action").val("dupliquer");
		});*/
	
});
function clickBouton(action)
{
	$("#action").val(action);
}

</script>
