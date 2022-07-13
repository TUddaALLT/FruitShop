/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

import java.util.ArrayList;
import java.util.HashMap; 

/**
 *
 * @author 84352
 */
public class Manager {

    Validate validate = new Validate();
    ArrayList<Fruit> fruits = new ArrayList<>();
    HashMap<String, ArrayList<Fruit>> Orders = new HashMap<String, ArrayList<Fruit>>();
    public void createFruit() {
        do {
            String fruitId;
            do {
                fruitId = validate.checkInputString("Enter Fruit Id: ");
                if (validate.checkID(fruitId, fruits) == null) {
                    break;
                }
            } while (true);
            String fruitName = validate.checkInputString("Enter Fruit Name: ");
            double Price = validate.checkDoubleLimit(1, Double.MAX_VALUE, "Enter Price: ", "Price must be digit >=1");
            int Quantity = validate.checkIntLimit(1, Integer.MAX_VALUE, "Enter Quantity: ", "Quatity must be digit >=1");
            String Origin = validate.checkInputString("Enter Origin: ");
            fruits.add(new Fruit(fruitId, fruitName, Origin, Quantity, Price));
            String choose = validate.checkOption("y|n", "Do you want to continue (Y/N)? :  ", "Must be Y or N");
            if (choose.equalsIgnoreCase("n")) {
                break;
            }
        } while (true);
        display(fruits, "all");
    }

    private ArrayList<Fruit> display(ArrayList<Fruit> fruits, String type) {
        ArrayList<Fruit> fruitsCanBuy = new ArrayList<>();
        if (type.equals("all")) {
            System.out.printf("| ++ Item ++ | ++ Fruit Name ++ | ++ Quantity ++ | ++ Price ++ |\n");
            int index = 1;
            for (Fruit fruit : fruits) {
                System.out.printf("%-20d%-20s%-13d%-20s\n", index,
                        fruit.getFruitName(),
                        fruit.getQuantity(), fruit.getPrice());
                index++;
            }
        } else if(type.equals(">0")) {
            System.out.printf("| ++ Item ++ | ++ Fruit Name ++ | ++ Quantity ++ | ++ Price ++ |\n");
            int index = 1;
            for (Fruit fruit : fruits) {
                if (fruit.getQuantity() > 0) {
                    System.out.printf("%-20d%-20s%-13d%-20s\n", index,
                            fruit.getFruitName(),
                            fruit.getQuantity(), fruit.getPrice());
                    fruitsCanBuy.add(fruit);
                } else {
                    index--;
                }
                index++;
            }
        }
        return fruitsCanBuy;
    }

    public void menu() {
        fruits.add(new Fruit("1", "tao", "vn", 10, 1));
        fruits.add(new Fruit("2", "cam", "nbb", 2, 4));

        fruits.add(new Fruit("0", "le", "sad", 33, 5));

        fruits.add(new Fruit("4", "dua", "vfdgfn", 5, 3));

        do {
            System.out.println(" FRUIT SHOP SYSTEM\n"
                    + "1.	Create Fruit\n"
                    + "2.	View orders\n"
                    + "3.	Shopping (for buyer)\n"
                    + "4.	Exit");
            int choice = validate.checkIntLimit(1, 4, "Choose:  ", "Must be 1->5");
            switch (choice) {
                case 1:
                    createFruit();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    shopping();
                    break;
                case 4:
                    return;
            }
        } while (true);
    }

    private void shopping() {
        ArrayList<Fruit> fruitsYouBuy = new ArrayList<>();
        ArrayList<Fruit> fruitsCanBuy = display(fruits, ">0");
        if (fruitsCanBuy.isEmpty()) {
            System.out.println("No fruit to buy");
            return;
        }
        do {
            int choose = validate.checkIntLimit(1, fruitsCanBuy.size() + 1, "Selects item:  ", "Over list");
            Fruit youChoose = fruitsCanBuy.get(choose - 1);
            System.out.println("You are selected:" + youChoose.getFruitName());
            int quantity = validate.checkIntLimit(0, youChoose.getQuantity(), "Please input quantity:",
                    "Quatity is over");
            youChoose.setQuantity(youChoose.getQuantity() - quantity);
            if (youChoose.getQuantity() == 0) {
                fruitsCanBuy.remove(youChoose);
            }
            Fruit fruitInOrder = validate.checkID(youChoose.getFruitId(), fruitsYouBuy);
            if (fruitInOrder != null) {
                fruitInOrder.setQuantity(fruitInOrder.getQuantity() + quantity);
            } else {
                if (quantity > 0) {
                    fruitsYouBuy.add(new Fruit(youChoose.getFruitId(), youChoose.getFruitName(),
                            youChoose.getOrigin(), quantity, youChoose.getPrice()));
                }

            }
            String yN = validate.checkOption("y|n", ": Do you want to order now (Y/N)? :  ", "Must be Y or N");
            if (yN.equalsIgnoreCase("y")) {
                break;
            } else {
                display(fruitsCanBuy, ">0");
            }

        } while (true);
        if (fruitsYouBuy.isEmpty()) {
            System.out.println("No quantity No order");
            return;
        } else {
            String name = validate.checkInputString("\nInput your name: ");
            System.out.println(java.time.LocalDateTime.now());
            printOrders(fruitsYouBuy);
            if (Orders.containsKey(name)) {
                name = name + "+" + java.time.LocalDateTime.now();
            }
            Orders.put(name, fruitsYouBuy);
        }

    }

    private void printOrders(ArrayList<Fruit> fruits) {
        System.out.println("Product | Quantity | Price | Amount\n");
        int total = 0;
        for (Fruit fruit : fruits) {
            System.out.printf("%-17s%-13s%-13s%-13s\n", fruit.getFruitName(),
                    fruit.getQuantity(), fruit.getPrice(), fruit.getQuantity()
                    * fruit.getPrice());
            total += fruit.getQuantity()
                    * fruit.getPrice();
        }
        System.out.println("Total:" + total);
       
    }

    private void viewOrders() {
        Orders.forEach((t, u) -> {
            if (!t.contains("+")) {
                System.out.println("Customer: " + t);
                printOrders(u);
                Orders.forEach((t1, u1) -> {
                    if (t1.length() > t.length()) {
                        t1 = t1.split("\\+")[0].trim();
                        if (t1.equalsIgnoreCase(t)) {
                            printOrders(u1);
                        }
                    }
                });
                 System.out.println("--------------------------------------");
            }
        });
    }
}
