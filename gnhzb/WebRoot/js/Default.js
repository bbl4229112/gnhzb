
var AJAX_SUCCESS_RETURN = "ok";
var AJAX_FAIL_RETURN = "error";
var rollbackFuntion = "";
var backfuntion;

var normal = {
    "color": "#000",
    "font-weight": "normal",
    "background-image": "url(/@Master/normal.gif)"
};

var selected = {
    "color": "#FFF",
    "font-weight": "bolder",
    "background-image": "url(/@Master/selected.gif)"
};

$(function () {
    //    if (window.location.href.toString().indexOf("/pub/UploadByFTP.aspx") == -1 || window.location.href.toString().indexOf("/datatask/editdata.aspx") == -1) {
    //        $("#form1").submit(function () {
    //            return false;
    //        });
    //    }

    //    $("input[type='text'], input[type='password'], textarea").focus(function () { $(this).css({ background-color: "#fefefe", border: "1px solid #faa517" }) });
    //    $("#d_TxtSearch").focus(function () { $(this).css({ background-color: "#ffffff", border: "1px solid #ffffff" }) });
    //$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
    $("#d_LogoNavPanel a").focus(function () {
        this.blur();
    });

    $("#d_LogoNavPanel a").click(NavItemClicked);
    $("#d_LogoNavPanel a").css(normal);
    $("#d_LogoNavPanel a:first").css(selected);

    $("#d_BtnSearch").click(SearchClicked);

    $("#searchboxtop span").click(function () {
        $("#searchboxtop span").removeClass("selected").addClass("none");
        $(this).removeClass("none").addClass("selected");
        $("#searchType").val($(this).attr("name"));
    });

    $("#d_TxtSearch").bind('keyup', function (event) {
        if (event.keyCode == 13) SearchClicked();
    });

    $(window).scroll(function () {
        if ($(this).scrollTop() != 0) {
            $('#toTop').fadeIn();
        } else {
            $('#toTop').fadeOut();
        }
    });

    $('#toTop').click(function () {
        $('body,html').animate({ scrollTop: 0 }, 800);
    });
});

function show() {
    $("#SelSearchType").show();
    $("#data").click(function () {
        $("#btnSearchType").val("数据");
        $("#SelSearchType").hide();
    })

    $("#demand").click(function () {
        $("#btnSearchType").val("需求");
        $("#SelSearchType").hide();
    })
}

function HideSerachType() {
    $("body").bind("click", HideSerachTypeFn);
}

function ShowSerachType() {
    $("body").unbind("click", HideSerachTypeFn);
}

function HideSerachTypeFn() {
    $("#SelSearchType").hide();
}

//搜索
function SearchClicked() {
    var search = trim($("#d_TxtSearch").val());
    var r = Math.random();
    var searchType = $("#searchType").val();

    if (search == null || search == "") {

        $('#d_TxtSearch').dialog({
            time: 2,
            content: '请输入搜索关键词！',
            icon: 'error'
        });
        return false;
    }
    var url = "";
    var key = $("#btnSearchType").val();
    if (key == "数据") {
        url = "/s/data?k=" + encodeURI(search.replace(/:|&|%/g, " "));
    }
    else if (key == "需求") {
        url = "/s/demand?search=" + encodeURI(search.replace(/:|&|%/g, " "));
    }
    window.location.href = url;
    return false;
}

//点文字的时候处理把当前项标识为“选择”
function NavItemClicked() {
    $("#d_LogoNavPanel a").css(normal);

    $(this).css(selected);

    if ($(this).text() != "首页") {
        art.dialog({
            time: 2,
            content: 'to do ...',
            icon: 'succeed'
        });
    }
}

function ShowLogin() {
    $.ajax({
        async: "false",
        type: "POST",
        url: "/home/ShowLogin.aspx",
        dataType: "text",
        cache: "false",
        success: LoginHtml,
        error: AjaxReturnError
    });
}

var loginDialog;
function LoginHtml(data, textStatus) {
    if ($("#divContent") != undefined) {
        $("#divContent").css("visibility", "hidden");
    }
    var html = data.toString();
    loginDialog = art.dialog({
        title: "登录数据堂",
        content: html,
        lock: false,
        initFn: function () { $("input[name='LoginName']").focus(); $("input[type='text'], input[type='password']").focus(function () { $(this).css({ background: "#fefefe", border: "1px solid #faa517" }) }); },
        closeFn: function () { if ($("#divContent") != undefined) { $("#divContent").css("visibility", "visible"); } }
    });

    $("#SubmitBtn").click(SubmitBtnClicked);

    $("#LoginName").bind('keyup', function (event) {
        if (event.keyCode == 13) $("#Password").focus();
    });

    $("#Password").bind('keyup', function (event) {
        if (event.keyCode == 13) SubmitBtnClicked();
    });
}

function SubmitBtnClicked() {
    var loginName = trim($("#LoginName").val());
    var password = trim($("#Password").val());
    if (loginName == "") {
        art.dialog({
            title: "用户登录",
            time: 2,
            content: "<div style='padding:15px;'>请输入用户名或邮箱！<div>",
            icon: 'error'
        });
        return;
    }

    if (password == "") {
        art.dialog({
            title: "用户登录",
            time: 2,
            content: "<div style='padding:15px;'>请输入密码！<div>",
            icon: 'error'
        });
        return;
    }

    var model = {
        LoginName: loginName,
        Password: hex_md5(password)
    };


    $.ajax({
        async: "false",
        type: "POST",
        url: "/home/LoginSubmitBtnClicked.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    jsonModel: JSON.stringify(model),
                    isLoginNameRemember: $("#RememberName").attr("checked"),
                    isAutoLogin: $("#AutoLogin").attr("checked")
                },
        success: AjaxReturnSuccess,
        error: AjaxReturnError
    });
}

function AjaxReturnSuccess(data, textStatus) {
    if (data.toString() == AJAX_SUCCESS_RETURN) {
        //alert("登录成功！");
        var url = decodeURI($("#ReturnUrl").val());
        if (url == "")
            url = "/";
        var pannelHtml = "<a class=\"fc_orange\" href=\"/User/My.aspx\" title=\"进入个人中心\">" + $("#LoginName").val() + "</a>，欢迎您来到数据堂！<a class=\"fc_orange\" href=\"/home/logout.aspx\">[注销]</a>"
        $("#setLogin").html(pannelHtml);
        loginDialog.close();
        if (rollbackFuntion != "") {
            window.eval(rollbackFuntion);
        }
    }
    else {
        var msg = data.toString();
        art.dialog({
            id:'testID',
            title: "用户登录",
            time: 2,
            content: "<div style='padding:15px;'>" + msg + "<div>",
            icon: 'error'
        });
    }
};

function IsLogin() {
    $.ajax({
        type: "POST",
        url: "/Account/AjaxIsLogin.aspx",
        dataType: "text",
        success: ValidateLogin,
        error: AjaxReturnError
    })
}

function ValidateLogin(data, textStatus) {
    var status = data.toString();
    if (status == "NoLogin") {
        return false;
    }
    else if (status == "IsLogin") {
        return true;
    }
}

function AjaxReturn(result) {
    var status = result.toString().substr(0, 5);
    if (status == "FAIL:") {
        var msg = result.toString().substr(5, result.length);
        art.dialog({
            time: 2,
            content: msg
        })
        return false;
    }
}

//刷新
function Refurbish(url) {
    window.location.href = url;
}

function AjaxReturnError(XMLHttpRequest, textStatus, eurrorThrown) {
    art.dialog({
        time: 2,
        content: '无法连接服务器！',
        icon: 'error'
    });
};

var bUserLogin = false;
var AJAX_SUCC = "SUCC:";
var AJAX_FAIL = "FAIL:";
var USER_LOGIN = "Login";
var USER_NOLOGIN = "Not_Login";
var loginUserID = 0;
function checkLogin() {
    $.ajax({
        async: false,
        type: "POST",
        url: "/CheckLogin.aspx",
        dataType: "text",
        cache: "false",
        success: function (data, textStatus) {
            var sResult = data.toString();
            var SUCC = sResult.substr(0, 5);
            var RESULT = sResult.substr(5);
            if (SUCC == AJAX_SUCC) {
                if (RESULT == USER_NOLOGIN) {
                    bUserLogin = false;
                    loginUserID = 0;
                }
                else {
                    bUserLogin = true;
                    loginUserID = parseInt(RESULT);
                }
            }
            else {
                alert("校验用户登录状态出错：" + RESULT);
            }
        },
        error: AjaxReturnError
    });
    return bUserLogin;
}

function trim(str) {
    return str.toString().replace(/(^\s*)|(\s*$)/g, "");
}