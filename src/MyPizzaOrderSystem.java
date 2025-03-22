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
            showMenu();    // ✅ Displays the menu

            int valg = scanner.nextInt();
            scanner.nextLine(); // ✅ Fix input issues

            if (valg == 1) {
                createOrder();
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
    private void createOrder() {
        ordreNr++; // ✅ Increment order number
        Bestilling newBestilling = new Bestilling(ordreNr);

        System.out.println("Choose pizzas for your order (type 'done' when finished):");
        while (true) {
            for (Menu pizza : Menu.values()) {
                System.out.println(pizza.getNr() + ": " + pizza.getName() + " - " + pizza.getPrice() + " DKK");
            }

            System.out.println("Enter pizza number (or type 'done' to finish):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int pizzaNumber = Integer.parseInt(input);
                Menu chosenPizza = null;

                for (Menu pizza : Menu.values()) {
                    if (pizza.getNr() == pizzaNumber) {
                        chosenPizza = pizza;
                        break;
                    }
                }

                if (chosenPizza != null) {
                    System.out.println("How many " + chosenPizza.getName() + " pizzas?");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 0; i < quantity; i++) {
                        newBestilling.tilfojVare(chosenPizza);
                    }

                    System.out.println(quantity + "x " + chosenPizza.getName() + " added!");
                    displayOrdersTable(); // ✅ Always show active and ready-for-collection orders

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

        // ✅ Move orders every 4 orders
        updateOrderStatus();
    }

    // ✅ Delete an order
    private void deleteOrder() {
        if (bestillinger.isEmpty()) {
            System.out.println("No orders to delete.");
            return;
        }

        System.out.println("Existing orders:");
        for (Bestilling order : bestillinger) {
            System.out.println("Order #" + order.getOrdreNr() + " - " + order.getVarer().size() + " pizzas");
        }

        System.out.println("Enter order number to delete:");
        int orderToDelete = scanner.nextInt();
        scanner.nextLine();

        boolean removed = bestillinger.removeIf(order -> order.getOrdreNr() == orderToDelete);
        if (removed) {
            System.out.println("Order #" + orderToDelete + " has been deleted.");
        } else {
            System.out.println("Order not found.");
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
    private void showMenu() {
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
        System.out.print("\033[H\033[2J");
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

    // ✅ Groups identical pizzas and displays quantity (e.g., "Carbona x3")
    private String getGroupedOrderItems(Bestilling order) {
        Map<String, Integer> pizzaCount = new LinkedHashMap<>();

        for (Menu pizza : order.getVarer()) {
            pizzaCount.put(pizza.getName(), pizzaCount.getOrDefault(pizza.getName(), 0) + 1);
        }

        return pizzaCount.entrySet().stream()
                .map(entry -> entry.getKey() + " x" + entry.getValue()) // ✅ Convert to "Carbona x3"
                .reduce((a, b) -> a + ", " + b) // ✅ Join into one string
                .orElse(""); // ✅ Return empty if no items
    }


    // ✅ Method to wrap long text into multiple lines
    private String wrapText(String text, int maxWidth) {
        StringBuilder wrappedText = new StringBuilder();
        int lineStart = 0;

        while (lineStart < text.length()) {
            int lineEnd = Math.min(lineStart + maxWidth, text.length());
            wrappedText.append(text, lineStart, lineEnd).append("\n");
            lineStart += maxWidth;
        }

        return wrappedText.toString().trim();
    }

    // ✅ Moves orders every 4 new orders
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
