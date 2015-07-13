package com.leo.sqlmap.man.utils;

/**
 * @author Leo.yy   Created on 15/7/13.
 * @description
 * @see
 */
public class EntityUtils {

    /**
     * 根据实体类的字段名获取对应数据库表列名
     * @param fieldName
     * @return
     */
    public static String getColumnByFieldName(String fieldName) {

        if (fieldName == null) {
            return null;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
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
