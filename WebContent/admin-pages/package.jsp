<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">PACKAGE FORM</div>
	<div class="panel-body">
		<c:if test="${packageObj.id ne '0'}">
			<s:form action="updatePackage" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="15" value="${packageObj.title}">
				</div>
				<div class="form-group">
					<label>Title Detail</label><input name="titleDetail" class="form-control" type="text" maxlength="20" value="${packageObj.titleDetail}">
				</div>
				<div class="form-group">
					<label>Item status</label><br/>
					<input type="radio" name="itemStatus" value="1" <c:if test="${packageObj.itemStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" name="itemStatus" value="0" <c:if test="${packageObj.itemStatus ne '1'}">checked</c:if>> No
				</div>
				<div class="form-group">
					<label>Color Suffix</label>
						<s:select cssClass="form-control"
							   headerKey="" headerValue="Please select"
							   list="colorSuffixList" name="colorSuffix" 
							   value="%{packageObj.colorSuffix}" />
				</div>
				<div class="form-group">
					<label>Old image</label>				
					<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
						<img src="${packageObj.backgroundUrl}" style="width: 200px; height: 150px;" alt="">
					</div>
				</div>
				<div class="form-group">
					<label>New image</label><input name="fileImage" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Content</label><s:textarea name="content" id="content" value="%{packageObj.content}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'content', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				<s:hidden name="keepSrc" value="%{packageObj.backgroundUrl}"></s:hidden>
				<s:hidden name="id" value="%{packageObj.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
				<s:token />
			</s:form>
		</c:if>
		
		<c:if test="${packageObj.id eq '0'}">
			<s:form action="addPackage" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="15" value="${packageObj.title}">
				</div>
				<div class="form-group">
					<label>Title Detail</label><input name="titleDetail" class="form-control" type="text" maxlength="20" value="${packageObj.titleDetail}">
				</div>
				<div class="form-group">
					<label>Item status</label><br/>
					<input type="radio" name="itemStatus" value="1" <c:if test="${packageObj.itemStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" name="itemStatus" value="0" <c:if test="${packageObj.itemStatus ne '1'}">checked</c:if>> No
				</div>
				<div class="form-group">
					<label>Color Suffix</label>
						<s:select cssClass="form-control"
							   headerKey="" headerValue="Please select"
							   list="colorSuffixList" name="colorSuffix" 
							   value="%{packageObj.colorSuffix}" />
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
				<div class="form-group">
					<label>Content</label><s:textarea name="content" id="content" value="%{packageObj.content}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'content', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				
				<s:hidden name="id" value="%{packageObj.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Add</button>
				<s:token />
			</s:form>
		</c:if>
	</div>
</div>
<div style="clear: left; margin-top: 10px;"></div>
<div style="width: 97%; margin: auto;">
	<div class="panel panel-default">
		<div class="table-responsive">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Title</th>
						<th>Title Detail</th>
						<th>Item status</th>
						<th>Color Suffix</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="packageList" status="status">
					<tr>
						<td style="width: 5%;">${status.index + 1}</td>
						<td style="width: 15%;" class="break-string"><s:property value="title"/></td>
						<td style="width: 25%;" class="break-string"><s:property value="titleDetail"/></td>
						<td style="width: 10%;"><c:if test="${itemStatus eq '1'}">Yes</c:if><c:if test="${itemStatus ne '1'}">No</c:if></td>
						<td style="width: 15%;" class="break-string"><s:property value="colorSuffix"/></td>
						<td style="width: 20%;"><img src="${backgroundUrl}" style="width: 200px; height: 150px;"></td>
						<td style="width: 5%;"><a class="dngaz" href="<%=request.getContextPath()%>/viewPackageDetail?id=${id}">view</a></td>
						<td style="width: 5%;"><a class="dngaz" onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deletePackage?id=${id}">delete</a></td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		</div>
	</div>
</div>
