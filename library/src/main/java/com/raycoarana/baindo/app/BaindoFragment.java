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

package com.raycoarana.baindo.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.Binder;
import com.raycoarana.baindo.BinderDelegate;

@TargetApi(11)
public class BaindoFragment extends Fragment implements BindableSource {

    private BinderDelegate mBinderDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinderDelegate = new BinderDelegate();
    }

    /**
     * Creates and returns a new Binder ready to bind a view property with a ViewModel property.
     */
    public Binder bind() {
        return mBinderDelegate.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinderDelegate.onDestroy();
    }

    @Override
    public View findViewById(int id) {
        View view = getView();
        if(view == null) {
            throw new IllegalStateException("Can't bind elements before onViewCreated().");
        }

        return view.findViewById(id);
    }

}
