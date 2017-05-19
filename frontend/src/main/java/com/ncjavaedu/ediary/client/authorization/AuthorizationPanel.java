package com.ncjavaedu.ediary.client.authorization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
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

import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(AuthorizationPanel.class.getName());

    @UiTemplate("AuthorizationPanel.ui.xml")
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
                if (userDTO != null)
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
                Info.display("Ошибка", "Произошла ошибка при отправке запроса на сервер");
                logger.log(Level.INFO, "Произошла ошибка при отправке запроса на сервер " + throwable.getMessage());
            }

            @Override
            public void onSuccess(UserDTO user) {
                saveInSession(user);
                onLogin(user);
            }
        };
        ClientUserService.App.getInstance().getUser(loginField.getText(), passField.getText(), callback);
    }

    private void saveInSession(UserDTO user) {
        AsyncCallback<UserDTO> sessionCallback = new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to store user in session");
            }

            @Override
            public void onSuccess(UserDTO userDTO) {
                logger.log(Level.INFO, userDTO.getLogin() + " saved in session");
            }
        };
        ClientSessionManagementService.App.getInstance().saveUser(user, sessionCallback);
    }

    private void onLogin(UserDTO dto) {
        if (dto == null) {
            Info.display("Ошибка", "Неправильное имя пользователя или пароль");
        } else {
            if (dto.getRole() != null) {
                if (dto.getRole() == RoleDTO.Admin)
                    displayAdminMenu();
                else {
                    logger.log(Level.INFO, "currentUser.getCourses() !! " + dto.getCourses().size());
                    logger.log(Level.INFO, "currentUser.getCourses() !! " + dto.getFirstName());
                    displayUserPage(dto);
                }
            } else
                displayUserPage(dto);
            Info.display("Вы вошли", "Здравствуйте, " + dto.getFirstName());
        }
    }

    private void displayAdminMenu() {
        Viewport vp = new Viewport();
        vp.add(new AdminMenu().asWidget());
        //TODO RootPanel.get("content")
        RootLayoutPanel.get().clear();
        RootLayoutPanel.get().add(vp);
    }

    private void displayUserPage(UserDTO dto) {
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