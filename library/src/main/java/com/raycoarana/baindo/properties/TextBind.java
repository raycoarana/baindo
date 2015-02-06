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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractProperty;

public class TextBind extends BaseObservableBind<AbstractProperty<CharSequence>> implements TextWatcher {

    public TextBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void afterTextChanged(final Editable editable) {
        doInBackgroundThread(() -> mTarget.setValue(editable, TextBind.this));
    }

    @Override
    protected void bindView() {
        TextView textView = getView();
        textView.addTextChangedListener(this);
    }

    @Override
    protected void unbindView() {
        TextView textView = getView();
        textView.removeTextChangedListener(this);
    }

    @Override
    protected void updateView() {
        TextView textView = getView();
        textView.setText(mTarget.getValue());
    }

    private TextView getView() {
        return (TextView) mView;
    }

}