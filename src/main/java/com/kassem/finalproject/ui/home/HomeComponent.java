package com.kassem.finalproject.ui.home;

import com.kassem.finalproject.ui.login.LoginComponent;
import com.kassem.finalproject.ui.view.category.ApplyNow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class HomeComponent extends VerticalLayout {

    public HomeComponent() {
        Label greeting = new Label("Our Home page(view should be added )");
        add(greeting);
        add(new RouterLink("Login", LoginComponent.class));
        add(new RouterLink("Apply now", ApplyNow.class));
    }
}
