/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.engcpp.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.util.Arrays.stream;
import org.jfree.io.FileUtilities;

/**
 *
 * @author engcpp
 */
public class FileUtils {        
    public static Matrix loadData(String filename) throws FileNotFoundException {        
        BufferedReader reader = new BufferedReader(new FileReader(FileUtilities.findFileOnClassPath(filename)));
         
        double[][] m = reader.lines()
            .map(l->stream(l.split(",")).mapToDouble(Double::parseDouble).toArray())
            .toArray(double[][]::new);
        
        return new Matrix(m);                
    }
}
