import java.util.*;

public class PizzaOrderSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Order> orders = new ArrayList<>();
    private static int lastOrderNumber = 0; // Tracks order numbering

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter:\n1: Create an order\n2: Delete an order\n3: Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createOrder();
                case 2 -> deleteOrder();
                case 3 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void createOrder() {
        System.out.println("Enter your order (e.g., 2 3x, 4, 6 3x):");
        String input = scanner.nextLine();
        Map<Menu, Integer> orderItems = parseOrder(input);

        lastOrderNumber++; // Increment order number
        Order newOrder = new Order(lastOrderNumber, orderItems);
        orders.add(newOrder);

        System.out.println("Order created: " + newOrder);
    }

    private static void deleteOrder() {
        System.out.println("Enter order number to delete:");
        int orderNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        orders.removeIf(order -> order.getOrderNumber() == orderNumber);
        System.out.println("Order " + orderNumber + " deleted.");
    }

    private static Map<Menu, Integer> parseOrder(String input) {
        Map<Menu, Integer> orderMap = new HashMap<>();
        String[] items = input.split(",");

        for (String item : items) {
            item = item.trim();
            String[] parts = item.split(" ");
            int pizzaNumber = Integer.parseInt(parts[0]);
            int quantity = (parts.length == 2) ? Integer.parseInt(parts[1].replace("x", "")) : 1;

            for (Menu menu : Menu.values()) {
                if (menu.getNr() == pizzaNumber) {
                    orderMap.put(menu, orderMap.getOrDefault(menu, 0) + quantity);
                    break;
                }
            }
        }
        return orderMap;
    }
}
