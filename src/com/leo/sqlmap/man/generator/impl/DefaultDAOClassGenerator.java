package com.leo.sqlmap.man.generator.impl;

import com.intellij.psi.PsiClass;
import com.leo.sqlmap.man.generator.DAOClassGenerator;
import com.leo.sqlmap.man.utils.DOClassUtils;

/**
 * @author Leo.yy   Created on 15/7/14.
 * @description
 * @see
 */
public class DefaultDAOClassGenerator implements DAOClassGenerator {

    @Override
    public String generateDAOClass(PsiClass doClass) {

        String fullClassName = doClass.getQualifiedName();
        String packageName = DOClassUtils.getPackageByClassName(fullClassName);
        String daoPackageName = DOClassUtils.getParentPackage(packageName) + ".dao";
        String daoImplPackageName = daoPackageName + ".impl";

        return null;

    }
}
