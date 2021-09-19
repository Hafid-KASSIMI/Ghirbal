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

package ghirbal;

import static ghirbal.GLOBAL_SHARES.MASSAR_WORKBOOK;
import static ghirbal.GLOBAL_SHARES.PREF_BUNDLE;
import static ghirbal.GLOBAL_SHARES.WINDOWS_CURRENT_STATUS;
import ghirbal.engine.MASSARDb;
import ghirbal.util.WINDOWS_SERVER;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DbSelector {
    
    private Stage stage;
    private final FileChooser fc;
    
    public DbSelector() {
        stage = null;
        fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 2003", "*.xls"));
        WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_SELECTOR);
    }
    
    public void show(Window window, ResourceBundle rb) {
        fc.setTitle(rb.getString("CHOOSE_DB_TITLE"));
        if ( PREF_BUNDLE.get("DONT_SHOW_SELECT_DB_TIP").equals("1") ) {
            select(window);
        }
        else {
            if ( stage == null ) {
                try {
                    FXMLLoader fl = new FXMLLoader(getClass().getResource("/ghirbal/views/dbSelector.fxml"), rb);
                    Parent root = fl.load();
                    Scene scene = new Scene(root);
                    scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.initOwner(window);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setResizable(false);
                    stage.getIcons().addAll(((Stage) window).getIcons());
                    stage.show();
                    stage.setOnHidden(evt -> {
                        select(window);
                    });
                }
                catch(IOException ex) { }
            }
            else
                stage.show();
        }
    }            
    
    public void hide() {
        stage.hide();
    }
    
    public void select(Window parent) {
        File ini_db = new File(PREF_BUNDLE.get("SELECTED_DB"));
        File ini_db_dir = new File(PREF_BUNDLE.get("SELECTED_DB_DIR"));
        if ( ini_db_dir.exists() && ini_db_dir.isDirectory() )
            fc.setInitialDirectory(ini_db_dir);
        File f = fc.showOpenDialog(parent);
        if ( f == null ) {
            WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_SELECTOR_ABORT);
            return;
        }
        if ( f.getParentFile() != ini_db_dir )
            PREF_BUNDLE.update("SELECTED_DB_DIR", f.getParent());
        MASSAR_WORKBOOK = new MASSARDb();
        if ( MASSAR_WORKBOOK.setWorkbook(f) ) {
            if ( f != ini_db )
                PREF_BUNDLE.update("SELECTED_DB", f.getPath());
            WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_SELECTOR_DONE);
        }
        else {
            WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DB_SELECTOR_INCOMPATIBLE);
        }
    }
}
