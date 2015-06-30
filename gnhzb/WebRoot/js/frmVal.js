//校验email\
function checkemail(email) {
    var rec = /\b(^(\S+@).+((\.cn)|(\.com)|(\.net)|(\.org)|(\.info)|(\.edu)|(\.mil)|(\.gov)|(\.biz)|(\.ws)|(\.us)|(\.tv)|(\.cc)|(\..{2,2}))$)\b/;
    return rec.test(email);
}
//校验手机
function chkmobile(mobile) {
    var rec = /^1[358]\d{9}$/;
    return rec.test(mobile);
}
//校验电话号码
function chktelphone(t)
{
    var rec = /(\d{3,4}-)?(\d{7,8})(-\d{1,4})?/
    return rec.test(t);
}

//校验输入是否含有中文

function chkChinese(v) {
    re = /[\u4E00-\u9FA5]/;
    return re.test(v);
}

//检验电话 010-11111111
function chktel(tel) {
    var rec = /^\d{3,4}-\d{7,8}(-\d{3,4})?$/;
    return rec.test(tel);
}
//检验电话 01011111111
function chktel2(tel) {
    var rec = /^0(([1-9]\d)|([3-9]\d{2}))\d{8}$/;
    return rec.test(tel);
}


//校验字符输入
function chk_nick(nickname) {
    var arr = new Array('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '[', ']', '/', '{', '}', '-', '+', '=', ';', '.', '\\', '?');
    for (var i = 0; i < arr.length; i++) {
        if (nickname.indexOf(arr[i]) != -1) {
            return true;
        }
    }
    return false;
}


// 判断输入是否是一个整数
function isint(str)
{
    var result=str.match(/^(-|\+)?\d+$/);
    if(result==null) return false;
    return true;
}


// 判断输入是否是有效的长日期格式 - "YYYY-MM-DD HH:MM:SS" || "YYYY/MM/DD HH:MM:SS"
function isdatetime(str)
{
    var result=str.match(/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
    if(result==null) return false;
    var d= new Date(result[1], result[3]-1, result[4], result[5], result[6], result[7]);
    return (d.getFullYear()==result[1]&&(d.getMonth()+1)==result[3]&&d.getDate()==result[4]&&d.getHours()==result[5]&&d.getMinutes()==result[6]&&d.getSeconds()==result[7]);
}


// 检查是否为 YYYY-MM-DD || YYYY/MM/DD 的日期格式
function isdate(str){
   var result=str.match(/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
   if(result==null) return false;
   var d=new Date(result[1], result[3]-1, result[4]);
   return (d.getFullYear()==result[1] && d.getMonth()+1==result[3] && d.getDate()==result[4]);
}


// 判断输入是否是有效的电子邮件
function isemail(str)
{
    var result=str.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/);
    if(result==null) return false;
    return true;
}

//匹配中国邮政编码(6位)
function ispostcode(str)
{
    var result = str.match(/\d{6}/);
    if(result==null) return false;
    return true;
}

//匹配国内电话号码(0511-4405222 或 021-87888822)
function istell(str)
{
    var result = str.match(/[0-9]\d{5}(?!\d)/);
    if (result == null) return false;
    return true;
}
