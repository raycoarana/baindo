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

import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BinderDelegateTest extends UnitTestSuite {

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private BaindoBinderFactory mBaindoBinderFactory;

    @Mock
    private BindableSource mSomeBindableSource;

    @Mock
    private UnbindableCollector mUnbindableCollector;

    @Mock
    private UnbindableCollector mRendererUnbindableCollection;

    @Mock
    private LifecycleBinderCollector mLifecycleBinderCollector;

    private BinderDelegate mBinderDelegate;
    private Binder mBind;

    @Test
    public void shouldCreateAndUnbind() {
        givenABinderDelegate();
        givenThatABindWasCreated();
        whenUnbind();
        thenTheCreatedBindIsUnbind();
    }

    @Test
    public void shouldUnbindOnDestroy() {
        givenABinderDelegate();
        givenThatABindWasCreated();
        whenOnDestroy();
        thenTheCreatedBindIsUnbind();
    }

    @Test
    public void shouldDestroyWorkDispatcherOnDestroy() {
        givenABinderDelegate();
        whenOnDestroy();
        thenWorkDispatcherIsDestroyed();
    }

    @Test
    public void shouldBindRendererWithoutCallUnbindOnDestroy() {
        givenABinderDelegate();
        givenThatABindWithUnbindableCollectorWasCreated();
        whenOnDestroy();
        thenTheCreatedIsNotUnbinded();
    }

    private void givenABinderDelegate() {
        mBinderDelegate = new BinderDelegate(mBaindoBinderFactory, mWorkDispatcher, mUnbindableCollector, mLifecycleBinderCollector);
    }

    private void givenThatABindWasCreated() {
        mBind = mBinderDelegate.bind(mSomeBindableSource);
    }

    private void givenThatABindWithUnbindableCollectorWasCreated() {
        mBind = mBinderDelegate.bind(mSomeBindableSource, mRendererUnbindableCollection);
    }

    private void whenOnDestroy() {
        mBinderDelegate.onDestroy();
    }

    private void whenUnbind() {
        mBinderDelegate.unbind();
    }

    private void thenTheCreatedBindIsUnbind() {
        verify(mUnbindableCollector).unbindAndReleaseAll();
    }

    private void thenTheCreatedIsNotUnbinded() {
        verify(mUnbindableCollector, never()).collect((Unbindable) mBind);
    }

    private void thenWorkDispatcherIsDestroyed() {
        verify(mWorkDispatcher).onDestroy();
    }

}
