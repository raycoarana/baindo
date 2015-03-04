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

package com.raycoarana.baindo.state.saver;

import android.os.Bundle;

import com.raycoarana.baindo.state.ValueSaver;

public class CharacterArrayValueSaver implements ValueSaver<char[]> {

    @Override
    public void save(Bundle state, String key, char[] value) {
        state.putCharArray(key, value);
    }

}
