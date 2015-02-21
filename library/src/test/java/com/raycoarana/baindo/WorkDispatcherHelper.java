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

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public class WorkDispatcherHelper {

    public static void setup(WorkDispatcher workDispatcher) {
        Answer<Object> answer = new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                ((Runnable)invocationOnMock.getArguments()[0]).run();
                return null;
            }

        };

        doAnswer(answer).when(workDispatcher).doInBackgroundThread(any(Runnable.class));
        doAnswer(answer).when(workDispatcher).doInUIThread(any(Runnable.class));
    }

}
