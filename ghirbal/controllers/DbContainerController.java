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

import ghirbal.engine.SchoolDb;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DbContainerController implements Initializable {
    
    @FXML private Label year, size, school, direction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setDb(SchoolDb db, ResourceBundle rb) {
        int all = db.getStudentsCount();
        if ( all == 0 ) {
            size.setText(rb.getString("ZERO_STUDENT"));
        }
        else if ( all == 1 ) {
            size.setText(rb.getString("ONE_STUDENT"));
        }
        else if ( all == 2 ) {
            size.setText(rb.getString("TWO_STUDENTS"));
        }
        else if ( all <= 10 ) {
            size.setText(all + " " + rb.getString("STUDENTS_UNDER_10"));
        }
        else if ( all % 100 == 0 ) {
            size.setText(all + " " + rb.getString("STUDENTS_DIV_100"));
        }
        else {
            size.setText(all + " " + rb.getString("STUDENTS"));
        }
        school.setText(db.getSchool());
        direction.setText(db.getDirection());
        year.setText(db.getYear());
    }
    
}
