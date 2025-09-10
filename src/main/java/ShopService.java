import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = Optional.ofNullable(productRepo.getProductById(productId)
                    .orElseThrow(() -> new NullPointerException("Product mit der Id: " + productId + " konnte nicht bestellt werden!")));

            productToOrder.ifPresent(products::add);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public Order updateOrder(String orderId, OrderStatus status) {
        Order order = orderRepo.getOrders().stream()
                .filter(ord -> ord.id().equals(orderId))
                .findFirst().orElseThrow(() -> new NullPointerException("Not found!"));

        return  order.withOrderStatus(status);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(status)).toList();
    }
}
