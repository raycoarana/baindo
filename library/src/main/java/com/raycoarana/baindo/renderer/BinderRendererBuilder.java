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

import android.view.View;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import com.raycoarana.baindo.BinderDelegate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public abstract class BinderRendererBuilder<T> extends RendererBuilder<T> {

    private final Map<Class<? extends T>, ? extends BinderRenderer<T>> mContentToPrototypeMap;
    private HashSet<Renderer> mRenderers = new HashSet<>();
    private BinderDelegate mBinderDelegate;
    private View convertView;

    @SuppressWarnings("unchecked")
    public BinderRendererBuilder(Map<Class<? extends T>, BinderRenderer<T>> contentToPrototypeMap) {
        super((Collection) contentToPrototypeMap.values());
        mContentToPrototypeMap = contentToPrototypeMap;
    }

    protected void injectDelegate(BinderDelegate binderDelegate) {
        mBinderDelegate = binderDelegate;
    }

    protected RendererBuilder withConvertView(View convertView) {
        super.withConvertView(convertView);
        this.convertView = convertView;
        return this;
    }

    protected Renderer build() {
        unbindRenderer(convertView);
        BinderRenderer newRenderer = (BinderRenderer) super.build();
        newRenderer.injectDelegate(mBinderDelegate);
        mRenderers.add(newRenderer);
        return newRenderer;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    protected Class getPrototypeClass(T content) {
        BinderRenderer<T> prototype = mContentToPrototypeMap.get(content.getClass());
        if(prototype == null) {
            throw new IllegalStateException("No valid prototype found for this content");
        }
        return prototype.getClass();
    }

    @SuppressWarnings("unchecked")
    private void unbindRenderer(View convertView) {
        if(convertView != null && convertView.getTag() != null) {
            Renderer renderer = (Renderer) convertView.getTag();
            renderer.onRecycle(null);
            mRenderers.remove(renderer);
        }
    }

    @SuppressWarnings("unchecked")
    public void destroy() {
        for(Renderer renderer : mRenderers) {
            renderer.onRecycle(null);
        }
    }

}