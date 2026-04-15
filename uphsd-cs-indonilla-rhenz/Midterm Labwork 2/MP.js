/**
 * CSV Dataset Processor - JavaScript (Node.js) Implementation
 * Processes CSV files to count valid rows, display formatted table, and find longest text entry
 */

// Variables to store dataset data
let records = [];           // Array of row arrays
let headers = [];           // Header row
let validRowCount = 0;      // Counter for non-empty rows
let filePath = '';          // User-provided file path

/**
 * Main processing function - Program entry point
 */
async function main() {
    try {
        // Step 1: Prompt user for dataset file path
        filePath = await promptUserForFilePath();
        
        // Step 2: Read and parse CSV file
        await readCSVFile();
        
        // Step 3: Process dataset and display results
        displayResults();
        
    } catch (error) {
        // Error handling for file operations
        console.error('Error processing dataset:', error.message);
        console.error('Please check if the file exists and is accessible.');
    }
}

/**
 * Prompts user for file path using Node.js readline
 */
function promptUserForFilePath() {
    return new Promise((resolve) => {
        const readline = require('readline').createInterface({
            input: process.stdin,
            output: process.stdout
        });
        
        readline.question('Enter the dataset file path: ', (path) => {
            readline.close();
            resolve(path);
        });
    });
}

/**
 * Reads CSV file using Node.js File System module
 * Parses CSV data into arrays, counts valid rows
 */
async function readCSVFile() {
    const fs = require('fs').promises;
    
    try {
        const data = await fs.readFile(filePath, 'utf8');
        const lines = data.trim().split('\n');
        let isFirstLine = true;
        
        // Processing logic: Parse each line
        for (let line of lines) {
            // Skip completely empty rows
            if (line.trim() === '') {
                continue;
            }
            
            // Simple CSV parsing using regex (handles quoted fields)
            const rowData = parseCSVLine(line);
            
            // Store headers from first non-empty row
            if (isFirstLine) {
                headers = rowData;
                isFirstLine = false;
            } else {
                records.push(rowData);
                validRowCount++;  // Increment valid row counter
            }
        }
        
        console.log('\n=== CSV File Loaded Successfully ===');
        console.log(`Total valid rows (excluding empty): ${validRowCount}`);
        console.log(`Total columns: ${headers.length}`);
        
    } catch (error) {
        throw new Error(`Failed to read file: ${error.message}`);
    }
}

/**
 * Simple CSV line parser using regex
 * Handles basic comma separation and quoted fields
 */
function parseCSVLine(line) {
    const regex = /("([^"]*)"|([^,]*))(,|$)/g;
    const matches = [];
    let match;
    
    while ((match = regex.exec(line)) !== null) {
        const value = match[2] ? match[2] : (match[3] || '');
        matches.push(value);
    }
    
    return matches;
}

/**
 * Displays all processing results
 */
function displayResults() {
    if (records.length === 0) {
        console.log('No valid data rows found in dataset.');
        return;
    }
    
    // Display formatted table
    displayFormattedTable();
    
    // Find and display longest text entry
    findLongestTextEntry();
}

/**
 * Displays dataset in formatted table with proper alignment
 */
function displayFormattedTable() {
    console.log('\n=== FORMATTED DATASET TABLE ===');
    console.log('Row # | ' + formatHeaderRow());
    
    // Display data rows with row numbers
    records.forEach((row, index) => {
        console.log(`${String(index + 1).padStart(4)} | ${formatDataRow(row)}`);
    });
}

/**
 * Formats header row with fixed width columns
 */
function formatHeaderRow() {
    return headers.map(header => truncateText(header, 15).padEnd(15) + '| ').join('');
}

/**
 * Formats data row with fixed width columns
 */
function formatDataRow(row) {
    return row.map(cell => truncateText(cell, 15).padEnd(15) + '| ').join('');
}

/**
 * Truncates text to specified length for table formatting
 */
function truncateText(text, maxLength) {
    if (!text) return '';
    return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
}

/**
 * Finds the longest text entry across all cells in dataset
 */
function findLongestTextEntry() {
    let longestText = '';
    let maxLength = 0;
    
    // Scan all records and cells for longest text
    for (let row of records) {
        for (let cell of row) {
            if (cell && cell.length > maxLength) {
                maxLength = cell.length;
                longestText = cell;
            }
        }
    }
    
    console.log('\n=== LONGEST TEXT ENTRY ===');
    console.log(`Longest text: "${longestText}"`);
    console.log(`Length: ${maxLength} characters`);
}

// Start the program
main();