//内容页图片点击放大效果

    //内容页图片点击放大效果函数主体开始
    function imgPop(imgBoxMod){
        imgBoxMod.each(function(){
        //超过最大尺寸时自动缩放内容页图片尺寸
        var ctnImgWidth=$(this).width();
        if(ctnImgWidth>618){
                $(this).width(618);
            }
        //点击图片弹出层放大图片效果
        $(this).click( function(){
            $("#append_parent").html("<div id='imgzoom'><div id='imgzoom_zoomlayer[ class='zoominner'><p><span class='y'><a title='在新窗口打开' target='_blank' class='imglink' id='imgzoom_imglink' href=''>在新窗口打开</a><a title='关闭' class='imgclose'>关闭</a></span></p><div id='imgzoom_img' class='hm'><img src='' id='imgzoom_zoom' style='cursor:pointer'></div></div></div><div id='imgzoom_cover'></div>"); //生成HTML代码
            var domHeight =$(document).height(); //文档区域高度
            $("#imgzoom_cover").css({"display":"block","height":domHeight});
            var imgLink=$(this).attr("src");
            $("#imgzoom_img #imgzoom_zoom").attr("src",imgLink);
            $("#imgzoom").css("display","block");
            imgboxPlace();
            })
    })
            //关闭按钮
    $("#append_parent .imgclose").live('click',function(){
        $("#imgzoom").css("display","none");
        $("#imgzoom_cover").css("display","none");
    })
        //新窗口打开图片
    $("#imgzoom_imglink").live('click',function(){
        var imgLink=$("#imgzoom_zoom").attr("src");
        $("#imgzoom_imglink").attr("href",imgLink);
    })
    //弹出窗口位置
    function imgboxPlace(){
        var cwinwidth=$("#imgzoom").width();
        var cwinheight=$("#imgzoom").height();
        var browserwidth =$(window).width();//窗口可视区域宽度
        var browserheight =$(window).height(); //窗口可视区域高度
        var scrollLeft=$(window).scrollLeft(); //滚动条的当前左边界值
        var scrollTop=$(window).scrollTop(); //滚动条的当前上边界值
        var imgload_left=scrollLeft+(browserwidth-cwinwidth)/2;
        var imgload_top=scrollTop+(browserheight-cwinheight)/2;
        $("#imgzoom").css({"left":imgload_left,"top":imgload_top});
        }
    }
    //内容页图片点击放大效果函数主体结