import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mustafa.project001.utils.getDictionarySearchFlow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import com.mustafa.project001.data.EmojiMapTo
import com.mustafa.project001.data.getDictionaryData
//import com.mustafa.project001.data.EmojiMap
import kotlinx.coroutines.launch
import java.net.URLDecoder
import kotlin.math.max

@Composable
fun SimilarWordCard(
    word: String,
    navController: NavHostController
) {
    val decodedWord = remember(word) {
        try {
            URLDecoder.decode(word, "UTF-8")
        } catch (e: Exception) {
            word
        }
    }
// CORRECT - use different variable names
    val emojiMap = EmojiMapTo()  // This is the Map
    val emojiRes: String? = emojiMap[decodedWord]  // This is the String result

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8.toString())
                navController.navigate("singleWord/$encodedWord")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = word,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2D2D2D)
                )
            )

            if (emojiRes != null) {
                    Text(
                        text = emojiRes,
                        fontSize = 18.sp,
                    )
            }


        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black.copy(alpha = 0.3f)
        )
    }
}

@Composable
fun CustomScrollbarListContainer(
    state: LazyListState,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var containerHeight by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()

    // Dynamically safely calculate scroll calculations
    val layoutInfo = state.layoutInfo
    val totalItemsCount = layoutInfo.totalItemsCount
    val visibleItems = layoutInfo.visibleItemsInfo

    var thumbTop = 0f
    var finalThumbHeight = 0f
    var remainingSpace = 0f
    var maxScrollIndex = 1f
    var itemHeight = 0f
    val hasItems = totalItemsCount > 0 && visibleItems.isNotEmpty()

    if (hasItems) {
        val firstVisibleItem = visibleItems.first()
        itemHeight = max(1f, firstVisibleItem.size.toFloat())
        val totalSize = totalItemsCount.toFloat()
        val estimatedTotalHeight = itemHeight * totalSize

        val scrollOffset = state.firstVisibleItemScrollOffset.toFloat()
        val firstItemProgress = scrollOffset / itemHeight
        val currentScrollPosition = state.firstVisibleItemIndex.toFloat() + firstItemProgress

        val thumbHeight = (containerHeight / estimatedTotalHeight) * containerHeight
        finalThumbHeight = thumbHeight.coerceAtLeast(50f)

        remainingSpace = max(1f, containerHeight - finalThumbHeight)
        maxScrollIndex = max(1f, totalSize - (containerHeight / itemHeight))
        thumbTop = ((currentScrollPosition / maxScrollIndex) * remainingSpace).coerceIn(0f, remainingSpace)
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                containerHeight = max(1f, coordinates.size.height.toFloat())
            }
            .drawWithContent {
                drawContent()
                if (hasItems) {
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(this.size.width - 8f, thumbTop),
                        size = Size(8f, finalThumbHeight),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                }
            }
            // Pointer key is isolated to the actual text state total item structure safely
            .pointerInput(totalItemsCount, remainingSpace, maxScrollIndex, itemHeight) {
                if (!hasItems || remainingSpace <= 0f) return@pointerInput
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    // Live gesture mapping calculations protected against out of bounds index crashes
                    val newThumbTop = (thumbTop + dragAmount.y).coerceIn(0f, remainingSpace)
                    val targetScrollIndexFloat = (newThumbTop / remainingSpace) * maxScrollIndex
                    val targetIndex = targetScrollIndexFloat.toInt().coerceIn(0, totalItemsCount - 1)
                    val targetOffset = ((targetScrollIndexFloat - targetIndex) * itemHeight).toInt()

                    coroutineScope.launch {
                        state.scrollToItem(index = targetIndex, scrollOffset = targetOffset)
                    }
                }
            }
    ) {
        content()
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun DictionarySearchRow(
    text: TextFieldValue,
    navController: NavHostController,
    isSorted: Boolean
) {
    val dictionary = remember { getDictionaryData() }
    val listState = rememberLazyListState()

    val searchResults by produceState<List<String>>(
        initialValue = emptyList(),
        text.text,
        isSorted
    ) {
        getDictionarySearchFlow(text.text, dictionary, isSorted).collect { value = it }
    }
    LaunchedEffect(text.text) {
        if (searchResults.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Header Section
        if (text.text.isNotBlank()) {
            Text(
                text = "🔍 Found ${searchResults.size} similar word(s)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        } else {
            Text(
                text = "📖 All Dictionary Words (${searchResults.size})",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (searchResults.isNotEmpty()) {
            CustomScrollbarListContainer(
                state = listState,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp) // Clearance gap preventing text overlap
                ) {
                    items(searchResults) { word ->
                        SimilarWordCard(
                            word = word,
                            navController = navController
                        )
                    }
                }
            }
        } else if (text.text.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    text = "❌ No similar words found for '${text.text}'",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
            }
        }
    }
}
