package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 30.04.17.
 */
public interface RoleProps extends PropertyAccess<RoleDTO> {
    ModelKeyProvider<RoleDTO> key();
    LabelProvider<RoleDTO> nameLabel();

    ValueProvider<RoleDTO, Integer> roleInt();
}
