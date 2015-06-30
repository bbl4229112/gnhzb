//当前正在操作的事件ID
currentEventID = null;

//系统事件监听表
enterEventList = new Array();
enterEventIDList = new Array();
enterlock = true;
//function setEnterEvent(){
document.onkeydown = function(event){
	var e = event?event:(window.event?window.event:null);
	if(e.keyCode == 13){

		//只有在enter可以按下的情况下才执行
		if(enterlock){
			//enterEventList.each(function(e){
			//	if(e){
			//		e.enter();
			//	}
			//});
			if(currentEventID != null){
				enterEventIDList.each(function(e,index){
					if(e == currentEventID){
						if(enterEventList[index]){
							enterEventList[index].enter();
						}
					}
				});
			}
		}
	}
}
//}