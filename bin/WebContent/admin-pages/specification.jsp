<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">SPECIFICATION FORM</div>
	<div class="panel-body">
		<s:form action="updateSpecification" role="form" enctype="multipart/form-data" theme="simple">	
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Old Image</label>				
					<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
						<img src="${product.specificationSrc}" style="width: 200px; height: 150px;" alt="">
					</div>
				</div>
				<div class="form-group">
					<label>New Image</label><input name="fileImage" class="form-control" type="file">
				</div>
			<div class="form-group">
				<label>Specifications</label><s:textarea name="specificationContent" id="specificationContent" value="%{product.specificationContent}"></s:textarea>
				<script>
	                // Replace the <textarea id="productContent"> with a CKEditor
	                // instance, using default configuration.
	                CKEDITOR.replace( 'specificationContent', {
	                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
	                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
	                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
	                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
	                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
	                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
	                } );
	            </script>
			</div>
			
			<s:hidden name="keepSrc" value="%{product.specificationSrc}"></s:hidden>
			<s:hidden name="id" value="%{product.id}"></s:hidden>
			<button type="submit" class="btn btn-info">Update</button>
		</s:form>
	</div>
</div>
