/*
 * Copyright (C) 2021 H. KASSIMI
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ghirbal.controllers;

import ghirbal.GLOBAL_SHARES;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {
    
    @FXML private Label appName, buildInfos;

    public AboutController() {
        
    }
    
    public void initialize() {
        appName.setText(GLOBAL_SHARES.APP_TITLE + " (" + GLOBAL_SHARES.APP_YEAR + ")");
        buildInfos.setText(buildInfos.getText().replace("%version%", GLOBAL_SHARES.APP_VERSION).replace("%date%", GLOBAL_SHARES.APP_DATE));
    }
    
}
