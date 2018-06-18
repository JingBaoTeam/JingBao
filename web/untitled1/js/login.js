/**
 * Created by Administrator on 2018/6/4.
 */
    //http://117.78.47.168:8080/mall
var xsite="http://www.ireson.cn/amall/";

var IsAccount=false;
var IsPassword=false;

//验证用户名
$(function(){

//$("input#username").ready(function(){
    $("#username").blur(function(){
        IsAccount=checkAccount();
    })

//})
//验证密码
//$("input#password").ready(function(){
    $("#password").blur(function(){
        IsPassword=checkPassword();
    })
//})


//$("input#loginbt").ready(function(){
    $(".login_btn").click(function(){

        if(!IsAccount){
            return checkAccount();
        }
        if(!IsPassword){
            return checkPassword();
        }

        var Data={
        account:$("#username").val(),
        password:$("#password").val()
    }

        $.post(xsite+"user/do_login.do",Data,function(rs){
            console.log(rs);
            if(rs.status==0){

                $(window).attr("location","index.html");
            }
            else{
                alert(rs.msg);
            }
        })
    })
//})

////记住用户名
//    window.onload = function(){
//        var oForm = document.getElementById('loginForm');
//        var oUser = document.getElementById('username');
//        //var oPswd = document.getElementById('pswd');
//        var oRemember = document.getElementById('isRememberUsername');
//        //页面初始化时，如果帐号密码cookie存在则填充
//        if(getCookie('username')/* && getCookie('pswd')*/){
//            oUser.value = getCookie('username');
////            oPswd.value = getCookie('pswd');
//            oRemember.checked = true;
//        }
//        //复选框勾选状态发生改变时，如果未勾选则清除cookie
//        oRemember.onchange = function (){
//            if(!this.checked){
//                delCookie('username');
////                delCookie('pswd');
//            }
//        }
//        //表单提交事件触发时，如果复选框是勾选状态则保存cookie
//        oForm.onsubmit = function(){
//            if(remember.checked){
//                setCookie('username',oUser.value,7); //保存帐号到cookie，有效期7天
////                setCookie('pswd',oPswd.value,7); //保存密码到cookie，有效期7天
//            }
//        };
//    };

})
////设置cookie
//function setCookie(name,value,day){
//    var date = new Date();
//    date.setDate(date.getDate() + day);
//    document.cookie = name + '=' + value + ';expires='+ date;
//};
////获取cookie
//function getCookie(name){
//    var reg = RegExp(name+'=([^;]+)');
//    var arr = document.cookie.match(reg);
//    if(arr){
//        return arr[1];
//    }else{
//        return '';
//    }
//};
////删除cookie
//function delCookie(name){
//    setCookie(name,null,-1);
//};
//验证用户名函数
function checkAccount(){
    if($("#username").val()==0){
        $("#Errorname").css("display","inline");
        return false;
    }
    else{
        $("#Errorname").css("display","none");
        return true;
    }
}
//验证密码函数
function checkPassword(){
    if(  $("#password").val()==0){
        $("#Errorpasswor").css("display","inline");
        return false;
    }
    else{
        $("#Errorpasswor").css("display","none");
        return true;
    }
}