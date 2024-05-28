package com.fabiolourenco.uifeaturebreeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fabiolourenco.common.R

@Composable
fun BreedSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: TextFieldValue = TextFieldValue(""),
    onSearchQueryChange: (TextFieldValue) -> Unit = {},
    onExecuteSearch: () -> Unit = {},
    onClearClicked: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = {
            Text(text = stringResource(R.string.breeds_search_hint))
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search icon"
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = MaterialTheme.colorScheme.background
        ),
        trailingIcon = {
            if (searchQuery.text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear icon",
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable {
                            onSearchQueryChange(TextFieldValue(""))
                            onClearClicked()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onExecuteSearch()
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}