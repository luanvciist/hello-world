<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><tiles:insertAttribute name="title" ignore="true" /></title>

	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
    <!-- BOOTSTRAP STYLES-->
    <link href="<%=request.getContextPath()%>/advance-admin/assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="<%=request.getContextPath()%>/advance-admin/assets/css/font-awesome.css" rel="stylesheet" />
       <!--CUSTOM BASIC STYLES-->
    <link href="<%=request.getContextPath()%>/advance-admin/assets/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="<%=request.getContextPath()%>/advance-admin/assets/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    
    <link href="<%=request.getContextPath()%>/css/custom.css" rel="stylesheet" />
	<script src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
	
	<script>
		function autofocus() {
			document.forms[0].elements[0].focus();
		}
	</script>
</head>
<body onload="autofocus()">
    <div id="wrapper">
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <div class="user-img-div">
                            <img src="${sessionScope.src}" class="img-thumbnail" />

                            <div class="inner-text">
                                ${sessionScope.username}
                            <br />
                                <a href="<%=request.getContextPath()%>/doLogout" style="color: white; font-size: 12px"><i class="fa fa-sign-out" aria-hidden="true"></i>Logout</a>
                            </div>
                        </div>

                    </li>
					<tiles:insertAttribute name="menu" ignore="true" />
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line"><tiles:insertAttribute name="header" ignore="true" /></h1>
                    </div>
                </div>
                <!-- /. ROW  -->
                <div class="row">
                    <tiles:insertAttribute name="contentRight" />
                </div>
                <!--/.ROW-->

            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->

    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<%=request.getContextPath()%>/advance-admin/assets/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="<%=request.getContextPath()%>/advance-admin/assets/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<%=request.getContextPath()%>/advance-admin/assets/js/jquery.metisMenu.js"></script>
       <!-- CUSTOM SCRIPTS -->
    <script src="<%=request.getContextPath()%>/advance-admin/assets/js/custom.js"></script>
    
    


</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnsubmit').on('click', function() {
			$(this).val('Please wait ...').attr('disabled', 'disabled');
			$('#theform').submit();
		});
	})
</script>
</html>
