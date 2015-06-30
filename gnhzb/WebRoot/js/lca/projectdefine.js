var projectobject;
var projectname;
var projectdetail;
var projecttype;

function projectdefine(type){
	projectobjectdefine.projecttype=type;
	 if(type=='LCA'){
		 projectobject=new getLCAprojectdefine();
		
	}else if(type=='LCC'){
		
	}else if(type=='PDM'){
		projectobject=new getPDMprojectdefine();
		
	}
	this.getprojecttype=function(){
	    	 return type;
	     }
}




