/**
 * Created by Administrator on 2018/6/17.
 */
//http://localhost:8080/mall/
//  http://www.ireson.cn/amall/
var baseUrl="http://117.78.47.168:8080/mall/";

//获取url中的参数
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

$(document).ready(function(){
    //加载登陆的用户信息
    $.ajax({
        url:baseUrl+"user/getuserinfo.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(user){
            if(user.status==0){
                $("#register-info").css({display:"none"});
                $("#login-info").css({display:"block"});
                $("#headerUsername").html(user.data.account);
                //获取用户购物车商品的数量
                getCartCount();
            }
        }
    });
    //用户登出
    $("#headerLogout").click(function(){
        $.ajax({
            url:baseUrl+"user/do_logout.do",
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(data){
                if(data.status==0){
                    $("#register-info").css({display:"block"});
                    $("#login-info").css({display:"none"});
                    //购物车中商品数量
                    $("#cartQuantity").html("[0]");
                }
            }
        });
    });
})

function getCartCount(){
    $.ajax({
        url:baseUrl+"cart/getcartcount.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs){
            if(rs.status==0){
                $("#cartQuantity").html("["+rs.data+"]");
            }
        }
    });
}