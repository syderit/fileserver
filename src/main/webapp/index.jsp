<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Servicio de Intercambio de Documentos</title>
</head>
<body>
<h2>Autenticación</h2>
<form action="CheckUser" method="post">
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
    <input type="submit" value="check user"/>
</form>
</body>
</html>
