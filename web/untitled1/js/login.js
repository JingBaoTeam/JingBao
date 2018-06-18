/**
 * Created by Administrator on 2018/6/4.
 */
    //http://www.ireson.cn/amall/
//var xsite=baseUrl;

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
        $.ajax({
            url:baseUrl+"user/do_login.do",
            data:Data,
            type:"post",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            success:function(rs) {
                console.log(rs);
                if (rs.status == 0) {
                    $(window).attr("location", "index.html");
                }
                else {
                    alert(rs.msg);
                }
            }
        })
        //$.post(xsite+"",Data,function(rs){
        //    console.log(rs);
        //    if(rs.status==0){
        //
        //        $(window).attr("location","index.html");
        //    }
        //    else{
        //        alert(rs.msg);
        //    }
        //})
    })
//})

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