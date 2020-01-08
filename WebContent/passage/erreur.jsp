<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%
    if(request.getAttribute("error")!=null)
    {
    %>
    <div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;margin:10px;">
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
			<strong>Erreur:</strong> <%=request.getAttribute("error") %></p>
		</div>
	</div>
	<br/>
    <%
    }
    %>