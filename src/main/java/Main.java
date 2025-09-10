import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // OrderMapRepo orderMapRepo = new OrderMapRepo();
        OrderListRepo orderRepo = new OrderListRepo();
        ProductRepo productRepo = new ProductRepo();

        ShopService shopService = new ShopService(orderRepo, productRepo);

        productRepo.addProduct(new Product("11", "Bred"));
        productRepo.addProduct(new Product("2", "Milk"));
        productRepo.addProduct(new Product("3", "Potato"));
        productRepo.addProduct(new Product("4", "Rice"));
        productRepo.addProduct(new Product("5", "Butter"));
        productRepo.addProduct(new Product("6", "Tee"));
        productRepo.addProduct(new Product("7", "Kaffee"));

        shopService.addOrder(Arrays.asList("11", "2", "4"));
        shopService.addOrder(Arrays.asList("3", "2", "5"));
        shopService.addOrder(Arrays.asList("7", "6"));

        orderRepo.getOrders().forEach(System.out::println);
        String orderID = shopService.getOrdersByStatus(OrderStatus.PROCESSING).getFirst().id();
        shopService.updateOrder(orderID, OrderStatus.IN_DELIVERY);

        System.out.println("===========");
        orderRepo.getOrders().forEach(System.out::println);
    }
}
