package com.ncjavaedu.ediary.client.authorization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class AuthorizationPanel implements IsWidget {

    @UiField
    TextField loginField;

    @UiField
    PasswordField passField;

    @UiField
    TextButton submitButton;

    @UiField
    TextButton clearButton;

    private Widget widget;
    private static AuthorizationUiBinder uiBinder = GWT.create(AuthorizationUiBinder.class);

    interface AuthorizationUiBinder extends UiBinder<Widget, AuthorizationPanel> {
    }

    /**
     * Constructor
     */
    public AuthorizationPanel() {
    }

    @UiHandler({"clearButton"})
    public void onClearClick(SelectEvent event) {
        loginField.setText("");
        passField.setText("");
    }

    @Override
    public Widget asWidget() {
        if (widget == null){
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}