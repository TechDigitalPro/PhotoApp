package project.android.imageprocessing.filter;

import java.util.ArrayList;
import java.util.List;
import project.android.imageprocessing.input.GLTextureOutputRenderer;
import project.android.imageprocessing.output.GLTextureInputRenderer;

public abstract class GroupFilter extends BasicFilter {
    private List<BasicFilter> filters = new ArrayList();
    private List<BasicFilter> initialFilters = new ArrayList();
    private List<BasicFilter> terminalFilters = new ArrayList();

    public void destroy() {
        super.destroy();
        for (BasicFilter filter : this.filters) {
            filter.destroy();
        }
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.terminalFilters.contains(source)) {
            setWidth(source.getWidth());
            setHeight(source.getHeight());
            synchronized (getLockObject()) {
                for (GLTextureInputRenderer target : getTargets()) {
                    target.newTextureReady(texture, this, newData);
                }
            }
            return;
        }
        for (BasicFilter initialFilter : this.initialFilters) {
            initialFilter.newTextureReady(texture, source, newData);
        }
    }

    protected void registerFilter(BasicFilter filter) {
        if (!this.filters.contains(filter)) {
            this.filters.add(filter);
        }
    }

    protected void registerInitialFilter(BasicFilter filter) {
        this.initialFilters.add(filter);
        registerFilter(filter);
    }

    protected void registerTerminalFilter(BasicFilter filter) {
        this.terminalFilters.add(filter);
        registerFilter(filter);
    }

    public void setRenderSize(int width, int height) {
        for (BasicFilter filter : this.filters) {
            filter.setRenderSize(width, height);
        }
    }
}
