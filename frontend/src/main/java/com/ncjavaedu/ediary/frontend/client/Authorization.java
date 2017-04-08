package com.ncjavaedu.ediary.frontend.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class Authorization implements IsWidget {
    private VBoxLayoutContainer widget;

    @Override
    public Widget asWidget() {
        if (widget == null) {
            BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 20, 0));
            flex.setFlex(1);

            widget = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
            widget.add(createForm1());
        }
        return widget;
    }

    private FramedPanel createForm1() {

        FormPanel form = new FormPanel();
        form.setMethod(FormPanel.Method.POST);

        VerticalPanel formpanel = new VerticalPanel();
        form.setWidget(formpanel);

        TextField login = new TextField();
        login.setAllowBlank(false);
        login.setEmptyText("Enter your login...");

        PasswordField password = new PasswordField();
        password.setEmptyText("Enter your password...");

        Button enter = new Button("Enter");
        enter.addClickHandler(event -> {
            if (login.getText().length() == 0 || password.getText().length() == 0) {
                Window.alert("The text box must not be empty!");
            } else {
                form.submit();
                RootPanel.get("slot4").clear();
                RootPanel.get("slot4").add(new AdminMain().asWidget());
                RootPanel.get("exit_button").add(new Button("Выход"));
            }
        });

        formpanel.add(new FieldLabel(login, "Login"));
        formpanel.add(new FieldLabel(password, "Password"));
        formpanel.add(enter);

        FramedPanel panel = new FramedPanel();
//        panel.setHeading("Login form");
        panel.add(form);

        return panel;
    }
}
