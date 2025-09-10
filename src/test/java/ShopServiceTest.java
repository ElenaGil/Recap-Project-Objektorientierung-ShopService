import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    Instant now = Instant.now();
    OrderListRepo orderRepo = new OrderListRepo();
    ProductRepo productRepo = new ProductRepo();

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService(orderRepo, productRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", now, List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_shouldThrowException_whenInvalidProductId() {
        //GIVEN
        ShopService shopService = new ShopService(orderRepo, productRepo);
        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrows(NullPointerException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByStatusTest() {
        //GIVEN
        ShopService shopService = new ShopService(orderRepo, productRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);
        OrderStatus expected = OrderStatus.PROCESSING;

        //THEN
        assertEquals(expected, actual.orderStatus());
        assertNotEquals(OrderStatus.COMPLETED, actual.orderStatus());
    }

    @Test
    void updateOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService(orderRepo, productRepo);
        List<String> productsIds = List.of("1");

        //WHEN
        Order newOrder = shopService.addOrder(productsIds);

        assertTrue(shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY).isEmpty());
        assertEquals(OrderStatus.PROCESSING, orderRepo.getOrderById(newOrder.id()).orderStatus());

        shopService.updateOrder(newOrder.id(), OrderStatus.IN_DELIVERY);

        //THEN
        assertFalse(shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY).isEmpty());
        assertEquals(OrderStatus.IN_DELIVERY, orderRepo.getOrderById(newOrder.id()).orderStatus());
    }
}
