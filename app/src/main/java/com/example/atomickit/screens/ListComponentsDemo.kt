package com.example.atomickit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomButton
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.EnhancedLazyColumn
import com.example.atomickit.components.EnhancedLazyGrid
import com.example.atomickit.components.ListState
import com.example.atomickit.components.PullRefreshConfig
import com.example.atomickit.components.SwipeAction
import com.example.atomickit.components.SwipeableFavoriteDeleteItem
import com.example.atomickit.components.SwipeableMultiActionItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ListDemoItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    var isFavorite: Boolean = false
)

/**
 * Demo screen showcasing the Enhanced Lazy Lists and Swipeable List Items
 */
@Composable
fun ListComponentsDemo() {
    // Sample data
    val items = remember {
        mutableStateListOf(
            ListDemoItem("1", "Inbox", "Check your messages", Icons.Default.Inbox),
            ListDemoItem("2", "Sent", "View sent messages", Icons.Default.Email),
            ListDemoItem("3", "Settings", "Configure your account", Icons.Default.Settings),
            ListDemoItem("4", "Profile", "Update your information", Icons.Default.Person),
            ListDemoItem("5", "Shopping", "View your cart", Icons.Default.ShoppingCart),
            ListDemoItem("6", "Archive", "Access archived items", Icons.Default.Archive)
        )
    }

    // Tab state
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // List state
    var listState by remember { mutableStateOf(ListState.CONTENT) }

    // Demo controls
    var isRefreshing by remember { mutableStateOf(false) }

    // Get the coroutine scope from the current composition
    val scope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
            )
    ) {
        // App bar
        CustomTopAppBar(
            titleText = "List Components",
            backgroundColor = Color(0xFF6366F1),
            contentColor = Color.White,
            elevation = 4.dp
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xFF6366F1),
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("Enhanced Lists") }
            )

            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("Swipeable Items") }
            )

            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = { Text("Multi-Action") }
            )
        }

        // Controls for toggling list states
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "List State: ",
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
            )

            CustomButton(
                onClick = { listState = ListState.CONTENT },
                text = "Content",
                backgroundColor = if (listState == ListState.CONTENT) Color(0xFF6366F1) else Color(
                    0xFFE5E7EB
                ),
                contentColor = if (listState == ListState.CONTENT) Color.White else Color.Black
            )

            CustomButton(
                onClick = { listState = ListState.LOADING },
                text = "Loading",
                backgroundColor = if (listState == ListState.LOADING) Color(0xFF6366F1) else Color(
                    0xFFE5E7EB
                ),
                contentColor = if (listState == ListState.LOADING) Color.White else Color.Black
            )

            CustomButton(
                onClick = { listState = ListState.EMPTY },
                text = "Empty",
                backgroundColor = if (listState == ListState.EMPTY) Color(0xFF6366F1) else Color(
                    0xFFE5E7EB
                ),
                contentColor = if (listState == ListState.EMPTY) Color.White else Color.Black
            )

            CustomButton(
                onClick = { listState = ListState.ERROR },
                text = "Error",
                backgroundColor = if (listState == ListState.ERROR) Color(0xFF6366F1) else Color(
                    0xFFE5E7EB
                ),
                contentColor = if (listState == ListState.ERROR) Color.White else Color.Black
            )
        }

        when (selectedTabIndex) {
            0 -> EnhancedListsTab(
                items = items,
                listState = listState,
                isRefreshing = isRefreshing,
                onRefresh = {


                    isRefreshing = true
                    // Simulate network delay
                    scope.launch {
                        delay(1500)
                        // Shuffle the items to simulate refresh
                        val shuffled = items.shuffled()
                        items.clear()
                        items.addAll(shuffled)
                        isRefreshing = false
                    }
                }
            )

            1 -> SwipeableItemsTab(
                items = items,
                onDelete = { id ->
                    // Remove the item from the list
                    items.removeIf { it.id == id }
                },
                onFavorite = { id ->
                    // Toggle favorite status
                    items.replaceAll { item ->
                        if (item.id == id) {
                            item.copy(isFavorite = !item.isFavorite)
                        } else {
                            item
                        }
                    }
                }
            )

            2 -> MultiActionTab(
                items = items,
                onDelete = { id ->
                    // Remove the item from the list
                    items.removeIf { it.id == id }
                },
                onEdit = { id ->
                    // Edit action (just a placeholder)
                    val item = items.find { it.id == id }
                    if (item != null) {
                        val index = items.indexOf(item)
                        items[index] = item.copy(title = "${item.title} (Edited)")
                    }
                },
                onFavorite = { id ->
                    // Toggle favorite status
                    items.replaceAll { item ->
                        if (item.id == id) {
                            item.copy(isFavorite = !item.isFavorite)
                        } else {
                            item
                        }
                    }
                },
                onShare = { /* Placeholder for share action */ }
            )
        }
    }
}

/**
 * Tab demonstrating Enhanced Lists
 */
@Composable
fun EnhancedListsTab(
    items: List<ListDemoItem>,
    listState: ListState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    var viewMode by remember { mutableIntStateOf(0) } // 0 = List, 1 = Grid

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // View mode toggle
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
            CustomButton(
                onClick = { viewMode = 0 },
                text = "List View",
                backgroundColor = if (viewMode == 0) Color(0xFF6366F1) else Color(0xFFE5E7EB),
                contentColor = if (viewMode == 0) Color.White else Color.Black,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            CustomButton(
                onClick = { viewMode = 1 },
                text = "Grid View",
                backgroundColor = if (viewMode == 1) Color(0xFF6366F1) else Color(0xFFE5E7EB),
                contentColor = if (viewMode == 1) Color.White else Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        if (viewMode == 0) {
            // List view
            EnhancedLazyColumn(
                listState = listState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                pullRefreshConfig = PullRefreshConfig(
                    enabled = true,
                    indicatorColor = Color(0xFF6366F1)
                ),
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    ItemCard(item)
                }
            }
        } else {
            // Grid view
            EnhancedLazyGrid(
                listState = listState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items.size) { index ->
                    GridItemCard(items[index])
                }
            }
        }
    }
}

/**
 * Tab demonstrating Swipeable List Items
 */
@Composable
fun SwipeableItemsTab(
    items: List<ListDemoItem>,
    onDelete: (String) -> Unit,
    onFavorite: (String) -> Unit
) {
    EnhancedLazyColumn(
        listState = ListState.CONTENT,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            SwipeableFavoriteDeleteItem(
                content = { ItemCard(item) },
                onFavorite = { onFavorite(item.id) },
                onDelete = { onDelete(item.id) },
                isFavorite = item.isFavorite
            )
        }
    }
}

/**
 * Tab demonstrating Multi-Action Swipeable Items
 */
@Composable
fun MultiActionTab(
    items: List<ListDemoItem>,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
    onFavorite: (String) -> Unit,
    onShare: (String) -> Unit
) {
    EnhancedLazyColumn(
        listState = ListState.CONTENT,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            SwipeableMultiActionItem(
                content = { ItemCard(item) },
                leftActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Star,
                        backgroundColor = if (item.isFavorite) Color(0xFFF59E0B) else Color(
                            0xFF6B7280
                        ),
                        onAction = { onFavorite(item.id) }
                    )
                ),
                rightActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Edit,
                        backgroundColor = Color(0xFF3B82F6),
                        onAction = { onEdit(item.id) },
                        weight = 1f
                    ),
                    SwipeAction(
                        icon = Icons.Default.Share,
                        backgroundColor = Color(0xFF10B981),
                        onAction = { onShare(item.id) },
                        weight = 1f
                    ),
                    SwipeAction(
                        icon = Icons.Default.Delete,
                        backgroundColor = Color(0xFFEF4444),
                        onAction = { onDelete(item.id) },
                        weight = 1f
                    )
                )
            )
        }
    }
}

/**
 * Card item for list views
 */
@Composable
fun ItemCard(item: ListDemoItem) {
    CustomCard(
        backgroundColor = Color.White,
        contentColor = Color(0xFF1F2937),
        boxShadow = BoxShadow(
            offsetY = 2.dp,
            blurRadius = 4.dp,
            color = Color(0x20000000)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (item.isFavorite) Color(0xFFF59E0B) else Color(0xFF6366F1))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column {
                androidx.compose.material3.Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                )

                androidx.compose.material3.Text(
                    text = item.subtitle,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Favorite indicator
            if (item.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite",
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * Card item for grid views
 */
@Composable
fun GridItemCard(item: ListDemoItem) {
    CustomCard(
        backgroundColor = Color.White,
        contentColor = Color(0xFF1F2937),
        boxShadow = BoxShadow(
            offsetY = 2.dp,
            blurRadius = 4.dp,
            color = Color(0x20000000)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (item.isFavorite) Color(0xFFF59E0B) else Color(0xFF6366F1))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            androidx.compose.material3.Text(
                text = item.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            )

            // Subtitle
            androidx.compose.material3.Text(
                text = item.subtitle,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF6B7280),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                ),
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListComponentsDemoPreview() {
    ListComponentsDemo()
}