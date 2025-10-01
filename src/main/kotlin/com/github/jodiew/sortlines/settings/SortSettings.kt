package com.github.jodiew.sortlines.settings

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "SortSettings", storages = [(Storage("sortlines.xml"))])
class SortSettings(internal val project: Project): SerializablePersistentStateComponent<SortSettings.State>(State()) {
    var ascOrderList: String
        get() = state.ascOrderList
        set(value) {
            updateState {
                it.copy(ascOrderList = value)
            }
        }

    var descOrderList: String
        get() = state.descOrderList
        set(value) {
            updateState {
                it.copy(descOrderList = value)
            }
        }

    val allOrders: List<String>
        get() = state.ascOrderList.split(", ") + state.descOrderList.split(", ")

    data class State (
        @JvmField var ascOrderList: String = "asc, ascending, a-z",
        @JvmField var descOrderList: String = "desc, descending, z-a",
    )

    companion object {
        @JvmStatic
        fun getInstance(project: Project): SortSettings = project.service()
    }
}