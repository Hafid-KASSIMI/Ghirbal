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

import static ghirbal.GLOBAL_SHARES.DB_FOLDER_PATH;
import static ghirbal.GLOBAL_SHARES.PREF_BUNDLE;
import static ghirbal.GLOBAL_SHARES.PREF_DB_PATH;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.sicut.db.Configurator;
import org.sicut.db.Preferences;
import org.sicut.util.Misc;

public class Ghirbal extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            Parent root;
            Scene scene;
            (new Configurator()).prepare(PREF_DB_PATH, DB_FOLDER_PATH, getClass().getResource("/ghirbal/resources/preferences.sql").openStream());
            PREF_BUNDLE = new Preferences(GLOBAL_SHARES.PREF_DB_PATH);
            root = FXMLLoader.load(getClass().getResource("/ghirbal/views/main.fxml"), ResourceBundle.getBundle("ghirbal.resources.i18n.strings", new Locale("ar", "AR")));
            scene = new Scene(root);
            scene.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            stage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/256.png")),
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/128.png")),
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/64.png")),
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/48.png")),
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/32.png")),
                new Image(getClass().getResourceAsStream("/ghirbal/resources/icons/16.png"))
            );
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
            stage.setTitle(GLOBAL_SHARES.APP_TITLE + " (" + GLOBAL_SHARES.APP_YEAR + ")");
            stage.setOnHiding(evt -> {
                if ( !PREF_BUNDLE.get("SERIALIZED_DB_NAME_GENERATED").equals("1") ) {
                    PREF_BUNDLE.update("SERIALIZED_DB_NAME", Misc.generateLocalUId() + "");
                    PREF_BUNDLE.update("SERIALIZED_DB_NAME_GENERATED", "1");
                }
                if ( PREF_BUNDLE.get("SERIALIZE_DBS_ON_CLOSE").equals("1") ) {
                    Misc.serialize(GLOBAL_SHARES.DATABASES, 
                        GLOBAL_SHARES.DB_FOLDER_PATH + PREF_BUNDLE.get("SERIALIZED_DB_NAME") + GLOBAL_SHARES.EXTENSION);
                }
                PREF_BUNDLE.commit();
            });
        } catch( IOException ex ) { }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
