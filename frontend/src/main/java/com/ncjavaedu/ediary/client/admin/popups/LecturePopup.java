package com.ncjavaedu.ediary.client.admin.popups;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.ncjavaedu.ediary.client.services.ClientLectureService;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.Date;

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
    @UiField
    DatePicker picker;
    @UiField(provided = true)
    Date minDate = new DateWrapper().addDays(-5).asDate();
    @UiField
    TextField hr;
    @UiField
    TextField mm;

    private AdminPopupCallbacks cb = null;
    private Date date;

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
            if(title.getText() != ""){
                if(hr.getText() != "") {
                    if(mm.getText() != "") {

                        int hours, minutes;
                        try {
                            hours = Integer.parseInt(hr.getText());
                        } catch (NumberFormatException e) {
                            hours = 12;
                        }
                        try {
                            minutes = Integer.parseInt(mm.getText());
                        } catch (NumberFormatException e) {
                            minutes = 12;
                        }
                        if(hours < 0 || hours > 23){
                            hours = 12;
                        }
                        if(minutes < 0 || minutes > 59){
                            minutes = 0;
                        }
                        LectureDTO dto = new LectureDTO();
                        if (lectureToEdit != null) {
                            dto.setLectureId(lectureToEdit.getLectureId());
                        }
                        dto.setTitle(title.getText());
                        dto.setClassroom(classroom.getText());
                        dto.setDescription(description.getText());
                        dto.setHomework(homework.getText());
                        if(date != null){
                            date.setHours(hours+3);
                            date.setMinutes(minutes);

                            dto.setDate(date);

                            AsyncCallback<LectureDTO> callback = new AsyncCallback<LectureDTO>() {
                                @Override
                                public void onFailure(Throwable throwable) {

                                }

                                @Override
                                public void onSuccess(LectureDTO lectureDTO) {
                                    Info.display("!", "Success");
                                    cb.lecturePopupValidated(lectureDTO);
                                }
                            };
                            ClientLectureService.App.getInstance().saveLecture(dto, callback);
                            super.hide();
                        }
                    }
                    else
                        Info.display("Ошибка", "Неверный формат вреемени");
                }
                else
                    Info.display("Ошибка", "Неверный формат вреемени");
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

    @UiHandler("picker")
    public void onValueChange(ValueChangeEvent<Date> event) {
        date = event.getValue();
        DateTimeFormat f = DateTimeFormat.getFormat("dd-MM-yyyy");
        Info.display("Value Changed", "You selected " + f.format(date));
    }
}
