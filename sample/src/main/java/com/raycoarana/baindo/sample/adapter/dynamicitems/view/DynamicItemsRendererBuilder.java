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

import com.raycoarana.baindo.renderer.BinderRenderer;
import com.raycoarana.baindo.renderer.BinderRendererBuilder;
import com.raycoarana.baindo.sample.adapter.dynamicitems.viewmodel.ItemViewModel;

import java.util.HashMap;

public class DynamicItemsRendererBuilder extends BinderRendererBuilder<ItemViewModel> {

    public DynamicItemsRendererBuilder() {
        super(new HashMap<Class<? extends ItemViewModel>, BinderRenderer<ItemViewModel>>(){{
            put(ItemViewModel.class, new DynamicItemsRenderer());
        }});
    }

}
