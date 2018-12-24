<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="col-md-6 col-sm-6 col-xs-12">
	<div class="panel panel-info">
		<div class="panel-heading">COLOR FORM</div>
		<div class="panel-body">
			<c:if test="${color.id ne '0'}">
				<s:form action="updateColor" role="form" enctype="multipart/form-data">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>Content</label><input name="content" class="form-control" type="text" maxlength="100" value="${color.content}">
					</div>
					<div class="form-group">
						<label>Old Image</label>				
						<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
							<img src="${color.src}" style="width: 200px; height: 150px;" alt="">
						</div>
					</div>
					<div class="form-group">
						<label>New Image</label><input name="fileImage" class="form-control" type="file">
					</div>
					<s:hidden name="keepSrc" value="%{color.src}"></s:hidden>
					<s:hidden name="id" value="%{color.id}"></s:hidden>
					<button type="submit" class="btn btn-info">Update</button>
				</s:form>
			</c:if>
			<c:if test="${color.id eq '0'}">
				<s:form action="addColor" role="form" enctype="multipart/form-data">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>Content</label><input name="content" class="form-control" type="text" maxlength="100" value="${color.content}">
					</div>
					<div class="form-group">
						<label>Old Image</label>				
						<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
							<img src="<%=request.getContextPath()%>/advance-admin/assets/img/demoUpload.jpg" alt="">
						</div>
					</div>
					<div class="form-group">
						<label>New Image</label><input name="fileImage" class="form-control" type="file">
					</div>
					<s:hidden name="id" value="%{color.id}"></s:hidden>
					<button type="submit" class="btn btn-info">Add</button>
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
					<th>Content</th>
					<th>Image</th>
					<th>View</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="colorList" status="status">
					<tr>
						<td>${status.index + 1}</td>
						<td>${content}</td>
						<td><img src="${src}" style="width: 200px; height: 150px;"></td>
						<td><a href="<%=request.getContextPath()%>/viewColorDetail?id=${id}">view</a></td>
						<td><a onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteColor?id=${id}">delete</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>
</div>
