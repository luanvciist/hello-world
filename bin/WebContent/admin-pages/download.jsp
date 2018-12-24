<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="col-md-6 col-sm-6 col-xs-12">
	<div class="panel panel-info">
		<div class="panel-heading">DOWNLOAD FORM</div>
		<div class="panel-body">
			<c:if test="${download.id ne '0'}">
				<s:form action="updateDownload" role="form" enctype="multipart/form-data" theme="simple">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>OS</label><input name="os" class="form-control" type="text" maxlength="50" value="${download.os}">
					</div>
					<div class="form-group">
						<label>Class Suffix</label>
						<s:select cssClass="form-control"
							   headerKey="" headerValue="Please select"
							   list="classSuffixList" name="classSuffix" 
							   value="%{download.classSuffix}" />
					</div>
					<div class="form-group">
						<label>Link download</label><input name="href" class="form-control" type="text" maxlength="100" value="${download.href}">
					</div>
					<div class="form-group">
						<label>Old Image</label>				
						<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
							<img src="${download.src}" style="width: 200px; height: 150px;" alt="">
						</div>
					</div>
					<div class="form-group">
						<label>New Image</label><input name="fileImage" class="form-control" type="file">
					</div>
					<s:hidden name="keepSrc" value="%{download.src}"></s:hidden>
					<s:hidden name="id" value="%{download.id}"></s:hidden>
					<button type="submit" class="btn btn-info">Update</button>
				</s:form>
			</c:if>
			<c:if test="${download.id eq '0'}">
				<s:form action="addDownload" role="form" enctype="multipart/form-data" theme="simple">
					<c:if test="${message != null}">
						<div class="form-group has-error message">
							<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
						</div>
					</c:if>	
					<div class="form-group">
						<label>OS</label><input name="os" class="form-control" type="text" maxlength="50" value="${download.os}">
					</div>
					<div class="form-group">
						<label>Class Suffix</label>
						<s:select cssClass="form-control"
							   headerKey="" headerValue="Please select"
							   list="classSuffixList" name="classSuffix" 
							   value="%{download.classSuffix}" />
					</div>
					<div class="form-group">
						<label>Link download</label><input name="href" class="form-control" type="text" maxlength="100" value="${download.href}">
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
					<s:hidden name="id" value="%{download.id}"></s:hidden>
					<button type="submit" class="btn btn-info">Add</button>
				</s:form>
			</c:if>
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
						<th>#</th>
						<th>OS</th>
						<th>Class Suffix</th>
						<th>Link</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="downloadList" status="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${os}</td>
							<td>${classSuffix}</td>
							<td><a href="${href}">${href}</a></td>
							<td><img src="${src}" style="width: 200px; height: 150px;"></td>
							<td><a href="<%=request.getContextPath()%>/viewDownloadDetail?id=${id}">view</a></td>
							<td><a onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteDownload?id=${id}">delete</a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</div>
