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

import ghirbal.controllers.DbContainerController;
import ghirbal.engine.SchoolDb;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DbContainer {

    private VBox p;
    
    public DbContainer(SchoolDb db, ResourceBundle rb) {
        try {
            FXMLLoader fl = new FXMLLoader(getClass().getResource("/ghirbal/views/dbContainer.fxml"));
            p = fl.load();
            ((DbContainerController)fl.getController()).setDb(db, rb);
        }
        catch ( IOException ex ) {
        }
    }
    
    public Node get(){
        return p;
    }
}
