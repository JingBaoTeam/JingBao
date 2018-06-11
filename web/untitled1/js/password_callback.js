/**
 * Created by Administrator on 2018/6/10.
 */
var xsite="http://www.ireson.cn/amall";

var Isname=false;

var Isanswer=false;

var Ispassword=false;

$(function(){

    //检验用户名
    $("#username").blur(function(){
        //
        Isname=checkname();
    })
    //})
    //检验答案
    $("#answer").blur(function(){

        Isanswer=checkanswer();
    })
//检验密码
    $("#password").blur(function(){
        Ispassword=checkpassword();
    })

//获取问题
    
    $("#username").blur(function(){
        if(!Isname){ return checkname();}
        console.log($("#username").val());
        console.log(typeof($("#username").val()));
        var User= {
            account:$("#username").val()
        }
        $.post(xsite+"/user/getuserquestion.do", User,function(rs){
            console.log(rs);
            $("#question").html(rs.msg);
        })
    })



    //新密码
    $("#answer").blur(function(){
        if(!Isanswer){return}

        AnswerForm={
            account:$("#username").val(),
            question:$("#question").val(),
            asw:$("#password").val()
        }

        $.post(xsite+"/user/checkuserasw.do" ,AnswerForm,function(rs){
            if(rs.status==0){

                $("#newps").css("display","inherit");
            }
            else{
                console.log(rs.status);
                $("#Erroranswer").html(rs.msg);
                $("#Erroranswer").css("display","inline");
            }
        })
    })


    NewKeyForm={
        account:$("#username").val(),
        newpwd:$("#newps").val(),
        token:"1111"
    }

//    修改密码
$("#submit").click(function(){
    $.post(xsite+"/user/resetpassword.do",NewKeyForm,function(rs){
        if(rs.status==0){
            $(window).attr("location","login.html");
        }
    })
})


})
//检验用户名函数
function checkname(){
    if( $("#username").val()==0){
        $("#Errorusername").css("display","inline");
        return false;
    }
    else{
        $("#Errorusername").css("display","none");
        return true;
    }
}
//检验答案函数
function checkanswer() {
    if($("#answer").val()==0){
        //console.log($("#answer").val()==0);
        $("#Erroranswer").html("请输入答案");
        $("#Erroranswer").css("display","inline");
        return false;}
    else{
        $("#Erroranswer").css("display","none");
        return true;}
}
//检查新密码函数
function checkpassword(){
    if($("#password").val()==0){
        console.log($("#password").val()==0);
        $("#Errorkeyword").css("display","inline");
        return false;
    }
    else{
        $("#Errorkeyword").css("display","none");
        return true;
    }
}