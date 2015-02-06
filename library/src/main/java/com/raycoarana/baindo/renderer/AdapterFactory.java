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

package com.raycoarana.baindo.renderer;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RendererBuilder;

public abstract class AdapterFactory<T> {

    private LayoutInflater mLayoutInflater;
    private RendererBuilder<T> mRendererBuilder;

    public AdapterFactory(LayoutInflater layoutInflater, RendererBuilder<T> rendererBuilder) {
        mLayoutInflater = layoutInflater;
        mRendererBuilder = rendererBuilder;
    }

    public BaseAdapter build(AdapteeCollection<T> collection) {
        return new BinderRendererAdapter<>(mLayoutInflater, mRendererBuilder, collection);
    }

}
