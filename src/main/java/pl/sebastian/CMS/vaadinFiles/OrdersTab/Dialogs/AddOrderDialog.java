package pl.sebastian.CMS.vaadinFiles.OrdersTab.Dialogs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import pl.sebastian.CMS.order.Order;
import pl.sebastian.CMS.order.OrderService;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.ValidationMessage;

public class AddOrderDialog extends Div {

    static private Order newOrder;
    static TextField modelNameField;
    static TextField graphicsNameField;
    static ComboBox<Order.OrderPriority> priorityComboBox;
    public static void openDialog(OrderService orderService) {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Nowe zamówienie");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog, orderService);
        Button cancelButton = new Button("Anuluj", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);


        dialog.open();
    }

    private static VerticalLayout createDialogLayout() {

        ValidationMessage ValidationMessage = new ValidationMessage();
        Binder<Order> binder = new Binder<>();

        modelNameField = new TextField("Model");
        graphicsNameField = new TextField("Grafika");
        priorityComboBox = new ComboBox<>("Priorytet");


        //graphicsComboBox.setItems
        //modelComboBox.setItems
        priorityComboBox.setItems(Order.OrderPriority.values());

        modelNameField.setRequired(true);


//        modelNameField.setRequired(true);
//        modelNameField.setErrorMessage("Pole nie moze byc puste");
//        graphicsNameField.setRequired(true);
//        graphicsNameField.setErrorMessage("Pole nie moze byc puste");
//        priorityComboBox.setRequired(true);
//        priorityComboBox.setErrorMessage("Wybierz cos");

        VerticalLayout dialogLayout = new VerticalLayout(modelNameField,
                graphicsNameField, priorityComboBox);



        binder.forField(modelNameField)
                .asRequired("Pole nie może być puste!")
                //.withStatusLabel(ValidationMessage)
                .bind(Order::getPhoneModel, Order::setPhoneModel);

        binder.forField(graphicsNameField)
                .asRequired("Pole nie może być puste!")
                //.withStatusLabel(ValidationMessage)
                .withConverter(new StringToIntegerConverter("Wpisz poprawną liczbę!"))
                .bind(Order::getGraphicsNumber, Order::setGraphicsNumber);

        binder.forField(priorityComboBox)
                .asRequired("Priorytet musi zostac wybrany!")
                .bind(Order::getPriority, Order::setPriority);


        binder.setBean(newOrder);

        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private static Button createSaveButton(Dialog dialog, OrderService orderService) {
        Button saveButton = new Button("Dodaj", e -> {



            //newOrder = new Order(modelNameField.getValue(),
              //      Integer.parseInt(graphicsNameField.getValue()),
                //    priorityComboBox.getValue(), Order.OrderStatus.ZLOZONE);

           // orderService.addUpdateOrder(newOrder);
            dialog.close();

        }
        );
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}
