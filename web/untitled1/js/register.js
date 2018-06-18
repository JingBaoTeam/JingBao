var xsite="http://www.ireson.cn/amall/";//接口网址

var Isusername=false;
var Ispassword=false;
var Isphone=false;
var Isemail=false;
var Isquestion=false;
var Isanswer=false;

$(document).ready(function(){
    $("input#username").blur(function(){//

        if($("#username").val()==0){       //判断输入框内容，并给出提示

            $("#usernameError").html("请输入用户名");
            $("#usernameError").css("display","inline");
            return;
        }
        $.post(xsite+"user/do_check_info.do", {info: $("#username").val(), type:"account"},// $("#username").name  验证用户名
            function (rs) {
                //console.log(rs);
                $("#usernameError").text(rs.msg);
                $("#usernameError").css("display","inline");
                if(rs.status==0){Isusername=true;}
            });
    })
})
//密码验证
var password;
var repassword;
$(document).ready(function(){
    $("input#password").blur(function(){
        if($("#password").val()==0){       //判断输入框内容，并给出提示
            $("#pserror").html("请输入密码");
            $("#pserror").css("display","inline");
            return;
        } else if($("#password").val().length< 6){
            $("#pserror").html("密码不可少于六位")
            $("#pserror").css("display","inline");
            return;
        }
        password=$("#password").val();
        if($("#password")!=0){$("#pserror").css("display","none");}
    })
    $("input#repassword").blur(function(){
        if($("#repassword").val==0){
            $("#repserror").html("请输入密码")
            $("#repserror").css("display","inline");
            return;
        }
        repassword=$("#repassword").val();
        if(password!=repassword&&repassword!=0){
            $("#repserror").html("两次输入的密码不同");
            $("#repserror").css("display","inline");
        }
        else{ $("#repserror").css("display","none");}
        Ispassword=true;
    })
})
//验证手机
$(document).ready(function(){
    $("input#phone").blur(function(){
        var regBox = {
            regMobile : /^0?1[3|4|5|7|8][0-9]\d{8}$/,//手机
        }
        var moblie=$("#phone").val();
        moblie.replace(/[ ]/g,"");
        var flag=regBox.regMobile.test(moblie);
        if(!flag){
            $("#phoneError").css("display","inline");
            $("#phoneError").html("请输入正确的手机号");
            return;
        }
        else{$("#phoneError").css("display","none");}
        $.post(xsite + "user/do_check_info.do", {info: $("#phone").val(), type:"phone"},// $("#username").name  验证用户名
            function (rs) {
                console.log($("#phone").val());
                //console.log(rs);
                $("#phoneError").html(rs.msg);
                $("#phoneError").css("display","inline");
                if(rs.status==0){Isphone=true;}
            });
    })
})
//验证邮箱
$(document).ready(function(){
    $("input#email").blur(function(){
        var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        var Email=filter.test($("#email").val());
        if(!Email){
            $("#emailerorr").css("display","inline");
            $("#emailerorr").html("请输入正确的邮箱");
            return;
        }
        else{$("#emailerorr").css("display","none"); }
        $.post(xsite+"user/do_check_info.do",{info:$("#email").val(),type:"email"},
            function(rs){
                    $("#emailerorr").css("display","inline");
                    $("#emailerorr").html(rs.msg);
                if(rs.status==0){Isemail=true;}
            })
    })
})
//密码提示
$(document).ready(function(){
    $("input#question").blur(function(){
        if($("#question").val()==0) {
            $("#usernameErrorq").css("display","inline");
        }
        else{ $("#usernameErrorq").css("display","none")}
        Isquestion=true;
    })
    $("input#answer").blur(function(){
        if($("#answer").val()==0){
            $("#usernameErrora").css("display","inline");
        }
        else{ $("#usernameErrora").css("display","none")
        Isanswer=true;
        }
    })
})
//
$(document).ready(function(){
    $("input#protocol").click(function(){
        var check=$("#protocol").prop("checked");
        if(!check) {
            $("#checkerror").css("display","inline");
            $("#checkerror").html("请同意服务协议");
            document.getElementById("bt").disabled=true;
        }
        else{
            $("#checkerror").css("display","none");
            document.getElementById("bt").disabled=false;
        }
    })
})


//提交注册信息
$("#bt").ready(function(){
    $("#bt").click(function(){

        if(!Isusername){
            alert("用户名错误")
            return;
        }
        if(!Ispassword){
            alert("密码错误")
            return;
        }
        if(!Isphone){
            alert("电话错误")
            return;
        }
        if(!Isemail){
            alert("邮箱错误")
            return;
        }
        if(!Isquestion){
            alert("问题错误")
            return;
        }
        if(!Isanswer){
            alert("答案错误")
            return;
        }

        var formData={
            account:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val(),
            phone:$("#phone").val(),
            question:$("#question").val(),
            asw:$("#answer").val(),
        };
        console.log(formData);
            $.post(xsite+"user/do_register.do",formData,function(rs){
                if(rs.status==0){
                    console.log(rs);
                    alert(rs.msg);
                    $(window).attr("location","login.html");
                }
                else{
                    alert(rs.msg);
                }
            })
    })

})
