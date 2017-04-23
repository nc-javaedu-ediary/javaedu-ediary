package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.Lecture;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 22.04.17.
 */
public interface LectureProps extends PropertyAccess<Lecture> {
    @Editor.Path("lectureId")
    ModelKeyProvider<Lecture> key();

    ValueProvider<Lecture, Integer> lectureId();
    ValueProvider<Lecture, String> title();
    ValueProvider<Lecture, String> classroom();
    ValueProvider<Lecture, String> description();
    ValueProvider<Lecture, String> homework();
}
