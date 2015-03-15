/*
 * Copyright 2015 Rayco Araña
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.raycoarana.baindo.events;

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.WorkDispatcherHelper;
import com.raycoarana.baindo.test.UnitTestSuite;
import com.raycoarana.baindo.viewmodel.Command;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class AbstractEventBindTest extends UnitTestSuite {

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private Command mCommand;

    private OnCreateEventBind mBind;

    @Test
    public void shouldExecuteCommandWhenEventIsFired() {
        givenAnEventBind();
        givenThatCommandIsBindedToEvent();
        whenEventIsFired();
        thenCommandIsExecuted();
    }

    private void givenAnEventBind() {
        WorkDispatcherHelper.setup(mWorkDispatcher);
        mBind =  new OnCreateEventBind(mWorkDispatcher);
    }

    private void givenThatCommandIsBindedToEvent() {
        mBind.to(mCommand);
    }

    private void whenEventIsFired() {
        mBind.execute();
    }

    private void thenCommandIsExecuted() {
        verify(mCommand).execute();
    }

}
