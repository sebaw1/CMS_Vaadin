package pl.sebastian.CMS.vaadinFiles;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="modele", layout = MainView.class)
public class ModelListGUI extends VerticalLayout {
    public ModelListGUI() {
        add(new Text("Zawartosc zakladki modele"));
    }
}
