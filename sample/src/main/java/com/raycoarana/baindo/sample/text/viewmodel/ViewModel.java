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

package com.raycoarana.baindo.sample.text.viewmodel;

import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.Property;

/**
 * In this example we see how to receive data from the UI, do some process to it and display
 * the transformed data. The process of the value is done in the background thread automatically.
 */
public class ViewModel {

    private String mRawMessage;
    public AbstractProperty<CharSequence> RawMessage = new AbstractProperty<CharSequence>() {

        @Override
        public CharSequence getValue() {
            return mRawMessage;
        }

        @Override
        public void onValueChanged(CharSequence newValue) {
            mRawMessage = newValue.toString();
            doHardWork();
            ManipulatedMessage.setValue(mRawMessage);
        }

    };

    private void doHardWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    public Property<CharSequence> ManipulatedMessage = new Property<>();

}
