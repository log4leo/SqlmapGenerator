package com.leo.sqlmap.man.generator;

import com.intellij.psi.PsiClass;

/**
 * @author Leo.yy   Created on 15/7/10.
 * @description
 * @see
 */
public interface SqlMapGenerator {

    public String generateSqlMap(PsiClass psiClass, String tableName) throws Exception;

}
