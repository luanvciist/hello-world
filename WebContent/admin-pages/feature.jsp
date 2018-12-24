<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">FEATURE FORM</div>
	<div class="panel-body">
 		<c:if test="${feature.id ne '0'}">
			<s:form action="updateFeature" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="50" value="${feature.title}">
				</div>
				<div class="form-group">
					<label>Div class</label>
					<s:select cssClass="form-control"
						   headerKey="" headerValue="Please select"
						   list="divClassList" name="divClass" 
						   value="%{feature.divClass}" />
				</div>
				<div class="form-group">
					<label>Link popup</label><input name="href" class="form-control" type="text" maxlength="100" value="${feature.href}">
				</div>
				<div class="form-group">
					<label>H6 Suffix</label>
					<s:select cssClass="form-control"
						   headerKey="" headerValue="Please select"
						   list="h6SuffixList" name="h6Suffix" 
						   value="%{feature.h6Suffix}" />
				</div>
				<div class="form-group">
					<label>Main status</label><br/>
					<input type="radio" name="mainStatus" value="1" <c:if test="${feature.mainStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" name="mainStatus" value="0" <c:if test="${feature.mainStatus ne '1'}">checked</c:if>> No
				</div>

				<div class="form-group">
					<label>Old Image</label>				
					<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
						<img src="${feature.src}" style="width: 200px; height: 150px;" alt="">
					</div>
				</div>
				<div class="form-group">
					<label>New Image</label><input name="fileImage" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Popup</label><s:textarea name="popup" id="popup" value="%{feature.popup}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'popup', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				
				<s:hidden name="keepSrc" value="%{feature.src}"></s:hidden>
				<s:hidden name="id" value="%{feature.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
				<s:token />
			</s:form>
 		</c:if>
 		<c:if test="${feature.id eq '0'}">
			<s:form action="addFeature" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="50" value="${feature.title}">
				</div>
				<div class="form-group">
					<label>Div class</label>
					<s:select cssClass="form-control"
						   headerKey="" headerValue="Please select"
						   list="divClassList" name="divClass" 
						   value="%{feature.divClass}" />
				</div>
				<div class="form-group">
					<label>Link popup</label><input name="href" class="form-control" type="text" maxlength="100" value="${feature.href}">
				</div>
				<div class="form-group">
					<label>H6 Suffix</label>
					<s:select cssClass="form-control"
						   headerKey="" headerValue="Please select"
						   list="h6SuffixList" name="h6Suffix" 
						   value="%{feature.h6Suffix}" />
				</div>
				<div class="form-group">
					<label>Main status</label><br/>
					<input type="radio" name="mainStatus" value="1" <c:if test="${feature.mainStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" name="mainStatus" value="0" <c:if test="${feature.mainStatus ne '1'}">checked</c:if>> No
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
					<label>Popup</label><s:textarea name="popup" id="popup" value="%{feature.popup}"></s:textarea>
					<script>
		                // Replace the <textarea id="productContent"> with a CKEditor
		                // instance, using default configuration.
		                CKEDITOR.replace( 'popup', {
		                	filebrowserBrowseUrl : 'ckfinder/ckfinder.html',
		                	filebrowserImageBrowseUrl : 'ckfinder/ckfinder.html?type=Images',
		                	filebrowserFlashBrowseUrl : 'ckfinder/ckfinder.html?type=Flash',
		                	filebrowserUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
		                	filebrowserImageUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
		                	filebrowserFlashUploadUrl : 'ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
		                } );
		            </script>
				</div>
				
				<s:hidden name="id" value="%{feature.id}"></s:hidden>
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
						<th>Div class</th>
						<th>H6 suffix</th>
						<th>Status</th>
						<th>Link popup</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="featureList" status="status">
					<tr>
						<td style="width: 5%;">${status.index + 1}</td>
						<td style="width: 20%;" class="break-string"><s:property value="title"/></td>
						<td style="width: 20%;" class="break-string"><s:property value="divClass"/></td>
						<td style="width: 10%;" class="break-string"><s:property value="h6Suffix"/></td>
						<td style="width: 5%;"><c:if test="${mainStatus eq '1'}">Yes</c:if><c:if test="${mainStatus ne '1'}">No</c:if></td>
						<td style="width: 20%;" class="break-string"><s:property value="href"/></td>
						<td style="width: 10%;"><img src="${src}" style="width: 100px; height: 70px;"></td>
						<td style="width: 5%;"><a class="dngaz" href="<%=request.getContextPath()%>/viewFeatureDetail?id=${id}">view</a></td>
						<td style="width: 5%;"><a class="dngaz" onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteFeature?id=${id}">delete</a></td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		</div>
	</div>
</div>
