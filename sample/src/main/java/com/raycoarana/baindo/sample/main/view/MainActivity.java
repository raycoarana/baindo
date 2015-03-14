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

package com.raycoarana.baindo.sample.main.view;

import android.os.Bundle;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.main.viewmodel.ViewModel;

public class MainActivity extends BaindoActivity {

    private ViewModel mViewModel = new ViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
    }

    private void bindViews() {
        bind().click().of(R.id.click_sample).to(mViewModel.OpenClickSampleCommand);
        bind().click().of(R.id.text_sample).to(mViewModel.OpenTextSampleCommand);
        bind().click().of(R.id.check_sample).to(mViewModel.OpenCheckSampleCommand);
        bind().click().of(R.id.progress_sample).to(mViewModel.OpenProgressSampleCommand);
        bind().click().of(R.id.uiaction_sample).to(mViewModel.OpenUIActionSampleCommand);
        bind().click().of(R.id.static_items_adapter_sample).to(mViewModel.OpenStaticItemsAdapterSampleCommand);
        bind().click().of(R.id.dynamic_items_adapter_sample).to(mViewModel.OpenDynamicItemsAdapterSampleCommand);
        bind().click().of(R.id.intent_sample).to(mViewModel.OpenIntentSampleCommand);
        bind().click().of(R.id.state_sample).to(mViewModel.OpenStateSampleCommand);
    }

}