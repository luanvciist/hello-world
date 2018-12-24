<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${sessionScope.role eq '1'}">
	<div class="col-md-12">
		<div class="alert alert-info">
			Chào mừng bạn tới trang quản lý website: <a href="<%=request.getContextPath()%>/index" target="_blank">https://mykid.viettel.vn/</a><br />
			Bạn có tất cả các quyền quản lý:
			<ul>
				<li>Quản lý thông tin ở banner</li>
				<li>Quản lý thông tin ở body</li>
				<li>Quản lý thông tin sản phẩm</li>
				<li>Quản lý thông tin các loại màu sản phẩm</li>
				<li>Quản lý thông tin các tính năng của sản phẩm</li>
				<li>Quản lý thông tin các hướng dẫn của sản phẩm</li>
				<li>Quản lý thông tin các gói cước của sản phẩm</li>
				<li>Quản lý thông tin các hình ảnh trong slider</li>
				<li>Quản lý thông tin cấu trúc sản phẩm</li>
			</ul>
			Bạn vui lòng nhìn sang menu trái để thực hiện các tác vụ quản lý.
		</div>
	</div>
</c:if>
<c:if test="${sessionScope.role eq '2'}">
	<div class="col-md-12">
		<div class="alert alert-info">
			Chào mừng bạn tới trang quản lý website: <a href="<%=request.getContextPath()%>/index" target="_blank">https://mykid.viettel.vn/</a><br />
			Bạn có tất cả các quyền quản lý:
			<ul>
				<li>Quản lý thông tin giới thiệu của sản phẩm</li>
				<li>Quản lý thông tin các hình ảnh trong slider</li>
			</ul>
			Bạn vui lòng nhìn sang menu trái để thực hiện các tác vụ quản lý.
		</div>
	</div>
</c:if>
