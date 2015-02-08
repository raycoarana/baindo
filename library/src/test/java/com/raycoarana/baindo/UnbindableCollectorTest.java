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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class UnbindableCollectorTest extends UnitTestSuite {

    @Mock
    private Unbindable mSomeUnbindableObject;

    private UnbindableCollector mUnbindableCollector;
    private Unbindable mUnbindableObject;

    @Test
    public void shouldReturnTheSameObjectWhenCollect() {
        givenAnUnbindableCollector();
        whenCollectSomeUnbindable();
        thenTheSameObjectIsReturnted();
    }

    @Test
    public void shouldUnbindAllCollectedObjects() {
        givenAnUnbindableCollector();
        givenThatSomeUnbindableObjectIsCollected();
        whenUnbindAndReleaseAll();
        thenTheUnbindableObjectIsUnbinded();
    }

    @Test
    public void shouldReleaseCollectedObjects() {
        givenAnUnbindableCollector();
        givenThatSomeUnbindableObjectIsCollected();
        whenUnbindAndReleaseAllTwice();
        thenTheUnbindableObjectIsUnbinded();
    }

    private void givenAnUnbindableCollector() {
        mUnbindableCollector = new UnbindableCollector();
    }

    private void givenThatSomeUnbindableObjectIsCollected() {
        whenCollectSomeUnbindable();
    }

    private void whenCollectSomeUnbindable() {
        mUnbindableObject = mUnbindableCollector.collect(mSomeUnbindableObject);
    }

    private void whenUnbindAndReleaseAll() {
        mUnbindableCollector.unbindAndReleaseAll();
    }

    private void whenUnbindAndReleaseAllTwice() {
        whenUnbindAndReleaseAll();
        whenUnbindAndReleaseAll();
    }

    private void thenTheSameObjectIsReturnted() {
        assertThat(mSomeUnbindableObject, is(mUnbindableObject));
    }

    private void thenTheUnbindableObjectIsUnbinded() {
        verify(mSomeUnbindableObject, once()).unbind();
    }

}
