package pl.sebastian.CMS.vaadinFiles;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sebastian.CMS.order.Order;
import pl.sebastian.CMS.order.OrderService;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.OrderedOrderGUI;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.ProductionOrderGUI;
import pl.sebastian.CMS.vaadinFiles.OrdersTab.ReadyOrderGUI;

import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

@ParentLayout(MainView.class)
@Route(value="zamowienia", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
//@RouteAlias(value = "zamowienia/zlozonee", layout = MainView.class)

public class OrderGUI extends VerticalLayout implements RouterLayout{//, BeforeEnterObserver{
    private Map<Tab, String> tabToUrlMapOrder = new LinkedHashMap<>();

    private OrderService orderService;

    @Autowired
    public OrderGUI(OrderService orderService) {
        //super();

        this.orderService = orderService;
        Tabs tabs = getTabsOrder();
        //UI.getCurrent().navigate(OrderedOrderGUI.class);//pierwsza zakładka jako główna
        tabs.addSelectedChangeListener(e -> UI.getCurrent().navigate(tabToUrlMapOrder.get(e.getSelectedTab())));

        add(tabs);
    }
    private Tabs getTabsOrder() {

        RouteConfiguration routeConfiguration = RouteConfiguration.forApplicationScope();

        //TODO - change count dynamically
        Binder<OrderService> binder = new Binder<>();
        Span badge1 = createBadge(0);
        //Span badge1 = new Span();
        binder.bind(new ReadOnlyHasValue<>(badge1::setText), OrderService::getOrderCountS, null);
        //binder.bind(bean -> badge1.setText(orderService.getOrderCountS()));
        binder.setBean(orderService);

        tabToUrlMapOrder.put(new Tab(new Span("Złożone"), badge1), routeConfiguration.getUrl(OrderedOrderGUI.class));
        tabToUrlMapOrder.put(new Tab(new Span("W produkcji"), createBadge(orderService.getOrderCount(Order.OrderStatus.W_PRODUKCJI))), routeConfiguration.getUrl(ProductionOrderGUI.class));
        tabToUrlMapOrder.put(new Tab(new Span("Gotowe"), createBadge(orderService.getOrderCount(Order.OrderStatus.GOTOWE))), routeConfiguration.getUrl(ReadyOrderGUI.class));
        //  createTab("Wzory")
        Tabs tabs = new Tabs(tabToUrlMapOrder.keySet().toArray(new Tab[]{}));
        tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
        tabs.getStyle().set("margin", "auto");

        return tabs;

    }
    private Span createBadge(int value) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small contrast");
        badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return badge;
    }

 //   @Override
    //public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
   //         beforeEnterEvent.forwardTo(OrderedOrderGUI.class);
   // }
}
