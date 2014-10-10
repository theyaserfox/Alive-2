package com.alive.alive.AliveEngine.Models.Common;

import java.util.ArrayList;

/**
 * Created by yessir on 26/09/14.
 */
/// <summary>
/// A type which handels the end result of the function and keep track of errors occured during runtime of it
/// </summary>
public class Response
{
    public ResponseState State;
    public ArrayList<Error> Errors = new ArrayList<Error>();
    public ArrayList<Object> Returns = new ArrayList<Object>();

    public Response() { State = ResponseState.SUCCESS; }

    //public void ShowErrors()
    //{
    //    foreach (Error error in Errors)
    //    {
            //Toast.makeText(getClass(), "Hello", Toast.LENGTH_LONG).show();
            //Dialog("Hello");
            //MessageBox.Show(error.ErrorMessage);
    //    }
    //}
}