/*
 * Copyright 2015 Rayco Araña
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.raycoarana.baindo.sample.state.view;

import android.os.Bundle;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.state.viewmodel.ViewModel;

public class StateActivity extends BaindoActivity {

    private static final String STATE_NAME = "viewmodel_name";

    private ViewModel mViewModel = new ViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        bindState();
        bindViews();
    }

    private void bindState() {
        bind().<String>stateWithKey(STATE_NAME).to(mViewModel.Name);
    }

    private void bindViews() {
        bind().<String>text().of(R.id.name).to(mViewModel.Name).readWrite();
        bind().click().of(R.id.move_out).to(mViewModel.MoveOutCommand);
    }
    
}
