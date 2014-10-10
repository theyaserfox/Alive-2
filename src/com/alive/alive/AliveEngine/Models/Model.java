package com.alive.alive.AliveEngine.Models;

/**
 * Created by yessir on 26/09/14.
 */
public abstract class Model {
    private String id;
    private String CreatedAt;
    private String UpdatedAt;
    private static String webServiceParent = "http://zonlinegamescom.ipage.com/alive";
    protected static String _webserviceMockURLParent = "http://zonlinegamescom.ipage.com/smarthypermarket/public/mocks";

    protected Boolean isFetched = false;
    private OnModelListener modelHandler;
    protected static Boolean isAllFetched = false;
    protected static OnModelListener OnAllModelsRetrieved;
    //abstract public Response Create();
    //abstract public Response Read();
    //abstract public Response Update();
    //abstract public Response Delete();

    public Model() {}

    public static String getWebServiceParent() {
        return webServiceParent;
    }

    public static void setWebServiceParent(String webServiceParent) {
        Model.webServiceParent = webServiceParent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public void setModelHandler(OnModelListener modelHandler) {
        this.modelHandler = modelHandler;
    }

    public OnModelListener getModelHandler() {
        return modelHandler;
    }

    public Boolean isAllFetched()
    {
        return isAllFetched;
    }
    public static void setOnAllModelsRetrieved(OnModelListener onAllModelsRetrieved) {
        OnAllModelsRetrieved = onAllModelsRetrieved;
    }
}
