/**
 * Created by dell on 2016/1/14.
 */
//menu-list
$('ul.menu-list li a').click(function(){
    $('ul.menu-list li a').removeClass('on');
    $(this).addClass('on');
})


//日历控件
$( "#datepicker" ).datepicker({
    dateFormat: "yy-mm-dd",
    monthNames: ['年 1 月', '年 2 月', '年 3 月', '年 4 月', '年 5 月', '年 6 月', '年 7 月', '年 8 月', '年 9 月', '年 10 月', '年 11 月', '年 12 月'],
    dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
    showMonthAfterYear: true
});

$( "#datepickerstart" ).datepicker({
    dateFormat: "yy-mm-dd",
    monthNames: ['年 1 月', '年 2 月', '年 3 月', '年 4 月', '年 5 月', '年 6 月', '年 7 月', '年 8 月', '年 9 月', '年 10 月', '年 11 月', '年 12 月'],
    dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
    showMonthAfterYear: true
});

$( "#datepickerend" ).datepicker({
    dateFormat: "yy-mm-dd",
    monthNames: ['年 1 月', '年 2 月', '年 3 月', '年 4 月', '年 5 月', '年 6 月', '年 7 月', '年 8 月', '年 9 月', '年 10 月', '年 11 月', '年 12 月'],
    dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
    showMonthAfterYear: true
});

