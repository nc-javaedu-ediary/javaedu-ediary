package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.CourseDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 23.04.17.
 */
public interface CourseProps extends PropertyAccess<CourseDTO> {
    @Editor.Path("courseId")
    ModelKeyProvider<CourseDTO> key();

    ValueProvider<CourseDTO, Integer> courseId();
    ValueProvider<CourseDTO, String> title();
}
