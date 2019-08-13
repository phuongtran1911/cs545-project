package edu.mum.repository;

import edu.mum.domain.OrderItem;
import edu.mum.domain.view.OrderItemInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    @Query("select i from OrderItem i " +
            "inner join Product p on i.product.id = p.id " +
            "inner join Seller s on p.seller.id = s.id " +
            "inner join Orders o on i.order.id = o.id " +
            "where s.id = :sellerId")
    public List<OrderItem> getOrderItemsBySeller(Long sellerId);
}
