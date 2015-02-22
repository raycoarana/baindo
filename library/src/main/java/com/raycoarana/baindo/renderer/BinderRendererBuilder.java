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
import com.raycoarana.baindo.UnbindableCollectorProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public abstract class BinderRendererBuilder<T> extends RendererBuilder<T> {

    private final Map<Class<? extends T>, ? extends BinderRenderer<T>> mContentToPrototypeMap;
    private HashSet<BinderRenderer> mRenderers = new HashSet<>();
    private View mConvertView;

    @SuppressWarnings("unchecked")
    public BinderRendererBuilder(Map<Class<? extends T>, BinderRenderer<T>> contentToPrototypeMap) {
        super((Collection) contentToPrototypeMap.values());
        mContentToPrototypeMap = contentToPrototypeMap;
    }

    protected void injectDelegate(BinderDelegate binderDelegate) {
        for(BinderRenderer binderRenderer : mContentToPrototypeMap.values()) {
            binderRenderer.injectDelegate(binderDelegate);
        }
    }

    protected void injectUnbindableCollectorProvider(UnbindableCollectorProvider unbindableCollectorProvider) {
        for(BinderRenderer binderRenderer : mContentToPrototypeMap.values()) {
            binderRenderer.injectUnbindableCollectorProvider(unbindableCollectorProvider);
        }
    }

    protected RendererBuilder withConvertView(View convertView) {
        super.withConvertView(convertView);
        this.mConvertView = convertView;
        return this;
    }

    protected Renderer build() {
        BinderRenderer oldRenderer = unbindRenderer(mConvertView);
        BinderRenderer newRenderer = (BinderRenderer) super.build();
        if(oldRenderer != newRenderer) {
            mRenderers.add(newRenderer);
        }
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
    private BinderRenderer unbindRenderer(View convertView) {
        if(convertView != null && convertView.getTag() != null) {
            BinderRenderer renderer = (BinderRenderer) convertView.getTag();
            renderer.unbind();
            return renderer;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void destroy() {
        for(BinderRenderer renderer: mRenderers) {
            renderer.unbind();
        }
    }

}
