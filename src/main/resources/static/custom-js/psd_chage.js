$(document).ready(function () {




    //手机号格式判断
    $("#newphone").on('input', function (e) {
        var pattern = /^1[34578]\d{9}$/;
        var b = pattern.test($("#newphone").val());
        if (b == false) {
            $("#format_phone").show();
            $("#getCode").attr('disabled', true);
            $("#getCode").css("background-color", "#ececec");

            $("#btn_chage").attr('disabled', true);
            $("#change").css("background-color", "#ececec");
        }
        if (b == true) {
            // console.log("123");
            $("#format_phone").hide();
            $("#getCode").attr('disabled', false);
            $("#getCode").css("background-color", "transparent");

            $("#btn_chage").attr('disabled', false);
            $("#change").css("background-color", "#1aad19");

        }
    });
    //校验密码是否6位，不能全是数字或者字母
    $("#newpwd").on('input', function (e) {
        var regExp = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
        var b = regExp.test($("#newpwd").val());
        if (b == false) {
            $("#regExpPwd").text("新密码格式不对");
            $("#format_pwd").show();

            $("#btn_chage").attr('disabled', true);
            $("#change").css("background-color", "#ececec");
        }
        if (b == true) {
            $("#format_pwd").hide();
            $("#btn_chage").attr('disabled', false);
            $("#change").css("background-color", "#1aad19");
        }

    });
    //清除code
    var curCount;//当前剩余秒数
    var InterValObj; //timer变量，控制时间
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#getCode").removeAttr("disabled");//启用按钮
            $("#getCode").text("重新发送验证码");
            /**
             * 清除验证码
             */
            var temp = {};
            temp.phone = $("#userPhone").val();
            // alert(temp.phone);
            $.ajax({
                async: false,
                type: "POST",
                url: "MallUser/deleteCode",//注意路径
                data: temp,
                dataType: "json",
                success: function (data) {
                    var itemb = data.data;
                    //1代表成功
                    if (itemb == "1") {

                    }
                    //2代表有异常
                    if (itemb == "2") {
                        swal({
                            title: "<span style='color:#ef3737;font-size: 26px'>系统异常！<span>",
                            text: "2秒后自动关闭。",
                            timer: 2000,
                            showConfirmButton: true,
                            html: true
                        });
                    }

                },
                error: function (data) {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>系统异常！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: true,
                        html: true
                    });

                }
            });

        }
        else {
            curCount--;
            $("#getCode").text(curCount + "秒再获取");
        }

    }
    //获取验证码
    $("#getCode").click(function (e) {
        // alert("123");

        var count = 60; //间隔函数，1秒执行
        curCount = count;
        var params = {};
        params.phone = $("#newphone").val();
        if (params.phone == "") {

            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>手机号不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            // clearInterval(int);
            return;
        }

        //设置获取验证码获取时间
        $("#getCode").attr("disabled", "true");
        $("#getCode").text(curCount + "秒再获取");
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次


        $.ajax({
            async: false,
            type: "POST",
            url: "MallUser/ResetPassword",//注意路径
            data: params,
            dataType: "json",
            success: function (data) {
                //console.log(JSON.stringify(data,null,4));
                var itm = data.data;
                if (itm == "1") {

                    swal({
                        title: "<span style='color:#6ddb8d;font-size: 26px'>验证码已发送！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });

                }
                if (itm == "2") {
                    swal({
                        title: "<span style='color:#f6d224;font-size: 26px'>手机号未被注册，请先注册！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (itm == "3") {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>验证码获取失败！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (itm == "4") {
                    swal({
                        title: "<span style='color:#f6d224;font-size: 26px'>验证码获取频繁！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
            },
            error: function (data) {
                //console.log(JSON.stringify(data,null,4));
                swal({
                    title: "<span style='color:#ef3737;font-size: 26px'>验证码获取失败！<span>",
                    text: "2秒后自动关闭。",
                    timer: 2000,
                    showConfirmButton: false,
                    html: true
                });
                //clearInterval(int);
            }
        });


    });

    //修改密码
    $("#btn_chage").click(function () {
        var params = {};
        params.newpwd = $("#newpwd").val();
        params.code = $("#code").val();
        params.newphone = $("#newphone").val();
        // alert(JSON.stringify(params));
        if (params.newpwd ==''){
            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>新密码不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            return;
        }
        if (params.newpwd == "" || params.code == "" || params.newphone == "" ) {

            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>验证码不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            return;
        }

        console.log(JSON.stringify(params,null,4));

        $.ajax({
            async: false,
            type: "POST",
            url: "MallUser/ChangePassword",//注意路径
            data: params,
            dataType: "json",
            success: function (data) {
                var item = data.data;
                if (item == "1") {

                    swal({
                        title: "<span style='color:#6ddb8d;font-size: 26px'>修改成功！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: true,
                        html: true
                    });
                    function jump(count) {
                        window.setTimeout(function(){
                            count--;
                            if(count > 0) {
                                $('#num').attr('innerHTML', count);
                                jump(count);
                            } else {
                                window.location.href="login.html";
                            }
                        }, 1000);
                    }
                    jump(2);
                    return;

                }
                if (item == "2") {
                    swal({
                        title: "<span style='color:#f6d224;font-size: 26px'>手机未被注册！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (item == "3") {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>验证码错误！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (item == "4") {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>未知错误！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (item == "5") {
                    swal({
                        title: "<span style='color:#f6d224;font-size: 26px'>验证码过期！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
                if (item == "6") {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>手机号有误！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
            },
            error: function (data) {
                swal({
                    title: "<span style='color:#ef3737;font-size: 26px'>修改失败！<span>",
                    text: "2秒后自动关闭。",
                    timer: 2000,
                    showConfirmButton: true,
                    html: true
                });
            }
        });

    });

});