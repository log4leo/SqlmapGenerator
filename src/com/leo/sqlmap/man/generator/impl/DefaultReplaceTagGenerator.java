package com.leo.sqlmap.man.generator.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.leo.sqlmap.man.utils.EntityUtils;
import com.leo.sqlmap.man.generator.ReplaceTagGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.leo.sqlmap.man.generator.ReplaceTagConstants.*;

/**
 * @author Leo.yy   Created on 15/7/13.
 * @description
 * @see
 */
public class DefaultReplaceTagGenerator implements ReplaceTagGenerator {

    private static Set<String> insertExcludedFields = new HashSet<String>();

    static {
        insertExcludedFields.add("id");
        insertExcludedFields.add("gmtCreate");
        insertExcludedFields.add("gmtModified");
    }

    @Override
    public Map<String, String> generateTag(PsiClass psiClass, String tableName) throws Exception {
        String doClassName = psiClass.getQualifiedName();
        Map<String, String> tagMap = new HashMap<String, String>();
        tagMap.put(NAMESPACE, tableName);
        tagMap.put(NAMESPACE_UPPER, toUpperCase(tableName));
        tagMap.put(DO_CLASS, doClassName);
        tagMap.put(DO_ALIAS, getClassSingleAlias(doClassName));
        tagMap.put(SELECT_PARAMS, generateSelectParams(psiClass));
        tagMap.put(UPDATE_PARAMS, generateUpdateParams(psiClass));
        tagMap.put(QUERY_CONDITIONS, generateQueryConditions(psiClass));
        tagMap.put(INSERT_FIELDS, generateInsertFields(psiClass));
        tagMap.put(INSERT_FIELDS_VALUE, generateInsertValues(psiClass));
        return tagMap;
    }

    private static String toUpperCase(String origin) {
        if (origin == null) {
            return null;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            char c = origin.charAt(i);
            ans.append(Character.toUpperCase(c));
        }
        return ans.toString();
    }

    private static String getClassSingleAlias(String className) {
        int index = className.lastIndexOf('.');
        if (index == -1) {
            return className;
        }
        String singleClassName = className.substring(index + 1);
        return Character.toLowerCase(singleClassName.charAt(0)) + singleClassName.substring(1);
    }


    private static String generateSelectParams(PsiClass psiClass) {
        return  iterateFieldsAction(psiClass, new IterateAction() {
            @Override
            public String extractInfo(PsiField psiField) {
                StringBuilder ans = new StringBuilder();
                String fieldName = psiField.getName();
                String columnName = EntityUtils.getColumnByFieldName(fieldName);
                ans.append(columnName);
                ans.append(" as ");
                ans.append(fieldName);
                ans.append(",\n");
                return ans.toString();
            }
        });

    }

    private static String generateUpdateParams(PsiClass psiClass) {
//        <isNotEmpty prepend="," property="orderStatus">
//                order_status=#orderStatus#
//        </isNotEmpty>
        return iterateFieldsAction(psiClass, new IterateAction() {
            @Override
            public String extractInfo(PsiField psiField) {
                StringBuilder ans = new StringBuilder();
                String fieldName = psiField.getName();
                ans.append("<isNotEmpty prepend=\",\" property=\"");
                ans.append(fieldName);
                ans.append("\">\n");
                ans.append(EntityUtils.getColumnByFieldName(fieldName));
                ans.append("=#");
                ans.append(fieldName);
                ans.append("#\n");
                ans.append("</isNotEmpty>\n");
                return ans.toString();
            }
        });

    }

    private static String generateQueryConditions(PsiClass psiClass) {
//        <isNotEmpty prepend="and" property="id">
//                id = #id#
//        </isNotEmpty>
        return  iterateFieldsAction(psiClass, new IterateAction() {
            @Override
            public String extractInfo(PsiField psiField) {
                StringBuilder ans = new StringBuilder();
                String fieldName = psiField.getName();
                ans.append("<isNotEmpty prepend=\"and\" property=\"");
                ans.append(fieldName);
                ans.append("\">\n");
                ans.append(EntityUtils.getColumnByFieldName(fieldName));
                ans.append("=#");
                ans.append(fieldName);
                ans.append("#\n");
                ans.append("</isNotEmpty>\n");
                return ans.toString();
            }
        });


    }

    private static String generateInsertFields(PsiClass psiClass) {
//        transfer_order,is_deleted
        String ans = iterateFieldsAction(psiClass, new IterateAction() {
            @Override
            public String extractInfo(PsiField psiField) {
                StringBuilder ans = new StringBuilder();
                String fieldName = psiField.getName();
                if (insertExcludedFields.contains(fieldName)) {
                    return "";
                }
                ans.append(EntityUtils.getColumnByFieldName(fieldName));
                ans.append(",");
                return ans.toString();
            }
        });
        if (ans.length() > 0) {
            ans = ans.substring(0, ans.length()-1);
        }
        return ans;
    }


    private static String generateInsertValues(PsiClass psiClass) {
        String ret = iterateFieldsAction(psiClass, new IterateAction() {
            @Override
            public String extractInfo(PsiField psiField) {
                String fieldName = psiField.getName();
                if (insertExcludedFields.contains(fieldName)) {
                    return "";
                }
                StringBuilder ans = new StringBuilder();
                ans.append("#");
                ans.append(fieldName);
                ans.append("#");
                ans.append(",");
                return ans.toString();
            }
        });
        if (ret.length() > 0) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    private  interface IterateAction{
        String extractInfo(PsiField psiField);
    }

    private static String iterateFieldsAction(PsiClass psiClass, IterateAction action) {
        if (psiClass == null) {
            return "";
        }
        PsiField[] psiFields = psiClass.getFields();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < psiFields.length; i++) {
            PsiField curField = psiFields[i];
            ans.append(action.extractInfo(curField));
        }
        return ans.toString();
    }
}
