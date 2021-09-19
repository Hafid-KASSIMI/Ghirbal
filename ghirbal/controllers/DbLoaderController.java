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

import static ghirbal.GLOBAL_SHARES.MASSAR_WORKBOOK;
import static ghirbal.GLOBAL_SHARES.WINDOWS_CURRENT_STATUS;
import ghirbal.util.WINDOWS_SERVER;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DbLoaderController implements Initializable {
    
    @FXML private ProgressIndicatorController piController;
    @FXML private Label progressLbl, counterLbl;

    private String loadDone;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_LOADER);
        piController.reset();
        progressLbl.setText(rb.getString("LOAD_PERCENTAGE"));
        counterLbl.setText("0");
        loadDone = rb.getString("LOAD_DONE");
        
    }
    
    public void start() {
        new Thread(() -> {
            MASSAR_WORKBOOK.loadDB();
            MASSAR_WORKBOOK.getStatus().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if ( newValue ) {
                    Platform.runLater(() -> {
                        progressLbl.setText(loadDone);
                        WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_LOADER_DONE);
                    });
                }
            });
            MASSAR_WORKBOOK.getProcessedSheets().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                Platform.runLater(() -> {
                    piController.setProgress((double) newValue);
                    counterLbl.setText(MASSAR_WORKBOOK.getCurrentStudent() + "");
                });
            });
        }).start();
    }
    
}
