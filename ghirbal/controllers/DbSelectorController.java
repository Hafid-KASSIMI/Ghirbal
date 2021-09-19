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

import static ghirbal.GLOBAL_SHARES.PREF_BUNDLE;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class DbSelectorController implements Initializable {

    @FXML private CheckBox dontShowAgainCB;
    @FXML private Button okBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dontShowAgainCB.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("DONT_SHOW_SELECT_DB_TIP", cur ? "1" : "0");
        });
        
        okBtn.setOnAction(evt -> {
            okBtn.getScene().getWindow().hide();
        });
    }    
    
}
