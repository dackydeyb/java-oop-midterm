//PADUADA, Dave Jhared G.
// BSCpE II - IF
// T & TH 10:30 - 13:30

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;

public class mainsolverplus {
    public static void main(String[] args) throws Exception {
        new WelcomeMessage();
    }
}

class WelcomeMessage {
    WelcomeMessage() throws Exception {
        String [] options = {"START", "EXIT"};
        
        int choice = JOptionPane.showOptionDialog ( null, "Welcome to the Main Solver+ !\nThis program will help you solve your triangle math\n\nClick START to continue", "Main Solver+", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
        if (choice == 0) {
            new TrianglePosition();
        } else {
            System.exit(0);
        }
    }
}

class TrianglePosition {
    TrianglePosition() throws Exception {

        String [] choices = {"-- Please select --", "Left side", "Right side", "Exit"};

        String part = (String) JOptionPane.showInputDialog(null, "Where is the 90 degree angle is located?", "Choose Position", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if ("-- Please select --".equals(part)) {
            new TrianglePosition();
        } else if ("Left side".equals(part)) {
            new LeftSideDegree();
        } else if ("Right side".equals(part)) {
            new RightSideDegree();
        } else {
            System.exit(0);
        }
    }
}

class LeftSideDegree {
    LeftSideDegree() throws Exception {
        String sideA_Left = JOptionPane.showInputDialog(null, "Enter the value of the adjacent side: ", "Adjacent Side", JOptionPane.QUESTION_MESSAGE);
        String sideB_Left = JOptionPane.showInputDialog(null, "Enter the value of the opposite side: ", "Opposite Side", JOptionPane.QUESTION_MESSAGE);
        String sideC_Left = JOptionPane.showInputDialog(null, "Enter the value of the hypotenuse: ", "Hypotenuse", JOptionPane.QUESTION_MESSAGE);
    }
}

class RightSideDegree {

    private double sideA; // Opposite
    private double sideB; // Adjacent
    private double sideC; // Hypotenuse
    private double angleA;
    private double angleB;

    RightSideDegree() throws Exception {
        getInput();
        if (areAllSidesMissing()) {
            JOptionPane.showMessageDialog(null, "At least one side must contain a value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            getInput(); // Re-prompt for input after showing the error message.
        }
        calculateMissingValues();
        displayResults();
    }

    private boolean areAllSidesMissing() {
        // Check if all sides are marked as missing (-1)
        return sideA == -1 && sideB == -1 && sideC == -1;
    }

    private void getInput() throws Exception {
        sideA = getDoubleFromInput("Enter the value of the opposite side (a): ", "Opposite Side");
        sideB = getDoubleFromInput("Enter the value of the adjacent side (b): ", "Adjacent Side");
        sideC = getDoubleFromInput("Enter the value of the hypotenuse (c): ", "Hypotenuse");
        angleA = getDoubleFromInput("Enter the value of angle A (degrees): ", "Angle A");
        angleB = getDoubleFromInput("Enter the value of angle B (degrees): ", "Angle B");
    }

    private double getDoubleFromInput(String message, String title) throws Exception {
        Object[] options = {"OK", "MISSING", "GO BACK", "Cancel"};
        
        int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == 0) { // OK
            while (true) {
                String input = JOptionPane.showInputDialog(null, "Enter a value:");
                    if (input.equals("")) {
                        JOptionPane.showMessageDialog(null, "You must enter a value", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        return Double.parseDouble(input);
                    }
            }
        } else if (option == 1) { // MISSING, when clicked it will input -1
            return -1;
        } else if (option == 2) { // GO BACK
            new TrianglePosition();
            return -1;
        } else { // Cancel or close
            System.exit(0);
            return -1; // This line will never be reached but is required to satisfy the compiler.
        }
    }

    private void calculateMissingValues() {

        //iF the user enters a value of -1, the program will calculate the missing value and it will use Pythagorean theorem.
        //!= means if the side# has values.
        if (sideA == -1 && sideB != -1 && sideC != -1) {
            sideA = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2)); // We use -(negative) here because we transposed the formula
        } else if (sideB == -1 && sideA != -1 && sideC != -1) {
            sideB = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2));
        } else if (sideC == -1 && sideA != -1 && sideB != -1) {
            sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));// Standard a^2 + b^2 = c^2 then square root to get c
        }

        if (angleA == -1 && sideA != -1 && sideC != -1) {
            angleA = Math.toDegrees(Math.asin(sideA / sideC));
        }
        if (angleB == -1 && sideB != -1 && sideC != -1) {
            angleB = Math.toDegrees(Math.acos(sideB / sideC));
        }
    }

    private void displayResults() throws Exception {
        String results = "Results:\n\n" +
                         "Side a (opposite): " + sideA + "\n" +
                         "Side b (adjacent): " + sideB + "\n" +
                         "Side c (hypotenuse): " + sideC + "\n\n" +
                         "Angle A: " + angleA + " degrees\n" +
                         "Angle B: " + angleB + " degrees\n"+
                         "Angle C: 90.0 degrees";
    
                         Object[] options = {"OK", "SAVE", "GO BACK"};
                         int option = JOptionPane.showOptionDialog(null, results, "Calculated Values",
                                 JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                 
                         if (option == 1) { // SAVE selected
                             saveResultsOutside();
                         } else if (option == 2) { // GO BACK selected
                                new TrianglePosition();
                         }
        // If OK or the close button is selected, do nothing and the program will terminate naturally.
    }


    private class saveResultInside {
        saveResultInside(double angleA, double angleB) throws Exception {
            // Specify the relative path to the Midterm Project directory
            File file = new File("Midterm Project/triangle_angles.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
    
            writer.println("Angle A: " + angleA + " degrees");
            writer.println("Angle B: " + angleB + " degrees");
            writer.println("Last updated on: " + new java.util.Date());
            writer.println(); // Adds a newline for separation between entries
    
            writer.close();
    
            JOptionPane.showMessageDialog(null, "Results have been saved to Midterm Project/triangle_angles.txt", "Results Saved", JOptionPane.INFORMATION_MESSAGE);
            int option = JOptionPane.showOptionDialog(null, "Would you like to calculate again?", "Calculate again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                new TrianglePosition();
            } else {
                JOptionPane.showMessageDialog(null, "Happy coding!", "Cheers", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void saveResultsOutside() throws Exception {
        File file = new File("triangle_sides.txt");
    
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        writer.println("Hypotenuse: " + sideC);
        writer.println("Adjacent: " + sideB);
        writer.println("Opposite: " + sideA + "\n");
        writer.println("Angle A: " + angleA + " degrees");
        writer.println("Angle B: " + angleB + " degrees");
        writer.println("Last updated on: " + new java.util.Date());
        writer.println(); // Adds a newline for separation between entries
    
        writer.close();
    
        JOptionPane.showMessageDialog(null, "Results have been saved to Midterm Project/triangle_sides.txt", "Results Saved", JOptionPane.INFORMATION_MESSAGE);
        int option = JOptionPane.showOptionDialog(null, "Would you like to calculate again?", "Calculate again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            new TrianglePosition();
        } else {
            JOptionPane.showMessageDialog(null, "Happy coding!", "Cheers", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}