using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

public partial class examples_lists_table_dataservice : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        int index = Convert.ToInt32(Request["index"]);
        int size = Convert.ToInt32(Request["size"]);
        if (size == 0) size = 20;
        
        int total = 123;
        int start = index * size;
        int end = start + size;        
        ArrayList list = new ArrayList();        
        for (int i = start; i < end; i++)
        {
            if (i >= total) break;
            Hashtable o = new Hashtable();
            o["company"] = "易度"+i;
            o["update"] = DateTime.Now;

            list.Add(o);
        }

        Hashtable ret = new Hashtable();
        ret["error"] = 0;
        ret["list"] = list;
        ret["total"] = total;

        string json = Edo.util.JSON.encode(ret);

        Response.Write(json);
    }
}
