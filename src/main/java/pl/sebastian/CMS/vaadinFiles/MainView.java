//package pl.sebastian.CMS.vaadinFiles;
//
//
//import com.vaadin.flow.component.applayout.AppLayout;
//import com.vaadin.flow.component.applayout.DrawerToggle;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.tabs.Tab;
//import com.vaadin.flow.component.tabs.Tabs;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.RouterLayout;
//import com.vaadin.flow.router.RouterLink;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Route("")
//public class MainView extends AppLayout implements RouterLayout {
//
//    private Map<Tabs, String> tabToUrlMap = new LinkedHashMap<>();
//
//    public MainView(){
//
//        H1 appTitle = new H1("CMS");
//        appTitle.getStyle()
//                .set("font-size", "var(--lumo-font-size-l)")
//                .set("line-height", "var(--lumo-size-l)")
//                .set("margin", "0 var(--lumo-space-m)");
//
//        Tabs views = getPrimaryNavigation();
//
//        DrawerToggle toggle = new DrawerToggle();
//
//        H2 viewTitle = new H2("Orders");
//        viewTitle.getStyle()
//                .set("font-size", "var(--lumo-font-size-l)")
//                .set("margin", "0");
//
//        Tabs subViews = getSecondaryNavigation();
//
//        HorizontalLayout wrapper = new HorizontalLayout(toggle, viewTitle);
//        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
//        wrapper.setSpacing(false);
//
//        VerticalLayout viewHeader = new VerticalLayout(wrapper, subViews);
//        viewHeader.setPadding(false);
//        viewHeader.setSpacing(false);
//
//        addToDrawer(appTitle, views);
//        addToNavbar(viewHeader);
//
//        setPrimarySection(Section.DRAWER);
//
//
//    }
//
//
//    private Tabs getPrimaryNavigation() {
//        Tabs tabs = new Tabs();
//        tabs.add(
//                createTab(VaadinIcon.DASHBOARD, "Dashboard"),
//                createTab(VaadinIcon.CART, "Orders"),
//                createTab(VaadinIcon.USER_HEART, "Customers"),
//                createTab(VaadinIcon.PACKAGE, "Products"),
//                createTab(VaadinIcon.RECORDS, "Documents"),
//                createTab(VaadinIcon.LIST, "Tasks"),
//                createTab(VaadinIcon.CHART, "Analytics")
//        );
//        tabs.setOrientation(Tabs.Orientation.VERTICAL);
//        tabs.setSelectedIndex(1);
//        return tabs;
//    }
//
//    private Tab createTab(VaadinIcon viewIcon, String viewName) {
//        Icon icon = viewIcon.create();
//        icon.getStyle()
//                .set("box-sizing", "border-box")
//                .set("margin-inline-end", "var(--lumo-space-m)")
//                .set("padding", "var(--lumo-space-xs)");
//
//        RouterLink link = new RouterLink();
//        link.add(icon, new Span(viewName));
//        // Demo has no routes
//         //link.setRoute(viewClass.java);
//        link.setTabIndex(-1);
//
//        return new Tab(link);
//    }
//
//
//    private Tabs getSecondaryNavigation() {
//        Tabs tabs = new Tabs();
//        tabs.add(
//                new Tab("All"),
//                new Tab("Open"),
//                new Tab("Completed"),
//                new Tab("Cancelled")
//        );
//        return tabs;
//    }
//
//}
//
//
//
package pl.sebastian.CMS.vaadinFiles;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;

import java.util.LinkedHashMap;
import java.util.Map;

//@Route("")
public class MainView extends VerticalLayout implements RouterLayout {

    private Map<Tab, String> tabToUrlMap = new LinkedHashMap<>();

    public MainView(){

        H1 title = new H1("CMS");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();
        //UI.getCurrent().navigate(tabToUrlMap.get("Zamówienia"));//pierwsza zakładka jako główna

        //UI.getCurrent().navigate(OrderGUI.class);//pierwsza zakładka jako główna
        tabs.addSelectedChangeListener(e -> UI.getCurrent().navigate(tabToUrlMap.get(e.getSelectedTab())));

        add(title, tabs);

    }

    private Tabs getTabs() {

        RouteConfiguration routeConfiguration = RouteConfiguration.forApplicationScope();

        tabToUrlMap.put(new Tab("Zamówienia"), routeConfiguration.getUrl(OrderGUI.class));
        tabToUrlMap.put(new Tab("Modele telefonów"), routeConfiguration.getUrl(ModelListGUI.class));
        tabToUrlMap.put(new Tab("Stan magazynowy"), routeConfiguration.getUrl(StockBalanceGUI.class));
        tabToUrlMap.put(new Tab("Wzory"), routeConfiguration.getUrl(ManageGraphicsGUI.class));
        //  createTab("Wzory")
        Tabs tabs = new Tabs(tabToUrlMap.keySet().toArray(new Tab[]{}));
        tabs.getStyle().set("margin", "auto");

        return tabs;

    }


}