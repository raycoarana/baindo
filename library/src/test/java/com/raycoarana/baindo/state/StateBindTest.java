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

import android.os.Bundle;

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.Property;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateBindTest extends UnitTestSuite {

    private static final String SOME_KEY = "some_key";

    @Mock
    private WorkDispatcher mWorkDispatcher;
    @Mock
    private Bundle mState;

    private String mKey;
    private StateBind<String> mStateBind;
    private Property<String> mProperty = new Property<>();
    private String mSomeValue = "some_value";

    @Before
    public void setUp() {
        super.setUp();
        WorkDispatcherHelper.setup(mWorkDispatcher);
    }

    @Test
    public void shouldUpdatePropertyWhenRestoringState() {
        givenAStateKey();
        givenANewState();
        givenAStateBind();
        givenThatIsBindToProperty();
        whenUpdateState();
        thenPropertyIsUpdated();
    }

    @Test
    public void shouldSavePropertyValueToStateWhenSavingState() {
        givenAStateKey();
        givenThatPropertyHaveAValue();
        givenAStateBind();
        givenThatIsBindToProperty();
        whenSavingState();
        thenStateIsUpdated();
    }

    private void givenThatPropertyHaveAValue() {
        mProperty.setValue(mSomeValue);
    }

    private void givenAStateKey() {
        mKey = SOME_KEY;
    }

    private void givenANewState() {
        when(mState.get(mKey)).thenReturn(mSomeValue);
    }

    private void givenAStateBind() {
        mStateBind = new StateBind<>(mWorkDispatcher, mKey);
    }

    private void givenThatIsBindToProperty() {
        mStateBind.to(mProperty);
    }

    private void whenUpdateState() {
        mStateBind.onUpdate(mState);
    }

    private void whenSavingState() {
        mStateBind.onSave(mState);
    }

    private void thenPropertyIsUpdated() {
        assertEquals(mSomeValue, mProperty.getValue());
    }

    private void thenStateIsUpdated() {
        verify(mState).putString(mKey, mSomeValue);
    }

}
