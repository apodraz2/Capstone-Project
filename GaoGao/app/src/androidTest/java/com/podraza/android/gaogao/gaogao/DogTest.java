package com.podraza.android.gaogao.gaogao;

import android.test.AndroidTestCase;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

import java.util.ArrayList;

/**
 * Created by adampodraza on 2/24/16.
 */
public class DogTest extends AndroidTestCase {


    public void testResult() {
        ParcelableDog denver = new ParcelableDog(1, new ArrayList<ParcelableTodo>(), "Denver");

        assertNotNull(denver.getTodos());
        assertEquals("Denver", denver.getName());

        ParcelableTodo firstTodo = new ParcelableTodo("feed");
        ParcelableTodo secondTodo = new ParcelableTodo("walk");

        denver.getTodos().add(firstTodo);
        denver.getTodos().add(secondTodo);

        assertNotNull(denver.getTodos().get(0));
        assertNotNull(denver.getTodos().get(1));

        assertEquals("feed", denver.getTodos().get(0).getTodo());
        assertEquals("walk", denver.getTodos().get(1).getTodo());

    }

}
