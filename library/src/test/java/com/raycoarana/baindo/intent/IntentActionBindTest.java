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

package com.raycoarana.baindo.intent;

import android.content.Intent;

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IntentActionBindTest extends UnitTestSuite {

    private static final String SOME_ACTION = "some_action";

    @Mock
    private Intent mIntent;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private AbstractProperty<String> mProperty;

    private IntentActionBind mIntentActionBind;

    @Test
    public void shouldUpdatePropertyWhenNewIntent() {
        givenAnIntentActionBind();
        givenAnIntentWithAction();
        whenNewIntent();
        thenPropertyIsUpdated();
    }

    private void givenAnIntentActionBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mIntentActionBind = new IntentActionBind(mWorkDispatcher);
        mIntentActionBind.to(mProperty);
    }

    private void givenAnIntentWithAction() {
        when(mIntent.getAction()).thenReturn(SOME_ACTION);
    }

    private void whenNewIntent() {
        mIntentActionBind.onNewIntent(mIntent);
    }

    private void thenPropertyIsUpdated() {
        verify(mProperty).setValue(SOME_ACTION);
    }

}
