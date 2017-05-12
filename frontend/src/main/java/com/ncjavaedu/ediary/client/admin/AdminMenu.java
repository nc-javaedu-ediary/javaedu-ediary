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
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.props.CourseProps;
import com.ncjavaedu.ediary.client.props.LectureProps;
import com.ncjavaedu.ediary.client.props.RoleValueProvider;
import com.ncjavaedu.ediary.client.props.UserProps;
import com.ncjavaedu.ediary.client.schedule.Schedule;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AdminMenu implements IsWidget, AdminPopupCallbacks {
    // Users tab
    @UiField
    TextButton addUserButton;
    @UiField
    TextButton editUserButton;
    @UiField
    TextButton removeUserButton;

    @UiField(provided = true)
    ColumnModel<UserDTO> usersCM;
    @UiField(provided = true)
    ListStore<UserDTO> usersStore;
    @UiField
    GridView<UserDTO> usersView;
    @UiField
    Grid<UserDTO> usersGrid;

    // Lectures tab
    @UiField
    TextButton addLectureButton;
    @UiField
    TextButton editLectureButton;
    @UiField
    TextButton removeLectureButton;

    @UiField(provided = true)
    ColumnModel<LectureDTO> lecturesCM;
    @UiField(provided = true)
    ListStore<LectureDTO> lecturesStore;
    @UiField
    GridView<LectureDTO> lecturesView;
    @UiField
    Grid<LectureDTO> lecturesGrid;

    // Course tab
    @UiField
    TextButton addCourseButton;
    @UiField
    TextButton editCourseButton;
    @UiField
    TextButton removeCourseButton;

    @UiField(provided = true)
    ColumnModel<CourseDTO> coursesCM;
    @UiField(provided = true)
    ListStore<CourseDTO> coursesStore;
    @UiField
    GridView<CourseDTO> coursesView;
    @UiField
    Grid<CourseDTO> coursesGrid;

    // Schedule tab
    @UiField(provided = true)
    ContentPanel timeTable;
//    Schedule timeTable;


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
            lectures = new ArrayList<>();
            courses = new ArrayList<>();

            // Generate tabs
            generateUsersTab();
            generateLecturesTab();
            generateCourseTab();
            generateSchedule();

            widget = uiBinder.createAndBindUi(this);

            GridSelectionModel<UserDTO> gridSelectionModel = new GridSelectionModel<>();
            gridSelectionModel.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<UserDTO>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<UserDTO> selectionChangedEvent) {
                    editUserButton.setEnabled(true);
                    removeUserButton.setEnabled(true);
                }
            });

            GridSelectionModel<LectureDTO> lectureSelectionModel = new GridSelectionModel<>();
            lectureSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<LectureDTO>() {
                        @Override
                        public void onSelectionChanged(SelectionChangedEvent<LectureDTO> selectionChangedEvent) {
                            editLectureButton.setEnabled(true);
                            removeLectureButton.setEnabled(true);
                        }
                    });

            GridSelectionModel<CourseDTO> courseSelectionModel = new GridSelectionModel<>();
            courseSelectionModel.addSelectionChangedHandler(
                    new SelectionChangedEvent.SelectionChangedHandler<CourseDTO>() {
                        @Override
                        public void onSelectionChanged(SelectionChangedEvent<CourseDTO> selectionChangedEvent) {
                            editCourseButton.setEnabled(true);
                            removeCourseButton.setEnabled(true);
                        }
                    });

            usersGrid.setSelectionModel(gridSelectionModel);
            lecturesGrid.setSelectionModel(lectureSelectionModel);
            coursesGrid.setSelectionModel(courseSelectionModel);

            usersGrid.getView().setAutoFill(true);
            lecturesGrid.getView().setAutoFill(true);
            coursesGrid.getView().setAutoFill(true);

            getUsers();
            getLectures();
            getCourses();
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
        final UserPopup popup = new UserPopup(usersGrid.getSelectionModel().getSelectedItem(), courses);

        popup.ShowUserPopup(this);
    }

    @UiHandler({"removeUserButton"})
    public void removeUserButtonClick(SelectEvent selectEvent){
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to delete");
            }

            @Override
            public void onSuccess(UserDTO dto) {
                for(UserDTO u : users){
                    if(u.getUserId() == dto.getUserId()){
                        users.remove(u);
                        break;
                    }
                }
                usersStore.replaceAll(users);
                usersGrid.getView().refresh(true);
                removeUserButton.setEnabled(false);
                editUserButton.setEnabled(false);
            }
        };
        ClientUserService.App.getInstance().deleteUser(usersGrid.getSelectionModel().getSelectedItem(), callback);
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

    @UiHandler({"removeLectureButton"})
    public void removeLectureButtonClick(SelectEvent selectEvent){
        AsyncCallback<LectureDTO> callback = new AsyncCallback<LectureDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to delete");
            }

            @Override
            public void onSuccess(LectureDTO dto) {
                for(LectureDTO l : lectures){
                    if(l.getLectureId() == dto.getLectureId()){
                        lectures.remove(l);
                        break;
                    }
                }
                lecturesStore.replaceAll(lectures);
                lecturesGrid.getView().refresh(true);
                removeLectureButton.setEnabled(false);
                editLectureButton.setEnabled(false);
            }
        };
        ClientLectureService.App.getInstance().deleteLecture(lecturesGrid.getSelectionModel().getSelectedItem(),
                callback);
    }

    @UiHandler({"addCourseButton"})
    public void addCourseButtonClick(SelectEvent selectEvent) {
        final CoursePopup popup = new CoursePopup(lectures);

        popup.ShowCoursePopup(this);
    }

    @UiHandler({"editCourseButton"})
    public void editCourseButtonClick(SelectEvent selectEvent) {
        final CoursePopup popup = new CoursePopup(coursesGrid.getSelectionModel().getSelectedItem(), lectures);

        popup.ShowCoursePopup(this);
    }

    @UiHandler({"removeCourseButton"})
    public void removeCourseButtonClick(SelectEvent selectEvent){
        AsyncCallback<CourseDTO> callback = new AsyncCallback<CourseDTO>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("!", "Failed to delete");
            }

            @Override
            public void onSuccess(CourseDTO dto) {
                for(CourseDTO c : courses){
                    if(c.getCourseId() == dto.getCourseId()){
                        courses.remove(c);
                        break;
                    }
                }
                coursesStore.replaceAll(courses);
                coursesGrid.getView().refresh(true);
                removeCourseButton.setEnabled(false);
                editCourseButton.setEnabled(false);
            }
        };
        ClientCourseService.App.getInstance().deleteCourse(coursesGrid.getSelectionModel().getSelectedItem(),
                callback);
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

    public void userPopupValidated(UserDTO user) {
        if (tryUpdateUser(user) == false) {
            users.add(user);
        }
        usersStore.replaceAll(users);
        usersGrid.getView().refresh(true);
    }

    private boolean tryUpdateUser(UserDTO user) {
        for (UserDTO u : users) {
            if (u.getUserId() == user.getUserId()) {
                u.setUserDTO(user);
                return true;
            }
        }
        return false;
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

    public void lecturePopupValidated(LectureDTO lecture) {
        if (tryUpdateLecture(lecture) == false) {
            lectures.add(lecture);
        }
        lecturesStore.replaceAll(lectures);
        lecturesGrid.getView().refresh(true);
    }

    private boolean tryUpdateLecture(LectureDTO lecture) {
        for (LectureDTO l : lectures) {
            if (l.getLectureId() == lecture.getLectureId()) {
                l.setLectureDTO(lecture);
                return true;
            }
        }
        return false;
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

    public void coursePopupValidated(CourseDTO course) {
        if (tryUpdateCourse(course) == false) {
            courses.add(course);
        }
        coursesStore.replaceAll(courses);
        coursesGrid.getView().refresh(true);
    }

    private boolean tryUpdateCourse(CourseDTO course) {
        for (CourseDTO c : courses) {
            if (c.getCourseId() == course.getCourseId()) {
                c.setCourseDTO(course);
                return true;
            }
        }
        return false;
    }

    //----------Timetable Tab-----------//

    private void generateSchedule() {
//        logger.log(Level.WARNING, "In generateSchedule " + lectures.size());

        timeTable = new ContentPanel();
    }

    //----------*****Get data*****-----------//

    private void getUsers() {
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список пользователей");
            }

            @Override
            public void onSuccess(List<UserDTO> users) {
                onGetUsers(users);
                usersStore.replaceAll(users);
                usersGrid.getView().refresh(true);
                usersGrid.reconfigure(usersGrid.getStore(), usersGrid.getColumnModel());
            }
        };
        ClientUserService.App.getInstance().getUsers(callback);
    }

    private void onGetUsers(List<UserDTO> users) {
        this.users.addAll(users);
    }

    private void getLectures() {
        AsyncCallback<List<LectureDTO>> callback = new AsyncCallback<List<LectureDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Info.display("Ошибка", "Не удалось получить список лекций");
            }

            @Override
            public void onSuccess(List<LectureDTO> lectures) {

                onGetLectures(lectures);
//                logger.log(Level.INFO, "success " + lectures.size());
                timeTable.add(new Schedule(lectures));
                lecturesStore.replaceAll(lectures);
                lecturesGrid.getView().refresh(true);
                lecturesGrid.reconfigure(lecturesGrid.getStore(), lecturesGrid.getColumnModel());
            }
        };
        ClientLectureService.App.getInstance().getLectures(callback);
    }

    private void onGetLectures(List<LectureDTO> lectures) {
        this.lectures.addAll(lectures);
    }

    private void getCourses() {
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
                coursesGrid.reconfigure(coursesGrid.getStore(), coursesGrid.getColumnModel());
            }
        };
        ClientCourseService.App.getInstance().getCourses(callback);
    }

    private void onGetCourses(List<CourseDTO> courses) {
        this.courses.addAll(courses);
    }
}