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

package com.raycoarana.baindo.sample.text.view;

import android.os.Bundle;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.text.viewmodel.ViewModel;

public class TextActivity extends BaindoActivity {

    private final ViewModel mViewModel = new ViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        bindViews();
    }

    private void bindViews() {
        bind().text().of(R.id.source).to(mViewModel.RawMessage).readWrite();
        bind().text().of(R.id.raw_destination).to(mViewModel.RawMessage).readOnly();
        bind().text().of(R.id.manipulated_destination).to(mViewModel.ManipulatedMessage).readOnly();
    }

}
