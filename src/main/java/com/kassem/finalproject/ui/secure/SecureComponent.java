package com.kassem.finalproject.ui.secure;

import com.kassem.finalproject.ui.login.SessionInfo;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

import org.springframework.beans.factory.annotation.Autowired;

@RoutePrefix("secure")
public class SecureComponent extends VerticalLayout implements RouterLayout {

    public SecureComponent(@Autowired SessionInfo sessionInfo) {
        setSizeFull();
        Label loginLabel = new Label("Secure application. Welcome " + sessionInfo.getCurrentUser().getUsername() + "!");
        loginLabel.getElement().getStyle().set("font-weight", "bold");
        loginLabel.getElement().getStyle().set("font-size", "150%");

        add(loginLabel);
    }

}