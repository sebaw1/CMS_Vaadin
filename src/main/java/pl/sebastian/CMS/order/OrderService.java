package pl.sebastian.CMS.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus orderStatusEnum){
        return orderRepository.getOrdersByStatus(orderStatusEnum);
    }
   // public Optional<Order> OrderfindById(Long id){
   //     return orderRepository.findById(id);
    //}

    public void addUpdateOrder(Order newOrder){
        orderRepository.save(newOrder);
        //System.out.println(newOrder);
    }

    public void deleteOrder(Long orderId){

        boolean exists = orderRepository.existsById(orderId);
        if(!exists) {
            throw new IllegalStateException(
                    "order with id "+ orderId + " doesn't exist");
        }
        orderRepository.deleteById(orderId);
    }

    public int getOrderCount(Order.OrderStatus orderStatusEnum){
        return orderRepository.countOrdersByStatus(orderStatusEnum);
    }

    public String getOrderCountS(){
        return Integer.toString(orderRepository.countOrdersByStatus(Order.OrderStatus.ZLOZONE));
    }
    @Transactional
    public void updateOrder(Long orderId, String phoneModel, Integer graphicsNumber){
                                            //new order params
        //found old order by ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("order with id " + orderId + " does not exist"));

        if(phoneModel != null && phoneModel.length() > 0 && !Objects.equals(order.getPhoneModel(), phoneModel)){
            order.setPhoneModel(phoneModel);
        }
        if(graphicsNumber != null && !Objects.equals(order.getGraphicsNumber(), graphicsNumber)){
            order.setGraphicsNumber(graphicsNumber);
        }

    }

    public void changeOrderStatus(Long orderId, Order.OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("order with id " + orderId + " does not exist"));

        if(status != null && !Objects.equals(order.getStatus(), status)){
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}
