package com.hasan.notesapp.features.notes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasan.notesapp.R
import com.hasan.notesapp.data.model.NotesResponse
import com.hasan.notesapp.ui.theme.Background
import com.hasan.notesapp.ui.theme.ContentColor
import com.hasan.notesapp.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen() {

    var search by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var isAddDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            isAddDialog = true
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "", tint = Color.White)
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Background)
        ) {
            AppSearchBar(search = search, onValueChange = {
                search = it
            }, modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 50.dp))
        }
    }

    if (isAddDialog) {
        ShowDialogBox(
            title = title,
            description = description,
            onTitleChange = {
                title = it
            },
            onDescriptionChange = {
                description = it
            },
            onClose = {
                isAddDialog = it
            }
        ) {

        }
    }
}


@Composable
fun ShowDialogBox(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onClose: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 15.dp)
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        shape = RoundedCornerShape(16.dp),
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = TopEnd) {
                IconButton(
                    onClick = {
                        onClose(false)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Red)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                AppTextField(
                    text = title,
                    placeholder = stringResource(R.string.hey),
                    onValueChange = onTitleChange
                )
                Spacer(modifier = Modifier.height(15.dp))
                AppTextField(
                    text = description,
                    placeholder = stringResource(R.string.hey),
                    onValueChange = onDescriptionChange,
                    modifier = Modifier.height(300.dp)
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = placeholder, color = Color.Black.copy(alpha = 0.4f))
        }
    )
}

@Composable
fun NotesEachRow(
    notesResponse: NotesResponse,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ContentColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = notesResponse.title, style = TextStyle(
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W600
                    ), modifier = Modifier.weight(0.7f)
                )
                IconButton(
                    onClick = {
                        /*TODO*/
                    },
                    modifier = Modifier
                        .weight(0.3f)
                        .align(CenterVertically)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Red)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = notesResponse.description, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = notesResponse.updated_at.split("T")[0], style = TextStyle(
                    color = Color.Black.copy(alpha = 0.3f),
                    fontSize = 10.sp
                )
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = search,
        shape = RoundedCornerShape(10.dp),
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = Color.Red)
        },
        trailingIcon = {
            if (search.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange("")
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        }
    )

}