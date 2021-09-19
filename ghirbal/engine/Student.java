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

import ghirbal.util.GENDER_SELECTOR;
import org.sicut.util.Misc;
import ghirbal.util.Operation;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public final class Student implements Serializable {
    private Integer num;
    private String firName, secName, gender, address, code, group, birthDate, cleanFirName, cleanSecName;
    private Long age;
    private int schoolId;

    public Student(Integer num, String firName, String secName, String gender, String address, String code, String birthDate) {
        this.num = num;
        this.firName = firName;
        this.secName = secName;
        cleanFirName = Misc.removeShaklAndMadd(firName);
        cleanSecName = Misc.removeShaklAndMadd(secName);
        this.gender = gender;
        this.address = address;
        this.code = code;
        setBirthDate(birthDate);
    }

    public Student() {
        num = 0;
        firName = "";
        secName = "";
        cleanFirName = "";
        cleanSecName = "";
        gender = "";
        address = "";
        code = "";
        birthDate = "";
        age = 0L;
    }

    public Integer getNum() {
        return num;
    }

    public String getFormattedNum() {
        return ( num > 9 ? "" : "0" ) + num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFirName() {
        return firName;
    }

    public void setFirName(String firName) {
        this.firName = firName;
        cleanFirName = Misc.removeShaklAndMadd(firName);
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
        cleanSecName = Misc.removeShaklAndMadd(secName);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        LocalDate dt;
        try {
            dt = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
            age = ChronoUnit.YEARS.between(dt, LocalDate.now());
        } catch ( DateTimeParseException ex ) {
            dt = LocalDate.now();
            age = 0L;
        }
        this.birthDate = dt.format(DateTimeFormatter.ofPattern("uuuu/MM/dd"));
    }

    public Long getAge() {
        return age;
    }
    
    public Boolean isGirl() {
        return gender.matches("(F)|(Female)|(Fille)|(أنثى)");
    }
    
    public boolean meets(String code, String fullName, String birthDate, int age, Operation ageOp, short gender){
        boolean res = true;
        if ( !code.isEmpty() ) {
            if ( !this.code.toLowerCase().contains(code.toLowerCase().replaceAll(" ", "")) ) 
                return false;
        }
        String[] arr = Misc.removeShaklAndMadd(fullName).split(" ");
        String srcName = cleanFirName + cleanSecName;
        for (String name : arr) {
            if ( srcName.contains(name) ) {
                srcName = srcName.replaceFirst(name, "");
            }
            else {
                return false;
            }
        }
        arr = birthDate.split(" ");
        for (String dt : arr) {
            if ( !this.birthDate.contains(dt) ) 
                return false;
        }
        if ( age > 0 ) {
            if ( !ageOp.getFunction().apply(this.age.intValue(), age) ) 
                return false;
        }
        return ( gender == GENDER_SELECTOR.ALL || ( gender == GENDER_SELECTOR.GIRLS_ONLY && isGirl() ) || 
               ( gender == GENDER_SELECTOR.BOYS_ONLY && !isGirl() ) );
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
