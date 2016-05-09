/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-04 15:59:39 UTC)
 * on 2016-05-09 at 19:55:08 UTC 
 * Modify at your own risk.
 */

package com.example.adampodraza.myapplication.backend.myApi;

/**
 * Service definition for MyApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link MyApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class MyApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.22.0 of the myApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "myApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public MyApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  MyApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "addDog".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link AddDog#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param name
   * @return the request
   */
  public AddDog addDog(java.lang.String email, java.lang.String name) throws java.io.IOException {
    AddDog result = new AddDog(email, name);
    initialize(result);
    return result;
  }

  public class AddDog extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Key> {

    private static final String REST_PATH = "createDog/{email}/{name}";

    /**
     * Create a request for the method "addDog".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link AddDog#execute()} method to invoke the remote operation. <p> {@link
     * AddDog#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param name
     * @since 1.13
     */
    protected AddDog(java.lang.String email, java.lang.String name) {
      super(MyApi.this, "POST", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.Key.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public AddDog setAlt(java.lang.String alt) {
      return (AddDog) super.setAlt(alt);
    }

    @Override
    public AddDog setFields(java.lang.String fields) {
      return (AddDog) super.setFields(fields);
    }

    @Override
    public AddDog setKey(java.lang.String key) {
      return (AddDog) super.setKey(key);
    }

    @Override
    public AddDog setOauthToken(java.lang.String oauthToken) {
      return (AddDog) super.setOauthToken(oauthToken);
    }

    @Override
    public AddDog setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddDog) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddDog setQuotaUser(java.lang.String quotaUser) {
      return (AddDog) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddDog setUserIp(java.lang.String userIp) {
      return (AddDog) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public AddDog setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public AddDog setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public AddDog set(String parameterName, Object value) {
      return (AddDog) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "createTodo".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link CreateTodo#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param dogId
   * @param descrption
   * @param done
   * @return the request
   */
  public CreateTodo createTodo(java.lang.String email, java.lang.Long dogId, java.lang.String descrption, java.lang.Boolean done) throws java.io.IOException {
    CreateTodo result = new CreateTodo(email, dogId, descrption, done);
    initialize(result);
    return result;
  }

  public class CreateTodo extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Key> {

    private static final String REST_PATH = "createTodo/{email}/{dog_id}/{descrption}/{done}";

    /**
     * Create a request for the method "createTodo".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link CreateTodo#execute()} method to invoke the remote operation. <p>
     * {@link
     * CreateTodo#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param dogId
     * @param descrption
     * @param done
     * @since 1.13
     */
    protected CreateTodo(java.lang.String email, java.lang.Long dogId, java.lang.String descrption, java.lang.Boolean done) {
      super(MyApi.this, "POST", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.Key.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.dogId = com.google.api.client.util.Preconditions.checkNotNull(dogId, "Required parameter dogId must be specified.");
      this.descrption = com.google.api.client.util.Preconditions.checkNotNull(descrption, "Required parameter descrption must be specified.");
      this.done = com.google.api.client.util.Preconditions.checkNotNull(done, "Required parameter done must be specified.");
    }

    @Override
    public CreateTodo setAlt(java.lang.String alt) {
      return (CreateTodo) super.setAlt(alt);
    }

    @Override
    public CreateTodo setFields(java.lang.String fields) {
      return (CreateTodo) super.setFields(fields);
    }

    @Override
    public CreateTodo setKey(java.lang.String key) {
      return (CreateTodo) super.setKey(key);
    }

    @Override
    public CreateTodo setOauthToken(java.lang.String oauthToken) {
      return (CreateTodo) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateTodo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateTodo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateTodo setQuotaUser(java.lang.String quotaUser) {
      return (CreateTodo) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateTodo setUserIp(java.lang.String userIp) {
      return (CreateTodo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public CreateTodo setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key("dog_id")
    private java.lang.Long dogId;

    /**

     */
    public java.lang.Long getDogId() {
      return dogId;
    }

    public CreateTodo setDogId(java.lang.Long dogId) {
      this.dogId = dogId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String descrption;

    /**

     */
    public java.lang.String getDescrption() {
      return descrption;
    }

    public CreateTodo setDescrption(java.lang.String descrption) {
      this.descrption = descrption;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Boolean done;

    /**

     */
    public java.lang.Boolean getDone() {
      return done;
    }

    public CreateTodo setDone(java.lang.Boolean done) {
      this.done = done;
      return this;
    }

    @Override
    public CreateTodo set(String parameterName, Object value) {
      return (CreateTodo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "createUser".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link CreateUser#execute()} method to invoke the remote operation.
   *
   * @param email
   * @return the request
   */
  public CreateUser createUser(java.lang.String email) throws java.io.IOException {
    CreateUser result = new CreateUser(email);
    initialize(result);
    return result;
  }

  public class CreateUser extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Key> {

    private static final String REST_PATH = "createUser/{email}";

    /**
     * Create a request for the method "createUser".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link CreateUser#execute()} method to invoke the remote operation. <p>
     * {@link
     * CreateUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected CreateUser(java.lang.String email) {
      super(MyApi.this, "POST", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.Key.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public CreateUser setAlt(java.lang.String alt) {
      return (CreateUser) super.setAlt(alt);
    }

    @Override
    public CreateUser setFields(java.lang.String fields) {
      return (CreateUser) super.setFields(fields);
    }

    @Override
    public CreateUser setKey(java.lang.String key) {
      return (CreateUser) super.setKey(key);
    }

    @Override
    public CreateUser setOauthToken(java.lang.String oauthToken) {
      return (CreateUser) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateUser setQuotaUser(java.lang.String quotaUser) {
      return (CreateUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateUser setUserIp(java.lang.String userIp) {
      return (CreateUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public CreateUser setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public CreateUser set(String parameterName, Object value) {
      return (CreateUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "deleteDog".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link DeleteDog#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
   * @return the request
   */
  public DeleteDog deleteDog(com.example.adampodraza.myapplication.backend.myApi.model.Key content) throws java.io.IOException {
    DeleteDog result = new DeleteDog(content);
    initialize(result);
    return result;
  }

  public class DeleteDog extends MyApiRequest<Void> {

    private static final String REST_PATH = "dog";

    /**
     * Create a request for the method "deleteDog".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link DeleteDog#execute()} method to invoke the remote operation. <p>
     * {@link
     * DeleteDog#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
     * @since 1.13
     */
    protected DeleteDog(com.example.adampodraza.myapplication.backend.myApi.model.Key content) {
      super(MyApi.this, "DELETE", REST_PATH, content, Void.class);
    }

    @Override
    public DeleteDog setAlt(java.lang.String alt) {
      return (DeleteDog) super.setAlt(alt);
    }

    @Override
    public DeleteDog setFields(java.lang.String fields) {
      return (DeleteDog) super.setFields(fields);
    }

    @Override
    public DeleteDog setKey(java.lang.String key) {
      return (DeleteDog) super.setKey(key);
    }

    @Override
    public DeleteDog setOauthToken(java.lang.String oauthToken) {
      return (DeleteDog) super.setOauthToken(oauthToken);
    }

    @Override
    public DeleteDog setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (DeleteDog) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public DeleteDog setQuotaUser(java.lang.String quotaUser) {
      return (DeleteDog) super.setQuotaUser(quotaUser);
    }

    @Override
    public DeleteDog setUserIp(java.lang.String userIp) {
      return (DeleteDog) super.setUserIp(userIp);
    }

    @Override
    public DeleteDog set(String parameterName, Object value) {
      return (DeleteDog) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "deleteTodo".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link DeleteTodo#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
   * @return the request
   */
  public DeleteTodo deleteTodo(com.example.adampodraza.myapplication.backend.myApi.model.Key content) throws java.io.IOException {
    DeleteTodo result = new DeleteTodo(content);
    initialize(result);
    return result;
  }

  public class DeleteTodo extends MyApiRequest<Void> {

    private static final String REST_PATH = "todo";

    /**
     * Create a request for the method "deleteTodo".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link DeleteTodo#execute()} method to invoke the remote operation. <p>
     * {@link
     * DeleteTodo#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
     * @since 1.13
     */
    protected DeleteTodo(com.example.adampodraza.myapplication.backend.myApi.model.Key content) {
      super(MyApi.this, "DELETE", REST_PATH, content, Void.class);
    }

    @Override
    public DeleteTodo setAlt(java.lang.String alt) {
      return (DeleteTodo) super.setAlt(alt);
    }

    @Override
    public DeleteTodo setFields(java.lang.String fields) {
      return (DeleteTodo) super.setFields(fields);
    }

    @Override
    public DeleteTodo setKey(java.lang.String key) {
      return (DeleteTodo) super.setKey(key);
    }

    @Override
    public DeleteTodo setOauthToken(java.lang.String oauthToken) {
      return (DeleteTodo) super.setOauthToken(oauthToken);
    }

    @Override
    public DeleteTodo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (DeleteTodo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public DeleteTodo setQuotaUser(java.lang.String quotaUser) {
      return (DeleteTodo) super.setQuotaUser(quotaUser);
    }

    @Override
    public DeleteTodo setUserIp(java.lang.String userIp) {
      return (DeleteTodo) super.setUserIp(userIp);
    }

    @Override
    public DeleteTodo set(String parameterName, Object value) {
      return (DeleteTodo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getDogs".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link GetDogs#execute()} method to invoke the remote operation.
   *
   * @param email
   * @return the request
   */
  public GetDogs getDogs(java.lang.String email) throws java.io.IOException {
    GetDogs result = new GetDogs(email);
    initialize(result);
    return result;
  }

  public class GetDogs extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.EntityCollection> {

    private static final String REST_PATH = "entitycollection/{email}";

    /**
     * Create a request for the method "getDogs".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link GetDogs#execute()} method to invoke the remote operation. <p>
     * {@link
     * GetDogs#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected GetDogs(java.lang.String email) {
      super(MyApi.this, "POST", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.EntityCollection.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public GetDogs setAlt(java.lang.String alt) {
      return (GetDogs) super.setAlt(alt);
    }

    @Override
    public GetDogs setFields(java.lang.String fields) {
      return (GetDogs) super.setFields(fields);
    }

    @Override
    public GetDogs setKey(java.lang.String key) {
      return (GetDogs) super.setKey(key);
    }

    @Override
    public GetDogs setOauthToken(java.lang.String oauthToken) {
      return (GetDogs) super.setOauthToken(oauthToken);
    }

    @Override
    public GetDogs setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetDogs) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetDogs setQuotaUser(java.lang.String quotaUser) {
      return (GetDogs) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetDogs setUserIp(java.lang.String userIp) {
      return (GetDogs) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GetDogs setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public GetDogs set(String parameterName, Object value) {
      return (GetDogs) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getTodos".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link GetTodos#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param dogId
   * @return the request
   */
  public GetTodos getTodos(java.lang.String email, java.lang.Long dogId) throws java.io.IOException {
    GetTodos result = new GetTodos(email, dogId);
    initialize(result);
    return result;
  }

  public class GetTodos extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.EntityCollection> {

    private static final String REST_PATH = "entitycollection/{email}/{dog_id}";

    /**
     * Create a request for the method "getTodos".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link GetTodos#execute()} method to invoke the remote operation. <p>
     * {@link
     * GetTodos#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param dogId
     * @since 1.13
     */
    protected GetTodos(java.lang.String email, java.lang.Long dogId) {
      super(MyApi.this, "POST", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.EntityCollection.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.dogId = com.google.api.client.util.Preconditions.checkNotNull(dogId, "Required parameter dogId must be specified.");
    }

    @Override
    public GetTodos setAlt(java.lang.String alt) {
      return (GetTodos) super.setAlt(alt);
    }

    @Override
    public GetTodos setFields(java.lang.String fields) {
      return (GetTodos) super.setFields(fields);
    }

    @Override
    public GetTodos setKey(java.lang.String key) {
      return (GetTodos) super.setKey(key);
    }

    @Override
    public GetTodos setOauthToken(java.lang.String oauthToken) {
      return (GetTodos) super.setOauthToken(oauthToken);
    }

    @Override
    public GetTodos setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetTodos) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetTodos setQuotaUser(java.lang.String quotaUser) {
      return (GetTodos) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetTodos setUserIp(java.lang.String userIp) {
      return (GetTodos) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GetTodos setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key("dog_id")
    private java.lang.Long dogId;

    /**

     */
    public java.lang.Long getDogId() {
      return dogId;
    }

    public GetTodos setDogId(java.lang.Long dogId) {
      this.dogId = dogId;
      return this;
    }

    @Override
    public GetTodos set(String parameterName, Object value) {
      return (GetTodos) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUser".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
   *
   * @param email
   * @return the request
   */
  public GetUser getUser(java.lang.String email) throws java.io.IOException {
    GetUser result = new GetUser(email);
    initialize(result);
    return result;
  }

  public class GetUser extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Entity> {

    private static final String REST_PATH = "entity/{email}";

    /**
     * Create a request for the method "getUser".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link GetUser#execute()} method to invoke the remote operation. <p>
     * {@link
     * GetUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected GetUser(java.lang.String email) {
      super(MyApi.this, "GET", REST_PATH, null, com.example.adampodraza.myapplication.backend.myApi.model.Entity.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetUser setAlt(java.lang.String alt) {
      return (GetUser) super.setAlt(alt);
    }

    @Override
    public GetUser setFields(java.lang.String fields) {
      return (GetUser) super.setFields(fields);
    }

    @Override
    public GetUser setKey(java.lang.String key) {
      return (GetUser) super.setKey(key);
    }

    @Override
    public GetUser setOauthToken(java.lang.String oauthToken) {
      return (GetUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUser setQuotaUser(java.lang.String quotaUser) {
      return (GetUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUser setUserIp(java.lang.String userIp) {
      return (GetUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GetUser setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public GetUser set(String parameterName, Object value) {
      return (GetUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateDog".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link UpdateDog#execute()} method to invoke the remote operation.
   *
   * @param name
   * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
   * @return the request
   */
  public UpdateDog updateDog(java.lang.String name, com.example.adampodraza.myapplication.backend.myApi.model.Key content) throws java.io.IOException {
    UpdateDog result = new UpdateDog(name, content);
    initialize(result);
    return result;
  }

  public class UpdateDog extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Key> {

    private static final String REST_PATH = "key/{name}";

    /**
     * Create a request for the method "updateDog".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link UpdateDog#execute()} method to invoke the remote operation. <p>
     * {@link
     * UpdateDog#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
     * @since 1.13
     */
    protected UpdateDog(java.lang.String name, com.example.adampodraza.myapplication.backend.myApi.model.Key content) {
      super(MyApi.this, "PUT", REST_PATH, content, com.example.adampodraza.myapplication.backend.myApi.model.Key.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public UpdateDog setAlt(java.lang.String alt) {
      return (UpdateDog) super.setAlt(alt);
    }

    @Override
    public UpdateDog setFields(java.lang.String fields) {
      return (UpdateDog) super.setFields(fields);
    }

    @Override
    public UpdateDog setKey(java.lang.String key) {
      return (UpdateDog) super.setKey(key);
    }

    @Override
    public UpdateDog setOauthToken(java.lang.String oauthToken) {
      return (UpdateDog) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateDog setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateDog) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateDog setQuotaUser(java.lang.String quotaUser) {
      return (UpdateDog) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateDog setUserIp(java.lang.String userIp) {
      return (UpdateDog) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public UpdateDog setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public UpdateDog set(String parameterName, Object value) {
      return (UpdateDog) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateTodo".
   *
   * This request holds the parameters needed by the myApi server.  After setting any optional
   * parameters, call the {@link UpdateTodo#execute()} method to invoke the remote operation.
   *
   * @param description
   * @param done
   * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
   * @return the request
   */
  public UpdateTodo updateTodo(java.lang.String description, java.lang.Boolean done, com.example.adampodraza.myapplication.backend.myApi.model.Key content) throws java.io.IOException {
    UpdateTodo result = new UpdateTodo(description, done, content);
    initialize(result);
    return result;
  }

  public class UpdateTodo extends MyApiRequest<com.example.adampodraza.myapplication.backend.myApi.model.Key> {

    private static final String REST_PATH = "updateTodo";

    /**
     * Create a request for the method "updateTodo".
     *
     * This request holds the parameters needed by the the myApi server.  After setting any optional
     * parameters, call the {@link UpdateTodo#execute()} method to invoke the remote operation. <p>
     * {@link
     * UpdateTodo#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param description
     * @param done
     * @param content the {@link com.example.adampodraza.myapplication.backend.myApi.model.Key}
     * @since 1.13
     */
    protected UpdateTodo(java.lang.String description, java.lang.Boolean done, com.example.adampodraza.myapplication.backend.myApi.model.Key content) {
      super(MyApi.this, "PUT", REST_PATH, content, com.example.adampodraza.myapplication.backend.myApi.model.Key.class);
      this.description = com.google.api.client.util.Preconditions.checkNotNull(description, "Required parameter description must be specified.");
      this.done = com.google.api.client.util.Preconditions.checkNotNull(done, "Required parameter done must be specified.");
    }

    @Override
    public UpdateTodo setAlt(java.lang.String alt) {
      return (UpdateTodo) super.setAlt(alt);
    }

    @Override
    public UpdateTodo setFields(java.lang.String fields) {
      return (UpdateTodo) super.setFields(fields);
    }

    @Override
    public UpdateTodo setKey(java.lang.String key) {
      return (UpdateTodo) super.setKey(key);
    }

    @Override
    public UpdateTodo setOauthToken(java.lang.String oauthToken) {
      return (UpdateTodo) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateTodo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateTodo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateTodo setQuotaUser(java.lang.String quotaUser) {
      return (UpdateTodo) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateTodo setUserIp(java.lang.String userIp) {
      return (UpdateTodo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String description;

    /**

     */
    public java.lang.String getDescription() {
      return description;
    }

    public UpdateTodo setDescription(java.lang.String description) {
      this.description = description;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Boolean done;

    /**

     */
    public java.lang.Boolean getDone() {
      return done;
    }

    public UpdateTodo setDone(java.lang.Boolean done) {
      this.done = done;
      return this;
    }

    @Override
    public UpdateTodo set(String parameterName, Object value) {
      return (UpdateTodo) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link MyApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link MyApi}. */
    @Override
    public MyApi build() {
      return new MyApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link MyApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setMyApiRequestInitializer(
        MyApiRequestInitializer myapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(myapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
