$(function($) {
    //读取传递过来的商品编号
    var pid = getParam("pid");
    //1.获取商品详情
    $.ajax({
        url: baseUrl + "product/getdetail.do",
        data: {
            productId: pid
        },
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function(result) {
            if (result.status == 0) {
                //数据加载成功
                //产品名称
                $("#productNameContainer").html(result.data.name);
                //将产品Id作为属性添加到productNameContainer中
                $("#productNameContainer").attr("data-id", result.data.id)
                //产品价格
                $("#productPriceContainer").html(result.data.price);
                //主图
                $("#productMainImage").attr("src", baseUrl+result.data.iconUrl);
                $("#productMainImage").addClass("productPictureImg");
                //详情
                $("#detailContainer").html(result.data.detail);
                //规格参数
                $("#specParamContainer").html(result.data.specParam);
                //产品库存
                $("#product_num").attr("data-stock",result.data.stock);
                $("#stock_container").html("库存："+result.data.stock);
                //商品小图，首先将将数据转为json数组
                var subimages = result.data.subImages;
                subimages = subimages.substring(0, subimages.length);
                var images = subimages.split(",");
                var samll_item = "";
                for(var i=0;i<images.length;i++){
                    samll_item+="<li> <img src=\'";
                    images[i] = baseUrl+images[i];
                    samll_item+=images[i];
                    samll_item+="\' ></li>";
                }

                $("#piclistContainer").html();
                $("#piclistContainer").append(samll_item);
            } else {
                //数据加载失败，跳转到错误页面
            }
        }
    });
    //2.购买数量减少
    $(".product_minus_2").click(function() {
        var stock = $("#product_num").attr("data-stock");
        var num = $("#product_num").val();
        num = parseInt(num) - 1;
        if (num <= 0) {
            num = 0
        }
        $("#product_num").val(num);
    });
    //3.购买数量增加
    $(".product_plus_1").click(function() {
        var stock = $("#product_num").attr("data-stock");
        var num = $("#product_num").val();
        num = parseInt(num) + 1;
        if(num>=stock){
            num=stock;
        }
        $("#product_num").val(num);
    });
    //4.加入购物车
    $("#addCart").click(function() {
        console.log(000);
        var pid = $("#productNameContainer").attr("data-id");
        var count = $("#product_num").val();
        if(count<=0){
            alert("请填写购买数量，且不能小于1！");
            return;
        }
        //加入购物车
        $.ajax({
            url: baseUrl + "cart/savecart.do",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            data: {
                'productId': pid,
                'count': count
            },
            type: "post",
            success: function(rs) {
                console.log(rs);
                if (rs.status == 0) {
                    console.log(rs.msg);
                    //弹出提示对话框
                    $(".address_popup").css("display", "block");
                    $(".black_bg").css("display", "block");
                }else{
                    alert(rs.msg);
                }
            }
        });
    });

    //5.商品小图切换大图
    $(".productPictureTableMain ul li img").live("click", function() {
        //去掉其他图片的选中样式
        $(".productPictureTableMain ul li img").removeClass("productPictureSelected");
        $(this).addClass("productPictureSelected");
        var imgSrc = $(this).attr("src");
        $(".productPictureImg").attr("src", imgSrc);
    });
    //6.Tab页切换
    $(".productTabBar li").click(function() {
        $(".productTabBar li").removeClass("productTab-selected");
        $(this).addClass("productTab-selected");
        var index = $(this).attr("data-index");
        $(".productTabContents li").css("display", "none");
        $(".productTabContents").find("li").eq(index).css("display", "block");
    });
    //7.加入购物车成功后，再逛逛按钮的单击事件
    $(".btn_gg").click(function(){
        $(".address_popup").css("display", "none");
        $(".black_bg").css("display", "none");
    });
    //8.加入购物车成功后，去结算按钮的单击事件
    $(".btn_js").click(function(){
        //跳转到购物车界面
        $(window).attr("location","cart.html");
    });
    $(".btn_close").click(function(){
        $(".address_popup").css("display","none");
        $(".black_bg").css("display","none");
    });
});
