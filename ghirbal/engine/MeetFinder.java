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
import ghirbal.util.GENDER_SELECTOR;
import ghirbal.util.Operation;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class MeetFinder {
    
    private ComboBox<SchoolDb> dbsCmB;
    private TextField nameTF, birthDateTF, massarTF;
    private CheckBox maleCB, femaleCB;
    private ComboBox<Operation> ageOpCmB;
    private Spinner<Integer> ageSp;
    private ComboBox<String> levelCmB;
    
    private Button firstBtn, lastBtn, prevBtn, nextBtn;
    private final ArrayList<Student> meet;
    private Label currentLbl, foundNameLbl, foundMassarLbl, foundBirthLbl, foundAgeLbl;
    private Label foundGrpLbl, foundNumeroLbl, foundGirlLbl, foundBoyLbl, foundDbLbl, foundGirlsLbl, foundLbl;
    private ResourceBundle rb;
    
    private final SimpleIntegerProperty currentMeet = new SimpleIntegerProperty();
    private ChangeListener cl1, cl2;
    private short selectedGender;
    
    public MeetFinder() {
        meet = new ArrayList();
        cl1 = (ChangeListener) (ObservableValue observable, Object oldValue, Object newValue) -> {
            findMeets();
        };
        cl2 = (ChangeListener) (ObservableValue observable, Object oldValue, Object newValue) -> {
            refreshSelectedGender();
        };
        currentMeet.addListener((obs, oldValue, newValue) -> {
            int cur = newValue.intValue();
            if ( !meet.isEmpty() ) {
                Student s = meet.get(cur);
                firstBtn.setDisable(cur == 0 );
                lastBtn.setDisable( cur == meet.size() - 1 );
                currentLbl.setText(( ++cur < 10 ? "0" : "" ) + cur);
                foundNameLbl.setText(s.getFirName() + " " + s.getSecName());
                foundMassarLbl.setText(s.getCode());
                foundBirthLbl.setText(s.getBirthDate());
                foundAgeLbl.setText(putAgeString(rb, s.getAge()));
                foundGirlLbl.managedProperty().bind(foundGirlLbl.visibleProperty());
                foundBoyLbl.managedProperty().bind(foundBoyLbl.visibleProperty());
                foundGirlLbl.setVisible(s.isGirl());
                foundBoyLbl.setVisible(!foundGirlLbl.isVisible());
                foundGrpLbl.setText(s.getGroup());
                if ( foundDbLbl != null )
                    foundDbLbl.setText(DATABASES.get(s.getSchoolId()).getSchool() + " (" + 
                        DATABASES.get(s.getSchoolId()).getYear() + ")");
                foundNumeroLbl.setText(s.getFormattedNum());
            }
        });
    }

    public MeetFinder(ComboBox<SchoolDb> dbsCmB, TextField nameTF, TextField birthDateTF, TextField massarTF, CheckBox maleCB, CheckBox femaleCB, ComboBox<Operation> ageOpCmB, Spinner<Integer> ageSp, ComboBox<String> levelCmB) {
        this();
        this.setInputFields(dbsCmB, nameTF, birthDateTF, massarTF, maleCB, femaleCB, ageOpCmB, ageSp, levelCmB);
    }
    
    public final void setInputFields(ComboBox<SchoolDb> dbsCmB, TextField nameTF, TextField birthDateTF, TextField massarTF, CheckBox maleCB, CheckBox femaleCB, ComboBox<Operation> ageOpCmB, Spinner<Integer> ageSp, ComboBox<String> levelCmB) {
        this.dbsCmB = dbsCmB;
        this.nameTF = nameTF;
        this.birthDateTF = birthDateTF;
        this.massarTF = massarTF;
        this.maleCB = maleCB;
        this.femaleCB = femaleCB;
        this.ageOpCmB = ageOpCmB;
        this.ageSp = ageSp;
        this.levelCmB = levelCmB;
        
        this.levelCmB.getSelectionModel().selectedItemProperty().addListener(cl1);
        
        this.dbsCmB.getSelectionModel().selectedItemProperty().addListener((obs, old, cur) -> {
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
                tmpLevels.addAll(cur.getLevels());
            levelCmB.getItems().clear();
            levelCmB.getItems().add("*");
            levelCmB.getItems().addAll(tmpLevels.stream().sorted((lev1, lev2) -> lev1.getWeight().compareTo(lev2.getWeight())).map(lev -> lev.getName()).distinct().collect(Collectors.toList()));
            if ( levelCmB.getItems().contains(selectedLevel) )
                levelCmB.getSelectionModel().select(selectedLevel);
            else
                levelCmB.getSelectionModel().selectFirst();
        });
    }
    
    public void setNavigationBar(Button firstBtn, Button lastBtn, Button prevBtn, Button nextBtn, Label currentLbl) {
        this.firstBtn = firstBtn;
        this.lastBtn = lastBtn;
        this.prevBtn = prevBtn;
        this.nextBtn = nextBtn;
        this.currentLbl = currentLbl;
        this.lastBtn.disableProperty().bindBidirectional(this.nextBtn.disableProperty());
        this.firstBtn.disableProperty().bindBidirectional(this.prevBtn.disableProperty());
        
        this.firstBtn.setOnAction(evt -> {
            currentMeet.set(0);
        });

        this.lastBtn.setOnAction(evt -> {
            currentMeet.set(meet.size() - 1);
        });

        this.nextBtn.setOnAction(evt -> {
            currentMeet.set(currentMeet.get() + 1);
        });

        this.prevBtn.setOnAction(evt -> {
            currentMeet.set(currentMeet.get() - 1);
        });
    }

    public void setDisplayLabels(Label foundNameLbl, Label foundMassarLbl, Label foundBirthLbl, 
            Label foundAgeLbl, Label foundGrpLbl, Label foundNumeroLbl,
            Label foundGirlLbl, Label foundBoyLbl, Label foundDbLbl, Label foundGirlsLbl, Label foundLbl) {
        this.foundNameLbl = foundNameLbl;
        this.foundMassarLbl = foundMassarLbl;
        this.foundBirthLbl = foundBirthLbl;
        this.foundAgeLbl = foundAgeLbl;
        this.foundGrpLbl = foundGrpLbl;
        this.foundNumeroLbl = foundNumeroLbl;
        this.foundGirlLbl = foundGirlLbl;
        this.foundBoyLbl = foundBoyLbl;
        this.foundDbLbl = foundDbLbl;
        this.foundGirlsLbl = foundGirlsLbl;
        this.foundLbl = foundLbl;
    }
    
    public void listen() {
        massarTF.textProperty().addListener(cl1);
        nameTF.textProperty().addListener(cl1);
        birthDateTF.textProperty().addListener(cl1);
        refreshSelectedGender();
        femaleCB.selectedProperty().addListener(cl2);
        maleCB.selectedProperty().addListener(cl2);
        femaleCB.selectedProperty().addListener(cl1);
        maleCB.selectedProperty().addListener(cl1);
        ageOpCmB.getSelectionModel().selectedItemProperty().addListener(cl1);
        ageSp.getValueFactory().valueProperty().addListener(cl1);
        findMeets();
    }
    
    public void ignore() {
        massarTF.textProperty().removeListener(cl1);
        nameTF.textProperty().removeListener(cl1);
        birthDateTF.textProperty().removeListener(cl1);
        femaleCB.selectedProperty().removeListener(cl1);
        maleCB.selectedProperty().removeListener(cl1);
        femaleCB.selectedProperty().removeListener(cl2);
        maleCB.selectedProperty().removeListener(cl2);
        ageOpCmB.getSelectionModel().selectedItemProperty().removeListener(cl1);
        ageSp.getValueFactory().valueProperty().removeListener(cl1);
    }
    
    
    private ArrayList<Student> findMeets(SchoolDb db) {
        return db.findStudent(
            massarTF.getText(), 
            nameTF.getText(), 
            birthDateTF.getText(), 
            ageSp.getValue(), 
            ageOpCmB.getSelectionModel().getSelectedItem(), 
            selectedGender, 
            levelCmB.getSelectionModel().getSelectedItem() == null ? "*" : levelCmB.getSelectionModel().getSelectedItem()
        );
    }
    
    private void findMeets() {
        if ( dbsCmB.getSelectionModel().getSelectedItem() == null )
            return;

        massarTF.setDisable(true);

        meet.clear();
        currentMeet.set(-1);
        if ( dbsCmB.getSelectionModel().getSelectedItem().equals(ALL_SCHOOLS) ) {
            DATABASES.forEach((key, db) -> {
                meet.addAll(findMeets(db));
            });
        }
        else {
            meet.addAll(findMeets(dbsCmB.getSelectionModel().getSelectedItem()));
        }

        if ( meet.isEmpty() ) {
            foundLbl.setText("0");
            foundGirlsLbl.setText("0");
            clear();
        }
        else {
            foundLbl.setText("" + meet.size());
            foundGirlsLbl.setText("" + meet.stream().filter(stu -> stu.isGirl()).count());
        }
        currentMeet.set(0);

        massarTF.setDisable(false);
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }
    
    private void clear() {
        currentLbl.setText(rb.getString("NOTHING_SYMBOL_SMALL"));
        foundNameLbl.setText(rb.getString("NOTHING_SYMBOL_LARGE"));
        foundMassarLbl.setText(rb.getString("NOTHING_SYMBOL_LARGE"));
        foundBirthLbl.setText(rb.getString("NOTHING_SYMBOL_LARGE"));
        foundAgeLbl.setText(rb.getString("NOTHING_SYMBOL_SMALL"));
        foundGirlLbl.setVisible(false);
        foundBoyLbl.setVisible(false);
        if ( foundDbLbl != null )
            foundDbLbl.setText(rb.getString("NOTHING_SYMBOL_LARGE"));
        foundGrpLbl.setText(rb.getString("NOTHING_SYMBOL_LARGE"));
        foundNumeroLbl.setText(rb.getString("NOTHING_SYMBOL_SMALL"));
        firstBtn.setDisable(true);
        lastBtn.setDisable(true);
    }
    
    private String putAgeString(ResourceBundle rb, long age) {
        String s;
        if ( age == 0 ) {
            s = rb.getString("ZERO_YEAR");
        }
        else if ( age == 1 ) {
            s = rb.getString("ONE_YEAR");
        }
        else if ( age == 2 ) {
            s = rb.getString("TWO_YEARS");
        }
        else if ( age <= 10 ) {
            s = age + " " + rb.getString("YEARS_UNDER_10");
        }
        else if ( age % 100 == 0 ) {
            s = age + " " + rb.getString("YEARS_DIV_100");
        }
        else {
            s = age + " " + rb.getString("YEARS");
        }
        return "(" + s + ")";
    }

    private void refreshSelectedGender() {
        if ( femaleCB.isSelected() && !maleCB.isSelected() )
            selectedGender = GENDER_SELECTOR.GIRLS_ONLY;
        else if ( !femaleCB.isSelected() && maleCB.isSelected() )
            selectedGender = GENDER_SELECTOR.BOYS_ONLY;
        else
            selectedGender = GENDER_SELECTOR.ALL;
    }
    
}
