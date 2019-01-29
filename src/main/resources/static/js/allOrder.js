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
                    $.toast("正在拉起支付页面",1000,function () {
                        window.location.href="http://www.nchhr.com/mall/wechat/pay";
                    });

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
                    $.toast("操作成功",1000,function () {
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