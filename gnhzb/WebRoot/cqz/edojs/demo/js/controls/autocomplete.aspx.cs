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

public partial class products_demo_js_controls_autocomplete : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string key = Request["key"];

        ArrayList db = (ArrayList)Edo.util.JSON.decode(@"[
{id: 1, text: 'jack'},
{id: 2, text: 'niko'},
{id: 3, text: 'jason'},
{id: 4, text: '张三'},
{id: 5, text: '李四'}
]
");

        ArrayList list = new ArrayList();
        for (int i = 0, l=db.Count; i < l; i++)
        {
            Hashtable o = (Hashtable)db[i];
            if (o["text"].ToString().IndexOf(key) != -1)
            {
                list.Add(o);
            }
        }
        string json = Edo.util.JSON.encode(list);

        Response.Write(json);
    }
}
