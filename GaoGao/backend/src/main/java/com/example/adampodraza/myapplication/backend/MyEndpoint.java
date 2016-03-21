/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.adampodraza.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.HashMap;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.adampodraza.example.com",
    ownerName = "backend.myapplication.adampodraza.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    private static HashMap<String, String> logins = new HashMap<String, String>();

    static {
        logins.put("apodra86@gmail.com", "hello");
    }

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "getUserId", httpMethod = "post")
    public MyBean sayHi(@Named("email") String email, @Named("password") String password) {
        MyBean response = new MyBean();
        if(logins.containsKey(email)) {
            if(logins.get(email).equals(password)) {
                response.setData(1L);
                return response;
            } else {
                response.setData(0);
                return response;
            }
        }
        response.setData(0);
        return response;
    }

}
