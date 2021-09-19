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

import ghirbal.engine.FakeSchoolDb;
import ghirbal.engine.MASSARDb;
import ghirbal.engine.SchoolDb;
import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;
import org.sicut.db.Preferences;
import org.sicut.util.EnvVariable;

public final class GLOBAL_SHARES {
    public static final String APP_TITLE = "غربـــال";
    public static final String APP_YEAR = "2021";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_DATE = "18 سبتمبر 2021";
    public static final String DB_FOLDER_PATH = EnvVariable.APPDATADirectory() + "/Ghirbal/";
    public static final String EXTENSION = ".ghirbal";
    public static final String PREF_DB_NAME = "preferences" + EXTENSION;
    public static final String PREF_DB_PATH = DB_FOLDER_PATH + PREF_DB_NAME;

    public static final SimpleIntegerProperty WINDOWS_CURRENT_STATUS = new SimpleIntegerProperty();
    public static MASSARDb MASSAR_WORKBOOK;
    public static final HashMap<Integer, SchoolDb> DATABASES = new HashMap();
    public static final SchoolDb ALL_SCHOOLS = new FakeSchoolDb();
    
    public static Preferences PREF_BUNDLE;
}
