package com.ncjavaedu.ediary.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.ncjavaedu.ediary.client.admin.popups.AdminPopupCallbacks;
import com.ncjavaedu.ediary.client.admin.popups.CoursePopup;
import com.ncjavaedu.ediary.client.admin.popups.LecturePopup;
import com.ncjavaedu.ediary.client.admin.popups.UserPopup;
import com.ncjavaedu.ediary.client.props.CourseProps;
import com.ncjavaedu.ediary.client.props.LectureProps;
import com.ncjavaedu.ediary.client.props.UserProps;
import com.ncjavaedu.ediary.client.model.Course;
import com.ncjavaedu.ediary.client.model.Lecture;
import com.ncjavaedu.ediary.client.model.User;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.state.client.GridStateHandler;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;


public class AdminMenu implements IsWidget, AdminPopupCallbacks {
    @UiField
    TextButton addUserButton;
    @UiField
    TextButton editUserButton;
    @UiField
    TextButton addLectureButton;
    @UiField
    TextButton editLectureButton;
    @UiField
    TextButton addCourseButton;
    @UiField
    TextButton editCourseButton;

    @UiField(provided=true)
    ColumnModel<User> usersCM;
    @UiField(provided=true)
    ListStore<User> usersStore;
    @UiField
    GridView<User> usersView;
    @UiField
    Grid<User> usersGrid;

    @UiField(provided = true)
    ColumnModel<Lecture> lecturesCM;
    @UiField(provided = true)
    ListStore<Lecture> lecturesStore;
    @UiField
    GridView<Lecture> lecturesView;
    @UiField
    Grid<Lecture> lecturesGrid;

    @UiField(provided = true)
    ColumnModel<Course> coursesCM;
    @UiField(provided = true)
    ListStore<Course> coursesStore;
    @UiField
    GridView<Course> coursesView;
    @UiField
    Grid<Course> coursesGrid;

    private static final UserProps userProps = GWT.create(UserProps.class);
    private static final LectureProps lecturesProps = GWT.create(LectureProps.class);
    private static final CourseProps coursesProps = GWT.create(CourseProps.class);
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
    private ContentPanel panel;
    private static AdminMenuUiBinder uiBinder = GWT.create(AdminMenuUiBinder.class);

    List<User> users;
    List<Lecture> lectures;
    List<Course> courses;

    @UiTemplate("AdminMenu.ui.xml")
    interface AdminMenuUiBinder extends UiBinder<ContentPanel, AdminMenu> {
    }

    public AdminMenu() {
    }

    @Override
    public Widget asWidget() {
        if(panel == null) {
            // TODO: remove this test values
            users = new ArrayList<>();
            users.add(new User("login", "pass",
                    "Name", "LName", "Univ", "Mail"));
            users.add(new User("login2", "password",
                    "Name", "LastName", "ITMO", "somemail@gmail.com"));

            lectures = new ArrayList<>();
            lectures.add(new Lecture("Lecture 1", "466",
                    "Some description here", "Some homework"));
            lectures.add(new Lecture("Lecture 2", "403", "Some description",
                    "Homework"));

            courses = new ArrayList<>();
            courses.add(new Course("Course 1"));
            courses.add(new Course("Course 2"));

            // Users tab
            ColumnConfig<User, Integer> idCol = new ColumnConfig<>(userProps.userId(), 20, "ID");
            ColumnConfig<User, String> loginCol = new ColumnConfig<>(userProps.login(), 50, "Логин");
            ColumnConfig<User, String> passCol = new ColumnConfig<>(userProps.password(), 50, "Пароль");
            ColumnConfig<User, String> fnCol = new ColumnConfig<>(userProps.firstName(), 80, "Имя");
            ColumnConfig<User, String> lnCol = new ColumnConfig<>(userProps.lastName(), 100, "Фамилия");
            ColumnConfig<User, String> uniCol = new ColumnConfig<>(userProps.university(), 80, "Университет");
            ColumnConfig<User, String> mailCol = new ColumnConfig<>(userProps.email(), 150, "Email");

            List<ColumnConfig<User, ?>> userColumns = new ArrayList<>();
            userColumns.add(idCol);
            userColumns.add(loginCol);
            userColumns.add(passCol);
            userColumns.add(fnCol);
            userColumns.add(lnCol);
            userColumns.add(uniCol);
            userColumns.add(mailCol);

            usersCM = new ColumnModel<>(userColumns);

            usersStore = new ListStore<>(userProps.key());
            usersStore.addAll(users);

            // Lectures tab
            ColumnConfig<Lecture, Integer> lectureIdCol = new ColumnConfig<>(lecturesProps.lectureId(), 30,
                    "ID");
            ColumnConfig<Lecture, String> titleCol = new ColumnConfig<>(lecturesProps.title(), 250,
                    "Название");
            ColumnConfig<Lecture, String> classroomCol = new ColumnConfig<>(lecturesProps.classroom(), 100,
                    "Аудитория");
            ColumnConfig<Lecture, String> descriptionCol = new ColumnConfig<>(lecturesProps.description(),
                    350, "Описание");
            ColumnConfig<Lecture, String> homeworkCol = new ColumnConfig<>(lecturesProps.homework(), 300,
                    "Домашнее задание");

            List<ColumnConfig<Lecture, ?>> lectureColumns = new ArrayList<>();
            lectureColumns.add(lectureIdCol);
            lectureColumns.add(titleCol);
            lectureColumns.add(classroomCol);
            lectureColumns.add(descriptionCol);
            lectureColumns.add(homeworkCol);

            lecturesCM = new ColumnModel<>(lectureColumns);

            lecturesStore = new ListStore<>(lecturesProps.key());
            lecturesStore.addAll(lectures);

            // Course tab
            ColumnConfig<Course, Integer> courseIdCol = new ColumnConfig<>(coursesProps.courseId(),30, "ID");
            ColumnConfig<Course, String> courseTitleCol = new ColumnConfig<>(coursesProps.title(), 100,
                    "Название");

            List<ColumnConfig<Course,?>> coursesColumns = new ArrayList<>();
            coursesColumns.add(courseIdCol);
            coursesColumns.add(courseTitleCol);

            coursesCM = new ColumnModel<>(coursesColumns);

            coursesStore = new ListStore<>(coursesProps.key());
            coursesStore.addAll(courses);

            panel = uiBinder.createAndBindUi(this);

            GridSelectionModel<User> gridSelectionModel = new GridSelectionModel<>();
            gridSelectionModel.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<User>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<User> selectionChangedEvent) {
                    editUserButton.setEnabled(true);
                }
            });

            GridSelectionModel<Lecture> lectureSelectionModel = new GridSelectionModel<>();
            lectureSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<Lecture>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<Lecture> selectionChangedEvent) {
                    editLectureButton.setEnabled(true);
                }
            });

            GridSelectionModel<Course> courseSelectionModel = new GridSelectionModel<>();
            courseSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<Course>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<Course> selectionChangedEvent) {
                    editCourseButton.setEnabled(true);
                }
            });

            usersGrid.setSelectionModel(gridSelectionModel);
            lecturesGrid.setSelectionModel(lectureSelectionModel);
            coursesGrid.setSelectionModel(courseSelectionModel);

            usersGrid.getView().setAutoFill(true);
            lecturesGrid.getView().setAutoFill(true);
            coursesGrid.getView().setAutoFill(true);

            GridStateHandler<User> userState = new GridStateHandler<>(usersGrid);
            userState.loadState();

            GridStateHandler<Lecture> lectureState = new GridStateHandler<>(lecturesGrid);
            lectureState.loadState();

            GridStateHandler<Course> courseState = new GridStateHandler<>(coursesGrid);
            courseState.loadState();
        }

        return panel;
    }

    @UiHandler({"addUserButton"})
    public void addUserButtonClick(SelectEvent selectEvent){
        final UserPopup popup = new UserPopup(courses);

        popup.ShowUserPopup(this);
    }

    @UiHandler({"editUserButton"})
    public void editUserButtonClick(SelectEvent selectEvent) {
        final UserPopup popup = new UserPopup(usersGrid.getSelectionModel().getSelectedItem(), courses);

        popup.ShowUserPopup(this);
    }

    @UiHandler({"addLectureButton"})
    public void addLectureButtonClick(SelectEvent selectEvent){
        final LecturePopup popup = new LecturePopup();

        popup.ShowLecturePopup(this);
    }

    @UiHandler({"editLectureButton"})
    public void editLectureButtonClick(SelectEvent selectEvent) {
        final LecturePopup popup = new LecturePopup(lecturesGrid.getSelectionModel().getSelectedItem());

        popup.ShowLecturePopup(this);
    }

    @UiHandler({"addCourseButton"})
    public void addCourseButtonClick(SelectEvent selectEvent){
        final CoursePopup popup = new CoursePopup();

        popup.ShowCoursePopup(this);
    }

    @UiHandler({"editCourseButton"})
    public void editCourseButtonClick(SelectEvent selectEvent) {
        final CoursePopup popup = new CoursePopup(coursesGrid.getSelectionModel().getSelectedItem());

        popup.ShowCoursePopup(this);
    }

    public void userPopupValidated(User user, boolean newUser){
        if(newUser == false)
        {
            users.remove(usersGrid.getSelectionModel().getSelectedItem());
            usersGrid.getSelectionModel().deselect(usersGrid.getSelectionModel().getSelectedItem());
            editUserButton.setEnabled(false);
        }
        users.add(user);
        usersStore.replaceAll(users);
        usersStore.commitChanges();
        usersView.refresh(false);
    }

    public void lecturePopupValidated(Lecture lecture, boolean newLecture){
        if(newLecture == false)
        {
            lectures.remove(lecturesGrid.getSelectionModel().getSelectedItem());
            lecturesGrid.getSelectionModel().deselect(lecturesGrid.getSelectionModel().getSelectedItem());
            editLectureButton.setEnabled(false);
        }
        lectures.add(lecture);
        lecturesStore.replaceAll(lectures);
        lecturesStore.commitChanges();
        lecturesView.refresh(false);
    }

    public void coursePopupValidated(Course course, boolean newCourse){
        if(newCourse == false)
        {
            courses.remove(coursesGrid.getSelectionModel().getSelectedItem());
            coursesGrid.getSelectionModel().deselect(coursesGrid.getSelectionModel().getSelectedItem());
            editCourseButton.setEnabled(false);
        }
        courses.add(course);
        coursesStore.replaceAll(courses);
        coursesStore.commitChanges();
        coursesView.refresh(false);
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