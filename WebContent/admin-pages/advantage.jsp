<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">ADVANTAGE FORM</div>
	<div class="panel-body">
		<s:form action="updateAdvantage" role="form" theme="simple" id="theform">	
			<c:if test="${message != null}">
				<div class="form-group has-error message">
					<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
				</div>
			</c:if>		
			<div class="form-group">
				<label>Advantage</label><s:textarea name="advantageContent" id="advantageContent" value="%{product.advantageContent}"></s:textarea>
				<script>
	                // Replace the <textarea id="productContent"> with a CKEditor
	                // instance, using default configuration.
	                CKEDITOR.replace( 'advantageContent', {
	                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
	                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
	                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
	                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
	                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
	                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash',
	                	on :
	                    {
	                        instanceReady : function( ev )
	                        {
	                            this.focus();
	                        }
	                    }
	                } );
	            </script>
			</div>
			
			<s:hidden name="id" value="%{product.id}"></s:hidden>
			<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
			<s:token />
		</s:form>
	</div>
</div>