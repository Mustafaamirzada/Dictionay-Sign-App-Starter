package com.mustafa.project001.ui.screens

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.mustafa.project001.data.VideoMap
import com.mustafa.project001.data.EmojiMapTo
import com.mustafa.project001.data.getRelatedWords
import java.net.URLDecoder

import com.mustafa.project001.data.familyMembers
import com.mustafa.project001.data.religion
import com.mustafa.project001.data.social
import com.mustafa.project001.data.bodyOrgans
import com.mustafa.project001.data.cloth
import com.mustafa.project001.data.houseHold
import com.mustafa.project001.data.fruits
import com.mustafa.project001.data.eating
import com.mustafa.project001.data.school
import com.mustafa.project001.data.animals
import com.mustafa.project001.data.nature
import com.mustafa.project001.data.peopleTasks
import com.mustafa.project001.data.tools
import com.mustafa.project001.data.athletics
import com.mustafa.project001.data.transportation
import com.mustafa.project001.data.cities
import com.mustafa.project001.data.places
import com.mustafa.project001.data.directions
import com.mustafa.project001.data.timesPlace
import com.mustafa.project001.data.adjectives
import com.mustafa.project001.data.pronouns
import com.mustafa.project001.data.emotions
import com.mustafa.project001.data.verbs
import com.mustafa.project001.data.questions
import com.mustafa.project001.data.medicine
import com.mustafa.project001.data.politics
import com.mustafa.project001.data.colorsName
import com.mustafa.project001.data.numbers
import com.mustafa.project001.data.drugs
import com.mustafa.project001.data.miscellaneous
import com.mustafa.project001.data.alphabets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleWordSign(
    navController: NavHostController,
    word: String
) {
    val context = LocalContext.current

    val decodedWord = remember(word) {
        try {
            URLDecoder.decode(word, "UTF-8")
        } catch (e: Exception) {
            word
        }
    }

    val mainGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF8F9FA), Color(0xFFE9ECEF))
    )

    val videoMap = VideoMap()
    val videoUri = videoMap[decodedWord]

    val emojiMap = EmojiMapTo()
    val emojiRes: String? = emojiMap[decodedWord]

    var isLooping by remember { mutableStateOf(true) }

    val familyMembersList = remember { familyMembers() }
    val religionList = remember { religion() }
    val socialList = remember { social() }
    val bodyOrgansList = remember { bodyOrgans() }
    val clothList = remember { cloth() }
    val houseHoldList = remember { houseHold() }
    val fruitsList = remember { fruits() }
    val eatingList = remember { eating() }
    val schoolList = remember { school() }
    val animalsList = remember { animals() }
    val natureList = remember { nature() }
    val peopleTasksList = remember { peopleTasks() }
    val toolsList = remember { tools() }
    val athleticsList = remember { athletics() }
    val transportationList = remember { transportation() }
    val citiesList = remember { cities() }
    val placesList = remember { places() }
    val directionsList = remember { directions() }
    val timesPlaceList = remember { timesPlace() }
    val adjectivesList = remember { adjectives() }
    val pronounsList = remember { pronouns() }
    val emotionsList = remember { emotions() }
    val verbsList = remember { verbs() }
    val questionsList = remember { questions() }
    val medicineList = remember { medicine() }
    val politicsList = remember { politics() }
    val colorsNameList = remember { colorsName() }
    val numbersList = remember { numbers() }
    val drugsList = remember { drugs() }
    val miscellaneousList = remember { miscellaneous() }
    val alphabetsList = remember { alphabets() }

    val relatedWords = remember(decodedWord) {
        if (religionList.contains(decodedWord)) {
            getRelatedWords(decodedWord, religionList)
        } else if(familyMembersList.contains(decodedWord)){
            getRelatedWords(decodedWord,familyMembersList)
        } else if(religionList.contains(decodedWord)){
            getRelatedWords(decodedWord,religionList)
        } else if(socialList.contains(decodedWord)){
            getRelatedWords(decodedWord,socialList)
        } else if(bodyOrgansList.contains(decodedWord)){
            getRelatedWords(decodedWord,bodyOrgansList)
        } else if(clothList.contains(decodedWord)){
            getRelatedWords(decodedWord,clothList)
        } else if(houseHoldList.contains(decodedWord)){
            getRelatedWords(decodedWord,houseHoldList)
        } else if(fruitsList.contains(decodedWord)){
            getRelatedWords(decodedWord,fruitsList)
        } else if(eatingList.contains(decodedWord)){
            getRelatedWords(decodedWord,eatingList)
        } else if(schoolList.contains(decodedWord)){
            getRelatedWords(decodedWord,schoolList)
        } else if(animalsList.contains(decodedWord)){
            getRelatedWords(decodedWord,animalsList)
        } else if(natureList.contains(decodedWord)){
            getRelatedWords(decodedWord,natureList)
        } else if(peopleTasksList.contains(decodedWord)){
            getRelatedWords(decodedWord,peopleTasksList)
        } else if(toolsList.contains(decodedWord)){
            getRelatedWords(decodedWord,toolsList)
        } else if(athleticsList.contains(decodedWord)){
            getRelatedWords(decodedWord,athleticsList)
        } else if(transportationList.contains(decodedWord)){
            getRelatedWords(decodedWord,transportationList)
        } else if(citiesList.contains(decodedWord)){
            getRelatedWords(decodedWord,citiesList)
        } else if(placesList.contains(decodedWord)){
            getRelatedWords(decodedWord,placesList)
        } else if(directionsList.contains(decodedWord)){
            getRelatedWords(decodedWord,directionsList)
        } else if(timesPlaceList.contains(decodedWord)){
            getRelatedWords(decodedWord,timesPlaceList)
        } else if(adjectivesList.contains(decodedWord)){
            getRelatedWords(decodedWord,adjectivesList)
        } else if(pronounsList.contains(decodedWord)){
            getRelatedWords(decodedWord,pronounsList)
        } else if(emotionsList.contains(decodedWord)){
            getRelatedWords(decodedWord,emotionsList)
        } else if(verbsList.contains(decodedWord)){
            getRelatedWords(decodedWord,verbsList)
        } else if(questionsList.contains(decodedWord)){
            getRelatedWords(decodedWord,questionsList)
        } else if(medicineList.contains(decodedWord)){
            getRelatedWords(decodedWord,medicineList)
        } else if(politicsList.contains(decodedWord)){
            getRelatedWords(decodedWord,politicsList)
        } else if(colorsNameList.contains(decodedWord)){
            getRelatedWords(decodedWord,colorsNameList)
        } else if(numbersList.contains(decodedWord)){
            getRelatedWords(decodedWord,numbersList)
        } else if(drugsList.contains(decodedWord)){
            getRelatedWords(decodedWord,drugsList)
        } else if(miscellaneousList.contains(decodedWord)){
            getRelatedWords(decodedWord,miscellaneousList)
        } else if(alphabetsList.contains(decodedWord)){
            getRelatedWords(decodedWord,alphabetsList)
        } else {
            emptyList()
        }
    }

    // Main Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Sign Details",
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 16.dp)
                    .clickable { navController.navigate("search") },
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF5F5F5),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = decodedWord,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            }
        }

        // --- MAIN CONTENT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- WORD HEADER SECTION ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(70.dp),
                        shape = CircleShape,
                        color = Color(0xFFF1F3F5)
                    ) {
                        if (emojiRes != null) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = emojiRes,
                                    fontSize = 48.sp,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = decodedWord.firstOrNull()?.toString() ?: "?",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6C757D)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        Text(
                            text = "Word",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = decodedWord,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF212529)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- VIDEO SECTION ---
            Text(
                text = "📹 Visual Sign Representation",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 12.dp, start = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (videoUri != null) {
                        AndroidView(
                            factory = { ctx ->
                                VideoView(ctx).apply {
                                    setVideoPath(videoUri)
                                    val mediaController = MediaController(ctx)
                                    mediaController.setAnchorView(this)
                                    setMediaController(mediaController)

                                    setOnPreparedListener { mp ->
                                        mp.isLooping = isLooping
                                        start()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(28.dp))
                        )

                        // Loop toggle button
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                                .clickable { isLooping = !isLooping },
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = if (isLooping) "🔁" else "⏹️",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "🎬",
                                    fontSize = 48.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No sign video available for '$decodedWord'",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- RELATED WORDS SECTION ---
            if (relatedWords.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Related Words",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 4.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(relatedWords) { relatedWord ->
                        val emoji = EmojiMapTo()[relatedWord]

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val encodedWord = java.net.URLEncoder.encode(relatedWord, "UTF-8")
                                    navController.navigate("singleWord/$encodedWord")
                                },
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF1F3F5)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = emoji ?: "❓",
                                        fontSize = 20.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = relatedWord,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- ADDITIONAL INFO SECTION ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Did you know?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF2C3E50)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sign language is a complete, natural language that has the same linguistic properties as spoken languages.",
                        fontSize = 14.sp,
                        color = Color(0xFF6C757D),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

