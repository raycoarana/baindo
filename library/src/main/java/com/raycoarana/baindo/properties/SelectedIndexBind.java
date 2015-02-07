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

import android.view.View;
import android.widget.AdapterView;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractProperty;

public class SelectedIndexBind extends BaseObservableBind<AbstractProperty<Integer>> implements AdapterView.OnItemSelectedListener {

    public SelectedIndexBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    @Override
    protected void bindView() {
        AdapterView adapterView = getView();
        adapterView.setOnItemSelectedListener(this);
    }

    @Override
    protected void unbindView() {
        AdapterView adapterView = getView();
        adapterView.setOnItemSelectedListener(null);
    }

    @Override
    protected void updateView() {
        AdapterView adapterView = getView();
        Integer selectedIndex = mTarget.getValue();
        adapterView.setSelection(selectedIndex != null ? selectedIndex : AdapterView.INVALID_POSITION);
    }

    private AdapterView getView() {
        return (AdapterView) mView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        onItemSelected(null);
    }

    public void onItemSelected(Integer item) {
        doInBackgroundThread(() -> {
            synchronized (this) {
                if (state == State.BINDED) {
                    mTarget.setValue(item, SelectedIndexBind.this);
                }
            }
        });
    }

}