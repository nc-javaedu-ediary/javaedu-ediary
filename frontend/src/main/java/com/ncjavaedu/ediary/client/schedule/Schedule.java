package com.ncjavaedu.ediary.client.schedule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;


public class Schedule implements IsWidget {
    @UiField(provided = true)
    FlexTable table;

    @UiField
    HorizontalLayoutContainer dates;

    private Widget widget;
    private static ScheduleUiBinder uiBinder = GWT.create(ScheduleUiBinder.class);

    @UiTemplate("Schedule.ui.xml")
    interface ScheduleUiBinder extends UiBinder<Widget, Schedule> {
    }

    public Schedule() {
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
//            table = new FlexTable();
//            table.setText(0, 1, "Понедельник");
//            table.setText(0, 2, "Вторник");
//            table.setText(0, 3, "Среда");
//            table.setText(0, 4, "Четверг");
//            table.setText(0, 5, "Пятница");
            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }
}