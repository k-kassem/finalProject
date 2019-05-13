package com.kassem.finalproject.ui.home;

import com.kassem.finalproject.ui.login.LoginComponent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class HomeComponent extends VerticalLayout {

    public HomeComponent() {
        Label greeting = new Label("Here To Add information in a form  to the hr manager be hired ");
        add(greeting);
        add(new RouterLink("Fill the form to be Added later", LoginComponent.class));
    }
}
