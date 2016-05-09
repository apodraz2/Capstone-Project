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
 * on 2016-05-09 at 19:26:42 UTC 
 * Modify at your own risk.
 */

package com.example.adampodraza.myapplication.backend.myApi.model;

/**
 * Model definition for Entity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the myApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Entity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String appId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String kind;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String namespace;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key parent;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JsonMap properties;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Entity propertiesFrom;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAppId() {
    return appId;
  }

  /**
   * @param appId appId or {@code null} for none
   */
  public Entity setAppId(java.lang.String appId) {
    this.appId = appId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Entity setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getKind() {
    return kind;
  }

  /**
   * @param kind kind or {@code null} for none
   */
  public Entity setKind(java.lang.String kind) {
    this.kind = kind;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNamespace() {
    return namespace;
  }

  /**
   * @param namespace namespace or {@code null} for none
   */
  public Entity setNamespace(java.lang.String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getParent() {
    return parent;
  }

  /**
   * @param parent parent or {@code null} for none
   */
  public Entity setParent(Key parent) {
    this.parent = parent;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public JsonMap getProperties() {
    return properties;
  }

  /**
   * @param properties properties or {@code null} for none
   */
  public Entity setProperties(JsonMap properties) {
    this.properties = properties;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Entity getPropertiesFrom() {
    return propertiesFrom;
  }

  /**
   * @param propertiesFrom propertiesFrom or {@code null} for none
   */
  public Entity setPropertiesFrom(Entity propertiesFrom) {
    this.propertiesFrom = propertiesFrom;
    return this;
  }

  @Override
  public Entity set(String fieldName, Object value) {
    return (Entity) super.set(fieldName, value);
  }

  @Override
  public Entity clone() {
    return (Entity) super.clone();
  }

}
