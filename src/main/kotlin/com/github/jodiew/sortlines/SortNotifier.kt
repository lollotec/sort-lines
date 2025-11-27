package com.github.jodiew.sortlines

import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object SortNotifier {
    fun notifyError(project: Project, message: String, goToAction: () -> Unit) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("com.github.jodiew.sortlines.SortNotifier")
            .createNotification(SortBundle.message("notification.com.github.jodiew.error.title"), message, NotificationType.ERROR)
            .addAction(NotificationAction.createSimple("Go to") {
                goToAction()
            })
            .notify(project)
    }
}
