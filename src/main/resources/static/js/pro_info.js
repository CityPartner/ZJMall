/*
HWG  详情页面的js文件
 */

$(function(){
    $("#AddToCartBtn1").click(function () {
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
            url:"/mall/addToCart",
            data:"BuyCount="+BuyCount+"&C_id="+C_id,
            type:"post",
            success:showAddCartResult,
        });
    });
    $("#NoISeeMore").click(function () {
        $("#join_cart").removeClass("weui-popup__container--visible");
    });
    $("#BuyNowBtn1").click(function () {
        $("#selcet_sku").addClass("weui-popup__container--visible");
    });
    $("#NoISeeMore2,#CloseByDia").click(function () {
        $("#selcet_sku").removeClass("weui-popup__container--visible");
    });
    $("#CloseCartDia").click(function () {
        $("#selcet_cart").removeClass("weui-popup__container--visible");
    });
});

function showAddCartResult(data) {
    if(data==1){
        $("#join_cart").addClass("weui-popup__container--visible");
    }
}