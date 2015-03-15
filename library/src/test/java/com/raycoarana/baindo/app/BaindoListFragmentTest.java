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
public class BaindoListFragmentTest extends UnitTestSuite {

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

    private BaindoListFragment mBaindoListFragment;
    private View mTargetView;

    @Test
    public void shouldInstanceBaindoDelegateOnCreate() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldDelegateBind() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenBind();
        thenBindIsDelegated();
    }

    @Test
    public void shouldDelegateUnbind() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenUnbind();
        thenUnbindIsDelegated();
    }

    @Test
    public void shouldDelegateOnStart() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnStart();
        thenOnStartIsDelegated();
    }

    @Test
    public void shouldDelegateOnResume() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnResume();
        thenOnResumeIsDelegated();
    }

    @Test
    public void shouldDelegateOnPause() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnPause();
        thenOnPauseIsDelegated();
    }

    @Test
    public void shouldDelegateOnStop() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnStop();
        thenOnStopIsDelegated();
    }

    @Test
    public void shouldDelegateOnAttach() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnAttach();
        thenOnAttachIsDelegated();
    }

    @Test
    public void shouldDelegateOnSaveInstanceState() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnSaveInstanceState();
        thenOnSaveInstanceStateIsDelegated();
    }
    
    @Test
    public void shouldDestroyBaindoOnDestroy() {
        givenABinderDelegate();
        givenABaindoListFragment();
        whenOnCreate();
        whenOnDestroy();
        thenBinderDelegateIsDestroyed();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenFindViewBeforeOnCreateView() {
        givenABaindoListFragment();
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

    private void givenABaindoListFragment() {
        mBaindoListFragment = new BaindoListFragment();
    }

    @SuppressWarnings("ResourceType")
    private void givenACustomBaindoFragment() {
        when(mView.findViewById(SOME_VIEW_ID)).thenReturn(mSomeView);
        mBaindoListFragment = new BaindoListFragment() {
            @Override
            public View getView() {
                return mView;
            }
        };
    }

    private void whenOnCreate() {
        mBaindoListFragment.onCreate(mock(Bundle.class));
    }

    private void whenOnStart() {
        mBaindoListFragment.onStart();
    }

    private void whenOnStop() {
        mBaindoListFragment.onStop();
    }

    private void whenOnPause() {
        mBaindoListFragment.onPause();
    }

    private void whenOnResume() {
        mBaindoListFragment.onResume();
    }

    private void whenOnAttach() {
        when(mNewActivity.getIntent()).thenReturn(mNewIntent);
        mBaindoListFragment.onAttach(mNewActivity);
    }

    private void whenOnSaveInstanceState() {
        mBaindoListFragment.onSaveInstanceState(mState);
    }
    
    private void whenOnDestroy() {
        mBaindoListFragment.onDestroy();
    }

    private void whenBind() {
        mBaindoListFragment.bind();
    }

    private void whenUnbind() {
        mBaindoListFragment.unbind();
    }

    private void whenFindViewById() {
        mTargetView = mBaindoListFragment.findViewById(SOME_VIEW_ID);
    }

    private void thenANewBinderDelegateIsCreated() {
        PowerMockito.verifyStatic();
        Baindo.buildBinderDelegate();
    }

    private void thenBinderDelegateIsDestroyed() {
        verify(mBinderDelegate).onDestroy();
    }

    private void thenBindIsDelegated() {
        verify(mBinderDelegate).bind(mBaindoListFragment);
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
