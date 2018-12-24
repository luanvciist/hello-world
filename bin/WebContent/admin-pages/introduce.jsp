<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">INTRODUCE FORM</div>
	<div class="panel-body">
		<c:if test="${sessionScope.role eq '1'}">
			<s:form action="updateIntroduce" role="form" theme="simple">
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Name</label><input name="productName" class="form-control" type="text" maxlength="50" value="${product.productName}">
				</div>
				<div class="form-group">
					<label>Introduce</label><s:textarea name="productContent" id="productContent" value="%{product.productContent}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'productContent', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				
				<s:hidden name="id" value="%{product.id}"></s:hidden>
				<button type="submit" class="btn btn-info">Update</button>
			</s:form>
		</c:if>
		<c:if test="${sessionScope.role eq '2'}">
			<s:form action="updateIntroduceMng" role="form" theme="simple">
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Name</label><input name="productName" class="form-control" type="text" maxlength="50" value="${product.productName}">
				</div>
				<div class="form-group">
					<label>Introduce</label><s:textarea name="productContent" id="productContent" value="%{product.productContent}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'productContent', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				
				<s:hidden name="id" value="%{product.id}"></s:hidden>
				<button type="submit" class="btn btn-info">Update</button>
			</s:form>
		</c:if>
	</div>
</div>
