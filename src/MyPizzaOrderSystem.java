import java.util.*;

public class MyPizzaOrderSystem {
    Scanner scanner = new Scanner(System.in);
    int ordreNr = 0;
    List<Bestilling> bestillinger = new ArrayList<>();

    public void start() {
        while (true) {
            System.out.println("1: Create Order");
            System.out.println("2: Delete Order");
            System.out.println("3: Exit");

            int valg = scanner.nextInt();
            scanner.nextLine(); // ✅ Fix input issues

            if (valg == 1) {
                ordreNr++; // ✅ Increment order number
                Bestilling newBestilling = new Bestilling(ordreNr);

                System.out.println("Choose pizzas for your order (type 'done' when finished):");

                while (true) {
                    // ✅ Show menu first
                    for (Menu pizza : Menu.values()) {
                        System.out.println(pizza.getNr() + ": " + pizza.getName() + " - " + pizza.getPrice() + " DKK");
                    }

                    System.out.println("Enter pizza number (or type 'done' to finish):");
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("done")) {
                        break; // ✅ Exit the loop when done
                    }

                    try {
                        int pizzaNumber = Integer.parseInt(input);

                        // ✅ Find pizza in menu
                        Menu chosenPizza = null;
                        for (Menu pizza : Menu.values()) {
                            if (pizza.getNr() == pizzaNumber) {
                                chosenPizza = pizza;
                                break;
                            }
                        }

                        if (chosenPizza != null) {
                            System.out.println("How many " + chosenPizza.getName() + " pizzas would you like?");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // ✅ Fix newline issue

                            for (int i = 0; i < quantity; i++) {
                                newBestilling.tilfojVare(chosenPizza);
                            }

                            System.out.println(quantity + "x " + chosenPizza.getName() + " added!");
                        } else {
                            System.out.println("Invalid pizza number, try again.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }

                bestillinger.add(newBestilling);
                System.out.println("Order #" + newBestilling.getOrdreNr() + " created with " +
                        newBestilling.getVarer().size() + " pizzas!");
            }

            else if (valg == 2) {
                if (bestillinger.isEmpty()) {
                    System.out.println("No orders to delete.");
                } else {
                    // ✅ Show all existing orders
                    System.out.println("Existing orders:");
                    for (Bestilling order : bestillinger) {
                        System.out.println("Order #" + order.getOrdreNr() + " - " + order.getVarer().size() + " pizzas");
                    }

                    // ✅ Ask user for the order number to delete
                    System.out.println("Enter order number to delete:");
                    int orderToDelete = scanner.nextInt();
                    scanner.nextLine(); // ✅ Fix newline issue

                    // ✅ Find and delete the order
                    boolean removed = bestillinger.removeIf(order -> order.getOrdreNr() == orderToDelete);

                    if (removed) {
                        System.out.println("Order #" + orderToDelete + " has been deleted.");
                    } else {
                        System.out.println("Order not found.");
                    }
                }
            }


            else if (valg == 3) {
                System.exit(0);
            }

            else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void main(String[] args) {
        MyPizzaOrderSystem system = new MyPizzaOrderSystem();
        system.start(); // ✅ Calls the method to start the menu
    }
}
