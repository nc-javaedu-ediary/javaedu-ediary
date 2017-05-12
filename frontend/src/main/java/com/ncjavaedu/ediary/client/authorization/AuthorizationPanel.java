package com.ncjavaedu.ediary.client.authorization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.admin.AdminMenu;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.ncjavaedu.ediary.client.services.ClientSessionManagementService;
import com.ncjavaedu.ediary.client.userpages.UserPage;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

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
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to get user from session");
            }

            @Override
            public void onSuccess(UserDTO userDTO) {
                if(userDTO != null)
                    onLogin(userDTO);
            }
        };
        ClientSessionManagementService.App.getInstance().getUser(callback);
    }

    @UiHandler({"clearButton"})
    public void onClearClick(SelectEvent event) {
        loginField.setText("");
        passField.setText("");
    }

    @UiHandler({"submitButton"})
    public void onClick(SelectEvent event) {
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("Ошибка", "Произошла ошибка при отправке запроса на сервер"
                        + throwable.getMessage());
            }

            @Override
            public void onSuccess(UserDTO user) {
                AsyncCallback<UserDTO> callback1 = new AsyncCallback<UserDTO>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Info.display("!", "Failed to store user in session");
                    }

                    @Override
                    public void onSuccess(UserDTO userDTO) {
                        onLogin(userDTO);
                    }
                };

                ClientSessionManagementService.App.getInstance().saveUser(user, callback1);
                onLogin(user);
            }
        };
        ClientUserService.App.getInstance().getUser(loginField.getText(),passField.getText(),callback);
    }

    private void onLogin(UserDTO dto){
        if (dto == null){
            Info.display("Ошибка", "Неправильное имя пользователя или пароль");
        } else {
            Info.display("Вы вошли", "Здравствуйте, " + dto.getFirstName());
            if(dto.getRole() != null)
            {
                if(dto.getRole() == RoleDTO.Admin)
                    displayAdminMenu();
                else
                    displayUserPage(dto);
            }
            else
                displayUserPage(dto);
        }
    }

    private void displayAdminMenu(){
        Viewport vp = new Viewport();
        vp.add(new AdminMenu().asWidget());
        //TODO RootPanel.get("content")
        RootLayoutPanel.get().clear();
        RootLayoutPanel.get().add(vp);
    }

    private void displayUserPage(UserDTO dto){
        Viewport vp = new Viewport();
        vp.add(new UserPage(dto).asWidget());
        //TODO RootPanel.get("content")
        RootLayoutPanel.get().clear();
        RootLayoutPanel.get().add(vp);
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}