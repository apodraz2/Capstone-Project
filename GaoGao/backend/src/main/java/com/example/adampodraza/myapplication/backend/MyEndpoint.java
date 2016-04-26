/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.adampodraza.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.repackaged.com.google.api.client.util.store.DataStoreFactory;
import com.google.appengine.repackaged.com.google.datastore.v1.Filter;


import java.util.HashMap;
import java.util.List;

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

    private DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    //TODO secure endpoints with token


    @ApiMethod(name = "createUser", httpMethod = "post")
    public Key createUser(@Named("email") String email) {


        Entity user = new Entity("User");

        user.setProperty("email", email);

        Key key = datastoreService.put(user);


        return key;
    }

    @ApiMethod(name="getUser", httpMethod="get")
    public Entity getUser(@Named("email") String email) {
        Query query = new Query("User");

        query.addFilter("email", Query.FilterOperator.EQUAL, email);

        PreparedQuery pq = datastoreService.prepare(query);

        Entity userEntity = pq.asSingleEntity();

        return userEntity;
    }



    //TODO get user's dogs
    @ApiMethod(name = "addDog", httpMethod = "post")
    public Dog createDog(@Named("dog_id") long id, @Named("email") String userEmail) {
        Dog response = new Dog();

        com.google.appengine.api.datastore.Query.FilterPredicate filter = new com.google.appengine.api.datastore.Query.FilterPredicate("email", com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, userEmail);

        Query query = new Query("Dog");

        Entity dog = new Entity("Dog");

        dog.setProperty("id", id);
        dog.setProperty("user_email", userEmail);

        List<Entity> dogList = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());

        dogList.add(dog);

        Key key = datastoreService.put(dog);
        datastoreService.put(dogList);


        return response;

    }

    //TODO get dog's todos


    //TODO update dog

    //TODO update todo

    //TODO delete dog

    //TODO delete todo

    //TODO insert dog

    //TODO insert todo

    //TODO assign dog to other user


}
