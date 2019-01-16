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
})
function toPay(){
    alert("hello");
    $(this).next().attr("type","text");

}