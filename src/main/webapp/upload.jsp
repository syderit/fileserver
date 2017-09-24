<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Servicio de Intercambio de Documentos</title>
</head>
<body>
<h2>Subida ficheros</h2>
<form action="UploadFile" enctype="multipart/form-data" method="post">
	Syder Password:
    <input type="text" name="password"/>
    <p>
	Fichero:<br>
	<input name="file" type="file" name="datafile" size="40"/>
	</p>
    <br/>
    <input type="submit" value="Upload File"/>
</form>
</body>
</html>