package pl.sebastian.CMS.vaadinFiles;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="wzory", layout = MainView.class)
public class ManageGraphicsGUI  extends VerticalLayout {

    public ManageGraphicsGUI() {
        add(new Text("wzory"));
    }
}
