package com.podraza.android.gaogao.gaogao;

import android.test.AndroidTestCase;

/**
 * Created by adampodraza on 3/4/16.
 */
public class UtilityTest extends AndroidTestCase {

    public void testResult() {
        assertEquals(Utility.isDogResult, "is_dog_result");
        assertEquals(Utility.page, "page");
        assertEquals(Utility.position, "position");
        assertEquals(Utility.emptyString, " ");
        assertEquals(Utility.arrayListIdentifier, "dogs");
    }

}
