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
public class LogisticRegressionRegularized {    
    
    LogisticRegressionRegularized(String fileName) throws FileNotFoundException {
        final Matrix data = FileUtils.loadData(fileName);        
        final Matrix positive = data.filterByCol(2, d->d == 0);
        final Matrix negative = data.filterByCol(2, d->d == 1);
        
    // ======================= Part 2: Plotting ================================       
        Chart chart = new Chart();
        chart.addSerie(new Serie("Not Admitted", positive, RED));
        chart.addSerie(new Serie("Admitted", negative, DARK_GREEN));    
        chart.showChart(true, null, "Exam 1 score", "Exam 2 score");
        
    // =================== Part 3: Cost and Gradient descent ===================             
        final Matrix X = mapFeature(data.getCol(0), data.getCol(1));
        final Matrix Y = data.getCol(2);
        Matrix theta = new Matrix(X.colsCount(), 1); // default is to be zeros already        
        double lambda = 1;
        
    // compute and display initial cost
        double cost = computeCostRegularized(X, Y, theta, lambda);
        Matrix grad = computeGradientDescentRegularized(X, Y, theta, lambda);
        System.out.println("Cost at initial theta (zeros) = " + cost);
        System.out.println("Expected cost value (approx) 0.693");
        System.out.println("Gradient at theta (zeros) = "+ grad);
        System.out.println("Expected gradients (approx) = |0.0085\t|0.0188\t|0.0001\t|0.0503\t|0.0115\t|");        
        System.out.println("===================================================");        
    // Compute and display cost and gradient with non-zero theta
        double test_lambda = 10;
        Matrix test_theta = new Matrix(X.colsCount(), 1).add(1.0);
        double cost2 = computeCostRegularized(X, Y, test_theta, test_lambda);
        Matrix grad2 = computeGradientDescentRegularized(X, Y, test_theta, test_lambda);
        
        System.out.println("Cost at test theta: = " + cost2);
        System.out.println("Expected cost (approx):  3.16");
        System.out.println("Gradient at test theta = " + grad2);
        System.out.println("Expected gradients (approx) = |0.3460\t|0.1614\t|0.1948\t|0.2269\t|0.0922\t| |0.043\t|2.566\t|2.647\t|");
        
        // Plot prediction =====================================================   
/*        
        theta = theta.addColumn(v->computeCostRegularized(
                new Matrix(new double[][]{{X.get(v.getRowIndex(), 0)}}), 
                new Matrix(new double[][]{{Y.get(v.getRowIndex(), 0)}}), 
                new Matrix(new double[][]{{v.getColumn(0)}}), lambda)).dropColumn(0);        
*/ 
    
/* PLOT NOT WORKING YET !        
        final Matrix demo_theta = new Matrix(new double[][]{
            {1.273005, 0.624876, 1.177376,-2.020142,-0.912616,-1.429907,
             0.125668,-0.368551,-0.360033,-0.171068,-1.460894,-0.052499,
            -0.618889,-0.273745,-1.192301,-0.240993,-0.207934,-0.047224,
            -0.278327,-0.296602,-0.453957,-1.045511, 0.026463,-0.294330,
             0.014381,-0.328703,-0.143796,-0.924883}          
        }).transpose();
        
        double[]u = linspace(-1, 1.5, 50);
        double[]v = linspace(-1, 1.5, 50);

        Matrix z = new Matrix(u.length, v.length);
        // Evaluate z = theta*x over the grid
        for (int i = 0; i < u.length; i++)
            for (int j = 0; j < v.length; j++) {
                Matrix tmp = new Matrix(new double[][]{{u[i], v[j]}});

                z.setWith(i, j, mapFeature(tmp.getCol(0), tmp.getCol(1)).multiply(demo_theta).get(0, 0));
            }
        
        z = z.transpose();// important to transpose z before calling contour          
                
        Matrix prediction = z;
        chart.addSerie(new Serie("prediction", prediction, BLUE, true));
        chart.showChart(true, null, "Exam 1 score", "Exam 2 score");
*/        
    }

   
   private double[] linspace(final double start, final double end, final int n) {
       final double[] line = new double[n];       
       
       final double fragment = (end - start)/n;
       
       for (int i=0; i < n; i++) {
           line[i] = start + (fragment * i);
       }
       
       return line;
   }
    
    private double computeCostRegularized(Matrix X, Matrix Y, Matrix theta, double lambda) {
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

       //reg_term = sum(theta.get(2:end) .^ 2) * lambda / (2 * m);       
       double regularized_term = theta.apply(row->row>0, col->col==0, v->pow(v, 2))
             .sum(0) * (lambda/(2*m));
                            
//       J = 1./m * sum((-y' .* log(g)) - ((1 - y)' .* log(1 - g)));       
       return  ( yT.multiply(logG).subtract(oneMinusYT.multiply(logOneMinusG)).sum(0) / m ) + regularized_term;       
    }    
    
    private Matrix computeGradientDescentRegularized(Matrix X, Matrix Y, Matrix theta, double lambda) {
       final double m = Y.rowsCount();
    
       Matrix z = X.multiply(theta);
       Matrix g = z.applyToCol(0, d->sigmoid(d));       
       
       Matrix reg_theta = theta;
       reg_theta.setWith(0, 0, 0);
      
       //  (lambda / m) * reg_theta'
       Matrix regularized_term = (reg_theta.transpose()).multiply(lambda/m);
        
       // ( 1./m * (g - y') * X ) + regularized_term;        
       return (g.subtract(Y).transpose().multiply(X).multiply(1/m)).add(regularized_term);
    }
    
    public double sigmoid(double z) {
       return 1/(1 + exp(-z));
    }
    

    public Matrix mapFeature(Matrix X1, Matrix X2) {
    /*
     MAPFEATURE Feature mapping function to polynomial features
    
       MAPFEATURE(X1, X2) maps the two input features
       to quadratic features used in the regularization exercise.
    
       Returns a new feature array with more features, comprising of 
       X1, X2, X1.^2, X2.^2, X1*X2, X1*X2.^2, etc..
    
       Inputs X1, X2 must be the same size
    */
        double degree = 6;
        Matrix out = new Matrix(X1, X2);
        
        for(int i = 1; i <= degree; i++) {
            for (int j = 0; j <= i; j++) {
                final double exponent1 = (i-j);
                final double exponent2 = j;
                out = out.addColumn(v -> {
                    final double x1 = v.getColumn(0);
                    final double x2 = v.getColumn(1);
                    
                    return pow(x1, exponent1) * pow(x2, exponent2);
                });
            }
        }
        
        return out.dropColumn(1).dropColumn(1).addColumn(0, 1);
    }        
    
    public static void main(String args[]) throws FileNotFoundException {
        new LogisticRegressionRegularized("data2.txt");
    }
}
