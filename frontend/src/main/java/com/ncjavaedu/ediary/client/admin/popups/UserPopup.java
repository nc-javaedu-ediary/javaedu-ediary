package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.ncjavaedu.ediary.client.model.*;
import com.ncjavaedu.ediary.client.props.CourseProps;
import com.ncjavaedu.ediary.client.services.ClientUserService;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.core.client.util.ToggleGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abogdanov on 22.04.17.
 */
public class UserPopup extends PopupPanel{

    @UiField
    TextField firstName;
    @UiField
    TextField lastName;
    @UiField
    TextField university;
    @UiField
    TextField email;
    @UiField
    TextField login;
    @UiField
    TextField password;

    @UiField
    Radio roleAdmin;
    @UiField
    Radio roleStudent;
    @UiField
    Radio roleLecturer;

    private CourseProps courseProps = GWT.create(CourseProps.class);

    @UiField(provided = true)
    Cell<String> textCell = new TextCell();
    @UiField(provided = true)
    ListStore<CourseDTO> allCoursesStore;
    @UiField(provided = true)
    ListStore<CourseDTO> userCoursesStore;
    @UiField(provided = true)
    ValueProvider<CourseDTO, String> courseValueProvider = courseProps.title();
    @UiField
    DualListField<CourseDTO, String> coursesList;

    private Radio selected;
    ToggleGroup toggleGroup = new ToggleGroup();

    private AdminPopupCallbacks cb = null;

    private static UserPopupUiBinder uiBinder = GWT.create(UserPopupUiBinder.class);

    private UserDTO userToEdit;

    private List<CourseDTO> courses;

    @UiTemplate("UserPopup.ui.xml")
    interface UserPopupUiBinder extends UiBinder<Widget, UserPopup> {
    }

    public UserPopup()
    {
        super(false);
        fillDualList();

        add(uiBinder.createAndBindUi(this));

        courses = new ArrayList<>();

        createToggleGroup();
    }

    public UserPopup(List<CourseDTO> courses){
        super(false);
        this.courses = courses;
        fillDualList();

        add(uiBinder.createAndBindUi(this));

        createToggleGroup();
        roleStudent.setValue(true);
    }

    public UserPopup(UserDTO user){
        super(false);
        userToEdit = user;
        fillDualList();

        add(uiBinder.createAndBindUi(this));

        courses = new ArrayList<>();

        fillFields();

        createToggleGroup();
    }

    public UserPopup(UserDTO user, List<CourseDTO> courses){
        super(false);
        userToEdit = user;
        this.courses = courses;
        fillDualList();

        add(uiBinder.createAndBindUi(this));

        fillFields();

        createToggleGroup();
    }

    @UiHandler({"cancelButton"})
    public void cancelButtonClick(SelectEvent selectEvent){
        super.hide();
    }

    @UiHandler({"saveButton"})
    public void saveButtonClick(SelectEvent selectEvent){
        if(cb != null){
            if(firstName.isValid()){
                if(login.isValid()){
                    if(password.isValid()){
                        UserDTO dto = new UserDTO();
                        if(userToEdit != null){
                            dto.setUserId(userToEdit.getUserId());
                        }
                        dto.setLogin(login.getText());
                        dto.setPassword(password.getText());
                        dto.setFirstName(firstName.getText());
                        dto.setLastName(lastName.getText());
                        dto.setEmail(email.getText());
                        dto.setUniversity(university.getText());
                        dto.setRole(radioToRoleDTO());
                        List<CourseDTO> courseDTOList = new ArrayList<>();
                        for(CourseDTO c: coursesList.getValue()){
                            courseDTOList.add(c);
                        }
                        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                Info.display("!","Fail1");
                            }

                            @Override
                            public void onSuccess(UserDTO userDTO) {
                                Info.display("!","Success");
                                cb.userPopupValidated(userDTO);
                            }
                        };
                        ClientUserService.App.getInstance().saveUser(dto, courseDTOList, callback);
                        Info.display("Редактирование пользователя", "Изменения сохранены");
                        super.hide();
                    }
                    else
                        Info.display("Ошибка", "Пароль введен неверно");
                }
                else
                    Info.display("Ошибка", "Логин введен неверно");
            }
            else
                Info.display("Ошибка", "Имя введено неверно");
        }
        else {
            super.hide();
        }
    }

    public void ShowUserPopup(final AdminPopupCallbacks cb){
        this.cb = cb;

        center();
        show();
    }

    public void fillFields(){
        firstName.setText(userToEdit.getFirstName());
        lastName.setText(userToEdit.getLastName());
        university.setText(userToEdit.getUniversity());
        email.setText(userToEdit.getEmail());
        login.setText(userToEdit.getLogin());
        password.setText(userToEdit.getPassword());
        RoleDTOToRadio(userToEdit.getRole());
    }

    public void createToggleGroup(){
        toggleGroup.add(roleAdmin);
        toggleGroup.add(roleStudent);
        toggleGroup.add(roleLecturer);

        toggleGroup.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> valueChangeEvent) {
                ToggleGroup group = (ToggleGroup) valueChangeEvent.getSource();
                selected = (Radio) group.getValue();
            }
        });
    }

    public void fillDualList(){
        userCoursesStore = new ListStore<>(courseProps.key());
        List<CourseDTO> nonUserCourses = new ArrayList<>();
        nonUserCourses.addAll(courses);
        if(userToEdit != null){
            List<CourseDTO> userCourses = userToEdit.getCourses();
            userCoursesStore.addAll(userCourses);
            for(CourseDTO c: courses){
                for(CourseDTO c2 : userCourses){
                    if(c.getCourseId() == c2.getCourseId()){
                        nonUserCourses.remove(c);
                    }
                }
            }
        }

        allCoursesStore = new ListStore<>(courseProps.key());
        if(courses != null){
            allCoursesStore.addAll(nonUserCourses);
        }

        coursesList = new DualListField<>(allCoursesStore, userCoursesStore, courseValueProvider, textCell);
        coursesList.addValidator(new EmptyValidator<List<CourseDTO>>());
        coursesList.setEnableDnd(true);
        coursesList.setMode(DualListField.Mode.INSERT);
    }

    public RoleDTO radioToRoleDTO(){
        if(selected == roleAdmin){
            return RoleDTO.Admin;
        }
        else if(selected == roleStudent){
            return RoleDTO.Student;
        }
        return RoleDTO.Lecturer;
    }

    public void RoleDTOToRadio(RoleDTO role){
        roleAdmin.setValue(false);
        roleStudent.setValue(false);
        roleLecturer.setValue(false);
        if(role == RoleDTO.Admin){
            roleAdmin.setValue(true);
        }
        else if(role == RoleDTO.Student){
            roleStudent.setValue(true);
        }
        else{
            roleLecturer.setValue(true);
        }
    }
}
