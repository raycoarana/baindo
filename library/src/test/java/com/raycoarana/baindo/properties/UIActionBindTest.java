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

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Observer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UIActionBindTest extends UnitTestSuite {

    private static final String NEW_VALUE = "new_value";

    @Mock
    private UIAction<Object> mUIAction;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private AbstractProperty<Object> mProperty;

    @Captor
    private ArgumentCaptor<Observer> mObserverArgumentCaptor;

    private UIActionBind<Object, Object> mUIActionBind;

    @Test
    public void shouldExecuteUIActionWhenPropertyChanges() {
        givenAUIActionBind();
        givenThatUIActionIsBindedToAProperty();
        whenPropertyChanges();
        thenUIActionIsExecuted();
    }

    @Test
    public void shouldUnbindUIAction() {
        givenAUIActionBind();
        givenThatUIActionIsBindedToAProperty();
        whenUnbind();
        thenPropertyIsUnbinded();
    }

    private void givenAUIActionBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mUIActionBind = new UIActionBind<>(mUIAction, mWorkDispatcher);
    }

    private void givenThatUIActionIsBindedToAProperty() {
        mUIActionBind.to(mProperty);
    }

    private void whenPropertyChanges() {
        verify(mProperty).addObserver(mObserverArgumentCaptor.capture());
        when(mProperty.getValue()).thenReturn(NEW_VALUE);
        mObserverArgumentCaptor.getValue().update(mProperty, NEW_VALUE);
    }

    private void whenUnbind() {
        mUIActionBind.unbind();
    }

    private void thenUIActionIsExecuted() {
        verify(mUIAction).onUpdate(NEW_VALUE);
    }

    private void thenPropertyIsUnbinded() {
        verify(mProperty).deleteObserver(mUIActionBind);
    }

}
