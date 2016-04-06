<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
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
<body class="resources-group">
   

    <!--<section class="layoutwrap bg-eee padT115 blog-group" id="blog">
          <h2 class="title text-center col-333 text-uppercase"><div>blog</div></h2>
          <div class="container padT15 padB25">
            <div class="row">
                <div class="col-md-12">
                  <dl class="dl-horizontal blog-item clearfix">
                    <dt>18<br/><small>2014.SEP</small></dt>
                    <dd>
                      <h4><a href="blog_detailed.html">How to win, manage and optimise Hotel Prices Ads on Google</a></h4>
                      <p>How to win, manage and optimise Hotel Prices Ads on Google As we all know, Google entered the hotel metasearch game with the introduction of Google Hotel Price Ads (HPA) in a number of Google products.metasearch engines can quickly connect to and work with most of the world’s largest hotel groups and CRSs. MetaSearch Manager is a unique and powerful tool to control and optimize</p>
                    </dd>
                  </dl>
                  <dl class="dl-horizontal blog-item clearfix">
                    <dt>22<br/><small>2014.SEP</small></dt>
                    <dd>
                      <h4><a href="#">Opportunities and challenges for metasearch marketing with hotels</a></h4>
                      <p>Opportunities and challenges for metasearch marketing with hotels As hotels continue their efforts to keep up with latest and greatest in electronic distribution, the three hottest topics continue to be mobile, social media and metasearch.</p>
                    </dd>
                  </dl>
                  <dl class="dl-horizontal blog-item clearfix">
                    <dt>25<br/><small>2014.SEP</small></dt>
                    <dd>
                      <h4><a href="#">Targeting China next as a travel metasearch brand Be ready for the grind</a></h4>
                      <p>The Campaign Manager enables seamless management and optimization of a</p>
                    </dd>
                  </dl>
                  <dl class="dl-horizontal blog-item clearfix">
                    <dt>22<br/><small>2014.SEP</small></dt>
                    <dd>
                      <h4><a href="#">Opportunities and challenges for metasearch marketing with hotels</a></h4>
                      <p>Opportunities and challenges for metasearch marketing with hotels As hotels continue their efforts to keep up with latest and greatest in electronic distribution, the three hottest topics continue to be mobile, social media and metasearch.</p>
                    </dd>
                  </dl>
                </div>                 
            </div> 
          </div>  
    </section>-->
	
	<%@ include file="site_top.html"%>
    <section class="bg-fff padT105 download-group" id="download">      
      <div class="container" >
         <div class="row">
              <div class="col-md-12 col-lg-8">
                 <h2 class="title col-333 text-uppercase"><div>White Paper</div></h2>
                 
                  <div class="col-lg-6 col-md-12">
                  <dl>
                    <dt><h5><a href="article_detailed01.html">Five questions to help marketers solve the metasearch dilemma</a></h5></dt>
                    <dd>
                      <p>As we all know, Google entered the hotel metasearch game with the introduction of Google Hotel Price Ads (HPA) in a number of Google products.</p>
                      <div class="pt20"><a href="article_detailed01.html"><button type="button" class="btn btn-info btn-xs">Download &gt;&gt;</button></a></div>
                    </dd>
                  </dl> 
                  </div>
                  <div class="col-lg-6 col-md-12">
                  <dl>
                    <dt><h5><a href="article_detailed03.html">How to optimize Google Hotel Price Ad performance</a></h5></dt>
                    <dd>
                      <p>Google entered the hotel meta-search game with the introduction of Google Hotel Price Ads (HPA) in a number of Google products</p>
                      <div class="pt20"><a href="article_detailed03.html"><button type="button" class="btn btn-info btn-xs">Download &gt;&gt;</button></a></div>
                    </dd>
                  </dl>
                  </div>
                  <div class="col-lg-6 col-md-12">   
                  <dl>
                    <dt><h5><a href="article_detailed02.html">Opportunities and challenges for metasearch marketing with hotels</a></h5></dt>
                    <dd>
                      <p>As hotels continue their efforts to keep up with latest and greatest in electronic distribution, the three hottest topics continue to be mobile, social media and metasearch.</p>
                      <div class="pt20"><a href="article_detailed02.html"><button type="button" class="btn btn-info btn-xs">Download &gt;&gt;</button></a></div>
                    </dd>
                  </dl>  
                  </div>                 
              </div>
              
              <div class="col-md-12 col-lg-4"   >
                  <h2 class="title col-333 text-uppercase"><div>Videos</div></h2>
                  <embed src="<%=basePath%>/jsp/webSite/flv/MetaSearch video By DerbySoft.mp4" quality="high" width="100%"  align="middle" allowScriptAccess="sameDomain"></embed>
                  <p class=" padT35 padB35" id="EVENTS"><a class="btn-more" href="#" role="button">More »</a></p>
              </div>
         </div>
      </div>
    </section>

    <section class="layoutwrap bg4 padT85 information-group" id="information">
      <div class="container">
        <div class="row">
            <div class="col-md-5">
              <h2 class="title col-333 text-uppercase"><div>NEWS</div></h2>
              <c:forEach items="${model.newsList}" var="news">
              <div class="news-item">
                  <small>${news.CreateTime }</small>
                  <h4><a href="news.do?p=newsDetail&newsID=${news.NewsID }">"${news.Title }"</a></h4>
                  <p>${news.Abstractz }</p>
              </div>
                </c:forEach>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-1"></div>
            <div class="col-md-5" >
              <h2 class="title col-333 text-uppercase"><div>EVENTS</div></h2>
              <div class="news-item"><small>11th Mar</small>
                  <h4><a href="events01.html">DerbySoft Participated in ITB Berlin 2015</a></h4>
                  <p>ITB BERLIN ( Internationale Tourismus Boerse Berlin ), the world's largest travel trade show took place from 4 to 8 March 2015. This year, it attracted 10,096 exhibitors from 186 countries to display their products and services to 175,000 visitors, who included 115,000 trade visitors during the 5 days exhibition. </p>
              </div>
              <div class="news-item"><small>25th Jan</small>
                  <h4><a href="events.html">"DerbySoft participated in Worldhotels 2015 Global Annual Conference"</a></h4>
                  <p>This year's Worldhotels 2015 Global Annual Conference took place in Garden Hotel Guangzhou from 22 to 25 January, more than 300 hotel general managers and hoteliers from 250 regions across 65 countries joined this event. </p>
              </div>
              <div class="news-item"><small>21th Nov</small>
                  <h4><a href="events.html">"DerbySoft Participates in 2014 International Hospitality Professional Manager Conference"</a></h4>
                  <p>2014 International Hospitality Professional Manager Conference attracted nearly 500 professional hotel managers to the Hangzhou Zijingang International Hotel on Nov 6-7 to discuss "Quality for China Hotel Professional Managers in This Era" and "How to Improve Yourself and Your Industry Value".</p>
              </div>
              <div class="news-item noline"><small>05th Nov</small>
                  <h4><a href="events.html">"DerbySoft Participates in Web in Travel (WIT) and ITB Asia Clinics"</a></h4>
                  <p>"Web in Travel" held in Singapore on Oct 28-29.</p>
                  <p>In the fast-changing world of the Internet, tourism industry also got great changes. The theme of "Web in Travel" is how best to dig the benefits of the Internet brings to the tourism industry. Explore and learn more about the Internet technology, hope can bring you more insights and ideas to improve your internet business.</p>
              </div>                          
            </div>
        </div>
      </div>
    </section>

    <section class="layoutwrap bg-fff padT85 Q&A-group" id="Q&A">
      <h2 class="title text-center col-333 text-uppercase"><div>Q&A</div></h2>
      <div class="container">
        <div class="panel-group FAQ-panel-group" id="accordion" role="tablist" aria-multiselectable="true">
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
              <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                 <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                 <span class="pull-left tips">Q:</span>
                 What does DerbySoft do?
                </a>
              </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                DerbySoft is a global technology company serving hotel companies and the hospitality distribution industry since 2002.  The rise of MetaSearch travel sites in recent years lead us to develop cutting edge tools to both manage connectivity and optimize performance for these channels.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What are the competitive advantages of DerbySoft MetaSearch Manager compared to those channel managers on the market?
                </a>
              </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                MetaSearch advertising requires 3 fundamental components:  Connetivity, Cache, and Control.  We do see other companies providing individual components of this matrix, however DerbySoft’s MetaSearch Manager program is the only solution to provide all of these elements from one place.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  If I am an individual hotel, how can I work with DerbySoft MetaSearch Manager?
                </a>
              </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
               We have many different options for connecting to and managing both individual properties and groups of hotels.  We would work with you to learn more about your existing capabilities and identify the approach best suited to your needs.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseTwo">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  If I am a hotel chain, how can I work with DerbySoft MetaSearch Manager?
                </a>
              </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                We already connect some of the largest brands names in the industry to MetaSearch channels and can likewise develop a solution that works for you.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFive">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Do I have to have PMS or CRS in order to work with DerbySoft MetaSearch Manager?
                </a>
              </h4>
            </div>
            <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
               You will need to have a system for storing and managing your Available Rates & Inventory (ARI) which we can connect to.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSix">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Do I have to use DerbySoft’s Connectivity Service before working with MetaSearch Manager?
                </a>
              </h4>
            </div>
            <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Yes
              </div>
            </div>
          </div>
          <!--添加7--15-->
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSeven">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  How much money should I put for MetaSearch Marketing fee per month?
                </a>
              </h4>
            </div>
            <div id="collapseSeven" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSeven">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                This varies depending upon your goals and objectives.  Many industry experts suggest at least 10% of your annual marketing budget should be committed to MetaSearch.  DerbySoft’s MetaSearch management team would work with you to determine the budget best suited to your needs.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEight">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseEight" aria-expanded="false" aria-controls="collapseEight">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What is ROAS after working with DerbySoft MetaSearch Manager?
                </a>
              </h4>
            </div>
            <div id="collapseEight" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEight">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                This depends upon many factors, including the goals for your campaigns.  In theory, we could set a target ROAS of 30-to-1, however if it does not deliver any bookings this may not make sense.  We generally encourage an approach that seeks to find the right balance between ROAS, bookings, and revenue.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingNine">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseNine" aria-expanded="false" aria-controls="collapseNine">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  How do you charge?
                </a>
              </h4>
            </div>
            <div id="collapseNine" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingNine">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                We have several different pricing models that can be customized to your needs.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTen" aria-expanded="false" aria-controls="collapseTen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Do you have after sales service?
                </a>
              </h4>
            </div>
            <div id="collapseTen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Yes, and we believe this is a critical component of our MetaSearch management program.  MetaSearch is complicated, and we will provide you with an account manager to act as your consultant and help guide you along the way.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEleven">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseEleven" aria-expanded="false" aria-controls="collapseEleven">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What is a “CPA Model” and what are the pros & cons of such programs?
                </a>
              </h4>
            </div>
            <div id="collapseEleven" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEleven">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                CPA stands for “Cost Per Acquisition.”  This model is ideal for a hotel that does not have the flexibility to include MetaSearch ad spend as part of their existing budget.  Instead, they would treat each booking as a commissionable reservation, and DerbySoft will pay the ad spend for them.  The disadvantage to this plan is it may not be ideal for maximum visibility.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwelve">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwelve" aria-expanded="false" aria-controls="collapseTwelve">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What are facilitated or assisted booking models?
                </a>
              </h4>
            </div>
            <div id="collapseTwelve" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwelve">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Facilitated and Assisted booking models are a new trend where the MetaSearch site in question manages the booking process within their own environment rather than handing it off to the hotel’s own website.  This need arose primarily due to the lack of mobile optimized booking engines, however has since spread to desktop use as well.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThirteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThirteen" aria-expanded="false" aria-controls="collapseThirteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What are Google Private Rates?
                </a>
              </h4>
            </div>
            <div id="collapseThirteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThirteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                These are special rate programs which can be built with restricted viewership, requiring the user to be signed in on their Google account.  The advantage to Google Private rates is it allows the hotel to achieve a better price bucket via lower rates, while at the same time not violating rate parity clauses.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFourteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFourteen" aria-expanded="false" aria-controls="collapseFourteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What is Qunar TTS?
                </a>
              </h4>
            </div>
            <div id="collapseFourteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFourteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                This is Qunar’s version of an assisted booking program.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFifeteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFifeteen" aria-expanded="false" aria-controls="collapseFifeteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What is TripAdvisor Instant Book?
                </a>
              </h4>
            </div>
            <div id="collapseFifeteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFifeteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                This is TripAdvisor’s version of an assisted booking program.
              </div>
            </div>
          </div>
          <!--添加新闻16--24-->
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSixteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSixteen" aria-expanded="false" aria-controls="collapseSixteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Why not just let the OTA sites book this for me?
                </a>
              </h4>
            </div>
            <div id="collapseSixteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSixteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                This can include many reasons, but perhaps the most important are cost factors and the advantage of owning your guests.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSeventeen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSeventeen" aria-expanded="false" aria-controls="collapseSeventeen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Which channels operate on a bidding model?
                </a>
              </h4>
            </div>
            <div id="collapseSeventeen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSeventeen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                The MetaSearch channels currently offering bidding models include Google, TripAdvisor, Kayak, and Trivago.  
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEighteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseEighteen" aria-expanded="false" aria-controls="collapseEighteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I bid differently for specific dates?
                </a>
              </h4>
            </div>
            <div id="collapseEighteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEighteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                None of the bidding channels are supporting date specific bid settings at this time.  However, if you know your typical lead time is (for example) 72hrs, you can generally adjust your bids based on this length of time.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingNineteen">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseNineteen" aria-expanded="false" aria-controls="collapseNineteen">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I bid differently for specific countries?
                </a>
              </h4>
            </div>
            <div id="collapseNineteen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingNineteen">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Yes
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwenty">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty" aria-expanded="false" aria-controls="collapseTwenty">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I bid differently by city?
                </a>
              </h4>
            </div>
            <div id="collapseTwenty" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwenty">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                No
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwenty-one">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty-one" aria-expanded="false" aria-controls="collapseTwenty-one">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I set a target ROAS?
                </a>
              </h4>
            </div>
            <div id="collapseTwenty-one" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwenty-one">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Yes
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwenty-two">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty-two" aria-expanded="false" aria-controls="collapseTwenty-two">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I set a target SOV?
                </a>
              </h4>
            </div>
            <div id="collapseTwenty-two" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwenty-two">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
               Yes, however only for Google.
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwenty-three">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty-three" aria-expanded="false" aria-controls="collapseTwenty-three">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  Can I set a budget?
                </a>
              </h4>
            </div>
            <div id="collapseTwenty-three" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwenty-three">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                Yes, budgets can be set by hotel, by channel, and by bid groups (coming soon).
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwenty-four">
              <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty-four" aria-expanded="false" aria-controls="collapseTwenty-four">
                  <em class="pull-right icon-minus"></em><em class="pull-right icon-plus"></em>
                  <span class="pull-left tips">Q:</span>
                  What sort of conversion rates will I see and what factors impact this?
                </a>
              </h4>
            </div>
            <div id="collapseTwenty-four" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwenty-four">
              <div class="panel-body">
                <span class="pull-left tips">A:</span>
                The conversion rates will depend greatly upon the type of hotel as well as the efficiency of the booking process.  MetaSearch is best suited to spontaneous booking decisions, which need to be fulfilled in the fewest number of steps possible.  This same consumer behavior also generates better conversion rates for types of hotels that are more condusive to short notice bookings, as opposed to hotels that involve a longer decision making process.
              </div>
            </div>
          </div>
          <!--添加新闻结束-->
        </div>
      </div>
      
    </section>
    <script src="js/require.js" data-main="js/main" ></script>
</body>
</html>