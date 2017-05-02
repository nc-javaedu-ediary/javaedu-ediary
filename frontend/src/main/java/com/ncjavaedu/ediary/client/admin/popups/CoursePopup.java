package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Created by abogdanov on 23.04.17.
 */
public class CoursePopup extends PopupPanel {
    @UiField
    TextField title;

    private AdminPopupCallbacks cb = null;

    private static CoursePopupUiBinder uiBinder = GWT.create(CoursePopupUiBinder.class);

    private CourseDTO courseToEdit;

    @UiTemplate("CoursePopup.ui.xml")
    interface CoursePopupUiBinder extends UiBinder<Widget, CoursePopup> {
    }

    public CoursePopup(){
        super(false);

        add(uiBinder.createAndBindUi(this));
    }

    public CoursePopup(CourseDTO courseToEdit){
        super(false);

        add(uiBinder.createAndBindUi(this));

        this.courseToEdit = courseToEdit;

        fillFields();
    }

    @UiHandler({"cancelButton"})
    public void cancelButtonClick(SelectEvent selectEvent){
        super.hide();
    }

    @UiHandler({"saveButton"})
    public void saveButtonClick(SelectEvent selectEvent){
        if(cb != null){
            if(title.isValid()){
                if(courseToEdit == null) {
                    cb.coursePopupValidated(new CourseDTO(title.getText()),true);
                }
                else{
                    cb.coursePopupValidated(new CourseDTO(title.getText()),false);
                }
                Info.display("Редактирование курса", "Изменения сохранены успешно");
                super.hide();
            }
            else
                Info.display("Ошибка", "Название введено неверно");
        }
        else {
            super.hide();
        }
    }

    public void ShowCoursePopup(final AdminPopupCallbacks cb){
        this.cb = cb;

        center();
        show();
    }

    public void fillFields(){
        title.setText(courseToEdit.getTitle());
    }
}
