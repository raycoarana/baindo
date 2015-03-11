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

package com.raycoarana.baindo.sample.intent.view;

import android.os.Bundle;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.intent.viewmodel.IntentReceiverViewModel;

public class IntentReceiverActivity extends BaindoActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_AGE = "age";

    private IntentReceiverViewModel mViewModel = new IntentReceiverViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_receiver);

        bindViews();
    }

    private void bindViews() {
        bind().<String>intentExtraWithKey(EXTRA_NAME).to(mViewModel.Name);
        bind().<String>intentExtraWithKey(EXTRA_AGE).to(mViewModel.Age);
        bind().text().of(R.id.hello).to(mViewModel.HelloMessage).readOnly();
    }

}
