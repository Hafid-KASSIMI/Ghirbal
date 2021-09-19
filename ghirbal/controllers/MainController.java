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

import org.sicut.util.Misc;
import ghirbal.*;
import static ghirbal.GLOBAL_SHARES.*;
import ghirbal.engine.*;
import ghirbal.util.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

    @FXML private FlowPane dbsFP;
    @FXML private ComboBox<SchoolDb> dbsCmB, dbsCmB1, dbsCmB2;
    @FXML private TextField nameTF, birthDateTF, massarTF;
    @FXML private CheckBox maleCB, femaleCB;
    @FXML private ComboBox<Operation> ageOpCmB;
    @FXML private Spinner<Integer> ageSp;
    @FXML private ComboBox<String> levelCmB, levelCmB1, levelCmB2;
    @FXML private Button addDbBtn, deleteDbsBtn;
    @FXML private Label foundNameLbl, foundMassarLbl, foundGirlsLbl, foundLbl, foundNumeroLbl;
    @FXML private Label foundBirthLbl, foundAgeLbl, foundGirlLbl, foundBoyLbl, foundGrpLbl, foundDbLbl, currentLbl;
    @FXML private Button nextBtn, lastBtn, prevBtn, firstBtn;
    @FXML private Label foundNameLbl1, foundMassarLbl1, foundGirlsLbl1, foundLbl1, foundNumeroLbl1;
    @FXML private Label foundBirthLbl1, foundAgeLbl1, foundGirlLbl1, foundBoyLbl1, foundGrpLbl1, foundDbLbl1, currentLbl1;
    @FXML private Button nextBtn1, lastBtn1, prevBtn1, firstBtn1;
    @FXML private Label foundNameLbl2, foundMassarLbl2, foundGirlsLbl2, foundLbl2, foundNumeroLbl2;
    @FXML private Label foundBirthLbl2, foundAgeLbl2, foundGirlLbl2, foundBoyLbl2, foundGrpLbl2, foundDbLbl2, currentLbl2;
    @FXML private Button nextBtn2, lastBtn2, prevBtn2, firstBtn2;
    @FXML private MenuItem quitMI, reloadMI, forgetMI, aboutMI;
    @FXML private CheckMenuItem saveCMI, use2DbsCMI, dbSelectTipCMI;
    @FXML private VBox singleDbResultVB, doubleDbsResultVB, dbsSelectVB, levsSelectVB;
    
    private static final DbLoader DBL = new DbLoader();
    private static final DbSelector DBS = new DbSelector();
    private static final About ABOUT = new About();
    private ComboBox<SchoolDb>[] dbsCombos;
    private SpinnerValueFactory<Integer> spSFD;
    private MeetFinder finder, finder1, finder2;

    public MainController() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        dbsCombos = new ComboBox[]{ dbsCmB, dbsCmB1, dbsCmB2 };
        finder = new MeetFinder(dbsCmB, nameTF, birthDateTF, massarTF, maleCB, femaleCB, ageOpCmB, ageSp, levelCmB);
        finder.setDisplayLabels(foundNameLbl, foundMassarLbl, foundBirthLbl, foundAgeLbl, foundGrpLbl, foundNumeroLbl, foundGirlLbl, foundBoyLbl, foundDbLbl, foundGirlsLbl, foundLbl);
        finder.setNavigationBar(firstBtn, lastBtn, prevBtn, nextBtn, currentLbl);
        finder.setRb(rb);
        finder1 = new MeetFinder(dbsCmB1, nameTF, birthDateTF, massarTF, maleCB, femaleCB, ageOpCmB, ageSp, levelCmB1);
        finder1.setDisplayLabels(foundNameLbl1, foundMassarLbl1, foundBirthLbl1, foundAgeLbl1, foundGrpLbl1, foundNumeroLbl1, foundGirlLbl1, foundBoyLbl1, foundDbLbl1, foundGirlsLbl1, foundLbl1);
        finder1.setNavigationBar(firstBtn1, lastBtn1, prevBtn1, nextBtn1, currentLbl1);
        finder1.setRb(rb);
        finder2 = new MeetFinder(dbsCmB2, nameTF, birthDateTF, massarTF, maleCB, femaleCB, ageOpCmB, ageSp, levelCmB2);
        finder2.setDisplayLabels(foundNameLbl2, foundMassarLbl2, foundBirthLbl2, foundAgeLbl2, foundGrpLbl2, foundNumeroLbl2, foundGirlLbl2, foundBoyLbl2, foundDbLbl2, foundGirlsLbl2, foundLbl2);
        finder2.setNavigationBar(firstBtn2, lastBtn2, prevBtn2, nextBtn2, currentLbl2);
        finder2.setRb(rb);
        
        spSFD = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        TextFormatter spFormatter = new TextFormatter(spSFD.getConverter(), spSFD.getValue());
        ageSp.setValueFactory(spSFD);
        ageSp.getEditor().setTextFormatter(spFormatter);
        spSFD.valueProperty().bindBidirectional(spFormatter.valueProperty());
        
        ageOpCmB.getItems().add(new GreaterThanOperation(rb.getString("GREATER_THAN")));
        ageOpCmB.getItems().add(new LesserThanOperation(rb.getString("LESSER_THAN")));
        ageOpCmB.getItems().add(new EqualsOperation(rb.getString("EQUALS")));
        ageOpCmB.getSelectionModel().selectFirst();
        
        WINDOWS_CURRENT_STATUS.addListener((ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            switch ( newValue.intValue() ) {
                case WINDOWS_SERVER.DB_LOADER_DONE:
                    DBL.hide();
                    DATABASES.put(MASSAR_WORKBOOK.getSchoolUId(), MASSAR_WORKBOOK.getDb());
                    showLoadedDatabases(rb);
                    break;
                case WINDOWS_SERVER.DB_SELECTOR_DONE:
                    dbSelectTipCMI.setSelected("0".equals(PREF_BUNDLE.get("DONT_SHOW_SELECT_DB_TIP")));
                    DBL.show(addDbBtn.getScene().getWindow(), rb);
                    DBL.startLoading();
                    break;
                case WINDOWS_SERVER.DB_SELECTOR_ABORT:
                    dbSelectTipCMI.setSelected("0".equals(PREF_BUNDLE.get("DONT_SHOW_SELECT_DB_TIP")));
                    WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DEATH_POINT);
                    break;
                case WINDOWS_SERVER.DB_SELECTOR_INCOMPATIBLE:
                    dbSelectTipCMI.setSelected("0".equals(PREF_BUNDLE.get("DONT_SHOW_SELECT_DB_TIP")));
                    WINDOWS_CURRENT_STATUS.set(WINDOWS_SERVER.DEATH_POINT);
                    break;
            }
        });
        
        addDbBtn.setOnAction(evt -> {
            DBS.show(addDbBtn.getScene().getWindow(), rb);
        });
        
        deleteDbsBtn.setOnAction(evt -> {
            DATABASES.clear();
            showLoadedDatabases(rb);
        });
        dbsFP.getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
            deleteDbsBtn.setDisable(dbsFP.getChildren().isEmpty());
        });
        
        massarTF.disableProperty().bindBidirectional(nameTF.disableProperty());
        massarTF.disableProperty().bindBidirectional(birthDateTF.disableProperty());
        massarTF.disableProperty().bindBidirectional(femaleCB.disableProperty());
        massarTF.disableProperty().bindBidirectional(maleCB.disableProperty());
        massarTF.disableProperty().bindBidirectional(ageOpCmB.disableProperty());
        massarTF.disableProperty().bindBidirectional(ageSp.disableProperty());
        
        finder.listen();
        
        singleDbResultVB.visibleProperty().addListener((obs, old, cur) -> {
            if ( old != null ) {
                doubleDbsResultVB.setVisible(old);
                dbsSelectVB.setVisible(cur);
                levsSelectVB.setVisible(cur);
            }
        });
        singleDbResultVB.managedProperty().bind(singleDbResultVB.visibleProperty());
        doubleDbsResultVB.managedProperty().bind(doubleDbsResultVB.visibleProperty());
        
        doubleDbsResultVB.setVisible(false);
        singleDbResultVB.setVisible(!PREF_BUNDLE.get("PERFORM_DOUBLE_DBS_SEARCH").equals("1"));
        use2DbsCMI.selectedProperty().addListener((obs, old, cur) -> {
            if ( cur ) {
                PREF_BUNDLE.update("PERFORM_DOUBLE_DBS_SEARCH", "1");
                singleDbResultVB.setVisible(false);
                finder.ignore();
                finder1.listen();
                finder2.listen();
            }
            else {
                PREF_BUNDLE.update("PERFORM_DOUBLE_DBS_SEARCH", "0");
                singleDbResultVB.setVisible(true);
                finder.listen();
                finder1.ignore();
                finder2.ignore();
            }
        });
        use2DbsCMI.setSelected("1".equals(PREF_BUNDLE.get("PERFORM_DOUBLE_DBS_SEARCH")));
        
        quitMI.setOnAction(evt -> {
            addDbBtn.getScene().getWindow().hide();
        });
        
        saveCMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("SERIALIZE_DBS_ON_CLOSE", cur ? "1" : "0");
        });
        saveCMI.setSelected("1".equals(PREF_BUNDLE.get("SERIALIZE_DBS_ON_CLOSE")));
        
        reloadMI.setOnAction(evt -> {
            loadSavedDbs(rb);
        });
        reloadMI.disableProperty().bind(forgetMI.disableProperty());

        forgetMI.setOnAction(evt -> {
            try {
                Files.delete(Paths.get(GLOBAL_SHARES.DB_FOLDER_PATH, PREF_BUNDLE.get("SERIALIZED_DB_NAME") + GLOBAL_SHARES.EXTENSION));
                forgetMI.setDisable(true);
            } catch (IOException ex) { }
        });
        forgetMI.setDisable(true);
        
        aboutMI.setOnAction(evt -> {
            ABOUT.show(addDbBtn.getScene().getWindow(), rb);
        });
        
        dbSelectTipCMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("DONT_SHOW_SELECT_DB_TIP", cur ? "0" : "1");
        });
        dbSelectTipCMI.setSelected("0".equals(PREF_BUNDLE.get("DONT_SHOW_SELECT_DB_TIP")));
        
        loadSavedDbs(rb);
    }
    
    private void loadSavedDbs(ResourceBundle rb) {
        try {
            GLOBAL_SHARES.DATABASES.putAll((HashMap<Integer, SchoolDb>)Misc.eval(GLOBAL_SHARES.DB_FOLDER_PATH + PREF_BUNDLE.get("SERIALIZED_DB_NAME") + GLOBAL_SHARES.EXTENSION));
            showLoadedDatabases(rb);
            forgetMI.setDisable(false);
        } catch( NullPointerException ex ) {}
    }
    
    private void showLoadedDatabases(ResourceBundle rb) {
        dbsFP.getChildren().clear();
        dbsCmB.getItems().clear();
        dbsCmB.getItems().add(ALL_SCHOOLS);
        DATABASES.forEach((key, db) -> {
            dbsFP.getChildren().add(new DbContainer(db, rb).get());
            dbsCmB.getItems().add(db);
        });        
        dbsCmB.getSelectionModel().selectFirst();
        for ( int i = 1; i < 3; i++ ) {
            dbsCombos[i].getItems().clear();
            dbsCombos[i].getItems().addAll(dbsCmB.getItems());
            dbsCombos[i].getSelectionModel().selectFirst();
        }
    }
    
}
