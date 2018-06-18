$(function(){
	//1.加载当前用户的地址信息
	getUserAddrInfo();
	//2.弹出地址新增对话框
	$(".add_address").click(function(){
		$(".address_popup").css("display","block");
		$(".black_bg").css("display","block");
	});
	//3.关闭地址新增对话框
	$(".btn_close").click(function(){
		$(".address_popup").css("display","none");
		$(".black_bg").css("display","none");
	});
	//4.设置默认地址
	$(".moren-tip").live("click",function(){
		//$(".moren-tip").html("");
		//$(this).html("默认地址");
		$(".address_item").removeClass("selected");
		$(this).parents('.address_item').addClass("selected");
		//取得选中地址的id属性值，并设置到提交按钮上
		var id = $(this).attr("data-id");
		$("#submit").attr("data-id",id);

 	});
     $(".item").click(function(){
		$(".item").removeClass("active");
		$(this).addClass("active");
	});
	//5.删除地址
	$("#del").live("click",function(){
		var id = $(this).attr("data-id");
		$.ajax({
			url:baseUrl+"addr/deladdr.do",
			xhrFields: {withCredentials: true},
	       	crossDomain: true,
	       	data:{"id":id},
	       	success:function(rs){
	       		if(rs.status==0){
	       			//删除成功，重新获取地址数据
	       			getUserAddrInfo();
	       		}else{
	       			alert(rs.msg);
	       		}
	       	}
		});
	});
	//6.设置为默认地址
	$("#default").live("click",function(){
		 var id=$(this).attr("data-id");
		 $.ajax({
		 	url:baseUrl+"addr/setdefault.do",
			xhrFields: {withCredentials: true},
	       	crossDomain: true,
	       	data:{"id":id},
	       	type:"post",
	       	success:function(rs){
	       		if(rs.status==0){
	       			getUserAddrInfo();
	       		}else{
	       			alert(rs.msg);
	       		}
	       	}
		 });
	});

	//7.新增地址
	$(".address_submit").click(function(){
		var name = $("#contact").val();
		var province = $("#province").val();
		var city = $("#city").val();
		var addr = $("#addr").val();
		var postcode = $("#postcode").val();
		var mobile=$("#tel").val();
		//做非空校验后提交后台
		$.ajax({
			url:baseUrl+"addr/saveaddr.do",
			xhrFields: {withCredentials: true},
	       	crossDomain: true,
	       	data:{"name":name,"province":province,"city":city,"addr":addr,"zip":postcode,"mobile":mobile},
	       	type:"post",
	       	success:function(rs){
	       		if(rs.status==0){
	       			getUserAddrInfo();
	       			$(".address_popup").css("display","none");
					$(".black_bg").css("display","none");
	       		}else{
	       			alert(rs.msg);
	       		}
	       	}
		});
	});
});

//读取用户地址信息
function getUserAddrInfo(){
	$.ajax({
		url:baseUrl+"addr/findaddrs.do",
		xhrFields: {withCredentials: true},
       	crossDomain: true,
       	success:function(rs){
       		if(rs.status==0){
       			$("#addr-item-container").html();
	   			var tpl=$("#addr-item-tpl").html();
				var func = Handlebars.compile(tpl);
		        var result = func(rs.data);
		        $("#addr-item-container").html(result);
		        //设置默认收货人地址
		        $(".moren-tip").each(function(i,obj){
		        	 var df = $(obj).attr("data-default");
		        	 if(df=='true'){
		        	 	var addrId= $(obj).attr("data-id");
		        	 	$("#submit").attr("data-id",addrId);
		        	 }
		        	// console.log(obj);
		        });
       		}
       	}
	});
}