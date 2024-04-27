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
        new WelcomeMessage();
    }
}

class WelcomeMessage {
    WelcomeMessage() throws Exception {
        String[] options = { "START", "EXIT" };

        int choice = JOptionPane.showOptionDialog(null,
                "Welcome to the Main Solver+ !\nThis program will help you solve your triangle math\n\nClick START to continue",
                "Main Solver+", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            new Triangle();
        } else {
            System.exit(0);
        }
    }
}

class Triangle {
    Triangle() throws Exception {

        String[] choices = { "-- Please select --", "1 Side and 1 Angle", "2 Sides with or without Angles", "Exit" };

        String part = (String) JOptionPane.showInputDialog(null, "How many givens?",
                "Type of Given", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if ("-- Please select --".equals(part)) {
            new Triangle();
        } else if ("1 Side and 1 Angle".equals(part)) {
            new OneAngleOneSide();
            /* new LeftSideDegree() */;
        } else if ("2 Sides with or without Angles".equals(part)) {
            new Sideside();
        } else {
            System.exit(0);
        }
    }
}

class OneAngleOneSide {
    private double sideA; // Opposite
    private double sideB; // Adjacent
    private double sideC; // Hypotenuse
    private double angleA;
    private double angleB;
    private double[] sides = new double[3]; // sideA, sideB, sideC
    private double[] angles = new double[2]; // angleA, angleB (angleC is always 90 degrees)

    

    OneAngleOneSide() throws Exception {
        getInput();
        if (areAllSidesMissing()) {
            JOptionPane.showMessageDialog(null, "At least one side must contain a value.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            getInput();
        }
        oneSideWithAngle();
        displayResults();
        // Initialize all sides and angles to -1 (indicating that they are not set)
        for (int i = 0; i < 3; i++) {
            sides[i] = -1;
        }
        for (int i = 0; i < 2; i++) {
            angles[i] = -1;
        }
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

    private void oneSideWithAngle() throws Exception {

        // Check if the sides form a valid right triangle
        if (sideC != -1 && (sideA > sideC || sideB > sideC)) {
            JOptionPane.showMessageDialog(null, "The hypotenuse must be greater than the other sides.", "Invalid Triangle", JOptionPane.ERROR_MESSAGE);
            getInput(); // Re-prompt for input after showing the error message.
        }

        // Pythagorean theorem
        if (sideA == -1 && sideB != -1 && sideC != -1) {
            sideA = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2)); // We use -(negative) here because we transposed the formula
        } else if (sideB == -1 && sideA != -1 && sideC != -1) {
            sideB = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2));
        } else if (sideC == -1 && sideA != -1 && sideB != -1) {
            sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));// Standard a^2 + b^2 = c^2 then square root to get c
        }

        if (sideA != -1 && sideB == -1 && sideC == -1 && angleA != -1 && angleB == -1) {
            angleA = Math.toRadians(angleA); // converting to radians
            sideB = sideA / Math.tan(angleA); // adjacent = opposite / tan(angle)
            sideC = sideA / Math.sin(angleA); // hypotenuse = opposite / sin(angle)
        } 

        else if (sideA == -1 && sideB != -1 && sideC == -1 && angleA != -1 && angleB == -1) {
            angleA = Math.toRadians(angleA); // converting to radians
            sideA = sideB * Math.tan(angleA); // opposite = adjacent * tan(angle)
            sideC = sideB / Math.cos(angleA); // hypotenuse = adjacent / cos(angle)
        } 

        else if (sideA == -1 && sideB == -1 && sideC != -1 && angleA != -1 && angleB == -1) {
            angleA = Math.toRadians(angleA); // converting to radians
            sideA = sideC * Math.sin(angleA); // opposite = hypotenuse * sin(angle)
            sideB = sideC * Math.cos(angleA); // adjacent = hypotenuse * cos(angle)
        }
        
        else if (sideA != -1 && sideB != -1 && sideC == -1 && angleA == -1 && angleB == -1) {
            sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2)); // hypotenuse = sqrt(a^2 + b^2)
            angleA = Math.toDegrees(Math.atan(sideA / sideB)); // angle A = arctan(opposite/adjacent)
            angleB = 90 - angleA; // angle B = 90 - angle A
        } 
        
        else if (sideA != -1 && sideB == -1 && sideC != -1 && angleA == -1 && angleB == -1) {
            angleA = Math.toDegrees(Math.asin(sideA / sideC)); // angle A = arcsin(opposite/hypotenuse)
            sideB = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2)); // adjacent = sqrt(c^2 - a^2)
            angleB = 90 - angleA; // angle B = 90 - angle A
        } 
        
        else if (sideA == -1 && sideB != -1 && sideC != -1 && angleA == -1 && angleB == -1) {
            angleA = Math.toDegrees(Math.acos(sideB / sideC)); // angle A = arccos(adjacent/hypotenuse)
            sideA = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2)); // opposite = sqrt(c^2 - b^2)
            angleB = 90 - angleA; // angle B = 90 - angle A
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
                "Angle A: " + angleA + " degrees\n" + // Assume angleA is already in degrees
                "Angle B: " + angleB + " degrees\n" + // Assume angleB is already in degrees
                "Angle C: 90.0 degrees\n" +
                "Total Angle: " + (angleA + angleB + 90.0) + " degrees\n\n";
    
        Object[] options = { "OK", "SAVE", "GO BACK" };
        int option = JOptionPane.showOptionDialog(null, results, "Calculated Values",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
        if (option == 1) { // SAVE selected
            saveResultsOutside();
        } else if (option == 2) { // GO BACK selected
            new Triangle();
        }
    }
    

    private void saveResultsOutside() throws Exception {
        File file = new File("One Side One Angle.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        writer.println("Hypotenuse: " + sideC);
        writer.println("Adjacent: " + sideB);
        writer.println("Opposite: " + sideA + "\n");
        writer.println("Angle A: " + Math.toDegrees(angleA) + " degrees");
        writer.println("Angle B: " + Math.toDegrees(angleB) + " degrees\n");
        writer.println("Total Angles: " + (Math.toDegrees(angleA) + Math.toDegrees(angleB) + 90.0) + "degrees\n");
        writer.println("Last updated on: " + new java.util.Date());
        writer.println(); // Adds a newline for separation between entries

        writer.close();

        JOptionPane.showMessageDialog(null, "Results have been saved to Midterm Project/triangle_sides.txt",
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
            sideA = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2)); // We use -(negative) here because we transposed the formula
        } else if (sideB == -1 && sideA != -1 && sideC != -1) {
            sideB = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2));
        } else if (sideC == -1 && sideA != -1 && sideB != -1) {
            sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));// Standard a^2 + b^2 = c^2 then square root to get c
        }

        // After calculating the missing side, we can calculate the missing angle using trigonometric functions.
        if (angleA == -1 && sideA != -1 && sideB != -1) { // TOA Tangent = Opposite side A /Adjacent side B
            angleA = Math.round(Math.toDegrees(Math.atan(sideA / sideB)) * 10) / 10.0;
            angleB = Math.round((90 - angleA) * 10) / 10.0;
        }
        if (angleB == -1 && sideA != -1 && sideB != -1) {
            angleB = Math.round(Math.toDegrees(Math.atan(sideB / sideA)) * 10) / 10.0;
            angleA = Math.round((90 - angleB) * 10) / 10.0;
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
                "Angle C: 90.0 degrees\n" +
                "Total Angle: " + (angleA + angleB + 90.0) + " degrees\n\n";

        Object[] options = { "OK", "SAVE", "GO BACK" };
        int option = JOptionPane.showOptionDialog(null, results, "Calculated Values",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (option == 1) { // SAVE selected
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

        writer.close();

        JOptionPane.showMessageDialog(null, "Results have been saved to Midterm Project/triangle_sides.txt",
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