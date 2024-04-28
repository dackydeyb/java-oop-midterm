//PADUADA, Dave Jhared G.
// BSCpE II - IF
// OOP Method used: Encapsulation
// T & TH 10:30 - 13:30

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;

public class mainsolverplus {
    public static void main(String[] args) throws Exception {
        new WelcomeMessage(); // Go to the WelcomeMessage class
    }
}

// The WelcomeMessage class will display a welcome message and ask the user to click START or EXIT
class WelcomeMessage {
    WelcomeMessage() throws Exception {
        String[] options = { "START", "EXIT" };

        int choice = JOptionPane.showOptionDialog(null,
                "Welcome to the Main Solver+ !\nThis program will help you solve your triangle math\n\nClick START to continue",
                "Main Solver+", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            new Triangle(); // Go to the Triangle class
        } else {
            System.exit(0);
        }
    }
}

// The Triangle class will ask the user to select the type of given values
class Triangle {
    Triangle() throws Exception {

        String[] choices = { "-- Please select --", "1 Side and 1 Angle", "2 Sides with or without Angles", "Exit" };

        String part = (String) JOptionPane.showInputDialog(null, "How many givens?",
                "Type of Given", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if ("-- Please select --".equals(part)) {
            new Triangle(); // Re-prompt.
        } else if ("1 Side and 1 Angle".equals(part)) {
            new OneAngleOneSide();
        } else if ("2 Sides with or without Angles".equals(part)) {
            new Sideside();
        } else {
            System.exit(0);
        }
    }
}

class OneAngleOneSide { // Contains all the methods for solving a right triangle with one angle and one side.

    // Initialize the sides and angles as -1 to indicate that they are missing
    // Why -1? Because the user can input 0 as a value for the sides and angles.
    // Also -1 acts like a placeholder to tell the compiler that the value is missing.
    private double sideA; // Opposite
    private double sideB; // Adjacent
    private double sideC; // Hypotenuse
    private double angleA;
    private double angleB;
    // angle C is given as 90 degrees since my topic is about 'Solutions of Right Triangle'

    // Constructor
    OneAngleOneSide() throws Exception {
        getInput();
        if (areAllSidesMissing()) {// If true, show an error message and re-prompt for input... If false, proceed
                                    // to calculate the missing values
            JOptionPane.showMessageDialog(null, "At least one side must contain a value.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            getInput();
        }
        oneSideWithAngle();
        displayResults();
    }

    // Get the input from the user
    // The user can input -1 to indicate that the side is missing
    private void getInput() throws Exception {
        sideA = getDoubleFromInput("Enter the value of the opposite side (a): ", "Opposite Side");
        sideB = getDoubleFromInput("Enter the value of the adjacent side (b): ", "Adjacent Side");
        sideC = getDoubleFromInput("Enter the value of the hypotenuse (c): ", "Hypotenuse");
        angleA = getDoubleFromInput("Enter the value of angle A (degrees): ", "Angle A");
        angleB = getDoubleFromInput("Enter the value of angle B (degrees): ", "Angle B");
    }

    private double getDoubleFromInput(String message, String title) throws Exception {

        Object[] options = { "OK", "MISSING", "GO BACK", "Cancel" };
        int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
            if (option == 0) { // OK
            while (true) { // Loop until the user enters a valid value
                String input = JOptionPane.showInputDialog(null, "Enter a value:");
                if (input.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a value", "Error", JOptionPane.ERROR_MESSAGE);
                    getInput(); // Re-prompt for input after showing the error message.
                } else {
                    return Double.parseDouble(input);
                }
            }
        } else if (option == 1) { // MISSING, when clicked it will input -1
            return -1;
        } else if (option == 2) { // GO BACK
            new Triangle();
            return -1;
        } else { // Cancel or close
            System.exit(0);
            return -1; // To avoid the error "missing return statement"
        }
    }

    // Check if all sides are marked as missing (-1)
    // If all sides are missing, the program will show an error message and re-prompt for input
    private boolean areAllSidesMissing() {
        return sideA == -1 && sideB == -1 && sideC == -1;
    }

    // this method will calculate the missing sides and angles
    private void oneSideWithAngle() throws Exception {

        // Check if the sides form a valid right triangle
        if (sideC != -1 && (sideA > sideC || sideB > sideC)) {
            JOptionPane.showMessageDialog(null, "The hypotenuse must be greater than the other sides.", "Invalid Triangle", JOptionPane.ERROR_MESSAGE);
            getInput(); // Re-prompt for input after showing the error message.
        }

        // Now use trigonometry to find missing sides:

        // Case when hypotenuse is known
        if (angleA == -1 && angleB != -1) {
            angleA = 90 - angleB; // Since it's a right triangle
        } else if (angleB == -1 && angleA != -1) {
            angleB = 90 - angleA;
        }

        // Trigonometric calculations for known hypotenuse
        if (sideC != -1) {
            if (sideA == -1 && angleA != -1) { // Find opposite using sine
            sideA = Math.sin(Math.toRadians(angleA)) * sideC;
            }
            if (sideB == -1 && angleA != -1) { // Find adjacent using cosine
            sideB = Math.cos(Math.toRadians(angleA)) * sideC;
            }
        }

        // Trigonometric calculations for known adjacent (side B)
        if (sideB != -1) {
            if (sideC == -1 && angleA != -1) { // Find hypotenuse using cosine
            sideC = sideB / Math.cos(Math.toRadians(angleA));
            }
            if (sideA == -1 && angleA != -1) { // Find opposite using tangent
            sideA = Math.tan(Math.toRadians(angleA)) * sideB;
            }
        }

        // Trigonometric calculations for known opposite (side A)
        if (sideA != -1) {
            if (sideC == -1 && angleA != -1) { // Find hypotenuse using sine
            sideC = sideA / Math.sin(Math.toRadians(angleA));
            }
            if (sideB == -1 && angleA != -1) { // Find adjacent using tangent
            sideB = sideA / Math.tan(Math.toRadians(angleA));
            }
        }

        // Calculations when one angle (B) and its opposite side (B) is known:
        if (sideB != -1 && angleB != -1) {
            if (sideC == -1) { // Find hypotenuse using sine of angle B
            sideC = sideB / Math.sin(Math.toRadians(angleB));
            }
            if (sideA == -1) { // Find opposite using cosine of angle B (since angle B = 90 - angle A)
            sideA = Math.cos(Math.toRadians(angleB)) * sideB;
            }
        }

        // Round the values to two decimal places
        sideA = Math.round(sideA * 100.0) / 100.0;
        sideB = Math.round(sideB * 100.0) / 100.0;
        sideC = Math.round(sideC * 100.0) / 100.0;
        angleA = Math.round(angleA * 100.0) / 100.0;
        angleB = Math.round(angleB * 100.0) / 100.0;
        

        // If angle A or B is not present and angle B or A is present, we can calculate angle A or B using the formula:  90 - angle B or A
        if (angleA != -1 && angleB == -1) {
            angleB = 90 - angleA;
        } else if (angleB != -1 && angleA == -1) {
            angleA = 90 - angleB;
        }
        }
        
        // Display the results to the user
        private void displayResults() throws Exception {
        String results = "Results:\n\n" +
                "Side a (opposite): " + sideA + "\n" +
                "Side b (adjacent): " + sideB + "\n" +
                "Side c (hypotenuse): " + sideC + "\n\n" +
                "Angle A: " + angleA + " degrees\n" + // Assume angleA is already in degrees
                "Angle B: " + angleB + " degrees\n" + // Assume angleB is already in degrees
                "Angle C: 90.0 degrees\n" +
                "Total Angle: " + (angleA + angleB + 90.0) + " degrees\n\n";
    
        Object[] options = { "OK", "SAVE", "GO BACK" };
        int option = JOptionPane.showOptionDialog(null, results, "Calculated Values",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
        if (option == 1) { // SAVE selected
            saveResultsOutside(); // Save the results to a file and will go to saveResultsOutside method
        } else if (option == 2) { // GO BACK selected
            new Triangle();
        }
    }
    
    // Save the results to a file
    // The file will be stacked with the results of each calculation
    // with the use of the PrintWriter class to write to a file.
    // If I use FileWriter, it will overwrite the file each time the program is run
    private void saveResultsOutside() throws Exception {
        File file = new File("One Side One Angle.txt");

        // When writing to a file, often wrap PrintWriter around a FileWriter to handle file creation and character encoding
        // The second argument true in FileWriter constructor is for enabling the append mode, which allows you to add to the existing content of the file instead of overwriting it.
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        writer.println("Hypotenuse: " + sideC);
        writer.println("Adjacent: " + sideB);
        writer.println("Opposite: " + sideA + "\n");
        writer.println("Angle A: " + angleA + " degrees");
        writer.println("Angle B: " + angleB + " degrees\n");
        writer.println("Total Angles: " + (angleA + angleB + 90.0) + "degrees\n");
        writer.println("Last updated on: " + new java.util.Date());
        writer.println(); // Adds a newline for separation between entries
        writer.println("=============================================================");

        writer.close(); // After writing data, I should close the PrintWriter object to release the resources.

        JOptionPane.showMessageDialog(null, "Results have been saved to \"One Side One Angle\".txt",
                "Results Saved", JOptionPane.INFORMATION_MESSAGE);
        int option = JOptionPane.showOptionDialog(null, "Would you like to calculate again?", "Calculate again?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            new Triangle();
        } else {
            JOptionPane.showMessageDialog(null, "Happy coding!", "Cheers", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

// The Sideside class will calculate the missing sides and angles of a right triangle with two sides given
// Same as the OneAngleOneSide class, but this time, we have two sides given,
// the only difference in this Sideside class is the method used to calculate the missing values.

class Sideside {

    // Initialize the sides and angles as -1 to indicate that they are missing

    private double sideA; // Opposite
    private double sideB; // Adjacent
    private double sideC; // Hypotenuse
    private double angleA;
    private double angleB;
    // angle C is given as 90 degrees since my topic is about 'Solutions of Right
    // Triangle'

    Sideside() throws Exception {
        getInput();
        if (areAllSidesMissing()) { // If true, show an error message and re-prompt for input... If false, proceed
                                    // to calculate the missing values
            JOptionPane.showMessageDialog(null, "At least one side must contain a value.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            getInput(); // Re-prompt for input after showing the error message.
        }
        twoSidesWithorWithoutAngle();
        displayResults();
    }

    private void getInput() throws Exception {
        sideA = getDoubleFromInput("Enter the value of the opposite side (a): ", "Opposite Side");
        sideB = getDoubleFromInput("Enter the value of the adjacent side (b): ", "Adjacent Side");
        sideC = getDoubleFromInput("Enter the value of the hypotenuse (c): ", "Hypotenuse");
        angleA = getDoubleFromInput("Enter the value of angle A (degrees): ", "Angle A");
        angleB = getDoubleFromInput("Enter the value of angle B (degrees): ", "Angle B");
    }

    private double getDoubleFromInput(String message, String title) throws Exception {
        Object[] options = { "OK", "MISSING", "GO BACK", "Cancel" };

        int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (option == 0) { // OK
            while (true) {
                String input = JOptionPane.showInputDialog(null, "Enter a value:");
                if (input.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must enter a value", "Error", JOptionPane.ERROR_MESSAGE);
                    getInput(); // Re-prompt for input after showing the error message.
                } else {
                    return Double.parseDouble(input);
                }
            }
        } else if (option == 1) { // MISSING, when clicked it will input -1
            return -1;
        } else if (option == 2) { // GO BACK
            new Triangle();
            return -1;
        } else { // Cancel or close
            System.exit(0);
            return -1;
        }
    }

    // Check if all sides are marked as missing (-1)
    private boolean areAllSidesMissing() {
        return sideA == -1 && sideB == -1 && sideC == -1;
    }

    private void twoSidesWithorWithoutAngle() throws Exception{

        // Check if the sides form a valid right triangle
        if (sideC != -1 && (sideA > sideC || sideB > sideC)) {
            JOptionPane.showMessageDialog(null, "The hypotenuse must be greater than the other sides.", "Invalid Triangle", JOptionPane.ERROR_MESSAGE);
            getInput(); // Re-prompt for input after showing the error message.
        }

        //iF the user enters a value of -1 or missing, the program will calculate the missing value and it will use Pythagorean theorem.
        //!= means if the side# has values.

        // Pythagorean theorem
        if (sideA == -1 && sideB != -1 && sideC != -1) {
            sideA = Math.round(Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2)) * 100) / 100.0; // We use -(negative) here because we transposed the formula
        } else if (sideB == -1 && sideA != -1 && sideC != -1) {
            sideB = Math.round(Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2)) * 100) / 100.0;
        } else if (sideC == -1 && sideA != -1 && sideB != -1) { 
             /* If side C is missing, Calculate sideC using the Pythagorean theorem
             By adding the calculation for side C as shown, the method now covers the case where 
             side C is missing and ensures that all sides and angles can be determined from just two known sides. */
            sideC = Math.round(Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2)) * 100) / 100.0;// Standard a^2 + b^2 = c^2 then square root to get c
        }

        // After calculating the missing side, we can calculate the missing angle using trigonometric functions.
        // TOA Tangent = Opposite side A /Adjacent side B
        // I will use TOA since I have the opposite and adjacent sides.
        // I will use Math.atan to get the angle in radians then convert it to degrees using Math.toDegrees.
        
        // Availability of Sides: If two sides of a right triangle are known and they are the opposite and 
        // adjacent sides relative to one of the non-right angles, then the tangent function is the direct choice because it uses those two sides.

        if (angleA == -1 && sideA != -1 && sideB != -1) { // If angle A is missing and side A and side B are present, we can calculate angle A
            angleA = Math.round(Math.toDegrees(Math.atan(sideA / sideB)) * 100) / 100.0;
            angleB = Math.round((90 - angleA) * 100) / 100.0;
        }
        if (angleB == -1 && sideA != -1 && sideB != -1) { // If angle B is missing and side A and side B are present, we can calculate angle B
            angleB = Math.round(Math.toDegrees(Math.atan(sideB / sideA)) * 100) / 100.0;
            angleA = Math.round((90 - angleB) * 100) / 100.0;
        }

        // If angle A or B is not present and angle B or A is present, we can calculate angle A or B using the formula:  90 - angle B or A
        if (angleA != -1 && angleB == -1) {
            angleB = 90 - angleA;
        } else if (angleB != -1 && angleA == -1) {
            angleA = 90 - angleB;
        }
    }

    private void displayResults() throws Exception {
        String results = "Results:\n\n" +
                "Side a (opposite): " + sideA + "\n" +
                "Side b (adjacent): " + sideB + "\n" +
                "Side c (hypotenuse): " + sideC + "\n\n" +
                "Angle A: " + angleA + " degrees\n" +
                "Angle B: " + angleB + " degrees\n" +
                "Angle C: 90.00 degrees\n" +
                "Total Angle: " + (angleA + angleB + 90.0) + " degrees\n\n";

        Object[] options = { "OK", "SAVE", "GO BACK" };
        int option = JOptionPane.showOptionDialog(null, results, "Calculated Values",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);   
            
        if (option == 0) {
            int calculateAgainOption = JOptionPane.showOptionDialog(null, "Would you like to calculate again?", "Calculate again?",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (calculateAgainOption == JOptionPane.YES_OPTION) {
            new Triangle();
            } else {
            JOptionPane.showMessageDialog(null, "Happy coding!", "Cheers", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            }
        } else if (option == 1) { // SAVE selected
            saveResultsOutside();
        } else if (option == 2) { // GO BACK selected
            new Triangle();
        }
    }


    private void saveResultsOutside() throws Exception {
        File file = new File("Two sides with or without Angles.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        writer.println("Hypotenuse: " + sideC);
        writer.println("Adjacent: " + sideB);   
        writer.println("Opposite: " + sideA + "\n");
        writer.println("Angle A: " + angleA + " degrees");
        writer.println("Angle B: " + angleB + " degrees\n");
        writer.println("Total Angles: " + (angleA + angleB + 90.0) + "degrees\n");
        writer.println("Last updated on: " + new java.util.Date());
        writer.println(); // Adds a newline for separation between entries
        writer.println("=============================================================");

        writer.close();
        JOptionPane.showMessageDialog(null, "Results have been saved to \"Two sides with or without Angles.txt\"",
                "Results Saved", JOptionPane.INFORMATION_MESSAGE);
        int option = JOptionPane.showOptionDialog(null, "Would you like to calculate again?", "Calculate again?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.YES_OPTION) {
            new Triangle();
        } else {
            JOptionPane.showMessageDialog(null, "Happy coding!", "Cheers", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}