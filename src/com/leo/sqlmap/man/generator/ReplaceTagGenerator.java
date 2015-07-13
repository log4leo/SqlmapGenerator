package com.leo.sqlmap.man.generator;

import com.intellij.psi.PsiClass;

import java.util.Map;

/**
 * @author Leo.yy   Created on 15/7/13.
 * @description
 * @see
 */
public interface ReplaceTagGenerator {

    Map<String, String> generateTag(PsiClass psiClass, String tableName) throws Exception;
}
