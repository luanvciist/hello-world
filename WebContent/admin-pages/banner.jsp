<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="col-md-6 col-sm-6 col-xs-12">
	<div class="panel panel-info">
		<div class="panel-heading">BANNER FORM</div>
		<div class="panel-body">
			<s:form action="updateBanner" role="form" enctype="multipart/form-data" id="theform">
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>		
				<div class="form-group">
					<label>Background</label><input name="fileBackground" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Parent Logo</label><input name="fileParentLogo" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Child Logo</label><input name="fileChildLogo" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Image</label><input name="fileImage" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="50" value="${title}">
				</div>
				<div class="form-group">
					<label>Slogan</label> <input name="slogan" class="form-control" type="text" maxlength="50" value="${slogan}">
				</div>
				<s:hidden name="id" value="%{banner.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
				<s:token />
			</s:form>
		</div>
	</div>
</div>
<div style="clear: left;"></div>
<div style="width: 97%; margin: auto;">
	<div class="panel panel-default">
		<div class="table-responsive">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>Background</th>
						<th>Parent Logo</th>
						<th>Child Logo</th>
						<th>Image</th>
						<th>Title</th>
						<th>Slogan</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 15%;"><img src="${banner.backgroundUrl}" height="100" width="140"></td>
						<td style="width: 15%;"><img src="${banner.parentLogoSrc}" height="100" width="140"></td>
						<td style="width: 15%;"><img src="${banner.childLogoSrc}" height="100" width="140"></td>
						<td style="width: 15%;"><img src="${banner.imageSrc}" height="100" width="140"></td>
						<td style="width: 20%;" class="break-string"><s:property value="banner.title"/></td>
						<td style="width: 20%;" class="break-string"><s:property value="banner.slogan"/></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
