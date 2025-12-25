package com.example.localapi.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.localapi.uicontroller.route.DestinasiDetail
import com.example.localapi.uicontroller.route.DestinasiEdit
import com.example.localapi.uicontroller.route.DestinasiEntry
import com.example.localapi.uicontroller.route.DestinasiHome
import com.example.localapi.view.DetailSiswaScreen
import com.example.localapi.view.EditSiswaScreen
import com.example.localapi.view.EntrySiswaScreen
import com.example.localapi.view.HomeScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {

        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                navigateToItemUpdate = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.itemIdArg) { type = NavType.IntType }
            )
        ) {
            DetailSiswaScreen(
                navigateToEditItem = { id ->
                    navController.navigate("${DestinasiEdit.route}/$id")
                },
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEdit.itemIdArg) { type = NavType.IntType }
            )
        ) {
            EditSiswaScreen(
                navigateBack = { navController.navigate(DestinasiHome.route) },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}