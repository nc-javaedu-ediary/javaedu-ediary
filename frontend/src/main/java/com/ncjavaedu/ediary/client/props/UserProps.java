package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 20.04.17.
 */
public interface UserProps extends PropertyAccess<UserDTO>{
    @Editor.Path("userId")
    ModelKeyProvider<UserDTO> key();

    ValueProvider<UserDTO, Integer> userId();
    ValueProvider<UserDTO, String> login();
    ValueProvider<UserDTO, String> password();
    ValueProvider<UserDTO, String> firstName();
    ValueProvider<UserDTO, String> lastName();
    ValueProvider<UserDTO, String> university();
    ValueProvider<UserDTO, String> email();
    ValueProvider<UserDTO, RoleDTO> role();
}

