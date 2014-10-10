package com.alive.alive.AliveEngine.Models;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alive.alive.AliveEngine.Models.Common.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by yessir on 26/09/14.
 */
public class File extends Model {
    public int Size;
    public String RealName;
    public String UploadedName;
    private String path;
    public String Content;
    private Bitmap bitmap;

    public File() { }
    public File(String content)
    {
        Content = content;
    }

    public String Create()
    {
        return "";
    }

    public Response Read()
    {
        return new Response();
    }

    public Response Update()
    {

        return new Response();
    }

    public Response Delete()
    {
        return new Response();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getPhoto() {

        if(this.path != null) {
            try {
                InputStream in = (InputStream) new URL(this.path).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
