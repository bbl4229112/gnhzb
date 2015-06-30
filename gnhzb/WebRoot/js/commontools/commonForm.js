/**
创建比较通用的form
*/

cims201.form.createForm = function(formItems){
	
}

/**
表单验证函数
*/
//表单验证器函数
cims201.form.validate.noEmptyStrict = function(v){
	var reg = /^\S+$/;
    var result = reg.exec(v);
    if(result == null) return "不能为空";
}
cims201.form.validate.noEmpty = function(v){
     var vr =  v.replace(/(^\s*)|(\s*$)/g, "");
     if(vr == "") return "不能为空";
}
cims201.form.validate.isEnglishAndLenthlimit = function(v){
    if(v.length > 20) return "超出字符长度";
    var reg = /^[a-zA-Z]+$/;   
 	   var result = reg.exec(v);
        if(result == null) return "只能输入英文且不允许出现空格";     
}
cims201.form.validate.leastLength = function(v){
    if(v.length < 3) return "密码不能少于三位";
}

cims201.form.validate.execReg = function(reg,v){
 var result =  reg.exec(v);
 if(result == null) return "格式不对，请重新输入";
}

cims201.form.validate.isEnglish = function(v){
    var reg = /^[a-zA-Z]+$/;   
 	   var result = reg.exec(v);
        if(result == null) return "只能输入英文且不允许出现空格";     
}

cims201.form.validate.isKtypeProperytEnglishName = function(v){
       var reg = /^[a-zA-Z_0-9]+$/;
      // alert(v);
       var firstcharater=v.substring(0,1);
     //  alert(firstcharater);
       var firstcharaterreg = /^[a-zA-Z]+$/;   
 	   var result = firstcharaterreg.exec(firstcharater);
        if(result == null) return "首字母只能输入英文且不允许出现空格";     
      
    	   var result = reg.exec(v);
           if(result == null) 
        	   return "只能输入英文且不允许出现空格"; 
    
       //return "第一个不能为下划线";
}
cims201.form.validate.isChinese = function(v){
       var reg = /^[\u0391-\uFFE5]+$/;
       var result = reg.exec(v);
       if(result == null) return "只能输入中文且不允许出现空格";
}
cims201.form.validate.isNumber = function(v){
       var reg = /^[0-9]+$/;
       var result = reg.exec(v);
       if(result == null) return "只能输入数字";
}
cims201.form.validate.mostLength = function(v){
    var reg = /^.{1,255}$/;
       var result = reg.exec(v);
       if(result == null) return "超出字符长度";
}
cims201.form.validate.mostLength1 = function(v){
    var reg = /^.{0,255}$/;
       var result = reg.exec(v);
       if(result == null) return "超出字符长度";
}


//长度限制，不能超过某长度的限制
cims201.form.validate.lengthLimit = function(v,limit){
    if(v.length>limit)
    return "输入长度不能超过"+limit;
}

