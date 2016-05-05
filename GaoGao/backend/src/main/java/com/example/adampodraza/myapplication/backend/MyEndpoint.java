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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.repackaged.com.google.api.client.util.store.DataStoreFactory;
import com.google.appengine.repackaged.com.google.datastore.v1.Filter;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonObject;


import java.util.ArrayList;
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

        Query query = new Query("User");

        query.addFilter("email", Query.FilterOperator.EQUAL, email);

        PreparedQuery pq = datastoreService.prepare(query);

        Entity userEntity = pq.asSingleEntity();

        if(userEntity != null) {
            return null;
        } else {


            Entity user = new Entity("User");

            user.setProperty("email", email);

            Key key = datastoreService.put(user);


            return key;
        }
    }

    @ApiMethod(name="getUser", httpMethod="get")
    public Entity getUser(@Named("email") String email) {
        Query query = new Query("User");

        query.addFilter("email", Query.FilterOperator.EQUAL, email);

        PreparedQuery pq = datastoreService.prepare(query);

        Entity userEntity = pq.asSingleEntity();



        return userEntity;
    }




    @ApiMethod(name = "addDog", httpMethod = "post")
    public Key createDog(@Named("email") String userEmail, @Named("name") String name) {
        Entity dog = new Entity("Dog");
        dog.setProperty("user_email", userEmail);
        dog.setProperty("name", name);

        Key key = datastoreService.put(dog);


        return key;

    }

    @ApiMethod(name = "getDogs", httpMethod = "post")
    public List<Entity> getDogs(@Named("email") String userEmail) {


        Query query = new Query("Dog");
        query.addFilter("email", Query.FilterOperator.EQUAL, userEmail);

        PreparedQuery pq = datastoreService.prepare(query);

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();



        return pq.asList(fetchOptions);

    }

    //TODO create one
    @ApiMethod(name="createTodo", httpMethod = "post")
    public Key createTodo(@Named("email") String userEmail, @Named("dog_id") Long dogId, @Named("descrption") String description, @Named("done") boolean done) {
        Entity todo = new Entity("Todo");
        todo.setProperty("email", userEmail);
        todo.setProperty("dog_id", dogId);
        todo.setProperty("description", description);
        todo.setProperty("done", done);

        Key key = datastoreService.put(todo);

        return key;
    }

    //TODO get dog's todos
    @ApiMethod(name="getTodos", httpMethod = "post")
    public List<Entity> getTodos(@Named("email") String userEmail, @Named("dog_id") long dogId) {
        Query query = new Query("Todo");
        query.addFilter("dog_id", Query.FilterOperator.EQUAL, dogId);

        PreparedQuery pq = datastoreService.prepare(query);

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        List<Entity> todos = pq.asList(fetchOptions);

        return todos;
    }


    //TODO update dog
    @ApiMethod(name="updateDog", httpMethod = "put")
    public Key updateDog(Key key, @Named("name") String name) {
        Entity entity;
        try {
            entity = datastoreService.get(key);
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
            return null;
        }

        datastoreService.delete(key);

        entity.setProperty("name", name);
        Key newKey = datastoreService.put(entity);
        return newKey;

    }

    //TODO update todo
    @ApiMethod(name="updateTodo", path="updateTodo", httpMethod = "put")
    public Key updateTodo(Key key, @Named("description") String description, @Named("done") boolean done) {
        Entity entity;
        try {
            entity = datastoreService.get(key);
        } catch(EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        datastoreService.delete(key);

        entity.setProperty("description", description);
        entity.setProperty("done", done);
        Key newKey = datastoreService.put(entity);

        return newKey;
    }

    //TODO delete dog
    @ApiMethod(name="deleteDog", httpMethod = "delete")
    public void deleteDog(Key key) {
        datastoreService.delete(key);
    }

    //TODO delete todo
    @ApiMethod(name="deleteTodo", httpMethod = "delete")
    public void deleteTodo(Key key) {
        datastoreService.delete(key);
    }

    //TODO assign dog to other user


}
