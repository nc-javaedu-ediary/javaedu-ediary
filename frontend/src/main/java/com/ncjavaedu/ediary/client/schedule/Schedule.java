package com.ncjavaedu.ediary.client.schedule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import java.util.Date;


public class Schedule extends Composite {

    @UiTemplate("Schedule.ui.xml")
    interface ScheduleUiBinder extends UiBinder<Widget, Schedule> {
    }

    private static final ScheduleUiBinder uiBinder = GWT.create(ScheduleUiBinder.class);


    @UiField
    FlexTable schedule;
    @UiField
    DateLabel monday;
    @UiField
    DateLabel sunday;
    @UiField
    TextButton previous;
    @UiField
    TextButton next;

    private Date currentDate;

    public Schedule() {
        initWidget(uiBinder.createAndBindUi(this));
        generateShedule();
        // Give the overall composite a style name.
//        setStyleName("table-style");
    }

    @UiHandler({"previous"})
    public void toPreviousWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000);
        monday.setValue(getWeekMonday(currentDate));
        sunday.setValue(getWeekFriday(currentDate));
//        weekSchedule();
    }

    @UiHandler({"next"})
    public void toNextWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        monday.setValue(getWeekMonday(currentDate));
        sunday.setValue(getWeekFriday(currentDate));
//        weekSchedule();
    }

    private Widget generateTimetableCell(String title, String lecturer, String cab) {
        VerticalLayoutContainer lecture = new VerticalLayoutContainer();
        lecture.add(new Label(title));
        lecture.add(new Label(lecturer));
        lecture.add(new Label("Кабинет №" + cab));
//        lecture.add(new Label("" + l.getDay()));
        return lecture;
    }

    private void generateTimetableHead() {
        schedule.setText(0, 1, "Понедельник");
        schedule.setText(0, 2, "Вторник");
        schedule.setText(0, 3, "Среда");
        schedule.setText(0, 4, "Четверг");
        schedule.setText(0, 5, "Пятница");
    }

    private void generateShedule() {
        generateTimetableHead();
        schedule.setText(1, 0, "15:00");
        schedule.setText(2, 0, "19:00");
        for (int j = 1; j < 3; j++)
            for (int i = 1; i < 6; i++)
                schedule.setText(j, i, "нет занятий");
        schedule.setWidget(1, 3, generateTimetableCell("Лекция1", "Иванов", "55"));
        schedule.setWidget(2, 2, generateTimetableCell("Лекция1", "Иванов", "55"));

        currentDate = new Date();
        monday.setValue(getWeekMonday(currentDate));
        sunday.setValue(getWeekFriday(currentDate));
//            dateBox1 = new DateBox();
//            dateBox1.setValue(new Date());
//        DateCell dateCell = new DateCell();
//        dateCell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
//            dateBox1 = new FieldLabel();
//        dateBox1 = new DateField(dateCell);
//        dateBox2 = new DateField(dateCell);
//        dateBox1.setValue(new Date());
//        dateBox2.setValue(new Date()); //DateBox
//            dateBox1.setText(dateBox2.getText());

    }

    private Date getWeekMonday(Date date) {
        return new Date(date.getTime() - (getDayOfWeek(date) - 1) * 24 * 60 * 60 * 1000);
    }

    private Date getWeekFriday(Date date) {
        return new Date(date.getTime() + (5 - getDayOfWeek(date)) * 24 * 60 * 60 * 1000);
    }

    private int getDayOfWeek(Date date) {
        switch (DateTimeFormat.getFormat("EEEE").format(date)) {
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            case "Sunday":
                return 7;
        }
        return 0;
    }
}