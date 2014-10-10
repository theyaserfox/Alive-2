package com.alive.alive.AliveEngine.Controllers;

import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;
import com.alive.alive.AliveEngine.Models.User;

/**
 * Created by yessir on 26/09/14.
 */
public class UserController {

    public Response CreateUser(User user)
    {
        Response response = new Response();

        if (user.getFirstName() == "")
            response.Errors.add(new Error("User First Name can't be empty."));
        if (user.getLastName() == "")
            response.Errors.add(new Error("User Last Name can't be empty."));
        if (user.getEmail() == "")
            response.Errors.add(new Error("User Email can't be empty."));
        if (user.getPassword() == "")
            response.Errors.add(new Error("User Password can't be empty."));

        if (response.Errors.size() > 0)
            response.State = ResponseState.FAIL;
        else
        {
            Response serverResponse = user.Create();

            if (serverResponse.State == ResponseState.FAIL)
            {
                response.Errors.add(new Error("Unknown error happend while saving user, please try again later"));
                response.State = ResponseState.FAIL;
            }
        }

        return response;
    }

    /// <summary>
    /// Log the user in the system
    /// </summary>
    /// <param name="inputs">List of inputs : email and password</param>
    /// <returns>Result of running the function</returns>
    public Response UserLogIn(User user)
    {
        Response response = new Response();

        if (user.getEmail() == "")
            response.Errors.add(new Error("User email can't be empty."));
        if (user.getPassword() == null)
            response.Errors.add(new Error("User password can't be empty."));

        if (response.Errors.size() > 0)
            response.State = ResponseState.FAIL;
        else
        {
            Response serverResponse = user.Read();

            if (serverResponse.State == ResponseState.FAIL)
            {
                response.Errors.add(new Error("Unknown error happend while logging in user, please try again later"));
                response.State = ResponseState.FAIL;
            }
        }

        return response;
    }
}
