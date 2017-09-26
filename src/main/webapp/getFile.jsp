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
	<script type="text/javascript">
		function submitForm() {
			if (document.forms.downloadForm.username.value == "") {
				$("#id_alert_warning_text").html("Introduzca su nombre de usuario");
				$("#id_alert_warning").show();
				return false;
			}
			
			if (document.forms.downloadForm.password.value == "") {
				$("#id_alert_warning_text").html("Introduzca su password");
				$("#id_alert_warning").show();
				return false;
			}
			
			document.forms.downloadForm.submit();
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="jumbotron text-center">
		  <img src="resources/images/logo2.png" style="width:180px" />
		  <p>Servicio de descarga de ficheros</p> 
		</div>
	</div>
	<div class="container">
		<div id="main_content" style="padding-bottom:25px">
			<div id="id_alert_danger" class="alert alert-danger alert-dismissable" style="display:<%= request.getAttribute("error") != null ? "block" : "none" %>">
				<span id="id_alert_danger_close" class="close" aria-label="close" onclick="$('#id_alert_danger').hide();">x</span>
				<span id="id_alert_danger_text"><%= request.getAttribute("error") %></span>
			</div>
			<div id="id_alert_warning" class="alert alert-warning alert-dismissable" style="display:none">
				<span id="id_alert_warning_close" class="close" aria-label="close" onclick="$('#id_alert_warning').hide();">x</span>
				<span id="id_alert_warning_text"></span>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<form action="GetFile" method="post" name="downloadForm">
					    <label for="id_user">Usuario:</label>
						<br/>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
							<input id="id_user" type="text" class="form-control" name="username" placeholder="usuario" maxlength="50">
						</div>
						<br/>
						<label for="id_password">Password:</label>
						<br/>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							<input id="id_password" type="password" class="form-control" name="password" placeholder="password" maxlength="50">
						</div>
					    <br/>
					    <input type="hidden" name="id" value="<%= request.getParameter("id") %>"/>
					    <input type="button" class="btn btn-primary" onclick="submitForm()" value="Descargar">
					</form>
				</div>
			</div>
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