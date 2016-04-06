<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
<html lang="zh-cn">
  <head>
	<base href="<%=basePath%>jsp/webSite/">
    <meta charset="utf-8">
    <title>DerbySoft MetaSearch Manager</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="X-UA-Compatible" content="IE-9">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="mobile-web-app-capable" content="yes"> 
    <meta name="renderer" content="webkit">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no,minimal-ui">
    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">   
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="css/style-ie.css">  
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <![endif]-->
    <script src="js/lib/prefixfree.min.js"></script>
  </head>
<body>

	<%@ include file="site_top.html"%>

    <div id="home-banner-carousel" class="carousel slide banner-carousel" data-ride="carousel" >
          <ol class="carousel-indicators hidden-xs">
              <li data-target="#home-banner-carousel" data-slide-to="0" class="active"></li>
              <li data-target="#home-banner-carousel" data-slide-to="1"></li>
              <li data-target="#home-banner-carousel" data-slide-to="2"></li>
          </ol>          
          <div class="carousel-inner" role="listbox">
              <div class="item active" style="background:#eee"> 
                  <div class="carousel-item-bg"><img src="images/banner4-bg.jpg" alt=""/></div>
                  <div class="banner-item banner04">
                       <a href="message.html" class="btn-freetrial hover-shadow hide">Contact Us</a>
                       <div class="banner-text text-center">
                          <h3 class="text-uppercase text-center pt70">faster BETTER COST-EFFECTIVE</h3>
                          <h4>The Complete Metasearch Solution</h4>
                          <a href="message.html" class="btn">Contact Us</a>
                       </div>   
                  </div>
             </div>
			  
            <div class="item" style="background:#666">            
                  <div class="carousel-item-bg"><img src="images/banner2-bg.jpg" alt=""/></div>
                  <div class="banner-item banner02">
                     <a href="message.html" class="btn-freetrial hover-shadow hide">Contact Us</a>
                     <span class="banner-img"></span>
                     <div class="banner-text text-uppercase">ONe connection <br/>to multiple platforms <br/><a href="message.html" class="btn">Contact Us</a></div>
                     
                  </div>            
              </div>
              <div class="item" style="background:#999"> 
                  <div class="carousel-item-bg"><img src="images/banner3-bg.jpg" alt=""/></div>
                  <div class="banner-item banner03">
                     <a href="comingSoon.html" class="btn-freetrial hover-shadow hide">Contact Us</a>
                     <span class="banner-img"></span>
                     <div class="banner-text text-uppercase">
                        <h3>Increase your roi</h3>
                        <h4>- AUTOMATED BIDDING AND OPTIMIZATION</h4>
                        <h4>- VISIBLE MANAGEMENT</h4>
                        <h4>- DETAILED REPORTING SYSTEMS</h4>
                        <a href="message.html" class="btn">Contact Us</a>
                     </div>
                  </div>
              </div>
              
              
          </div>
          <div class="carousel-wrap">
              <a href="Solutions.html#tabNr1"><button type="button" class="btn btn-huge btn-danger"><i class="connectivity"></i>Connect</button></a>
              <a href="Solutions.html#tabNr2"><button type="button" class="btn btn-huge btn-success"><i class="cache"></i>Cache</button></a>
              <a href="Solutions.html#tabNr3"><button type="button" class="btn btn-huge btn-warning"><i class="control"></i>Control</button></a>
          </div>
    </div>

    <section class="layoutwrap bg-fff padT105 metasearchinfo-group hide" id="metasearchinfo">
        <h2 class="title text-center col-333"><div>What Is MetaSearch Manager ?</div></h2>
        <div class="tiltips text-center col-666">DerbySoft is a global technology company serving hotel companies and the hospitality distribution industry since 2002. </div>
        <div class="container padT55 padB55 hidden-xs">
            <div class="row">
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-platform"></em></span>
                  <h3><div>Platform</div></h3>
                  <p><a href="#">MetaSearch Management</a></p>
                  <p><a href="#">Dashboard And Detailed Reporting</a></p>
                  <p><a href="#">Campaign Manager</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-solution"></em></span>
                  <h3><div>Solutions</div></h3>
                  <p><a href="#">Connectivity Solution</a></p>
                  <p><a href="#">Reporting Analysis</a></p>
                  <p><a href="#">Bidding Optimization</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-channel"></em></span>
                  <h3><div>Channels</div></h3>
                  <p><a href="#">Google</a></p>
                  <p><a href="#">TripAdvisor</a></p>
                  <p><a href="#">Kayak</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-resource"></em></span>
                  <h3><div>Resources</div></h3>
                  <p><a href="#">Blog</a></p>
                  <p><a href="#">White Paper</a></p>
                  <p><a href="#">News</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-aboutus"></em></span>
                  <h3><div>About Us</div></h3>
                  <p><a href="#">At A Glance</a></p>
                  <p><a href="#">Our Culture</a></p>
                  <p><a href="#">Our Vision</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="col-lg-2 col-sm-4 col-xs-6">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-contact"></em></span>
                  <h3><div>Contact</div></h3>
                  <p><a href="#">Email</a></p>
                  <p><a href="#">Message</a></p>
                  <p><a href="#">Tel</a></p>
                  <p><a href="#">more »</a></p>
                </div>
            </div>
        </div>

        <div id="carousel-metasearchinfo-generic" class="carousel slide visible-xs" data-ride="carousel">            
            <div class="carousel-inner" role="listbox">
              <div class="item clearfix active">
                 <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-platform"></em></span>
                  <h3><div>Platform</div></h3>
                  <p><a href="#">MetaSearch Management</a></p>
                  <p><a href="#">Dashboard And Detailed Reporting</a></p>
                  <p><a href="#">Campaign Manager</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-solution"></em></span>
                  <h3><div>Solutions</div></h3>
                  <p><a href="#">Connectivity Solution</a></p>
                  <p><a href="#">Reporting Analysis</a></p>
                  <p><a href="#">Bidding Optimization</a></p>
                  <p><a href="#">more »</a></p>
                </div>
              </div>
              <div class="item clearfix">
                 <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-channel"></em></span>
                  <h3><div>Channels</div></h3>
                  <p><a href="#">Google</a></p>
                  <p><a href="#">TripAdvisor</a></p>
                  <p><a href="#">Kayak</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-resource"></em></span>
                  <h3><div>Resources</div></h3>
                  <p><a href="#">Blog</a></p>
                  <p><a href="#">White Paper</a></p>
                  <p><a href="#">News</a></p>
                  <p><a href="#">more »</a></p>
                </div>                
              </div>
              <div class="item clearfix">
                <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-aboutus"></em></span>
                  <h3><div>About Us</div></h3>
                  <p><a href="#">At A Glance</a></p>
                  <p><a href="#">Our Culture</a></p>
                  <p><a href="#">Our Vision</a></p>
                  <p><a href="#">more »</a></p>
                </div>
                <div class="metasearchinfo-item">
                  <span class="metasearchinfo-ico hover-shadow"><em class="metasearchinfo-ico-con icon-contact"></em></span>
                  <h3><div>Contact</div></h3>
                  <p><a href="#">Email</a></p>
                  <p><a href="#">Message</a></p>
                  <p><a href="#">Tel</a></p>
                  <p><a href="#">more »</a></p>
                </div>                
              </div>             
            </div>
            <!-- Controls -->
            <a class="btn-prev" href="#carousel-metasearchinfo-generic" role="button" data-slide="prev">
              <span class="platform-control-ico platform-control-left"></span>
            </a>
            <a class="btn-next" href="#carousel-metasearchinfo-generic" role="button" data-slide="next">
              <span class="platform-control-ico platform-control-right"></span>
            </a>
        </div>
    </section>

    
    <!-- 修改 -->
    <section class="layoutwrap bg-fff padT105 download-group" id="download" style="min-height:630px;">
      <h2 class="title text-center col-333 text-uppercase"><div><a href="javascript:;" class="col-333">What Is MetaSearch Manager ?</a></div></h2>
      <p class="text-center col-666 ft20">DerbySoft's MetaSearch Manager is a new solution to enable and manage metasearch connections <br/>and optimize the marketing opportunities they hold. </p>
      <div class="video"><a href="https://vimeo.com/131061522">watch the video</a></div>
      <div class="wrap search">
         <div class="row">
              <div class="col-sm-6 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="platform.html">
                            <span><i class="icon icon-icon-platform"></i></span>
                            <dt>Platform<em></em></dt>
                        </a>
                   </dl>
              </div>
              
              <div class="col-sm-6 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="Solutions.html">
                            <span><i class="icon icon-icon-solution"></i></span>
                            <dt>Solutions<em></em></dt>
                         </a>
                   </dl>
              </div>
              <div class="col-sm-6 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="channel.html">
                            <span><i class="icon icon-icon-channel"></i></span>
                            <dt>Channels<em></em></dt>
                        </a>
                   </dl>
              </div>
              
              
              <div class="col-sm-6 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="Resources2.html">
                            <span><i class="icon icon-icon-resource"></i></span>
                            <dt>Resources<em></em></dt>
                         </a>
                   </dl>
              </div>
              
              <div class="col-sm-6 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="about.html">
                            <span><i class="icon icon-icon-aboutus"></i></span>
                            <dt>About Us<em></em></dt>
                         </a>
                   </dl>
              </div>
              
              <div class="col-sm-2 col-lg-2 col-md-4 col-xs-6">
              		<dl>
                    	<a href="message.html">
                            <span><i class="icon icon-icon-mail"></i></span>
                            <dt>Contact<em></em></dt>
                         </a>
                   </dl>
              </div>
              
         </div>
      </div>
    </section>
    <!-- /修改 -->

    <section class="layoutwrap bg1 padT55 platform-group" id="platform">
        <h2 class="title text-center col-fff text-uppercase"><div><a href="platform.html" class="col-fff">Platform</a></div></h2>
        <div class="tiltips text-center col-fff">DerbySoft’s MetaSearch Manager Platform is the only application to combine all necessary components of MetaSearch marketing in one place.  Hotels can easily connect to multiple MetaSearch channels and efficiently optimize their campaigns.  Likewise, the MetaSearch channels themselves use our technologies to quickly connect to the world’s largest hotel groups and Central Reservation Systems.</div>
        <div class="playform-slywrap ">
          <div class="sly playform-sly">
            <ul>
              <li class="active">
                  <div class="carousel-item">
                      <span class="carousel-item-icon icon-platform1"></span>
                      Metasearch Manager
                  </div>
                  <div class="carousel-content">
                    <h4>MetaSearch Manager</h4>
                    <p>The MetaSearch Manager enables seamless management and optimization of MetaSearch campaigns across multiple channels. Hoteliers can use the application to control settings across an entire portfolio or channel, or at a very granular level including specific rooms and rate plans.</p>
                    <span class="carousel-item-icon icon-platform1"></span>
                  </div>
              </li>
              
              
              <li class="">
                  <div class="carousel-item">
                      <span class="carousel-item-icon icon-platform3"></span>
                      Reporting and Analytics
                  </div>
                  <div class="carousel-content">
                    <h4>Reporting and Analytics</h4>
                    <p>DerbySoft’s MetaSearch Manager application provides configurable reporting metrics for instand and actionable insights. Detailed analysis can include campaign results data such as number of impressions, clicks, bookings, conversion rates, Return on Ad Spend (ROAS) and more. MetaSearch Manager can even aggregate results from multiple channels to enable cross-channel analytics.</p>
                    <span class="carousel-item-icon icon-platform3"></span>
                  </div>
              </li>
              
              <li class="">
                  <div class="carousel-item">
                      <span class="carousel-item-icon icon-platform6"></span>
                      Automated Bidding Manager
                  </div>
                  <div class="carousel-content">
                    <h4>Automated Bidding Manager</h4>
                    <p>The Bidding Modules for Google Hotel Ads, TripAdvisor, and other auction model MetaSearch platforms are the first automatic bidding tools in the Hospitality Industry!  Clients can use these tools to target specific ad positions and/or ROAS.  Using our proprietary algorithms, the system automatically adjusts your spending according to specific rules, goals, and budgetary constraints.</p>
                    <span class="carousel-item-icon icon-platform6"></span>
                  </div>
              </li>
              
              
            </ul>
          </div>
          <ul class="slidebtn visible-xs hidden-xs"></ul>
        </div>
    </section>

    <section class="layoutwrap bg-fff padT105 download-group hidden-xs" id="download">
      <h2 class="title text-center col-333 text-uppercase"><div><a href="javascript:;" class="col-333">download</a></div></h2>
      <div class="container padT35">
         <div class="row">
              <div class="col-sm-12 col-lg-8" style="padding-right:30px">
                  <h4><i class="download-icon icon-pdf"></i>White Paper<a href="#">MORE <strong>+</strong></a></h4> 
                  <div class="row">
                    <!-- <div class="line col-lg-5 col-sm-12" style="display:none;">
                       <dl>
                        <dt><h5>Hedna_change_discovery_servi</h5></dt>
                        <dd>
                          <p>Change Discovery is a fairly new concept to the hotel distribution industry. The various implementations thus far have created a need to define</p>
                          <div class="clearfix"><a href="#" class="download-icon icon-down"></a></div>
                        </dd>
                      </dl>
                    </div> -->
                    <div class="line col-lg-5 col-sm-12">
                       <dl>
                        <dt><h5><a href="article_detailed01.html">Five questions to help marketers solve the metasearch dilemma</a></h5></dt>
                        <dd>
                          <p>As we all know, Google entered the hotel metasearch game with the introduction of Google Hotel Price Ads (HPA) in a number of Google products.</p>
                          <div class="clearfix"><a href="article_detailed01.html" class="download-icon icon-down"></a></div>
                        </dd>
                      </dl>
                    </div>
                    <div class="col-md-1"></div>
                    <div class="col-md-1"></div>
                    <div class="line col-lg-5 col-sm-12">
                       <dl>
                        	<dt><h5><a href="article_detailed03.html">How to optimize Google Hotel Price Ad performance</a></h5></dt>
                        	<dd>
                           <p>Google entered the hotel meta-search game with the introduction of Google Hotel Price Ads (HPA) in a number of Google products</p>
                          	<div class="clearfix"><a href="article_detailed03.html" class="download-icon icon-down"></a></div>
                        </dd>
                      </dl>
                    </div>
                 </div>
                 <div class="row">
                    <div class="line col-lg-5 col-sm-12">
                       <dl>
                        <dt><h5><a href="article_detailed02.html">Opportunities and challenges for metasearch marketing with hotels</a></h5></dt>
                        <dd>
                          <p>As hotels continue their efforts to keep up with latest and greatest in electronic distribution, the three hottest topics continue to be mobile, social media and metasearch.</p>
                          <div class="clearfix"><a href="article_detailed02.html" class="download-icon icon-down"></a></div>
                        </dd>
                      </dl>
                    </div>
                    <div class="col-md-1"></div>
                    <div class="col-md-1"></div>
                    
                 </div>
           	</div>
              
              <div class="col-sm-12 col-lg-4">
                  <h4><i class="download-icon icon-avi"></i>Videos<a href="#">MORE <strong>+</strong></a></h4>
				  <embed src="flv/MetaSearch video By DerbySoft.mp4" quality="high" width="100%"  align="middle" allowScriptAccess="sameDomain"></embed>
              </div>
         </div>
      </div>
    </section>


    <section class="layoutwrap bg3 padT115 information-group" id="information">
      <div class="container">
        <div class="row">
            <div class="col-md-5">
              <h2 class="title col-333 text-uppercase"><div>NEWS</div></h2>
               <c:forEach items="${model.newsList}" var="news">
              <div class="news-item">
                  <small>${news.CreateTime }</small>
                  <h4><a href="/Derby/siteHome.do?p=newsDetail&indexID=${news.IndexID }">"${news.Title }"</a></h4>
                  <p>${news.Abstractz }</p>
              </div>
                </c:forEach>
              <a href="#" class="icon-more visible-lg"></a>              
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-1"></div>
            <div class="col-md-5">
                <h2 class="title col-333 text-uppercase"><div>Media Report</div></h2> 
                <div id="carousel-mediareport" class="carousel slide carousel-mediareport" data-ride="carousel">                  
                  <!-- Wrapper for slides -->
                  <div class="carousel-inner" role="listbox">
                    <div class="item active">
                      <h4><a href="#">1"Targeting China next as a travel metasearch brand?......</a></h4>
                      <img src="images/temp/report-img01.jpg" class="img-thumbnail" alt="">
                      <p>2014 China Hotel Marketing Summit successfully held in Shanghai Eastern Jin Hilton,in the two-day summit in more than 30 hotels and related industries from experts and executives at the meeting were</p>
                    </div>
                    <div class="item">
                      <h4><a href="#">2"Targeting China next as a travel metasearch brand?......</a></h4>
                      <img src="images/temp/report-img02.jpg" class="img-thumbnail" alt="">
                      <p>2014 China Hotel Marketing Summit successfully held in Shanghai Eastern Jin Hilton,in the two-day summit in more than 30 hotels and related industries from experts and executives at the meeting were</p>
                    </div>
                    <div class="item">
                      <h4><a href="#">3"Targeting China next as a travel metasearch brand?......</a></h4>
                      <img src="images/temp/report-img03.jpg" class="img-thumbnail" alt="">
                      <p>2014 China Hotel Marketing Summit successfully held in Shanghai Eastern Jin Hilton,in the two-day summit in more than 30 hotels and related industries from experts and executives at the meeting were</p>
                    </div>                     
                  </div>
                  <!-- Controls -->
                  <a class="left carousel-control" href="#carousel-mediareport" role="button" data-slide="prev">
                    <span class="carousel-mediareport-icon left"></span>
                    <span class="sr-only">Previous</span>
                  </a>
                  <a class="right carousel-control" href="#carousel-mediareport" role="button" data-slide="next">
                    <span class="carousel-mediareport-icon right"></span>
                    <span class="sr-only">Next</span>
                  </a>
                </div>
                <a href="#" class="btn-more">LEARN MORE</a>
            </div>
        </div>
      </div>
    </section>
    
    
    
    <section class="layoutwrap bg-fff padT105 download-group hidden-xs" style="min-height:300px;">
      <h2 class="title text-center col-333 text-uppercase"><div><a href="javascript:;" class="col-333">The leading MetaSearch Management Platform</a></div></h2>
      <div class="container padT35">
         <div class="row">
              <img src="images/kehu.jpg"/>
         </div>
      </div>
    </section>
    
    

    <section class="layoutwrap bg-1e9ec4 contact-group" id="contact">
      <div class="container contact-block1">
        <div class="row">
          <div class="col-md-4 clearfix">
            <i class="contact-icon icon-local"></i>
            <address>
                <strong>DerbySoft Offices</strong>
                7F(South Tower), Building, No.20. Lane 91 E'shan Road Shanghai P.R. China<br>
                U.S. OFFICE: 15950 North Dallas Parkway Suite 400 Dallas, TX 75248
            </address>
          </div>
          <div class="col-md-4 pl117 clearfix">
            <i class="contact-icon icon-phone"></i>
            <address>
                <strong>Contact Us</strong>
                <span class="col-fff contact-block1-style">(Shanghai Office) </span><em class="contact-block1-style">+86 (21) 5168 8822 </em>
                <span class="col-fff contact-block1-style">(U.S. Office) </span><em class="contact-block1-style">+1   (214) 785 8110 </em>
            </address>
          </div>
          <div class="col-md-4 pl70 clearfix" >
            <i class="contact-icon icon-message"></i>
            <address>
                <strong class="pt20"><a href="message.html">New Business Inquiries</a></strong>
            </address>
          </div>
        </div>
      </div>   
      <div class="contact-block2" id="contactus">
        <div class="con-wrap">
          <div class="row padB55">
            <div class="contact-info-item padB35">
              <em></em>
              <p class="contact-info">At DerbySoft, we understand our guests’ wishes and we are dedicated to providing for every inquiry. Feel free to get in touch with the DerbySoft team and let us know how we can meet your expectations.</p>
              <address class="hidden-xs">
                <strong>DerbySoft</strong>
                <p><abbr title="Address">ADD:</abbr>7F(South Tower), Building, No.20. Lane 91 E'shan Road Shanghai P.R. China</p>
                <p><abbr title="Phone">TEL:</abbr>+86 (21) 5168 8822 (Shanghai Office) <br> +1   (214) 785 8110 (U.S. Office)</p>
               Email:<a href="mailto:MetaSearchManager@DerbySoft.com" class="col-fff">MetaSearchManager@DerbySoft.com</a>
              </address>
              <p class="hidden-xs"><img src="images/code.jpg" alt="" id="code"></p>
            </div>
          </div>
          
      </div> 
      
      <div class="container">
      <div class="row">
            <div class="col-md-12 clearfix contact-foot-item col-xs-12">
                <div class="pull-left">DerbySoft Ltd. All rights reserved.</div>
                <div class="pull-right">
                   <a href="https://twitter.com/MetaSearchM" class="foot-icon icon-t"></a>
                   <a href="https://www.facebook.com/derbysoft?ref=bookmarks" class="foot-icon icon-f"></a>
                   <a href="https://www.linkedin.com/company/derbysoft" class="foot-icon icon-in"></a>
                </div>
            </div>
          </div>  
        </div>
      </div>
    </section>


    <script src="js/require.js" data-main="js/main" ></script>  
</body>
</html>