package com.engcpp;

import com.engcpp.utils.Chart;
import com.engcpp.utils.FileUtils;
import com.engcpp.utils.Matrix;
import com.engcpp.utils.Serie;
import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import java.io.FileNotFoundException;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static org.jfree.chart.ChartColor.DARK_GREEN;

/**
 *
 * @author engcpp
 */
public class LogisticRegression {    
    
    LogisticRegression(String fileName) throws FileNotFoundException {
        final Matrix data = FileUtils.loadData(fileName);        
        final Matrix positive = data.filterByCol(2, d->d == 0);
        final Matrix negative = data.filterByCol(2, d->d == 1);
        
    // ======================= Part 2: Plotting ================================       
        Chart chart = new Chart();
        chart.addSerie(new Serie("Not Admitted", positive, RED));
        chart.addSerie(new Serie("Admitted", negative, DARK_GREEN));    
        chart.showChart(true, null, "Exam 1 score", "Exam 2 score");
        
    // =================== Part 3: Cost and Gradient descent ===================        
        final Matrix X = new Matrix(data.getCol(0), data.getCol(1)).addColumn(0, 1);
        final Matrix Y = data.getCol(2);
        Matrix theta = new Matrix(X.colsCount(), 1); // default is to be zeros already        
        
    // compute and display initial cost
        double cost = computeCost(X, Y, theta);
        Matrix grad = computeGradientDescent(X, Y, theta);
        System.out.println("Cost at initial theta (zeros) = " + cost);
        System.out.println("Expected cost value (approx) 0.693");
        System.out.println("Gradient at theta (zeros) = "+ grad);        
        System.out.println("Expected gradients (approx) = |-0.1000\t|-12.0092\t|-11.2628\t|");        
        System.out.println("===================================================");        
    // Compute and display cost and gradient with non-zero theta
        Matrix test_theta = new Matrix(new double[][]{{-24}, {0.2}, {0.2}});
        double cost2 = computeCost(X, Y, test_theta);
        Matrix grad2 = computeGradientDescent(X, Y, test_theta);
        
        System.out.println("Cost at test theta: = " + cost2);
        System.out.println("Expected cost (approx): 0.218");
        System.out.println("Gradient at test theta = " + grad2);
        System.out.println("Expected gradients (approx) = |0.043\t|2.566\t|2.647\t|");

        
        // Plot prediction =====================================================
        theta = new Matrix(new double[][]{{-25.161}, {0.206}, {0.201}});
        double[][] plot = new double[][]{
            {X.min(1) - 2, 0},
            {X.max(1) + 2, 0}
        };
        
        plot[0][1] = (-1/theta.get(2, 0)) * ( theta.get(1, 0) * plot[0][0] + theta.get(0, 0));
        plot[1][1] = (-1/theta.get(2, 0)) * ( theta.get(1, 0) * plot[1][0] + theta.get(0, 0));
        
        final Matrix prediction = new Matrix(plot);
        chart.addSerie(new Serie("prediction", prediction, BLUE, true));
        chart.showChart(true, null, "Exam 1 score", "Exam 2 score");
    }
    
    private double computeCost(Matrix X, Matrix Y, Matrix theta) {
    // Number of training examples
       final double m = Y.rowsCount();
    
       Matrix z = X.multiply(theta);
       Matrix g = z.applyToCol(0, d->sigmoid(d));
       
       // log(g)
       Matrix logG = g.applyToCol(0, v->log(v));       
       // -y'
       Matrix yT = Y.multiply(-1).transpose();
       
       // (1 - y)'
       Matrix oneMinusYT = Y.applyToCol(0, v->(1 - v)).transpose();
       // log(1 - g)
       Matrix logOneMinusG = g.applyToCol(0, v->log(1-v));

//       J = 1./m * sum((-y' .* log(g)) - ((1 - y)' .* log(1 - g)));       
       return  yT.multiply(logG).subtract(oneMinusYT.multiply(logOneMinusG)).sum(0) / m;       
    }    
    
    private Matrix computeGradientDescent(Matrix X, Matrix Y, Matrix theta) {
       final double m = Y.rowsCount();
    
       Matrix z = X.multiply(theta);
       Matrix g = z.applyToCol(0, d->sigmoid(d));       
        
       // 1./m * (g - y') * X;        
       return g.subtract(Y).transpose().multiply(X).multiply(1/m);
    }
    
    public double sigmoid(double z) {
       return 1/(1 + exp(-z));
    }
        
    public static void main(String args[]) throws FileNotFoundException {
        new LogisticRegression("data1.txt");
    }
}
