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

package com.raycoarana.baindo.sample.intent.viewmodel;

import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.Property;

public class IntentReceiverViewModel {

    public AbstractProperty<String> Name = new AbstractProperty<String>() {

        @Override
        public void onValueChanged(String newValue) {
            updateHelloMessage();
        }

    };

    public AbstractProperty<Integer> Age = new AbstractProperty<Integer>() {

        @Override
        public void onValueChanged(Integer newValue) {
            updateHelloMessage();
        }

    };

    public Property<String> HelloMessage = new Property<>();
    public Property<String> Action = new Property<>();
    public Property<String> Data = new Property<>();
    public Property<String> Type = new Property<>();

    private void updateHelloMessage() {
        HelloMessage.setValue(String.format("Hi %s! You are %d years old.", Name.getValue(), Age.getValue()));
    }

}
