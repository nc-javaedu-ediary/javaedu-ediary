package com.ncjavaedu.ediary.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MainApplication implements IsWidget, EntryPoint {

    private Widget widget;
    private static ReportingUiBinder uiBinder = GWT.create(ReportingUiBinder.class);

    @UiTemplate("MainApplication.ui.xml")
    interface ReportingUiBinder extends UiBinder<Widget, MainApplication> {
    }

    @Override
    public Widget asWidget() {
        if (widget == null) {
            widget = uiBinder.createAndBindUi(this);
        }

        return widget;
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        GWT.setUncaughtExceptionHandler(new
            GWT.UncaughtExceptionHandler() {
                @Override
                public void onUncaughtException(Throwable e) {
                    // do exception handling stuf
                    verifyNotUmbrellaException(e);
//                    e.printStackTrace();
                }
            });

        // do module loading stuff
        Viewport vp = new Viewport();
        vp.add(asWidget());
        RootLayoutPanel.get().add(vp);
    }

    private void verifyNotUmbrellaException(Throwable e) {
        for (Throwable th : ((UmbrellaException) e).getCauses()) {
            if (th instanceof UmbrellaException) {
                verifyNotUmbrellaException(th);
            } else {
                th.printStackTrace();
            }
        }
    }
}
