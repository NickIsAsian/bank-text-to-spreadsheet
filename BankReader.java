import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BankReader {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Scanner fileScanner = getFile(scan);

        // Header row for Google Sheets
        System.out.println("Date\tExpense\tAmount");

        while (fileScanner.hasNextLine()) {
       
            String line = fileScanner.nextLine();
            if (!line.matches("^\\d{2}/\\d{2}.*")) {
                continue;
            }
            String date = getDate(line);
            String expense = getExpense(line);
            double amount = getAmount(line);

            // TAB-separated output
            System.out.println(date + "\t" + expense + "\t" + amount);
        }

        fileScanner.close();
        scan.close();
    }

    public static Scanner getFile(Scanner keyboard) throws IOException {
        while (true) {
            System.out.print("Enter the file name: ");
            File inputFile = new File(keyboard.nextLine());

            if (inputFile.exists()) {
                return new Scanner(inputFile);
            } else {
                System.out.println("Sorry, that file does not exist");
            }
        }
    }

    public static String getDate(String line) {
        Scanner lineScanner = new Scanner(line);
        String date = lineScanner.next();
        lineScanner.close();
        return date;
     
    }

    public static String getExpense(String line) {
        Scanner lineScanner = new Scanner(line);

        // Skip date and transaction ID
        lineScanner.next();
        lineScanner.next();

        String expense = "";

        while (lineScanner.hasNext()) {
            String word = lineScanner.next();

            // Stop before the amount
            if (word.startsWith("$")) {
                break;
            }

            expense += word + " ";
        }
        expense = expense.trim();
        lineScanner.close();
        return expense;
    }

    public static double getAmount(String line) {
        Scanner lineScanner = new Scanner(line);
        String last = "";

        while (lineScanner.hasNext()) {
            last = lineScanner.next();
        }
        lineScanner.close();
        return Double.parseDouble(last.substring(1));
    }
}
