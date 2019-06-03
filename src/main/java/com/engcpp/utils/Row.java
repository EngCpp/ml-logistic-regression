package com.engcpp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author engcpp
 */
public final class Row<T>{
    private final List columns;
    private final int idx;
    
    public Row(int idx, double[]data) {
        columns = new ArrayList<T>();
        this.idx = idx;
        
        for (double d : data)
            columns.add(d);
    }
    
    public T getColumn(int i){
        return (T)columns.get(i);
    }
    
    public int getRowIndex() {
        return idx;
    }
    
    public int getColumnsCount() {
        return columns.size();
    }
}
