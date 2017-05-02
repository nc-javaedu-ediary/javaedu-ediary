package com.ncjavaedu.ediary.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.admin.popups.AdminPopupCallbacks;
import com.ncjavaedu.ediary.client.admin.popups.CoursePopup;
import com.ncjavaedu.ediary.client.admin.popups.LecturePopup;
import com.ncjavaedu.ediary.client.admin.popups.UserPopup;
import com.ncjavaedu.ediary.client.model.*;
import com.ncjavaedu.ediary.client.props.CourseProps;
import com.ncjavaedu.ediary.client.props.LectureProps;
import com.ncjavaedu.ediary.client.props.RoleValueProvider;
import com.ncjavaedu.ediary.client.props.UserProps;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


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

    @UiField(provided = true)
    ColumnModel<UserDTO> usersCM;
    @UiField(provided = true)
    ListStore<UserDTO> usersStore;
    @UiField
    GridView<UserDTO> usersView;
    @UiField
    Grid<UserDTO> usersGrid;

    @UiField(provided = true)
    ColumnModel<LectureDTO> lecturesCM;
    @UiField(provided = true)
    ListStore<LectureDTO> lecturesStore;
    @UiField
    GridView<LectureDTO> lecturesView;
    @UiField
    Grid<LectureDTO> lecturesGrid;

    @UiField(provided = true)
    ColumnModel<CourseDTO> coursesCM;
    @UiField(provided = true)
    ListStore<CourseDTO> coursesStore;
    @UiField
    GridView<CourseDTO> coursesView;
    @UiField
    Grid<CourseDTO> coursesGrid;

    private static final UserProps userProps = GWT.create(UserProps.class);
    private static final LectureProps lecturesProps = GWT.create(LectureProps.class);
    private static final CourseProps coursesProps = GWT.create(CourseProps.class);

    private Widget widget;
    private static AdminMenuUiBinder uiBinder = GWT.create(AdminMenuUiBinder.class);

    private List<UserDTO> users;
    private List<LectureDTO> lectures;
    private List<CourseDTO> courses;

    private static final Logger logger = Logger.getLogger(AdminMenu.class.getName());

    @UiTemplate("AdminMenu.ui.xml")
    interface AdminMenuUiBinder extends UiBinder<ContentPanel, AdminMenu> {
    }

    public AdminMenu() {
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            users = new ArrayList<>();
            getUsers();

            lectures = new ArrayList<>();
            getLectures();

            courses = new ArrayList<>();
            getCourses();

            // Users tab
            generateUsersTab();

            // Lectures tab
            generateLecturesTab();

            // Course tab
            generateCourseTab();

            widget = uiBinder.createAndBindUi(this);

            GridSelectionModel<UserDTO> gridSelectionModel = new GridSelectionModel<>();
            gridSelectionModel.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<UserDTO>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<UserDTO> selectionChangedEvent) {
                    editUserButton.setEnabled(true);
                }
            });

            GridSelectionModel<LectureDTO> lectureSelectionModel = new GridSelectionModel<>();
            lectureSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<LectureDTO>() {
                        @Override
                        public void onSelectionChanged(SelectionChangedEvent<LectureDTO> selectionChangedEvent) {
                            editLectureButton.setEnabled(true);
                        }
                    });

            GridSelectionModel<CourseDTO> courseSelectionModel = new GridSelectionModel<>();
            courseSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<CourseDTO>() {
                        @Override
                        public void onSelectionChanged(SelectionChangedEvent<CourseDTO> selectionChangedEvent) {
                            editCourseButton.setEnabled(true);
                        }
                    });

            usersGrid.setSelectionModel(gridSelectionModel);
            lecturesGrid.setSelectionModel(lectureSelectionModel);
            coursesGrid.setSelectionModel(courseSelectionModel);

            usersGrid.getView().setAutoFill(true);
            lecturesGrid.getView().setAutoFill(true);
            coursesGrid.getView().setAutoFill(true);
        }

        return widget;
    }

    @UiHandler({"addUserButton"})
    public void addUserButtonClick(SelectEvent selectEvent) {
        final UserPopup popup = new UserPopup(courses);

        popup.ShowUserPopup(this);
    }

    @UiHandler({"editUserButton"})
    public void editUserButtonClick(SelectEvent selectEvent) {
        final UserPopup popup = new UserPopup(usersGrid.getSelectionModel().getSelectedItem(),courses);

        popup.ShowUserPopup(this);
    }

    @UiHandler({"addLectureButton"})
    public void addLectureButtonClick(SelectEvent selectEvent) {
        final LecturePopup popup = new LecturePopup();

        popup.ShowLecturePopup(this);
    }

    @UiHandler({"editLectureButton"})
    public void editLectureButtonClick(SelectEvent selectEvent) {
        final LecturePopup popup = new LecturePopup(lecturesGrid.getSelectionModel().getSelectedItem());

        popup.ShowLecturePopup(this);
    }

    @UiHandler({"addCourseButton"})
    public void addCourseButtonClick(SelectEvent selectEvent) {
        final CoursePopup popup = new CoursePopup();

        popup.ShowCoursePopup(this);
    }

    @UiHandler({"editCourseButton"})
    public void editCourseButtonClick(SelectEvent selectEvent) {
        final CoursePopup popup = new CoursePopup(coursesGrid.getSelectionModel().getSelectedItem());

        popup.ShowCoursePopup(this);
    }

    //-------User Management Tab---------//

    private void generateUsersTab() {
        ColumnConfig<UserDTO, Integer> idCol = new ColumnConfig<>(userProps.userId(), 20, "ID");
        ColumnConfig<UserDTO, String> loginCol = new ColumnConfig<>(userProps.login(), 50, "Логин");
        ColumnConfig<UserDTO, String> passCol = new ColumnConfig<>(userProps.password(), 50, "Пароль");
        ColumnConfig<UserDTO, String> fnCol = new ColumnConfig<>(userProps.firstName(), 80, "Имя");
        ColumnConfig<UserDTO, String> lnCol = new ColumnConfig<>(userProps.lastName(), 100, "Фамилия");
        ColumnConfig<UserDTO, String> uniCol = new ColumnConfig<>(userProps.university(), 80, "Университет");
        ColumnConfig<UserDTO, String> mailCol = new ColumnConfig<>(userProps.email(), 150, "Email");
        RoleValueProvider rvp = new RoleValueProvider();
        ColumnConfig<UserDTO, String> roleCol = new ColumnConfig<>(rvp, 150, "Роль");

        List<ColumnConfig<UserDTO, ?>> userColumns = new ArrayList<>();
        userColumns.add(idCol);
        userColumns.add(loginCol);
        userColumns.add(passCol);
        userColumns.add(fnCol);
        userColumns.add(lnCol);
        userColumns.add(uniCol);
        userColumns.add(mailCol);
        userColumns.add(roleCol);

        usersCM = new ColumnModel<>(userColumns);

        usersStore = new ListStore<>(userProps.key());
        usersStore.addAll(users);
    }

    public void userPopupValidated(UserDTO user, boolean newUser) {
        //TODO: remove
        for(CourseDTO c: user.getCourses()){
            Info.display(c.getTitle().toString(),c.getCourseId().toString());
        }
        if (newUser == false) {
            users.remove(usersGrid.getSelectionModel().getSelectedItem());
            usersGrid.getSelectionModel().deselect(usersGrid.getSelectionModel().getSelectedItem());
            editUserButton.setEnabled(false);
        }
        users.add(user);
        usersStore.replaceAll(users);
        usersGrid.getView().refresh(true);
        // TODO: Fix saving
//        else{
//            AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
//                @Override
//                public void onFailure(Throwable throwable) {
//                    Info.display("Ошибка", "Не удалось сохранить пользователя в базе");
//                    Info.display("Error", throwable.getMessage().toString());
//                }
//
//                @Override
//                public void onSuccess(UserDTO userDTO) {
//                    Info.display("Успешно", "Пользователь сохранен в базе");
//                    users.add(userDTO);
//                    usersStore.replaceAll(users);
//                    usersGrid.getView().refresh(true);
//                }
//            };
//            ClientUserService.App.getInstance().saveUser(user,callback);
//        }
    }

    //-------Lecture Management Tab---------//

    private void generateLecturesTab() {
        ColumnConfig<LectureDTO, Integer> lectureIdCol = new ColumnConfig<>(lecturesProps.lectureId(), 30,
                "ID");
        ColumnConfig<LectureDTO, String> titleCol = new ColumnConfig<>(lecturesProps.title(), 250,
                "Название");
        ColumnConfig<LectureDTO, String> classroomCol = new ColumnConfig<>(lecturesProps.classroom(), 100,
                "Аудитория");
        ColumnConfig<LectureDTO, String> descriptionCol = new ColumnConfig<>(lecturesProps.description(),
                350, "Описание");
        ColumnConfig<LectureDTO, String> homeworkCol = new ColumnConfig<>(lecturesProps.homework(), 300,
                "Домашнее задание");

        List<ColumnConfig<LectureDTO, ?>> lectureColumns = new ArrayList<>();
        lectureColumns.add(lectureIdCol);
        lectureColumns.add(titleCol);
        lectureColumns.add(classroomCol);
        lectureColumns.add(descriptionCol);
        lectureColumns.add(homeworkCol);

        lecturesCM = new ColumnModel<>(lectureColumns);

        lecturesStore = new ListStore<>(lecturesProps.key());
        lecturesStore.addAll(lectures);
    }

    public void lecturePopupValidated(LectureDTO lecture, boolean newLecture) {
        if (newLecture == false) {
            lectures.remove(lecturesGrid.getSelectionModel().getSelectedItem());
            lecturesGrid.getSelectionModel().deselect(lecturesGrid.getSelectionModel().getSelectedItem());
            editLectureButton.setEnabled(false);
        }
        lectures.add(lecture);
        lecturesStore.replaceAll(lectures);
        lecturesGrid.getView().refresh(true);
    }

    //-------Course Management Tab---------//

    private void generateCourseTab() {
        ColumnConfig<CourseDTO, Integer> courseIdCol = new ColumnConfig<>(coursesProps.courseId(), 30, "ID");
        ColumnConfig<CourseDTO, String> courseTitleCol = new ColumnConfig<>(coursesProps.title(), 100,
                "Название");

        List<ColumnConfig<CourseDTO, ?>> coursesColumns = new ArrayList<>();
        coursesColumns.add(courseIdCol);
        coursesColumns.add(courseTitleCol);

        coursesCM = new ColumnModel<>(coursesColumns);

        coursesStore = new ListStore<>(coursesProps.key());
        coursesStore.addAll(courses);
    }

    public void coursePopupValidated(CourseDTO course, boolean newCourse) {
        if (newCourse == false) {
            courses.remove(coursesGrid.getSelectionModel().getSelectedItem());
            coursesGrid.getSelectionModel().deselect(coursesGrid.getSelectionModel().getSelectedItem());
            editCourseButton.setEnabled(false);
        }
        courses.add(course);
        coursesStore.replaceAll(courses);
        coursesGrid.getView().refresh(true);
    }

    private void getUsers(){
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список пользователей");
            }

            @Override
            public void onSuccess(List<UserDTO> users) {
                onGetUsers(users);
                usersStore.replaceAll(users);
                usersGrid.getView().refresh(true);
            }
        };
        ClientUserService.App.getInstance().getUsers(callback);
    }

    private void onGetUsers(List<UserDTO> users){
        this.users.addAll(users);
    }

    private void getLectures(){
        AsyncCallback<List<LectureDTO>> callback = new AsyncCallback<List<LectureDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список лекций");
            }

            @Override
            public void onSuccess(List<LectureDTO> lectures) {
                onGetLectures(lectures);
                lecturesStore.replaceAll(lectures);
                lecturesGrid.getView().refresh(true);
            }
        };
        ClientLectureService.App.getInstance().getLectures(callback);
    }

    private void onGetLectures(List<LectureDTO> lectures){
        this.lectures.addAll(lectures);
    }

    private void getCourses(){
        AsyncCallback<List<CourseDTO>> callback = new AsyncCallback<List<CourseDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список курсов");
            }

            @Override
            public void onSuccess(List<CourseDTO> courses) {
                onGetCourses(courses);
                coursesStore.replaceAll(courses);
                coursesGrid.getView().refresh(true);
            }
        };
        ClientCourseService.App.getInstance().getCourses(callback);
    }

    private void onGetCourses(List<CourseDTO> courses){
        this.courses.addAll(courses);
    }
}