/**
 * Created by Administrator on 2018/6/10.
 */
//var xsite=baseUrl;

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
        var User= {
            account:$("#username").val()
        }
        $.ajax({
            url:baseUrl+"user/getuserquestion.do",
            type:"post",
            data:User,
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(rs){$("#question").html(rs.msg);}
        })
        //$.post(xsite+"/user/getuserquestion.do", User,function(rs){
        //    console.log(rs);
        //    $("#question").html(rs.msg);
        //})
    })


    var token;//口令
    //验证答案
    $("#answer").blur(function(){
        if(!Isanswer){  return checkanswer();  }
        //答案数据
        AnswerForm={
            account:$("#username").val(),
            question:$("#question").html(),
            asw:$("#answer").val()
        }
        console.log(AnswerForm);
        $.ajax({
            url:baseUrl+"/user/checkuserasw.do",
            type:"post",
            data:AnswerForm,
            xhrFields: {withCredentials: true},
            crossDomain: true,
            success:function(rs) {
                if (rs.status == 0) {
                    $("#newps").css("display", "inherit");
                    token=rs.msg;
                }
                else {
                    console.log(rs);
                    $("#Erroranswer").html(rs.msg);
                    $("#Erroranswer").css("display", "inline");
                }
            }
        })
        //$.post(xsite+"" ,AnswerForm,function(rs){
        //    if(rs.status==0){
        //        $("#newps").css("display","inherit");
        //    }
        //    else{
        //        console.log(rs);
        //        $("#Erroranswer").html(rs.msg);
        //        $("#Erroranswer").css("display","inline");
        //    }
        //})
    })


//    修改密码
$("#submit").click(function(){
    //判断密码
    if(!Ispassword){return checkpassword();}
    //密码数据

    $.ajax({
        url:baseUrl+"user/resetpassword.do",
        data:{
            account:$("#username").val(),
            newpwd:$("#newps").val(),
            token:token
        },
        type:"post",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs) {
            if (rs.status == 0) {
                console.log(rs);
                alert(rs.msg);
                $(window).attr("location", "login.html");
            }
        }
    })
    //$.post(xsite+"/",NewKeyForm,function(rs){
    //    if(rs.status==0){
    //        console.log(rs);
    //        alert(rs.msg);
    //        $(window).attr("location","login.html");
    //    }
    //})
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