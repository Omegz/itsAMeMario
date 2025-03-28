import java.util.*;

public class MyPizzaOrderSystem {
    Scanner scanner = new Scanner(System.in);
    int ordreNr = 0;
    List<Bestilling> bestillinger = new ArrayList<>();
    List<Bestilling> klarTilAfhentning = new ArrayList<>();
    List<Bestilling> ordreHistorik = new ArrayList<>();

    public void start() {
        while (true) {
            clearScreen(); // ✅ Clears screen before showing menu
            visMuligheder();    // ✅ Displays the menu

            int valg = scanner.nextInt();
            scanner.nextLine(); // ✅ Fix input issues

            if (valg == 1) {
                opretOrder();
            } else if (valg == 2) {
                deleteOrder();
            } else if (valg == 3) {
                showActiveOrders();
            } else if (valg == 4) {
                showOrderHistory();
            } else if (valg == 5) {
                System.exit(0);
            } else {
                System.out.println("Invalid choice, try again.");
            }

            // ✅ Pause before redisplaying the menu
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    // ✅ Create an order

    private void opretOrder() {
        ordreNr++;
        Bestilling nyBestilling = new Bestilling(ordreNr);

        System.out.println("indtast pizza nummer (indtast 'færdig' efter valg):");
        while (true) {
            for (Menu pizza : Menu.values()) {
                System.out.println(pizza.getNr() + ": " + pizza.getName() + " - " + pizza.getPrice() + " DKK");
            }

            System.out.println("indtast venligst, pizza nummer (indtast 'færdig' når valgt):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("færdig")) {
                break;
            }

            try {
                int pizzaNummer = Integer.parseInt(input);
                Menu valgtPizza = null;

                for (Menu pizza : Menu.values()) {
                    if (pizza.getNr() == pizzaNummer) {
                        valgtPizza = pizza;
                        break;
                    }
                }

                if (valgtPizza != null) {
                    System.out.println("hvor mange " + valgtPizza.getName() + " pizzaer?");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < quantity; i++) {
                        nyBestilling.tilfojVare(valgtPizza);
                    }

                    System.out.println(quantity + "x " + valgtPizza.getName() + " tilføjet!");
                    displayOrdersTable(); // viser altid aktive ordrer

                } else {
                    System.out.println("ugyldig pizza nummer, prøv igen.");
                }

            } catch (NumberFormatException e) {
                System.out.println(" indtast venligst et gyldigt pizza nummer.");
            }
        }

        bestillinger.add(nyBestilling);
        System.out.println("Ordrer #" + nyBestilling.getOrdreNr() + " oprettet med " +
                nyBestilling.getVarer().size() + " pizzaer!");


        updateOrderStatus();
    }


    // ✅ Delete an order
//    private void deleteOrder() {
//        if (bestillinger.isEmpty()) {
//            System.out.println("No orders to delete.");
//            return;
//        }
//
//        System.out.println("Existing orders:");
//        for (Bestilling order : bestillinger) {
//            System.out.println("Order #" + order.getOrdreNr() + " - " + order.getVarer().size() + " pizzas");
//        }
//
//        System.out.println("Enter order number to delete:");
//        int orderToDelete = scanner.nextInt();
//        scanner.nextLine();
//
//        boolean removed = bestillinger.removeIf(order -> order.getOrdreNr() == orderToDelete);
//        if (removed) {
//            System.out.println("Order #" + orderToDelete + " has been deleted.");
//        } else {
//            System.out.println("Order not found.");
//        }
//    }


    //Slet en ordre med bekræftelse
    private void deleteOrder() {
        if (bestillinger.isEmpty()) {
            System.out.println("Ingen aktive ordrer at slette.");
            return;
        }

        System.out.println("Eksisterende ordrer:");
        for (Bestilling order : bestillinger) {
            System.out.println("Ordre #" + order.getOrdreNr() + " - " + order.getVarer().size() + " pizzaer");
        }

        System.out.print("Indtast ordrenummer for at slette: ");
        int orderToDelete = scanner.nextInt();
        scanner.nextLine();

        Bestilling foundOrder = null;
        for (Bestilling order : bestillinger) {
            if (order.getOrdreNr() == orderToDelete) {
                foundOrder = order;
                break;
            }
        }
        if (foundOrder != null) {
            System.out.print("Er du sikker på, at du vil slette ordre #" + orderToDelete + "? (ja/nej): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("ja")) {
                bestillinger.remove(foundOrder);
                ordreHistorik.add(foundOrder);
                System.out.println("Ordre #" + orderToDelete + " er blevet slettet og arkiveret.");
            } else {
                System.out.println("Sletning annulleret.");
            }
        } else {
            System.out.println("Ordre ikke fundet.");
        }
    }


    // ✅ Show active orders in a table format
    private void showActiveOrders() {
        System.out.println("\n=== ACTIVE ORDERS ===");
        System.out.println("Order # | Status");
        System.out.println("--------------------");

        for (Bestilling order : bestillinger) {
            System.out.println(order.getOrdreNr() + "      | In Progress");
        }

        for (Bestilling order : klarTilAfhentning) {
            System.out.println(order.getOrdreNr() + "      | Ready for Collection");
        }
        System.out.println();
    }


    // ✅ Displays the menu AND the order table
    private void visMuligheder() {
        System.out.println("==== PIZZA ORDER SYSTEM ====");
        System.out.println("1: Create Order");
        System.out.println("2: Delete Order");
        System.out.println("3: Show Active Orders");
        System.out.println("4: Show Order History");
        System.out.println("5: Exit");
        System.out.println("===========================");
        System.out.print("Enter your choice: ");
    }


    // ✅ Show order history in a table format
    private void showOrderHistory() {
        System.out.println("\n=== ORDER HISTORY ===");
        System.out.println("Order # | Status");
        System.out.println("--------------------");

        for (Bestilling order : ordreHistorik) {
            System.out.println(order.getOrdreNr() + "      | Completed");
        }
        System.out.println();
    }


    // ✅ Clears the screen and always shows the orders table
    private void clearScreen() {
//        System.out.print("\033[H\033[2J");
        System.out.flush();
        displayOrdersTable(); // ✅ Always show active and ready-for-collection orders
    }

    // ✅ Table for active orders and ready-for-collection orders (ALWAYS VISIBLE)
    private void displayOrdersTable() {
        System.out.println("\n=================================================================================================================");
        System.out.printf("%-50s | %-50s%n", "ACTIVE ORDERS", "READY FOR COLLECTION");
        System.out.println("=================================================================================================================");

        int maxRows = Math.max(bestillinger.size(), klarTilAfhentning.size());

        for (int i = 0; i < maxRows; i++) {
            // ✅ Middle column: Active Orders
            String activeOrderColumn = " ";
            if (i < bestillinger.size()) {
                Bestilling order = bestillinger.get(i);
                String items = getGroupedOrderItems(order); // ✅ Now groups identical pizzas

                // ✅ Wrap long text into lines
                activeOrderColumn = wrapText(String.format("Order #%d - %d pizzas: %s",
                        order.getOrdreNr(), order.getVarer().size(), items), 45);
            }

            // ✅ Right column: Ready for Collection Orders
            String readyOrderColumn = " ";
            if (i < klarTilAfhentning.size()) {
                Bestilling order = klarTilAfhentning.get(i);
                String items = getGroupedOrderItems(order); // ✅ Now groups identical pizzas

                // ✅ Wrap long text into lines
                readyOrderColumn = wrapText(String.format("Order #%d - %d pizzas: %s",
                        order.getOrdreNr(), order.getVarer().size(), items), 45);
            }

            // ✅ Print each line of wrapped text
            List<String> activeLines = Arrays.asList(activeOrderColumn.split("\n"));
            List<String> readyLines = Arrays.asList(readyOrderColumn.split("\n"));
            int maxLines = Math.max(activeLines.size(), readyLines.size());

            for (int j = 0; j < maxLines; j++) {
                String activeLine = (j < activeLines.size()) ? activeLines.get(j) : "";
                String readyLine = (j < readyLines.size()) ? readyLines.get(j) : "";
                System.out.printf("%-50s | %-50s%n", activeLine, readyLine);
            }

            System.out.println("-----------------------------------------------------------------------------------------------------------------");
        }
    }

    //  Groups identical pizzas and displays quantity (e.g., "Carbona x3")
    private String getGroupedOrderItems(Bestilling order) {

        // LinkedHashMap is imported from java.util
        // Map is used to store key–value pairs, e.g. "Carbonara" : 2
        // We're creating a Map of type LinkedHashMap, which allows us to keep the order of insertion
        // We use the Map to check if a pizza has already been added before
        // If it has, we increase the value (count) of that key instead of creating a new key
        //We use a LinkedHashMap to group identical pizzas and keep track of how many of each were ordered,
        // in the same order the customer added them
        Map<String, Integer> pizzaCount = new LinkedHashMap<>();

        //  for loop to search for pizzas (based from Menu) from our order (Bestilling )
        for (Menu pizza : order.getVarer()) {
            // add the key: pizza name
            // add the value : look in pizza count. find name, do we have an instance o fthat name?
            // if yes, take the value and add 1. if not then give 0 to that value and add 1
            pizzaCount.put(pizza.getName(), pizzaCount.getOrDefault(pizza.getName(), 0) + 1);
        }

        // aim is to return our map from above into a string

        String result = "";
        // we are looping and adding to our map
        for (Map.Entry<String, Integer> entry : pizzaCount.entrySet()) {
            if (!result.isEmpty()) {
                result += ", ";
            }
            result += entry.getKey() + " x" + entry.getValue();
        }

        return result;

    }


    //Method to wrap long text into multiple lines, it takes text and a max width we decide.
    private String wrapText(String text, int maxWidth) {
        // StringBuilder is imported via import java.util.*; it allows us to place the entire order in one string
        // instead of a new string for every item added to order. normal strings cant be edited, but this one can.
        StringBuilder wrappedText = new StringBuilder();
        int lineStart = 0;

        while (lineStart < text.length()) {
            // decides end of the line based on whats smaller between the (max amount we can have in one line)
            // or how much text we actually have
            int lineEnd = Math.min(lineStart + maxWidth, text.length());
            // actually adds that line of text with our decided lower and upper limits set. then starts a new line
            wrappedText.append(text, lineStart, lineEnd).append("\n");
            // resets the start to where we reached so far in the text.
            lineStart += maxWidth;
        }

        return wrappedText.toString().trim();
    }

    //  Moves orders every 4 new orders
    private void updateOrderStatus() {
        if (bestillinger.size() >= 4) {
            // ✅ Move the oldest active order to "Ready for Collection"
            Bestilling oldestOrder = bestillinger.remove(0);
            klarTilAfhentning.add(oldestOrder);
            System.out.println("Order #" + oldestOrder.getOrdreNr() + " is now Ready for Collection!");
        }

        if (klarTilAfhentning.size() >= 4) {
            // ✅ Move the oldest "Ready for Collection" order to "History"
            Bestilling collectedOrder = klarTilAfhentning.remove(0);
            ordreHistorik.add(collectedOrder);
            System.out.println("Order #" + collectedOrder.getOrdreNr() + " has been collected and added to history.");
        }
    }

}
