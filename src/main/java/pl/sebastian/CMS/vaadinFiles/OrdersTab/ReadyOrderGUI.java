package pl.sebastian.CMS.vaadinFiles.OrdersTab;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.sebastian.CMS.vaadinFiles.OrderGUI;

@Route(value="zamowienia/gotowe", layout = OrderGUI.class)
public class ReadyOrderGUI extends VerticalLayout {
    public ReadyOrderGUI() {
        add(new Text("gotowe zamówienia"));
    }
}
