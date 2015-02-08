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

package com.raycoarana.baindo.test;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.times;

public abstract class TestSuite {

    protected static Answer<Object> answerWithFirstArgument() {
        return answerWithArgument(0);
    }

    protected static Answer<Object> answerWithArgument(final int argumentIndex) {
        return new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArguments()[argumentIndex];
            }
        };
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    protected VerificationMode once() {
        return times(1);
    }

}
