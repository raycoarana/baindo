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

package com.raycoarana.baindo.state;

import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class ValueSaverFactoryTest extends UnitTestSuite {

    private ValueSaverFactory mValueSaverFactory;
    private ValueSaver<?> mValueSaver;

    @Test
    public void shouldBuildAValueSaverForKnownType() {
        givenAValueSaverFactory();
        whenBuildWithAKnownType();
        thenAValueSaverIsCreated();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailBuildAValueSaverForUnknownType() {
        givenAValueSaverFactory();
        whenBuildWithAUnknownType();
    }

    private void givenAValueSaverFactory() {
        mValueSaverFactory = ValueSaverFactory.getInstance();
    }

    private void whenBuildWithAKnownType() {
        mValueSaver = mValueSaverFactory.build(42);
    }

    private void whenBuildWithAUnknownType() {
        mValueSaver = mValueSaverFactory.build(new SomeClass());
    }

    private void thenAValueSaverIsCreated() {
        assertNotNull(mValueSaver);
    }

    private static class SomeClass {}

}
