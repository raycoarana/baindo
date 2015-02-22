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

import android.os.Bundle;

import com.raycoarana.baindo.Baindo;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Baindo.class, BinderDelegate.class})
public class BaindoListFragmentTest extends UnitTestSuite {

    @Mock
    private BinderDelegate mBinderDelegate;

    private BaindoListFragment mBaindoListFragment;

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

    private void givenABinderDelegate() {
        PowerMockito.mockStatic(Baindo.class);
        PowerMockito.when(Baindo.buildBinderDelegate()).thenReturn(mBinderDelegate);
    }

    private void givenABaindoListFragment() {
        mBaindoListFragment = new BaindoListFragment();
    }

    private void whenOnCreate() {
        mBaindoListFragment.onCreate(mock(Bundle.class));
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
        mBaindoListFragment.findViewById(anyInt());
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

}
