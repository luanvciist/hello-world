<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="col-md-6 col-sm-6 col-xs-12">
	<div class="panel panel-info">
		<div class="panel-heading">BODY FORM</div>
		<div class="panel-body">
			<s:form action="updateBody" role="form" enctype="multipart/form-data" id="theform">
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>		
				<div class="form-group">
					<label>Old Image</label>				
					<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
						<c:if test="${body.id eq '0'}">
							<img src="<%=request.getContextPath()%>/advance-admin/assets/img/demoUpload.jpg" alt="">
						</c:if>
						<c:if test="${body.id ne '0'}">
							<img src="${body.src}" style="width: 200px; height: 150px;" alt="">
						</c:if>
						
					</div>
				</div>
				<div class="form-group">
					<label>New Image</label><input name="fileImage" class="form-control" type="file">
				</div>
				
				<s:hidden name="keepSrc" value="%{body.src}"></s:hidden>
				<s:hidden name="id" value="%{body.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
				<s:token />
			</s:form>
		</div>
	</div>
</div>
<div class="col-md-6 col-sm-6 col-xs-12">
<div class="panel panel-default">
	<div class="table-responsive">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Image</th>
					<th>View</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="bodyList" status="status">
					<tr>
						<td style="width: 10%;">${status.index + 1}</td>
						<td style="width: 80%;"><img src="${src}" style="width: 350px; height: 300px;"></td>
						<td style="width: 10%;"><a class="dngaz" href="<%=request.getContextPath()%>/viewBodyDetail?id=${id}">view</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>
</div>