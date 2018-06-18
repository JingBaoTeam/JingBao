/**
 * Created by Administrator on 2018/6/8.
 */
    //http://117.78.47.168:8080/mall/
//var xsite=baseUrl;
$(function(){
//     获取购物车信息
   GetCartInfo();


//清空购物车
    $("#clear").click(function(){
        $.ajax({
            url:baseUrl+"cart/clearcarts.do",
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(rs){
                    if(rs.status==0){
                        //console.log(data.status);
                        $("#cart-empty").css("display","block");
                        $("#cart-container").css("display","none");
                    }
                    else{
                        alert(rs.msg);
                        $(window).attr("location","login.html");
                    }
            }
        })
        //$.get(xsite+"/",function(rs){
        //    if(rs.status==0){
        //        //console.log(data.status);
        //        $("#cart-empty").css("display","block");
        //        $("#cart-container").css("display","none");
        //    }
        //    else{
        //        alert(rs.msg);
        //        $(window).attr("location","login.html");
        //    }
        //})
    })
    //减少数量
    $(".minus-btn").live("click",function(){
        var quantity= $(this).next().val();
        quantity=parseInt(quantity)-1;
        if(quantity<=0){
            alert("数量不可小于1！");
            return;
        }
        $(".l").val(quantity);
       var productId=  $(this).attr("data-product-id");
       UpdataQuantity(productId,quantity);
    })
    //增加数量
    $(".plus-btn").live("click",function(){
        var quantity=$(this).prev().val();
        quantity=parseInt(quantity)+1;
        $(".l").val(quantity);
        var productId=$(this).attr("data-product-id");
        UpdataQuantity(productId,quantity);
    })
//删除商品
    $(".delete").live("click",function(){
        var productId = $(this).attr("data-product-id");
        console.log(productId);
        $.ajax({
            url:baseUrl+"cart/delcarts.do",
            data:{productId:productId},
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(rs){
                UpdataPageInfo(rs);
            }
        })
        //$.get(xsite+"/",,function(rs){
        //    UpdataPageInfo(rs);
        //})
    })
    $("#submit").click(function(){
        $(window).attr("location","order_confirm.html");
    })

    $(".item").click(function(){
        $(".item").removeClass("active");
        $(this).addClass("active");
    });


})
//获取信息
function  GetCartInfo(){
    $.ajax({
        url:baseUrl+"cart/findallcarts.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs){
            console.log(rs);
           // alert(rs.msg);
            UpdataPageInfo(rs);
        }
    })
    //$.get(xsite+"/",function(rs){
    //    alert(rs.msg);
    //    UpdataPageInfo(rs);
    //})
}
//更新商品数量
function UpdataQuantity(productId,quantity){
    $.ajax({
        url:baseUrl+"cart/updatecarts.do",
        data:{'count':quantity,'productId':productId},
        xhrFields:{withCredentials:true},
        crossDomain:true,
        success:function(rs){
            UpdataPageInfo(rs);
        }
    })
    //$.get(xsite+"/",Form,function(data){
    //    UpdataPageInfo(data);
    //})
}
//更新网页商品
function UpdataPageInfo(rs) {
    if (rs.status == 0) {
        if (rs.data.lists.length == 0) {
            $("#cart-empty").css("display", "block");
            $("#cart-container").css("display", "none");
        } else {
            $("#cart-empty").css("display", "none");
            $("#cart-container").css("display", "block");
            //更新购物车列表信息
            $("#cart-item-container").html();
            var tpl = $("#cart-item-tpl").html();
            var func = Handlebars.compile(tpl);
            var result = func(rs.data.lists);
            $("#cart-item-container").html(result);
            //更新购物车总价格
            $("#amount").html("￥" + rs.data.totalPrice);
        }
    } else {
        //未登录直接跳入登录界面
        $(window).attr("location", "login.html")
        //console.log(rs.msg);
    }
}