package com.alive.alive.AliveEngine.Controllers;

import android.graphics.Bitmap;
import android.util.Base64;

import com.alive.alive.AliveEngine.Models.*;
import com.alive.alive.AliveEngine.Models.Object;
import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by yessir on 29/09/14.
 */
public class ObjectController {
    /// <summary>
    /// Create new object in the system
    /// </summary>
    /// <param name="inputs">List of inputs : name, serial and image</param>
    /// <returns>Result of running the function</returns>
    public Response CreateObject(Object object)
    {
        Response response = new Response();

        if (object.getName() == "")
            response.Errors.add(new Error("Object Name can't be empty."));
        if (object.getSerialNumber() == "" && object.getBarcode() == "")
            response.Errors.add(new Error("Object Serial and Barcode can't both be empty."));
        //if (object.getFiles().size() == 0)
        //    response.Errors.add(new Error("Object Image not captured."));

        if (response.Errors.size() > 0)
            response.State = ResponseState.FAIL;
        else
        {
            //String Rand1 = RandomString(4);
            //String Rand2 = RandomString(4);
            //String imagePathNameString = Rand1 + "-" + Rand2;
            //Bitmap imageBitmap = image;
            //string imageSerial = (string)serial.Value;
            //string pathString = "./subdir";
            //System.IO.Directory.CreateDirectory(pathString);
            //String imagePath = "./subdir/" + imagePathNameString + ".jpg";
            //image.
            //image.Save(imagePath);
            //String imageString = BitMapToString(image);
            //User loggedInUser = User.getLoggedInUser();

            //File file = new File(imageString);
            //ArrayList<File> files = new ArrayList<File>();
            //files.add(file);
            //loggedInUser.getId();
            //Response serverResponse = loggedInUser.AttachObject(
            //        new Object(code, true, files)
            //);

            Response serverResponse = object.Create();

            if (serverResponse.State == ResponseState.FAIL)
            {
                response.Errors.add(new Error("Unknown error happend while saving object, please try again later"));
                response.State = ResponseState.FAIL;
            }
        }

        return response;
    }

    /// <summary>
    /// Update an object in the system
    /// </summary>
    /// <param name="inputs">List of inputs : serial and image</param>
    /// <returns>Result of running the function</returns>
    public Response UpdateObject(String code, Bitmap image)
    {
        Response response = new Response();

        if (code == "")
            response.Errors.add(new Error("Object Serial can't be empty."));
        if (image == null)
            response.Errors.add(new Error("Object Image not captured."));

        if (response.Errors.size() > 0)
            response.State = ResponseState.FAIL;
        else
        {
            //string Rand1 = RandomString(4);
            //string Rand2 = RandomString(4);
            //string imagePathNameString = Rand1 + "-" + Rand2;
            //System.Drawing.Bitmap imageBitmap = (System.Drawing.Bitmap)image.Value;
            //string imageSerial = (string)serial.Value;
            //string pathString = "./subdir";
            //System.IO.Directory.CreateDirectory(pathString);
            //String imagePath = @"./subdir/" + imagePathNameString + @".jpg";
            //imageBitmap.Save(imagePath);
            String imageString = BitMapToString(image);
            User loggedInUser = User.getLoggedInUser();

            File file = new File(imageString);
            ArrayList<File> files = new ArrayList<File>();
            files.add(file);

            Response serverResponse = loggedInUser.updateObject(
                    new Object(code, true, files)
            );

            if (serverResponse.State == ResponseState.FAIL)
            {
                response.Errors.add(new Error("Unknown error happend while updating object, please try again later"));
                response.State = ResponseState.FAIL;
            }
        }

        return response;
    }

    private static Random random = new Random(Calendar.getInstance().get(Calendar.SECOND)); //thanks to McAden

    private String RandomString(int size)
    {
        StringBuilder builder = new StringBuilder();
        char ch;
        for (int i = 0; i < size; i++)
        {
            ch =(char)(Math.floor(26 * random.nextDouble() + 65));
            builder.append(ch);
        }

        return builder.toString();
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String strBitMap = Base64.encodeToString(b, Base64.DEFAULT);
        return strBitMap;
    }
}
