function Submit() {
    var nickname = $("#nickname").val();
    var telephone = $("#telephone").val();
    var email = $("#email").val();
    var Characterization = trim($("#Characterization").val());
    if (Characterization.length > 500) {
        $("#Characterization").dialog({
            content: '需求描述长度已超过500字符！',
            time: 2
        })
        return;
    }
    if (trim(nickname) == "") {
        $("#nickname").dialog({
            content: '请输入您的称呼！',
            time: 2
        })
        return;
    }
    if (trim(telephone) == "") {
        $("#telephone").dialog({
            content: '请输入您的联系方式！',
            time: 2
        })
        return;
    }
    if (!chkmobile(telephone) && !chktelphone(telephone)) {
        art.dialog({
            time: 2,
            content: '联系电话格式不正确！'
        });
        return;
    }
    if (trim(email) == "") {
        $("#email").dialog({
            content: '请输入您的邮箱地址！',
            time: 2
        })
        return;
    }
    if (!checkemail(email)) {
        $("#email").dialog({
            time: 2,
            content: "邮件地址格式不正确！"
        })
        this.focus();
        return false;
    }

    var id = 0;
    if ($("#dataid") != undefined) {
        id = $("#dataid").val();
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/CostlinessDataContactSubmit.aspx",
        dataType: "text",
        cache: "false",
        data: {
            id: id,
            NickName: nickname,
            Telephone: telephone,
            Email: email,
            Characterization: Characterization
        },
        success: CostlinessDataContactSubmitSuccess,
        error: AjaxReturnError
    });
}

function CostlinessDataContactSubmitSuccess(data, textStatus) {
    if (data.toString() == AJAX_SUCCESS_RETURN) {
        art.dialog.tips('您的信息已提交，我们的数据专员会尽快联系您！', 3);
        CostlinessDataContactDialog.close();
        if ($("#divContent") != undefined) {
            $("#divContent").css("visibility", "visible");
        }
    }
    else {
        art.dialog.tips('提交失败，请刷新页面重试！', 3);
        CostlinessDataContactDialog.close();
        if ($("#divContent") != undefined) {
            $("#divContent").css("visibility", "visible");
        }
    }
}