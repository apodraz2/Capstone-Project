package com.podraza.android.gaogao.gaogao;

import android.test.AndroidTestCase;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

/**
 * Created by adampodraza on 2/24/16.
 */
public class DogTest extends AndroidTestCase {


    public void testResult() {
        ParcelableDog denver = new ParcelableDog(1, "Denver");

        assertNotNull(denver.getTodos());
        assertEquals("Denver", denver.getName());

        ParcelableTodo firstTodo = new ParcelableTodo("feed", false);
        ParcelableTodo secondTodo = new ParcelableTodo("walk", false);

        denver.getTodos().add(firstTodo);
        denver.getTodos().add(secondTodo);

        assertNotNull(denver.getTodos().get(0));
        assertNotNull(denver.getTodos().get(1));

        assertEquals("feed", denver.getTodos().get(0).getTodo());
        assertEquals("walk", denver.getTodos().get(1).getTodo());

    }

}
