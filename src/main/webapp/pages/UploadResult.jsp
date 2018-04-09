<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="resources/bootstrap-3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/fileserver.css">
	<script src="resources/jquery-3.2.1/jquery-3.2.1.min.js"></script>
	<script src="resources/bootstrap-3.3.7/js/bootstrap.min.js"></script>
	<title>Servicio de Intercambio de Documentos</title>
</head>
<body>
	<div class="container-fluid">
		<div class="jumbotron text-center">
		  <img src="resources/images/logo2.png" style="width:180px" />
		  <p>Servicio de subida de ficheros</p> 
		</div>
	</div>
	<div class="container">
		<div id="main_content" style="padding-bottom:25px">
		
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>Fichero subido correctamente
					<br/>
					<small>Conserve la URL del fichero para poder descargarlo más adelante</small>
					</h4>
				</div>
				<div class="panel-body">
					<%= request.getAttribute("newfileurl") %><br/>
					<b>(URL Nuevo acuerdo OCSUM)</b> <%= request.getAttribute("newnewfileurl") %><br/>
				</div>
			</div>
			<input type="button" class="btn btn-primary" value="Volver" onclick="document.location='index.jsp'">
		</div>
	</div>
	<div class="container-fluid">
		<footer class="footer">
			<p class="small" style="margin:10px">Para cualquier problema pongase en contacto con: <a href="mailto:soporte@servicios-syder.es" target="_top"><span class="glyphicon glyphicon-envelope"></span> soporte@servicios-syder.es</a>.</p>
			<p class="small" style="margin:10px">2017 &copy; Todos los derechos reservados.</p>
        </footer>
	</div>
</body>
</html>