/**
 * Created by Administrator on 2018/6/18.
 */
$(function(){
    //1.加载产品分类
    $.ajax({
        url:baseUrl+"param/findallparams.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs){
            console.log(rs);
            var tpl=$("#param_tpl").html();
            var func = Handlebars.compile(tpl);
            var data = rs.data;
            var result = func(data);
            $("#paramContainer").html(result);
        }
    });

    //2.加载热销商品
    $.ajax({
        url:baseUrl+"product/findhotproducts.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs){
            console.log(rs);
            var tpl=$("#hot_tpl").html();
            var func = Handlebars.compile(tpl);
            var data = new Array();
            for(var i=0;i<rs.data.length;i++){
                rs.data[i].iconUrl=baseUrl+rs.data[i].iconUrl;
                data[i]=rs.data[i];
                if(i>=4){
                    //前台只展示5条
                    break;
                }
            }
            var result = func(data);
            $("#hotContainer").html(result);
            //为最后一个li添加样式
            $("#hotContainer>li:last-child").addClass("right_border");
            console.log($("#hotContainer>li:last-child"));
        }
    });

    //3.加载楼层商品
    $.ajax({
        url:baseUrl+"product/findfloors.do",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        success:function(rs){
            console.log(rs);
            if(rs.status==1){
                return;
            }
            //1楼数据
            var data1 = rs.data.oneFloor;
            for(var i=0;i<data1.list.length;i++){
                data1.list[i].iconUrl=baseUrl+data1.list[i].iconUrl;
            }
            data1.mt.iconUrl = baseUrl+data1.mt.iconUrl;
            data1.mb.iconUrl = baseUrl+data1.mb.iconUrl;
            var func11 = Handlebars.compile($("#floor_odd_top").html());
            var func12 = Handlebars.compile($("#floor_odd_bottom").html());
            var func13 = Handlebars.compile($("#floor_odd_right").html());
            $("#floor_one_m").html();
            $("#floor_one_m").append(func11(data1.mt));
            $("#floor_one_m").append(func12(data1.mb));
            $("#floor_one_r").html();
            $("#floor_one_r").append(func13(data1.list));
            //3楼数据
            var data3 = rs.data.threeFloor;
            for(var i=0;i<data3.list.length;i++){
                data3.list[i].iconUrl=baseUrl+data3.list[i].iconUrl;
            }
            data3.mt.iconUrl = baseUrl+data3.mt.iconUrl;
            data3.mb.iconUrl = baseUrl+data3.mb.iconUrl;
            $("#floor_three_m").html();
            $("#floor_three_m").append(func11(data3.mt));
            $("#floor_three_m").append(func12(data3.mb));
            $("#floor_three_r").html();
            $("#floor_three_r").append(func13(data3.list));
            //2楼数据
            var data2 = rs.data.twoFloor;
            for(var i=0;i<data2.mb.length;i++){
                data2.mb[i].iconUrl=baseUrl+data2.mb[i].iconUrl;
            }
            data2.mt.iconUrl = baseUrl+data2.mt.iconUrl;
            for(var i=0;i<data2.rb.length;i++){
                data2.rb[i].iconUrl=baseUrl+data2.rb[i].iconUrl;
            }
            for(var i=0;i<data2.rt.length;i++){
                data2.rt[i].iconUrl=baseUrl+data2.rt[i].iconUrl;
            }
            var func21 = Handlebars.compile($("#floor_even_top").html());
            var func22 = Handlebars.compile($("#floor_even_m_r_b").html());
            var func23 = Handlebars.compile($("#floor_even_right").html());

            $("#floor_two_m").html();
            $("#floor_two_m").append(func21(data2.mt));
            $("#floor_two_m").append(func22(data2.mb));
            $("#floor_two_r").html();
            $("#floor_two_r").append(func23(data2.rt));
            $("#floor_two_r").append(func22(data2.rb));

            //4楼数据
            var data4 = rs.data.fourFloor;

            for(var i=0;i<data4.mb.length;i++){
                data4.mb[i].iconUrl=baseUrl+data4.mb[i].iconUrl;
            }
            data4.mt.iconUrl = baseUrl+data4.mt.iconUrl;
            for(var i=0;i<data4.rb.length;i++){
                data4.rb[i].iconUrl=baseUrl+data4.rb[i].iconUrl;
            }
            for(var i=0;i<data4.rt.length;i++){
                data4.rt[i].iconUrl=baseUrl+data4.rt[i].iconUrl;
            }
            //var func41 = Handlebars.compile($("#floor_even_top").html());
            //var func42 = Handlebars.compile($("#floor_even_m_r_b").html());
            //var func43 = Handlebars.compile($("#floor_even_right").html());
            //var func24 = Handlebars.compile($("#floor_even_m_r_b").html());

            $("#floor_four_m").html();
            $("#floor_four_m").append(func21(data4.mt));
            $("#floor_four_m").append(func22(data4.mb));
            $("#floor_four_r").html();
            $("#floor_four_r").append(func23(data4.rt));
            $("#floor_four_r").append(func22(data4.rb));
        }
    });
})


$(function () {
    //4.首页轮播
    $(".slideBox").slide({mainCell:".bd ul",effect:"leftLoop",autoPlay:true});

    //5.产品分类
    $('.iten').live({
        mouseenter:function(){
            var children_div = $(this).children('div');
            var t_this = $(this);
            if (t_this.find(".subCategory .subView").text() == "") {
                // t_this.find(".subCategory .subView").html("<img src=\"images/lodding.gif\" alt=\"加载中...\"/>");
            }
            //有子分类时才显示
            t_this.find('.item_hd').css({
                border:'none'
            })
            t_this.find('.item_hd').prev().css({
                border:'none'
            })
            t_this.prev().find('.item_hd').css({
                border:'none'
            });
            children_div.show();
            $(this).addClass('selected');
        },
        mouseleave:function(){
            var children_div = $(this).children('div');

            $(this).find('.item_hd').css({
                'border-bottom':'1px dotted white'
            })
            $(this).prev().find('.item_hd').css({
                'border-bottom':'1px dotted white'
            })

            children_div.hide();

            $(this).removeClass('selected');
        }
    });
});
