package com.leo.sqlmap.man.generator.impl;

import com.intellij.psi.PsiClass;
import com.leo.sqlmap.man.utils.DOClassUtils;
import com.leo.sqlmap.man.utils.VFSUtils;
import com.leo.sqlmap.man.generator.ReplaceTagGenerator;
import com.leo.sqlmap.man.generator.SqlMapGenerator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leo.yy   Created on 15/7/10.
 * @description
 * @see
 */
public class DefaultSqlMapGenerator implements SqlMapGenerator {

    private static String SQL_MAP_TEMPLATE = "/com/leo/sqlmap/man/generator/impl/sql_map_template.xml";

    private ReplaceTagGenerator replaceTagGenerator = new DefaultReplaceTagGenerator();

    @Override
    public String generateSqlMap(PsiClass psiClass) throws Exception {

        String fullClassName = psiClass.getQualifiedName();
        String shortClassName = DOClassUtils.getClassNameWithoutPackage(fullClassName);

        String tableName = DOClassUtils.getTableNameByShortDOClassName(shortClassName);

        final Map<String, String> stringStringMap = replaceTagGenerator.generateTag(psiClass, tableName);
        Map<String, String> tagMap = stringStringMap;

        try {

            InputStream templateInput = getClass().getResourceAsStream(SQL_MAP_TEMPLATE);
            BufferedReader templateReader = new BufferedReader(new InputStreamReader(templateInput));

            String outputXmlPath = getOutputXmlByDoClass(psiClass);
            BufferedWriter resultWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputXmlPath)));
            String line;
            while ((line = templateReader.readLine()) != null) {
                String replacedLine = replaceWithTagValues(line, tagMap);
                resultWrite.write(replacedLine);
                resultWrite.newLine();
            }

            resultWrite.flush();
            resultWrite.close();
            return outputXmlPath;
        } catch (IOException e) {
            throw e;
        }
    }

    private static String replaceWithTagValues(String origin,Map<String,String> replaceTagVlues) throws Exception{
        StringBuilder ans = new StringBuilder();
        int start = origin.indexOf('{');
        while (start != -1) {
            char dollar = origin.charAt(start - 1);
            if (dollar != '$') {
                throw new Exception("Format error,{} without $ prefix");
            }
            ans.append(origin.substring(0, start-1));
            origin = origin.substring(start);
            int end = origin.indexOf('}');
            if (end == -1) {
                throw new Exception("Format error,'{' occured but with no corresponding '}'");
            }
            // with no '{' and no '}'
            String tagKey = origin.substring(1, end);
            if (replaceTagVlues.containsKey(tagKey)) {
                ans.append(replaceTagVlues.get(tagKey));
            }

            origin = origin.substring(end + 1);
            start = origin.indexOf('{');

        }
        ans.append(origin);
        return ans.toString();
    }

    private static String getOutputXmlByDoClass(PsiClass psiClass) {
        String packageDirPath = VFSUtils.getDirectoryPathByPsiClass(psiClass);
        String outputXmlPath = packageDirPath + "/" + DOClassUtils.getTableNameByShortDOClassName(psiClass.getName()) + "_sql_map.xml";
        return outputXmlPath;
    }





    public static void main(String[] args) throws Exception {
        String origin = "select from $table for $action";
        Map<String, String> tagMap = new HashMap<String, String>();
        tagMap.put("table", "data");
        tagMap.put("action", "update");
        System.out.println(replaceWithTagValues(origin, tagMap));
    }
}
