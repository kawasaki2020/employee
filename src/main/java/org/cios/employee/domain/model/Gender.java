package org.cios.employee.domain.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Gender implements Serializable {

    private static final Map<String, Gender> genderMap;

    static {
        Map<String, Gender> map = new HashMap<>();
//        for (Gender gender : values()) {
//            map.put(gender.code, gender);
//        }
        genderMap = Collections.unmodifiableMap(map);
    }

    private final String code;

    private Gender(String code) {
        this.code = code;
    }

    public static Gender getByCode(String code) {
        Gender gender = genderMap.get(code);
        return gender;
    }

    public String getCode() {
        return code;
    }}
