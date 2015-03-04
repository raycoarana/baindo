/*
 * Copyright 2015 Rayco Araña
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.raycoarana.baindo.state;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;

import com.raycoarana.baindo.test.IntegrationTestSuite;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ValueSaverIT extends IntegrationTestSuite {

    private static final String SOME_KEY = "some_key";

    private ValueSaverFactory mValueSaverFactory = ValueSaverFactory.getInstance();
    private Map<String, Object> mValueMap = new HashMap<>();

    @Test
    public void shouldSaveAllSupportedValueTypes() {
        prepareValuesMap();
        for (Map.Entry<String, Object> entry : mValueMap.entrySet()) {
            Bundle mBundle = new Bundle();
            testWithValue(mBundle, entry.getKey(), entry.getValue());
        }
    }

    private void prepareValuesMap() {
        mValueMap.put("Byte", (byte) 42);
        mValueMap.put("byte[]", new byte[]{});
        mValueMap.put("Serializable", new HashMap());
        mValueMap.put("String", "42");
        mValueMap.put("String[]", new String[]{});
        mValueMap.put("Float", (float) 42);
        mValueMap.put("float[]", new float[]{});
        mValueMap.put("Double", 42d);
        mValueMap.put("double[]", new double[]{});
        mValueMap.put("Short", (short) 42);
        mValueMap.put("short[]", new short[]{});
        mValueMap.put("Integer", 42);
        mValueMap.put("int[]", new int[]{});
        mValueMap.put("Long", 42L);
        mValueMap.put("long[]", new long[]{});
        mValueMap.put("Boolean", true);
        mValueMap.put("boolean[]", new boolean[]{});
        mValueMap.put("Character", 'a');
        mValueMap.put("char[]", new char[]{});
        mValueMap.put("CharSequence", new SpannableString(""));
        mValueMap.put("CharSequence[]", new CharSequence[]{});
        mValueMap.put("Parcelable", new Intent());
        mValueMap.put("Parcelable[]", new Parcelable[]{});
    }

    private <T> void testWithValue(Bundle mState, String key, T value) {
        ValueSaver<T> valueSaver = mValueSaverFactory.build(value);
        valueSaver.save(mState, key, value);
        assertEquals("Fail testing ValueSaver for type " + value.getClass().getName(),
                mState.get(key),
                value);
    }

}
