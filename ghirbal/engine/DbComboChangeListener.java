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

package ghirbal.engine;

import static ghirbal.GLOBAL_SHARES.ALL_SCHOOLS;
import static ghirbal.GLOBAL_SHARES.DATABASES;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class DbComboChangeListener implements ChangeListener<SchoolDb> {

    private final ComboBox<SchoolDb> dbsCmB;
    private final ComboBox<String> levelCmB;

    public DbComboChangeListener(ComboBox<SchoolDb> dbsCmB, ComboBox<String> levelCmB) {
        this.dbsCmB = dbsCmB;
        this.levelCmB = levelCmB;
    }
    
    @Override
    public void changed(ObservableValue<? extends SchoolDb> observable, SchoolDb oldValue, SchoolDb newValue) {
         if ( dbsCmB.getSelectionModel().getSelectedItem() == null )
                return;
            String selectedLevel = levelCmB.getSelectionModel().getSelectedItem();
            ArrayList<Level> tmpLevels = new ArrayList();
            if ( dbsCmB.getSelectionModel().getSelectedItem().equals(ALL_SCHOOLS) ) {
                DATABASES.forEach((key, db) -> {
                    tmpLevels.addAll(db.getLevels());
                });
            }
            else
                tmpLevels.addAll(newValue.getLevels());
            levelCmB.getItems().clear();
            levelCmB.getItems().add("*");
            levelCmB.getItems().addAll(tmpLevels.stream().sorted((lev1, lev2) -> lev1.getWeight().compareTo(lev2.getWeight())).map(lev -> lev.getName()).distinct().collect(Collectors.toList()));
            if ( levelCmB.getItems().contains(selectedLevel) )
                levelCmB.getSelectionModel().select(selectedLevel);
            else
                levelCmB.getSelectionModel().selectFirst();
    }
    
}
