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

package com.pedrogomez.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raycoarana.baindo.renderer.BinderRenderer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RendererTestHelper {

    @SuppressWarnings("unchecked")
    public static <T> Renderer<T> build(RendererBuilder<T> rendererBuilder,
                                            T content,
                                            View convertView,
                                            ViewGroup parent,
                                            LayoutInflater layoutInflater) {
        return rendererBuilder.withContent(content)
                              .withConvertView(convertView)
                              .withParent(parent)
                              .withLayoutInflater(layoutInflater)
                              .build();
    }

    public static void render(Renderer renderer) {
        renderer.render();
    }

    public static void mockRenderer(Renderer renderer) {
        when(renderer.copy()).thenReturn(mock(BinderRenderer.class));
    }
}
