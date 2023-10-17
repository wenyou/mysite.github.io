/**
 * Created by Administrator on 2018/3/23.
 */
$(function(){
    if($("#hefei").length>0){
        map();
    }

    //滚动固定
    if($("#fixTab").length>0){
        var offsetTop=$("#fixTab").offset().top;
        $(window).scroll(function(){
           var scrollTop=$(window).scrollTop();
            if(parseInt(offsetTop)<parseInt(scrollTop)){
                $("#fixTab").css("position","fixed");
            }else{
                $("#fixTab").css("position","static");
            }
        })
    }

    //切换
    $(".tab_hd li").click(function(){
        var $index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        var $bd=$(this).parents(".tab_hd").next(".tab_bd");
        if($bd.length>0){
            $bd.find("li").eq($index).show().siblings().hide();
        }
    })


    //发展历程切换
    $(".development_box").slide();
})

//地址
function map(){
    /*地址*/
    var map;
    var point;
    var opts = {};
    var infoWindow;
    map = new BMap.Map("hefei");
    point = new BMap.Point(117.261237,31.835522);
    opts = {
        width: 220, // 信息窗口宽度
        height: 50, // 信息窗口高度
        title: "<span class='map_tit'>英烁网络</span>", // 信息窗口标题
        enableMessage: true, //设置允许信息窗发送短息
        offset: new BMap.Size(0, -30)
    }
    infoWindow = new BMap.InfoWindow("<span class='map_msg'>地址：安徽合肥蜀山区望江西路西湖国际广场A座13层</span>", opts);
    // 添加带有定位的导航控件
    var top_left_control = new BMap.ScaleControl({
        anchor: BMAP_ANCHOR_BOTTOM_LEFT
    }); // 左上角，添加比例尺
    var top_left_navigation = new BMap.NavigationControl(); //左上角，添加默认缩放平移控件
    map.addControl(top_left_control);
    map.addControl(top_left_navigation);
    map.centerAndZoom(point, 19);
    var marker = new BMap.Marker(point); // 创建标注
    map.addOverlay(marker); // 将标注添加到地图中
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    map.openInfoWindow(infoWindow, point); //开启信息窗口
    marker.addEventListener("click", function() {
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    });
}

(function($){
   $.fn.slide=function(){
       return this.each(function(){
           var $listcon=$(this).find("ul").parent(".development_list");
           var $ul=$(this).find("ul");
           var $li=$ul.find("li");
           var $liWidth=$li.outerWidth(true);
           var $liNum=$li.length;
           var $ulWidth=parseInt($liWidth)*parseInt($liNum);
           var $listWidth=$listcon.width();
           $(this).find(".rightbtn").click(function(){
               $ul.stop(true,true);
               var $left=$ul.css("margin-left");
               if(parseInt($ulWidth)+parseInt($left)>parseInt($listWidth)){

                   $ul.animate({marginLeft:"-="+$liWidth+"px"},300);
               }
           });
           $(".development_box .leftbtn").click(function(){
               $ul.stop(true,true);
               var $left=$ul.css("margin-left");
               if(parseInt($left)<0){
                   $ul.animate({marginLeft:"+="+$liWidth+"px"},300);
               }
           });
       })
   }
})(jQuery);