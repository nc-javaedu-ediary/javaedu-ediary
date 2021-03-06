package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.ncjavaedu.ediary.client.props.LectureProps;
import com.ncjavaedu.ediary.client.props.UserProps;
import com.ncjavaedu.ediary.client.services.ClientCourseService;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursePopup extends PopupPanel {
    @UiField
    TextField title;

    private UserProps userProps = GWT.create(UserProps.class);
    private LectureProps lectureProps = GWT.create(LectureProps.class);

    @UiField(provided = true)
    LabelProvider<UserDTO> cbValueProvider = userProps.fullName();
    @UiField(provided = true)
    ListStore<UserDTO> cbLecturersStore;
    @UiField
    ComboBox lecturer;

    @UiField(provided = true)
    Cell<String> textCell = new TextCell();
    @UiField(provided = true)
    ListStore<LectureDTO> allLecturesStore;
    @UiField(provided = true)
    ListStore<LectureDTO> courseLecturesStore;
    @UiField(provided = true)
    ValueProvider<LectureDTO, String> lectureValueProvider = lectureProps.title();
    @UiField
    DualListField<LectureDTO, String> lecturesList;

    private List<LectureDTO> lectures;
    private List<LectureDTO> newLectures;
//    private List<LectureDTO> removedLectures;
    private boolean isSaved = false;

    private AdminPopupCallbacks cb = null;

    private static final Logger logger = Logger.getLogger(CoursePopup.class.getName());

    private static CoursePopupUiBinder uiBinder = GWT.create(CoursePopupUiBinder.class);

    private CourseDTO courseToEdit;

    // TODO: Fix edit default selection

    @UiTemplate("CoursePopup.ui.xml")
    interface CoursePopupUiBinder extends UiBinder<Widget, CoursePopup> {
    }

    public CoursePopup(){
        super(false);

        fillDualList();
        fillComboBox();

        add(uiBinder.createAndBindUi(this));
    }

    public CoursePopup(List<LectureDTO> lectures) {
        super(false);
        this.lectures = lectures;

        fillDualList();
        fillComboBox();

        add(uiBinder.createAndBindUi(this));
    }

    public CoursePopup(CourseDTO courseToEdit){
        super(false);
        this.courseToEdit = courseToEdit;

        fillDualList();
        fillComboBox();

        add(uiBinder.createAndBindUi(this));

        fillFields();
    }

    public CoursePopup(CourseDTO courseToEdit, List<LectureDTO> lectures) {
        super(false);
        this.courseToEdit = courseToEdit;
        this.lectures = lectures;

        fillDualList();
        fillComboBox();

        add(uiBinder.createAndBindUi(this));

        fillFields();
    }

    @UiHandler({"cancelButton"})
    public void cancelButtonClick(SelectEvent selectEvent) {
        super.hide();
    }

    @UiHandler({"saveButton"})
    public void saveButtonClick(SelectEvent selectEvent) {
        if (cb != null) {
            if (!Objects.equals(title.getText(), "")) {
                UserDTO lec = (UserDTO) lecturer.getValue();
                if (lec != null) {

                    CourseDTO dto = new CourseDTO();

                    if (courseToEdit != null) {
                        dto.setCourseId(courseToEdit.getCourseId());
                    }
                    dto.setTitle(title.getText());


                    newLectures = new ArrayList<>();
                    for (LectureDTO l : lecturesList.getValue()) {
//                        lectureDTOS.add(l);
                        l.setCourse(dto);
                        newLectures.add(l);
                    }

                    logger.log(Level.INFO, "newLectures " + newLectures.size());

                    AsyncCallback<CourseDTO> callback = new AsyncCallback<CourseDTO>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            Info.display("!", "Fail");
                        }

                        @Override
                        public void onSuccess(CourseDTO dto) {
                            isOk(true);
                            cb.coursePopupValidated(dto);
                            Info.display("Success", "Изменения сохранены");

                        }
                    };
                    ClientCourseService.App.getInstance().saveCourse(dto, lec, newLectures, callback);
//                    super.hide();
                } else
                    Info.display("Ошибка", "Лектор не выбран");
            } else
                Info.display("Ошибка", "Название введено неверно");
        } else {
            super.hide();
        }
    }

    private void isOk(boolean b) {
        isSaved(b);
        super.hide();
    }

    public void ShowCoursePopup(final AdminPopupCallbacks cb) {
        this.cb = cb;

        center();
        show();
    }

    public void fillFields() {
        title.setText(courseToEdit.getTitle());
    }

    public void fillComboBox() {
        cbLecturersStore = new ListStore<>(userProps.key());
        lecturer = new ComboBox(cbLecturersStore, cbValueProvider);

        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Info.display("Ошибка", "Не удалось получить список лекторов");
            }

            @Override
            public void onSuccess(List<UserDTO> userDTOS) {
                cbLecturersStore.addAll(userDTOS);
            }
        };
        ClientUserService.App.getInstance().getLecturers(callback);
    }

    public void fillDualList() {
        courseLecturesStore = new ListStore<>(lectureProps.key());
        List<LectureDTO> nonCourseLectures = new ArrayList<>();
        nonCourseLectures.addAll(lectures);
        if (courseToEdit != null) {
            List<LectureDTO> courseLectures = courseToEdit.getLectures();
            courseLecturesStore.addAll(courseLectures);
            for (LectureDTO l : lectures) {
                if (l.getCourse() != null) {
                    nonCourseLectures.remove(l);
                }
            }
        }

        allLecturesStore = new ListStore<>(lectureProps.key());
        if (lectures != null) {
            allLecturesStore.addAll(nonCourseLectures);
        }

        lecturesList = new DualListField<>(allLecturesStore, courseLecturesStore, lectureValueProvider, textCell);
        lecturesList.addValidator(new EmptyValidator<List<LectureDTO>>());
        lecturesList.setEnableDnd(true);
        lecturesList.setMode(DualListField.Mode.INSERT);

    }

    public List<LectureDTO> getNewLectures() {
        return newLectures;
    }

    public List<LectureDTO> getLectures() {
        return lectures;
    }

    private void isSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public boolean isSaved() {
        return isSaved;
    }


}
