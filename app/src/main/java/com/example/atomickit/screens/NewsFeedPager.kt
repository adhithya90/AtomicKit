package com.example.atomickit.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox


import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext



import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.components.Text
import com.example.atomickit.ui.theme.AppTypography
import com.example.atomickit.ui.theme.LocalAppTypography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class representing a news item
 */
data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val source: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * A news feed screen that displays news items one at a time in a pager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsFeedPager(
    modifier: Modifier = Modifier
) {
    val typography = AppTypography.create(
        headingColor = Color(0xFF212529),
        bodyColor = Color(0xFF495057)
    )

    // Sample news data with actual image URLs
    val newsItems = remember {
        listOf(
            NewsItem(
                id = "1",
                title = "Apple Announces New Developer Tools",
                summary = "Apple has unveiled a suite of new developer tools at their annual conference, designed to simplify app creation and improve performance across their ecosystem.",
                imageUrl = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?q=80&w=1000",
                source = "techcrunch"
            ),
            NewsItem(
                id = "2",
                title = "Tech Giants Announce New AI Research Coalition",
                summary = "Major technology companies have formed a new coalition focused on advancing responsible AI research, with plans to share certain non-proprietary datasets and research findings.",
                imageUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
                source = "wired"
            ),
            NewsItem(
                id = "3",
                title = "Breakthrough in Quantum Computing Announced",
                summary = "Scientists have achieved a major milestone in quantum computing, creating a stable qubit that could revolutionize the field and lead to practical quantum computers sooner than expected.",
                imageUrl = "https://images.unsplash.com/photo-1635070041078-e363dbe005cb?q=80&w=1000",
                source = "sciencetoday"
            ),
            NewsItem(
                id = "4",
                title = "New Electric Vehicle Battery Extends Range by 30%",
                summary = "Researchers have developed a new battery technology for electric vehicles that promises to increase driving range by up to 30% while reducing charging time by half.",
                imageUrl = "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2065&q=80",
                source = "electrek"
            )
        )
    }

    // Dark theme colors
    val darkBackground = Color(0xFF121212)
    val darkSurface = Color(0xFF1E1E1E)
    val primary = Color(0xFF6366F1)
    val textColor = Color.White
    val subtextColor = Color.White.copy(alpha = 0.7f)

    // Pager state - using vertical pager
    val pagerState = rememberPagerState(pageCount = { newsItems.size })
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(LocalAppTypography provides typography) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(darkBackground)
                .padding(
                    top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                )

        ) {
            // App bar with "Discover" title
            CustomTopAppBar(
                title = {
                    Text(
                        text = "Discover",
                        style = LocalAppTypography.current.h1
                    )
                },
                backgroundColor = darkBackground,
                contentColor = textColor,
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            )

            // News pager - changed to vertical
            androidx.compose.foundation.pager.VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { page ->
                val newsItem = newsItems[page]
                NewsCard(
                    newsItem = newsItem,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Bottom navigation
            BottomNavigation(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


/**
 * News item card with image, title, and summary
 */
@Composable
fun NewsCard(
    newsItem: NewsItem,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier,
        backgroundColor = Color(0xFF1D2951),
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        boxShadow = BoxShadow(
            offsetY = 4.dp,
            blurRadius = 8.dp,
            color = Color(0x40000000)
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Column {
            // Image placeholder taking exactly 50% of the card's height
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsItem.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = newsItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f), // Takes exactly 50% of the available height
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = newsItem.title,
                    style = LocalAppTypography.current.h2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = newsItem.summary,
                    style = LocalAppTypography.current.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Author/source with timestamp
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Author avatar (circular placeholder)
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Author name
                    Text(
                        text = newsItem.source,
                        style = LocalAppTypography.current.bodySmall,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /* Bookmark action */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Bookmark",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { /* Audio action */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Listen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * Bottom navigation bar
 */
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Discover",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Home",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = "Saved",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * Format timestamp to a readable date string
 */
private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun NewsFeedPagerPreview() {
    NewsFeedPager()
}