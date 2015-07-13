package com.leo.sqlmap.man.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.leo.sqlmap.man.panel.CollectInfoPanel;

/**
 * @author Leo.yy   Created on 15/7/9.
 * @description
 * @see
 */
public class GenerateSqlMapAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        CollectInfoPanel panel = new CollectInfoPanel(project);
        panel.showInCenter();
    }





}
