/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.engcpp.utils;

import java.awt.Color;

/**
 *
 * @author engcpp
 */
public class Serie {
    private String label;
    private Matrix data;
    private Color color;
    private boolean lineVisible;
    private boolean shapeFilled;
    
    public Serie(String label, Matrix data, Color color, boolean lineVisible, boolean shapeFilled) {
        this.label = label;
        this.data = data;
        this.color = color;
        this.lineVisible = lineVisible;
        this.shapeFilled = shapeFilled;
    }

    public Serie(String label, Matrix matrix, Color color) {
        this(label, matrix, color,  false, true);
    }
    
    public Serie(String label, Matrix matrix, Color color, boolean line) {
        this(label, matrix, color,  line, false);
    }    

    public Serie(String not_Admitted, Matrix positive, javafx.scene.paint.Color YELLOWGREEN) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getLabel() {
        return label;
    }
    
    public Matrix getData() {
        return data;
    }

    public Color getColor() {
        return color;
    }

    public boolean isLineVisible() {
        return lineVisible;
    }

    public boolean isShapeFilled() {
        return shapeFilled;
    }

}
