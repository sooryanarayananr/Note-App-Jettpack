package hoods.com.noteapplication.feature_note.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hoods.com.noteapplication.feature_note.presentation.detail.DeatilAssistedFactory
import hoods.com.noteapplication.feature_note.presentation.detail.DetailScreen
import hoods.com.noteapplication.feature_note.presentation.detail.DetailState
import hoods.com.noteapplication.feature_note.presentation.home.HomeScreen
import hoods.com.noteapplication.feature_note.presentation.home.HomeViewModel

enum class Screens
{
    Home,Detail
}

@Composable
fun NoteNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    assistedFactory: DeatilAssistedFactory
)
{
    NavHost(navController = navHostController,
        startDestination = Screens.Home.name
    )
    {
        composable(route=Screens.Home.name)
        {
            val state by homeViewModel.state.collectAsState()
            HomeScreen(
                navController= navHostController,
                state = state,
                onDeleteNote = homeViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screens.Detail.name}?id=$it"
                    )
                }
            )
        }
        composable(
            route ="${Screens.Detail.name}?id={id}",
            arguments = listOf(
                navArgument("id"){
                    NavType.LongType
                    defaultValue = -1L
                })
        )
        {
            backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            DetailScreen(
                noteId = id,
                assistedFactory = assistedFactory,
                navigateUp = {navHostController.navigateUp()}
            )
        }
    }
}

fun NavHostController.navigateToSingleTop(route:String)
{
    navigate(route)
    {
        popUpTo(graph.findStartDestination().id)
        {
            saveState=true
        }
        launchSingleTop = true
        restoreState = true
    }
}