package com.ncjavaedu.ediary.client.schedule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.userpages.lecture.LecturePage;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Schedule extends Composite {
    @UiField
    FlexTable schedule;
    @UiField
    Label monday;
    @UiField
    Label friday;
    @UiField
    TextButton previous;
    @UiField
    TextButton next;

    private static final ScheduleUiBinder uiBinder = GWT.create(ScheduleUiBinder.class);

    private static final Logger logger = Logger.getLogger(Schedule.class.getName());

    private Date currentDate;
    private List<LectureDTO> allLectures;
    private List<CourseDTO> allCoursers;
    private int role;
    private Map<Integer, List<LectureDTO>> weeklectureMap;

    @UiTemplate("Schedule.ui.xml")
    interface ScheduleUiBinder extends UiBinder<Widget, Schedule> {
    }

    public Schedule(){
        initWidget(uiBinder.createAndBindUi(this));
        currentDate = new Date();
        generateShedule(true);
    }

    public Schedule(List<LectureDTO> allLectures) {
        this.allLectures = allLectures;
//        logger.log(Level.INFO, "lectures in Schedule " + allLectures.size());
        currentDate = new Date();
        initWidget(uiBinder.createAndBindUi(this));
        generateShedule(false);
        // Give the overall composite a style name.
//        setStyleName("table-style");
    }

    @UiHandler({"previous"})
    public void toPreviousWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000);
        setMondayAndFriday();
        weekSchedule();
    }

    @UiHandler({"next"})
    public void toNextWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        setMondayAndFriday();
        weekSchedule();
    }


    private void generateShedule(Boolean isTemp) {
        generateTimetableHead();
        if (isTemp) {
            templateSchedule();
        } else {
            createWeeklectureMap();
            weekSchedule();
        }
    }

    private void generateTimetableHead() {
        schedule.setText(0, 1, "Понедельник");
        schedule.setText(0, 2, "Вторник");
        schedule.setText(0, 3, "Среда");
        schedule.setText(0, 4, "Четверг");
        schedule.setText(0, 5, "Пятница");
    }

    private void templateSchedule() {
        schedule.setText(1, 0, "15:00");
        schedule.setText(2, 0, "19:00");
        for (int j = 1; j < 3; j++)
            for (int i = 1; i < 6; i++)
                schedule.setText(j, i, "нет занятий");
        schedule.setWidget(1, 3, generateTimetableCell("Лекция1", "Иванов", "55"));
        schedule.setWidget(2, 2, generateTimetableCell("Лекция1", "Иванов", "55"));

        currentDate = new Date();
        setMondayAndFriday();
    }

    private void weekSchedule() {
        setMondayAndFriday();

        Integer currentWeek = getNumberOfWeek(currentDate) * getYear(currentDate);

        if (weeklectureMap.containsKey(currentWeek)) {
//            logger.log(Level.INFO, "currentWeek exist in map " + currentWeek);

            List<LectureDTO> currentWeekLectures = weeklectureMap.get(currentWeek);

            if (!currentWeekLectures.isEmpty()) {
                Set<String> timeOfLectures = new TreeSet<>();
                for (LectureDTO lecture : currentWeekLectures) {
                    timeOfLectures.add(lecture.getLectureTime());
                }
                String[] timesRow = timeOfLectures.toArray(new String[timeOfLectures.size()]);
                noLecturesTable(timesRow);

                int dayOfWeak;
                for (LectureDTO lecture : currentWeekLectures) {
                    dayOfWeak = getDayOfWeek(lecture.getDate());
                    if (dayOfWeak >= 0 && dayOfWeak < 5)
                        for (int i = 1; i <= timesRow.length; i++) {
                            if (schedule.getText(i, 0).equals(lecture.getLectureTime())) {
                                schedule.setWidget(i, dayOfWeak + 1, generateTimetableCell(lecture));//не более 1 лекции
                                break;
                            }
                        }
                }
            } else {
                noLecturesTable(new String[]{"--:--"});
            }
        } else {
            noLecturesTable(new String[]{"--:--"});
        }

        setMondayAndFriday();
    }

    private void noLecturesTable(String[] timesRow) {
        int rowCount = schedule.getRowCount() - 1;
        int timesRowLength = timesRow.length;
        if (rowCount > timesRowLength) {
            while (rowCount != timesRowLength) {
                schedule.removeRow(rowCount);
                rowCount--;
            }
        }

        for (int i = 0; i < timesRow.length; i++) {
            schedule.setText(i + 1, 0, timesRow[i]);
            for (int j = 1; j <= 5; j++)
                schedule.setText(i + 1, j, "нет занятий");
        }
    }


    private void createWeeklectureMap() {
        weeklectureMap = new HashMap<>();
        Integer key;
        for (LectureDTO l : allLectures) {
            key = getNumberOfWeek(l.getDate()) * getYear(l.getDate());
            if (!weeklectureMap.containsKey(key)) {
                List<LectureDTO> list = new ArrayList<>();
                list.add(l);
                weeklectureMap.put(key, list);
            } else {
                weeklectureMap.get(key).add(l);
            }
        }
    }

    private void setMondayAndFriday() {
        monday.setText(getWeekMonday(currentDate));
        friday.setText(getWeekFriday(currentDate));
    }

    private int getDayOfWeek(Date date) {
        String s = DateTimeFormat.getFormat("EEEE").format(date).toLowerCase();
        switch (s) {
            case "monday":
            case "понедельник":
                return 0;
            case "tuesday":
            case "вторник":
                return 1;
            case "wednesday":
            case "среда":
                return 2;
            case "thursday":
            case "четверг":
                return 3;
            case "friday":
            case "пятница":
                return 4;
            case "saturday":
            case "суббота":
                return 5;
            case "sunday":
            case "воскресенье":
                return 6;
        }
        return 0;
    }

    private String getWeekMonday(Date date) {
        date = new Date(date.getTime() - (getDayOfWeek(date) - 1) * 24 * 60 * 60 * 1000);
        return DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
    }

    private String getWeekFriday(Date date) {
        date = new Date(date.getTime() + (5 - getDayOfWeek(date)) * 24 * 60 * 60 * 1000);
        return DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
    }

    private int getDayOfYear(Date date) {
        int day = getDay(date), month = getMonth(date), year = getYear(date);

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean isLeapYear = (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0));

        int dayOfYear = 0;
        for (int i = 0; i < month - 1; i++)
            dayOfYear += daysInMonth[i];

        dayOfYear += day;

        if (isLeapYear && dayOfYear > 59)
            dayOfYear++; //сколько дней до текущего
        return dayOfYear;
    }

    private int getNumberOfWeek(Date date) {
        long firstThursday = date.getTime() - (getDayOfWeek(date) - 4) * 24 * 3600;
        return ((getDayOfYear(new Date(firstThursday)) - 1) / 7) + 1;
    }

    private int getYear(Date date) {
        return Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(date));
    }

    private int getMonth(Date date) {
        return Integer.parseInt(DateTimeFormat.getFormat("MM").format(date));
    }

    private int getDay(Date date) {
        return Integer.parseInt(DateTimeFormat.getFormat("dd").format(date));
    }

    private Widget generateTimetableCell(String title, String lecturer, String cab) {
        VerticalLayoutContainer lecture = new VerticalLayoutContainer();
        lecture.add(new Label(title));
        lecture.add(new Label(lecturer));
        lecture.add(new Label("Кабинет №" + cab));
//        lecture.add(new Label("" + l.getDay()));
        return lecture;
    }

    private Widget generateTimetableCell(final LectureDTO lectureDTO) {

        Label title = new Label(lectureDTO.getTitle());
        Label lecturer = new Label(lectureDTO.getCourse().getLecturer().getFullName());
        Label classRoom = new Label("Кабинет №" + lectureDTO.getClassroom());


        title.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                logger.log(Level.INFO, "до " + lectureDTO.getTitle());

                final LecturePage lecturePage = new LecturePage(lectureDTO);
                logger.log(Level.INFO, "после ");

//                lecturePage.ShowLecturePopup(this);
                lecturePage.center();
                lecturePage.show();
            }
        });
//        lecturer.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                //TODO toLecturers lectures
//            }
//
//        });
        VerticalLayoutContainer lectureCell = new VerticalLayoutContainer();
        lectureCell.add(title);
        lectureCell.add(lecturer);
        lectureCell.add(classRoom);
//        lectureCell.add(new Label(lectureDTO.getDay()));
        return lectureCell;
    }
}