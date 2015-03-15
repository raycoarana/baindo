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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.raycoarana.baindo.Baindo;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Baindo.class, BinderDelegate.class})
public class BaindoFragmentTest extends UnitTestSuite {

    private static final int SOME_VIEW_ID = 0x666;

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private Intent mNewIntent;

    @Mock
    private Bundle mState;

    @Mock
    private Activity mNewActivity;

    @Mock
    private View mView;

    @Mock
    private View mSomeView;

    private BaindoFragment mBaindoFragment;
    private View mTargetView;

    @Test
    public void shouldInstanceBaindoDelegateOnCreate() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldDelegateBind() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenBind();
        thenBindIsDelegated();
    }

    @Test
    public void shouldDelegateUnbind() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenUnbind();
        thenUnbindIsDelegated();
    }

    @Test
    public void shouldDelegateOnStart() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnStart();
        thenOnStartIsDelegated();
    }

    @Test
    public void shouldDelegateOnResume() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnResume();
        thenOnResumeIsDelegated();
    }

    @Test
    public void shouldDelegateOnPause() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnPause();
        thenOnPauseIsDelegated();
    }

    @Test
    public void shouldDelegateOnStop() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnStop();
        thenOnStopIsDelegated();
    }

    @Test
    public void shouldDelegateOnAttach() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnAttach();
        thenOnAttachIsDelegated();
    }

    @Test
    public void shouldDelegateOnSaveInstanceState() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnSaveInstanceState();
        thenOnSaveInstanceStateIsDelegated();
    }
    
    @Test
    public void shouldDestroyBaindoOnDestroy() {
        givenABinderDelegate();
        givenABaindoFragment();
        whenOnCreate();
        whenOnDestroy();
        thenBinderDelegateIsDestroyed();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenFindViewBeforeOnCreateView() {
        givenABaindoFragment();
        whenFindViewById();
    }

    @Test
    public void shouldFindViewInFragmentView() {
        givenACustomBaindoFragment();
        whenFindViewById();
        thenTheViewIsReturned();
    }

    private void givenABinderDelegate() {
        PowerMockito.mockStatic(Baindo.class);
        PowerMockito.when(Baindo.buildBinderDelegate()).thenReturn(mBinderDelegate);
    }

    private void givenABaindoFragment() {
        mBaindoFragment = new BaindoFragment();
    }

    @SuppressWarnings("ResourceType")
    private void givenACustomBaindoFragment() {
        when(mView.findViewById(SOME_VIEW_ID)).thenReturn(mSomeView);
        mBaindoFragment = new BaindoFragment() {
            @Override
            public View getView() {
                return mView;
            }
        };
    }

    private void whenOnCreate() {
        mBaindoFragment.onCreate(mock(Bundle.class));
    }

    private void whenOnStart() {
        mBaindoFragment.onStart();
    }

    private void whenOnStop() {
        mBaindoFragment.onStop();
    }

    private void whenOnPause() {
        mBaindoFragment.onPause();
    }

    private void whenOnResume() {
        mBaindoFragment.onResume();
    }

    private void whenOnAttach() {
        when(mNewActivity.getIntent()).thenReturn(mNewIntent);
        mBaindoFragment.onAttach(mNewActivity);
    }

    private void whenOnSaveInstanceState() {
        mBaindoFragment.onSaveInstanceState(mState);
    }

    private void whenOnDestroy() {
        mBaindoFragment.onDestroy();
    }

    private void whenBind() {
        mBaindoFragment.bind();
    }

    private void whenUnbind() {
        mBaindoFragment.unbind();
    }


    private void whenFindViewById() {
        mTargetView = mBaindoFragment.findViewById(SOME_VIEW_ID);
    }

    private void thenANewBinderDelegateIsCreated() {
        PowerMockito.verifyStatic();
        Baindo.buildBinderDelegate();
    }

    private void thenBinderDelegateIsDestroyed() {
        verify(mBinderDelegate).onDestroy();
    }

    private void thenBindIsDelegated() {
        verify(mBinderDelegate).bind(mBaindoFragment);
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

    private void thenOnAttachIsDelegated() {
        verify(mBinderDelegate).onFragmentAttach(mNewIntent);
    }

    private void thenOnSaveInstanceStateIsDelegated() {
        verify(mBinderDelegate).onSaveInstanceState(mState);
    }

    @SuppressWarnings("ResourceType")
    private void thenTheViewIsReturned() {
        verify(mView).findViewById(SOME_VIEW_ID);
        assertEquals(mSomeView, mTargetView);
    }

}
