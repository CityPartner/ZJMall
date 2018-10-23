/*
HWG  详情页面的js文件
 */

$(function(){

    $("#CustomService").click(function(){
        $("#custom_service").addClass("weui-popup__container--visible");
    });

    $("#custom_service").click(function(){
        $("#custom_service").removeClass("weui-popup__container--visible");
    });

    $("#AddToCartBtn1").click(function () {
        // alert(1);
        $("#selcet_cart").addClass("weui-popup__container--visible");
    });
    $("#AddToCartBtn2").click(function () {
        var BuyCount=$("#BuyCount2").val();
        var stock=$("#stock").val();
        if(BuyCount == ""){
            alert("请输入购买数量");
            return ;
        }else if(Number(BuyCount)>Number(stock)){
            alert("最多只能购买"+stock+"箱");
            return ;
        }else if(Number(BuyCount)<1){
            alert("请输入有效数字！");
            return ;
        }
        var C_id=$("#C_id").val();
        // alert(C_id+"  "+BuyCount);
        $("#selcet_cart").removeClass("weui-popup__container--visible");
        $.ajax({
            url:"/mall/shopCart/addToCart",
            data:"BuyCount="+BuyCount+"&C_id="+C_id,
            type:"post",
            success:showAddCartResult,
            error:NoResFromServ,
        });
    });
    $("#NoISeeMore").click(function () {
        $("#join_cart").removeClass("weui-popup__container--visible");
    });
    $("#BuyNowBtn1").click(function () {
        $("#selcet_sku").addClass("weui-popup__container--visible");
    });
    $("#BuyNowBtn2").click(function () {
        // alert("hello");
        var BuyCount=$("#BuyCount3").val();
        var stock=$("#stock").val();
        if(BuyCount == ""){
            alert("请输入购买数量");
            return ;
        }else if(Number(BuyCount)>Number(stock)){
            alert("最多只能购买"+stock+"箱");
            return ;
        }else if(Number(BuyCount)<1){
            alert("请输入有效数字！");
            return ;
        }
        var C_id=$("#C_id").val();
        var dataString=C_id;
        dataString += ":"+BuyCount+",";
        window.location.href="/mall/saveCommodityData?CommodityData="+dataString;
    });
    $("#NoISeeMore2,#CloseByDia").click(function () {
        $("#selcet_sku").removeClass("weui-popup__container--visible");
    });
    $("#CloseCartDia").click(function () {
        $("#selcet_cart").removeClass("weui-popup__container--visible");
    });

    $(".DecreaseNum").click(function () {
        // alert("hello");
        var num=Number($(this).next().val());
        // alert(num);
        // alert("-1=="+num);
        // num -= 1;
        // alert(num);
        if(Number(num)<=1){

        }else{
            $(this).next().prop("value",num-1);
        }
    });
    $(".IncreaseNum").click(function () {
        // alert("hello");
        var num=Number($(this).prev().val());
        // alert(num);
        // alert("+1=="+num);
        // num += 1;
        // alert(num);
        $(this).prev().prop("value",num+1);
    });



});

function showAddCartResult(data) {
    if(data==1){
        $("#join_cart").addClass("weui-popup__container--visible");
    }
}
function NoResFromServ() {
    window.location.href="/mall/login.html";
}