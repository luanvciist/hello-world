<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-info" style="width: 97%; margin:auto">
	<div class="panel-heading">GUIDELINE FORM</div>
	<div class="panel-body">
 		<c:if test="${guideline.id ne '0'}">
			<s:form action="updateGuideline" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
				<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="100" value="${guideline.title}">
				</div>
				<div class="form-group">
					<label>Popup status</label><br/>
					<input type="radio" id="rdHasPopup" name="popupStatus" value="1" <c:if test="${guideline.popupStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" id="rdNoPopup" name="popupStatus" value="0" <c:if test="${guideline.popupStatus ne '1'}">checked</c:if>> No
				</div>
				<div class="form-group" id ="hasPopup" style = "display: none;">
					<label>Link popup</label><input name="href" class="form-control" type="text" maxlength="100" value="${guideline.href}">
				</div>
				<div class="form-group" id ="noPopup">
					<label>Pdf file</label><input name="filePdf" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Old Image</label>				
					<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
						<img src="${guideline.src}" style="width: 200px; height: 150px;" alt="">
					</div>
				</div>
				<div class="form-group">
					<label>New Image</label><input name="fileImage" class="form-control" type="file">
				</div>
				<div class="form-group">
					<label>Content</label><s:textarea name="content" id="content" value="%{guideline.content}"></s:textarea>
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
				<div class="form-group" id = "valuePopup">
					<label>Popup</label><s:textarea name="popup" id="popup" value="%{guideline.popup}"></s:textarea>
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
				
				<s:hidden name="keepSrc" value="%{guideline.src}"></s:hidden>
				<s:hidden name="id" value="%{guideline.id}"></s:hidden>
				<button type="submit" class="btn btn-info" id="btnsubmit">Update</button>
				<s:token />
			</s:form>
 		</c:if>
 		<c:if test="${guideline.id eq '0'}">
			<s:form action="addGuideline" role="form" enctype="multipart/form-data" theme="simple" id="theform">			
				<c:if test="${message != null}">
					<div class="form-group has-error message">
						<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
					</div>
				</c:if>	
								<div class="form-group">
					<label>Title</label><input name="title" class="form-control" type="text" maxlength="100" value="${guideline.title}">
				</div>
				<div class="form-group">
					<label>Popup status</label><br/>
					<input type="radio" id="rdHasPopup" name="popupStatus" value="1" <c:if test="${guideline.popupStatus eq '1'}">checked</c:if>> Yes
					<input type="radio" id="rdNoPopup" name="popupStatus" value="0" <c:if test="${guideline.popupStatus ne '1'}">checked</c:if>> No
				</div>
				<div class="form-group" id ="hasPopup" style = "display: none;">
					<label>Link popup</label><input name="href" class="form-control" type="text" maxlength="100" value="${guideline.href}">
				</div>
				<div class="form-group" id ="noPopup">
					<label>Pdf file</label><input name="filePdf" class="form-control" type="file">
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
					<label>Content</label>
					<s:textarea name="content" id="content" value="%{guideline.content}"></s:textarea>
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
		        <div class="form-group" id = "valuePopup">
		        	<label>Popup</label>
					<s:textarea name="popup" id="popup" value="%{guideline.popup}"></s:textarea>
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
				
				<s:hidden name="id" value="%{guideline.id}"></s:hidden>
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
						<th>Link popup</th>
						<th>Display</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="guidelineList" status="status">
					<tr>
						<td style="width: 5%;">${status.index + 1}</td>
						<td style="width: 30%;" class="break-string"><s:property value="title"/></td>
						<td style="width: 30%;" class="break-string"><s:property value="href"/></td>
						<td style="width: 5%;"><c:if test="${popupStatus eq '1'}">Yes</c:if><c:if test="${popupStatus ne '1'}">No</c:if></td>
						<td style="width: 20%;"><img src="${src}" style="width: 200px; height: 125px;"></td>
						<td style="width: 5%;"><a class="dngaz" href="<%=request.getContextPath()%>/viewGuidelineDetail?id=${id}">view</a></td>
						<td style="width: 5%;"><a class="dngaz" onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteGuideline?id=${id}">delete</a></td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$('#rdHasPopup').click(function(){
       	$("#hasPopup").show();
		$("#noPopup").hide();
		$("#valuePopup").show();
	 });
	
	$('#rdNoPopup').click(function(){	   
       	$("#hasPopup").hide();
  		$("#noPopup").show();
  		$("#valuePopup").hide();
	 });
});

$(window).on("load", function (e) {
	var radioValue = $("input[name='popupStatus']:checked").val();
		if (radioValue == "1") {
			$("#hasPopup").show();
			$("#noPopup").hide();
			$("#valuePopup").show();
		} else {
			$("#hasPopup").hide();
			$("#noPopup").show();
			$("#valuePopup").hide();
		}
	})
</script>
