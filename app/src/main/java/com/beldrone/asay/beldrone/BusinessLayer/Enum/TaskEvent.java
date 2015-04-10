package com.beldrone.asay.beldrone.BusinessLayer.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asay on 13.1.2015.
 */
public enum TaskEvent {
    FOLLOW_ME(1, "Follow Me"),
    LOOK_AT_ME(2, "Look at Me"),
    COME_HOME(3, "Come Home");

    private int code;
    private String label;

    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, TaskEvent> codeToStatusMapping;

    private TaskEvent(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static TaskEvent getStatus(int i) {

        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);

    }

    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, TaskEvent>();
        for (TaskEvent s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }



}
