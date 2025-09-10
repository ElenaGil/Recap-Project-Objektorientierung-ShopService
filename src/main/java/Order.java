import lombok.With;

import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        Instant orderedAt,
        List<Product> products,
        OrderStatus orderStatus
) {
}
