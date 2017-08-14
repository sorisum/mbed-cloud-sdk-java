package com.arm.mbed.cloud.sdk.testutils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SDK {
    private Map<String, APIModule> modules;
    private String packageName;

    public SDK(String packageName, Map<String, APIModule> modules) {
        super();
        setModules(modules);
        setPackageName(packageName);
    }

    public SDK() {
        this(null, null);
    }

    /**
     * @return the modules
     */
    public Map<String, APIModule> getModules() {
        return modules;
    }

    /**
     * @param modules
     *            the modules to set
     */
    public void setModules(Map<String, APIModule> modules) {
        this.modules = modules;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName
     *            the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
        ReflectionUtils.init(packageName);
    }

    public APIModule getModule(String moduleSimpleName) {
        if (moduleSimpleName == null || modules == null) {
            return null;
        }
        return modules.get(moduleSimpleName);

    }

    public void addModule(APIModule module) {
        if (module == null) {
            return;
        }
        if (modules == null) {
            modules = new LinkedHashMap<>();
        }
        modules.put(module.getSimpleName(), module);
    }

}
