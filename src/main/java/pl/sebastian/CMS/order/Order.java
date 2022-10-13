package pl.sebastian.CMS.order;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator") //generationType.AUto BEZ SEQUENCE GENERATOR
    @SequenceGenerator(name="order_generator", sequenceName = "order_seq", allocationSize = 1)
    private Long id;
    private String phoneModel;
    private Integer graphicsNumber;
    @Enumerated(EnumType.STRING)
    private OrderPriority priority;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public enum OrderPriority{
        INPOST,
        POCZTA
    }
    public enum OrderStatus{
        ZLOZONE,
        W_PRODUKCJI,
        GOTOWE
    }


    //for entity
    public Order() {
    }

    public Order(Long id, String phoneModel, Integer graphicsNumber, OrderPriority priority, OrderStatus status) {
        this.id = id;
        this.phoneModel = phoneModel;
        this.graphicsNumber = graphicsNumber;
        this.priority = priority;
        this.status = status;

    }

    public Order(String phoneModel, Integer graphicsNumber, OrderPriority priority, OrderStatus status) {
        this.phoneModel = phoneModel;
        this.graphicsNumber = graphicsNumber;
        this.priority = priority;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public Integer getGraphicsNumber() {
        return graphicsNumber;
    }

    public void setGraphicsNumber(Integer graphicsNumber) {
        this.graphicsNumber = graphicsNumber;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", phoneModel='" + phoneModel + '\'' +
                ", graphicsNumber=" + graphicsNumber +
                ", priority='" + priority + '\'' +
                ", state='" + status + '\'' +
                '}';
    }
}
