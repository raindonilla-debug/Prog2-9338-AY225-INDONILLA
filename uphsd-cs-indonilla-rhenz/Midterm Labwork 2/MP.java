import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CSV Dataset Processor - Java Implementation
 * Processes CSV files to count valid rows, display formatted table, and find longest text entry
 */
public class CSVProcessor {
    
    // Variables to store dataset data
    private List<String[]> records;  // 2D array to store CSV rows and columns
    private String[] headers;         // Store CSV header row
    private int validRowCount;       // Counter for non-empty rows
    
    /**
     * Main method - Program entry point
     */
    public static void main(String[] args) {
        CSVProcessor processor = new CSVProcessor();
        processor.processDataset();
    }
    
    /**
     * Main processing method - Orchestrates entire workflow
     */
    public void processDataset() {
        Scanner scanner = new Scanner(System.in);
        
        // Step 1: Prompt user for dataset file path
        System.out.print("Enter the dataset file path: ");
        String filePath = scanner.nextLine();
        
        try {
            // Step 2: Read and parse CSV file
            readCSVFile(filePath);
            
            // Step 3: Process dataset and display results
            displayResults();
            
        } catch (IOException e) {
            // Error handling for file operations
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Please check if the file exists and is accessible.");
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Reads CSV file using BufferedReader and FileReader
     * Parses each line into String array, stores in ArrayList
     * Counts valid (non-empty) rows during parsing
     */
    private void readCSVFile(String filePath) throws IOException {
        records = new ArrayList<>();
        validRowCount = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            // Processing logic: Read line by line
            while ((line = br.readLine()) != null) {
                // Skip completely empty rows
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Simple CSV parsing (handles basic comma separation)
                String[] rowData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                // Store headers from first non-empty row
                if (isFirstLine) {
                    headers = rowData;
                    isFirstLine = false;
                } else {
                    records.add(rowData);
                    validRowCount++;  // Increment valid row counter
                }
            }
        }
        
        System.out.println("\n=== CSV File Loaded Successfully ===");
        System.out.println("Total valid rows (excluding empty): " + validRowCount);
        System.out.println("Total columns: " + (headers != null ? headers.length : 0));
    }
    
    /**
     * Displays formatted table output and finds longest text entry
     */
    private void displayResults() {
        if (records.isEmpty()) {
            System.out.println("No valid data rows found in dataset.");
            return;
        }
        
        // Display formatted table
        displayFormattedTable();
        
        // Find and display longest text entry
        findLongestTextEntry();
    }
    
    /**
     * Displays dataset in formatted table with proper alignment
     * Uses fixed-width columns for readability
     */
    private void displayFormattedTable() {
        System.out.println("\n=== FORMATTED DATASET TABLE ===");
        System.out.println("Row # | " + formatHeaderRow());
        
        // Display data rows with row numbers
        for (int i = 0; i < records.size(); i++) {
            String[] row = records.get(i);
            System.out.println(String.format("%-5s| %s", (i + 1), formatDataRow(row)));
        }
    }
    
    /**
     * Formats header row with fixed width columns
     */
    private String formatHeaderRow() {
        if (headers == null) return "";
        
        StringBuilder header = new StringBuilder();
        for (String headerCell : headers) {
            String formatted = String.format("%-15s| ", truncateText(headerCell, 15));
            header.append(formatted);
        }
        return header.toString();
    }
    
    /**
     * Formats data row with fixed width columns
     */
    private String formatDataRow(String[] row) {
        StringBuilder dataRow = new StringBuilder();
        for (String cell : row) {
            String formatted = String.format("%-15s| ", truncateText(cell, 15));
            dataRow.append(formatted);
        }
        return dataRow.toString();
    }
    
    /**
     * Truncates text to specified length for table formatting
     */
    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
    
    /**
     * Finds the longest text entry across all cells in dataset
     * Compares character length of each cell value
     */
    private void findLongestTextEntry() {
        String longestText = "";
        int maxLength = 0;
        
        // Scan all records and cells for longest text
        for (String[] row : records) {
            for (String cell : row) {
                if (cell != null && cell.length() > maxLength) {
                    maxLength = cell.length();
                    longestText = cell;
                }
            }
        }
        
        System.out.println("\n=== LONGEST TEXT ENTRY ===");
        System.out.println("Longest text: \"" + longestText + "\"");
        System.out.println("Length: " + maxLength + " characters");
    }
}