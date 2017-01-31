/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2016, FrostWire(R). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dkf.jmule.activities.internal;

import android.app.Fragment;
import android.content.Intent;
import org.dkf.jed2k.android.ConfigurationManager;
import org.dkf.jmule.R;
import org.dkf.jmule.activities.MainActivity;
import org.dkf.jmule.activities.SettingsActivity;
import org.dkf.jmule.activities.WizardActivity;
import org.dkf.jmule.fragments.TransfersFragment;
import org.dkf.jmule.fragments.TransfersFragment.TransferStatus;

/**
 * @author gubatron
 * @author aldenml
 */
public final class MainController {

    private final MainActivity activity;

    public MainController(MainActivity activity) {
        this.activity = activity;
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void closeSlideMenu() {
        activity.closeSlideMenu();
    }


    public void switchFragment(int itemId) {
        Fragment fragment = activity.getFragmentByMenuId(itemId);
        if (fragment != null) {
            activity.switchContent(fragment);
        }
    }

    public void showPreferences() {
        Intent i = new Intent(activity, SettingsActivity.class);
        activity.startActivity(i);
    }

    public void showTransfers(TransferStatus status) {
        if (!(activity.getCurrentFragment() instanceof TransfersFragment)) {
            TransfersFragment fragment = (TransfersFragment) activity.getFragmentByMenuId(R.id.menu_main_transfers);
            //fragment.selectStatusTab(status);
            switchFragment(R.id.menu_main_transfers);
        }
    }

    public void startWizardActivity() {
        ConfigurationManager.instance().resetToDefaults();
        Intent i = new Intent(activity, WizardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }
}
