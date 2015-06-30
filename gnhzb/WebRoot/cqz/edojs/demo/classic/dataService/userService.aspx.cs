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

using IBatisNet.DataMapper;
using IBatisNet.DataAccess;

public partial class dataService_userService : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        Hashtable result = new Hashtable();

        ISqlMapper sqlMap = Mapper.Instance();
        try
        {
            sqlMap.BeginTransaction();

            string method = Request["method"].ToString();
            string dataString = Request["data"].ToString();
            Hashtable data = (Hashtable)Edo.util.JSON.decode(dataString);
            switch (method)
            {
                case "add":
                    data["id"] = Guid.NewGuid().ToString();
                    sqlMap.Insert("TEST.insertUser", data);
                    break;
                case "delete":
                    ArrayList users = (ArrayList)data["users"];
                    for (int i = 0, l = users.Count; i < l; i++)
                    {
                        Hashtable u = (Hashtable)users[i];
                        sqlMap.Delete("TEST.deleteUser", u);
                    }
                    break;
                case "update":
                    Hashtable user = (Hashtable)sqlMap.QueryForObject("TEST.getUserByID", data);                    
                    foreach (DictionaryEntry kv in data)
                    {
                        user[kv.Key] = kv.Value;
                    }
                    sqlMap.Delete("TEST.deleteUser", user);
                    sqlMap.Insert("TEST.insertUser", user);
                    break;
                case "getuser":
                    result["data"] = sqlMap.QueryForObject("TEST.getUserByID", data);
                    break;
                case "search":
                    int adsearch = Convert.ToInt32(data["adsearch"]);
                    int gender = Convert.ToInt32(data["gender"]);
                    string country = Convert.ToString(data["country"]);                    
                    string startdate = Convert.ToString(data["startdate"]);
                    string finishdate = Convert.ToString(data["finishdate"]);                    

                    string where = "";
                    if (adsearch == 1)
                    {
                        if (gender != -1)
                        {
                            where += " and gender=" + gender;
                        }               
                        if (country != "-1")
                        {
                            where += " and country='" + country + "'";
                        }                       
                    }
                    DateTime start = new DateTime(1960, 1, 1);
                    try
                    {
                        start = Convert.ToDateTime(startdate);
                    }
                    catch (Exception ex)
                    {
                    }
                    DateTime finish = new DateTime(5000, 1, 1);
                    try
                    {
                        finish = Convert.ToDateTime(finishdate);
                    }
                    catch (Exception ex)
                    {
                    }
                    data["key"] = "%" + data["key"] + "%";
                    data["where"] = where;
                    data["startdate"] = start;
                    data["finishdate"] = finish;
                    result["data"] = Mapper.Instance().QueryForList("TEST.getUsers", data);
                    break;
            }
            result["error"] = 0;

            sqlMap.CommitTransaction();
        }
        catch (Exception ex)
        {
            result["error"] = -1;
            result["msg"] = ex.Message;

            sqlMap.RollBackTransaction();
        }

        Response.Write(Edo.util.JSON.encode(result));
    }    
}
