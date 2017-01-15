/**
 * Chemical Equation Balancer and Stoichiometry Calculator
 * @author Brad Huang and Charlie Lin
 * @date January 20 2016
 * ICS3U6 (Pre-AP Computer Science) - Mr. Mangat
 * Final Project
 */

// all imports required for a functional program 
import java.util.*;
import java.text.DecimalFormat;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class equationBalancer {
    //static variables for program
    static Element[] elmts;
    static Compound[] comps;
    static int elmtNum, compNum;
    static String eqn = null;
    static int chargeTot;

    //atomicMass array matches the element name array right below it
    //randomEquation generater gives possibilities when random button is clicked
    static double[] atomicMass = {0, 1.01, 4.00, 6.94, 9.01, 10.81, 12.01, 14.01, 16.00, 19.00, 20.18, 22.99, 24.31, 26.98, 28.09, 30.97, 32.07, 35.45, 39.10, 39.95, 40.08, 44.96, 47.87, 50.94, 52.00, 54.94, 55.85, 58.69, 58.93, 63.55, 65.39, 69.72, 72.64, 74.92, 78.96, 79.90, 83.80, 85.47, 87.62, 88.91, 91.22, 92.91, 95.94, 98.00, 101.07, 102.91, 106.42, 107.87, 112.41, 114.82, 118.71, 121.76, 126.90, 127.60, 131.29, 132.91, 137.33, 138.91, 140.12, 140.91, 144.24, 145.00, 150.36, 151.96, 157.25, 158.93, 162.50, 164.93, 167.26, 168.93, 173.04, 174.97, 178.49, 180.95, 183.84, 186.21, 190.23, 192.22, 195.08, 196.97, 200.59, 204.38, 207.20, 208.98, 209.00, 210.00, 222.00, 223.00, 226.00, 227.00, 231.04, 232.04, 237.00, 238.03, 243.00, 244.00, 247.00, 247.00, 251.00, 252.00, 257.00, 258.00, 259.00, 261.00, 262.00, 262.00, 264.00, 266.00, 268.00, 272.00, 277.00, 723.23};
    static String[] elementName = {null, "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "K", "Ar", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Ni", "Co", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "I", "Te", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Pa", "Th", "Np", "U", "Am", "Pu", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Rf", "Lr", "Db", "Bh", "Sg", "Mt", "Rg", "Hs", "Brch"};
    static String[] randomEquations = {"Brch + CuSO4 = Brch2(SO4)3 + Cu", "CuSO4*5H2O = CuSO4 + H2O", "PhCH3 + KMnO4 + H2SO4 = PhCOOH + K2SO4 + MnSO4 + H2O", "Cr2O7[2-] + H[+] = Cr[3+] + H2O", "K4Fe(CN)6 + KMnO4 + H2SO4 = KHSO4 + Fe2(SO4)3 + MnSO4 + HNO3 + CO2 + H2O", "Fe + Cl2 = FeCl3", "N = N2", "A3[-] + B2[2+] = A5B", "C 3 H 5 ( O H ) 3 + O 2 = H 2 O + C O 2", "Foo[5+] + Bar[3-] = FooBar2 + FooBar[-]", "ICl + H2O = Cl[-] + IO3[-] + I2 + H[+]", "HNO3 + Cu = Cu(NO3)2 + H2O + NO", "AB2 + AC3 + AD5 + AE7 + AF11 + AG13 + AH17 + AI19 + AJ23 = A + ABCDEFGHIJ", "C3H8O + O2 = CO2 + H2O", "C = N2", "H + O = H2 + O2", "P4 + OH[-] + H2O = H2PO2[-] + P2H4"};

    //JFrame title
    static JFrame myWindow = new JFrame("Chemical Equation Balancer and Stoichiometry Calculator by BRCH ACADEMIC");

    static JPanel startPanel = new JPanel(); //three static panels that all go onto the main window
    static JPanel resultPanel = new JPanel(); //static so keylistener and action listener can be used later
    static JPanel endPanel = new JPanel();

    static JButton firstButton;
    static JButton finalize;

    static JTextField firstField;
    static JLabel resultLabel = new JLabel("", SwingConstants.CENTER); //use this to center all text

    static final String html1 = "<html><body style='width: "; //alters format and makes balanced equation aligned to center
    static final String html2 = "px'>";

    static JLabel[] compoundLabel; //arrays to match with the above created arrays
    static JLabel[] coeffLabel; //values from arrays will go into the these labels and textfields
    static JTextField[] molarMassField;
    static JTextField[] massField;

    static int compoundColumnNum; //compound integer for keyListener
    static boolean molar; //molar mass integer

    public static void main(String[] args) {
        myWindow.setSize(800, 150);
        myWindow.setLayout(new BorderLayout(15, 40)); //borderLayout for window

        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setResizable(false); //not resizable to prevent layout glitches or changes

        JLabel inputLabel = new JLabel("Input:    ");
        inputLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 17)); //set Font

        firstField = new JTextField("Enter the Chemical Equation Here", 40); //40 character space for long equations
        firstField.selectAll();
        firstField.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));

        JButton firstButton = new JButton("Balance!");
        firstButton.addActionListener(new balanceListener()); //links the balance button to an action listener

        JButton secondButton = new JButton("Random");
        secondButton.addActionListener(new randomListener()); //links the random button to an action listener

        startPanel.add(inputLabel);
        startPanel.add(firstField);
        startPanel.add(firstButton);
        startPanel.add(secondButton);
        myWindow.add(startPanel, BorderLayout.NORTH); //add the input bar to the top of the window

        myWindow.pack(); //shrinks the whole window in an efficient manner
        myWindow.setLocationRelativeTo(null);
        myWindow.setVisible(true);

        while (eqn == null) {  //infinite loop
        }

        resultPanel.setLayout(new BorderLayout(100, 15));

        JLabel noticeLabel = new JLabel("         Balanced Equation: ");
        noticeLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 17));
        resultPanel.add(noticeLabel, BorderLayout.NORTH);

        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        JLabel blankSpace1 = new JLabel("        "); //blankspace to help with border layout to even it
        JLabel blankSpace2 = new JLabel("        ");
        resultPanel.add(blankSpace1, BorderLayout.WEST);
        resultPanel.add(blankSpace2, BorderLayout.EAST);

    }

    static class randomListener implements ActionListener { //ActionListener for random button
        public void actionPerformed(ActionEvent event) {
            Random rdm = new Random();
            eqn = randomEquations[rdm.nextInt(17)]; //takes random from randomEquation list
            firstField.setText(eqn); //sets the text to the selected equation
            calc(eqn); //runs calculation method
            myWindow.revalidate(); //revalidates and refreshes the window
        }
    }

    static class balanceListener implements ActionListener { //ActionListener for balance button
        public void actionPerformed(ActionEvent event) {
            eqn = firstField.getText();  //get text from user
            calc(eqn); //calculate method
            myWindow.revalidate(); //revalidates and refreshes the window
        }
    }

    public static class keyListener implements KeyListener { //keyListener for instataneous molar mass to mass ratio calculations
        int columnNum = compoundColumnNum;
        boolean isMolarMassField = molar;

        public void keyTyped(KeyEvent e) {  //key events make appropriate mass changes immediatiely
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            try {
                if (isMolarMassField){
                    comps[columnNum].molarMass = Double.parseDouble(molarMassField[columnNum].getText());
                }
                calculateMass(Double.parseDouble(massField[columnNum].getText()) / comps[columnNum].molarMass / comps[columnNum].num, isMolarMassField, columnNum);
            } catch (NumberFormatException ex) { //if molarMassField is equal the the source of keyListener, above equation runs again to calculate
                calculateMass(0, isMolarMassField, columnNum);
            }
        }
    }

    public static void calculateMass(double moles, boolean isMolarMassField, int columnNum) { //calculation process for mass using n=m/Mm
        for (int i = 0; i < compNum; i++) {
            try {
                if (Double.parseDouble(molarMassField[i].getText()) == 0)
                    return;
            } catch (NumberFormatException ex) { //catch to not do the above calculation over again to prevent over memory usage
                return;
            }
        }

        for (int i = 0; i < compNum; i++) {
            if ((isMolarMassField) || columnNum != i)
                massField[i].setText(String.valueOf(round2Dec(comps[i].num * comps[i].molarMass * moles)));
        }
    } //more calculations for mass/molar mass etc...

    public static void calc(String eqn) {
        try {
            double[][] mtx = parse(eqn);
            int[] balanced = new int[compNum];
            balanced = balance(mtx);
            for (int i = 0; i < compNum; i++) {
                comps[i].num = balanced[i]; //for loop to advance comps.num by 1 each time until the limit is reached
            } //the answer to the mole numbers

            chargeTot = 0;
            for (int i = 0; i < compNum; i++) {
                chargeTot += comps[i].charge * booInt(comps[i].lr) * balanced[i];
                //System.out.print(comps[i].molarMass + " ");
                // attemts at programming left here to demonstrate trial and error
            }
            //System.out.println(chargeTot);

            String balancedEqn = format(balanced);
            //System.out.println(balancedEqn);

            compoundLabel = new JLabel[compNum]; //comps[i].name
            coeffLabel = new JLabel[compNum]; //comps[i].num
            molarMassField = new JTextField[compNum]; //comps[i].molarMass
            massField = new JTextField[compNum]; //comps[i].num * comps[i].molarMass

            resultLabel.setText(html1 + "600" + html2 + balancedEqn); //format, width 600

            endPanel.removeAll();
            endPanel.setLayout(new GridLayout(compNum + 1, 4, 10, 5)); //grid layout (amount of compounds with arrays)

            // final panel with molar mass, mass and stoich GUI display all aligned to center
            JLabel title1Label = new JLabel("Compound", SwingConstants.CENTER);
            JLabel title2Label = new JLabel("Coefficient", SwingConstants.CENTER);
            JLabel title3Label = new JLabel("Molar Mass(g/mol)", SwingConstants.CENTER);
            JLabel title4Label = new JLabel("Weight(g)", SwingConstants.CENTER);
            endPanel.add(title1Label);
            endPanel.add(title2Label);
            endPanel.add(title3Label);
            endPanel.add(title4Label);

            // sets new fonts
            Font compsFont = new Font("Arial", Font.BOLD, 16);
            Font numFont = new Font("Arial", Font.BOLD, 14);

            //GUI to centralize everything (Lots of hard work!!)
            for (compoundColumnNum = 0; compoundColumnNum < compNum; compoundColumnNum++) {
                compoundLabel[compoundColumnNum] = new JLabel(comps[compoundColumnNum].name, SwingConstants.CENTER); //CENTERED
                compoundLabel[compoundColumnNum].setFont(compsFont);

                coeffLabel[compoundColumnNum] = new JLabel(String.valueOf(comps[compoundColumnNum].num), SwingConstants.CENTER); //CENTERED
                coeffLabel[compoundColumnNum].setFont(compsFont);

                if (comps[compoundColumnNum].molarMass != -1){
                    molarMassField[compoundColumnNum] = new JTextField(String.valueOf(comps[compoundColumnNum].molarMass), 10);
                } else {
                    molarMassField[compoundColumnNum] = new JTextField("", 5); //uses blank spaces to ensure proper alignment and centralization
                }

                molarMassField[compoundColumnNum].setFont(numFont);
                molar = true;
                molarMassField[compoundColumnNum].addKeyListener(new keyListener()); //calls upon keylistener

                massField[compoundColumnNum] = new JTextField("", 5);
                massField[compoundColumnNum].setFont(numFont);
                molar = false; //not molar (boolean false)

                //to mark the source of the following listener's mass instead of the molar mass
                massField[compoundColumnNum].addKeyListener(new keyListener());

                // add components to the endPanel
                endPanel.add(compoundLabel[compoundColumnNum]);
                endPanel.add(coeffLabel[compoundColumnNum]);
                endPanel.add(molarMassField[compoundColumnNum]);
                endPanel.add(massField[compoundColumnNum]);
            }

            // add all components to the myWindow for a finalized look
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.add(endPanel, BorderLayout.SOUTH);
            myWindow.pack();
            myWindow.setLocationRelativeTo(null);
            myWindow.revalidate();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Syntax error! error code: 1");
            resultLabel.setText("Syntax error!");
            JLabel blankSpace3 = new JLabel("        ");
            resultPanel.add(blankSpace3, BorderLayout.SOUTH);
            myWindow.remove(endPanel);
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.pack();
            myWindow.revalidate();
            //if array out of bounds, myWindow will be re-composed and revalidated to prevent error going from correct to error
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Syntax error! error code: 2");
            resultLabel.setText("Syntax error!");
            JLabel blankSpace3 = new JLabel("        ");
            resultPanel.add(blankSpace3, BorderLayout.SOUTH);
            myWindow.remove(endPanel);
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.pack();
            myWindow.revalidate();
            //if string index out of bounds, myWindow will be re-composed and revalidated to prevent error going from correct to error
        } catch (NullPointerException e) {
            System.err.println("Syntax error! error code: 3");
            resultLabel.setText("Syntax error!");
            JLabel blankSpace3 = new JLabel("        ");
            resultPanel.add(blankSpace3, BorderLayout.SOUTH);
            myWindow.remove(endPanel);
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.pack();
            myWindow.revalidate();
            //if null pointer is out of the specified, myWindow will be re-composed and revalidated to prevent error going from correct to error
        } catch (MISException e) {
            System.err.println("Multiple Independent Solutions! error code: 4 " + e.getMessage());
            resultLabel.setText("Multiple Independent Solutions!");
            JLabel blankSpace3 = new JLabel("        ");
            resultPanel.add(blankSpace3, BorderLayout.SOUTH);
            myWindow.remove(endPanel);
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.pack();
            myWindow.revalidate();
            //if equation does not make chemical sense, myWindow will be re-composed and revalidated to prevent error going from correct to error
        } catch (AllZeroException e) {
            System.err.println("All-zero solution! error code: 5");
            resultLabel.setText("All-zero solution!");
            JLabel blankSpace3 = new JLabel("        ");
            resultPanel.add(blankSpace3, BorderLayout.SOUTH);
            myWindow.remove(endPanel);
            myWindow.add(resultPanel, BorderLayout.CENTER);
            myWindow.pack();
            myWindow.revalidate();
            //if there is no solution, myWindow will be re-composed and revalidated to prevent error going from correct to error
        }

    }


    public static double[][] parse(String eqn) throws MISException {
        eqn = eqn.replaceAll("\\s+", "");     // remove all spaces from the equation
        eqn = eqn.replaceAll("\\+]", "\\@@");
        eqn = eqn.replaceAll("\\-]", "\\##");

        elmtNum = parseElements(eqn); //parse element number
        compNum = parseCompoundsNum(eqn); //parse compound number

        comps = new Compound[compNum];
        comps = parseCompounds(eqn); //parses all properties of the compound

        boolean boo = true; //makes true only to make false later (used for temporary case setting)

        double[][] matrix = new double[elmtNum + 1][compNum + 1]; //sets up matrix to be prepared for reducing
        for (int i = 0; i < elmtNum; i++) {
            for (int j = 0; j < compNum; j++) {
                matrix[i][j] = comps[j].atomNum[i];
            }
        }

        if (compNum - 1 > elmtNum) { //inspects if an equation has more than one compound than the elements
            for (int i = 0; i < compNum; i++) {
                if (comps[i].charge != 0) {
                    boo = false;
                    break; //BREAKS OUT WHEN FALSE
                }
            }

            if (boo) //equation not solvable gives the exception
                throw new MISException("element = " + String.valueOf(elmtNum) + " compound = " + String.valueOf(compNum));
            else {   // add another row of charges to make balanced
                for (int i = 0; i < compNum; i++) {
                    matrix[elmtNum][i] = booInt(comps[i].lr) * comps[i].charge;
                }
            }
        }

        return matrix; //return the above matrix
    }

    public static int gcf(int[] input) { //greatest common factor in the list
        int gcfNum = gcf(input[0], input[1]);
        for (int i = 2; i < input.length; i++) {
            if (input[i] != 0) {
                gcfNum = gcf(gcfNum, input[i]);
                if (gcfNum == 1)
                    return 1;
            }
        }
        return gcfNum;
    }

    public static int gcf(int m, int n) { //determine greatest commmon factor between 2 numbers
        int a = Math.max(m, n), b = Math.min(m, n), divisor;
        for (int i = 1; i < b; i++) {
            if (b % i == 0) {
                divisor = b / i;
                if (a % divisor == 0) {
                    return Math.abs(divisor);
                }
            }
        }
        return 1;
    }

    public static String format(int[] atomicNums) { //formatting and colour, using html to bypass border layout restrictions
        String balancedEqn = "<html>";
        for (int i = 0; i < compNum; i++) {
            if (i != 0)
                balancedEqn += " "; //provide a space for formatting
            if (atomicNums[i] != 1)
                balancedEqn += "<font color=red>" + String.valueOf(atomicNums[i]) + "</font>"; //add red number of moles for each compound
            balancedEqn += subsupscript(comps[i].name); // add compound name with subscripts and superscripts
            if (i != compNum - 1)
                balancedEqn += " ";
            if (comps[i].lr == true && comps[i + 1].lr == false)
                balancedEqn += "="; //if applicable add equal sign
            else if (i != compNum - 1)
                balancedEqn += "+";  //if applicable add plus sign
        }

        if (chargeTot < 0) {
            if (chargeTot == -1)
                balancedEqn += " + " + "e<sup>-</sup>";  //add electrons when applicable
            else
                balancedEqn += " + " + String.valueOf(Math.abs(chargeTot)) + "e<sup><font color=blue>-</font></sup>";
        } else if (chargeTot > 0) {
            if (chargeTot == 1)
                balancedEqn = balancedEqn.substring(0, balancedEqn.indexOf(" = ")) + "+ " + "e<sup><font color=blue>-</font></sup> " + balancedEqn.substring(balancedEqn.indexOf(" = "));
            else
                balancedEqn = balancedEqn.substring(0, balancedEqn.indexOf(" = ")) + "+ " + String.valueOf(Math.abs(chargeTot)) + "e<sup><font color=blue>-</font></sup> " + balancedEqn.substring(balancedEqn.indexOf(" = "));
        }
        balancedEqn += "</html>";
        return balancedEqn;
    }

    public static String subsupscript(String str) {  // add subscripts and superscripts into the names of the compounds
        char[] strArr = str.toCharArray();
        String subStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(strArr[i])) {
                try {
                    if (strArr[i - 1] != '*')
                        subStr += "<sub>" + str.substring(i, i + 1) + "</sub>";   // add subscripts into numbers if nut preceeded by an asterisk
                    else
                        subStr += str.substring(i, i + 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    subStr += "<sub>" + str.substring(i, i + 1) + "</sub>";
                }
            } else
                subStr += str.substring(i, i + 1);
        }
        subStr = subStr.replaceAll("\\[", "\\<sup><font color=blue>");     // add superscripts into charges
        subStr = subStr.replaceAll("\\]", "\\</font></sup>");

        return subStr;
    }

    public static int[] balance(double[][] M) throws AllZeroException {     // reduce the matrix and extract the balanced mole number coefficents
        int rowCount = M.length;
        int columnCount = M[0].length;
        int gcfNum, numCount = 0, zeroCount = 0;

        double multiplyCoeff = 1;
        int[] balanced = new int[columnCount - 1];
        int lead = 0;     // reduce every line starting from the first

        for (int r = 0; r < rowCount; r++) {
            if (lead >= columnCount)     // reduce through every lines
                break;
            {
                int i = r;
                while (M[i][lead] == 0) {
                    i++;
                    if (i == rowCount) {    // find the reduction number
                        i = r;
                        lead++;
                        if (lead == columnCount) {     // determine if finished reducing
                            for (int a = 0; a < rowCount; a++) {
                                for (int b = 0; b < columnCount; b++) {
                                    if (M[a][b] != 0 && M[a][b] != 1)
                                        numCount++;
                                    //System.out.print(M[a][b] + " ");
                                }
                                if (numCount == 0)
                                    zeroCount++;
                                //System.out.println();
                            }
                            if (zeroCount == rowCount)      // determine if numbers are all zero
                                throw new AllZeroException();
                            for (int j = 0; j < columnCount - 2; j++) {
                                balanced[j] = (int) Math.round(-M[j][columnCount - 2] * Math.abs(multiplyCoeff));     // transfer each valid number mole coefficents into an array
                            }
                            balanced[columnCount - 2] = (int) Math.round(Math.abs(multiplyCoeff));
                            gcfNum = gcf(balanced);
                            for (int j = 0; j <= columnCount - 2; j++) {
                                balanced[j] /= gcfNum;
                            }
                            return balanced;
                        }
                    }
                }
                double[] temp = M[r];
                M[r] = M[i];
                M[i] = temp;
            }

            {
                double lv = M[r][lead];
                multiplyCoeff *= lv;     // record all recuding numbers for multiplying
                for (int j = 0; j < columnCount; j++)
                    M[r][j] /= lv;     // reduce the line
            }

            for (int i = 0; i < rowCount; i++) {
                if (i != r) {
                    double lv = M[i][lead];
                    for (int j = 0; j < columnCount; j++)
                        M[i][j] -= lv * M[r][j];      // clear a line and complete the reduction
                }
            }
            lead++;     // proceed to the next line
        }

        for (int a = 0; a < rowCount; a++) {     // consolidate al valid numbers into array as previously mentioned
            for (int b = 0; b < columnCount; b++) {
                if (M[a][b] != 0 && M[a][b] != 1)
                    numCount++;
                //System.out.print(M[a][b] + " ");
            }
            if (numCount == 0)
                zeroCount++;
            //System.out.println();
        }
        if (zeroCount == rowCount)
            throw new AllZeroException();
        for (int j = 0; j < columnCount - 2; j++) {
            balanced[j] = (int) Math.round(-M[j][columnCount - 2] * Math.abs(multiplyCoeff));
        }
        balanced[columnCount - 2] = (int) Math.round(Math.abs(multiplyCoeff));
        gcfNum = gcf(balanced);
        for (int j = 0; j <= columnCount - 2; j++) {
            balanced[j] /= gcfNum;
        }
        return balanced;
    }

    public static int parseElements(String eqn) {    // accumulate element numbers and form each element
        ArrayList<String> list = new ArrayList<String>();
        char[] eqnArr = eqn.toCharArray();
        int elmtNum, frontNum;

        for (int i = 0; i < eqn.length(); i++) {
            if ((int) eqnArr[i] > 64 && (int) eqnArr[i] < 91) {     // find an upper cased letter
                frontNum = i;
                if (i < eqn.length() - 1) {
                    try {
                        while ((int) eqnArr[i + 1] > 96 && (int) eqnArr[i + 1] < 123) {     // record every lower cased letter that follows
                            if (i != eqn.length() - 1)
                                i++;
                            else
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                if (!list.contains(eqn.substring(frontNum, i + 1))) {
                    list.add(eqn.substring(frontNum, i + 1));     // use lists as we are not sure how many we can find
                }
            }
        }

        elmtNum = list.size();
        elmts = new Element[elmtNum];

        for (int i = 0; i < elmtNum; i++) {
            elmts[i] = new Element(list.get(i));     // transfer all Elements into the array
        }

        return elmtNum;
    }

    public static int parseCompoundsNum(String eqn) {     // accumulate compound numbers using recursive
        if (eqn.isEmpty()) {
            return 2;
        } else if (eqn.substring(0, 1).equals("+")) {     // compound numbers = number of "+" + 2 (+ repesent charge have been eleminated)
            return parseCompoundsNum(eqn.substring(1)) + 1;
        } else {
            return parseCompoundsNum(eqn.substring(1));
        }
    }

    public static Compound[] parseCompounds(String eqn) {     // form an array of compounds
        int i = 0;
        boolean containsEqualSign;
        String name;
        while (!eqn.isEmpty()) {
            if (eqn.indexOf("=") == -1) {
                containsEqualSign = false;     // determine which side the compound is on by eliminating the already parsed
                if (eqn.indexOf("+") == -1) {
                    name = eqn;
                    eqn = "";
                } else {
                    name = eqn.substring(0, eqn.indexOf("+"));
                    eqn = eqn.substring(eqn.indexOf("+") + 1);
                }
            } else {
                containsEqualSign = true;
                if (eqn.indexOf("+") == -1) {
                    name = eqn.substring(0, eqn.indexOf("="));
                    eqn = eqn.substring(eqn.indexOf("=") + 1);
                    ;
                } else {
                    name = eqn.substring(0, Math.min(eqn.indexOf("+"), eqn.indexOf("=")));
                    eqn = eqn.substring(Math.min(eqn.indexOf("+"), eqn.indexOf("=")) + 1);
                }
            }
            comps[i] = new Compound(name, containsEqualSign);
            i++;
        }
        return comps;
    }

    public static int booInt(boolean boo) {     // returns a numerical value of a boolean
        if (boo)
            return 1;
        else
            return -1;
    }

    public static double round2Dec(double d) {    // format the doubles into two decimal form
        DecimalFormat twoDForm = new DecimalFormat("####.##");
        return Double.valueOf(twoDForm.format(d));
    }

    static class Element {     // handle each element
        String name;
        double elementMass = -1;

        public Element(String name) {
            this.name = name;     // assign elements with their names
            for (int i = 0; i < 112; i++) {
                if (name.equals(elementName[i])) {
                    this.elementMass = atomicMass[i];    // assign the element with their atomic masses
                    break;
                }
            }
        }
    }

    static class Compound {     // handle each compound
        String name = "";
        double[] atomNum;
        boolean lr = true;
        int charge = 0;
        int num;
        double molarMass = 0;

        public Compound(String name, boolean lr) {
            this.lr = lr;    // assign compounds with their side, left = true , right = false
            this.atomNum = new double[elmtNum];
            char[] nameArr = name.toCharArray();
            int frontNum = 0, iGrowth;

            for (int i = 0; i < nameArr.length; i++) {
                iGrowth = 0;
                if ((int) nameArr[i] > 64 && (int) nameArr[i] < 91) {     // search for upper cased letters
                    frontNum = i;
                    try {
                        while ((int) nameArr[i + 1] > 96 && (int) nameArr[i + 1] < 123) {     // include every lower cased letter that follows
                            if (i < name.length() - 1)
                                i++;
                            else
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    if (i < name.length() - 1) {
                        try {
                            while ((int) nameArr[i + 1] > 47 && (int) nameArr[i + 1] < 58) {     // search for integers
                                if (i < name.length() - 1) {
                                    i++;
                                    iGrowth++;
                                } else
                                    break;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                    for (int j = 0; j < elmts.length; j++) {
                        if (elmts[j].name.equals(name.substring(frontNum, i - iGrowth + 1))) {
                            // accumulate the number of apperance of an element in a compound that are to be transferred into the matrix
                            if (iGrowth == 0)
                                this.atomNum[j] = this.atomNum[j] + booInt(this.lr);
                            else {
                                this.atomNum[j] = this.atomNum[j] + booInt(this.lr) * Integer.parseInt(name.substring(i - iGrowth + 1, i + 1));
                            }
                        }
                    }
                } else if (nameArr[i] == '[') {
                    // accumulate the charges of a compound if a '[' appears
                    if (name.indexOf("@@") == -1) {
                        if (name.substring(i + 1, name.indexOf("##")).equals(""))    // ## represent negative charges
                            this.charge = -1;
                        else
                            this.charge = -Integer.parseInt(name.substring(i + 1, name.indexOf("##")));
                        // replace the name of each compound back to resume a normal apparance
                        name = name.replaceAll("\\##", "\\-]");
                    } else if (name.indexOf("##") == -1) {
                        if (name.substring(i + 1, name.indexOf("@@")).equals(""))    // @@ represent negative charges
                            this.charge = 1;
                        else
                            this.charge = Integer.parseInt(name.substring(i + 1, name.indexOf("@@")));
                        // replace the name of each compound back to resume a normal apparance
                        name = name.replaceAll("\\@@", "\\+]");
                    }
                } else if (nameArr[i] == '(') {     // handle cases with parentheses
                    String temp = name.substring(name.indexOf('(', i) + 1, name.indexOf(')', i));     // extract the subcompound inside the bracket
                    char[] tempArr = temp.toCharArray();
                    int tempIndex = Integer.parseInt(String.valueOf(name.charAt(name.indexOf(')', i) + 1)));
                    for (int j = 0; j < temp.length(); j++) {     // parse the subcompound
                        iGrowth = 0;
                        if ((int) tempArr[j] > 64 && (int) tempArr[j] < 91) {     // search for upper cased letters in subcompounds
                            frontNum = j;
                            if (j < temp.length() - 1) {
                                while ((int) tempArr[j + 1] > 96 && (int) tempArr[j + 1] < 123) {     // include every lower cased letter that follows in subcompounds
                                    if (j < temp.length() - 1)
                                        j++;
                                    else
                                        break;
                                }
                            }
                            if (j < temp.length() - 1) {
                                try {
                                    while ((int) tempArr[j + 1] > 47 && (int) tempArr[j + 1] < 58) {     // search for integers in subcompounds
                                        if (j < temp.length() - 1) {
                                            j++;
                                            iGrowth++;
                                        } else
                                            break;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }
                            }
                            for (int k = 0; k < elmts.length; k++) {
                                if (elmts[k].name.equals(temp.substring(frontNum, j - iGrowth + 1))) {     // assign with the appearance frequency in subcompounds
                                    if (iGrowth == 0)
                                        this.atomNum[k] = this.atomNum[k] + tempIndex * booInt(this.lr);
                                    else {
                                        this.atomNum[k] = this.atomNum[k] + tempIndex * booInt(this.lr) * Integer.parseInt(temp.substring(j - iGrowth + 1, j + 1));
                                    }
                                }
                            }
                        }
                    }
                    i = name.indexOf(')', i) + 1;
                } else if (nameArr[i] == '*') {
                    String temp = name.substring(name.indexOf('*', i) + 2);     // extract the subcompound inside the bracket
                    char[] tempArr = temp.toCharArray();
                    int tempIndex = Integer.parseInt(String.valueOf(name.charAt(name.indexOf('*', i) + 1)));
                    for (int j = 0; j < temp.length(); j++) {     // parse the subcompound
                        iGrowth = 0;
                        if ((int) tempArr[j] > 64 && (int) tempArr[j] < 91) {     // search for upper cased letters in subcompounds
                            frontNum = j;
                            if (j < temp.length() - 1) {
                                while ((int) tempArr[j + 1] > 96 && (int) tempArr[j + 1] < 123) {     // include every lower cased letter that follows in subcompounds
                                    if (j < temp.length() - 1)
                                        j++;
                                    else
                                        break;
                                }
                            }
                            if (j < temp.length() - 1) {
                                try {
                                    while ((int) tempArr[j + 1] > 47 && (int) tempArr[j + 1] < 58) {     // search for integers in subcompounds
                                        if (j < temp.length() - 1) {
                                            j++;
                                            iGrowth++;
                                        } else
                                            break;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }
                            }
                            for (int k = 0; k < elmts.length; k++) {
                                if (elmts[k].name.equals(temp.substring(frontNum, j - iGrowth + 1))) {     // assign with the appearance frequency in subcompounds
                                    if (iGrowth == 0)
                                        this.atomNum[k] = this.atomNum[k] + tempIndex * booInt(this.lr);
                                    else {
                                        this.atomNum[k] = this.atomNum[k] + tempIndex * booInt(this.lr) * Integer.parseInt(temp.substring(j - iGrowth + 1, j + 1));
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
            this.name = name;
            for (int i = 0; i < elmtNum; i++) {
                // calculate molar mass of the compound
                if (elmts[i].elementMass == -1 && this.atomNum[i] != 0) {
                    this.molarMass = 0;
                    break;
                } else {
                    this.molarMass += elmts[i].elementMass * Math.abs(this.atomNum[i]);
                }
            }
            this.molarMass = round2Dec(this.molarMass);
        }
    }
}