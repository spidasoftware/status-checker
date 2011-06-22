<%@ page import="com.spidasoftware.status.Connection" %>
<html>
    <head>
        <title>Status Checker</title>
        <meta name="layout" content="main" />
	<g:javascript>
		setTimeout("location.reload(true);",30000);
	</g:javascript>
    </head>
    <body>
	<em>Reloads every 30 seconds</em>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create" controller="connection">Add Connection</g:link></span>
        </div>
        <div class="dialog">
            <table>
                <thead>
                  <tr>
                    <th>Name</th><th>URL</th><th>Description</th>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${Connection.list().sort({ it.toString() })}" var="c">
                      <tr>
                        <td class="${c?.isUp() ? 'up' : 'down'}">
                          <g:link controller="connection" action="edit" id="${c.id}">${c?.toString()}</g:link>
                        </td>
                        <td><a href="${c.url}">${c.url}</a></td>
                        <td><p>${c.description}</p></td>
                      </tr>
                  </g:each>
                </tbody>
            </table>
        </div>
    </body>
</html>
