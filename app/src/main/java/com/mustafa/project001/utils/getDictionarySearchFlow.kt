package com.mustafa.project001.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

fun getDictionarySearchFlow(
    query: String,
    dictionary: List<String>,
    isSorted: Boolean
): Flow<List<String>> = flow {
    // FIX: Emit all words when query is empty or just spaces
    if (query.isBlank()) {
        if (isSorted) {
            emit(dictionary.sorted()) // Return alphabetically sorted list
        } else {
            emit(dictionary) // Return original unsorted list
        }
        return@flow
    }

    val matches = dictionary
        .map { word ->
            val distance = minEditDistance(query, word)
            val maxLen = maxOf(query.length, word.length)
            val similarity = if (maxLen > 0) (1 - distance.toFloat() / maxLen) else 0f
            word to similarity
        }
        .filter { it.second >= 0.4f }
        .sortedByDescending { it.second }
        .map { it.first }

    if (isSorted) {
        emit(matches.sorted())
    } else {
        emit(matches)
    }
}.flowOn(Dispatchers.Default)

fun minEditDistance(word1: String, word2: String): Int {
    val m = word1.length
    val n = word2.length
    val dp = Array(m + 1) { IntArray(n + 1) }

    for (i in 0..m) dp[i][0] = i
    for (j in 0..n) dp[0][j] = j

    for (i in 1..m) {
        for (j in 1..n) {
            val cost = if (word1[i - 1] == word2[j - 1]) 0 else 1
            dp[i][j] = minOf(
                dp[i - 1][j] + 1,
                dp[i][j - 1] + 1,
                dp[i - 1][j - 1] + cost
            )
        }
    }
    return dp[m][n]
}
