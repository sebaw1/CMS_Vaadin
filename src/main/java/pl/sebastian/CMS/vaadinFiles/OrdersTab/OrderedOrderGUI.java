package pl.sebastian.CMS.vaadinFiles.OrdersTab;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sebastian.CMS.order.Order;
import pl.sebastian.CMS.order.OrderService;
import pl.sebastian.CMS.vaadinFiles.OrderGUI;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.Dialogs.AddOrderDialog;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.ValidationMessage;

import java.util.List;
import java.util.Set;


@Route(value="zamowienia/zlozone", layout = OrderGUI.class)
@RouteAlias(value = "", layout = OrderGUI.class)
public class OrderedOrderGUI extends VerticalLayout{

    private ListDataProvider<Order> dataProvider;
    private OrderService orderService;
    //private TextField orderList;
    private List<Order> orderList;
    private Button deleteOrderButton;
    private Button addOrderButton;
    private Button changeStatusOrderButton;
    private Set<Order> selectedOrders;
    private Order selectedOrder;


    @Autowired
    public OrderedOrderGUI(OrderService orderService) {
        //super();

        this.orderService = orderService;
        orderList = orderService.getOrdersByStatus(Order.OrderStatus.ZLOZONE);

        deleteOrderButton = new Button("Usuń zaznaczone");
        addOrderButton = new Button("Dodaj nowe zamówienie");
        changeStatusOrderButton = new Button("Przenieś zaznaczone do produkcji");

        add(addOrderButton);
        gridView();
        add(changeStatusOrderButton, deleteOrderButton);

    }
//    private Grid<Order> gridView(){
//        Grid<Order> orderGrid = new Grid<>();
//
//        orderGrid.setSelectionMode(Grid.SelectionMode.MULTI);
//        orderGrid.addColumn(Order::getId).setHeader("ID").setAutoWidth(true).setSortable(true);
//        orderGrid.addColumn(Order::getPhoneModel).setHeader("Model").setAutoWidth(true).setSortable(true);
//        orderGrid.addColumn(Order::getGraphicsNumber).setHeader("Grafika").setAutoWidth(true).setSortable(true);
//        orderGrid.addColumn(Order::getPriority).setHeader("Priorytet").setAutoWidth(true).setSortable(true);
//        orderGrid.addColumn(Order::getStatus).setHeader("Status").setAutoWidth(true).setSortable(true);
//        orderGrid.setItems(orderList);
//
//        return orderGrid;
//    }

    private void gridView(){
        ValidationMessage modelValidationMessage = new ValidationMessage();
        ValidationMessage graphicsValidationMessage = new ValidationMessage();
        ValidationMessage priorityValidationMessage = new ValidationMessage();

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

        Binder<Order> binder = new Binder<>();
        Editor<Order> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Grid.Column<Order> editColumn = grid.addComponentColumn(orderColumn -> {
            Button editButton = new Button("Edytuj");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();

                editor.editItem(orderColumn);
                selectedOrder = orderColumn;
            });
            return editButton;
        }).setWidth("230px").setFlexGrow(0);

        TextField modelField = new TextField();
        modelField.setWidthFull();
        binder.forField(modelField)
                .asRequired("Pole nie może być puste!")
                .withStatusLabel(modelValidationMessage)
                .bind(Order::getPhoneModel, Order::setPhoneModel);
        modelColumn.setEditorComponent(modelField);

        TextField graphicsField = new TextField();
        graphicsField.setWidthFull();
        binder.forField(graphicsField)
                .asRequired("Pole nie może być puste!")
                .withStatusLabel(graphicsValidationMessage)
                .withConverter(new StringToIntegerConverter("Wpisz poprawną liczbę!"))
                .bind(Order::getGraphicsNumber, Order::setGraphicsNumber);
        graphicsColumn.setEditorComponent(graphicsField);

        ComboBox<Order.OrderPriority> priorityComboBox = new ComboBox<>();
        priorityComboBox.setItems(Order.OrderPriority.values());
        priorityComboBox.setWidthFull();
        binder.forField(priorityComboBox)
                .asRequired("Priorytet musi zostac wybrany!")
                .bind(Order::getPriority, Order::setPriority);
        priorityColumn.setEditorComponent(priorityComboBox);



//        EmailField emailField = new EmailField();
//        emailField.setWidthFull();
//        addCloseHandler(emailField, editor);
//        binder.forField(emailField).asRequired("Email must not be empty")
//                .withValidator(new EmailValidator(
//                        "Please enter a valid email address"))
//                .withStatusLabel(emailValidationMessage)
//                .bind(Order::getEmail, Order::setEmail);
//        emailColumn.setEditorComponent(emailField);

        Button saveButton = new Button("Zapisz", e -> editor.save());
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        editor.addSaveListener(saveEvent -> { /*System.out.println(selectedOrder);*/ orderService.addUpdateOrder(selectedOrder);});

        editor.addCancelListener(e -> {
            modelValidationMessage.setText("");
            graphicsValidationMessage.setText("");
            priorityValidationMessage.setText("");
        });

        grid.setItems(orderList);

        getThemeList().clear();
        getThemeList().add("spacing-s");
        add(grid, modelValidationMessage, graphicsValidationMessage,
                priorityValidationMessage);

        setSelectionHandler(grid);
        setButtonHandler(grid);
    }

    private void setSelectionHandler(Grid<Order> orderGrid){
        orderGrid.addSelectionListener(e -> selectedOrders = e.getAllSelectedItems());
    }
    private void setButtonHandler(Grid<Order> orderGrid){
        //orderGrid.deselectAll();

        addOrderButton.addClickListener(e -> {
           // AddOrderDialog.createDialog();
            AddOrderDialog.openDialog(orderService);
            //dataProvider.refreshAll();
        });

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
                        orderService.changeOrderStatus(o.getId(), Order.OrderStatus.W_PRODUKCJI); //may be null? Optional?
                    }
                    dataProvider.getItems().remove(o);
                }
            }
            orderGrid.deselectAll();
            dataProvider.refreshAll();
        }
    }
}
