package com.ncjavaedu.ediary.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.*;
import com.ncjavaedu.ediary.client.async.UserServiceCallback;
import com.ncjavaedu.ediary.services.UserService;
import com.ncjavaedu.ediary.services.UserServiceAsync;
import com.sencha.gxt.widget.core.client.container.Viewport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MainApplication implements IsWidget, EntryPoint {

    private static ReportingUiBinder uiBinder = GWT.create(ReportingUiBinder.class);
    private Widget widget;

    @Autowired
    UserServiceAsync userService = GWT.create(UserService.class);

    @Override
    public Widget asWidget() {
        if (widget == null) {
            widget = uiBinder.createAndBindUi(this);
        }

        return widget;
    }

    interface ReportingUiBinder extends UiBinder<Widget, MainApplication> {
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Viewport vp = new Viewport();
        vp.add(asWidget());
        RootLayoutPanel.get().add(vp);
        userService.getUsers(new UserServiceCallback());
    }
}
