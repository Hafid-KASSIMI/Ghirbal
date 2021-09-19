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

import java.util.function.BiFunction;

public abstract class Operation {
    
    private String symbol;
    private BiFunction<Integer, Integer, Boolean> function;
    
    public Operation() {
    }

    public Operation(String symbol) {
        this.symbol = symbol;
    }

    public Operation(BiFunction<Integer, Integer, Boolean> function) {
        this.function = function;
    }

    public Operation(String symbol, BiFunction<Integer, Integer, Boolean> function) {
        this.symbol = symbol;
        this.function = function;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BiFunction<Integer, Integer, Boolean> getFunction() {
        return function;
    }

    public void setFunction(BiFunction<Integer, Integer, Boolean> function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return symbol;
    }
    
}
