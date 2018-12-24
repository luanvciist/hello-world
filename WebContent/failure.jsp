<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Error Page</title>

<!-- BOOTSTRAP STYLES-->
<link href="<%=request.getContextPath()%>/advance-admin/assets/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="<%=request.getContextPath()%>/advance-admin/assets/css/font-awesome.css" rel="stylesheet" />
<!-- PAGE LEVEL STYLES-->
<link href="<%=request.getContextPath()%>/advance-admin/assets/css/error.css" rel="stylesheet" />
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />

</head>
<body>
	<div class="container">
		<div class="row text-center">
			<div class="col-md-12 set-pad">
				<p class="p-err"><s:property value="errorMessage"/> </p>
				<a href="<%=request.getContextPath()%>/index" class="btn btn-danger"><i
					class="fa fa-mail-reply"></i>&nbsp;PLEASE GO BACK</a>
			</div>
		</div>
	</div>
</body>
</html>
