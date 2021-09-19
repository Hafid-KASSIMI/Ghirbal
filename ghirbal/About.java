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

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class About {

    private Stage stage;
    
    public About() {
        stage = null;
    }
    
    public void show(Window window, ResourceBundle rb) {
        if ( stage == null ) {
            try {
                FXMLLoader fl = new FXMLLoader(getClass().getResource("/ghirbal/views/about.fxml"), rb);
                Parent root = fl.load();
                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.initOwner(window);
                stage.getIcons().addAll(((Stage) window).getIcons());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setResizable(false);
                stage.show();
            }
            catch(IOException ex) {
                System.out.println(ex);
            }
        }
        else
            stage.show();
    }
    
    public void hide() {
        stage.hide();
    }
}
