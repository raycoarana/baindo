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

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BaseBindTest extends UnitTestSuite {

    @Mock
    private BindableSource mBindableSource;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private Runnable mRunnable;

    private BaseBind mBaseBind;

    @Test
    public void shouldNotDispatchWorkUIThreadIfNotBinded() {
        givenABaseBind();
        whenDispatchWorkToUIThread();
        thenWorkIsNotExecuted();
    }

    private void givenABaseBind() {
        mBaseBind = new BaseBind(mBindableSource, mWorkDispatcher) {
            @Override
            protected void bind() {

            }

            @Override
            protected void onUnbind() {

            }
        };
    }

    private void whenDispatchWorkToUIThread() {
        mBaseBind.doInUIThread(mRunnable);
    }

    private void thenWorkIsNotExecuted() {
        verify(mRunnable, never()).run();
    }

}
