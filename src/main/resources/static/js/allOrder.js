/**
 HWG
 */
$(function () {
    $(".toPayBtn").click(function () {
        var oid=$(this).next().val();
        $.ajax({
            url:"/mall/toPay",
            type:"post",
            data:"oid="+oid,
            success:function (data) {
                if(data==1){
                    window.location.href="/mall/wechat/pay";
                }else{
                    $.toast("请重试","text");
                }
            }
        });
    });
    $(".delOrderBtn").click(function () {
        var oid=$(this).next().val();
        $.ajax({
            url:"/mall/delOrder",
            type:"post",
            data:"oid="+oid,
            success:function (data) {
                if(data==1){
                    $.toast("删除成功",1000,function () {
                       window.location.reload();
                    });
                }else{
                    $.toast("请重试","text");
                }
            }
        });
    });
})
function toPay(){
    window.location.href="/mall/wechat/pay";

}