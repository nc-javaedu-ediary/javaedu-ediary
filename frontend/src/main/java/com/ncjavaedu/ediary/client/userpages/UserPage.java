package com.ncjavaedu.ediary.client.userpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.admin.AdminMenu;
import com.sencha.gxt.widget.core.client.form.DateField;

/**
 * Created by abogdanov on 25.04.17.
 */
public class UserPage implements IsWidget {
    @UiField(provided = true)
    DateField dateBox1;
    @UiField(provided = true)
    DateField dateBox2;
    @UiField(provided = true)
    FlexTable schedule;

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
