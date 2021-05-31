package project.android.imageprocessing.filter;

import java.util.ArrayList;
import java.util.List;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public abstract class CompositeFilter extends MultiInputFilter {
    private List<GLTextureOutputRenderer> filters = new ArrayList();
    private List<BasicFilter> initialFilters = new ArrayList();
    private List<GLTextureOutputRenderer> inputOutputFilters = new ArrayList();
    private List<GLTextureOutputRenderer> terminalFilters = new ArrayList();

    public CompositeFilter(int numOfInputs) {
        super(numOfInputs);
    }

    public void destroy() {
        super.destroy();
        for (GLTextureOutputRenderer filter : this.filters) {
            filter.destroy();
        }
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.inputOutputFilters.contains(source)) {
            if (!this.texturesReceived.contains(source)) {
                super.newTextureReady(texture, source, newData);
                for (BasicFilter initialFilter : this.initialFilters) {
                    initialFilter.newTextureReady(texture, source, newData);
                }
            }
        } else if (this.terminalFilters.contains(source)) {
            super.newTextureReady(texture, source, newData);
        } else {
            for (BasicFilter initialFilter2 : this.initialFilters) {
                initialFilter2.newTextureReady(texture, source, newData);
            }
        }
    }

    protected void registerFilter(GLTextureOutputRenderer filter) {
        if (!this.filters.contains(filter)) {
            this.filters.add(filter);
        }
    }

    protected void registerInitialFilter(BasicFilter filter) {
        this.initialFilters.add(filter);
        registerFilter(filter);
    }

    protected void registerInputOutputFilter(GLTextureOutputRenderer filter) {
        this.inputOutputFilters.add(filter);
    }

    protected void registerTerminalFilter(GLTextureOutputRenderer filter) {
        this.terminalFilters.add(filter);
        registerFilter(filter);
    }

    public void setRenderSize(int width, int height) {
        for (GLTextureOutputRenderer filter : this.filters) {
            filter.setRenderSize(width, height);
        }
        super.setRenderSize(width, height);
    }
}
