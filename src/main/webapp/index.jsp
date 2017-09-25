<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Servicio de Intercambio de Documentos</title>
	<script type="text/javascript">
		function submitForm() {
			if (document.forms.uploadForm.file.files.length != 1) {
				alert("Seleccione un fichero para subir");
			} else {
				var originalfilename = document.forms.uploadForm.file.files[0].name;
				document.forms.uploadForm.originalfilename.value = originalfilename;
				document.forms.uploadForm.submit();
			}
		}
	</script>
</head>
<body>
<h2>Subida ficheros</h2>
<p>
<span id="error_id" style="color:red"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></span>
</p>
<form action="UploadFile" enctype="multipart/form-data" method="post" name="uploadForm">
	Syder Password:
    <input type="text" name="password"/>
    <p>
	Fichero:<br>
	<input name="file" type="file" name="datafile" size="40"/>
	</p>
    <br/>
	<input type="hidden" name="originalfilename" />
    <input onclick="submitForm()" type="button" value="Upload File"/>
</form>
</body>
</html>
