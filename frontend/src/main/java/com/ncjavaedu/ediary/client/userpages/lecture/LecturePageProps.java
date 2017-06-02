package com.ncjavaedu.ediary.client.userpages.lecture;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface LecturePageProps extends PropertyAccess<UserDTO>{
    @Editor.Path("userId")
    ModelKeyProvider<UserDTO> key();

    ValueProvider<UserDTO, Integer> userId();
    ValueProvider<UserDTO, String> firstName();
    ValueProvider<UserDTO, String> lastName();
}

