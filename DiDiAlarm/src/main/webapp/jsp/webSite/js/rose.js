function carousel_platform() {
    var $playformsly = $('.playform-sly');
    if ($(window).width() < 641) {        
        $('.playform-sly ul li').width($playformsly.width());
    }

    $('#platform .playform-sly').sly({
        horizontal: 1,
        itemNav: "centered",
        activateOn: "click",
        pagesBar: $(".slidebtn"),
        activatePageOn: "click",
        dragContent: 1,
        touchDragging: 1,
        smart: 1,
        prev: '#platform .playform-btn-prev',
        next: '#platform .playform-btn-next',
        easing: "easeOutBack",
        pageBuilder: function(index) {
            return '<li>&nbsp;</li>';
        }
    });
}

function tabs() {
    $('.solutTabnav li').click(function() {
        $(this).addClass('active').siblings().removeClass('active');
        var index = $(this).index();
        $(".solutTabBox > div.solutLine").eq($(this).index()).show().siblings("div.solutLine").hide();
    });	    
}

function carousel_banner() {
    $('#home-banner-carousel').carousel({
        interval: 4000,
        pause: 'hover'
    });
    $('#home-banner-carousel').hammer().on('swipeleft', function() {
        $(this).carousel('next');
    });
    $('#home-banner-carousel').hammer().on('swiperight', function() {
        $(this).carousel('prev');
    });
}

function lazyload_img() {
    var $lazy = $("img.lazy");
    $lazy.lazyload({
        effect: "fadeIn"
    });
}

function match_browser() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1]:
        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    //以下进行测试
    if (ua.match(/msie ([\d.]+)/) && parseInt(Sys.ie) <= 8) {
        $('body').html(' ').removeClass();
        alert('本网站是由html5代码编写，为了体验更好的预览效果，建议您使用IE8以上或者支持HTML5的浏览器进行完整预览，给您带来不便敬请谅解！');
    }
}

function nav_solution(){    
    /*nav jumpto solution.html*/
    var str=window.location.href,
        len=str.length,
        tabnr=str.substring(len-6,len);
    switch(tabnr){
        case 'tabNr1': 
            $('.solutTabnav li:eq(0)').trigger( "click" );
            break;
        case 'tabNr2': 
            $('.solutTabnav li:eq(1)').trigger( "click" );
            break;
        case 'tabNr3': 
            $('.solutTabnav li:eq(2)').trigger( "click" );
            break;    
    }
    if($('#solutTab').length){
        $('#navSolution>li').each(function(i){           
            $(this).click(function(){
                $('.solutTabnav li:eq('+i+')').trigger( "click" );
            })            
        })
    }
}

function inputFocus(){
	//文本域显示隐藏
	$(".Inpfocus").focus(function(){
		if($(this).val() ==this.defaultValue){  
			$(this).val("");
			$(this).css('color','#555');
		} 
	}).blur(function(){
		if ($(this).val() == '') {
			$(this).val(this.defaultValue);
			$(this).css('color','#999');
		}
	});
	$(".clickTextfocus").focus(function(){
		$(this).hide().siblings('input,textarea').css('display','block').focus();
	});
	$(".clickfocus").blur(function(){
		if($(this).val()==''){
			$(this).hide().siblings('input,textarea').css('display','block');	
		}
	});
}

function rosefunction() {
	lazyload_img();
    carousel_platform();
    carousel_banner();
    tabs();
    match_browser();
    nav_solution();
    $('.hei01').css('height',$('#at1').height()+'px');
    $('.hei02').css('height',($('#at2').height()+72)+'px');
    $('.hei03').css('height',($('#at3').height()+72)+'px');
	// console.log($('#at1').height());

    $('#carousel-mediareport').hammer().on('swipeleft', function() {
        $(this).carousel('next');
    });
    $('#carousel-mediareport').hammer().on('swiperight', function() {
        $(this).carousel('prev');
    });

    $('#carousel-metasearchinfo-generic').hammer().on('swipeleft', function() {
        $(this).carousel('next');
    });
    $('#carousel-metasearchinfo-generic').hammer().on('swiperight', function() {
        $(this).carousel('prev');
    });

    $('#carousel-pageviedo').hammer().on('swipeleft', function() {
        $(this).carousel('next');
    });
    $('#carousel-pageviedo').hammer().on('swiperight', function() {
        $(this).carousel('prev');
    });
	inputFocus();
    navShow();
}
function navShow(){
    $('.navbar-nav li').hover(function(){
        $(this).addClass('openShow');
    },function(){
        $(this).removeClass('openShow');
    });
}