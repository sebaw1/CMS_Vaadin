package pl.sebastian.CMS.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping()
    public void addNewOrder(@RequestBody Order order){
        orderService.addUpdateOrder(order);
    }

    @DeleteMapping("{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(orderId);
    }

    @PutMapping("{orderId}")
    public void updateOrder(@PathVariable("orderId") Long orderId,
                            @RequestParam(required = false) String phoneModel,
                            @RequestParam(required = false) Integer graphicsNumber){
        orderService.updateOrder(orderId, phoneModel, graphicsNumber);
    }
}
