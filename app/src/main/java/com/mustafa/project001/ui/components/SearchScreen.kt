package com.mustafa.project001.ui.components

import DictionarySearchRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController

import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun SearchScreen(navController: NavHostController) {
    var textValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var listDictionarySortType by rememberSaveable { mutableStateOf(true) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchHeader(
            textValue = textValue,
            onValueChange = { textValue = it },
            onBackClick = { navController.popBackStack() },
            focusRequester = focusRequester,
            onSortClick = {listDictionarySortType = !listDictionarySortType},
            isSorted = listDictionarySortType,

            )
        DictionarySearchRow(
            isSorted = listDictionarySortType,
            text = textValue,
            navController = navController
        )
    }
}

