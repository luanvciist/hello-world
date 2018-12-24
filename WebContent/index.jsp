<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="en">
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-123873628-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag(){dataLayer.push(arguments);}
	gtag('js', new Date());  gtag('config', 'UA-123873628-1');
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="HTML5 Template">
<meta name="description" content="Sofbox - Responsive Software Landing Page">
<meta name="author" content="iqonicthemes.in">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>MyKiD - Đồng hồ thông minh chính hãng</title>

<!-- Favicon -->
<link rel="shortcut icon" href="./images/favicon.ico">

<!-- Google Fonts -->
<!-- Roboto -->
<link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&amp;subset=vietnamese" rel="stylesheet">
<!-- Open Sans -->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i&amp;subset=vietnamese" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Bangers&amp;subset=vietnamese" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Baloo+Paaji&amp;subset=vietnamese" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Pacifico&amp;subset=vietnamese" rel="stylesheet">

<!-- Font Awesome -->
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

<!-- Bootstrap -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-

bootstrap/3.3.7/css/bootstrap.min.css">

<!-- owl-carousel -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.2.1/assets/owl.carousel.min.css">

<!-- Magnific Popup -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/magnific-popup.min.css">

<!-- Animate -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">

<!-- Ionicons -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.0/css/ionicons.min.css">
<link rel="stylesheet" href="./css/style.css">
<!-- Responsive -->
<link rel="stylesheet" href="./css/responsive.css">

<!-- custom style -->
<link rel="stylesheet" href="./css/custom.css">

<!-- Style -->

</head>
<body>
<!-- loading -->
<div id="loading">
  <div id="loading-center">
    <div class="loader">
      <div class="cube">
        <div class="sides">
          <div class="top"></div>
          <div class="right"></div>
          <div class="bottom"></div>
          <div class="left"></div>
          <div class="front"></div>
          <div class="back"></div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- loading End --> 
<!-- Header -->
<header id="header-wrap">
  <div class="navbar navbar-default navbar-fixed-top menu-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a href="" class="navbar-brand"> <img class="img-responsive" id="logo_img" src="${banner.childLogoSrc}" alt="logo"> </a> </div>
     <div class="navbar-collapse collapse"> <!-- <a href="#compare-services" class="page-scroll button pull-right visible-lg">ĐẶT MUA</a>-->
        <nav>
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a class="page-scroll" href="#iq-home">Trang chủ</a></li>
            <li><a class="page-scroll" href="#how-it-works">Giới thiệu</a></li>
            <li><a class="page-scroll" href="#how-works">Tính năng</a></li>
            <li><a class="page-scroll" href="#features">Thông số kỹ thuật</a></li>
            <!--<li><a class="page-scroll" href="#HDSD">HDSD</a></li>-->
            <li><a class="page-scroll" href="#pricing">Gói cước</a></li>
            <li><a class="page-scroll" href="#team">Tải ứng dụng</a></li>
<!--            <li><a class="page-scroll" href="#footer-info">Hỗ trợ</a></li>-->
<!--            <li><a class="page-scroll" href="#contact">Contact</a></li>-->
          </ul>
        </nav>
      </div>
    </div>
  </div>
</header>
<!-- Header End --> 
<!-- Banner -->
<section id="iq-home" class="iq-banner overview-block-pt iq-bg iq-bg-fixed iq-over-blue-90" style="background: url('${banner.backgroundUrl}');">
  <div class="container">
    <div class="banner-text">
      <div class="row">
        <div class="col-md-6 text-center">
         <div class="" style="margin-top: -40px;"><img src="${banner.parentLogoSrc}" width="340px" height="" alt=""/></div>
          <h1 class="text-uppercase iq-font-white iq-tw-3"><b class="iq-tw-7"><s:property value="banner.title"/></b></h1>
          <h2 class="iq-font-yellow iq-tw-3 " style="margin-top: -16px;"><s:property value="banner.slogan"/></h2>
<!--          <p class="iq-font-white iq-pt-15 iq-mb-40">Đồng hồ thông minh MyKID nghe gọi như điện thoại. Kết nối liên lạc hoàn hảo với gia đình, không lo liên lạc với người ngoài</p>-->
<!--          <a href="#compare-services" class="button bt-black text-uppercase page-scroll">Đăng ký mua trực tuyến</a> -->
        </div>
        <div class="col-md-6">
          <div class="iq-banner-video"> 
            <!--                            <a href="http://iqonicthemes.com/themes/sofbox/v3/video/01.mp4" class="iq-video popup-youtube"><i class="ion-ios-play-outline"></i></a>-->
            <div class="iq-waves">
              <div class="waves wave-1"></div>
              <div class="waves wave-2"></div>
              <div class="waves wave-3"></div>
            </div>
            <img class="banner-img img-responsive" src="${banner.imageSrc}" alt=""> </div>
        </div>
      </div>
    </div>
  </div>
  <div class="banner-objects"> <span class="banner-objects-01 skrollable skrollable-between" data-bottom="transform:translatey(50px)" data-top="transform:translatey(-50px);" style="transform: translateY(46.2121px);"> <img src="./images/03.png" alt="drive02"> </span> <span class="banner-objects-02 iq-fadebounce"> <span class="iq-round"></span> </span> <span class="banner-objects-03 iq-fadebounce"> <span class="iq-round"></span> </span> <span class="banner-objects-04 skrollable skrollable-between" data-bottom="transform:translatex(100px)" data-top="transform:translatex(-100px);" style="transform: translateX(-51.3158px);"> <img src="./images/04.png" alt="drive02"> </span> </div>
</section>
<!-- Banner End --> 
<!-- Main Content -->
<div class="main-content clearfix"> 
  <!-- How it Works -->
  <section id="how-it-works" class="overview-block-ptb it-works re4-mt-50">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title">
            <h3 class="title iq-tw-7">Giới Thiệu MyKID</h3>
          </div>
          <div>
            <div class="col-sm-5 text-center img-center"> <img class="img-responsive" src="${srcImage1}" style="margin-top: -63px; width: 411px; height: 638px"> </div>
            <div class="col-sm-7 ">
				<s:property value="product.productContent" escapeHtml="false"/>          
            </div>
          </div>
          
        </div>
      </div>
<!--
      <div class="row phuongthu">
        <div class="col-sm-12 col-md-4">
          <div class="iq-works-box text-center">
            <div class="icon-bg center-block"><i aria-hidden="true" class="ion-ios-monitor-outline"></i></div>
            <h5 class="iq-tw-7 text-uppercase iq-mt-25 iq-mb-15">Fully Responsive</h5>
            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, </p>
          </div>
        </div>
        <div class="col-sm-12 col-md-4 re-mt-50">
          <div class="iq-works-box text-center">
            <div class="icon-bg center-block"><i aria-hidden="true" class="ion-ios-albums-outline"></i></div>
            <h5 class="iq-tw-7 text-uppercase iq-mt-25 iq-mb-15">Well Documented</h5>
            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, </p>
          </div>
        </div>
        <div class="col-sm-12 col-md-4 re-mt-50">
          <div class="iq-works-box text-center">
            <div class="icon-bg center-block"><i aria-hidden="true" class="ion-ios-color-wand-outline"></i></div>
            <h5 class="iq-tw-7 text-uppercase iq-mt-25 iq-mb-15">Easy To Use</h5>
            <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, </p>
          </div>
        </div>
      </div>
-->
    </div>
    <div class="iq-objects-how-it-works ">
                <span class="iq-objects-02 skrollable skrollable-between" data-bottom="transform:translatey(50px)" data-top="transform:translatey(-100px);" style="transform: translateY(-39.909px);">
                    <img src="./images/vis_7.png" alt="drive02">
                </span>
                <span class="iq-objects-02right skrollable skrollable-between" data-bottom="transform:translatey(50px)" data-top="transform:translatey(-100px);" style="transform: translateY(-39.909px);">
                    <img src="./images/vis_7right.png" alt="drive02">
                </span>
<!--
                <span class="iq-objects-02">
                    <img src="./images/04.png" alt="drive02">
                </span>
-->
<!--
                <span class="iq-objects-03 iq-fadebounce">
                    <span class="iq-round"></span>
                </span>
-->
            </div>
  </section>
  <!-- How it Works END --> 
  <!-- Who is Sofbox ? -->
  <section id="how-works" class="overview-block-ptb how-works">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 p0">
                        <h1 class="iq-tw-6 iq-mb-25 font-style-3">Tính năng</h1>
<!--                        <img class="img-responsive" src="images/tinhnang.png" width="" height="" alt=""/>-->
                        <div class="feature">
	                        <s:iterator value="featureList" var="feature">
	                        	<div class="${divClass}">
		                        	<div class="iq-fancy-box text-center">
			                            <div class="iq-icon"> <a href="#<s:property value="#feature.href" />" class="news-detail-popup"><img src="${src}" <c:if test="${mainStatus == 1}">style="width: 129px;margin-top: -12px;"</c:if>></a> </div>
			                            <div class="fancy-content">
			                                <h6 class="iq-tw-6 iq-pt-10 iq-pb-10 ${h6Suffix}" <c:if test="${mainStatus == 1}">style="text-transform: uppercase;"</c:if>><a href="#<s:property value="#feature.href" />" class="news-detail-popup "><s:property value="title"/> </a></h6>
			                            </div>
		                       		</div>
	                       		</div>
	                        </s:iterator>
                    	</div>
                    </div>
                    <div class="col-md-4">
                        <img class="iq-works-img" src="${srcImage2}" alt="drive01" width= "453" height="560">
                    </div>
                </div>
            </div>
            <div class="iq-objects">
                <span class="iq-objects-01">
                    <img src="./images/02.png" alt="drive02">
                </span>
                <span class="iq-objects-02 skrollable skrollable-before" data-bottom="transform:translatey(50px)" data-top="transform:translatey(-100px);" style="transform: translateY(50px);">
                    <img src="./images/03.png" alt="drive02">
                </span>
                <span class="iq-objects-03 skrollable skrollable-before" data-bottom="transform:translatex(50px)" data-top="transform:translatex(-100px);" style="transform: translateX(50px);">
                    <img src="./images/04.png" alt="drive02">
                </span>
                <span class="iq-objects-04 iq-fadebounce">
                    <span class="iq-round"></span>
                </span>
            </div>
        </section>
  <!-- Who is Sofbox ? END --> 
  <!-- Software Features -->
  <section id="software-features" class="overview-block-ptb software">
            <div class="iq-software-demo">
                <img class="img-responsive" src="${srcImage3}" alt="drive05" width="833" height="731">
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-md-offset-6 ">
                        <s:property value="product.advantageContent" escapeHtml="false"/>
                    </div>
                </div>
            </div>
            <div class="iq-objects-software">
                <span class="iq-objects-01">
                    <img src="./images/03.png" alt="drive02">
                </span>
                <span class="iq-objects-02">
                    <img src="./images/04.png" alt="drive02">
                </span>
                <span class="iq-objects-03 iq-fadebounce">
                    <span class="iq-round"></span>
                </span>
            </div>
        </section>
  <!-- Software Features END --> 
  <!-- Great Screenshots -->
  <section id="great-screenshots" class="overview-block-ptb iq-bg iq-bg-fixed iq-over-blue-80 iq-screenshots" style="background: url(images/bg/07.jpg);">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title white">
            <h2 class="title iq-tw-7">Video - Hình ảnh</h2>
            <!--<p class="iq-font-white">Video và hình ảnh đồng hồ thông minh trẻ em MyKID Viettel - Kết nối yêu thương</p>-->						
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1 text-center">
          <div class="screenshots-slider popup-gallery">
            <div class="slider-container">
              <div class="slider-content">
             	<s:iterator value="sliderList" status="status">
    				<div class="slider-single ${sliderSuffix}"> <a href="${src}" class="popup-img"><img class="slider-single-image" src="${src}" alt="${status.index + 1}"></a></div>
  				</s:iterator>
              </div>
              <a class="slider-left" href="javascript:void(0);"><i class="fa fa-angle-left"></i></a> <a class="slider-right" href="javascript:void(0);"><i class="fa fa-angle-right"></i></a></div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- Great Screenshots END --> 
  <!-- Tab Features -->
  <div id="features" class="iq-amazing-tab white-bg">
    <div class="container">
      <div class="row">
        <div class="col-sm-12"> 
          <!-- Nav tabs -->
          <ul id="iq-amazing-tab" class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active1"><a href="#home" aria-controls="home" role="tab" data-toggle="tab"><img class="mr20" src="images/icon/sm-black-w.png"><span>Thông số kỹ thuật</span></a></li>
            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab"><img class="mr20" src="images/icon/sm-blue-w.png"><span>Màu sắc</span></a></li>
            <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab"><img class="mr20" src="images/icon/sm-pink-w.png"><span>Cấu tạo đồng hồ</span></a></li>
          </ul>
          <!-- Tab panes -->
          <div class="tab-content iq-mt-50">
            <div role="tabpanel" class="tab-pane active" id="home">
            	<div class="col-sm-5"><img class="img-responsive" src="${product.specificationSrc}"></div>
				<div class="col-sm-7 ">
					<s:property value="product.specificationContent" escapeHtml="false"/>
				</div>			
           </div>
            <div role="tabpanel" class="tab-pane" id="profile">
              <div class="row iq-mb-50">
					<s:iterator value="colorList">
	 				 	<div class="col-sm-4 text-center height-default-color">
							<img src="${src}" width="230" height="278" alt=""/> 
							<p class=""><s:property value="content"/> </p>
						</div>
 					</s:iterator>
				</div>
             
              
            </div>
            <div role="tabpanel" class="tab-pane" id="messages"> 
				<div class="row iq-mb-50">
					<s:iterator value="structureList">
	      				<div class="col-sm-6"><img class="img-responsive" src="${src}" width="" height="" alt=""/> </div>
					</s:iterator>
				</div>
				
          </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Tab Features END --> 
  <!-- Hướng dẫn sử dụng -->
  
  <section id="HDSD" class="overview-block-ptb grey-bg">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title">
            <h3 class="title iq-tw-7">Hướng dẫn sử dụng </h3>
            
          </div>
        </div>
      </div>
      <div class="row">
       <div class="col-sm-12">
       		<div class="owl-two owl-carousel owl-theme">
       			<s:iterator value="guidelineList" var="guideline">
    				<div class="item iq-blog-box">
					<div class="iq-blog-image clearfix"> <img class="img-responsive center-block" src="${src}" alt="#"> </div>
					<div class="iq-blog-detail">
					  <div class="blog-title"><a href="<c:if test="${popupStatus == 1}">#</c:if>${href}" <c:if test="${popupStatus == 1}">class="news-detail-popup"</c:if>>
						<h6 class="iq-tw-6 iq-mb-10 "><s:property value="#guideline.title" /></h6>
						</a> </div>
					  <div class="blog-content ">
						<p><s:property value="#guideline.content" escapeHtml="false"/></p>
					  </div>
					</div>
				  </div>
  				</s:iterator>
		    </div>
       </div>


      </div>
    </div>
	
  
  </section> 
  <!-- Sofbox Specialities END --> 
  
  <!-- Counter -->
  <div class="section-inner iq-counter-box iq-bg iq-bg-fixed iq-over-black-50 iq-font-white" style="background: url(images/bg/02.jpg);">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title white">
            <h1 class="title iq-tw-7">
            	<span>Trải nghiệm hoàn toàn mới</span>
            	<br><br>
                <span class="colored">Cùng đồng hồ thông minh trẻ em MyKID</span>
            </h1>
            

          </div>
        </div>
      </div>
      
    </div>
  </div>
  <!-- Counter END --> 
  <!-- Gói cước -->
  <section id="pricing" class="overview-block-ptb white-bg iq-price-table">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title">
            <h3 class="title iq-tw-7">Các gói cước</h3>
            <p>GÓI CƯỚC CHO DỊCH VỤ MYKID<br>
            	Cho phép chuyển đổi qua lại giữa các gói cước MYKID30, MYKID50 trên ứng dụng (Bao gồm các gói cước đóng trước)

            </p>
          </div>
        </div>
      </div>
      <div class="row">
      <div class="col-sm-12">
			<div class="owl-one owl-carousel owl-theme">
				<s:iterator value="packageList">
					<div class="<c:if test="${itemStatus == 1}">item </c:if>iq-pricing text-center">
						<div class="price-title iq-bg ${colorSuffix}" style="background: url('${backgroundUrl}');">
						  <h1 class="iq-font-white iq-tw-7 "><s:property value="title"/></h1>
						  <span class="text-uppercase iq-tw-4 iq-font-white "><s:property value="titleDetail"/></span> </div>
						<s:property value="content" escapeHtml="false"/>
				  	</div>
				</s:iterator>
			</div>	
      </div>
		

      </div>
    </div>
  </section>
  <!-- Affordable Price END --> 
  <!-- Tải ứng dụng -->
  <section id="team" class="overview-block-ptb grey-bg">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title">
            <h3 class="title iq-tw-7">Tải ứng dụng</h3>
            <!--<p>Tải phần mềm cài vào điện thoại</p>-->
			<p>Tải ứng dụng <b>"MyKID Viettel"</b> từ Google Play hoặc AppStore</p>
          </div>
        </div>
      </div>
      <div class="row">
      	<s:iterator value="downloadList">
      		<div class="col-xs-6 col-sm-6 col-lg-3 col-md-3 ${classSuffix}">
	          	<div class="iq-team grey-bg">
	            	<div class=""><a href="${href}"><img src="${src}" class="img-responsive center-block" alt="#"></a>  </div>
	        	</div>
        	</div>
      	</s:iterator>
      </div>
    </div>
  </section>
  <!-- Meet the Team END --> 
  <!-- Compare Services -->
<!--
  <section id="compare-services" class="overview-block-ptb iq-bg iq-bg-fixed iq-over-blue-90" style="background: url(images/bg/05.jpg);">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <div class="heading-title white">
            <h3 class="title iq-tw-7">Đặt mua đồng hồ MyKID</h3>
            <p class="iq-font-white">Quý Khách đặt mua Đồng hồ MyKID của Viettel, xin vui lòng nhập đầy đủ mọi thông tin bên dưới. Chúng tôi sẽ liên hệ lại xác nhận và triển khai cho Quý Khách trong thời gian sớm nhất</p>
          </div>
        </div>
      </div>
      <div class="row">
        <div class=""></div>
      </div>
      
      <div class="row">
          <div class="iq-services-box text-left iq-font-white">
           <div class="contact-form">
           	<form class="form-hozirontal clearfix">
           		<div class="form-group">
					<div class="col-sm-2 label-control iq-font-white">Họ và tên:</div>
					<div class="col-sm-4">
						<input class="form-control" id="exampleInputName2" placeholder="" type="text">
					</div>
					<div class="col-sm-2 label-control iq-font-white">Điện thoại: <span class="formRequired">(*)</span></div>
					<div class="col-sm-4">
						<input class="form-control" id="" placeholder="" type="text">
					</div>
			   </div>
          	<div class="form-group">
					<div class="col-sm-2 label-control iq-font-white">Địa chỉ:</div>
					<div class="col-sm-4">
						<input class="form-control" id="" placeholder="" type="text">
					</div>
					<div class="col-sm-2 label-control iq-font-white">Màu sắc: </div>
					<div class="col-sm-4">
						<select name="groupCategoryId" id="lstGroupCategory" class="form-control">
							<option value="0">- Chọn màu -</option>
							<option value="1">Màu xanh</option>
							<option value="2">Màu Đen</option>
							<option value="3">Màu Hồng</option>
						</select>
					</div>
			   </div>
          	<div class="form-group">
					<div class="col-sm-2 label-control iq-font-white">Số lượng:</div>
					<div class="col-sm-4">
						<input class="form-control" id="" placeholder="" type="text">
					</div>
					<div class="col-sm-2 label-control iq-font-white">Yêu cầu khác: </div>
					<div class="col-sm-4">
						<textarea cols="50" rows="2" name="form[yeucau]" id="yeucau" class="rsform-text-box area-control"></textarea>
					</div>
			   </div>
           	</form>
           	<div class="text-center iq-mt-50 ">
           		<div class="btn-group">
           			<a href="javascript:void(0)" class="button btn-colored text-uppercase">Đặt mua</a>
           			<a href="javascript:void(0)" class="button btn-grey text-uppercase">Nhập lại</a>
           		</div>
           		
           	</div>
			   
           
          </div>
        </div>


      </div>
    </div>
  </section>
-->

</div>
<!-- Main Content END --> 
<!-- Footer -->
<footer> 



  <section id="footer-info" class="footer-info iq-pt-60">
    <div class="container">
      <div class="row">
        <div class="col-sm-6 col-md-4">
          <div class="iq-footer-box text-left">
            <div class="iq-icon"> <i aria-hidden="true" class="ion-ios-location-outline"></i> </div>
            <div class="footer-content">
              <h4 class="iq-tw-6 iq-pb-10">Hệ thống bán hàng</h4>
              <ul class="ul-list">
				  <li><a href="https://vietteltelecom.vn/ho-tro/thong-tin-hotro/danh-sach-cua-hang">Cửa hàng trực tiếp Viettel</a></li>
				  <li><a href="https://viettelstore.vn/sieu-thi-gan-nhat.html">Hệ thống siêu thị Viettel Store</a></li>
				</ul>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-4 re4-mt-30">
          <div class="iq-footer-box text-left">
            <div class="iq-icon"> <i aria-hidden="true" class="ion-ios-list-outline"></i> </div>
            <div class="footer-content">
              <h4 class="iq-tw-6 iq-pb-10">Chính sách</h4>
              <ul class="ul-list">
				  <li><a href="#">Chính sách giao hàng</a></li>
				  <li><a href="./images/CHINHSACHBAOHANH.pdf">Chính sách bảo hành</a></li>
				  <li><a href="./images/Tai lieu huong dan xu ly loi dich vu MyKid - Website.pdf">Các câu hỏi thường gặp</a></li>
				</ul>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-4 re-mt-30">
          <div class="iq-footer-box text-left">
            <div class="iq-icon"> <i aria-hidden="true" class="ion-ios-telephone-outline"></i> </div>
            <div class="footer-content">
              <h4 class="iq-tw-6 iq-pb-10">Bán hàng - hỗ trợ</h4>
              <ul class="ul-list">
				  <li><a href="#">Tổng đài hỗ trợ: 18008098 (miễn phí)</a></li>
<!--
				  <li><a href="#">Hỗ trợ kỹ thuật/ bảo hành: 19000122</a></li>
				  <li><a href="#">Hotline: 098 123456</a></li>
-->
				  <li><a href="#">Email: mykid@viettel.com.vn</a></li>
				  <li><a href="./images/25.07_ HDSD tinh nang_MyKID.pdf" download="HDSD">Hướng dẫn sử dụng</a></li>
				</ul>
            </div>
          </div>
        </div>
<!--
        <div class="col-sm-6 col-md-3 re-mt-30">
          <ul class="info-share">
            <li><a href="javascript:void(0)"><i class="fa fa-twitter"></i></a></li>
            <li><a href="javascript:void(0)"><i class="fa fa-facebook"></i></a></li>
            <li><a href="javascript:void(0)"><i class="fa fa-google"></i></a></li>
          </ul>
        </div>
-->
      </div>
      <div class="row iq-mt-40">
        <div class="col-sm-12 text-center">
          <div class="footer-copyright iq-ptb-20"> Copyright @<span id="copyright"> 
            <script>document.getElementById('copyright').appendChild(document.createTextNode(new Date().getFullYear()))</script> </span> <a href="javascript:void(0)" class="text-green">MyKiD.</a> All Rights Reserved </div>
        </div>
      </div>
    </div>
  </section>
  <!-- Footer Info END --> 
</footer>
<!-- Footer END --> 
<!-- back-to-top -->
<div id="back-to-top" style="display: none;"> <a class="top" id="top" href="#top"> <i class="ion-ios-upload-outline"></i> </a> </div>
<!-- back-to-top End --> 
<!-- jQuery --> 
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script> 
<!-- jQuery Migrate --> 
<script type="text/javascript" src="https://code.jquery.com/jquery-migrate-1.4.1.min.js"></script> 
<!-- owl-carousel --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.2.1/owl.carousel.min.js"></script> 
<!-- Counter --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-countto/1.2.0/jquery.countTo.min.js"></script> 
<!-- Jquery Appear --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.appear/0.3.6/jquery.appear.min.js"></script> 
<!-- Magnific Popup --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script> 
<!-- Retina --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/retina.js/2.1.3/retina.min.js"></script> 
<!-- wow --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/wow/1.1.2/wow.min.js"></script> 
<!-- Countdown --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.countdown/2.2.0/jquery.countdown.min.js"></script> 
<!-- Skrollr --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js"></script> 
<!-- Bootstrap --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="./js/jquery.magnific-popup.min.js"></script> 
<!-- Custom --> 
<script type="text/javascript" src="./js/custom.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function () {
		jQuery('.news-detail-popup').magnificPopup({
			type: 'inline',
			preloader: false,
			alignTop: true,
			overflowY: 'scroll'
		});
	});
</script>

	<s:iterator value="guidelineList">
		<c:if test="${popupStatus == 1}">
			<div id="${href}" class="mfp-hide white-popup-block ">
				<s:property value="popup" escapeHtml="false"/>
			</div>
		</c:if>
	</s:iterator>
	
	<s:iterator value="featureList">
			<div id="${href}" class="mfp-hide white-popup-block ">
				<s:property value="popup" escapeHtml="false"/>
			</div>
	</s:iterator>
	<div id="news-detail-content-x" class="mfp-hide white-popup-block">
	  <h5>Đang cập nhật thông tin  </h5>
	</div>
</body>
</html>