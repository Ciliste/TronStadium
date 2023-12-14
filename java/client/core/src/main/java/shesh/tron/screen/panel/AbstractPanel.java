package shesh.tron.screen.panel;

import com.kotcrab.vis.ui.widget.VisWindow;

public abstract class AbstractPanel extends VisWindow {

    protected AbstractPanel() {

        super("", false);

        setMovable(false);

        initUI();
        initUIListeners();
    }

    protected abstract void initUI();

    protected abstract void initUIListeners();
}
