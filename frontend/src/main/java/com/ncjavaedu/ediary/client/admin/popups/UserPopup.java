package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.ncjavaedu.ediary.client.model.Course;
import com.ncjavaedu.ediary.client.model.User;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.form.TextField;

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

//    @UiField(provided = true)
//    DualListField<Course, String> courseStringDualListField;

    private AdminPopupCallbacks cb = null;

    private static UserPopupUiBinder uiBinder = GWT.create(UserPopupUiBinder.class);

    private User userToEdit;

    private List<Course> courses;

    @UiTemplate("UserPopup.ui.xml")
    interface UserPopupUiBinder extends UiBinder<Widget, UserPopup> {
    }

    public UserPopup()
    {
        super(false);

//        setDualList();

        add(uiBinder.createAndBindUi(this));

        courses = new ArrayList<>();
    }

    public UserPopup(List<Course> courses){
        super(false);

        this.courses = courses;

//        setListBox();

        add(uiBinder.createAndBindUi(this));
    }

    public UserPopup(User user){
        super(false);

//        setDualList();

        add(uiBinder.createAndBindUi(this));

        courses = new ArrayList<>();

        userToEdit = user;

        fillFields();
    }

    public UserPopup(User user, List<Course> courses){
        super(false);

        this.courses = courses;

//        setListBox();

        add(uiBinder.createAndBindUi(this));

        userToEdit = user;

        fillFields();
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
                        if(userToEdit == null) {
                            cb.userPopupValidated(new User(login.getText(),
                                    password.getText(), firstName.getText(), lastName.getText(),
                                    university.getText(), email.getText()), true);
                        }
                        else{
                            cb.userPopupValidated(new User(login.getText(),
                                    password.getText(), firstName.getText(), lastName.getText(),
                                    university.getText(), email.getText()), false);
                        }
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
    }

//    public void setDualList(){
//        CourseProps props = GWT.create(CourseProps.class);
//
//        ListStore<Course> courseListStore = new ListStore<>(props.key());
//        courseListStore.addAll(courses);
//
//        ListStore<Course> coursesToStore = new ListStore<>(props.key());
//
//        courseStringDualListField = new DualListField<>(courseListStore, coursesToStore, props.title(), new TextCell());
//        courseStringDualListField.addValidator(new EmptyValidator<List<Course>>());
//        courseStringDualListField.setEnableDnd(true);
//        courseStringDualListField.setMode(DualListField.Mode.INSERT);
//    }

//    public void setListBox(){
//        coursesListBox = new ListBox();
//
//        coursesListBox.setVisibleItemCount(10);
//        coursesListBox.setMultipleSelect(true);
//
//        for(Course c: courses){
//            coursesListBox.addItem(c.getTitle());
//        }
//    }
}
