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

package com.raycoarana.baindo.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.raycoarana.baindo.Baindo;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Baindo.class, BinderDelegate.class})
public class BaindoListActivityTest extends UnitTestSuite {

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private Intent mNewIntent;

    @Mock
    private Bundle mState;

    private BaindoListActivity mBaindoListActivity;

    @Test
    public void shouldInstanceBaindoDelegateOnCreate() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldInstanceBaindoDelegateOnCreateWithPersistableBundle() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreateWithPersistableBundle();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldDelegateBind() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenBind();
        thenBindIsDelegated();
    }

    @Test
    public void shouldDelegateUnbind() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenUnbind();
        thenUnbindIsDelegated();
    }

    @Test
    public void shouldDelegateOnStart() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnStart();
        thenOnStartIsDelegated();
    }

    @Test
    public void shouldDelegateOnResume() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnResume();
        thenOnResumeIsDelegated();
    }

    @Test
    public void shouldDelegateOnPause() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnPause();
        thenOnPauseIsDelegated();
    }

    @Test
    public void shouldDelegateOnStop() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnStop();
        thenOnStopIsDelegated();
    }

    @Test
    public void shouldDelegateOnNewIntent() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnNewIntent();
        thenOnNewIntentIsDelegated();
    }

    @Test
    public void shouldDelegateOnSaveInstanceState() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnSaveInstanceState();
        thenOnSaveInstanceStateIsDelegated();
    }
    
    @Test
    public void shouldDestroyBaindoOnDestroy() {
        givenABinderDelegate();
        givenABaindoListActivity();
        whenOnCreate();
        whenOnDestroy();
        thenBinderDelegateIsDestroyed();
    }

    private void givenABinderDelegate() {
        PowerMockito.mockStatic(Baindo.class);
        PowerMockito.when(Baindo.buildBinderDelegate()).thenReturn(mBinderDelegate);
    }

    private void givenABaindoListActivity() {
        mBaindoListActivity = new BaindoListActivity();
    }

    private void whenOnCreate() {
        mBaindoListActivity.onCreate(mock(Bundle.class));
    }

    private void whenOnCreateWithPersistableBundle() {
        mBaindoListActivity.onCreate(mock(Bundle.class), mock(PersistableBundle.class));
    }
    
    private void whenOnStart() {
        mBaindoListActivity.onStart();
    }

    private void whenOnStop() {
        mBaindoListActivity.onStop();
    }

    private void whenOnPause() {
        mBaindoListActivity.onPause();
    }

    private void whenOnResume() {
        mBaindoListActivity.onResume();
    }

    private void whenOnNewIntent() {
        mBaindoListActivity.onNewIntent(mNewIntent);
    }

    private void whenOnSaveInstanceState() {
        mBaindoListActivity.onSaveInstanceState(mState);
    }
    
    private void whenOnDestroy() {
        mBaindoListActivity.onDestroy();
    }

    private void whenBind() {
        mBaindoListActivity.bind();
    }

    private void whenUnbind() {
        mBaindoListActivity.unbind();
    }

    private void thenANewBinderDelegateIsCreated() {
        PowerMockito.verifyStatic();
        Baindo.buildBinderDelegate();
    }

    private void thenBinderDelegateIsDestroyed() {
        verify(mBinderDelegate).onDestroy();
    }

    private void thenBindIsDelegated() {
        verify(mBinderDelegate).bind(mBaindoListActivity);
    }

    private void thenUnbindIsDelegated() {
        verify(mBinderDelegate).unbind();
    }

    private void thenOnStartIsDelegated() {
        verify(mBinderDelegate).onStart();
    }

    private void thenOnStopIsDelegated() {
        verify(mBinderDelegate).onStop();
    }

    private void thenOnResumeIsDelegated() {
        verify(mBinderDelegate).onResume();
    }

    private void thenOnPauseIsDelegated() {
        verify(mBinderDelegate).onPause();
    }

    private void thenOnNewIntentIsDelegated() {
        verify(mBinderDelegate).onNewIntent(mNewIntent);
    }

    private void thenOnSaveInstanceStateIsDelegated() {
        verify(mBinderDelegate).onSaveInstanceState(mState);
    }

}
