package edu.zju.cims201.spider.commontool;

import java.io.File;
import java.io.FileOutputStream;

import edu.zju.cims201.spider.heaton.bot.HTTP;
import edu.zju.cims201.spider.heaton.bot.Log;

public class FileToDisk {
	protected String _filepath = "";
	
	public FileToDisk(String _filepath) {
		super();
		this._filepath = _filepath;
	}
	public String get_filepath() {
		return _filepath;
	}
	public void set_filepath(String _filepath) {
		this._filepath = _filepath;
	}
	
	
	public FileToDisk() {
		super();		
	}
	
	public void processFile(HTTP file) {
		try {
			if (_filepath.length() > 0) {
				int i = file.getURL().lastIndexOf('/');

				if (i != -1) {

					String url = file.getURL();
					//URL address = new URL(url);
					//Date time = new Date(address.openConnection().getLastModified());
					// String str=new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss").format(time);
					// Mon, 24 Feb 2003 08:13:45 GMT
					String last_modified = file.getServerHeaders().get(
							"Date").getValue();
					String str = last_modified.substring(12, 16) + "-";
					str += last_modified.substring(8, 11) + "-";
					str += last_modified.substring(5, 7) + " ";
					str += last_modified.substring(16, 24);
					str.replaceAll("Jan", "01");
					str.replaceAll("Feb", "02");
					str.replaceAll("Mar", "03");
					str.replaceAll("Apr", "04");
					str.replaceAll("May", "05");
					str.replaceAll("Jun", "06");
					str.replaceAll("Jul", "07");
					str.replaceAll("Aug", "08");
					str.replaceAll("Sep", "09");
					str.replaceAll("Oct", "10");
					str.replaceAll("Nov", "11");
					str.replaceAll("Dec", "12");
					// String str=new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss").format();
					String str2 = str.replaceAll(":", "#");
					String url2 = (url.replaceAll(":", "#")).replaceAll("/",
							"%");
					String filename = file.getURL().substring(i + 1);
					if (filename.equals(""))
						filename = "root.html";
					filename = "[" + url2 + "]" + "(" + str2 + ")" + filename;

					String dir = getDir(url);
					String wholeDir = buildDir(_filepath, dir);

					FileOutputStream fso = new FileOutputStream(new File(
							wholeDir, filename));
					fso.write(file.getBody().getBytes("iso8859_1"));
					fso.close();
				}
			}
		} catch (Exception e) {
			Log.logException("Can't save output file: ", e);
		}
	}

	public String getDir(String str) {
		int start = str.indexOf("/");
		int end = str.lastIndexOf("/");
		String path = str.substring(start + 2, end);
		int pos = path.indexOf("/");
		if (pos == -1)
			return "";
		else {
			String dir = path.substring(pos + 1);
			return dir;
		}
	}

	public String buildDir(String rootDir, String subDir) {
		String wholeDir;
		File directory;
		if (rootDir.endsWith("/")) {
			wholeDir = rootDir + subDir;
			directory = new File(wholeDir);
		} else {
			wholeDir = rootDir + "/" + subDir;
			directory = new File(wholeDir);
		}
		if (!directory.exists())
			directory.mkdirs();

		return wholeDir;
	}
}
