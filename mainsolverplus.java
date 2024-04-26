//PADUADA, Dave Jhared G.
// BSCpE II - IF
// T & TH 10:30 - 13:30

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;

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
        calculateMissingValues();
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
        String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
        if (input.equals("")) {
            return -1;
        }
        return Double.parseDouble(input);
    }
    

    private void calculateMissingValues() throws Exception {
        if (sideA == -1 && sideB != -1 && sideC != -1) { // Solve for sideA
            sideA = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideB, 2));
        } else if (sideB == -1 && sideA != -1 && sideC != -1) { // Solve for sideB
            sideB = Math.sqrt(Math.pow(sideC, 2) - Math.pow(sideA, 2));
        } else if (sideC == -1 && sideA != -1 && sideB != -1) { // Solve for sideC
            sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));
        }
    
        // Validate and calculate angles using trigonometric ratios if possible
        if (angleA == -1 && sideA != -1 && sideC != -1) { // Solve for angle A using sine
            angleA = Math.toDegrees(Math.asin(sideA / sideC));
        }
        if (angleB == -1 && sideB != -1 && sideC != -1) { // Solve for angle B using cosine
            angleB = Math.toDegrees(Math.acos(sideB / sideC));
        }
    }
    

    private void displayResults() {
        String results = "Results:\n" +
                         "Side a (opposite): " + sideA + "\n" +
                         "Side b (adjacent): " + sideB + "\n" +
                         "Side c (hypotenuse): " + sideC + "\n" +
                         "Angle A: " + angleA + " degrees\n" +
                         "Angle B: " + angleB + " degrees\n"+
                         "Angle C: 90.0 degrees\n";
        JOptionPane.showMessageDialog(null, results, "Calculated Values", JOptionPane.INFORMATION_MESSAGE);
    }
}