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
import com.raycoarana.baindo.observables.AbstractProperty;

public class EnabledBind extends BaseObservableBind<Boolean, AbstractProperty<Boolean>> {

    public EnabledBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    @Override
    protected void bindView() {
        throw new IllegalStateException("Enabled bind doesn't support WRITE or READ_WRITE mode.");
    }

    @Override
    protected void unbindView() {
    }

    @Override
    protected void updateView() {
        mView.setEnabled(getValueOrFalse());
    }

    protected boolean getValueOrFalse() {
        Boolean value = mTarget.getValue();
        return value != null ? value : false;
    }

}