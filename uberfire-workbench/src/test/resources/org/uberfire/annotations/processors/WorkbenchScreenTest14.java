package org.uberfire.annotations.processors;

import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.annotations.WorkbenchToolBar;
import org.uberfire.client.workbench.widgets.toolbar.ToolBar;

import com.google.gwt.user.client.ui.IsWidget;

@WorkbenchScreen(identifier = "test14")
public class WorkbenchScreenTest14 {

    @WorkbenchPartView
    public IsWidget getView() {
        return null;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "title";
    }

    @WorkbenchToolBar
    public ToolBar getToolBar() {
        return null;
    }

}
