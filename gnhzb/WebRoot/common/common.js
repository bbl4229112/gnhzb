function errorAlert(code){
	Edo.MessageBox.show({
        //autoClose: 2000,
        title: '错误提醒',
        width: 300,
        height: 150,
        icon: Edo.MessageBox.ERROR,
        //msg: 'You are closing a tab that has unsaved changes. <br/>Would you like to save your changes?',
        msg: '出错啦!<br>错误代码:'+code,
        //callback: showResult,
        buttons: Edo.MessageBox.YESNOCANCEL
	});
}

function warnAlert(code){
	Edo.MessageBox.show({
        //autoClose: 2000,
        title: '错误提醒',
        width: 300,
        height: 150,
        icon: Edo.MessageBox.WARNING,
        //msg: 'You are closing a tab that has unsaved changes. <br/>Would you like to save your changes?',
        msg: code,
        //callback: showResult,
        buttons: Edo.MessageBox.YESNOCANCEL
	});
}