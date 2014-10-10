package com.alive.alive.AliveEngine.Models;
//package com.alive.alive.Common;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yessir on 26/09/14.
 */
public class Object extends Model {
    private String name;
    private String serialNumber;
    private String barcode;
    private boolean isObject;
    private String kind;
    private ArrayList<File> files;
    private ArrayList<Post> posts;
    private Bitmap bitmap;

    //public static String WebserviceURLFull = WebServiceParent + "/objects";

    private static List<CreateObjectTask> createObjectTasks = new ArrayList<CreateObjectTask>();

    public Object() { }
    public Object(String code, boolean isObject, ArrayList<File> files)
    {
        if(isObject) this.serialNumber = code;
        else this.barcode = code;
        this.files = files;
    }

    public Object(String name, String code, boolean isObject, String kind) {
        this.name = name;
        this.isObject = isObject;
        if(isObject) this.serialNumber = code;
        else this.barcode = code;
        this.kind = kind;
    }

    public boolean isObject() {
        return isObject;
    }

    public void setObject(boolean isObject) {
        this.isObject = isObject;
    }

    private class CreateObjectTask extends AsyncTask<Object, Void,  ArrayList<Object>>
    {
        final String Tag_OID="id";

        final String Tag_Kind = "kind";
        final String Tag_SerialNumber = "serial_number";
        final String Tag_IsObject = "is_object";
        final String Tag_UserId = "userId";

        final String url_create_user = getWebServiceParent() + "/object_create/result";
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            for(int i = 0; i < createObjectTasks.size(); i++) {
                createObjectTasks.get(i).cancel(true);
            }
            createObjectTasks.clear();
            createObjectTasks.add(this);
            getModelHandler().ProgressBarShower();
        }

        @Override
        protected ArrayList<Object> doInBackground(Object... newObjects) {
            ArrayList<Object> objects = new ArrayList<Object>();

            for(int i = 0; i < newObjects.length; i++) {

                List<NameValuePair> CParms = new ArrayList<NameValuePair>();
                CParms.add(new BasicNameValuePair(Tag_Kind, newObjects[i].getKind()));
                CParms.add(new BasicNameValuePair(Tag_SerialNumber, newObjects[i].getSerialNumber()));
                CParms.add(new BasicNameValuePair(Tag_IsObject, newObjects[i].isObject() ? "1" : "0"));
                CParms.add(new BasicNameValuePair(Tag_UserId, User.getLoggedInUser().getId()));
                JSONObject jsonObject = jsonParser.makeHttpRequest(url_create_user, "POST", CParms);

                String OID = null;

                try {
                    OID = jsonObject.getString(Tag_OID);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                newObjects[i].setId(OID);

                objects.add(newObjects[i]);
            }
            return objects;
        }
    }

    public Response Create()
    {
        Response respone = new Response();

        if (this.name == "" || this.serialNumber == "" || this.isObject == true || this.kind == "")
            respone.Errors.add(new Error("An input field or more is/are missing"));

        CreateObjectTask createObjectTask = new CreateObjectTask() {
            protected void onPostExecute(ArrayList<Object> result) {
                //copy(result);
                createObjectTasks.remove(this);
                User.getLoggedInUser().AttachObject(result.get(0));
                //setLoggedInUser(result.get(0));
                getModelHandler().ProgressBarHider();
                getModelHandler().OnModelSent();
            }
        };

        createObjectTask.execute(this);

        //RestRequest request = new RestRequest("http://zonlinegamescom.ipage.com/gp/public/objects/create", Method.POST);
        //request.AddParameter("serial_number", _serialNumber);
        ////request.AddParameter("user_id", _userId);
        //request.AddFile("image", @"C:\Users\Yaser\Desktop\zedny.jpg");
        //byte[] data;
        //using (MemoryStream ms = new MemoryStream())
        //{
        //    _image.Save(ms, ImageFormat.Bmp);
        //    data = ms.ToArray();
        //    ms.Close();
        //}
        //request.AddFile("image", x => new MemoryStream(data), string.Empty);

        //calling server with restClient
        //RestClient restClient = new RestClient();
        //restClient.ExecuteAsync(request, (response) =>
        //{
        //    if (response.StatusCode == HttpStatusCode.OK)
        //    {
        //        //upload successfull
        //        if (response.Content.Contains("true"))
        //        {
        //            respone.State = ResponseState.SUCCESS;
        //        }
        //        else
        //            respone.State = ResponseState.FAIL;
        //    }
        //    else
        //    {
        //        //error ocured during upload
        //        respone.State = ResponseState.FAIL;
        //    }
        //});
        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }

    public Response Read()
    {
        Response respone = new Response();

        //string url = User.WebserviceURLFull + "/" + _owners[_owners.Count - 1].Username + "/object?sn=" + this.Serial;
        //string data = null;
        //HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
        //try
        //{
        //    HttpWebResponse response = (HttpWebResponse)request.GetResponse();
        //    StreamReader sr = new StreamReader(response.GetResponseStream());
        //    data = sr.ReadToEnd();
        //}
        //catch
        //{
        //    respone.State = ResponseState.FAIL;
        //}
        ////if ( == true)
        ////    return true;
        ////else
        ////    return false;

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    //public static List<User> getOwners(RestClient client, String serialNumber)
    //{
    //    RestRequest ownersRequest = new RestRequest("objects/{serialNumber}/owners");
    //    ownersRequest.AddUrlSegment("serialNumber", serialNumber);
    //    IRestResponse<List<User>> ownersResponse = client.Execute<List<User>>(ownersRequest);

    //    return ownersResponse.Data;
    //}
}
