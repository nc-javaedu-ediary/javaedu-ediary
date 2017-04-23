package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.Course;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 23.04.17.
 */
public interface CourseProps extends PropertyAccess<Course> {
    @Editor.Path("courseId")
    ModelKeyProvider<Course> key();

    ValueProvider<Course, Integer> courseId();
    ValueProvider<Course, String> title();
}
