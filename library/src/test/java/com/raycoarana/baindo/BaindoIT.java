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

import com.raycoarana.baindo.test.IntegrationTestSuite;

import org.junit.Test;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@Config(emulateSdk = 18)
public class BaindoIT extends IntegrationTestSuite {

    @Test
    public void shouldProvideBinderDelegate() {
        BinderDelegate binderDelegate = Baindo.buildBinderDelegate();
        assertNotNull(binderDelegate);
    }

    @Test
    public void shouldProvideWorkDispatcher() {
        WorkDispatcher workDispatcher = Baindo.buildWorkDispatcher();
        assertNotNull(workDispatcher);
    }

}