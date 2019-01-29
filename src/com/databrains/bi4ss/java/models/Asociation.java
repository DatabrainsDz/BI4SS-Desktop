package com.databrains.bi4ss.java.models;

public class Asociation {
    String module,
            modules;

    public Asociation(String module, String modules) {
        this.module = module;
        this.modules = modules;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}
