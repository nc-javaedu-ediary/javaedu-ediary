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
    FlexTable schedule_table;
    @UiField
    Label monday;
    @UiField
    Label friday;
    @UiField
    TextButton previous;
    @UiField
    TextButton next;

    private static final ScheduleUiBinder uiBinder = GWT.create(ScheduleUiBinder.class);

    private Date currentDate;
    private List<LectureDTO> allLectures;
    private boolean showUserList = false;
    private Map<Integer, List<LectureDTO>> weeklectureMap;

    private static final Logger logger = Logger.getLogger(Schedule.class.getName());

    @UiTemplate("Schedule.ui.xml")
    interface ScheduleUiBinder extends UiBinder<Widget, Schedule> {
    }

    public Schedule(List<LectureDTO> allLectures, boolean showUserList) {
        this.allLectures = allLectures;
        this.showUserList = showUserList;
        currentDate = new Date();

        initWidget(uiBinder.createAndBindUi(this));
        generateShedule();
    }

    @UiHandler({"previous"})
    public void toPreviousWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000);
        weekSchedule();
    }

    @UiHandler({"next"})
    public void toNextWeek(SelectEvent event) {
        currentDate.setTime(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        weekSchedule();
    }


    private void generateShedule() {
        generateTimetableHead();
        if (allLectures != null && allLectures.size() != 0) {
            createWeeklectureMap();
            weekSchedule();
            logger.log(Level.INFO, "i create weekSchedule");
        } else {
            templateSchedule();
            logger.log(Level.INFO, "i create templateSchedule");
        }
    }

    private void generateTimetableHead() {
        schedule_table.setText(0, 1, "Понедельник");
        schedule_table.setText(0, 2, "Вторник");
        schedule_table.setText(0, 3, "Среда");
        schedule_table.setText(0, 4, "Четверг");
        schedule_table.setText(0, 5, "Пятница");
    }

    private void templateSchedule() {
        setMondayAndFriday();
        noLecturesTable(new String[]{"--:--"});
    }

    private void weekSchedule() {
        templateSchedule();

        Integer currentWeek = getNumberOfWeek(currentDate) * getYear(currentDate);

        if (weeklectureMap != null && !weeklectureMap.isEmpty() && weeklectureMap.containsKey(currentWeek)) {

            List<LectureDTO> currentWeekLectures = weeklectureMap.get(currentWeek);

            if (!currentWeekLectures.isEmpty()) {
                Set<String> timeOfLectures = new TreeSet<>();
                for (LectureDTO lecture : currentWeekLectures) {
//                    if (lecture.getCourse() != null)
                        timeOfLectures.add(lecture.getLectureTime());
                }

                String[] timesRow = timeOfLectures.toArray(new String[timeOfLectures.size()]);
                noLecturesTable(timesRow);
                logger.log(Level.INFO, "i 1");


                int dayOfWeak;
                for (LectureDTO lecture : currentWeekLectures) {
                    dayOfWeak = getDayOfWeek(lecture.getDate());
//                    if (lecture.getCourse() != null) {
                        if (dayOfWeak >= 0 && dayOfWeak < 5) {
                            for (int i = 1; i <= timesRow.length; i++) {
                                if (schedule_table.getText(i, 0).equals(lecture.getLectureTime())) {
                                    if (schedule_table.getText(i, dayOfWeak + 1).equals("нет занятий")) {
                                        schedule_table.setWidget(i, dayOfWeak + 1, generateTimetableCell(lecture));
                                        //не более 1 лекции
                                        logger.log(Level.INFO, "i 3");
                                        break;
                                    } else {

                                        logger.log(Level.WARNING, "more then 1 lecture in " + i + " " + (dayOfWeak + 1));
                                        logger.log(Level.WARNING, "schedule_table.getElement " + schedule_table.getElement().toString());
//                                    Widget w = schedule_table.getWidget(i, dayOfWeak + 1);
//                                    schedule_table.getElement().toString();
//                                    if (w!= null)
//                                        schedule_table.setWidget();
                                    }
                                }
                            }
                        }
//                    }
                }
            }
        }
    }

    private void noLecturesTable(String[] timesRow) {
        int rowCount = schedule_table.getRowCount() - 1;
        int timesRowLength = timesRow.length;
        if (rowCount > timesRowLength) {
            while (rowCount != timesRowLength) {
                schedule_table.removeRow(rowCount);
                rowCount--;
            }
        }

        for (int i = 0; i < timesRow.length; i++) {
            schedule_table.setText(i + 1, 0, timesRow[i]);
            for (int j = 1; j <= 5; j++)
                schedule_table.setText(i + 1, j, "нет занятий");
        }

        schedule_table.getParent().setHeight(Integer.toString(50 + timesRow.length * 22));

    }


    private void createWeeklectureMap() {
        weeklectureMap = new HashMap<>();
        updateWeekLectureMap(allLectures);
    }

    private void updateWeekLectureMap(List<LectureDTO> lectureDTOList) {
        Integer key;
        for (LectureDTO l : lectureDTOList) {
            if (l.getCourse()!= null) {
                if (l.getDate() != null)
                    key = getNumberOfWeek(l.getDate()) * getYear(l.getDate());
                else
                    key = 0;

                if (!weeklectureMap.containsKey(key)) {
                    List<LectureDTO> list = new ArrayList<>();
                    list.add(l);
                    weeklectureMap.put(key, list);
                } else {
                    weeklectureMap.get(key).add(l);
                }
            }
        }
    }

    private void setMondayAndFriday() {
        monday.setText(getWeekMonday(currentDate));
        friday.setText(getWeekFriday(currentDate));
    }

    private int getDayOfWeek(Date date) {
        switch (DateTimeFormat.getFormat("EEEE").format(date).toLowerCase()) {
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
        return 10;
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

    private Widget generateTimetableCell(final LectureDTO lectureDTO) {
        VerticalLayoutContainer lectureCell = new VerticalLayoutContainer();
//        logger.log(Level.INFO, "до create LecturePage " + lectureDTO.getTitle() + " showUserList " + showUserList);

        if (lectureDTO.getTitle() != null) {
            Label title = new Label(lectureDTO.getTitle());
            logger.log(Level.INFO, "lectureDTO.getTitle() " + 1);

            title.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    logger.log(Level.INFO, "lectureDTO.getTitle() " + 2);

                    LecturePage lecturePage = new LecturePage(lectureDTO, showUserList);
//                lecturePage.ShowLecturePopup(this);
                    lecturePage.center();
                    lecturePage.show();

                    logger.log(Level.INFO, "после create LecturePage " + lectureDTO.getTitle());
                }
            });
            lectureCell.add(title);

            logger.log(Level.INFO, "lectureDTO.getTitle() " + 2);
            logger.log(Level.INFO, "lectureDTO.getCourse() " + lectureDTO.getCourse());


            if (lectureDTO.getCourse().getLecturer() != null) {
                //TODO
                logger.log(Level.INFO, "getLecturer " + 2);

                Label lecturer = new Label(lectureDTO.getCourse().getLecturer().getFullName());
//                lecturer.addClickHandler(new ClickHandler() {
//                    @Override
//                    public void onClick(ClickEvent event) {
//                        //TODO toLecturers lectures
//                    }
//
//                });
                lectureCell.add(lecturer);
            }
            logger.log(Level.INFO, "lectureDTO.getTitle() " + 3);

            if (lectureDTO.getClassroom() != null) {
                Label classRoom = new Label("Кабинет №" + lectureDTO.getClassroom());
                lectureCell.add(classRoom);
            }

            logger.log(Level.INFO, "lectureDTO.getTitle() " + 4);

            if (lectureDTO.getDay() != null) {
                lectureCell.add(new Label(lectureDTO.getDay()));
            }
        }
        return lectureCell;
    }

    public void updateSchedule(List<LectureDTO> lectureDTOList){
        updateWeekLectureMap(lectureDTOList);
        weekSchedule();
    }
}