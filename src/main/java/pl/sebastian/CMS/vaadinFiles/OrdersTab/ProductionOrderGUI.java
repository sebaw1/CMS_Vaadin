package pl.sebastian.CMS.vaadinFiles.OrdersTab;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import pl.sebastian.CMS.order.Order;
import pl.sebastian.CMS.order.OrderService;
import pl.sebastian.CMS.vaadinFiles.MainView;
import pl.sebastian.CMS.vaadinFiles.OrderGUI;

import java.util.List;
import java.util.Set;

@Route(value="zamowienia/w_produkcji", layout = OrderGUI.class)
public class ProductionOrderGUI extends VerticalLayout {
    private final OrderService orderService;
    private ListDataProvider<Order> dataProvider;
    private List<Order> orderList;
    private Button deleteOrderButton;
    private Button changeStatusOrderButton;
    private Set<Order> selectedOrders;

    public ProductionOrderGUI(OrderService orderService) {
        //super();

        this.orderService = orderService;
        orderList = orderService.getOrdersByStatus(Order.OrderStatus.W_PRODUKCJI);

        deleteOrderButton = new Button("Usuń zaznaczone");
        changeStatusOrderButton = new Button("Zaznaczone oznacz jako gotowe");

        gridView();

        add(changeStatusOrderButton, deleteOrderButton);

    }
    private void gridView(){

        Grid<Order> grid = new Grid<>(Order.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        //Grid.Column<Order> idColumn =
        grid.addColumn(Order::getId)
                .setHeader("ID").setAutoWidth(true).setSortable(true);
        Grid.Column<Order> modelColumn = grid.addColumn(Order::getPhoneModel)
                .setHeader("Model").setAutoWidth(true).setSortable(true);
        Grid.Column<Order> graphicsColumn = grid.addColumn(Order::getGraphicsNumber)
                .setHeader("Grafika").setAutoWidth(true).setSortable(true);
        Grid.Column<Order> priorityColumn = grid.addColumn(Order::getPriority)
                .setHeader("Priorytet").setAutoWidth(true).setSortable(true);
        Grid.Column<Order> statusColumn = grid.addColumn(Order::getStatus)
                .setHeader("Status").setAutoWidth(true).setSortable(true);


        grid.setItems(orderList);

        getThemeList().clear();
        getThemeList().add("spacing-s");
        add(grid);

        setSelectionHandler(grid);
        setButtonHandler(grid);
    }

    private void setSelectionHandler(Grid<Order> orderGrid){
        orderGrid.addSelectionListener(e -> selectedOrders = e.getAllSelectedItems());
    }
    private void setButtonHandler(Grid<Order> orderGrid){
        //orderGrid.deselectAll();

        deleteOrderButton.addClickListener(e -> {
            buttonActionHandler("del", orderGrid);
        });

        changeStatusOrderButton.addClickListener(e->{
            buttonActionHandler("chStatus", orderGrid);
        });

    }

    private void buttonActionHandler(String action, Grid<Order> orderGrid) {
        dataProvider = (ListDataProvider<Order>)orderGrid.getDataProvider();

        if(selectedOrders == null || selectedOrders.isEmpty()){
            Notification notification = Notification.show("Wybierz element!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        else {

            for (Order o : selectedOrders) {
                if (o != null) {

                    if(action.equals("del")) {
                        orderService.deleteOrder(o.getId()); //may be null? Optional?
                    }else if(action.equals("chStatus")){
                        orderService.changeOrderStatus(o.getId(), Order.OrderStatus.GOTOWE); //may be null? Optional?
                    }
                    dataProvider.getItems().remove(o);
                }
            }
            orderGrid.deselectAll();
            dataProvider.refreshAll();
        }
    }

}
