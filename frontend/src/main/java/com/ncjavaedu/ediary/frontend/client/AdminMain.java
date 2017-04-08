package com.ncjavaedu.ediary.frontend.client;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;

import java.util.Arrays;
import java.util.List;

public class AdminMain implements IsWidget {
    private VBoxLayoutContainer widget;

    @Override
    public Widget asWidget() {
        if (widget == null) {
            BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData(new Margins(0, 0, 20, 0));
            flex.setFlex(1);

            widget = new VBoxLayoutContainer(VBoxLayoutContainer.VBoxLayoutAlign.CENTER);
            widget.add(adminMenu());
        }

        return widget;
    }

    public PlainTabPanel adminMenu() {

        PlainTabPanel tabPanel = new PlainTabPanel();

        VerticalPanel tab1 = new VerticalPanel();

        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.add(new Label("Список курсов"));
        horizontalPanel.add(new Button("Добавить курс"));

        List<String> courses = Arrays.asList("Dev", "SE");
        CellList<String> cellList = new CellList<>(new TextCell());
        cellList.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);
        cellList.setRowCount(courses.size(), true);
        // Push the data into the widget.
        cellList.setRowData(0, courses);
        tab1.add(horizontalPanel);
        tab1.add(cellList);

        HTML tab2 = new HTML("Содержимое вкладки tab2");
        tab2.setStyleName("pad-text");

        Label tab3 = new Label("Содержимое вкладки tab3");
        tab3.setStyleName("pad-text");

        Label tab4 = new Label("Содержимое вкладки tab4");
        tab4.setStyleName("pad-text");

        Label tab5 = new Label("Содержимое вкладки tab5");
        tab4.setStyleName("pad-text");

        tabPanel.add(tab1, "Управление курсами");
        tabPanel.add(tab2, "Управление пользователями");
        tabPanel.add(tab3, "Управление лекциями");
        tabPanel.add(tab4, "Расписание лекций");
        tabPanel.add(tab5, "Отчеты");
        return tabPanel;
    }

}

