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

package com.raycoarana.baindo.sample.adapter.dynamicitems.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raycoarana.baindo.renderer.BinderRenderer;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.adapter.dynamicitems.viewmodel.ItemViewModel;

public class DynamicItemsRenderer extends BinderRenderer<ItemViewModel> {

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindViews() {
        ItemViewModel viewModel = getContent();
        bind().click().of(getRootView()).to(viewModel.ShowCommand);
        bind().text().of(getRootView()).to(viewModel.Ticks).readOnly();
    }

}
