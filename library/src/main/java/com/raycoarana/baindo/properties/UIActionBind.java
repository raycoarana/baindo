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

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.observables.AbstractProperty;

public class UIActionBind<T> extends BaseObservableBind<AbstractProperty<T>> {

    private final UIAction<T> mUIAction;

    public UIActionBind(UIAction<T> uiAction, WorkDispatcher workDispatcher) {
        super(null, workDispatcher, FINAL_BIND_LEVEL);
        mUIAction = uiAction;
        mBindWay = BindWay.READ_ONLY;
    }

    @Override
    protected void bindView() {
        //Nothing to do
    }

    @Override
    protected void unbindView() {
        //Nothing to do
    }

    @Override
    protected void updateView() {
        mUIAction.onUpdate(mTarget.getValue());
    }

}
