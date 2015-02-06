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

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.Unbindable;

public class BinderRendererAdapter<T> extends RendererAdapter<T> implements Unbindable {

    private BinderRendererBuilder mRendererBuilder;

    public BinderRendererAdapter(LayoutInflater layoutInflater, RendererBuilder rendererBuilder, AdapteeCollection<T> collection) {
        super(layoutInflater, rendererBuilder, collection);

        if(rendererBuilder instanceof BinderRendererBuilder) {
            mRendererBuilder = (BinderRendererBuilder) rendererBuilder;
        }
    }

    void injectBinderDelegate(BinderDelegate binderDelegate) {
        mRendererBuilder.injectDelegate(binderDelegate);
    }

    @Override
    public void unbind() {
        mRendererBuilder.destroy();
        mRendererBuilder = null;
    }

}