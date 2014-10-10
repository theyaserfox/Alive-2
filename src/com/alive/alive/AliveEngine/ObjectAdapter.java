package com.alive.alive.AliveEngine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alive.alive.AliveEngine.Models.Object;
import com.alive.alive.VuforiaSamples.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by yessir on 04/10/14.
 */
public class ObjectAdapter extends ArrayAdapter<Object> {

    private Context context;
    private List<Object> objectList;

    public ObjectAdapter(Context context, int resource, List<Object> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objectList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_object, parent, false);

        //Display flower name in the TextView widget
        Object object = objectList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(object.getName());

        //Display flower photo in ImageView widget
        if (object.getBitmap() != null) {
            ImageView image = (ImageView) view.findViewById(R.id.imageView1);
            image.setImageBitmap(object.getBitmap());
        }
        else {
            ObjectAndView container = new ObjectAndView();
            container.object = object;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return view;
    }

    class ObjectAndView {
        public Object object;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<ObjectAndView, Void, ObjectAndView> {

        @Override
        protected ObjectAndView doInBackground(ObjectAndView... objectAndViews) {

            ObjectAndView container = objectAndViews[0];
            Object object = container.object;

            try {
                String imageUrl = object.getFiles().get(0).getPath();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                object.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ObjectAndView objectAndView) {
            ImageView image = (ImageView) objectAndView.view.findViewById(R.id.imageView1);
            image.setImageBitmap(objectAndView.bitmap);                                             //Display the image
            objectAndView.object.setBitmap(objectAndView.bitmap);                                   //Save the image for future use
        }
    }

}
