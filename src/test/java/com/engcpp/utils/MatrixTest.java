package com.engcpp.utils;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author engcpp
 */
@RunWith(JUnit4.class)
public class MatrixTest {
   
  @Test
  public void matrixTest() {
    Matrix m3x3 = new Matrix(new double[][]{
        {1,2,3},
        {10,10,10},
        {9,8,7}
    });
    System.out.println("Matrix 3x3");
    System.out.println(m3x3);

    assertEquals(m3x3.get(0, 0), 1.0);
    assertEquals(m3x3.get(1, 0), 10.0);
    assertEquals(m3x3.get(2, 0), 9.0);

    assertEquals(m3x3.get(0, 1), 2.0);
    assertEquals(m3x3.get(1, 1), 10.0);     
    assertEquals(m3x3.get(2, 1), 8.0);

    assertEquals(m3x3.get(0, 2), 3.0);
    assertEquals(m3x3.get(1, 2), 10.0);
    assertEquals(m3x3.get(2, 2), 7.0);          
  }  

  @Test
  public void matrixIdentityTest() {
    // 3x3 matrix
    Matrix identity = new Matrix(3).identity();

    System.out.println("Matrix 3x3 identity");
    System.out.println(identity);

    assertEquals(identity.get(0, 0), 1.0);
    assertEquals(identity.get(1, 0), 0.0);
    assertEquals(identity.get(2, 0), 0.0);

    assertEquals(identity.get(0, 1), 0.0);
    assertEquals(identity.get(1, 1), 1.0);     
    assertEquals(identity.get(2, 1), 0.0);

    assertEquals(identity.get(0, 2), 0.0);
    assertEquals(identity.get(1, 2), 0.0);
    assertEquals(identity.get(2, 2), 1.0);      
  }   
  
  @Test
  public void matrixTransposedTest() {
    Matrix m3x3T = new Matrix(new double[][]{
        {1,2,3},
        {4,5,6},
        {7,8,9}
    }).transpose();
    System.out.println("Matrix 3x3 transposed");
    System.out.println(m3x3T);

    assertEquals(m3x3T.get(0, 0), 1.0);
    assertEquals(m3x3T.get(1, 0), 2.0);
    assertEquals(m3x3T.get(2, 0), 3.0);

    assertEquals(m3x3T.get(0, 1), 4.0);
    assertEquals(m3x3T.get(1, 1), 5.0);     
    assertEquals(m3x3T.get(2, 1), 6.0);

    assertEquals(m3x3T.get(0, 2), 7.0);
    assertEquals(m3x3T.get(1, 2), 8.0);
    assertEquals(m3x3T.get(2, 2), 9.0);          
  }
  
  @Test
  public void matrixASumBTest() {
    Matrix A = new Matrix(new double[][]{
        {1, 2},
        {3, 4}
    });
    Matrix B = new Matrix(new double[][]{
        {10, 20},
        {30, 40}
    });      

    Matrix C = A.add(B);
    Matrix D = B.add(A);

    System.out.println("Matrix C = matrix A +  matrix B");
    System.out.println(C);
    
    assertEquals(D, C);
    assertEquals(C.get(0, 0), 11.0);
    assertEquals(C.get(0, 1), 22.0);
    assertEquals(C.get(1, 0), 33.0);      
    assertEquals(C.get(1, 1), 44.0);      
  } 
  
    @Test
  public void matrixASumScalarBTest() {
    Matrix A = new Matrix(new double[][]{
        {1, 2},
        {3, 4}
    });
    
    final double B = 10;    

    Matrix C = A.add(B);

    System.out.println("Matrix C = matrix A + scalar B");
    System.out.println(C);

    assertEquals(C.get(0, 0), 11.0);
    assertEquals(C.get(0, 1), 12.0);
    assertEquals(C.get(1, 0), 13.0);      
    assertEquals(C.get(1, 1), 14.0);      
  }
  
  @Test
  public void matrixASubtractBTest() {
    Matrix A = new Matrix(new double[][]{
        {10, 20},
        {30, 40}
    });
    Matrix B = new Matrix(new double[][]{
        {5, 5},
        {5, 5}
    });      

    Matrix C = A.subtract(B);

    System.out.println("Matrix C = matrix A - matrix B");
    System.out.println(C);

    assertEquals(C.get(0, 0), 5.0);
    assertEquals(C.get(0, 1), 15.0);     
    assertEquals(C.get(1, 0), 25.0);      
    assertEquals(C.get(1, 1), 35.0);      
  }
  
  @Test
  public void matrixASubtractScalarBTest() {
    Matrix A = new Matrix(new double[][]{
        {10, 20},
        {30, 40}
    });
    double B = 5;
    Matrix C = A.subtract(B);

    System.out.println("Matrix C = matrix A - scalar B");
    System.out.println(C);

    assertEquals(C.get(0, 0), 5.0);
    assertEquals(C.get(0, 1), 15.0);     
    assertEquals(C.get(1, 0), 25.0);      
    assertEquals(C.get(1, 1), 35.0);      
  }  
  
  
  @Test
  public void matrixAMultiplyedByMatrixBTest() {
    Matrix A = new Matrix(new double[][]{
        { 2, 3},
        { 0, 1},
        {-1, 4}
    });
    Matrix B = new Matrix(new double[][]{
        { 1, 2, 3},
        {-2, 0, 4}
    });
    Matrix C = A.multiply(B);

    System.out.println("Matrix C = matrix A * matrix B");
    System.out.println(C);

    assertEquals(C.get(0, 0), -4.0);
    assertEquals(C.get(0, 1),  4.0);
    assertEquals(C.get(0, 2), 18.0);

    assertEquals(C.get(1, 0), -2.0);      
    assertEquals(C.get(1, 1),  0.0);
    assertEquals(C.get(1, 2),  4.0);

    assertEquals(C.get(2, 0), -9.0);      
    assertEquals(C.get(2, 1), -2.0);
    assertEquals(C.get(2, 2), 13.0);    
  }    
  
  @Test
  public void matrixBMultiplyedByMatrixATest() {
    Matrix A = new Matrix(new double[][]{
        { 2, 3},
        { 0, 1},
        {-1, 4}
    });
    Matrix B = new Matrix(new double[][]{
        { 1, 2, 3},
        {-2, 0, 4}
    });
    Matrix C = B.multiply(A);

    System.out.println("Matrix C = matrix B * matrix A");
    System.out.println(C);

    assertEquals(C.get(0, 0), -1.0);
    assertEquals(C.get(0, 1), 17.0);

    assertEquals(C.get(1, 0), -8.0);      
    assertEquals(C.get(1, 1), 10.0);  
  }
  
  @Test
  public void matrixAMultiplyedByScalarBTest() {
    Matrix A = new Matrix(new double[][]{
        {10, 20},
        {30, 40}
    });
    double B = 10;
    Matrix C = A.multiply(B);

    System.out.println("Matrix C = matrix A * scalar B");
    System.out.println(C);

    assertEquals(C.get(0, 0), 100.0);
    assertEquals(C.get(0, 1), 200.0);
    assertEquals(C.get(1, 0), 300.0);
    assertEquals(C.get(1, 1), 400.0);
  }    
  
  @Test
  public void matrixADividedByScalarBTest() {
    Matrix A = new Matrix(new double[][]{
        {10, 20},
        {30, 40}
    });
    double B = 10;
    Matrix C = A.divide(B);

    System.out.println("Matrix C = matrix A / scalar B");
    System.out.println(C);

    assertEquals(C.get(0, 0), 1.0);
    assertEquals(C.get(0, 1), 2.0);
    assertEquals(C.get(1, 0), 3.0);
    assertEquals(C.get(1, 1), 4.0);
  }   
  
  @Test
  public void matrixADeterminantTest() {
    Matrix A = new Matrix(new double[][]{
        {1, 2, 3},
        {0, 1, 4},
        {5, 6, 0}
    });

    double det = A.determinant();

    System.out.println("Determinant of matrix C");
    System.out.println(det);

    assertEquals(det, 1.0);

  }   
}