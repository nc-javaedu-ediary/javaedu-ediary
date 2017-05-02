package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class UserPage implements IsWidget {

    private Widget widget;

    private static UserPageUiBinder uiBinder = GWT.create(UserPageUiBinder.class);

    @UiTemplate("UserPage.ui.xml")
    interface UserPageUiBinder extends UiBinder<Widget, UserPage> {
    }

    public UserPage(){

    }

    @Override
    public Widget asWidget(){
        if(widget == null){
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}
