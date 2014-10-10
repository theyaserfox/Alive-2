package com.alive.alive.AliveEngine.Models;

import android.os.AsyncTask;
import android.widget.ProgressBar;

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
public class User extends Model {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Object> objects;
    private static User loggedInUser = new User();

    private static List<RetrieveUserTask> retrieveUserTasks = new ArrayList<RetrieveUserTask>();
    private static List<CreateUserTask> createUserTasks = new ArrayList<CreateUserTask>();

    public User() { }

    public  User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    private class CreateUserTask extends AsyncTask<User, Void,  ArrayList<User>>
    {
        final String Tag_UID="id";

        final String Tag_FirstName = "firstName";
        final String Tag_LastName = "lastName";
        final String Tag_Email = "email";
        final String Tag_Password = "password";

        final String url_create_user = getWebServiceParent() + "/signup/result";
        JSONParser jsonParser = new JSONParser();

        @Override
        protected void onPreExecute() {
            for(int i = 0; i < createUserTasks.size(); i++) {
                createUserTasks.get(i).cancel(true);
            }
            createUserTasks.clear();
            createUserTasks.add(this);
            getModelHandler().ProgressBarShower();
        }

        @Override
        protected ArrayList<User> doInBackground(User... newUsers) {
            ArrayList<User> users = new ArrayList<User>();

            for(int i = 0; i < newUsers.length; i++) {

                List<NameValuePair> CParms = new ArrayList<NameValuePair>();
                CParms.add(new BasicNameValuePair(Tag_FirstName, newUsers[i].getFirstName()));
                CParms.add(new BasicNameValuePair(Tag_LastName, newUsers[i].getLastName()));
                CParms.add(new BasicNameValuePair(Tag_Email, newUsers[i].getEmail()));
                CParms.add(new BasicNameValuePair(Tag_Password, newUsers[i].getPassword()));
                JSONObject jsonObject = jsonParser.makeHttpRequest(url_create_user, "POST", CParms);

                String UID = null;

                try {
                    UID = jsonObject.getString(Tag_UID);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                newUsers[i].setId(UID);

                users.add(newUsers[i]);
            }
            return users;
        }
    }

    public Response Create()
    {
        Response respone = new Response();

        if (this.firstName == "" || this.lastName == "" || this.email == "" || this.password == "")
            respone.Errors.add(new Error("An input field or more is/are missing"));

        CreateUserTask createUserTask = new CreateUserTask() {
            protected void onPostExecute(ArrayList<User> result) {
                //copy(result);
                createUserTasks.remove(this);
                setLoggedInUser(result.get(0));
                getModelHandler().ProgressBarHider();
                getModelHandler().OnModelSent();
            }
        };

        createUserTask.execute(this);

        return respone;
    }

    private class RetrieveUserTask extends AsyncTask<User, Void, ArrayList<User>> {

        final String TAG_UID = "id";
        final String TAG_UEMAIL = "email";
        final String TAG_UPASSWPORD = "password";
        final String TAG_UFIRSTNAME = "firstName";
        final String TAG_ULASTNAME = "lastName";

        JSONParser jsonParser = new JSONParser();
        final String url_product_detials = getWebServiceParent() + "/login/result";

        @Override
        protected void onPreExecute() {
            for(int i = 0; i < retrieveUserTasks.size(); i++) {
                retrieveUserTasks.get(i).cancel(true);
            }
            retrieveUserTasks.clear();
            retrieveUserTasks.add(this);
            getModelHandler().ProgressBarShower();
        }

        @SuppressWarnings("finally")
        @Override
        protected ArrayList<User> doInBackground(User... oldUsers) {
            ArrayList<User> users = new ArrayList<User>();

            for(int i = 0; i < oldUsers.length; i++) {
                List<NameValuePair> CParams = new ArrayList<NameValuePair>();
                CParams.add(new BasicNameValuePair(TAG_UEMAIL, oldUsers[i].getEmail()));
                CParams.add(new BasicNameValuePair(TAG_UPASSWPORD, oldUsers[i].getPassword()));
                JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "POST", CParams);

                String id = null;
                String firstName = null;
                String lastName = null;

                try {
                    //JSONArray userObj = json.getJSONArray(TAG_USER);
                    id = json.getString(TAG_UID);
                    firstName = json.getString(TAG_UFIRSTNAME);
                    lastName = json.getString(TAG_ULASTNAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                oldUsers[i].setId(id);
                oldUsers[i].setFirstName(firstName);
                oldUsers[i].setLastName(lastName);

                users.add(oldUsers[i]);
            }

            return users;
        }
    }

    public Response Read()
    {
        Response respone = new Response();

        if (this.email == "" || this.password == "")
            respone.Errors.add(new Error("Email or Password isnt set"));

        RetrieveUserTask retrieveUser = new RetrieveUserTask() {
            protected void onPostExecute(ArrayList<User> result) {
                //isFetched = true;
                //copy(result);
                retrieveUserTasks.remove(this);
                setLoggedInUser(result.get(0));
                getModelHandler().ProgressBarHider();
                getModelHandler().OnModelRetrieved();
            }
        };

        retrieveUser.execute(this);

        return respone;
    }

    public Response Update()
    {
        Response respone = new Response();

        //try
        //{
        //    String URL = WebserviceURLFull + "/edit";

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

        //    String responsefromserver = Encoding.UTF8.GetString(responseBytes);

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
        //    String url = WebserviceURLFull + "/delete";
        //    var data = new NameValueCollection();

        //    data["market_id"] = _market.Id.ToString();
        //    data["id"] = id;

        //    byte[] responseBytes = wb.UploadValues(url, "POST", data);

        //    String responsefromserver = Encoding.UTF8.GetString(responseBytes);

        //    if (responsefromserver != null)
        //        return true;

        //    return false;

        //}
        //catch { return false; }
        if (respone.Errors.size() > 0)
            respone.State = ResponseState.FAIL;
        return respone;
    }

    public Response AttachObject(Object obj)
    {
        Response respone = new Response();
        if (obj.getFiles().get(0).getPath() == "" || obj.getSerialNumber() == "" || this.getId() == "")
            respone.Errors.add(new Error("File or serial or Id isnt set"));

        objects.add(obj);

        //RestRequest attachObjectRequest = new RestRequest("objects/create", Method.POST);
        //attachObjectRequest.AddFile("image", obj.Files[0].Path);
        //attachObjectRequest.AddParameter("serial_number", obj.SerialNumber);
        //attachObjectRequest.AddParameter("user_id", Id);

        //IRestResponse<Object> attachObjectResponse = HttpClient.Execute<Object>(attachObjectRequest);
        //if (attachObjectResponse.Data != null)
        //    Objects.Add(attachObjectResponse.Data);
        //else
        //    respone.Errors.Add(new Error("Error Happened while creating new object!"));

        //if (respone.Errors.Count > 0)
        //    respone.State = ResponseState.FAIL;

        return respone;
    }

    protected void copy(User user)
    {

        //CreatedAt = user.CreatedAt;
        //UpdatedAt = user.UpdatedAt;
        setId(user.getId());
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
    }

    protected void loadObjects()
    {
        //RestRequest objectsRequest = new RestRequest("users/{username}/objects", Method.GET);
        //objectsRequest.AddUrlSegment("username", Username);

        //IRestResponse<List<Object>> objectsResponse = HttpClient.Execute<List<Object>>(objectsRequest);
        //Objects = objectsResponse.Data;
    }

    public Response makePost(Object obj, Post post)
    {
        Response response = new Response();

        if (post.TextPosts.get(0).Paragraph == "" || obj.getSerialNumber() == "" || this.getId() == "")
            response.Errors.add(new Error("Text Post or Serial or Id isnt set"));


        //RestRequest makePostRequest = new RestRequest("posts/create", Method.POST);
        //makePostRequest.AddParameter("user_id", Id);
        //makePostRequest.AddParameter("serial_number", obj.SerialNumber);
        //makePostRequest.AddParameter("paragraph", post.TextPosts[0].Paragraph);

        //IRestResponse<Post> makePostResponse = HttpClient.Execute<Post>(makePostRequest);

        //if (makePostResponse.Data != null)
        //    obj.Posts.Add(makePostResponse.Data);
        //else
        //    response.Errors.Add(new Error("Error Happened while making new post!"));

        //if (response.Errors.Count > 0)
        //    response.State = ResponseState.FAIL;

        return response;
    }

    public Response updateObject(java.lang.Object obj)
    {
        Response response = new Response();
        //List<Object> userObjects = Objects.Where((userObject) => userObject.SerialNumber == obj.SerialNumber).ToList();
        //if (userObjects.Count <= 0)
        //    response.Errors.Add(new Error("User doesnt have this object"));
        //else
        //{
        //    RestRequest objectUpdateRequest = new RestRequest("objects/{id}/update", Method.POST);
        //    objectUpdateRequest.AddUrlSegment("id", userObjects[0].Id.ToString());

        //    objectUpdateRequest.AddParameter("serial_number", obj.SerialNumber);
        //    objectUpdateRequest.AddFile("image", obj.Files[0].Path);

        //    IRestResponse updateObjectResponse = HttpClient.Execute(objectUpdateRequest);
        //    bool updated = updateObjectResponse.Content.Contains("true");

        //    if (!updated)
        //        response.Errors.Add(new Error("unkown error happened while updating object"));
        //}


        if (response.Errors.size() > 0)
            response.State = ResponseState.FAIL;

        return response;
    }

    public Response FindObject(String serial)
    {
        Response response = new Response();

        for(Object obj:this.getObjects()) {
            if(obj.getSerialNumber() == serial)
            {
                response.Returns.add(obj);
                break;
            }
        }

        return response;
    }
}
