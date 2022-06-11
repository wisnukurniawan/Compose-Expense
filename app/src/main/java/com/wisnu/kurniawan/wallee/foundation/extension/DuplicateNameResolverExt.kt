package com.wisnu.kurniawan.wallee.foundation.extension

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias OnResolveDuplicateName = suspend (String) -> Unit

suspend fun duplicateNameResolver(
    updateName: suspend () -> Unit,
    onDuplicate: () -> Flow<Any>
): Flow<Any> {
    return try {
        updateName()
        flow { emit(Any()) }
    } catch (e: SQLiteConstraintException) {
        onDuplicate()
    }
}

private fun resolveName(
    name: String,
    names: Flow<List<String>>,
    updateNameWithNewName: OnResolveDuplicateName,
): Flow<Any> {
    return names
        .map { name.resolveDuplicate(it) }
        .onEach { updateNameWithNewName(it) }
        .map { Any() }
}

fun String.resolveDuplicate(names: List<String>): String {
    return if (names.any { it == this }) {
        this
            .addSuffixIdentifier()
            .resolveDuplicate(names)
    } else {
        this
    }
}

fun String.addSuffixIdentifier(): String {
    if (isBlank()) return "1"

    val names = this.split(" ")

    if (names.size == 1) return "$this 1"

    val currentIdentifier = names.last().toIntOrNull() ?: return "$this 1"

    val lastIndex = names.lastIndex

    return names.foldIndexed(
        ""
    ) { index, acc, cur ->
        when {
            index == lastIndex -> acc + " ${currentIdentifier + 1}"
            acc.isBlank() -> cur
            else -> "$acc $cur"
        }
    }
}
