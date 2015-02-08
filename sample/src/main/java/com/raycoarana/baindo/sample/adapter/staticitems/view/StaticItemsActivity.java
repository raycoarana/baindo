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

package com.raycoarana.baindo.sample.adapter.staticitems.view;

import android.os.Bundle;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.adapter.staticitems.viewmodel.ViewModel;

public class StaticItemsActivity extends BaindoActivity {

    private StaticItemsAdapterFactory mStaticItemsAdapterFactory;
    private ViewModel mViewModel = new ViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_item_adapter);

        initialize();
        bindViews();
    }

    private void initialize() {
        mStaticItemsAdapterFactory = new StaticItemsAdapterFactory(getLayoutInflater());
    }

    private void bindViews() {
        bind().click().of(R.id.add).to(mViewModel.AddCommand);
        bind().click().of(R.id.remove).to(mViewModel.RemoveCommand);
        bind().enabled().of(R.id.remove).to(mViewModel.CanRemoveItems).readOnly();
        bind().adapterWithFactory(mStaticItemsAdapterFactory).of(R.id.spinner).to(mViewModel.Items);
        bind().selectedIndex().of(R.id.spinner).to(mViewModel.SelectedItemIndex).readWrite();
        bind().text().of(R.id.selected).to(mViewModel.SelectedItem).readOnly();
    }

}
