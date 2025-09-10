import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = Optional.ofNullable(productRepo.getProductById(productId)
                    .orElseThrow(() -> new NullPointerException("Product mit der Id: " + productId + " konnte nicht bestellt werden!")));

            productToOrder.ifPresent(products::add);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), Instant.now(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public void updateOrder(String orderId, OrderStatus status) {
        Order existingOrder = orderRepo.getOrderById(orderId);
        if (existingOrder == null) {
            throw new NullPointerException("Order not found");
        }
        Order updatedOrder = existingOrder.withOrderStatus(status);
        orderRepo.removeOrder(existingOrder.id());
        orderRepo.addOrder(updatedOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(status)).toList();
    }
}
