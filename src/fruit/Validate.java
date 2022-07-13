/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 84352
 */
public class Validate {
    Scanner in = new Scanner(System.in);
    String checkInputString(String mess) {
        while (true) {
            System.out.println(mess);
            String result = in.nextLine().trim();
            if (result.length() == 0) {
                System.err.println("Must be alphabet");
            } else {
                return result;
            }

        }
    }
     String checkOption(String str, String mess , String error) {
        while (true) {
            try {
                System.out.println(mess);
                String result = in.nextLine().trim().toLowerCase();

                if (result.matches(str)) {
                    return result;
                } else {
                    System.err.println(error);
                }
            } catch (NumberFormatException ex) {
                System.err.println(error);

            }
        }
      
    }
    int checkIntLimit(int min, int max,String mess, String error) {
        while (true) {
            try {
                System.out.println(mess);
                int n = Integer.parseInt(in.nextLine());
                if (n < min || n > max) {
                    throw new NumberFormatException();
                }
                return n;
            } catch (NumberFormatException ex) {
                System.err.println(error);
            }
        }
    }
    double checkDoubleLimit(double min, double max, String mess, String error) {
        while (true) {
            try {
                System.out.println(mess);
                double n = Double.parseDouble(in.nextLine());
                if (n < min || n > max) {
                    throw new NumberFormatException();
                }
                return n;
            } catch (NumberFormatException ex) {
                System.err.println(error);
            }
        }
    }
    Fruit checkID(String id, ArrayList<Fruit> fruits ){
        for (Fruit fruit : fruits) {
            if(fruit.getFruitId().equalsIgnoreCase(id)){
                return fruit;
            }
        }
        return null;
    }
}
