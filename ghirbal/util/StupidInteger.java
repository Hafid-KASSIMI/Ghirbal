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

package ghirbal.util;

public class StupidInteger {
    
    private int i;

    public StupidInteger() {
    }

    public StupidInteger(int i) {
        this.i = i;
    }

    public int get() {
        return i;
    }

    public void increment() {
        ++i;
    }

    public void decrement() {
        --i;
    }

    public void set(int i) {
        this.i = i;
    }
}
