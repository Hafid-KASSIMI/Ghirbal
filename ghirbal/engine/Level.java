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

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
    private final ArrayList<Group> groups;
    private String name;
    private Integer weight;

    public Level() {
        groups = new ArrayList();
        weight = -1;
    }
    
    public Level(String name) {
        this();
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Group addGroup(String groupName){
        Group grp = new Group(groupName, name);
        groups.add(grp);
        if ( weight == -1 ) {
            weight = "1A2A3ATC1B2B".indexOf(grp.getInitials());
        }
        return grp;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getGroupsCount() {
        return groups.size();
    }
    
    public ArrayList<Group> getGroups() {
        return groups;
    }
    
    public Group getGroup(String groupName) {
        for( Group g : groups ) {
            if ( g.getName().equalsIgnoreCase(groupName) )  return g;
        }
        return null;
    }
    
    public Group getGroup(int index) {
        if ( index >= groups.size() ) {
            return null;
        }
        return groups.get(index);
    }
    
    public Integer getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name;
    }
}
