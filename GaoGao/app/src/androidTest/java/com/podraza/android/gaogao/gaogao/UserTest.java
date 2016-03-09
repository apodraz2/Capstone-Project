package com.podraza.android.gaogao.gaogao;

import android.test.AndroidTestCase;

/**
 * Created by adampodraza on 3/4/16.
 */
public class UserTest extends AndroidTestCase {

    User user = new User("Adam", "hello@hello.com");

    public void testResult() {
        assertNotNull(user);

        assertEquals(1, user.getId());
        assertEquals("Adam", user.getName());
        assertEquals("hello@hello.com", user.getEmail());

        ParcelableDog dog1 = new ParcelableDog("Denver");
        ParcelableDog dog2 = new ParcelableDog("Bailey");

        user.getDogs().add(dog1);
        user.getDogs().add(dog2);

        assertEquals(dog1.getName(), user.getDogs().get(0).getName());
        assertEquals(dog2.getName(), user.getDogs().get(1).getName());



        assertNotSame(dog1, user.getDogs().get(0));





    }


}
