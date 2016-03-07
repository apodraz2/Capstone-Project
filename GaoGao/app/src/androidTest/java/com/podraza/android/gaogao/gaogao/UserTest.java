package com.podraza.android.gaogao.gaogao;

import android.test.AndroidTestCase;

/**
 * Created by adampodraza on 3/4/16.
 */
public class UserTest extends AndroidTestCase {

    User user = new User(1, "Adam", "hello@hello.com");

    public void testResult() {
        assertNotNull(user);

        assertEquals(1, user.getId());
        assertEquals("Adam", user.getName());
        assertEquals("hello@hello.com", user.getEmail());

        ParcelableDog dog1 = new ParcelableDog(1, "Denver");
        ParcelableDog dog2 = new ParcelableDog(2, "Bailey");

        user.getDogs().add(dog1);
        user.getDogs().add(dog2);

        assertEquals(dog1.getName(), user.getDogs().get(0).getName());
        assertEquals(dog2.getName(), user.getDogs().get(1).getName());

        user.updateDogData(0, " ");

        assertNotSame(dog1, user.getDogs().get(0));

        user.updateDogData(0, "Baby");

        assertEquals("Baby", user.getDogs().get(0).getName());

        user.updateDogData(2, "Adam");

        assertEquals("Adam", user.getDogs().get(1).getName());

        try {
            user.updateDogData(4, "George");
        } catch (IndexOutOfBoundsException x) {
            x.printStackTrace();
        }

        assertEquals(user.getDogs().size(), 2);

        ParcelableTodo todo1 = new ParcelableTodo("walk", false);
        ParcelableTodo todo2 = new ParcelableTodo("feed", false);

        user.getDogs().get(0).getTodos().add(todo1);
        user.getDogs().get(0).getTodos().add(todo2);

        assertEquals(todo1.getTodo(), user.getDogs().get(0).getTodos().get(0).getTodo());
        assertEquals(todo2.getTodo(), user.getDogs().get(0).getTodos().get(1).getTodo());

        try {
            user.updateTodoData(4, "Eat", 89);
        } catch(IndexOutOfBoundsException x) {
            x.printStackTrace();
        }

        user.updateTodoData(1, "Buy Food", 0);

        assertEquals("Buy Food", user.getDogs().get(0).getTodos().get(1).getTodo());

        user.updateTodoData(0, " ", 0);

        assertEquals("Buy Food", user.getDogs().get(0).getTodos().get(0).getTodo());

    }


}
