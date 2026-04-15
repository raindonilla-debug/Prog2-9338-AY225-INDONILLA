/**
 * Name: Rhenz A. Indonilla
 * Student ID: 22-1550-135
 * Course: BSCSIT 1203 Programming 2
 * Assignment: Assignment 01 - Determinant Solver
 * Date: April 12, 2026
 * Description: Node.js implementation of 3x3 determinant cofactor expansion.
 */

const matrix = [
    [2, 1, 3],
    [4, 5, 2],
    [1, 3, 4]
];

const printMatrix = (m) => {
    m.forEach(row => {
        console.log(`| ${row.map(val => val.toString().padStart(2)).join(' ')} |`);
    });
};

const computeMinor = (a, b, c, d, step) => {
    const result = (a * d) - (b * c);
    console.log(`Step ${step} - Minor M1${step}: det([${a},${b}],[${c},${d}]) = (${a}x${d}) - (${b}x${c}) = ${a*d} - ${b*c} = ${result}`);
    return result;
};

const solveDeterminant = (m) => {
    console.log("==============================================");
    console.log("   3x3 MATRIX DETERMINANT SOLVER");
    console.log("   Student: [YOUR FULL NAME]");
    console.log("   Assigned Matrix:");
    console.log("==============================================");
    printMatrix(m);
    console.log("==============================================\n");

    console.log("Expanding along Row 1 (cofactor expansion):\n");

    // Minor calculations
    const m11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2], 1);
    const m12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2], 2);
    const m13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1], 3);

    // Cofactor calculations
    const c11 = 1 * m[0][0] * m11;
    const c12 = -1 * m[0][1] * m12;
    const c13 = 1 * m[0][2] * m13;

    console.log(`\nCofactor C11 = (+1) x ${m[0][0]} x ${m11} = ${c11}`);
    console.log(`Cofactor C12 = (-1) x ${m[0][1]} x ${m12} = ${c12}`);
    console.log(`Cofactor C13 = (+1) x ${m[0][2]} x ${m13} = ${c13}`);

    const det = c11 + c12 + c13;
    console.log(`\ndet(M) = ${c11} + (${c12}) + ${c13}`);

    console.log("\n==============================================");
    console.log(` ✓  DETERMINANT = ${det}`);
    
    if (det === 0) {
        console.log(" The matrix is SINGULAR—it has no inverse.");
    }
    console.log("==============================================");
};

solveDeterminant(matrix);