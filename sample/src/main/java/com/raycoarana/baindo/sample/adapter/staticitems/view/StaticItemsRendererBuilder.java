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

import com.raycoarana.baindo.renderer.BinderRenderer;
import com.raycoarana.baindo.renderer.BinderRendererBuilder;

import java.util.HashMap;

public class StaticItemsRendererBuilder extends BinderRendererBuilder<String> {

    public StaticItemsRendererBuilder() {
        super(new HashMap<Class<? extends String>, BinderRenderer<String>>(){{
            put(String.class, new StaticItemsRenderer());
        }});
    }

}
