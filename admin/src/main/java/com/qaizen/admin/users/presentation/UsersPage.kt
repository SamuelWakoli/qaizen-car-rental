package com.qaizen.admin.users.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun UsersPage(
    windowSize: WindowSizeClass,
    navHostController: NavHostController,
) {

    val itemMaxWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 600.dp
        else -> 300.dp
    }

    Column(
        modifier = Modifier
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier, columns = StaggeredGridCells.Adaptive(itemMaxWidth)
        ) {
            items(20) {
                UserListItem(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                )
            }
        }
    }
}