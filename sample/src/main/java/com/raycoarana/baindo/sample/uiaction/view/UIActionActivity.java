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
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ViewGroup;

import com.raycoarana.baindo.app.BaindoActivity;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.sample.R;
import com.raycoarana.baindo.sample.uiaction.viewmodel.ViewModel;

public class UIActionActivity extends BaindoActivity {

    private ViewModel mViewModel = new ViewModel();
    private TransitionManager mTransitionManager;
    private Scene mCurrent;
    private Scene mNormalScene;
    private Scene mFavoriteScene;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiaction_normal);

        initializeAnimations();
        bindViews();
    }

    private void initializeAnimations() {
        ViewGroup contentView = (ViewGroup) findViewById(R.id.content);
        initializeScenes(contentView);
        initializeTransitionManager(contentView);
    }

    private void initializeScenes(ViewGroup contentView) {
        mNormalScene = Scene.getSceneForLayout(contentView, R.layout.activity_uiaction_normal, this);
        mFavoriteScene = Scene.getSceneForLayout(contentView, R.layout.activity_uiaction_favorite, this);
    }

    private void initializeTransitionManager(ViewGroup contentView) {
        TransitionInflater transitionInflater = TransitionInflater.from(this);
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.favorite_transition_manager, contentView);
    }

    private void bindViews() {
        bind().uiAction(mFavoriteUIAction).to(mViewModel.IsFavorite);
        bind().click().of(R.id.favorite).to(mViewModel.ToggleFavorite);
        bind().text().of(R.id.name).to(mViewModel.Name).readOnly();
    }

    private UIAction<Boolean> mFavoriteUIAction = (Boolean isFavorite) -> {
            //Do any UI action in response to a ViewModel's property change
            Scene scene = isFavorite ? mFavoriteScene : mNormalScene;
            transitionTo(scene);
    };

    private void transitionTo(Scene scene) {
        if(scene != mCurrent) {
            boolean mustRebind = mCurrent != null;
            mCurrent = scene;
            mTransitionManager.transitionTo(scene);
            if(mustRebind) {
                unbind();
                bindViews();
            }
        }
    }

}
