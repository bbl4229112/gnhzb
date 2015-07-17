var myTable;
function createUserList(){
var myWin = null;

var myUserinputupdate=null;
var myUserinput = null;
var myColumns = [
                    Edo.lists.Table.createMultiColumn(),
                    
                    {header: '姓名', width:50, headerAlign:'center',align:'center',  dataIndex: 'name'},
                    {header: '性别', width:50, headerAlign:'center',align:'center',  dataIndex: 'sex'},
                    {header: '爱好', width:70, headerAlign:'center', dataIndex: 'hobby'},
        		    {header: '简介', width:100, headerAlign:'center', dataIndex: 'introduction'},
                    //{header: '密码', dataIndex: 'password'},
        		    {header: '邮箱',  width: 100, headerAlign:'center', dataIndex: 'email'},
        		    {header: '图片路径', width:100, headerAlign:'center', dataIndex: 'picturePath'}
        			//{header: '管理角色', dataIndex: 'administrativeRole'},
        			//{header: '技术角色', dataIndex: 'technicalRole'},
   			
                ];
           
myTable = new createTable({autoColumns:true},'100%','100%','用户列表展示',myColumns,['新增','修改','删除'],[addUser,updateUser,delRow],'user!listuser.action', {},true,null);


function addUser(){
//alert("增加用户");
   	if(myWin == null){
		myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		});
		
		myWin = new cims201.utils.getWin(450,410,"增加用户窗口",myUserinput.getUserinput());				
	}
	else
	{
	myWin.destroy();
		myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		});
	myWin = new cims201.utils.getWin(450,410,"增加用户窗口",myUserinput.getUserinput());	
	
	}
	Edo.get("email").set('valid',chkEmail);
	Edo.get("pictureword").set('visible',false);
	//Edo.get("picturePath").setValue("c5d8c0c9-b8a7-49c7-88f6-4f3514bd313f.jpg");
	myUserinput.getUserinput().reset();
//	myWin.show(450,100,true);
	setWinScreenPosition(450,458,myWin,null);

}

function updateUser(){
	var rs=myTable.getSelectedItems();
	if(null==rs[0])
	{alert("请选择需要修改的用户！");
	return null;
	}
	if(myWin == null){
		myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		});
		myWin = new cims201.utils.getWin(450,410,"修改用户窗口",myUserinput.getUserinput());

	}
	else
	{   myWin.destroy();
	
	   	myUserinput = new userinput(function(){
			myWin.hide();
			myTable.search();		
		});
		myWin = new cims201.utils.getWin(450,410,"修改用户窗口",myUserinput.getUserinput());
	
	}
	
	myUserinput.getUserinput().setForm(rs[0]);
	Edo.get("email").set('readOnly',true);
	Edo.get("pictureword").set('visible',false);
	
	//	myWin.show(450,100,true);
	setWinScreenPosition(450,458,myWin,null);
	

}

function delRow(){
var rs=myTable.getSelectedItems();

	if(rs){
		myTable.deleteRecord(rs);
		cims201.utils.getData('user!delete.action',{id:rs[0].id});
		myTable.search();	
		
	}
//myTable.search();
//var abddd = 1;
}
function setAdmin(){
	var rs=myTable.getSelectedItems();

	if(rs){
		myTable.deleteRecord(rs);
		cims201.utils.getData('user!user2adminsave.action',{id:rs[0].id});
		alert("已把该用户设为管理员");
	}
}

function chkEmail(v){
	var userlist = cims201.utils.getData('user!listuser1.action',{});
	for (var i = 0; i < userlist.length; i++) {
		var user = userlist[i];
		var descr = user['email'];
		if(v == descr){
		return "邮箱已存在，请重新输入";
		}
	}
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{1,}){1,})$/;
	var result = reg.exec(v);
	if(result == null) 
		return "只能输入字母、数字和下划线";
}
  

///用于刷新页面
Edo.create({
	id: 'ct',    
    type: 'box',                 
    layout: 'vertical',
    width: 850,
    height: 500,    
    children: [
        myTable.getTable()
    ],
    render: document.body
	
});


//成员方法，其他的都是临时方法，临时变量
this.getUserList = function(){
	return myTable;
}
}

  function setWinScreenPosition(width,height,win,kid)
		{
		var screenw= cims201.utils.getScreenSize().width;
		var screenh=cims201.utils.getScreenSize().height;
		if(width<screenw)
		{width=(screenw-width)/2
		
		}
		else{
		width=0;
		}
		if(height<screenh)
		{height=(screenh-height)/2
		
		}
		else{
		height=0;
		}
		if(null!=kid)
		unvisiblemodul(kid);
		 win.show(width,height,true);
		}



