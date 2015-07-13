package com.leo.sqlmap.man.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;

/**
 * @author Leo.yy   Created on 15/7/10.
 * @description
 * @see
 */
public class VFSUtils {

    public static String getDirectoryPathByPsiClass(PsiClass psiClass) {

        PsiDirectory directory = psiClass.getContainingFile().getContainingDirectory();
        String path = directory.getVirtualFile().getCanonicalPath();

        return path;
    }
}
