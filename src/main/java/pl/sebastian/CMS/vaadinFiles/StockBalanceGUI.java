package pl.sebastian.CMS.vaadinFiles;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="magazyn", layout = MainView.class)
public class StockBalanceGUI extends VerticalLayout {

    public StockBalanceGUI() {
        add(new Text("Zawartosc zakladki stan magazynowy"));
    }
}
