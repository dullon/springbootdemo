package com.dullon.demoboot.designpattern2;

import java.util.regex.Pattern;

/**
 * 解释器模式（Interpreter） ：它建立一个解释器（Interpreter），对于特定的计算机程序设计语言，用来解释预先定义的文法。简单地说，Interpreter模式是一种简单的语法解释器构架。
 *
 * 解释器模式属于行为模式，Gof是这样定义的：给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子。
 * 解释器模式需要解决的是，如果一种特定类型的问题发生的频率足够高，那么可能就值得将该问题的各个实例表述为一个简单语言中的句子。这样就可以构建一个解释器，该解释器通过解释这些句子来解决该问题。
 *
 * 应用实例 就是正则表达式
 */
public class InterpreterTest {
    public static final String CHARSET_UTF8 = "UTF-8";
    private static final String HTML_REGEX = "</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>";

    public InterpreterTest() {
    }

    //正则表达式
    public static String stripHtml(String text) {
        return Pattern.compile("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>", 2).matcher(text).replaceAll("");
    }

    public static String modelNameTODbName(String modelName) {
        return javaNameToDbName(modelName);
    }

    public static String fieldNameTODbName(String fieldName) {
        return javaNameToDbName(fieldName);
    }

    //把JavaBean 对象的 对应成员变量名 转换成数据库的对应字段名。
    public static String javaNameToDbName(String javaName) {
        if (javaName == null) {
            return null;
        } else if (javaName.length() <= 0) {
            return "";
        } else {
            StringBuffer dbName = new StringBuffer();
            dbName.append(Character.toUpperCase(javaName.charAt(0)));

            for(int namePos = 1; namePos < javaName.length(); ++namePos) {
                char curChar = javaName.charAt(namePos);
                if (Character.isUpperCase(curChar)) {
                    dbName.append('_');
                }

                dbName.append(Character.toUpperCase(curChar));
            }

            return dbName.toString();
        }
    }

    public static String upperFirstChar(String string) {
        if (string == null) {
            return null;
        } else if (string.length() <= 1) {
            return string.toLowerCase();
        } else {
            StringBuffer sb = new StringBuffer(string);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }
    }

    public static String lowerFirstChar(String string) {
        if (string == null) {
            return null;
        } else if (string.length() <= 1) {
            return string.toLowerCase();
        } else {
            StringBuffer sb = new StringBuffer(string);
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
            return sb.toString();
        }
    }

    public static String dbNameToClassName(String tableName) {
        return upperFirstChar(dbNameToVarName(tableName));
    }
    //把数据库中的字段名转换成 JavaBean 对象的 对应成员变量名。
    public static String dbNameToVarName(String columnName) {
        if (columnName == null) {
            return null;
        } else {
            StringBuffer fieldName = new StringBuffer(columnName.length());
            boolean toUpper = false;

            for(int i = 0; i < columnName.length(); ++i) {
                char ch = columnName.charAt(i);
                if (ch == '_') {
                    toUpper = true;
                } else if (toUpper) {
                    fieldName.append(Character.toUpperCase(ch));
                    toUpper = false;
                } else {
                    fieldName.append(Character.toLowerCase(ch));
                }
            }

            return fieldName.toString();
        }
    }
}
