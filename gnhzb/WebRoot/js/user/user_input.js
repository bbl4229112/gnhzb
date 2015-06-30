function userinput(callback,roletreenodeid) {
	// 创建用户输入面板
	var iuser = Edo.create({
				// id: 'userForm',
				type : 'box',
				padding : 10,
				// title: '创建用户',
				width : 430,
				// render: document.body,
				children : [

				{
							type : 'formitem',
							label : '邮箱<span style="color:red;">*</span>:',
							labelWidth : 90,
							children : [{
										type : 'text',
										width : 300,
										id : 'email'
									}]
						}, {
							type : 'formitem',
							label : '密码<span style="color:red;">*</span>:',
							labelWidth : 90,
							children : [{
										type : 'password',
										width : 300,
										id : 'password',
										valid : cims201.form.validate.leastLength
									}]
						}, {
							type : 'formitem',
							label : '确认密码<span style="color:red;">*</span>:',
							labelWidth : 90,
							children : [{
										type : 'password',
										width : 300,
										id : 'repeatpassword',
										valid : chkPasswordconfirm
									}]
						}, {
							type : 'formitem',
							label : '姓名<span style="color:red;">*</span>:',
							labelWidth : 90,
							children : [{
										type : 'text',
										width : 300,
										id : 'name',
										valid : cims201.form.validate.noEmpty
									}]
						},

						{
							type : 'formitem',
							label : '性别<span style="color:red;">*</span>:',
							labelWidth : 90,
							children : [{
										id : 'sex',
										type : 'combo',
										width : 300,
										valid : cims201.form.validate.noEmpty,
										readOnly : true,
										displayField : 'text',
										valueField : 'value',
										data : [{
													text : '男',
													value : '男'
												}, {
													text : '女',
													value : '女'
												}]

									}]
						},

						{
							type : 'formitem',
							label : '爱好:',
							labelWidth : 90,
							children : [{
										type : 'textarea',
										width : 300,
										height : 50,
										id : 'hobby',
										valid : cims201.form.validate.mostLength1
									}]
						}, {
							type : 'formitem',
							label : '简介:',
							labelWidth : 90,
							children : [{
										type : 'textarea',
										width : 300,
										height : 90,
										id : 'introduction',
										valid : cims201.form.validate.mostLength1
									}]
						},

						{
							type : 'formitem',
							label : '上传头像:',
							labelWidth : 90,
							id : 'pictureword',
							children : [{
										type : 'text',
										width : 300,
										id : 'picturePath'
									}]
						},
					

						{
							type : 'formitem',
							layout : 'horizontal',
							padding : [8, 0, 8, 0],
							children : [

							{
										type : 'space',
										width : 140
									}, {
										id : 'submitBtn',
										type : 'button',
										text : '提交表单'
									}

							]
						}]
			});

	// 事件监听
	submitBtn.on('click', function(e) {
				// alert(Edo.get('hobby').text);

				// 验证表单
				if (iuser.valid()) {

					var o = iuser.getForm(); // 获取表单值

					var json = Edo.util.Json.encode(o);
					// alert(json); //可以用ajax发送到服务端

					cims201.utils.getData('user!save.action', {
								json : json,roletreenodeid:roletreenodeid
							});
					callback();
				}
			});
	//
	// reset.on('click', function(e){
	// iuser.reset();
	// });

	function chkPasswordconfirm(v) {
		var password = Edo.get('password').text;
		if (password != v) {
			// showNoticeMessage("wrong","两次输入的密码不一致","passconfimWrap");
			return "两次输入的密码不一致";
		}
	}

	/**
	 * 检查密码格式
	 * 
	 * @returns {Number}
	 */

	/*
	 * function chkPassword(){ var password=$.trim($("#passwInp").val());
	 * if(password == "") return 0; var res=isGoodPassword(password);
	 * if(res==false) return -3; var len; var i; var isPassword = true; len = 0;
	 * for (i=0;i<password.length;i++){ if (password.charCodeAt(i)>255)
	 * isPassword = false; } if(!isPassword || password.length < 6) return -1;
	 * if(password.length > 16 ) return -2; return 1; } function CharMode(iN){
	 * if (iN>=48 && iN <=57) //数字 return 1; if (iN>=65 && iN <=90) //大写字母
	 * return 2; if (iN>=97 && iN <=122) //小写 return 4; else return 8; //特殊字符 }
	 * function chkPasswordconfirm(){ var password= $("#passwInp").val(); var
	 * passwordconfirm = $("#passConfim").val(); if(passwordconfirm==''){
	 * $("#passConfim").attr("class","g-ipt"); return false; } if(password !=
	 * passwordconfirm){
	 * showNoticeMessage("wrong","两次输入的密码不一致","passconfimWrap"); return false;
	 * }else { showNoticeMessage("success","","passconfimWrap");
	 * $("#passConfim").attr("class","g-ipt"); RegResult.passwordconfim=true;
	 * return true; } }
	 */

	this.getUserinput = function() {
		return iuser;
	}

}
