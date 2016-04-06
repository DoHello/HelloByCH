// JavaScript Document

$(function(){
	
	/*$(".show-bg").click(function(){
		$(".body-box").fadeIn(200);
		$(".body-bg").css({"display":"block",height:$(document).height()});

	});*/
	
	$(".body-bg").click(function(){
		$(".body-box").fadeOut(150);
		$(".body-bg").fadeOut(150);
		return false;
	});
	
	$('#Map1 .show-bg').each(function(index){
        $(this).click(function(){
			console.log(index);
			$('.body-box').show();
			$('.body-bg').show();
			$('.body-box div').eq(index).show().siblings().hide();
		});
    });
	
	$('#Map2 .show-bg').each(function(index) {
        $(this).click(function(){
			console.log(index);
			$('.body-box').show();
			$('.body-bg').show();
			$('.body-box div').eq(index).show().siblings().hide();
		});
    });
	
	$('#Map3 .show-bg').each(function(index) {
        $(this).click(function(){
			console.log(index);
			$('.body-box').show();
			$('.body-bg').show();
			$('.body-box div').eq(index).show().siblings().hide();
		});
    });
	
});
