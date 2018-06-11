/**
 * Created by Administrator on 2018/6/8.
 */
var xsite="http://www.ireson.cn/amall/";
$(function(){
//     获取购物车信息
    GetCartInfo();


//清空购物车
    $("#clear").click(function(){
        $.get(xsite+"/cart/clearcarts.do",function(data){
            if(data.status==0){
                //console.log(data.status);
                $("#cart-empty").css("display","block");
                $("#cart-container").css("display","none");
            }
            else{
                alert(data.msg);
                $(window).attr("location","login.html");

            }
        })
    })
    //减少数量
    $("#minus-btn").live("click",function(){
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
    $("#plus-btn").live("click",function(){
        var quantity=$(this).prev().val();
        quantity=parseInt(quantity)+1;
        $(".l").val(quantity);
        var productId=$(this).attr("data-product-id");
        UpdataQuantity(productId,quantity);
    })
//删除商品
    $(".delete").live("click",function(){
        var productId = $(this).attr("data-product-id");
        $.get(xsite+"/cart/delcarts.do",productId,function(rs){
            UpdataPageInfo(rs);
        })
    })
    $("#submit").click(function(){
        $(window).attr("location","order_confirm.html");
    })




})
//获取信息
function  GetCartInfo(){
    $.get(xsite+"/cart/findallcarts.do",function(data){
        alert(data.msg);
    })
}
//更新商品数量
function UpdataQuantity(productId,quantity){
    Form={
        productId:productId,
        count:quantity
    };

    $.get(xsite+"/cart/updatecarts.do",Form,function(data){
        UpdataPageInfo(data);
    })
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