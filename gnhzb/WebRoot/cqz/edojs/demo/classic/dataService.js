function searchUsers(o, callback){
    var o = {
        data: o,
        method: 'search'
    };
    Edo.util.Ajax.request({        
        url: 'dataService/userService.aspx',
        type: 'post',
        params: o,
        onSuccess: function(text){        
            var o = Edo.util.Json.decode(text);
            if(o.error == 0){
                if(callback) callback(o);                
            }else{
                alert(o.msg);
            }
        },
        onFail: function(code){
            alert("加载错误,"+code);
        }
    });
}
function addUser(o, callback){
    Edo.util.Ajax.request({
        url: 'dataService/userService.aspx',
        type: 'post',
        params: {
            method: 'add',
            data: o
        },
        onSuccess: function(text){        
            var o = Edo.util.Json.decode(text);
            if(o.error == 0){
                if(callback) callback(o)
            }else{
                alert(o.msg);
            }
        },
        onFail: function(code){
            alert("新增错误,"+code);
        }
    });
}

function deleteUser(o, callback){
    Edo.util.Ajax.request({
        url: 'dataService/userService.aspx',
        type: 'post',
        params: {
            method: 'delete',
            data: o
        },
        onSuccess: function(text){        
            var o = Edo.util.Json.decode(text);
            if(o.error == 0){
                if(callback) callback(o)
            }else{
                alert(o.msg);
            }
        },
        onFail: function(code){
            alert("新增错误,"+code);
        }
    });
}

function updateUser(o, callback){
    Edo.util.Ajax.request({
        url: 'dataService/userService.aspx',
        type: 'post',
        params: {
            method: 'update',
            data: o
        },
        onSuccess: function(text){        
            var o = Edo.util.Json.decode(text);
            if(o.error == 0){
                if(callback) callback(o)
            }else{
                alert(o.msg);
            }
        },
        onFail: function(code){
            alert("新增错误,"+code);
        }
    });
}
