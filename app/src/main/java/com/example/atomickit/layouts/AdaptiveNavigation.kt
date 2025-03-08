package com.example.atomickit.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.Text

/**
 * Data class representing a navigation item
 *
 * @param title The text label for the navigation item
 * @param icon The icon for the navigation item
 * @param badge Optional badge count (if null, no badge is shown)
 * @param destinationRoute The route identifier for navigation
 */
data class NavItem(
    val title: String,
    val icon: ImageVector,
    val badge: Int? = null,
    val destinationRoute: String
)

/**
 * Adaptive Navigation configuration
 *
 * @param navBackgroundColor Background color for navigation containers
 * @param activeColor Color for active navigation items
 * @param inactiveColor Color for inactive navigation items
 * @param badgeColor Color for badges
 * @param navItemHeight Height for navigation items in sidebar
 * @param breakpoint The width breakpoint to switch between bottom and side navigation (in dp)
 * @param sidebarWidth Width of the sidebar navigation when expanded
 * @param sidebarCollapsedWidth Width of the sidebar when collapsed (icons only)
 * @param bottomNavHeight Height of the bottom navigation bar
 * @param expandSidebarByDefault Whether the sidebar should be expanded by default on larger screens
 */
data class AdaptiveNavConfig(
    val navBackgroundColor: Color = Color(0xFF1E293B),
    val activeColor: Color = Color(0xFF6366F1),
    val inactiveColor: Color = Color(0xFFCBD5E1),
    val badgeColor: Color = Color(0xFFEF4444),
    val navItemHeight: Dp = 56.dp,
    val breakpoint: Dp = 600.dp,
    val sidebarWidth: Dp = 240.dp,
    val sidebarCollapsedWidth: Dp = 72.dp,
    val bottomNavHeight: Dp = 64.dp,
    val expandSidebarByDefault: Boolean = true
)

/**
 * Enum for navigation display modes
 */
enum class NavDisplayMode {
    BOTTOM_NAV,
    SIDEBAR
}

/**
 * An adaptive navigation component that automatically switches between bottom navigation
 * for narrow screens and sidebar navigation for wider screens.
 *
 * @param items List of navigation items
 * @param selectedItem Currently selected navigation item
 * @param onItemSelected Callback when a navigation item is selected
 * @param modifier Modifier for the container
 * @param config Navigation configuration properties
 * @param header Optional composable for the sidebar header
 * @param content Content to be displayed in the main area
 */
@Composable
fun AdaptiveNavigation(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier,
    config: AdaptiveNavConfig = AdaptiveNavConfig(),
    header: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    // States for responsive behavior
    var isSidebarExpanded by remember { mutableStateOf(config.expandSidebarByDefault) }

    ResponsiveLayout(
        ranges = mapOf(
            // Small screens: Bottom navigation
            rememberScreenSizeRange(0.dp, config.breakpoint - 1.dp) to {
                BottomNavLayout(
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = onItemSelected,
                    content = content,
                    config = config,
                    modifier = modifier
                )
            },
            // Large screens: Sidebar navigation
            rememberScreenSizeRange(config.breakpoint, null) to {
                SideNavLayout(
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = onItemSelected,
                    isSidebarExpanded = isSidebarExpanded,
                    onSidebarExpandToggle = { isSidebarExpanded = !isSidebarExpanded },
                    header = header,
                    content = content,
                    config = config,
                    modifier = modifier
                )
            }
        )
    )
}

/**
 * Layout with bottom navigation
 */
@Composable
private fun BottomNavLayout(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    config: AdaptiveNavConfig,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = config.bottomNavHeight),
            content = content
        )

        BottomNavBar(
            items = items,
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
            config = config,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Layout with sidebar navigation
 */
@Composable
private fun SideNavLayout(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    isSidebarExpanded: Boolean,
    onSidebarExpandToggle: () -> Unit,
    config: AdaptiveNavConfig,
    modifier: Modifier = Modifier,
    header: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val sidebarWidth by animateDpAsState(
        targetValue = if (isSidebarExpanded) config.sidebarWidth else config.sidebarCollapsedWidth,
        label = "sidebarWidth"
    )

    Row(modifier = modifier.fillMaxSize()) {
        SideNavBar(
            items = items,
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
            isExpanded = isSidebarExpanded,
            onExpandToggle = onSidebarExpandToggle,
            config = config,
            width = sidebarWidth,
            header = header
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            content = content
        )
    }
}

/**
 * Bottom Navigation Bar component
 */
@Composable
private fun BottomNavBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    config: AdaptiveNavConfig,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(config.bottomNavHeight)
            .background(config.navBackgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedItem == item
                // Each item gets equal width
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    BottomNavItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = { onItemSelected(item) },
                        activeColor = config.activeColor,
                        inactiveColor = config.inactiveColor,
                        badgeColor = config.badgeColor
                    )
                }
            }
        }
    }
}

/**
 * Side Navigation Bar component
 */
@Composable
private fun SideNavBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    config: AdaptiveNavConfig,
    width: Dp,
    header: @Composable (BoxScope.() -> Unit)?
) {
    Column(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .background(config.navBackgroundColor)
            .padding(vertical = 8.dp)
    ) {
        // Header
        if (header != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    ),
                content = header
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Toggle button
        IconButton(
            onClick = onExpandToggle,
            modifier = Modifier
                .align(if (isExpanded) Alignment.End else Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Toggle sidebar",
                tint = config.inactiveColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nav items
        items.forEach { item ->
            val isSelected = selectedItem == item
            SideNavItem(
                item = item,
                isSelected = isSelected,
                isExpanded = isExpanded,
                onClick = { onItemSelected(item) },
                activeColor = config.activeColor,
                inactiveColor = config.inactiveColor,
                badgeColor = config.badgeColor,
                itemHeight = config.navItemHeight
            )
        }
    }
}

/**
 * Bottom Navigation Item
 */
@Composable
private fun BottomNavItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    activeColor: Color,
    inactiveColor: Color,
    badgeColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(56.dp) // Fixed height for consistency
            .clip(RoundedCornerShape(12.dp)) // Add rounded corners for press state
            .clickable(onClick = onClick)
            .padding(6.dp) // Inner padding
    ) {
        // Icon with badge
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (isSelected) activeColor else inactiveColor,
                modifier = Modifier.size(24.dp)
            )

            // Badge
            if (item.badge != null && item.badge > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = 4.dp, y = (-4).dp)
                        .background(badgeColor, CircleShape)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (item.badge > 99) "99+" else item.badge.toString(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Always show title but with different styling when selected
        Text(
            text = item.title,
            color = if (isSelected) activeColor else inactiveColor,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            maxLines = 1
        )
    }
}

/**
 * Side Navigation Item
 */
@Composable
private fun SideNavItem(
    item: NavItem,
    isSelected: Boolean,
    isExpanded: Boolean,
    onClick: () -> Unit,
    activeColor: Color,
    inactiveColor: Color,
    badgeColor: Color,
    itemHeight: Dp
) {
    val background = if (isSelected) {
        Modifier.background(
            color = activeColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp)
        )
    } else {
        Modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(background)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        // Icon with optional badge
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (isSelected) activeColor else inactiveColor,
                modifier = Modifier.size(24.dp)
            )

            // Badge
            if (item.badge != null && item.badge > 0) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = 6.dp, y = (-6).dp)
                        .background(badgeColor, CircleShape)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (item.badge > 99) "99+" else item.badge.toString(),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Text label (only if expanded)
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Text(
                text = item.title,
                color = if (isSelected) activeColor else inactiveColor,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                ),
                maxLines = 1,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

/**
 * Icon with Badge
 */
@Composable
fun IconWithBadge(
    icon: ImageVector,
    badgeCount: Int? = null,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
    badgeColor: Color = Color(0xFFEF4444)
) {
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )

        if (badgeCount != null && badgeCount > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 6.dp, y = (-6).dp)
                    .background(badgeColor, CircleShape)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}