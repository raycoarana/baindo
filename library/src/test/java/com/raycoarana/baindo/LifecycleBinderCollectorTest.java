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

package com.raycoarana.baindo;

import android.content.Intent;
import android.os.Bundle;

import com.raycoarana.baindo.intent.IntentBind;
import com.raycoarana.baindo.state.StateBind;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class LifecycleBinderCollectorTest extends UnitTestSuite {

    @Mock
    private Intent mIntent;

    @Mock
    private IntentBind mIntentBind;

    @Mock
    private Intent mNewIntent;

    @Mock
    private Bundle mStateBundle;

    @Mock
    private StateBind mStateBind;

    private LifecycleBinderCollector mLifecycleBinderCollector;

    @Test
    public void shouldDispatchNewIntentWhenCollect() {
        givenALifecycleBinderCollector();
        givenThatIsUpdatedWithTheInitialIntent();
        whenCollect();
        thenANewIntentIsDispatched();
    }

    @Test
    public void shouldDispatchNewIntentToAllIntentBinds() {
        givenALifecycleBinderCollector();
        givenThatIsUpdatedWithTheInitialIntent();
        givenThatAIntentBindIsCollected();
        whenDispatchANewIntent();
        thenTheNewIntentIsDispatched();
    }

    @Test
    public void shouldUpdateWhenCollectNewStateBind() {
        givenALifecycleBinderCollector();
        givenThatASavedInstanceStateIsReceived();
        whenCollectStateBind();
        thenStateBindIsUpdated();
    }

    @Test
    public void shouldUpdateAllStateBindsOnRestoreSavedInstanceState() {
        givenALifecycleBinderCollector();
        givenThatAStateBindIsCollected();
        whenASavedInstanceReceived();
        thenStateBindIsUpdated();
    }

    @Test
    public void shouldSaveAllStateBindsOnSaveInstanceState() {
        givenALifecycleBinderCollector();
        givenThatAStateBindIsCollected();
        whenSaveInstanceState();
        thenStateIsSaved();
    }

    private void givenThatASavedInstanceStateIsReceived() {
        mLifecycleBinderCollector.updateSavedInstanceState(mStateBundle);
    }

    private void givenALifecycleBinderCollector() {
        mLifecycleBinderCollector = new LifecycleBinderCollector();
    }

    private void givenThatIsUpdatedWithTheInitialIntent() {
        mLifecycleBinderCollector.updateIntent(mIntent);
    }

    private void givenThatAIntentBindIsCollected() {
        whenCollect();
    }

    private void givenThatAStateBindIsCollected() {
        whenCollectStateBind();
    }

    private void whenCollect() {
        mLifecycleBinderCollector.collect(mIntentBind);
    }

    private void whenDispatchANewIntent() {
        mLifecycleBinderCollector.updateIntent(mNewIntent);
    }

    private void whenCollectStateBind() {
        mLifecycleBinderCollector.collect(mStateBind);
    }

    private void whenASavedInstanceReceived() {
        mLifecycleBinderCollector.updateSavedInstanceState(mStateBundle);
    }

    private void whenSaveInstanceState() {
        mLifecycleBinderCollector.saveInstanceState(mStateBundle);
    }

    private void thenANewIntentIsDispatched() {
        verify(mIntentBind).onNewIntent(mIntent);
    }

    private void thenTheNewIntentIsDispatched() {
        verify(mIntentBind).onNewIntent(mNewIntent);
    }

    private void thenStateBindIsUpdated() {
        verify(mStateBind).onUpdate(mStateBundle);
    }

    private void thenStateIsSaved() {
        verify(mStateBind).onSave(mStateBundle);
    }

}
