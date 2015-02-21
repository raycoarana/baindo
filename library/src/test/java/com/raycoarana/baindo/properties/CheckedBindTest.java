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
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Observer;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CheckedBindTest extends UnitTestSuite {

    private static final boolean IS_CHECKED = true;
    private static final boolean DEFAULT_VALUE = false;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private BindableSource mBindableSource;

    @Mock
    private AbstractProperty<Boolean> mProperty;

    @Captor
    private ArgumentCaptor<Observer> mObserverArgumentCaptor;

    @Captor
    private ArgumentCaptor<CompoundButton.OnCheckedChangeListener> mOnCheckedChangeListenerArgumentCaptor;

    private CheckedBind mCheckedBind;
    private View mView;

    @Test
    public void shouldUpdatePropertyWhenCheckedChanges() {
        givenACheckedBind();
        givenACheckBoxView();
        givenThatCheckedBindIsBindedAsReadWrite();
        whenCheckedChanged();
        thenPropertyIsChanged();
    }

    @Test
    public void shouldUpdateViewWhenPropertyChanges() {
        givenACheckedBind();
        givenACheckBoxView();
        givenThatCheckedBindIsBindedAsReadWrite();
        whenPropertyUpdated();
        thenViewIsChanged();
    }

    @Test
    public void shouldNotUpdatePropertyOnceUnbinded() {
        givenACheckedBind();
        givenACheckBoxView();
        givenThatCheckedBindIsBindedAsReadWrite();
        givenThatIsUnbinded();
        whenCheckedChanged();
        thenPropertyIsNotChanged();
    }

    @Test
    public void shouldNotTryToUnbindViewWhenNotACompoundButton() {
        givenACheckedBind();
        givenACheckedView();
        givenThatCheckedBindIsBindedAsReadOnly();
        whenUnbind();
        //Should not fail
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryingToBindToIncompatibleView() {
        givenACheckedBind();
        givenSomeView();
        givenThatCheckedBindIsBindedAsReadWrite();
    }

    @Test
    public void shouldNotUpdatePropertyIfReadOnly() {
        givenACheckedBind();
        givenACheckBoxView();
        givenThatCheckedBindIsBindedAsReadOnly();
        whenCheckedChanged();
        thenPropertyIsNotChanged();
    }

    @Test
    public void shouldNotUpdateViewIfWriteOnly() {
        givenACheckedBind();
        givenACheckBoxView();
        givenThatCheckedBindIsBindedAsWriteOnly();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotCompoundButtonAndBindIsWrite() {
        givenACheckedBind();
        givenACheckedView();
        givenThatCheckedBindIsBindedAsWriteOnly();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotCompoundButtonAndBindIsReadWrite() {
        givenACheckedBind();
        givenACheckedView();
        givenThatCheckedBindIsBindedAsReadWrite();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    private void givenACheckedBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mCheckedBind = new CheckedBind(mBindableSource, mWorkDispatcher);
    }

    private void givenACheckBoxView() {
        mView = mock(CheckBox.class);
    }

    private void givenSomeView() {
        mView = mock(View.class);
    }

    private void givenACheckedView() {
        mView = mock(CheckedTextView.class);
    }

    private void givenThatCheckedBindIsBindedAsReadWrite() {
        mCheckedBind.of(mView).to(mProperty).readWrite();
    }

    private void givenThatCheckedBindIsBindedAsReadOnly() {
        mCheckedBind.of(mView).to(mProperty).readOnly();
    }

    private void givenThatCheckedBindIsBindedAsWriteOnly() {
        mCheckedBind.of(mView).to(mProperty).writeOnly();
    }

    private void givenThatIsUnbinded() {
        mCheckedBind.unbind();
    }

    private void whenCheckedChanged() {
        verify(((CompoundButton) mView), atMost(2)).setOnCheckedChangeListener(mOnCheckedChangeListenerArgumentCaptor.capture());
        if (mOnCheckedChangeListenerArgumentCaptor.getAllValues().size() > 0) {
            CompoundButton.OnCheckedChangeListener listener = mOnCheckedChangeListenerArgumentCaptor.getValue();
            if (listener != null) {
                listener.onCheckedChanged((CompoundButton) mView, IS_CHECKED);
            }
        }
    }

    private void whenPropertyUpdated() {
        verify(mProperty, atMost(1)).addObserver(mObserverArgumentCaptor.capture());
        if(mObserverArgumentCaptor.getAllValues().size() > 0) {
            when(mProperty.getValue()).thenReturn(true);
            Observer observer = mObserverArgumentCaptor.getValue();
            observer.update(mProperty, IS_CHECKED);
        }
    }

    private void whenUnbind() {
        givenThatIsUnbinded();
    }

    private void thenPropertyIsChanged() {
        verify(mProperty).setValue(IS_CHECKED, mCheckedBind);
    }

    private void thenViewIsChanged() {
        verify(((CheckBox) mView)).setChecked(DEFAULT_VALUE);
        verify(((CheckBox) mView)).setChecked(IS_CHECKED);
    }

    private void thenViewIsNotChanged() {
        verify(((CheckBox) mView), never()).setChecked(DEFAULT_VALUE);
        verify(((CheckBox) mView), never()).setChecked(IS_CHECKED);
    }

    private void thenPropertyIsNotChanged() {
        verify(mProperty, never()).setValue(IS_CHECKED, mCheckedBind);
    }

}
