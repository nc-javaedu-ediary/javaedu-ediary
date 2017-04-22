package com.ncjavaedu.ediary.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;

import java.util.Date;


public class AdminMenu implements IsWidget {
    //    @UiField
//    DateLabel dateLabel;
    @UiField(provided = true)
    DateField dateBox1;
    @UiField(provided = true)
    DateField dateBox2;
    @UiField(provided = true)
    FlexTable schedule;


    private Widget widget;
    private static AdminMenuUiBinder uiBinder = GWT.create(AdminMenuUiBinder.class);


    @UiTemplate("AdminMenu.ui.xml")
    interface AdminMenuUiBinder extends UiBinder<Widget, AdminMenu> {
    }

    public AdminMenu() {
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
//            Расписание
            schedule = new FlexTable();
            schedule.setText(0, 1, "Понедельник");
            schedule.setText(0, 2, "Вторник");
            schedule.setText(0, 3, "Среда");
            schedule.setText(0, 4, "Четверг");
            schedule.setText(0, 5, "Пятница");
            schedule.setText(1, 0, "15:00");
            schedule.setText(2, 0, "19:00");
            for (int j = 1; j < 3; j++)
                for (int i = 1; i < 6; i++)
                    schedule.setText(j, i, "нет занятий");
            schedule.setWidget(1, 3, lectCell("Лекция1", "Иванов", "55"));
            schedule.setWidget(2, 2, lectCell("Лекция1", "Иванов", "55"));

//            dateBox1 = new DateBox();
//            dateBox1.setValue(new Date());
            DateCell dateCell = new DateCell();
            dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
            dateBox1 = new DateField(dateCell);
            dateBox2 = new DateField(dateCell);
            dateBox1.setValue(new Date());
            dateBox2.setValue(new Date());

            widget = uiBinder.createAndBindUi(this);
        }
        return widget;
    }

    private Widget lectCell(String title, String lecturer, String cab) {
        VerticalLayoutContainer lecture = new VerticalLayoutContainer();
        lecture.add(new Label(title));
        lecture.add(new Label(lecturer));
        lecture.add(new Label("Кабинет №" + cab));
//        lecture.add(new Label("" + l.getDay()));
        return lecture;
    }
}