package com.ncjavaedu.ediary.client.userpages.lecture;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.admin.popups.AdminPopupCallbacks;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LecturePage extends PopupPanel {
    @UiField
    Label theme;
    @UiField
    Label lectDate;
    @UiField
    Label lecturer;
    @UiField
    Label description;
    @UiField
    Label homework;

//    @UiField
//    Grid<UserDTO> usersGrid;
//    @UiField
//    GridView<UserDTO> usersView;
//    @UiField(provided = true)
//    ColumnModel<UserDTO> usersCM;
//    @UiField(provided = true)
//    ListStore<UserDTO> usersStore;
    private static final Logger logger = Logger.getLogger(LecturePage.class.getName());


    private static final LecturePageProps userProps = GWT.create(LecturePageProps.class);

    private LectureDTO lecture;

    private UserDTO lector;
    private CourseDTO course;
    private List<UserDTO> users;

    private static LecturePageUiBinder uiBinder = GWT.create(LecturePageUiBinder.class);
    private AdminPopupCallbacks cb;

    @UiTemplate("LecturePage.ui.xml")
    interface LecturePageUiBinder extends UiBinder<Widget, LecturePage> {
    }

    public LecturePage() {
        super(true);
        add(uiBinder.createAndBindUi(this));
    }

    public LecturePage(LectureDTO lecture, boolean showUserList) {
        super(true);
        this.lecture = lecture;
        course = lecture.getCourse();
        logger.log(Level.INFO, "course " + course);

        lector = course.getLecturer();
        logger.log(Level.INFO, "lector " + lector.getUserId());
        logger.log(Level.INFO, "showUserList " + showUserList);


        if (showUserList)
            for (UserDTO user : course.getUsers()) {
                logger.log(Level.INFO, "user " + user + " " + user.getUserId());

                if (!Objects.equals(user.getUserId(), lector.getUserId())) {
                    users.add(user);
                    logger.log(Level.INFO, "user " + user + " " + user.getUserId());
                }
            }
//        logger.log(Level.INFO, "!users " + users.size());
//        logger.log(Level.INFO, "lector " + lector);

        try {
            generatePage(showUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(uiBinder.createAndBindUi(this));

    }

    private void generatePage(boolean showUserList) {
        theme.setText(lecture.getTitle());
        logger.log(Level.INFO, "lecture.getTitle() ");

        lectDate.setText(lecture.getLectureDay());
        try {
            lecturer.setText(lecture.getCourse().getLecturer().getFullName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        description.setText(lecture.getDescription());
        if (!Objects.equals(lecture.getHomework(), ""))
            homework.setText(lecture.getHomework());
        else
            homework.setText("Не задано");
//        if (showUserList)
//            generateUserList();
    }

//    private void generateUserList() {
////        IdentityValueProvider<UserDTO> provider = new IdentityValueProvider<>();
////        final CheckBoxSelectionModel<UserDTO> selectionModel = new CheckBoxSelectionModel<>(provider);
//        ColumnConfig<UserDTO, Integer> idCol = new ColumnConfig<>(userProps.userId(), 20, "ID");
//        ColumnConfig<UserDTO, String> fnCol = new ColumnConfig<>(userProps.firstName(), 80, "Имя");
//        ColumnConfig<UserDTO, String> lnCol = new ColumnConfig<>(userProps.lastName(), 100, "Фамилия");
//
//        List<ColumnConfig<UserDTO, ?>> userColumns = new ArrayList<>();
////        userColumns.add(selectionModel.getColumn());
//        userColumns.add(idCol);
//        userColumns.add(fnCol);
//        userColumns.add(lnCol);
//
//        usersCM = new ColumnModel<>(userColumns);
//
//        usersStore = new ListStore<>(userProps.key());
////        if (users != null)
////            usersStore.addAll(users);
//    }

    public void ShowLecturePopup(final AdminPopupCallbacks cb) {
        this.cb = cb;

        center();
        show();
    }
}