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
import com.raycoarana.baindo.test.UnitTestSuite;
import com.raycoarana.baindo.viewmodel.Command;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class ClickToCommandBindTest extends UnitTestSuite {

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private BindableSource mBindableSource;

    @Mock
    private View mView;

    @Mock
    private Command mCommand;

    @Captor
    private ArgumentCaptor<View.OnClickListener> onClickListenerArgumentCaptor;

    private ClickToCommandBind mBind;

    @Test
    public void shouldExecuteCommandWhenViewClicked() {
        givenAClickToCommandBind();
        givenThatCommandIsBindedToView();
        whenViewIsClicked();
        thenCommandIsExecuted();
    }

    @Test
    public void shouldUnbindView() {
        givenAClickToCommandBind();
        givenThatCommandIsBindedToView();
        whenUnbind();
        thenViewIsUnbind();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryingToSetADirectionOfBindingReadOnly() {
        givenAClickToCommandBind();
        givenThatCommandIsBindedToViewReadOnly();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryingToSetADirectionOfBindingWriteOnly() {
        givenAClickToCommandBind();
        givenThatCommandIsBindedToViewWriteOnly();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryingToSetADirectionOfBindingReadWrite() {
        givenAClickToCommandBind();
        givenThatCommandIsBindedToViewReadWrite();
    }

    protected void givenAClickToCommandBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mBind =  new ClickToCommandBind(mBindableSource, mWorkDispatcher);
    }

    private void givenThatCommandIsBindedToView() {
        mBind.of(mView).to(mCommand);
    }

    private void givenThatCommandIsBindedToViewReadOnly() {
        mBind.of(mView).to(mCommand).readOnly();
    }

    private void givenThatCommandIsBindedToViewWriteOnly() {
        mBind.of(mView).to(mCommand).writeOnly();
    }

    private void givenThatCommandIsBindedToViewReadWrite() {
        mBind.of(mView).to(mCommand).readWrite();
    }

    private void whenViewIsClicked() {
        verify(mView).setOnClickListener(onClickListenerArgumentCaptor.capture());
        onClickListenerArgumentCaptor.getValue().onClick(mView);
    }

    private void thenCommandIsExecuted() {
        verify(mCommand).execute();
    }

    private void whenUnbind() {
        mBind.unbind();
    }

    private void thenViewIsUnbind() {
        verify(mView).setOnClickListener(null);
    }

}
