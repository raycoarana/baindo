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

package com.raycoarana.baindo.properties;

import android.view.View;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Observer;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class AbstractBindTest<T, Bind extends BaseBind<T, AbstractProperty<T>>> extends UnitTestSuite {

    protected final T mDefaultValue;
    protected final T mNewValue;

    @Mock
    protected WorkDispatcher mWorkDispatcher;

    @Mock
    protected BindableSource mBindableSource;

    @Mock
    protected AbstractProperty<T> mProperty;

    @Captor
    private ArgumentCaptor<Observer> mObserverArgumentCaptor;

    protected Bind mBaseBind;
    protected View mView;
    private boolean mWriteNotSupported;

    public AbstractBindTest(T defaultValue, T newValue, boolean writeSupported) {
        mDefaultValue = defaultValue;
        mNewValue = newValue;
        mWriteNotSupported = !writeSupported;
    }

    @Before
    public void setUp() {
        super.setUp();

        when(mProperty.getValue()).thenReturn(mDefaultValue);
    }

    @Test
    public void shouldUpdateViewWhenPropertyChanges() {
        givenABaseBind();
        givenACompatibleView();
        if(mWriteNotSupported) {
            givenThatBindIsBindedAsReadOnly();
        } else {
            givenThatBindIsBindedAsReadWrite();
        }
        whenPropertyUpdated();
        thenViewIsChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryingToBindToIncompatibleView() {
        givenABaseBind();
        givenSomeView();
        givenThatBindIsBindedAsReadWrite();
    }

    @Test
    public void shouldNotUpdateViewIfWriteOnly() {
        givenABaseBind();
        givenACompatibleView();
        try {
            givenThatBindIsBindedAsWriteOnly();
            whenPropertyUpdated();
            thenViewIsNotChanged();
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage(), mWriteNotSupported);
        }
    }

    protected void givenABaseBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mBaseBind = getBind();
    }

    protected abstract Bind getBind();

    private void givenSomeView() {
        mView = mock(View.class);
    }

    protected void givenThatBindIsBindedAsReadWrite() {
        mBaseBind.of(mView).to(mProperty).readWrite();
    }

    protected void givenThatBindIsBindedAsReadOnly() {
        mBaseBind.of(mView).to(mProperty).readOnly();
    }

    protected void givenThatBindIsBindedAsWriteOnly() {
        mBaseBind.of(mView).to(mProperty).writeOnly();
    }

    protected void givenThatIsUnbinded() {
        mBaseBind.unbind();
    }

    protected abstract void givenACompatibleView();

    protected void whenPropertyUpdated() {
        verify(mProperty, atMost(1)).addObserver(mObserverArgumentCaptor.capture());
        if(mObserverArgumentCaptor.getAllValues().size() > 0) {
            when(mProperty.getValue()).thenReturn(mNewValue);
            Observer observer = mObserverArgumentCaptor.getValue();
            observer.update(mProperty, mNewValue);
        }
    }

    private void thenViewIsChanged() {
        verifyViewChanged(mDefaultValue);
        verifyViewChanged(mNewValue);
    }

    protected void thenViewIsNotChanged() {
        verifyViewNotChanged(mDefaultValue);
        verifyViewNotChanged(mNewValue);
    }

    protected abstract void verifyViewChanged(T value);
    protected abstract void verifyViewNotChanged(T value);

}
