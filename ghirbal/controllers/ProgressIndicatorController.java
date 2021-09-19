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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ProgressIndicatorController implements Initializable {

    @FXML private ProgressIndicator pi;
    @FXML private Label value;
    @FXML private Label tick;
    @FXML private final SimpleBooleanProperty done;

    public ProgressIndicatorController() {
        done = new SimpleBooleanProperty(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pi.progressProperty().addListener((ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            value.setText(String.format("%02.02f", newValue.doubleValue() * 100) + "%");
            if (newValue.doubleValue() == 1) {
                tick.setVisible(true);
                value.setVisible(false);
            }
            else {
                tick.setVisible(false);
                value.setVisible(true);
            }
        });
    }
    
    public void setProgress(double progress) {
        pi.setProgress(progress);
        done.set(progress == 1);
    }
    
    public void setVisible(Boolean visible) {
        pi.getParent().setVisible(visible);
    }
    
    public void reset() {
        tick.setVisible(false);
        value.setVisible(false);
        pi.setProgress(0);
    }

    public SimpleBooleanProperty doneProperty() {
        return done;
    }
    
}
