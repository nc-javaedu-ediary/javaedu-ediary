package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.User;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 20.04.17.
 */
public interface UserProps extends PropertyAccess<User>{
    @Editor.Path("userId")
    ModelKeyProvider<User> key();

    ValueProvider<User, Integer> userId();
    ValueProvider<User, String> login();
    ValueProvider<User, String> password();
    ValueProvider<User, String> firstName();
    ValueProvider<User, String> lastName();
    ValueProvider<User, String> university();
    ValueProvider<User, String> email();
}
