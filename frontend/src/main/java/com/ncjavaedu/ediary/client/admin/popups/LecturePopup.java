package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Created by abogdanov on 23.04.17.
 */
public class LecturePopup extends PopupPanel {
    @UiField
    TextField title;
    @UiField
    TextField classroom;
    @UiField
    TextField description;
    @UiField
    TextField homework;

    private AdminPopupCallbacks cb = null;

    private static LecturePopupUiBinder uiBinder = GWT.create(LecturePopupUiBinder.class);

    private LectureDTO lectureToEdit;

    @UiTemplate("LecturePopup.ui.xml")
    interface LecturePopupUiBinder extends UiBinder<Widget, LecturePopup> {
    }

    public LecturePopup(){
        super(false);

        add(uiBinder.createAndBindUi(this));
    }

    public LecturePopup(LectureDTO lectureToEdit){
        super(false);

        add(uiBinder.createAndBindUi(this));

        this.lectureToEdit = lectureToEdit;

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
                if(lectureToEdit == null) {
                    cb.lecturePopupValidated(new LectureDTO(title.getText(), classroom.getText(),
                            description.getText(), homework.getText()),true);
                }
                else{
                    cb.lecturePopupValidated(new LectureDTO(title.getText(), classroom.getText(),
                            description.getText(), homework.getText()),false);
                }
                Info.display("Редактирование лекции", "Изменения сохранены успешно");
                super.hide();
            }
            else
                Info.display("Ошибка", "Название введено неверно");
        }
        else {
            super.hide();
        }
    }

    public void ShowLecturePopup(final AdminPopupCallbacks cb){
        this.cb = cb;

        center();
        show();
    }

    public void fillFields(){
        title.setText(lectureToEdit.getTitle());
        classroom.setText(lectureToEdit.getClassroom());
        description.setText(lectureToEdit.getDescription());
        homework.setText(lectureToEdit.getHomework());
    }
}
