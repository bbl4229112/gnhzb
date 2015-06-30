$(function () {
    $("#form1").submit(function () {
        return false;
    });
    $("#ReviewPanel a").click(ReviewTabClicked);
    $("#menuDataInfo a").click(MenuDataInfoClicked);

    var HasPreview = $("#HasPreview").val();
    if (HasPreview == 1) InitFlash();
    setTimeout(loadDoc, 1000);
    _share_tencent_weibo(null, document.getElementById("content"));
    $("#SubmitConsult").click(SubmitConsult);
    $("#addConsult div").hide();
});

function MenuDataInfoClicked() {
    var HasPreview = $("#HasPreview").val();
    var menuName = $(this).attr("name");

    if ($(this).hasClass("on"))
        return;

    $("#menuDataInfo a").removeClass("on");
    $(this).addClass("on");

    $("#divContent div").addClass("hide");
    if (menuName == "Intro" || menuName == "Preview") {
        if (HasPreview == 1) {
            $("#tag_Preview").removeClass("hide");
            loadSwf(menuName);
        }
        else {
            $("#tag_Intro").removeClass("hide");
            $("#tag_Intro div").removeClass("hide");
        }
    }
    else {
        $("#tag_" + menuName).removeClass("hide");
    }
}

var UserPoint = 0;
function OperateClicked() {
    var operateName = $(this).attr("name");
    var operateURL = "";
    var id = $("#id").val();
    var mid = 0;
    var point = parseInt($("#point").val());
    var price = parseInt($("#Price").val());
    var NeedPost = $("#NeedPost").val();
    var NeedContact = $("#NeedContact").val();
    var hasDownloaded = $("#hasDownloaded").val();
    var htmlAlert = "<div class='dialogBody'>";
    var htmlContent = "";
    var bOperateEnabled = true;

    if (point >= 0 && price == 0 && NeedPost == "False") {

        if (!checkLogin()) {
            rollbackFuntion = "OperateClicked()";
            ShowLogin();
            return;
        }

        var publisherID = parseInt($("#PublisherID").val()); //发布者ID
        var bMySelf = false;
        if (publisherID != 0 && publisherID == loginUserID)
            bMySelf = true;

        var myPoint = getUserPoint();
        operateName = "直接下载";
        operateURL = "/User/getDataset.aspx?id=" + id;

        if (point == 0 || bMySelf) { //不需要积分的，或者是自己发布的数据
            htmlContent = "";
        }
        else {

            IsInstituteOfSoftware();
            var state = setInterval(function () {
                if (syncstate == 2) {
                    clearInterval(state);
                }
            }, 200);

            if (IsPackSale && orgname != undefined) {
                if (orgname != "") {
                    if (isdownloaded) {
                        htmlContent += "<p class='get_info'>您近期下载过本数据，30天之内重复下载不需要付出积分，您可以直接下载本数据。</p>";;
                        bOperateEnabled = true;
                    } else if (packstate == 99) {
                        bOperateEnabled = true;
                    }
                    else {
                        bOperateEnabled = false;
                    }

                    htmlContent += "<p class='get_info'>欢迎您使用数据堂与" + orgname + "图书馆联合推出的“数据下载绿色通道”服务！</p>";
                    switch (packstate) {
                        case 1:
                            htmlContent += "<p class='get_info'>您的邮箱还未验证，我们已经向您的邮箱" + email + "发送了一封验证邮件，请注意查收！</p>";
                            break;
                        case 2:
                            htmlContent += "<p class='get_info'>服务已经过期！</p>";
                            break;
                        case 3:
                            htmlContent += "<p class='get_info'>今日累积下载数据已经达到" + maxcount + "次，！</p>";
                            break;
                    }
                }

            } else {
                if (hasDownloaded == "True") {
                    htmlContent = "<p class='get_info'>您近期下载过本数据，30天之内重复下载不需要付出积分，您可以直接下载本数据。</p>";;
                }
                else if (myPoint >= point) {
                    htmlContent = "<p class='get_info'>下载本数据需要付出<span style='font-weight:bold;color:red'>" + point + "</span>积分，您当前的剩余积分：<span style='font-weight:bold;color:red'>" + myPoint + "</span>，可以直接下载。</p>";
                }
                else {
                    htmlContent = "<p class='get_info'>下载本数据需要付出<span style='font-weight:bold;color:red'>" + point + "</span>积分，您当前的剩余积分：<span style='font-weight:bold;color:red'>" + myPoint + "</span>，不足以支付。</p>";
                    bOperateEnabled = false;
                }
                htmlContent += "<p class='get_about'><span class='h2'>您可以通过以下途径获取积分：</span></p>";
                htmlContent += "<ol class='ga_olli'><li>（1）登录邮箱验证（获10积分，<a href='/User/SecurityVerify.aspx' target='_blank'>立即验证</a>）</li>";
                htmlContent += "<li>（2）发布数据（每个数据发布成功获得5分，<a href='/pub/PublishData.aspx' target='_blank'>去发布</a>）</li>";
                htmlContent += "<li>（3）邀请好友（每成功邀请1位好友获10分，<a href='/other/EmailInvite.aspx' target='_blank'>去邀请</a>）</li>";
                htmlContent += "<li>（4）数据被别人下载（每次1-20分不等）</li>";
                htmlContent += "<li>（5）在线充值（利用堂币换积分，<a href='/User/Charge.aspx' target='_blank'>去充值</a>）</li>";
                htmlContent += "<li>（6）评价数据（评价下载过的数据可获得3分，<a href='/User/MyReview.aspx' target='_blank'>去评价</a>）</li>";
                htmlContent += "</ol><p class='tc pt10'><a href='/help/index.aspx#6_5' target='_blank'>查看更多积分的获取规则&gt;&gt;</a></span></p>"
            }
        }
    }
    else {
        if (NeedPost == "True") {
            mid = $("#MediumInfo").val();
            if (mid == 0) {
                $("#MediumInfo").dialog({
                    time: 1,
                    content: '必须选择一个介质！',
                    icon: 'error'
                });
                return;
            }

            if (!checkLogin()) {
                rollbackFuntion = "OperateClicked();";
                ShowLogin();
                return;
            }

            htmlContent += "";
            operateURL = "/Buy/Confirm.aspx?id=" + id + "&mid=" + mid;
        } else if (NeedContact == "True") {
            $.ajax({
                async: "false",
                type: "POST",
                url: "/DataRes/CostlinessDataContact.aspx",
                dataType: "text",
                cache: "false",
                data:
                        {
                            id: id
                        },
                success: CostlinessDataContactSucc,
                error: AjaxReturnError
            })
            return;
        }
        else {

            if (!checkLogin()) {
                rollbackFuntion = "OperateClicked()";
                ShowLogin();
                return;
            }

            var id_DataRes = $("#id").val();

            htmlContent = "";
            operateURL = "/Buy/Confirm.aspx?id=" + id_DataRes;
        }
    }

    if (htmlContent != "") { //弹出确认
        htmlAlert += htmlContent;
        htmlAlert += "<div class='btnBox tc mt10'>";
        if (bOperateEnabled)
            htmlAlert += "<a class='btn_orange' href='#' id='btnOperate'>" + operateName + "</a>";
        else
            htmlAlert += "<a class='btn_orange' href='#' id='btnCancel'>关 闭</a>";

        htmlAlert += "</div></div>";

        $("#FlexPaperViewer").hide();
        var alertDialog = art.dialog({
            title: operateName + "获取数据提示",
            lock: true,
            content: htmlAlert,
            closeFn: function () {
                $("#FlexPaperViewer").show();
            }
        });

        if (bOperateEnabled)
            $("#btnOperate").click(function () { $("#btnOperate").attr("href", operateURL); $("#btnOperate").attr("target", "_blank"); setTimeout(function () { Refurbish("http://www.datatang.com/data/" + id); }, 2000); });
        else
            $("#btnCancel").click(function () { $("#btnOperate").attr("target", ""); alertDialog.close(); });
    }
    else { //直接跳转
        window.location.href = operateURL;
        return;
    }
}

var syncstate = 0, isdownloaded = false, orgname = "", IsPackSale = false, email = "", packstate = 0, maxcount = 0;
function IsInstituteOfSoftware() {
    syncstate = 1;
    if (!checkLogin()) {
        rollbackFuntion = "IsInstituteOfSoftware()";
        ShowLogin();
        return;
    }
    var id_DataRes = $("#id").val();
    $.ajax({
        async: false,
        type: "POST",
        url: "/User/ajax/AjaxGetUserIdentity.aspx",
        dataType: "text",
        cache: "false",
        data: { id: id_DataRes },
        success: function (data, textStatus) {
            if (data.toString().split("|")[0] == AJAX_SUCCESS_RETURN) {
                orgname = data.toString().split("|")[1];
                isdownloaded = data.toString().split("|")[2] == "True" ? true : false;
                IsPackSale = true;
                syncstate = 2;
                packstate = 99;
            }
            else if (data.toString().split("|")[0] == AJAX_FAIL_RETURN) {
                var errmsg = data.toString().split("|")[1];
                art.dialog({
                    time: 2,
                    content: errmsg
                });
                packstate = parseInt(data.toString().split("|")[1]);
                if (packstate == 1) {
                    email = data.toString().split("|")[3];
                }
                else if (packstate == 3) {
                    maxcount = parseInt(data.toString().split("|")[3]);
                }
                orgname = data.toString().split("|")[2];
                IsPackSale = true;
                syncstate = 2
            }
            else {
                art.dialog({
                    time: 2,
                    content: '服务器错误！'
                });
                syncstate = 2
            }
        },
        error: AjaxReturnError
    })
}

function getUserPoint() {
    $.ajax({
        async: false,
        type: "POST",
        url: "/DataRes/AjaxGetUserPoint.aspx",
        dataType: "text",
        cache: "false",
        success: function (data, textStatus) {
            if (data.toString().split("|")[0] == AJAX_SUCCESS_RETURN) {
                UserPoint = parseInt(data.toString().split("|")[1]);
            }
            else {
                art.dialog({
                    time: 2,
                    content: '服务器错误！'
                });
                return 0;
            }
        },
        error: AjaxReturnError
    });
    return UserPoint;
}

function loadDoc() {
    getReviewList("all", 1);
    getConsult(1);
}

function DownloadResult(data, textStatus) {
    var id = $("#id").val();
    if (data.toString() == "Free") {
        window.location.href = "/User/GetDataset.aspx?id=" + escape(id);
    }
    else if (data.toString() == "NeedBuy") {
        window.location.href = "/Buy/Cart.aspx?id=" + id;
    }
    else {
        art.dialog({
            time: 2,
            content: '未登录，请先登录！',
            closeFn: function () { ShowLogin(); }
        });
    }
}

function AddCollectClicked() {
    var id = $("#id").val();

    if (!checkLogin()) {
        rollbackFuntion = "AddCollectClicked()";
        ShowLogin();
        return;
    }

    $.ajax(
    {
        async: "false",
        type: "POST",
        url: "/DataRes/DetailAddChangClicked.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    id: id
                },
        success: AddCollectSucc,
        error: AjaxReturnError

    })
}

function AddCollectSucc(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        art.dialog({
            time: 2,
            content: '必须要先登录，才能添加收藏，请先登录！',
            closeFn: function () {
                var argument1 = data;
                var argument2 = textStatus;
                rollbackFuntion = "AddCollectSucc(" + argument1 + "," + argument2 + ")";
                ShowLogin();
            }
        });
    }
    else {
        if (data.toString() == AJAX_SUCCESS_RETURN) {
            art.dialog({
                time: 2,
                content: '添加到收藏成功！',
                icon: 'succeed'
            });
        }
        else {
            var msg = data.toString();
            art.dialog({
                time: 2,
                content: msg,
                icon: 'error'
            });
        }
    }
}

function InitFlash() {
    var fileName = $("#fileIntro").val();

    var swfVersionStr = "10.0.0";
    var xiSwfUrlStr = "";

    var flashvars =
    {
        SwfFile: escape(fileName),
        Scale: 1.1,
        ZoomTransition: "easeOut",
        ZoomTime: 0.5,
        ZoomInterval: 0.1,
        FitPageOnLoad: false,
        FitWidthOnLoad: true,
        PrintEnabled: false,
        FullScreenAsMaxWindow: false,
        ProgressiveLoading: true,
        PrintToolsVisible: false,
        ViewModeToolsVisible: false,
        ZoomToolsVisible: true,
        FullScreenVisible: false,
        NavToolsVisible: true,
        CursorToolsVisible: false,
        SearchToolsVisible: false,
        localeChain: "en_US"
    };

    var params =
        {
        }

    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    var attributes = {};
    attributes.id = "FlexPaperViewer";
    attributes.name = "FlexPaperViewer";
    swfobject.embedSWF(
            "/DataRes/FlexPaperViewer.swf",
            "flashContent",
            "764", "545",
            swfVersionStr,
            xiSwfUrlStr,
            flashvars,
            params,
            attributes);

    swfobject.createCSS("#flashContent", "display:block;text-align:left;");

    setTimeout(resizeSwf, 2000);
}

function ReviewTabClicked() {
    if ($(this).hasClass("on"))
        return;

    $("#ReviewPanel a").removeClass("on");
    $(this).addClass("on");

    var type = $(this).attr("name");

    getReviewList(type, 1);
}

function loadSwf(name) {
    getDocViewer().setZoom(1.1);
    var docName = "";
    if (name == "Intro")
        docName = $("#fileIntro").val();
    else
        docName = $("#fileReview").val();
    getDocViewer().loadSwf(docName);
    setTimeout(resizeSwf, 2000);
}

function resizeSwf() {
    //1.设置比例
    getDocViewer().setZoom(1.5);
    //2.移动位置
    //...????
}

function switchSwf(obj) {
    getDocViewer().loadSwf($(obj).val());
}

function getReviewList(reviewType, page) {
    var id = $("#id").val();
    $("#ajaxReview").html("正在获取...");
    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/ReviewList.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    page: page,
                    id: id,
                    type: reviewType
                },
        success: GetReviewListSucc,
        error: AjaxReturnError

    });
}

function GetReviewListSucc(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        art.dialog({
            time: 2,
            content: '服务器错误！',
            icon: 'error'
        });
        return;
    }

    $("#ajaxReview").html(data.toString());
    $("#pageBar1 a").click(ReviewListPaged);
}

var reviewtype = "all", reviewpage = 0;
function ReviewListPaged() {
    var page = $(this).attr("name");
    var type = $("#reviewTypeString").val();
    reviewtype = type;
    reviewpage = page;
    getReviewList(type, page);
}

function getConsult(page) {
    var id = $("#id").val();
    $("#ajaxConsult").html("正在获取...");
    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/BuyConsultList.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    page: page,
                    id: id
                },
        success: GetConsultSucc,
        error: AjaxReturnError

    });
}

function GetConsultSucc(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        $("#ajaxConsult").html("服务器异常,请稍后重试！");
    }
    else {
        $("#ajaxConsult").html(data.toString());
        $("#pageBar1 a").click(ConsultPaged);
    }
}

function ConsultPaged() {
    var pageIndex = $(this).attr("name");
    getConsult(pageIndex);
}

function AjaxReturnError(XMLHttpRequest, textStatus, errorThrown) {
    art.dialog({
        time: 2,
        content: '无法连接服务器！',
        icon: 'error'
    });
};

function SeeCopyright() {
    if ($("#HCopyright").val() == "") {
        art.dialog({
            time: 2,
            content: '暂无版权信息！'
        });
        return;
    }
    window.showModalDialog("Copyright.htm", document.getElementById("HCopyright"), "dialogWidth:400px;dialogHeight:220px;scroll:yes;status:no';center:yes;");
}

var dlgShare;
function AddShareClicked() {
    var id = $("#id").val();

    if (!checkLogin()) {
        rollbackFuntion = "AddShareClicked()";
        ShowLogin();
        return;
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/DetailShareDialog.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    id: id
                },
        success: GetShareDialogSucc,
        error: AjaxReturnError

    });
}

function GetShareDialogSucc(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        $("#AddShare").dialog({
            time: 2,
            content: '未登录，请先登录！',
            icon: 'error',
            closeFn: function () { ShowLogin(); }
        });
        return;
    }

    var html = data.toString();
    $("#divContent").css("visibility", "hidden");
    dlgShare = $("#AddShare").dialog({
        title: "发送邮件-分享数据给好友",
        content: html,
        lock: true,
        closeFn: function () { $("#divContent").css("visibility", "visible"); }
    });

    $("#AddShare2").click(ShareSubmited);
    $("#CancleShare").click(Closedlg);
}

//取消
function Closedlg() {
    dlgShare.close();
}

function ShareSubmited() {
    var title = $("#share_title").val();
    var email = $("#share_email").val();
    var body = $("#share_body").val();
    var myname = $("#share_myname").val();
    if (email == "") {
        art.dialog({
            time: 2,
            content: '请输入邮箱地址！',
            icon: 'error'
        });
        return;
    }
    if (!checkemail(email)) {
        art.dialog({
            time: 2,
            content: '邮箱格式不正确！',
            icon: 'error'
        });
        return;
    }
    if (title == "") {
        art.dialog({
            time: 2,
            content: '请输入邮件标题！',
            icon: 'error'
        });
        return;
    }
    if (body == "") {
        art.dialog({
            time: 2,
            content: '请输入邮件内容！',
            icon: 'error'
        });
        return;
    }
    if (myname == "") {
        art.dialog({
            time: 2,
            content: '请输入签名！',
            icon: 'error'
        });
        return;
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/DetailShareDialogSubmited.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    title: title,
                    email: email,
                    body: encodeURI(body),
                    myname: myname
                },
        success: ShareSubmitedSucc,
        error: ShareSubmitedError

    });
}

function ShareSubmitedSucc(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        $("#AddShare").dialog({
            time: 2,
            content: '内部发生错误！',
            icon: 'error'
        });
        Closedlg();
        return;
    }
    else {
        $("#AddShare").dialog({
            time: 2,
            content: '已经将该数据成功分享给好友！'
        });
        Closedlg();
        return;
    }
}

function ShareSubmitedError(XMLHttpRequest, textStatus, errorThrown) {
    art.dialog({
        time: 2,
        content: '无法连接服务器！',
        icon: 'error'
    });
    Closedlg();
};

function postToTXWB() {
    var title = "数据堂数据分享：" + $("#DefaultTitle").val();
    var DefaultTitle = $("#DefaultTitle").val();
    var _t = encodeURI(title);
    var id = $("#id").val();
    var shareUrl = "http://www.datatang.com/data/" + escape(id) + "/?dt_source=" + escape("share") + "&dt_code=" + escape("QQt");
    var _url = encodeURIComponent(shareUrl);
    var _assname = encodeURI("datatang");
    var _appkey = encodeURI("e6f2b3cb7d024aadb737050ee7fc8300"); //你从腾讯获得的appkey
    var _pic = encodeURI(''); //（例如：var _pic='图片url1|图片url2|图片url3....）
    var _site = 'http://www.datatang.com'; //你的网站地址
    var _u = 'http://v.t.qq.com/share/share.php?url=' + _url + '&appkey=' + _appkey + '&site=' + _site + '&pic=' + _pic + '&title=' + _t + '&assname=' + _assname;
    window.open(_u, "数据堂数据共享", 'width=700, height=680, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no');
}

function postToXLWB() {
    var _w = 16, _h = 16;
    var param = {
        url: location.href,
        type: '3',
        count: '', /**是否显示分享数，1显示(可选)*/
        appkey: '3115291008', /**您申请的应用appkey,显示分享来源(可选)*/
        title: '', /**分享的文字内容(可选，默认为所在页面的title)*/
        pic: 'http://www.datatang.com/images/logo.png', /**分享图片的路径(可选)*/
        ralateUid: '2128908815', /**关联用户的UID，分享微博会@该用户(可选)*/
        rnd: new Date().valueOf()
    }
    var temp = [];
    for (var p in param) {
        temp.push(p + '=' + encodeURIComponent(param[p] || ''))
    }
    document.write('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + temp.join('&') + '" width="' + _w + '" height="' + _h + '"></iframe>')
}

var _share_tencent_weibo = function () {
    var share_btn = function (_arr) {
        if (_arr[0]) {
            return _arr[0];
        }
        else {
            var o = document.createElement("a"),
            _ostyle = "width:92px;height:22px;background:url(http://open.t.qq.com/apps/qshare/images/icon.gif) no-repeat #f00;position:absolute;display:none;";
            o.setAttribute("style", _ostyle);
            o.style.cssText = _ostyle;
            o.setAttribute("href", "javascript:;");
            document.body.insertBefore(o, document.body.childNodes[0]);
            return o;
        }
    }(arguments);
    var share_area = function (_arr) {
        if (_arr[1]) {
            return _arr[1];
        }
        else {
            return document.body;
        }
    }(arguments);
    var _site = "http://www.datatang.com"; //你的网站地址
    var _appkey = encodeURI("e6f2b3cb7d024aadb737050ee7fc8300"); //你从腾讯微博开放平台获得的appkey
    var _web = {
        "name": document.title || "",
        "href": document.location
    };
    var _pic = function (share_area) {
        var _imgarr = share_area.getElementsByTagName("img");
        var _srcarr = [];
        for (var i = 0; i < _imgarr.length; i++) {
            _srcarr.push(_imgarr[i].src);
        }
        return _srcarr;
    }(share_area).join("|"); //图片地址
    var _u = 'http://v.t.qq.com/share/share.php?url=' + encodeURIComponent(_web.href) + '&appkey=' + _appkey + '&site=' + _site + '&title=$title$ &pic=' + _pic; //分享目标
    var _select = function () {
        return (document.selection ? document.selection.createRange().text : document.getSelection()).toString();
    };
    if (!!window.find) {
        HTMLElement.prototype.contains = function (B) {
            return this.compareDocumentPosition(B) - 19 > 0
        }
    }
    String.prototype.elength = function () {
        return this.replace(/[^\u0000-\u00ff]/g, "aa").length;
    }
    document.onmouseup = function (e) {
        e = e || window.event;
        var o = e.target || e.srcElement;
        if (share_area.contains(o) || share_area == o) {
            var _e = {
                "x": e.clientX,
                "y": e.clientY
            };
            var _o = {
                "w": share_btn.clientWidth,
                "h": share_btn.clientHeight
            };
            var _d = window.pageYOffset || (document.documentElement || document.body).scrollTop || 0;
            var x = (_e.x - _o.w < 0) ? _e.x + _o.w : _e.x - _o.w,
            y = (_e.y - _o.h < 0) ? _e.y + _d - _o.h : _e.y + _d - _o.h + (-[1, ] ? 10 : 0);
            if (_select() && _select().length >= 10) {
                with (share_btn.style) {
                    display = "inline-block"
                    left = (x - 5) + "px";
                    top = y + "px";
                    position = "absolute";
                    zIndex = "999999";
                }
            } else {
                share_btn.style.display = "none";
            }
        } else {
            share_btn.style.display = "none";
        }
    };
    share_btn.onclick = function () {
        var _str = _select();
        var _strmaxlen = 280 - ("我来自于科研数据网站-数据堂" + " " + _web.name).elength();
        var _resultstr = "";
        if (_str.elength() > _strmaxlen) {
            _strmaxlen = _strmaxlen - 3;
            for (var i = _strmaxlen >> 1; i <= _strmaxlen; i++) {
                if ((_str.slice(0, i)).elength() > _strmaxlen) {
                    break;
                }
                else {
                    _resultstr = _str.slice(0, i);
                }
            }
            _resultstr += "...";
        } else {
            _resultstr = _str;
        }
        if (_str) {
            var url = _u.replace("$title$", encodeURIComponent(_resultstr + " " + _web.name));
            if (! -[1, ])
            { url = url.substr(0, 2048); }
            window.open(url, '', 'width=700,height=680,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,location=yes,resizable=no,status=no');
        }
    };
};


//_share_tencent_weibo(share_btn,share_area);如须自定义功能，share_btn,share_area自己设置

function OperateConsult() {
    if (!checkLogin()) {
        rollbackFuntion = "OperateConsult()";
        ShowLogin();
        return;
    }
    $("#addConsult div").show();
}

function SubmitConsult() {
    if (!checkLogin()) {
        rollbackFuntion = "SubmitConsult()";
        ShowLogin();
        return;
    }
    var Question = $("#ConsultContent").val();
    var id_DataRes = $("#id").val();
    if (trim(Question) == "") {
        $("#ConsultContent").dialog({
            content: '请填写咨询内容！',
            time: 2
        })
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/AddBuyConsult.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    id_DataRes: id_DataRes,
                    Question: Question
                },
        success: ConsultSuccessed,
        error: AjaxReturnError

    });
}

function ConsultSuccessed(data, textStatus) {
    if (data.toString() == AJAX_FAIL_RETURN) {
        $("#addConsult").dialog({
            time: 2,
            content: '发表咨询失败，请刷新页面重试！',
            icon: 'error'
        });
        return;
    }
    else if (data.toString() == AJAX_SUCCESS_RETURN) {
        $("#addConsult").dialog({
            time: 2,
            content: '发表咨询成功，我们会尽快给您回复！'
        });
        $("#addConsult div").hide();
        return;
    }
}

var CostlinessDataContactDialog;
function CostlinessDataContactSucc(data, textStatus) {
    var html = data.toString();
    CostlinessDataContactDialog = art.dialog({
        title: '联系数据专员',
        content: html,
        closeFn: function () { $("#divContent").css("visibility", "visible"); },
        initFn: function () { $("#divContent").css("visibility", "hidden"); $("#btnSubmit").click(Submit); }
    })
}

function GoReview() {
    if (!checkLogin()) {
        rollbackFuntion = "GoReview()";
        ShowLogin();
        return;
    }
    var id = $("#id").val();
    window.location.href = "/User/AddReview.aspx?data=" + escape(id);
    return;
}

var ReplyObject;
function ReplySub(obj) {
    var contentText, id_review;
    if (obj != undefined) {
        ReplyObject = obj;
        contentText = obj.parentNode.parentNode.firstChild.firstChild.value;
        id_review = obj.parentNode.parentNode.firstChild.lastChild.value;
    }
    else {
        obj = ReplyObject;
        contentText = obj.parentNode.parentNode.firstChild.firstChild.value;
        id_review = obj.parentNode.parentNode.firstChild.lastChild.value;
    }
    if (trim(contentText) == "") {
        $(obj.parentNode.parentNode.firstChild.firstChild).dialog({
            time: 2,
            content: '请输入回复内容！',
            icon: 'error'
        });
        return;
    }
    if (contentText.length < 5) {
        $(contentText).dialog({
            time: 2,
            content: '回复内容须大于5个字符！',
            icon: 'error'
        });
        return;
    }
    if (contentText.length > 140) {
        $(contentText).dialog({
            time: 2,
            content: '回复内容已超过限定的140个字符！',
            icon: 'error'
        });
        return;
    }

    if (!checkLogin()) {
        rollbackFuntion = "ReplySub();";
        ShowLogin();
        return;
    };

    var current_user = $("#CurrentUser").val();

    if (current_user != "") {
        $.ajax({
            async: "false",
            type: "POST",
            url: "/DataRes/AddReplyToReview.aspx",
            dataType: "text",
            cache: "false",
            data:
                    {
                        id_Review: id_review,
                        content: contentText
                    },
            success: ReplyToReviewSuccessed,
            error: AjaxReturnError

        });
    }
    else {
        art.dialog({
            time: 2,
            content: '请登录后再继续！',
            icon: 'error'
        });
        return;
    }
}

function ReplyToReviewSuccessed(data, textStatus) {
    if (data.toString() == AJAX_SUCCESS_RETURN) {
        if (reviewpage > 0) {
            getReviewList(reviewtype, reviewpage);
        }
        else {
            getReviewList(reviewtype, 1);
        }
    }
    else {
        art.dialog({
            time: 2,
            content: '回复失败！',
            icon: 'error'
        });
    }
}

function PubPapers() {
    if (!checkLogin()) {
        rollbackFuntion = "PubPapers();";
        ShowLogin();
        return;
    };

    var id = $("#id").val();
    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/RelevantPapers.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    id: id
                },
        success: RelevantPapersSuccessed,
        error: AjaxReturnError
    });
}

function RelevantPapersSuccessed(data, textStatus) {
    var html = data.toString();
    var dialog = art.dialog({
        title: '发布数据相关论文',
        content: html
    });
}

function PubAlgorithm() {
    if (!checkLogin()) {
        rollbackFuntion = "PubAlgorithm();";
        ShowLogin();
        return;
    };

    var id = $("#id").val();
    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/DataAlgorithm.aspx",
        dataType: "text",
        cache: "false",
        data:
                {
                    id: id
                },
        success: DataAlgorithmSuccessed,
        error: AjaxReturnError
    });
}

function DataAlgorithmSuccessed(data, textStatus) {
    var html = data.toString();
    var dialog = art.dialog({
        title: '发布数据相关算法',
        content: html
    });
}

function getFreeSampleApplyForm() {
    if (!checkLogin()) {
        rollbackFuntion = "getFreeSampleApplyForm()";
        ShowLogin();
        return;
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/FreeSampleApply.aspx",
        dataType: "text",
        cache: "false",
        success: showFreeSampleApplyForm,
        error: AjaxReturnError
    });
}

function showFreeSampleApplyForm(data, textStatus) {
    var html = data.toString();
    var dialog = art.dialog({
        title: '请先填写免费试用申请',
        content: html,
        closeFn: function () { $("#divContent").css("visibility", "visible"); },
        initFn: function () { $("#divContent").css("visibility", "hidden"); }
    });
}

function submitFreeSampleApply() {
    var id_DataRes = $("#id").val();
    var nickName = trim($("#NickName").val());
    var corpName = trim($("#CorpName").val());
    var phone = trim($("#Phone").val());
    var purpose = trim($("#Purpose").val());

    if (nickName == "") {
        art.dialog({
            time: 2,
            content: '请输入称谓！',
            icon: 'error'
        });
        $("#NickName").val("");
        $("#NickName").focus();
        return;
    }
    if (nickName.length < 2) {
        art.dialog({
            time: 2,
            content: '请输入正确的称谓！',
            icon: 'error'
        });
        $("#NickName").val("");
        $("#NickName").focus();
        return;
    }
    if (corpName == "") {
        art.dialog({
            time: 2,
            content: '请输入所在单位名称！',
            icon: 'error'
        });
        $("#CorpName").focus();
        return;
    }
    if (corpName.length < 2) {
        art.dialog({
            time: 2,
            content: '请输入正确的单位名称！',
            icon: 'error'
        });
        $("#CorpName").val("");
        $("#CorpName").focus();
        return;
    }

    if (phone == "") {
        art.dialog({
            time: 2,
            content: '请输入手机/电话号码！',
            icon: 'error'
        });
        $("#Phone").focus();
        return;
    }
    if (!istell(phone) && !chkmobile(phone)) {
        art.dialog({
            time: 2,
            content: '请输入正确的手机/电话号码！',
            icon: 'error'
        });
        $("#Phone").val("");
        $("#Phone").focus();
        return;
    }

    if (purpose == "") {
        art.dialog({
            time: 2,
            content: '请输入数据用途！',
            icon: 'error'
        });
        $("#Purpose").focus();
        return;
    }
    if (purpose.length < 6) {
        art.dialog({
            time: 2,
            content: '数据用途写的太简单了！',
            icon: 'error'
        });
        $("#Purpose").focus();
        return;
    }

    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/FreeSampleApplySubmit.aspx",
        dataType: "text",
        data: {
            id_DataRes: id_DataRes,
            realName: nickName,
            corpName: corpName,
            phone: phone,
            purpose: purpose
        },
        cache: "false",
        success: submitFreeSampleApplySuccess,
        error: AjaxReturnError
    });

}

function submitFreeSampleApplySuccess(data, textStatus) {
    var sResult = data.toString();
    var SUCC = sResult.substr(0, 5);
    var RESULT = sResult.substr(5);
    if (SUCC == AJAX_SUCC) {
        var transfurl = RESULT;
        window.location.href = transfurl;
    }
    else {
        art.dialog({
            time: 2,
            content: '保存数据失败！',
            icon: 'error'
        });
    }
}

var isInitedWanfangData = false;
function GetWanfangPapers() {
    if (isInitedWanfangData) return;
    $("#WanfangPapers").html("<p style=\"margin:5px 0px 0px 0px;\">正在加载...</p>");
    var Keywords = trim($("#DefaultTitle").val());
    $.ajax({
        async: "false",
        type: "POST",
        url: "/DataRes/AjaxGetWanFangPapers.aspx",
        dataType: "text",
        data: {
            Keywords: Keywords,
            ShowCount: 10
        },
        cache: "false",
        success: doGetWanfangPapers,
        error: AjaxReturnError
    });
}

function doGetWanfangPapers(data, textStatus) {

    isInitedWanfangData = true;

    if (data.toString() != "") {
        $("#WanfangPapers").html(data.toString());
    }
    else {
        $("#WanfangPapers").text("暂无信息");
    }
}