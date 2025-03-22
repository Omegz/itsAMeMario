import java.util.Map;

public class Order {
    private final int orderNumber;
    private final Map<Menu, Integer> items;

    public Order(int orderNumber, Map<Menu, Integer> items) {
        this.orderNumber = orderNumber;
        this.items = items;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Map<Menu, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order #" + orderNumber + ":\n");
        for (Map.Entry<Menu, Integer> entry : items.entrySet()) {
            sb.append(entry.getValue()).append("x ").append(entry.getKey().getName()).append("\n");
        }
        return sb.toString();
    }
}
