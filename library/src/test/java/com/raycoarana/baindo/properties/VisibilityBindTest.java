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

package com.raycoarana.baindo.properties;

import android.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class VisibilityBindTest extends AbstractBindTest<Boolean, VisibilityBind> {

    private static final boolean NEW_VALUE = true;
    private static final Boolean DEFAULT_VALUE = null;
    private static final boolean DONT_SUPPORT_WRITE = false;

    public VisibilityBindTest() {
        super(DEFAULT_VALUE, NEW_VALUE, DONT_SUPPORT_WRITE);
    }

    @Override
    protected VisibilityBind getBind() {
        return new VisibilityBind(mBindableSource, mWorkDispatcher);
    }

    @Override
    protected void givenACompatibleView() {
        mView = mock(View.class);
    }

    @Override
    protected void verifyViewChanged(Boolean value) {
        value = getValueOrFalse(value);
        verify(mView).setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void verifyViewNotChanged(Boolean value) {
        verify(mView, never()).setVisibility(getValueOrFalse(value) ? View.VISIBLE : View.GONE);
    }

    private Boolean getValueOrFalse(Boolean value) {
        return value != null ? value : false;
    }

}
