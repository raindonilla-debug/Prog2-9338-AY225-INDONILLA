/**
 * Name: Rhenz A. Indonilla
 * Student ID: 22-1550-135
 * Course: BSCSIT 1203 Programming 2
 * Assignment: Assignment 01 - Determinant Solver
 * Date: April 12, 2026
 * Description: Computes the determinant of a 3x3 matrix using cofactor expansion
 * along the first row with step-by-step console output.
 */

public class DeterminantSolver {

    public static void main(String[] args) {
        // Matrix declaration
        int[][] matrix = {
            {2, 1, 3},
            {4, 5, 2},
            {1, 3, 4}
        };

        printHeader();
        printMatrix(matrix);
        solveDeterminant(matrix);
    }

    public static void printHeader() {
        System.out.println("==============================================");
        System.out.println("   3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("   Student: Rhenz A. Indonilla"); // Added name here
        System.out.println("   Assigned Matrix:");
        System.out.println("==============================================");
    }

    public static void printMatrix(int[][] m) {
        for (int[] row : m) {
            System.out.print("| ");
            for (int val : row) {
                System.out.printf("%2d ", val);
            }
            System.out.println("|");
        }
        System.out.println("==============================================\n");
    }

    public static int computeMinor(int a, int b, int c, int d, int step) {
        int result = (a * d) - (b * c);
        // Improved the string formatting for better alignment
        System.out.printf("Step %d - Minor M1%d: det([%d, %d], [%d, %d]) = (%d*%d) - (%d*%d) = %d - %d = %d\n",
                step, step, a, b, c, d, a, d, b, c, (a * d), (b * c), result);
        return result;
    }

    public static void solveDeterminant(int[][] m) {
        System.out.println("Expanding along Row 1 (cofactor expansion):\n");

        // Step-by-step minor calculations
        // M11: Minor from deleting Row 1, Col 1
        int m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2], 1);
        // M12: Minor from deleting Row 1, Col 2
        int m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2], 2);
        // M13: Minor from deleting Row 1, Col 3
        int m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1], 3);

        // Cofactor calculations: C_ij = (-1)^(i+j) * a_ij * M_ij
        int c11 = 1 * m[0][0] * m11;
        int c12 = -1 * m[0][1] * m12;
        int c13 = 1 * m[0][2] * m13;

        System.out.println("\nCofactor C11 = (+1) x " + m[0][0] + " x " + m11 + " = " + c11);
        System.out.println("Cofactor C12 = (-1) x " + m[0][1] + " x " + m12 + " = " + c12);
        System.out.println("Cofactor C13 = (+1) x " + m[0][2] + " x " + m13 + " = " + c13);

        int determinant = c11 + c12 + c13;
        System.out.printf("\ndet(M) = %d + (%d) + %d\n", c11, c12, c13);
        
        System.out.println("\n==============================================");
        System.out.println(" ✓  DETERMINANT = " + determinant);
        
        // Handle singular matrix case
        if (determinant == 0) {
            System.out.println(" The matrix is SINGULAR—it has no inverse.");
        } else {
            System.out.println(" The matrix is NON-SINGULAR and invertible.");
        }
        System.out.println("==============================================");
    }
}