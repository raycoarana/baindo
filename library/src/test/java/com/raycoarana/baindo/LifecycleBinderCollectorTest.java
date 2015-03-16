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

import com.raycoarana.baindo.events.AbstractEventBind;
import com.raycoarana.baindo.events.OnCreateEventBind;
import com.raycoarana.baindo.events.OnDestroyEventBind;
import com.raycoarana.baindo.events.OnPauseEventBind;
import com.raycoarana.baindo.events.OnResumeEventBind;
import com.raycoarana.baindo.events.OnStartEventBind;
import com.raycoarana.baindo.events.OnStopEventBind;
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

    @Mock
    private OnCreateEventBind mOnCreateEventBind;

    @Mock
    private OnDestroyEventBind mOnDestroyEventBind;

    @Mock
    private OnStartEventBind mOnStartEventBind;

    @Mock
    private OnStopEventBind mOnStopEventBind;

    @Mock
    private OnResumeEventBind mOnResumeEventBind;

    @Mock
    private OnPauseEventBind mOnPauseEventBind;

    @Mock
    private AbstractEventBind mBindedEvent;


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

    @Test
    public void shouldDispatchOnCreateEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnCreateEventIsCollected();
        whenDispatchOnCreateEvent();
        thenAllEventBindsAreExecuted();
    }

    @Test
    public void shouldDispatchOnDestroyEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnDestroyEventIsCollected();
        whenDispatchOnDestroyEvent();
        thenAllEventBindsAreExecuted();
    }

    @Test
    public void shouldDispatchOnStartEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnStartEventIsCollected();
        whenDispatchOnStartEvent();
        thenAllEventBindsAreExecuted();
    }

    @Test
    public void shouldDispatchOnStopEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnStopEventIsCollected();
        whenDispatchOnStopEvent();
        thenAllEventBindsAreExecuted();
    }

    @Test
    public void shouldDispatchOnResumeEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnResumeEventIsCollected();
        whenDispatchOnResumeEvent();
        thenAllEventBindsAreExecuted();
    }

    @Test
    public void shouldDispatchOnPauseEventToAllBinds() {
        givenALifecycleBinderCollector();
        givenThatAnOnPauseEventIsCollected();
        whenDispatchOnPauseEvent();
        thenAllEventBindsAreExecuted();
    }

    private void givenThatAnOnCreateEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnCreateEventBind);
    }

    private void givenThatAnOnDestroyEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnDestroyEventBind);
    }

    private void givenThatAnOnStartEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnStartEventBind);
    }

    private void givenThatAnOnStopEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnStopEventBind);
    }

    private void givenThatAnOnResumeEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnResumeEventBind);
    }

    private void givenThatAnOnPauseEventIsCollected() {
        mBindedEvent = mLifecycleBinderCollector.collect(mOnPauseEventBind);
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

    private void whenDispatchOnCreateEvent() {
        mLifecycleBinderCollector.onCreate();
    }

    private void whenDispatchOnDestroyEvent() {
        mLifecycleBinderCollector.onDestroy();
    }

    private void whenDispatchOnStartEvent() {
        mLifecycleBinderCollector.onStart();
    }

    private void whenDispatchOnStopEvent() {
        mLifecycleBinderCollector.onStop();
    }

    private void whenDispatchOnResumeEvent() {
        mLifecycleBinderCollector.onResume();
    }

    private void whenDispatchOnPauseEvent() {
        mLifecycleBinderCollector.onPause();
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

    private void thenAllEventBindsAreExecuted() {
        verify(mBindedEvent).execute();
    }

}
