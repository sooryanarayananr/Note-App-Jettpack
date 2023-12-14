package hoods.com.noteapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hoods.com.noteapplication.common.ScreenViewState
import hoods.com.noteapplication.feature_note.data.local.model.Note
import hoods.com.noteapplication.feature_note.presentation.home.HomeScreen
import hoods.com.noteapplication.feature_note.presentation.home.HomeState
import hoods.com.noteapplication.feature_note.presentation.home.notes

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val items = listOf(
                BottomNavigationItem(
                    title = "Daybook",
                    selectedIcon = Icons.Filled.Create,
                    unselectedIcon = Icons.Outlined.Create,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "Calendar",
                    selectedIcon = Icons.Filled.DateRange,
                    unselectedIcon = Icons.Outlined.DateRange,
                    hasNews = false,
                    //badgeCount = 45
                ),
                BottomNavigationItem(
                    title = "More",
                    selectedIcon = Icons.Filled.MoreVert,
                    unselectedIcon = Icons.Outlined.MoreVert,
                    hasNews = false,
                ),
            )

            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyAppBarWithActions()
                    },
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                    },
                                    label = {
                                        Text(
                                            text = item.title,
                                            color = if (selectedItemIndex == index) Color.Red else Color.Gray
                                        )
                                    },
                                    alwaysShowLabel = false,
                                    icon = {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    selectedItemIndex = index
                                                }
                                        ) {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title,
                                                tint = if (index == selectedItemIndex) Color.Red else Color.Gray
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    when (selectedItemIndex) {
                        0 -> {
                            var hasLoadedHome = false
                            val navController = rememberNavController()
                            val onDeleteNote: (Note) -> Unit = { /* Handle note deletion */ }
                            val state = HomeState(notes = ScreenViewState.Loading) // Initial loading state

                            Box(modifier = Modifier.fillMaxSize()) {
                                if (hasLoadedHome) {
                                    NavHost(
                                        modifier = Modifier.fillMaxSize(),
                                        navController = rememberNavController(),
                                        startDestination = "home"
                                    ) {
                                        composable("home") {
                                            HomeScreen(
                                                navController = navController,
                                                state = state,
                                                onDeleteNote = { },
                                                onNoteClicked = { /* Handle note click navigation */ }
                                            )
                                        }
                                    }
                                }

                                FloatingActionButton(
                                    onClick = {
                                        if (!hasLoadedHome) {
                                            hasLoadedHome = true

                                            NavHost(
                                                modifier = Modifier.fillMaxSize(),
                                                navController = rememberNavController(),
                                                startDestination = "home"
                                            ) {
                                                composable("home") {
                                                    HomeScreen(
                                                        navController = navController,
                                                        state = state,
                                                        onDeleteNote = { },
                                                        onNoteClicked = { /* Handle note click navigation */ }
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "+",
                                        //tint = Color.White
                                    )
                                }
                            }
                        }
                        1 -> {
                            // Content for Calendar
                        }

                        2 -> {
                            // Content for More
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBarWithActions() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val oswaldFontFamily = FontFamily(
        Font(resId = R.font.oswald_regular, weight = FontWeight.Normal),
        Font(resId = R.font.oswald_bold, weight = FontWeight.Bold)
    )
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Daybook",
                    fontFamily = oswaldFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = { /* Do something */ }) {
                    Icon(Icons.Default.AccountBox, contentDescription = "Create")
                }
                IconButton(onClick = { /* Do something */ }) {
                    Icon(Icons.Default.Person, contentDescription = "Login")
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = {
                            Row {
                                Icon(Icons.Default.Settings, contentDescription = "Settings")
                                Text("Settings")
                            }
                        },
                        onClick = { Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show() }
                    )
                }
            }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}
