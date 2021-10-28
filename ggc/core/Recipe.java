package ggc.core;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Recipe implements Serializable {
    private List<Component> _components = new ArrayList<>();

    public List<Component> getComponents() {
        return _components;
    }

}
