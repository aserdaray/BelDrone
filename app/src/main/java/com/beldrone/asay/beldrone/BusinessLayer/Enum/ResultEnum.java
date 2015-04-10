package com.beldrone.asay.beldrone.BusinessLayer.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asay on 24.12.2014.
 */
public enum ResultEnum {
    OK(0, "Success", "The Operation is successful."),
    SUCCESS(1, "Success", "The Operation is successful."),
    DB_FAILED(-1, "DB Error", "Database connection problem!"),
    RESULT_KOPTER_ERROR(-2, "Result Error", "There isn't any Result for KopterDevice at DB"),
    RESULT_MOBILE_ERROR(-3, "Result Error", "There isn't any Result for MobileDevice at DB");

    private int code;
    private String label;
    private String description;

    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, ResultEnum> codeToStatusMapping;

    private ResultEnum(int code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    public static ResultEnum getStatus(int i) {

        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);

    }

    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, ResultEnum>();
        for (ResultEnum s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }


}