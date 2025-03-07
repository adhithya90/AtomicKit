package com.example.atomickit.screens.meetingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.atomickit.components.CustomTopAppBar
import com.example.atomickit.ui.theme.AppTypography
import com.example.atomickit.ui.theme.LocalAppTypography
import kotlinx.coroutines.launch

/**
 * A screen that demonstrates the meeting cards UI component
 */
@Composable
fun MeetingCardsDemo() {
    val typography = AppTypography.create()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Sample meeting data
    val meetings = listOf(
        Meeting(
            organization = "Android Developer Experience",
            title = "Onboarding Batch 1",
            startTime = "09:00",
            endTime = "11:00 AM",
            participants = listOf(
                Participant("John Doe", "https://randomuser.me/api/portraits/men/1.jpg"),
                Participant("Jane Smith", "https://randomuser.me/api/portraits/women/1.jpg")
            ),
            backgroundColor = Color(0xFFF8A173),
            textColor = Color.White
        ),
        Meeting(
            organization = "DevEx Working Group",
            title = "Kick-off Meeting",
            startTime = "09:30",
            endTime = "10:30 AM",
            participants = listOf(
                Participant("Alice Johnson", "https://randomuser.me/api/portraits/women/2.jpg"),
                Participant("Bob Anderson", "https://randomuser.me/api/portraits/men/2.jpg"),
                Participant("Carol Williams", "https://randomuser.me/api/portraits/women/3.jpg"),
                Participant("Dave Miller", "https://randomuser.me/api/portraits/men/3.jpg")
            ),
            backgroundColor = Color.White,
            textColor = Color(0xFF1F2937)
        ),
        Meeting(
            organization = "ADUX Team",
            title = "Weekly Sync",
            startTime = "13:00",
            endTime = "14:00 PM",
            participants = listOf(
                Participant("Emily Davis", "https://randomuser.me/api/portraits/women/4.jpg"),
                Participant("Frank Wilson", "https://randomuser.me/api/portraits/men/4.jpg"),
                Participant("Grace Lee", "https://randomuser.me/api/portraits/women/5.jpg")
            ),
            backgroundColor = Color(0xFF90CAF9),
            textColor = Color(0xFF0D47A1)
        ),
        Meeting(
            organization = "Android Form Factor Team",
            title = "Campaign Review",
            startTime = "15:30",
            endTime = "16:30 PM",
            participants = listOf(
                Participant("Henry Brown", "https://randomuser.me/api/portraits/men/5.jpg"),
                Participant("Irene Chen", "https://randomuser.me/api/portraits/women/6.jpg"),
                Participant("James Taylor", "https://randomuser.me/api/portraits/men/6.jpg"),
                Participant("Karen Moore", "https://randomuser.me/api/portraits/women/7.jpg"),
                Participant("Leo Adams", "https://randomuser.me/api/portraits/men/7.jpg")
            ),
            backgroundColor = Color(0xFFA5D6A7),
            textColor = Color(0xFF1B5E20)
        )
    )

    CompositionLocalProvider(LocalAppTypography provides typography) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    titleText = "Meeting Cards",
                    backgroundColor = Color(0xFF6366F1),
                    contentColor = Color.White
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF8F9FA))
                    .padding(
                        top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                        bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    meetings.forEach { meeting ->
                        MeetingCard(
                            meeting = meeting,
                            modifier = Modifier.fillMaxWidth(),
                            onCardClick = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Selected: ${meeting.title}")
                                }
                            },
                            onLinkClick = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Joining: ${meeting.meetingLink}")
                                }
                            },
                            onMoreClick = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("More options for: ${meeting.title}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun MeetingCardsDemoPreview() {
    MeetingCardsDemo()
}