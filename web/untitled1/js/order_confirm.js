$(function(){
	//1.读取当前用户要生产的订单的购物车商品信息
	getCartInfo();
	//2.提交生确认信息生成订单
	$("#submit").click(function(){
		//收货人地址ID
		var id = $(this).attr("data-id");
		//提交订单
		$.ajax({
			url:baseUrl+"order/createorder.do",
			xhrFields: {withCredentials: true},
	       	crossDomain: true,
	       	data:{"addrId":id},
	       	type:"post",
	       	success:function(rs){
	       		if(rs.status==0){
	       			//跳转到订单详情页面
	       			//console.log(rs.data);
	       			$(window).attr("location","order_detail.html?orderNo="+rs.data.orderNo);
	       		}else{
	       			alert(rs.msg);
	       		}
	       	}
		});
	});
});


//读取购物车信息
function getCartInfo(){
	$.ajax({
		url:baseUrl+"cart/findallcarts.do",
		xhrFields: {withCredentials: true},
       	crossDomain: true,
       	success:function(rs){
       		console.log(rs);
       		//数据返回成功
       		updatePageInfo(rs);
       	}
	});
}

//根据返回数据更新页面信息
function updatePageInfo(rs){
	if(rs.status==0){
		if(rs.data.lists.length==0){
			
		}else{
			//更新购物车列表信息
			$("#order_confirm_item_container").html();
   			var tpl=$("#product-item-tpl").html();
			var func = Handlebars.compile(tpl);
	        var result = func(rs.data.lists);
	        $("#order_confirm_item_container").html(result);
	        //更新购物车总价格
	        $("#amount").html("￥"+rs.data.totalPrice);
	    }
    }else{
    	//未登录直接跳入登录界面
    	$(window).attr("location","login.html")
    }
}
