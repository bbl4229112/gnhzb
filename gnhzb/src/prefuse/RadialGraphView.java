package prefuse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import prefuse.data.Graph;
import prefuse.util.ui.JPrefuseApplet;


public class RadialGraphView extends JPrefuseApplet {

    public void init() {
    	Graph g = null;
		try {
			g = getGraph("固体推进剂","tree","forward");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.setContentPane(
       prefuse.owl2prefuse.sample.RadialGraphView.demo(g, "name"));
    }
    
	private static Graph getGraph(String searchname, String viewtype, String direction)
	throws MalformedURLException, IOException, ClassNotFoundException {
// URL url = new URL(getCodeBase(), getCodeBase()
// + "servlet/DisplayGraph?searchname=" + searchname
// + "&viewtype=" + viewtype + "&direction=" + direction);

//String location = this.getCodeBase().toString();
// String location="http://192.168.0.65:8088/MapleTr/";
 String location="http://zzh:8080/MapleTr/";

// if(location.eq)
URL url = new URL(location + "map/loadOwlFile?searchname=" + searchname
		+ "&viewtype=" + viewtype + "&direction=" + direction);
// System.out.println(url.toString());
// app.getAppletContext().showDocument(new URL("javascript:s(\""+
// searchname+"\",\""+direction+"\",\""+viewtype+"\")"));
URLConnection con = url.openConnection();
con.setUseCaches(false);
InputStream in = con.getInputStream();
ObjectInputStream objStream;
objStream = new ObjectInputStream(in);
Graph graph = (Graph) objStream.readObject();
return graph;
}
} // end of class RadialGraphView
