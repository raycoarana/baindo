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

import android.widget.Checkable;
import android.widget.CompoundButton;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractProperty;

public class CheckedBind extends BaseObservableBind<AbstractProperty<Boolean>> implements CompoundButton.OnCheckedChangeListener {

    public CheckedBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
        doInBackgroundThread(() -> {
            synchronized (this) {
                if(state == State.BINDED) {
                    mTarget.setValue(isChecked);
                }
            }
        });

    }

    @Override
    protected void bindView() {
        Checkable view = getView();
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setOnCheckedChangeListener(this);
        } else {
            throw new IllegalStateException(String.format("Check binder doesn't supports %s in WRITE or READ_WRITE modes",
                                                          view.getClass().getName()));
        }
    }

    @Override
    protected void unbindView() {
        Checkable view = getView();
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setOnCheckedChangeListener(null);
        }
    }

    @Override
    protected void updateView() {
        Checkable view = getView();
        view.setChecked(mTarget.getValue() != null ? mTarget.getValue() : false);
    }

    private Checkable getView() {
        return (Checkable) mView;
    }

}
