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

package com.raycoarana.baindo.sample.uiaction.view;

import android.os.Bundle;
import android.widget.ImageView;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.uiaction.viewmodel.ViewModel;

public class UIActionActivity extends BaindoActivity {

    private ViewModel mViewModel = new ViewModel();
    private ImageView mImageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiaction);

        bindViews();
    }

    private void bindViews() {
        mImageView = (ImageView) findViewById(R.id.image);

        bind().progress().of(R.id.transparency).to(mViewModel.TransparencyPercent).writeOnly();
        bind().uiAction(mImageTransparencyControl).to(mViewModel.Transparency);
    }

    private UIAction<Float> mImageTransparencyControl = new UIAction<Float>() {
        @Override
        public void onUpdate(Float value) {
            //Do any UI action in response to a ViewModel's property change
            mImageView.setAlpha(value);
        }
    };

}
