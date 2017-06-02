package com.ncjavaedu.ediary.client.props;

import com.google.gwt.editor.client.Editor;
import com.ncjavaedu.ediary.client.model.LectureDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by abogdanov on 22.04.17.
 */
public interface LectureProps extends PropertyAccess<LectureDTO> {
    @Editor.Path("lectureId")
    ModelKeyProvider<LectureDTO> key();

    ValueProvider<LectureDTO, Integer> lectureId();
    ValueProvider<LectureDTO, String> title();
    ValueProvider<LectureDTO, String> classroom();
    ValueProvider<LectureDTO, String> description();
    ValueProvider<LectureDTO, String> homework();
}
