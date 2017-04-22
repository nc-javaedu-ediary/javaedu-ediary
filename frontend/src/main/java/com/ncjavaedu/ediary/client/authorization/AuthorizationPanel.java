package com.ncjavaedu.ediary.client.authorization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.ClientUserService;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.List;

public class AuthorizationPanel implements IsWidget {

    @UiField
    TextField loginField;

    @UiField
    PasswordField passField;

    @UiField
    TextButton submitButton;

    @UiField
    TextButton clearButton;

    @UiField
    FieldLabel infoLabel;


    private Widget widget;
    private static AuthorizationUiBinder uiBinder = GWT.create(AuthorizationUiBinder.class);

    interface AuthorizationUiBinder extends UiBinder<Widget, AuthorizationPanel> {
    }

    /**
     * Constructor
     */
    public AuthorizationPanel() {
    }

    @UiHandler({"submitButton"})
    public void onSubmitClick(SelectEvent event) {
        infoLabel.setVisible(false);
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            public void onFailure(Throwable caught) {
                showError("Something went wrong");
            }

            @Override
            public void onSuccess(UserDTO dto) {
                onLogin(dto);
            }
        };
        ClientUserService.App.getInstance().getUser(loginField.getText(), passField.getText(), callback);
    }

    @UiHandler({"clearButton"})
    public void onClearClick(SelectEvent event) {
        hideError();
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

    private void onLogin(UserDTO dto){
        if (dto == null){
            showError("Incorrect login or password");
        } else {
            //TODO implement logic
            showError("You are logged as " + dto.getFirstName());
        }
    }

    private void hideError(){
        infoLabel.setVisible(false);
    }

    private void showError(String text){
        infoLabel.setVisible(true);
        infoLabel.setText(text);
    }

    //Example - save user
    private void saveUser(){
        UserDTO dto = new UserDTO();
        double rnd = Math.random();
        dto.setFirstName("Morgan " + rnd);
        dto.setLastName("Frimen " + rnd);
        dto.setEmail("morgan.frimen@gmail.com");

        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            public void onFailure(Throwable caught) {
               showError("onFailureSave");
            }

            @Override
            public void onSuccess(UserDTO dto) {
                showError("onSuccessSave");
                onSaveUser(dto);
            }
        };
        ClientUserService.App.getInstance().saveUser(dto, callback);
    }

    private void onSaveUser(UserDTO dto){

    }
    //Example - get users
    private void getUsers(){
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            public void onFailure(Throwable caught) {
                loginField.setText("onFailure");
            }

            @Override
            public void onSuccess(List<UserDTO> users) {
                onGetUsers(users);
            }
        };
        ClientUserService.App.getInstance().getUsers(callback);
    }

    private void onGetUsers(List<UserDTO> users){
        loginField.setText(users.get(0).getFirstName());
    }
}