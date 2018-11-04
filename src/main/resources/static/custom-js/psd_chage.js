$(document).ready(function () {


    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    function phone(Phone) {
        var pattern = /^1[34578]\d{9}$/;
        var b = pattern.test(Phone);
        return b;

    }

    function format_pwd(pwd) {
        var regExp = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
        var b = regExp.test(pwd);
        return b;
    }

    var curCount = 0;//当前剩余秒数

    //手机号格式判断
    $("#newphone").on('input', function (e) {

        if (phone($("#newphone").val())) {
            // console.log("123");
            $("#format_phone").hide();
            if (curCount == 0) {
                $("#getCode").attr('disabled', false);
                $("#getCode").css("background-color", "transparent");
            }


            if (format_pwd($("#newpwd").val())) {
                $("#btn_chage").attr('disabled', false);
                $("#change").css("background-color", "#1aad19");
            }


        } else {
            $("#format_phone").show();
            $("#getCode").attr('disabled', true);
            $("#getCode").css("background-color", "#ececec");

            $("#btn_chage").attr('disabled', true);
            $("#change").css("background-color", "#ececec");
        }
    });
    //校验密码是否6位，不能全是数字或者字母
    $("#newpwd").on('input', function (e) {

        if (format_pwd($("#newpwd").val())) {
            $("#format_pwd").hide();

            if (phone($("#newphone").val())) {
                $("#btn_chage").attr('disabled', false);
                $("#change").css("background-color", "#1aad19");
            }

        } else {
            $("#regExpPwd").text("新密码格式不对");
            $("#format_pwd").show();

            $("#btn_chage").attr('disabled', true);
            $("#change").css("background-color", "#ececec");
        }

    });


    //清除code

    var InterValObj; //timer变量，控制时间
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器

            if (phone($("#newphone").val())) {

                $("#getCode").removeAttr("disabled");//启用按钮
                $("#getCode").text("重新发送验证码");
                $("#getCode").css("background-color", "transparent");
            }
            /**
             * 清除验证码
             */
            var temp = {};
            temp.phone = $("#userPhone").val();
            // alert(temp.phone);
            $.ajax({
                async: false,
                type: "POST",
                url: projectName + "/deleteCode",//注意路径
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
                            title: "<span style='color:#ef3737;font-size: 22px'>系统异常！<span>",
                            text: "2秒后自动关闭。",
                            timer: 2000,
                            showConfirmButton: true,
                            html: true
                        });
                    }

                },
                error: function (data) {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 22px'>系统异常！<span>",
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

    //保存cookies 不要多次拿密码
    function setCookies(name) {

        var expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (1 * 60 * 1000));
//?替换成分钟数如果为60分钟则为 60 * 60 *1000
        var m = expiresDate.getMinutes();     //获取当前分钟数(0-59)
        var s = expiresDate.getSeconds();
        $.cookie(name, m * 60 + s, {
            path: '/',//cookie的作用域
            expires: expiresDate
        });

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
                title: "<span style='color:#f6d224;font-size: 22px'>手机号不能为空！<span>",
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
        $("#getCode").css("background-color", "#ececec");
        var temp = true;
        if ($.cookie(params.phone) != null) {
            swal({
                title: "<span style='color:#f6d224;font-size: 22px'>验证码获取频繁！<span>",
                text: "2秒后自动关闭。",
                timer: 1500,
                showConfirmButton: true,
                html: true
            });
            temp = false
        } else {
            setCookies(params.phone);
        }

        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        if (temp == false) {
            return;
        }

        $.ajax({
            async: false,
            type: "POST",
            url: projectName + "/ResetPassword",//注意路径
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
                if (itm == null){
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
        if (params.newpwd == '') {
            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>新密码不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            return;
        }
        if (params.newpwd == "" || params.code == "" || params.newphone == "") {

            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>验证码不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            return;
        }

        console.log(JSON.stringify(params, null, 4));

        $.ajax({
            async: false,
            type: "POST",
            url: projectName + "/ChangePassword",//注意路径
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
                        window.setTimeout(function () {
                            count--;
                            if (count > 0) {
                                $('#num').attr('innerHTML', count);
                                jump(count);
                            } else {
                                window.location.href = "login.html";
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