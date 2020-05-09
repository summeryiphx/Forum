$('#submit').on('click',function () {
    var userid = $("#userid").val();
    var username = $("#username").val();
    var mail = $("#mail").val();
    /*用于检验邮箱*/
    var myreg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    if (mail == ''|| mail == undefined) {
        alert("邮箱不能为空！");
    } else if (!myreg.test(mail)) {
        alert("请输入有效的邮箱！");
    }else if (myreg.test(mail)) {
        $.ajax({
            "url":"/updateinfo",
            "type":"post",
            "data":{
                "id":userid,
                "username":username,
                "mail":mail
            },
            "success":function (data) {
                if (data == "修改成功") {
                    alert(data);
                    setTimeout(function () {
                        parent.window.location.reload();
                    },1000);
                }else if (data == "发生一点小错误，请稍后再试") {
                    alert(data);
                }
            }
        });
    }
});