<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${sessionScope.role eq '1'}">
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-info">
			<div class="panel-heading">SLIDER FORM</div>
			<div class="panel-body">
				<c:if test="${slider.id ne '0'}">
					<s:form action="updateSlider" role="form" enctype="multipart/form-data" theme="simple">
						<c:if test="${message != null}">
							<div class="form-group has-error message">
								<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
							</div>
						</c:if>	
						<div class="form-group">
							<label>Slider suffix</label>
							<s:select cssClass="form-control"
								   headerKey="" headerValue="Please select"
								   list="sliderSuffixList" name="sliderSuffix" 
								   value="%{slider.sliderSuffix}" />
						</div>
						<div class="form-group">
							<label>Old Image</label>				
							<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
								<img src="${slider.src}" style="width: 200px; height: 150px;" alt="">
							</div>
						</div>
						<div class="form-group">
							<label>New Image</label><input name="fileImage" class="form-control" type="file">
						</div>
						
						<s:hidden name="id" value="%{slider.id}"></s:hidden>
						<button type="submit" class="btn btn-info">Update</button>
					</s:form>
				</c:if>
				<c:if test="${slider.id eq '0'}">
					<s:form action="addSlider" role="form" enctype="multipart/form-data" theme="simple">
						<c:if test="${message != null}">
							<div class="form-group has-error message">
								<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
							</div>
						</c:if>	
						<div class="form-group">
							<label>Slider suffix</label>
							<s:select cssClass="form-control"
								   headerKey="" headerValue="Please select"
								   list="sliderSuffixList" name="sliderSuffix" 
								   value="%{slider.sliderSuffix}" />
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
						
						<s:hidden name="id" value="%{slider.id}"></s:hidden>
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
						<th>Slider Sufix</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="sliderList" status="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${sliderSuffix}</td>
							<td><img src="${src}" style="width: 200px; height: 150px;"></td>
							<td><a href="<%=request.getContextPath()%>/viewSliderDetail?id=${id}">view</a></td>
							<td><a onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteSlider?id=${id}">delete</a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</c:if>

<c:if test="${sessionScope.role eq '2'}">
	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="panel panel-info">
			<div class="panel-heading">SLIDER FORM</div>
			<div class="panel-body">
				<c:if test="${slider.id ne '0'}">
					<s:form action="updateSliderMng" role="form" enctype="multipart/form-data" theme="simple">
						<c:if test="${message != null}">
							<div class="form-group has-error message">
								<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
							</div>
						</c:if>	
						<div class="form-group">
							<label>Slider suffix</label>
							<s:select cssClass="form-control"
								   headerKey="" headerValue="Please select"
								   list="sliderSuffixList" name="sliderSuffix" 
								   value="%{slider.sliderSuffix}" />
						</div>
						<div class="form-group">
							<label>Old Image</label>				
							<div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
								<img src="${slider.src}" style="width: 200px; height: 150px;" alt="">
							</div>
						</div>
						<div class="form-group">
							<label>New Image</label><input name="fileImage" class="form-control" type="file">
						</div>
						
						<s:hidden name="id" value="%{slider.id}"></s:hidden>
						<button type="submit" class="btn btn-info">Update</button>
					</s:form>
				</c:if>
				<c:if test="${slider.id eq '0'}">
					<s:form action="addSliderMng" role="form" enctype="multipart/form-data" theme="simple">
						<c:if test="${message != null}">
							<div class="form-group has-error message">
								<label class="control-label" for="error"><s:property value="message" escapeHtml="false"/></label>
							</div>
						</c:if>	
						<div class="form-group">
							<label>Slider suffix</label>
							<s:select cssClass="form-control"
								   headerKey="" headerValue="Please select"
								   list="sliderSuffixList" name="sliderSuffix" 
								   value="%{slider.sliderSuffix}" />
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
						<s:hidden name="id" value="%{slider.id}"></s:hidden>
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
						<th>Slider Sufix</th>
						<th>Image</th>
						<th>View</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="sliderList" status="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${sliderSuffix}</td>
							<td><img src="${src}" style="width: 200px; height: 150px;"></td>
							<td><a href="<%=request.getContextPath()%>/viewSliderDetailMng?id=${id}">view</a></td>
							<td><a onclick="return confirm('Are you sure you want to Delete?');" href="<%=request.getContextPath()%>/deleteSliderMng?id=${id}">delete</a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</c:if>