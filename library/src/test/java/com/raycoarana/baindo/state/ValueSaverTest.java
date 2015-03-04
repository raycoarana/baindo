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

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;

import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

public class ValueSaverTest extends UnitTestSuite {

    private static final String SOME_KEY = "some_key";

    private ValueSaverFactory mValueSaverFactory = ValueSaverFactory.getInstance();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldSaveSizeValueType() {
        Bundle bundle = mock(Bundle.class);
        Size value = new Size(42, 42);
        ValueSaver<Size> valueSaver = mValueSaverFactory.build(value);
        valueSaver.save(bundle, SOME_KEY, value);
        verify(bundle).putSize(SOME_KEY, value);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void shouldSaveSizeFValueType() {
        Bundle bundle = mock(Bundle.class);
        SizeF value = new SizeF(42, 42);
        ValueSaver<SizeF> valueSaver = mValueSaverFactory.build(value);
        valueSaver.save(bundle, SOME_KEY, value);
        verify(bundle).putSizeF(SOME_KEY, value);
    }

}
