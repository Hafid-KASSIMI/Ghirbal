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

import ghirbal.util.Operation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SchoolDb implements Serializable {
    private final ConcurrentHashMap<String, Level> levels;
    private String school, direction, academy, year;
    private int studentsCount;
    
    public SchoolDb() {
        levels = new ConcurrentHashMap();
    }
    
    public void reset() {
        levels.clear();
        school = direction = academy = year = "";
    }
    
    public ArrayList<Level> getLevels() {
        return new ArrayList(levels.values());
    }
    
    public Level getLevel(String levelName) {
        return levels.get(levelName);
    }
    
    public Level addLevel(String levelName) {
        if ( levels.containsKey(levelName) )
            return levels.get(levelName);
        Level lev = new Level(levelName);
        levels.put(levelName, lev);
        return lev;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public ArrayList<Student> findStudent(String code, String fullName, String birthDate, int age, Operation ageOp, short gender, String levelName){
        ArrayList<Student> stus = new ArrayList();
        if ( "*".equals(levelName) ) {
            levels.forEach((key, level) -> {
                level.getGroups().forEach(group -> {
                    stus.addAll(group.getStudents().stream().filter(student -> (student.meets(code, fullName, birthDate, age, ageOp, gender))).collect(Collectors.toList()));
                });
            });
        }
        else if ( levels.containsKey(levelName) ) {
            levels.get(levelName).getGroups().forEach(group -> {
                stus.addAll(group.getStudents().stream().filter(student -> (student.meets(code, fullName, birthDate, age, ageOp, gender))).collect(Collectors.toList()));
            });
        }
        return stus;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    @Override
    public String toString() {
        return school + " - " + direction + " [" + year + "]";
    }
    
    
}
