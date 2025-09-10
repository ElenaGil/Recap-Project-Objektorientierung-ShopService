import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_shouldThrowException_whenInvalidProductId() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //THEN
        assertThrows(NullPointerException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByStatusTest() {
        //GIVEN
        ShopService shopService = new ShopService();
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
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);
        System.out.println(actual);

        Order updated = shopService.updateOrder(actual.id(), OrderStatus.IN_DELIVERY);
        System.out.println("Updated: "+updated);

        OrderStatus expected = OrderStatus.IN_DELIVERY;

        //THEN
        assertEquals(expected, updated.orderStatus());
        assertNotEquals(OrderStatus.PROCESSING, updated.orderStatus());
    }
}
