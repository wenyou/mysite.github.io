/**
 * Created by Administrator on 2018/3/23.
 */
$(function(){

    $(".addservice_tit li").hover(function(){
        var $index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".addservice_list ul").eq($index).show().siblings().hide();
    },function(){})

    //切换
    $(".tab_hd li").click(function(){
        var $index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        var $bd=$(this).parents(".tab_hd").next(".tab_bd");
        if($bd.length>0){
                $bd.children().eq($index).show().siblings().hide();
        }
    });

    //发展历程
    (function ($) {
        var sup = $('.path .step');
        var btns = sup.find('.btn'),
            prev = btns.filter('.prev'),
            next = btns.filter('.next'),
            sup = sup.find('.wrap'),
            items = sup.find('.item'),
            item = items.eq(0),
            len = items.length,
            len_s = 4,              // 显示4个
            idx = 0,
            idx_m = len - len_s,
            idx_o = len_s - 1,
            width = item.width(),
            clickable = true;

        // 初始化容器宽度
        sup.width(width * len);
        for (var i = 0, temp = null, hei; i < len; i++) {
            temp = items.eq(i);
            hei = -temp.height();
            temp.data('height', hei);
            temp.css({ 'left': width * i, 'bottom': hei });
        }
        $(window).on('load', function () {
            // 默认显示4个
            itemRecu(0, len_s, 250, function () {
                items.css('opacity', 1);
            });
            // $('#history').hover(function () {
            //     btns.stop(false, true).fadeIn(300);
            // }, function () {
            //     btns.stop(false, true).fadeOut(300);
            // })
            prev.on('click', function () {
                if (clickable) idx === 0 ? undefined : slide(--idx, false);
            });
            next.on('click', function () {
                if (clickable) idx === idx_m ? undefined : slide(++idx, true);
            });
        });

        var item_prev = items.eq(0), item_next = items.eq(idx_o);
        function slide(idx, flag) {
            clickable = false;
            if (flag) {
                item_prev.css('bottom', item_prev.data('height'));
                item_prev = items.eq(idx);
                item_next = items.eq(idx + idx_o);
                item_next.css('bottom', 0);
            } else {
                item_next.css('bottom', item_next.data('height'));
                item_next = items.eq(idx + idx_o);
                item_prev = items.eq(idx);
                item_prev.css('bottom', 0);
            }
            sup.stop().animate({ 'margin-left': idx * -width }, 500, clickableFunc);
        }

        function clickableFunc() { clickable = true; }

        function itemRecu(idx, len, delay, func) {
            if (idx === len) {
                func();
                return;
            }
            item = items.eq(idx);
            item.css({ 'opacity': 1, 'bottom': 0 });
            setTimeout(function () {
                itemRecu(idx + 1, len, delay, func);
            }, delay);
        }
    })(jQuery);
})



