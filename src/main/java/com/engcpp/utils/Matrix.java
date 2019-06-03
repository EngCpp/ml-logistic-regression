package com.engcpp.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int n) {        
        this(new double[n][n]);
    }
       
    public Matrix(int rows, int cols) {        
        this(new double[rows][cols]);
    }

    public Matrix(double...dt) {        
        this.cols = 1;
        this.rows = dt.length;
        this.data = new double[rows][cols];
        for (int row=0; row<rows; row++)
            this.data[row][0] = dt[row];
    }
    
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }        

    public Matrix addColumn(final Function<Row<Double>, Double> mapfunction) {
        double[][] response = new double[rows][cols+1];
        
        for (int row=0; row < rows; row++)            
            for (int col=0; col < cols+1; col++)  
                response[row][col] = (col == cols) ? 
                    mapfunction.apply(new Row(row, data[row])) : data[row][col];
        
        return new Matrix(response);        
    }

    public Matrix apply(Predicate<Integer>rowPredicate, Predicate<Integer>colPredicate,
            Function<Double, Double> mapfunction) {
        
        if (colPredicate == null)
            colPredicate = i->i==0;
        
        if (rowPredicate == null)
            rowPredicate = i->true;
        
        double[][] response = new double[rows][cols];
                        
        for (int row=0; row < rows; row++)
            for (int col=0; col < cols; col++)
                response[row][col] = colPredicate.test(col) && rowPredicate.test(row)? 
                    mapfunction.apply(data[row][col]) : data[row][col];
        
        return new Matrix(response);        
    }
            
    public Matrix applyToCol(int column, Function<Double, Double> mapfunction) {               
        return apply(null, col->col==column, mapfunction);        
    }
    
    public Matrix filterByCol(final int column, Predicate<Double>predicate) {
        List<double[]>filtered =
        Arrays.asList(data)
              .stream()
              .filter(d->predicate.test(d[column]))
              .collect(Collectors.toList());
        return new Matrix(filtered.toArray(new double[filtered.size()][cols]));
    }
    
    public double sum(int col) {
        double total = 0;
    
        for (int row=0; row < rows; row++)
            total += data[row][col];
            
        return total;
    }
    
    public Matrix(Matrix m, Matrix n) {
        this.cols = m.cols + n.cols;
        this.rows  = m.rows;
        this.data = new double[rows][cols];
        
        for (int row=0; row < rows; row++) {
            for (int col1=0; col1<m.cols; col1++)
                data[row][col1] = m.get(row, col1);
            
            for (int col2=0; col2<n.cols; col2++)
                data[row][m.cols + col2] = n.get(row, col2);
        }        
    }        
    
    public int rowsCount(){
        return rows;
    }

    public int colsCount(){
        return cols;
    }
    
    public double min(int column){
        double minVal = Double.MAX_VALUE;
        
        for (int row=0; row < rows; row++) 
           minVal = Math.min(minVal, data[row][column]);
        
        return minVal;
    }
    
    public double max(int column){
        double maxVal = Double.MIN_VALUE;
        
        for (int row=0; row < rows; row++) 
           maxVal = Math.max(maxVal, data[row][column]);
        
        return maxVal;
    }    
    
    public Matrix fillWith(double val) {
        double[][] b = new double[rows][cols];
        
        for (int row=0; row < rows; row++) 
            for (int col=0; col<cols; col++) 
                b[row][col] = val;
        
        return new Matrix(b);
    }
    
    public Matrix dropColumn(int pos) {
          double[][] b = new double[rows][cols-1];
        
        for (int row=0; row < rows; row++)
            for (int col=0; col < cols; col++)
                if (col < pos)
                    b[row][col] = data[row][col];
                else if (col > pos)
                    b[row][col-1] = data[row][col];
        
        return new Matrix(b);  
    }
    
    public Matrix addColumn(int pos, double val) {
        double[][] b = new double[rows][cols+1];
        
        for (int row=0; row < rows; row++)
            for (int col=0; col < cols+1; col++)
                b[row][col] = (col == pos) ? val :
                    (col > pos) ? data[row][col-1] : data[row][col];                    
        
        return new Matrix(b);
    }    
    
    public void setWith(int row, int col, double val) {
        data[row][col] = val;        
    }    
    
    public double get(int row, int col) {
        return this.data[row][col];
    }

    public double[] getRow(int row) {
        return data[row];
    }

    public Matrix getCol(int col) {
        double[][] colsData = new double[rows][1];

        for (int row = 0; row < rows; row++)
            colsData[row][0] = data[row][col];

        return new Matrix(colsData);
    }

    public Matrix identity() {
        double[][] a = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            a[i][i] = 1;

        return new Matrix(a);
    }

    // return B = A^T
    public Matrix transpose() {
        final double[][] b = new double[cols][rows];
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                b[col][row] = this.get(row, col);

        return new Matrix(b);
    }
    
    
    // return c = a + b
    public Matrix add(Matrix b) {               
        final double[][] c = new double[rows][cols];
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) + b.get(row, col);

        return new Matrix(c);
    }
    
    // return c = a + b
    public Matrix add(double b) {      
        final double[][] c = new double[rows][cols];
        
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) + b;

        return new Matrix(c);
    }    

    // return c = a - b
    public Matrix subtract(Matrix b) {
        final double[][] c = new double[rows][cols];
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) - b.get(row, col);

        return new Matrix(c);
    }
    
    // return c = a - b
    public Matrix subtract(double b) {
        final double[][] c = new double[rows][cols];
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) - b;

        return new Matrix(c);
    }    

    // return c = a * b
    public Matrix multiply(Matrix b) {
        if (cols != b.rows)
            throw new RuntimeException("Illegal matrix dimensions.");

        final double[][] c = new double[rows][b.cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < b.cols; col++)
                for (int k = 0; k < cols; k++)
                    c[row][col] += this.get(row, k) * b.get(k, col);

        return new Matrix(c);
    }

    public Matrix multiply(double b) {
        final double[][] c = new double[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) * b;

        return new Matrix(c);
    }
    
    public Matrix divide(double b) {        
        if (b == 0)
            throw new RuntimeException("Illegal division by zero.");
        
        final double[][] c = new double[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                c[row][col] = this.get(row, col) / b;

        return new Matrix(c);
    }
    
    public double determinant(){
        double data[][] = new double[rows][cols*2];        
        
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                data[row][col] = get(row, col);
                data[row][col+rows] = get(row, col);
            }
        
        double mainDiagonal = 0;        
        for (int col=0; col < cols; col++) {
            double dig = 1;
            
            for (int row = 0; row < rows; row++) 
                dig *= data[row][col+row];
            
            mainDiagonal += dig;
        }
        
        double secDiagonal = 0;
        for (int col=0; col < cols; col++) {
            double dig = 1;
            
            for (int row = rows-1, inc = 0; row >= 0; row--, inc++)
                dig *= data[row][col+inc];
        
            secDiagonal += dig;
        }
        
        return mainDiagonal - secDiagonal;     
    }
    
    
    public Matrix divide(Matrix b) {        

        Matrix c = multiply(b.transpose());
        
        return c;
    }     
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (int row = 0; row < rows; row++) {
            sb.append("|");
            for (int col=0; col<cols; col++) {
                sb.append(this.get(row, col))
                  .append("\t|");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix))
            return false;
        
        Matrix other = ((Matrix) obj);
        
        if (other.rows != rows || other.cols != cols)
            return false;
        
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                if (get(row, col) != other.get(row, col))
                    return false;
        
        return true;
    }
}