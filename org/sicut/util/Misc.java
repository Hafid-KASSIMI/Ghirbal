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

package org.sicut.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

public final class Misc {
    
    public static String getLevelAbrev(String groupName) {
        return groupName.substring(0, groupName.lastIndexOf("-"));
    }
    
    public static String cleanReversedAR(String text) {
        return text.replace("(", "%OP%").replace("[", "%OB%").replace("{", "%OC%").replace(")", "(").replace("]", "[").replace("}", "{").replace("%OP%", ")").replace("%OB%", "]").replace("%OC%", "}");
    }
    
    public static String removeShaklAndMadd(String arText) {
        return arText.replaceAll("[ًٌٍَُِّْـ]", "");
    }
    
    public static Boolean isArabic(String text) {
        return text.matches(".*[أبجدهـوزحطيكلمنسعفصقرشتثخذضظغ].*");
    }
    
    public static int generateLocalUId() {
        Properties p = System.getProperties();
        return Math.abs((p.getProperty("os.name") + p.getProperty("os.arch") + 
        p.getProperty("os.version") + p.getProperty("user.name")).hashCode());
    }
    
    public static Boolean serialize(Object o, String path) {
        try {
            (new ObjectOutputStream(new FileOutputStream(path))).writeObject(o);
            return true;
        } catch (IOException ex) { }
        return false;
    }
    
    public static Object eval(String path) {
        Object o = null;
        try {
            return (new ObjectInputStream(new FileInputStream(path))).readObject();
        } catch (IOException | ClassNotFoundException ex) { }
        return o;
    }
}
