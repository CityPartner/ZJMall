$(document).ready(function () {

    // alert("123")

    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //失去焦点的时候，掩藏样式
    // $('#userPhone').blur(function () {
    //     $("#format_phone").hide();
    // });
    // $('#repwd').blur(function () {
    //     $("#checkPwd").hide();
    // });
    //
    var curCount = 0;//当前剩余秒数


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

    function rePwd() {
        var params = {};
        params.pwd = $("#pwd").val();
        params.repwd = $("#repwd").val();
        if (params.pwd == params.repwd) {
            return true;
        }
        else {
            return false;
        }
    }


    //校验手机号格式

    $("#userPhone").on('input', function (e) {
        if (phone($("#userPhone").val())) {
            // console.log("123");
            $("#format_phone").hide();
            if (curCount == 0) {

                $("#getCode").attr('disabled', false);
                $("#getCode").css("background-color", "transparent");
                if (format_pwd($("#pwd").val())) {
                    if (rePwd()) {
                        $("#regist_login").attr('disabled', false);
                        $("#regist_login").css("background-color", "#e64340");
                    }
                }
            }
        } else {
            $("#format_phone").show();
            $("#getCode").attr('disabled', true);
            $("#getCode").css("background-color", "#ececec");
        }
    });


    //判断第二次密码输入的正确性
    $("#repwd").on('input', function (e) {
        var params = {};
        params.pwd = $("#pwd").val();
        params.repwd = $("#repwd").val();
        if (rePwd()) {
            $("#regExpPwd").text("第二次密码输入错误");
            $("#checkPwd").hide();
            if (phone($("#userPhone").val())) {
                $("#regist_login").attr('disabled', false);
                $("#regist_login").css("background-color", "#e64340");
            } else {
                $("#format_phone").show();
                $("#getCode").attr('disabled', true);
                $("#getCode").css("background-color", "#ececec");
            }


        } else {
            // console.log("123");
            $("#regExpPwd").text("第二次密码输入错误");
            $("#checkPwd").show();
            $("#regist_login").attr('disabled', true);
            $("#regist_login").css("background-color", "#999");

        }
    });

    //校验密码是否6位
    $("#pwd").on('input', function (e) {
        if (format_pwd($("#pwd").val())) {
            $("#checkPwd").hide();
        } else {
            $("#regExpPwd").text("密码格式不对");
            $("#checkPwd").show();
        }

    });


    var InterValObj; //timer变量，控制时间
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj);//停止计时器
            if (phone($("#userPhone").val())) {
                $("#getCode").removeAttr("disabled");//启用按钮
                $("#getCode").css("background-color", "transparent");
            }
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
                url: projectName + "/deleteCode",//注意路径
                data: temp,
                dataType: "json",
                success: function (data) {
                    var itemb = data.data;
                    if (itemb == "1") {

                    }
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

    function setCookies(name) {

        var expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (1 * 60 * 1000));
//?替换成分钟数如果为60分钟则为 60 * 60 *1000
        var m=expiresDate.getMinutes();     //获取当前分钟数(0-59)
        var s=expiresDate.getSeconds();
        $.cookie(name, m*60+s, {
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
        params.phone = $("#userPhone").val();
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
        $("#getCode").css("background-color", "#ececec");
        var temp = true;
        if ($.cookie(params.phone) !=null){
            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>验证码获取频繁！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            temp = false;
        } else {
            setCookies(params.phone);
        }
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        if (temp == false){
            return;
        }

        $.ajax({
            async: false,
            type: "POST",
            url: projectName + "/MallUserRegistered",//注意路径
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
                        title: "<span style='color:#f6d224;font-size: 26px'>该手机号已被注册！<span>",
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


    //注册登录
    $("#regist_login").click(function () {
        var params = {};
        params.userPhone = $("#userPhone").val();
        params.code = $("#code").val();
        params.pwd = $("#pwd").val();
        params.repwd = $("#repwd").val();
        // alert(JSON.stringify(params));
        if (params.userPhone == "" || params.code == "" || params.pwd == "" || params.repwd == "") {

            swal({
                title: "<span style='color:#f6d224;font-size: 26px'>验证码不能为空！<span>",
                text: "2秒后自动关闭。",
                timer: 2000,
                showConfirmButton: true,
                html: true
            });
            return;
        }


        // console.log(JSON.stringify(params,null,4));

        $.ajax({
            async: false,
            type: "POST",
            url: projectName + "/RegistLogin",//注意路径
            data: params,
            dataType: "json",
            success: function (data) {
                var item = data.data;
                if (item == "1") {

                    swal({
                        title: "<span style='color:#6ddb8d;font-size: 26px'>注册成功！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                    window.location.href = "index";
                    return;

                }
                if (item == "2") {
                    swal({
                        title: "<span style='color:#ef3737;font-size: 26px'>手机号错误！<span>",
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
                        title: "<span style='color:#f6d224;font-size: 26px'>该手机号已被注册！<span>",
                        text: "2秒后自动关闭。",
                        timer: 2000,
                        showConfirmButton: false,
                        html: true
                    });
                }
            },
            error: function (data) {
                swal({
                    title: "<span style='color:#ef3737;font-size: 26px'>注册失败！<span>",
                    text: "2秒后自动关闭。",
                    timer: 2000,
                    showConfirmButton: true,
                    html: true
                });
            }
        });

    });
});