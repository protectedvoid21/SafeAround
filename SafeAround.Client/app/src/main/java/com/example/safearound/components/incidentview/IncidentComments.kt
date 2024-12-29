package com.example.safearound.components.incidentview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safearound.helpers.formatForDisplay
import com.example.safearound.helpers.getCommentsText
import com.example.safearound.models.Comment
import kotlinx.datetime.LocalDateTime

@Composable
fun IncidentComments(comments: List<Comment>?) {
    Text(
        getCommentsText(comments?.size ?: 0),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    if (!comments.isNullOrEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            items(comments) { Comment(it) }
        }
    }
}

@Composable
fun Comment(comment: Comment) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = comment.userName,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Clip
            )
            Text(comment.createdOn.formatForDisplay(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(comment.content, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
@Preview(showBackground = true)
fun IncidentCommentsPreview() {
    val exampleComments = listOf(
        Comment(1, "aa", "jberski23", "Wszystko już naprawione", LocalDateTime(2021, 10, 14, 14, 11)),
        Comment(2, "aa", "ilya", "na szczescie nikt nie ucierpial", LocalDateTime(2021, 10, 13, 17, 43)),
        Comment(3, "aa", "gawronsky", "od 50 minut korek, policja informuje o zmianie kierunku ruchu", LocalDateTime(2021, 10, 10, 12, 0)),
        Comment(4, "aa", "Kamil Nowak", "Auto przewrocone, wezwano karetke", LocalDateTime(2021, 10, 13, 9, 9)),
    )
    IncidentComments(comments = exampleComments)
}