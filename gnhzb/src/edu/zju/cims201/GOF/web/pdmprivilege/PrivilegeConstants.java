package edu.zju.cims201.GOF.web.pdmprivilege;

import edu.zju.cims201.GOF.util.ConfigUtil;

public class PrivilegeConstants {
	
	public static ConfigUtil config = new ConfigUtil("application.properties");
	
	public static final String USER_UPLOAD_KNOWLEDGE=config.getValue("priviledge_upload");
	public static final String USER_VIEW_KNOWLEDGE=config.getValue("priviledge_view");
	public static final String USER_DOWNLOAD_KNOWLEDGE=config.getValue("priviledge_download");
	public static final String USER_EDIT_KNOWLEDGE=config.getValue("priviledge_edit");
	
	public static final String ADMIN_ADMIN_NODE=config.getValue("priviledge_admin_node");
	public static final String ADMIN_ASIGN_ADMINISTRATOR=config.getValue("priviledge_asign_admin");
}
