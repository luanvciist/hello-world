<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="col-md-6 col-sm-6 col-xs-12">
	<div class="panel panel-info">
		<div class="panel-heading">STRUCTURE FORM</div>
		<div class="panel-body">
			<c:if test="${structure.id ne '0'}">
				<s:form action="updateStructure" role="form" enctype="multipart/form-data" id="theform">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>Old Image</label>				
						<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
							<img src="${structure.src}" style="width: 200px; height: 150px;" alt="">
						</div>
					</div>
					<div class="form-group">
						<label>New Image</label><input name="fileImage" class="form-control" type="file">
					</div>
					<s:hidden name="id" value="%{structure.id}"></s:hidden>
					<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
					<s:token />
				</s:form>
			</c:if>
			<c:if test="${structure.id eq '0'}">
				<s:form action="addStructure" role="form" enctype="multipart/form-data" id="theform">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>Old Image</label>				
						<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
							<img src="<%=request.getContextPath()%>/advance-admin/assets/img/demoUpload.jpg" alt="">
						</div>
					</div>
					<div class="form-group">
						<label>New Image</label><input name="fileImage" class="form-control" type="file">
					</div>
					<s:hidden name="id" value="%{structure.id}"></s:hidden>
					<button type="submit" class="btn btn-info" id="btnsubmit">Add</button>
					<s:token />
				</s:form>
			</c:if>
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
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="structureList" status="status">
					<tr>
						<td style="width: 10%;">${status.index + 1}</td>
						<td style="width: 70%;"><img src="${src}" style="width: 310px; height: 155px;"></td>
						<td style="width: 10%;"><a class="dngaz" href="<%=request.getContextPath()%>/viewStructureDetail?id=${id}">view</a></td>
						<td style="width: 10%;"><a class="dngaz" onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteStructure?id=${id}">delete</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>
</div>
