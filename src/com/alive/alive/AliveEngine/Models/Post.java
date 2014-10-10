package com.alive.alive.AliveEngine.Models;

import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;

import java.util.List;

/**
 * Created by yessir on 26/09/14.
 */
public class Post extends Model {
    public List<TextPost> TextPosts;
    public static String WebserviceURLFull = getWebServiceParent() + "/posts";

    public Post() { }
    public Post(List<TextPost> textPosts)
    {
        TextPosts = textPosts;
    }

    public Response Create()
    {
        Response respone = new Response();
        //   string URL = WebserviceURLFull + "/create";

        //string URL = "http://zonlinegamescom.ipage.com/gp/public/posts/create";

        //WebClient webClient = new WebClient();

        //NameValueCollection formData = new NameValueCollection();

        //formData["user_id"] = this.UserId;

        //formData["serial_number"] = this.Serial;

        //formData["paragraph"] = this.Content;

        //byte[] responseBytes = webClient.UploadValues(URL, "POST", formData);

        //string responsefromserver = Encoding.UTF8.GetString(responseBytes);
        //webClient.Dispose();
        //if (responsefromserver.Contains("true"))
        //    respone.State = ResponseState.SUCCESS;
        //else
        //    respone.State = ResponseState.FAIL;


        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }

    public Response Read()
    {
        Response respone = new Response();



        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }

    public Response Update()
    {
        Response respone = new Response();

        //try
        //{
        //    string URL = WebserviceURLFull + "/edit";

        //    WebClient webClient = new WebClient();

        //    NameValueCollection formData = new NameValueCollection();

        //    formData["id"] = id;

        //    formData["name"] = name;

        //    formData["barcode"] = barcode;

        //    formData["market_id"] = _market.Id.ToString();

        //    formData["price"] = price.ToString();

        //    formData["weight"] = weight;

        //    formData["description"] = description;

        //    byte[] responseBytes = webClient.UploadValues(URL, "POST", formData);

        //    string responsefromserver = Encoding.UTF8.GetString(responseBytes);

        //    webClient.Dispose();

        //    if (responsefromserver == null)
        //        return false;
        //    else
        //        return true;

        //}
        //catch (Exception ex)
        //{
        //    return false;
        //}
        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }

    public Response Delete()
    {
        Response respone = new Response();

        //try
        //{
        //    WebClient wb = new WebClient();
        //    string url = WebserviceURLFull + "/delete";
        //    var data = new NameValueCollection();

        //    data["market_id"] = _market.Id.ToString();
        //    data["id"] = id;

        //    byte[] responseBytes = wb.UploadValues(url, "POST", data);

        //    string responsefromserver = Encoding.UTF8.GetString(responseBytes);

        //    if (responsefromserver != null)
        //        return true;

        //    return false;

        //}
        //catch { return false; }
        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }
}
