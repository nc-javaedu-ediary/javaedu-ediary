package com.ncjavaedu.ediary.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;


public class AdminMenu implements IsWidget {
    @UiField
    ListBox userList;
    @UiField
    ListBox courseList;

    private Widget widget;
    private static AdminMenuUiBinder uiBinder = GWT.create(AdminMenuUiBinder.class);


    @UiTemplate("AdminMenu.ui.xml")
    interface AdminMenuUiBinder extends UiBinder<Widget, AdminMenu> {
    }

    public AdminMenu() {
    }

    @Override
    public Widget asWidget() {
        if (widget == null){
            widget = uiBinder.createAndBindUi(this);
//            loginField.focus();
        }
        return widget;
    }
}