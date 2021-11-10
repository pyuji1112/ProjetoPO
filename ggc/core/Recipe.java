package ggc.core;

import java.util.List;
import java.io.Serializable;

public class Recipe implements Serializable {
    private List<Component> _components;

    public Recipe(List<Component> components) {
        _components = components;
    }

    public List<Component> getComponents() {
        return _components;
    }

}
