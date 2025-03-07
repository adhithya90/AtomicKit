package com.example.atomickit.screens.meetingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.atomickit.components.BoxShadow
import com.example.atomickit.components.CustomCard
import com.example.atomickit.components.Text
import com.example.atomickit.ui.theme.AppFonts
import com.example.atomickit.ui.theme.AppTypography
import com.example.atomickit.ui.theme.LocalAppTypography

/**
 * Data class representing a meeting participant
 */
data class Participant(
    val name: String,
    val imageUrl: String
)

/**
 * Data class representing a meeting
 */
data class Meeting(
    val organization: String,
    val title: String,
    val startTime: String,
    val endTime: String,
    val participants: List<Participant>,
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color(0xFF1F2937),
    val meetingLink: String = "meeting"
)

/**
 * A meeting card component that displays meeting information with participants
 */
@Composable
fun MeetingCard(
    meeting: Meeting,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
    onLinkClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    val typography = AppTypography.create()

    CompositionLocalProvider(LocalAppTypography provides typography) {
        CustomCard(
            modifier = modifier,
            backgroundColor = meeting.backgroundColor,
            contentColor = meeting.textColor,
            shape = RoundedCornerShape(24.dp),
            boxShadow = BoxShadow(
                offsetY = 2.dp,
                blurRadius = 6.dp,
                color = Color(0x43A2A2A2)
            ),
            onClick = onCardClick
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Organization name
                Text(
                    text = meeting.organization,
                    style = TextStyle(
                        fontFamily = AppFonts.BricolageGrotesque,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = meeting.textColor
                    )
                )

                // Meeting title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${meeting.title}",
                        style = TextStyle(
                            fontFamily = AppFonts.BricolageGrotesque,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = meeting.textColor
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = onMoreClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = meeting.textColor
                        )
                    }
                }


                // Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccessTimeFilled,
                            contentDescription = "Time",
                            tint = meeting.textColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${meeting.startTime} - ${meeting.endTime}",
                        style = TextStyle(
                            fontFamily = AppFonts.OpenSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = meeting.textColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Participants
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ParticipantsStack(
                        participants = meeting.participants,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${meeting.participants.size} participants",
                        style = TextStyle(
                            fontFamily = AppFonts.OpenSans,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = meeting.textColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Meeting link
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .clickable(onClick = onLinkClick)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = "Video",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Join ${meeting.meetingLink}",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = AppFonts.OpenSans,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * A component that displays a stack of participant avatars
 */
@Composable
fun ParticipantsStack(
    participants: List<Participant>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row {
            participants.take(3).forEachIndexed { index, participant ->
                Box(
                    modifier = Modifier
                        .offset(x = if (index > 0) (-12).dp else 0.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    AsyncImage(
                        model = participant.imageUrl,
                        contentDescription = participant.name,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Show a +X indicator if there are more participants
            if (participants.size > 3) {
                Box(
                    modifier = Modifier
                        .offset(x = (-12).dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF6366F1)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${participants.size - 3}",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = AppFonts.OpenSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp

                        )
                    )
                }
            }
        }
    }
}

/**
 * A preview parameter provider for meeting cards
 */
class MeetingPreviewParameterProvider : PreviewParameterProvider<Meeting> {
    override val values: Sequence<Meeting> = sequenceOf(
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
            organization = "Android Form Factor Team",
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
        )
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun MeetingCardPreview(@PreviewParameter(MeetingPreviewParameterProvider::class) meeting: Meeting) {
    MeetingCard(meeting = meeting)
}

