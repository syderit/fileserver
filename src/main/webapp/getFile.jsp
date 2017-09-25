<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Servicio de Intercambio de Documentos</title>
</head>
<body>
<h2>Introduzca sus credenciales para descargar el fichero</h2>
<p>
<span id="error_id" style="color:red"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></span>
</p>
<form action="GetFile" method="post">
    <div>
        <label for="id_username">Usuario:</label>
        <input type="text" id="id_username" name="username" />
    </div>
    <br/>
    <div>
        <label for="id_password">Contraseña:</label>
        <input type="password" id="id_password" name="password" />
    </div>
    <br/>
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>"/>
    <input type="submit" value="Descargar"/>
</form>
</body>
</html>