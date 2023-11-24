package com.soso.common.utils.pattern;

import java.util.regex.Pattern;

public class Validate {

    static public boolean isEmail(String str){
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$",str);
    }
}
