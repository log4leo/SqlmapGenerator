package com.leo.sqlmap.man.utils;

/**
 * @author Leo.yy   Created on 15/7/14.
 * @description
 * @see
 */
public class DOClassUtils {

    public static String getPackageByClassName(String fullClassName) {
        int index = fullClassName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fullClassName.substring(0, index);
    }

    public static String getParentPackage(String packageName) {
        int index = packageName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return packageName.substring(0, index);
    }


    public static String getDOClassAlias(String fullClassName) {

        String shortClassName = getClassNameWithoutPackage(fullClassName);

        if (StringUtils.isEmpty(shortClassName)) {
            return "";
        }
        return Character.toLowerCase(shortClassName.charAt(0)) + shortClassName.substring(1);
    }

    /**
     * 获取类名，不包含全限定package
     * @param fullClassName
     * @return
     */
    public static String getClassNameWithoutPackage(String fullClassName) {
        int index = fullClassName.lastIndexOf('.');
        if (index == -1) {
            return fullClassName;
        }
        String singleClassName = fullClassName.substring(index + 1);
        return Character.toLowerCase(singleClassName.charAt(0)) + singleClassName.substring(1);
    }

    public static String getTableNameByShortDOClassName(String className) {
        if (StringUtils.isEmpty(className)) {
            return "";
        }
        // should be most cases
        if (className.endsWith("DO")) {
            className = className.substring(0, className.length() - 2);
        }
        StringBuilder ans = new StringBuilder();
        ans.append(Character.toLowerCase(className.charAt(0)));
        for (int i = 1; i < className.length(); i++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c)) {
                ans.append("_");
                ans.append(Character.toLowerCase(c));
            } else {
                ans.append(c);
            }

        }

        return ans.toString();

    }


}
