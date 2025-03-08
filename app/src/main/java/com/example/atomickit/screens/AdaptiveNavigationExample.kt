package com.example.atomickit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atomickit.components.Text
import com.example.atomickit.layouts.AdaptiveNavConfig
import com.example.atomickit.layouts.AdaptiveNavigation
import com.example.atomickit.layouts.NavItem
import com.example.atomickit.layouts.ResponsiveLayoutProvider

/**
 * Example showing how to integrate AdaptiveNavigation with Jetpack Navigation.
 * This provides a complete navigation solution that works across all screen sizes.
 */
@Composable
fun AdaptiveNavigationWithJetpackNav() {
    // Set up standard navigation controller
    val navController = rememberNavController()

    // Define navigation items with routes
    val navItems = remember {
        listOf(
            NavItem(
                title = "Home",
                icon = Icons.Default.Home,
                destinationRoute = "home"
            ),
            NavItem(
                title = "Search",
                icon = Icons.Default.Search,
                destinationRoute = "search"
            ),
            NavItem(
                title = "Cart",
                icon = Icons.Default.ShoppingCart,
                badge = 3,
                destinationRoute = "cart"
            ),
            NavItem(
                title = "Profile",
                icon = Icons.Default.Person,
                destinationRoute = "profile"
            ),
            NavItem(
                title = "Settings",
                icon = Icons.Default.Settings,
                destinationRoute = "settings"
            )
        )
    }

    // Get current destination to determine which nav item is selected
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Find the matching NavItem for the current destination
    val currentNavItem = navItems.find { item ->
        currentDestination?.hierarchy?.any {
            it.route == item.destinationRoute
        } == true
    } ?: navItems.first()

    val navConfig = AdaptiveNavConfig(
        navBackgroundColor = Color(0xFF1E293B),
        activeColor = Color(0xFF6366F1),
        inactiveColor = Color(0xFFCBD5E1),
        badgeColor = Color(0xFFEF4444),
        breakpoint = 840.dp
    )

    ResponsiveLayoutProvider {
        AdaptiveNavigation(
            items = navItems,
            selectedItem = currentNavItem,
            onItemSelected = { navItem ->
                // Handle navigation using NavController
                navController.navigate(navItem.destinationRoute) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            config = navConfig,
            header = {
                Text(
                    text = "My App",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        ) {
            // Content area with NavHost
            Box(modifier = Modifier.fillMaxSize()) {
                // Set up the navigation graph
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { Text(text= "Home Screen") }
                    composable("search") { Text(text= "Search Screen") }
                    composable("cart") { Text(text= "Favorites Screen") } // Reusing favorites screen as cart
                    composable("profile") { Text(text= "Profile Screen") }
                    composable("settings") { Text(text= "Settings Screen") }
                }
            }
        }
    }
}

/**
 * Example function that shows how to integrate AdaptiveNavigation with navigation-compose
 * in a real world application.
 */
@Composable
fun AdaptiveNavigationUsageGuide() {
    // 1. Define your navigation items with routes that match your navigation graph
    val navItems = listOf(
        NavItem("Home", Icons.Default.Home, destinationRoute = "home"),
        NavItem("Search", Icons.Default.Search, destinationRoute = "search"),
        // Add more items as needed
    )

    // 2. Set up your NavController
    val navController = rememberNavController()

    // 3. Get current destination from NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 4. Determine which navigation item is currently selected
    val currentNavItem = navItems.find { item ->
        currentDestination?.hierarchy?.any { it.route == item.destinationRoute } == true
    } ?: navItems.first()

    // 5. Use ResponsiveLayoutProvider to enable responsive features
    ResponsiveLayoutProvider {
        // 6. Set up the AdaptiveNavigation component
        AdaptiveNavigation(
            items = navItems,
            selectedItem = currentNavItem,
            onItemSelected = { item ->
                // 7. Handle navigation with NavController
                navigateInApp(navController, item.destinationRoute)
            }
        ) {
            // 8. Content area with NavHost
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                // 9. Define your navigation graph
                composable("home") { /* Your home screen */ }
                composable("search") { /* Your search screen */ }
                // Add more destinations as needed
            }
        }
    }
}

/**
 * Helper function to handle navigation in a standard way
 */
private fun navigateInApp(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
fun AdaptiveNavigationPreview() {
    AdaptiveNavigationWithJetpackNav()
}