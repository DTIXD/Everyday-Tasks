package com.example.everydaytasks

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.center
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.everydaytasks.ui.theme.BGColor
import androidx.compose.foundation.verticalScroll
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.bottommenu.BottomMenu
import com.example.everydaytasks.ui.theme.date.DatePage
import com.example.everydaytasks.ui.theme.date.DateScreenDataObject
import com.example.everydaytasks.ui.theme.login.LoginPage
import com.example.everydaytasks.ui.theme.login.LoginScreenObject
import com.example.everydaytasks.ui.theme.progress.ProgressPage
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.example.everydaytasks.ui.theme.review.ReviewPage
import com.example.everydaytasks.ui.theme.review.ReviewScreenDataObject
import com.example.everydaytasks.ui.theme.search.SearchPage
import com.example.everydaytasks.ui.theme.search.SearchScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.everydaytasks.ui.theme.BorderColor
import com.example.everydaytasks.ui.theme.ButtonBGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
import com.example.everydaytasks.ui.theme.GreyButtonBGColor
import com.example.everydaytasks.ui.theme.TodayColor
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.everydaytasks.ui.theme.IntervalColor
import com.example.everydaytasks.ui.theme.SelectedItemColor
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.YearMonth
import androidx.compose.ui.graphics.ColorFilter
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.app.NotificationManagerCompat
import com.example.everydaytasks.ui.theme.AddingToneColor
import com.example.everydaytasks.ui.theme.TagsBGColor
import com.example.everydaytasks.ui.theme.TagsColor
import com.example.everydaytasks.ui.theme.TomorrowColor
import com.example.everydaytasks.ui.theme.WarningBorderColor
import com.example.everydaytasks.ui.theme.WarningColor
import com.example.everydaytasks.ui.theme.WeekColor
import com.example.everydaytasks.ui.theme.taskfunc.TaskFunctionsObject
import com.example.everydaytasks.ui.theme.taskfunc.TaskFunctionsPage
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.text.replaceFirstChar
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        WindowCompat.setDecorFitsSystemWindows(window, true)

        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            val isBottomMenuVisible = remember {
                mutableStateOf(false)
            }

            val sheet1State = remember {
                mutableStateOf(false)
            }
            val sheet2State = remember {
                mutableStateOf(false)
            }
            val sheet3State = remember {
                mutableStateOf(false)
            }
            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Scaffold(
                    floatingActionButton = {
                        if (isBottomMenuVisible.value) {
                            FloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        sheet1State.value = true
                                    }
                                },
                                containerColor = ButtonBGColor,
                                modifier = Modifier
                                    .offset(y = (-8).dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    containerColor = BGColor,
                    bottomBar = {
                        if (isBottomMenuVisible.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(BottomMenuColor)
                            ) {
                                BottomMenu(
                                    onTodayClick = {
                                        navController.navigate(ProgressScreenDataObject())
                                    },
                                    onUpcomingClick = {
                                        navController.navigate(DateScreenDataObject())
                                    },
                                    onSearchClick = {
                                        navController.navigate(SearchScreenDataObject)
                                    },
                                    onReviewClick = {
                                        navController.navigate(ReviewScreenDataObject)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginScreenObject
                    ) {
                        composable<LoginScreenObject> {
                            LoginPage { navData ->
                                navController.navigate(navData)
                                isBottomMenuVisible.value = true
                            }
                        }
                        composable<ProgressScreenDataObject> {
                            ProgressPage(
                                modifier = Modifier
                                    .padding(
                                        innerPadding
                                    )
                            )
                        }
                        composable<DateScreenDataObject> {
                            DatePage(
                                modifier = Modifier
                                    .padding(
                                    innerPadding
                                    )
                            )
                        }
                        composable<SearchScreenDataObject> {
                            SearchPage(
                                modifier = Modifier
                                    .padding(
                                        innerPadding
                                    )
                            )
                        }
                        composable<ReviewScreenDataObject> {
                            ReviewPage(
                                modifier = Modifier
                                    .padding(
                                        innerPadding
                                    )
                            )
                        }
                        composable<TaskFunctionsObject> {
                            TaskFunctionsPage(
                                modifier = Modifier
                                    .padding(
                                        innerPadding
                                    ),
                                onNavigationToProgressPage = { navData ->
                                    navController.navigate(navData)
                                    isBottomMenuVisible.value = true
                                }
                            )
                        }
                    }
                    var newTask by remember { mutableStateOf("") }

                    val fs = Firebase.firestore
                    val list = remember {
                        mutableStateOf(
                            emptyList<ProgressScreenDataObject>()
                        )
                    }

                    val actionsRef = fs.collection("actions")
                        .document("list")
                    val listA = remember {
                        mutableStateListOf(
                            "Actions Available",
                        )
                    }
                    actionsRef.addSnapshotListener { snapshot, e ->
                        if (snapshot != null && snapshot.exists()) {
                            val list = (snapshot.get("items") as? List<*>)?.filterIsInstance<String>()
                                ?: emptyList()
                            listA.clear()
                            listA.addAll(list)
                        }
                    }

                    val key = fs.collection("tasks")
                        .document().id
                    val today = remember {
                        LocalDate.now()
                    }
                    fs.collection("tasks").addSnapshotListener { snapshot, exception ->
                        list.value =
                            snapshot?.toObjects(ProgressScreenDataObject::class.java) ?: emptyList()
                    }

                    val addForToday = remember {
                        mutableStateOf(true)
                    }

                    val dateSelection = remember {
                        mutableIntStateOf(1)
                    }
                    val dateSelectionText = remember {
                        mutableStateOf("Today")
                    }

                    val showDialog3 = remember {
                        mutableStateOf(false)
                    }
                    val showDialog4 = remember {
                        mutableStateOf(false)
                    }
                    val showDialog5 = remember {
                        mutableStateOf(false)
                    }
                    val showDialog6 = remember {
                        mutableStateOf(false)
                    }
                    val showDialog7 = remember {
                        mutableStateOf(false)
                    }

                    val selectedPriority = remember {
                        mutableIntStateOf(0)
                    }
                    val selectedReminder = remember {
                        mutableIntStateOf(0)
                    }
                    val currentDate = LocalDate.now()
                    var selectedDate by remember { mutableStateOf(currentDate) }
                    val taskTime = remember {
                        mutableStateOf(LocalTime.now())
                    }
                    val taskTag = remember {
                        mutableStateOf("")
                    }
                    var annotatedParts by remember { mutableStateOf(setOf<String>()) }
                    var tagName by remember { mutableStateOf("") }
                    val regex = Regex("@\\w+")

                    if (sheet1State.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheet1State.value = false
                                    newTask = ""
                                }
                            },
                            sheetState = rememberModalBottomSheetState(
                                skipPartiallyExpanded = true
                            ),
                            containerColor = BGColor,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    ),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState())
                                            .padding(8.dp)
                                    ) {
                                        if (newTask.isEmpty()) {
                                            Text("NewTask", color = Color.Gray, fontSize = 18.sp)
                                        }

                                        val annotatedString = buildAnnotatedString {
                                            var lastIndex = 0
                                            for (match in regex.findAll(newTask)) {
                                                append(newTask.substring(lastIndex, match.range.first))
                                                val word = match.value
                                                val isAnnotated = annotatedParts.contains(word)
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = Color.White,
                                                        background = if (isAnnotated) Color.Transparent else Color.Transparent
                                                    )
                                                ) {
                                                    append(word)
                                                }
                                                lastIndex = match.range.last + 1
                                            }
                                            if (lastIndex < newTask.length) append(newTask.substring(lastIndex))
                                        }

                                        Text(
                                            text = annotatedString,
                                            fontSize = 18.sp,
                                            color = Color.White,
                                            lineHeight = 26.sp,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .drawBehind {
                                                    val layout = textLayoutResult ?: return@drawBehind
                                                    annotatedParts.forEach { word ->
                                                        val startIndex = newTask.indexOf(word)
                                                        if (startIndex != -1) {
                                                            val endIndex = startIndex + word.length
                                                            val boxes = (startIndex until endIndex).map {
                                                                layout.getBoundingBox(it)
                                                            }

                                                            if (boxes.isNotEmpty()) {
                                                                boxes.forEach {
                                                                    drawRoundRect(
                                                                        color = TagsColor,
                                                                        topLeft = Offset(it.left - 5.dp.toPx(), it.top - 2.dp.toPx()),
                                                                        size = Size(
                                                                            width = (it.right - it.left) + 10.dp.toPx(),
                                                                            height = (it.bottom - it.top) + 4.dp.toPx()
                                                                        ),
                                                                        cornerRadius = CornerRadius(8.dp.toPx())
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                },
                                            onTextLayout = { textLayoutResult = it },
                                            softWrap = true,
                                            overflow = TextOverflow.Clip,
                                            maxLines = Int.MAX_VALUE
                                        )

                                        BasicTextField(
                                            value = newTask,
                                            onValueChange = { text ->
                                                newTask = text
                                                val match = regex.findAll(newTask).firstOrNull {match ->
                                                    match.range.last + 1 == text.length
                                                }

                                                if (match != null) {
                                                    showDialog5.value = true
                                                    tagName = match.value.removePrefix("@")
                                                }
                                            },
                                            textStyle = TextStyle(color = Color.Transparent, fontSize = 18.sp),
                                            cursorBrush = SolidColor(Color.White),
                                            modifier = Modifier
                                                .matchParentSize()
                                                .background(Color.Transparent)
                                        )
                                    }

                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    ) {
                                        items(listA) { label ->
                                            if (listA.indexOf(label) <= listA.indexOf("Actions Available")) {
                                                when (label) {
                                                    "Date" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        sheet1State.value = false
                                                                        sheet2State.value = true
                                                                    }
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(width = 10.dp)
                                                            )
                                                            Image(
                                                                painter = painterResource(
                                                                    id = R.drawable.ic_today
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(size = 20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    color = when (dateSelection.intValue) {
                                                                        1 -> TodayColor
                                                                        2 -> TomorrowColor
                                                                        3 -> Color.White
                                                                        else -> WeekColor
                                                                    },
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text = dateSelectionText.value,
                                                                color =
                                                                    when (dateSelection.intValue) {
                                                                        1 -> TodayColor
                                                                        2 -> TomorrowColor
                                                                        3 -> Color.White
                                                                        else -> WeekColor
                                                                    },
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Priority" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    showDialog3.value = true
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id =
                                                                        if (selectedPriority.intValue == 0)
                                                                            R.drawable.ic_priority
                                                                        else
                                                                            R.drawable.ic_priority_number
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter =
                                                                    if (selectedPriority.intValue == 0)
                                                                        null
                                                                    else ColorFilter.tint(
                                                                        when (selectedPriority.intValue) {
                                                                            1 -> Color.Red
                                                                            2 -> Color(0xFFFF9800)
                                                                            3 -> Color.Blue
                                                                            else -> Color.Gray
                                                                        }
                                                                    )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text =
                                                                    when (selectedPriority.intValue) {
                                                                        1 -> "P1"
                                                                        2 -> "P2"
                                                                        3 -> "P3"
                                                                        4 -> "P4"
                                                                        else -> "Priority"
                                                                    },
                                                                color =
                                                                    when (selectedPriority.intValue) {
                                                                        1 -> Color.Red
                                                                        2 -> Color(0xFFFF9800)
                                                                        3 -> Color.Blue
                                                                        else -> Color.White
                                                                    },
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Reminder" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        sheet1State.value = false
                                                                        sheet3State.value = true
                                                                    }
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_reminder
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    color = when (selectedReminder.intValue) {
                                                                        0 -> Color.White
                                                                        1 -> SelectedItemColor
                                                                        2 -> Color.Blue
                                                                        else -> Color.Green
                                                                    }
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text =
                                                                    when (selectedReminder.intValue) {
                                                                        0 -> "Reminder"
                                                                        1 -> "30 minutes"
                                                                        2 -> "Location"
                                                                        else -> "Time"
                                                                    },
                                                                color =
                                                                    when (selectedReminder.intValue) {
                                                                        0 -> Color.White
                                                                        1 -> SelectedItemColor
                                                                        2 -> Color.Blue
                                                                        else -> Color.Green
                                                                    },
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Executor" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        sheet1State.value = false
                                                                        sheet3State.value = true
                                                                    }
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_executor
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(Color.White)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text = "Executor",
                                                                color = Color.White,
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Tags" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    newTask += "@"
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_tags
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    color = Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text = "Tags",
                                                                color = Color.White,
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Deadline" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    showDialog7.value = true
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_deadline
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(color = Color.White)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text = "Deadline",
                                                                color = Color.White,
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Location" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        sheet1State.value = false
                                                                        sheet3State.value = true
                                                                    }
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_location
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(color = Color.White)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(8.dp)
                                                            )
                                                            Text(
                                                                text = "Location",
                                                                color = Color.White,
                                                                fontSize = 15.sp
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                    "Actions Available" ->
                                                        Row(
                                                            modifier = Modifier
                                                                .background(
                                                                    color = BGColor
                                                                )
                                                                .padding(10.dp)
                                                                .clip(
                                                                    RoundedCornerShape(10.dp)
                                                                )
                                                                .border(
                                                                    1.dp,
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    color = BorderColor
                                                                )
                                                                .clickable {
                                                                    showDialog4.value = true
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.three_dots
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(20.dp),
                                                                contentScale = ContentScale.Crop
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(10.dp)
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .height(40.dp)
                                                            )
                                                        }
                                                }
                                            }
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(17.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(50.dp)
                                                .clip(
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .background(
                                                    color = GreyButtonBGColor
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painterResource(id = R.drawable.addbutton),
                                                contentDescription = null,
                                                Modifier
                                                    .padding(5.dp)
                                                    .clickable {
                                                        fs.collection("tasks")
                                                            .document(key).set(
                                                                ProgressScreenDataObject(
                                                                    newTask = newTask,
                                                                    isCompleted = false,
                                                                    key = key,
                                                                    dayAdded =
                                                                        if (addForToday.value) selectedDate.toString()
                                                                        else today.toString(),
                                                                    daySelected = today.toString(),
                                                                    category =
                                                                        if (addForToday.value) "Today"
                                                                        else "EveryDay",
                                                                    wasAdded = addForToday.value,
                                                                    lastAdded = today.minusDays(
                                                                        1
                                                                    ).toString(),
                                                                    firstAdd = 1,
                                                                    time = taskTime.value.toString(),
                                                                    priority = selectedPriority.intValue,
                                                                    tag = taskTag.value
                                                                )
                                                            )
                                                    },
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (showDialog3.value) {
                            Popup(
                                alignment = Alignment.Center,
                                onDismissRequest = {
                                    showDialog3.value = false
                                },
                                properties = PopupProperties(
                                    focusable = true
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(
                                            top = 100.dp
                                        )
                                        .width(400.dp)
                                        .clip(
                                            RoundedCornerShape(15.dp)
                                        )
                                        .background(
                                            color = BottomMenuColor
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = BorderColor,
                                            shape = RoundedCornerShape(15.dp)
                                        )

                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                vertical = 15.dp,
                                                horizontal = 20.dp
                                            )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog3.value = false
                                                    selectedPriority.intValue = 1
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.ic_priority_number
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(Color.Red)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Priority 1",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(30.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog3.value = false
                                                    selectedPriority.intValue = 2
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.ic_priority_number
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(
                                                    Color(0xFFFF9800)
                                                )
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Priority 2",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(30.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog3.value = false
                                                    selectedPriority.intValue = 3
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.ic_priority_number
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(Color.Blue)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Priority 3",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(30.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog3.value = false
                                                    selectedPriority.intValue = 4
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.ic_priority_number
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(Color.Gray)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Priority 4",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (showDialog4.value) {
                            Popup(
                                alignment = Alignment.Center,
                                onDismissRequest = {
                                    showDialog4.value = false
                                },
                                properties = PopupProperties(
                                    focusable = true
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(
                                            top = 500.dp
                                        )
                                        .width(300.dp)
                                        .background(
                                            color = BGColor
                                        )
                                        .clip(
                                            RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = BorderColor,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    Column {
                                        listA.forEach { label ->
                                            if (listA.indexOf(label) > listA.indexOf("Actions Available")) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .height(15.dp)
                                                )
                                                when (label) {
                                                    "Location" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_location
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Location",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    "Deadline" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_deadline
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Deadline",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                    "Date" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_today
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Date",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                    "Priority" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_priority
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Priority",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                    "Reminder" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_reminder
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Reminder",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                    "Executor" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_executor
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Executor",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                    "Tags" -> {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(
                                                                    horizontal = 15.dp
                                                                )
                                                                .clickable {
                                                                    scope.launch {
                                                                        showDialog4.value = false
                                                                    }
                                                                    listA.remove(label)
                                                                    listA.add(0, label)
                                                                    actionsRef.set(mapOf("items" to listA.toList()))
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Image(
                                                                painterResource(
                                                                    id = R.drawable.ic_tags
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp),
                                                                contentScale = ContentScale.Crop,
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                            Spacer(
                                                                modifier = Modifier
                                                                    .width(15.dp)
                                                            )
                                                            Text(
                                                                text = "Tags",
                                                                fontSize = 20.sp,
                                                                color = Color.White
                                                            )
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(15.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(
                                                    color = IntervalColor
                                                )
                                        ) {}
                                        Spacer(
                                            modifier = Modifier
                                                .height(15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .clickable {
                                                    navController.navigate(TaskFunctionsObject)
                                                    scope.launch {
                                                        sheet1State.value = false
                                                        showDialog4.value = false
                                                        isBottomMenuVisible.value = false
                                                    }
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.edit
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(
                                                    Color.White
                                                )
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Edit task actions",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(15.dp)
                                        )
                                    }
                                }
                            }
                        }

                        if (showDialog5.value) {
                            Popup(
                                alignment = Alignment.Center,
                                onDismissRequest = {
                                    showDialog5.value = false
                                },
                                properties = PopupProperties(
                                    focusable = false
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 300.dp)
                                        .clip(RoundedCornerShape(10))
                                        .width(350.dp)
                                        .background(TagsBGColor)
                                        .border(
                                            width = 2.dp,
                                            color = BorderColor,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            val match = regex.findAll(newTask).lastOrNull()
                                            match?.let {
                                                annotatedParts = annotatedParts + it.value
                                            }
                                            taskTag.value = tagName
                                            showDialog5.value = false
                                        }
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(20.dp)
                                    )
                                    Image(
                                        painterResource(
                                            id = R.drawable.ic_tags
                                        ),
                                        contentDescription = null,
                                        Modifier
                                            .size(30.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .width(20.dp)
                                    )
                                    Text(
                                        text = "Add tag '$tagName'",
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(60.dp)
                                    )
                                }
                            }
                        }
                        val selectedDimension = remember {
                            mutableIntStateOf(1)
                        }
                        val selectedDayPart = remember {
                            mutableIntStateOf(
                                if (
                                    LocalTime.now().format(
                                        DateTimeFormatter.ofPattern("a")
                                    ) == "AM"
                                ) 1
                                else 2
                            )
                        }
                        val selectedHour = remember {
                            mutableIntStateOf(LocalTime.now().hour % 12)
                        }
                        val selectedMinute = remember {
                            mutableIntStateOf(LocalTime.now().minute)
                        }
                        val keyboard = remember {
                            mutableStateOf(false)
                        }

                        if (showDialog7.value) {
                            Popup(
                                alignment = Alignment.Center,
                                onDismissRequest = {
                                    showDialog7.value = false
                                },
                                properties = PopupProperties(
                                    focusable = true
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 20.dp
                                        )
                                        .clip(
                                            RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = BorderColor,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .background(
                                            color = BGColor
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(10.dp)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 20.dp
                                                ),
                                            text = "Choose your time",
                                            color = CaptionTextColor,
                                            fontSize = 15.sp
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .height(10.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 40.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                if (!keyboard.value) {
                                                    Box(
                                                        modifier = Modifier
                                                            .height(80.dp)
                                                            .width(100.dp)
                                                            .clip(
                                                                RoundedCornerShape(10.dp)
                                                            )
                                                            .background(
                                                                color =
                                                                    if (selectedDimension.intValue == 1) AddingToneColor
                                                                    else BottomMenuColor
                                                            )
                                                            .border(
                                                                width = 2.dp,
                                                                color = BottomMenuColor,
                                                                shape = RoundedCornerShape(
                                                                    10.dp
                                                                )
                                                            )
                                                            .clickable {
                                                                selectedDimension.intValue =
                                                                    1
                                                            },
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = String.format(
                                                                "%02d",
                                                                selectedHour.intValue
                                                            ),
                                                            color = Color.White,
                                                            fontSize = 50.sp
                                                        )
                                                    }
                                                } else {
                                                    TextField(
                                                        value = selectedHour.intValue.toString(),
                                                        onValueChange = { newValue ->
                                                            newValue.toIntOrNull()
                                                                ?.let { number ->
                                                                    if (number in 1..12) {
                                                                        selectedHour.intValue =
                                                                            number
                                                                    }
                                                                }
                                                        },
                                                        textStyle = TextStyle(
                                                            color = Color.White,
                                                            fontSize = 50.sp,
                                                            textAlign = TextAlign.Center
                                                        ),
                                                        shape = RoundedCornerShape(10.dp),
                                                        colors = TextFieldDefaults.colors(
                                                            unfocusedContainerColor = BottomMenuColor,
                                                            focusedContainerColor = AddingToneColor,
                                                            unfocusedIndicatorColor = Color.Transparent,
                                                            focusedIndicatorColor = Color.Transparent,
                                                        ),
                                                        modifier = Modifier
                                                            .width(100.dp)
                                                            .height(80.dp)
                                                            .border(
                                                                width = 2.dp,
                                                                color = BottomMenuColor,
                                                                shape = RoundedCornerShape(
                                                                    10.dp
                                                                )
                                                            ),
                                                        singleLine = true,
                                                        keyboardOptions = KeyboardOptions(
                                                            keyboardType = KeyboardType.Number
                                                        )
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(5.dp)
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Text(
                                                        text = "Hours",
                                                        color = CaptionTextColor,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .width(5.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = ":",
                                                    color = Color.White,
                                                    fontSize = 50.sp
                                                )
                                                if (keyboard.value) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(5.dp)
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Text(
                                                        text = " ",
                                                        color = CaptionTextColor,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .width(5.dp)
                                            )
                                            Column {
                                                if (!keyboard.value) {
                                                    Box(
                                                        modifier = Modifier
                                                            .height(80.dp)
                                                            .width(100.dp)
                                                            .clip(
                                                                RoundedCornerShape(10.dp)
                                                            )
                                                            .background(
                                                                color =
                                                                    if (selectedDimension.intValue == 2) AddingToneColor
                                                                    else BottomMenuColor
                                                            )
                                                            .border(
                                                                width = 2.dp,
                                                                color = BottomMenuColor,
                                                                shape = RoundedCornerShape(
                                                                    10.dp
                                                                )
                                                            )
                                                            .clickable {
                                                                selectedDimension.intValue =
                                                                    2
                                                            },
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = String.format(
                                                                "%02d",
                                                                selectedMinute.intValue
                                                            ),
                                                            color = Color.White,
                                                            fontSize = 50.sp
                                                        )
                                                    }
                                                } else {
                                                    TextField(
                                                        value = selectedMinute.intValue.toString(),
                                                        onValueChange = { newValue ->
                                                            newValue.toIntOrNull()
                                                                ?.let { number ->
                                                                    if (number in 0..59) {
                                                                        selectedMinute.intValue =
                                                                            number
                                                                    }
                                                                }
                                                        },
                                                        textStyle = TextStyle(
                                                            color = Color.White,
                                                            fontSize = 50.sp,
                                                            textAlign = TextAlign.Center
                                                        ),
                                                        shape = RoundedCornerShape(10.dp),
                                                        colors = TextFieldDefaults.colors(
                                                            unfocusedContainerColor = BottomMenuColor,
                                                            focusedContainerColor = AddingToneColor,
                                                            unfocusedIndicatorColor = Color.Transparent,
                                                            focusedIndicatorColor = Color.Transparent,
                                                        ),
                                                        modifier = Modifier
                                                            .width(100.dp)
                                                            .height(80.dp)
                                                            .border(
                                                                width = 2.dp,
                                                                color = BottomMenuColor,
                                                                shape = RoundedCornerShape(
                                                                    10.dp
                                                                )
                                                            ),
                                                        singleLine = true,
                                                        keyboardOptions = KeyboardOptions(
                                                            keyboardType = KeyboardType.Number
                                                        )
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(5.dp)
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Text(
                                                        text = "Minutes",
                                                        color = CaptionTextColor,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .width(10.dp)
                                            )
                                            Column {
                                                Column(
                                                    modifier = Modifier
                                                        .height(80.dp)
                                                        .clip(
                                                            RoundedCornerShape(10.dp)
                                                        )
                                                        .border(
                                                            width = 2.dp,
                                                            color = BottomMenuColor,
                                                            shape = RoundedCornerShape(
                                                                10.dp
                                                            )
                                                        )
                                                        .background(
                                                            color = BottomMenuColor
                                                        ),
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxHeight(.5f)
                                                            .fillMaxWidth(.98f)
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    topStart = 10.dp,
                                                                    topEnd = 10.dp
                                                                )
                                                            )
                                                            .background(
                                                                color =
                                                                    if (selectedDayPart.intValue == 1) AddingToneColor
                                                                    else BottomMenuColor
                                                            )
                                                            .clickable {
                                                                selectedDayPart.intValue =
                                                                    1
                                                            },
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = "AM",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth(.98f)
                                                            .fillMaxHeight()
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    bottomStart = 10.dp,
                                                                    bottomEnd = 10.dp
                                                                )
                                                            )
                                                            .background(
                                                                color =
                                                                    if (selectedDayPart.intValue == 2) AddingToneColor
                                                                    else BottomMenuColor
                                                            )
                                                            .clickable {
                                                                selectedDayPart.intValue =
                                                                    2
                                                            },
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = "PM",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                    }
                                                }
                                                if (keyboard.value) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(5.dp)
                                                    )
                                                }
                                                if (keyboard.value) {
                                                    Text(
                                                        text = " ",
                                                        color = CaptionTextColor,
                                                        fontSize = 15.sp
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(25.dp)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 20.dp
                                                )
                                        ) {
                                            if (!keyboard.value) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(250.dp)
                                                            .clip(
                                                                CircleShape
                                                            )
                                                            .background(
                                                                BottomMenuColor
                                                            )
                                                            .border(
                                                                2.dp,
                                                                BottomMenuColor,
                                                                CircleShape
                                                            ),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Canvas(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .pointerInput(Unit) {
                                                                    detectDragGestures { change, _ ->
                                                                        val center =
                                                                            Offset(
                                                                                size.width / 2f,
                                                                                size.height / 2f
                                                                            )
                                                                        val dx =
                                                                            change.position.x - center.x
                                                                        val dy =
                                                                            change.position.y - center.y
                                                                        val angleRad =
                                                                            atan2(
                                                                                dy,
                                                                                dx
                                                                            )
                                                                        var angleDeg =
                                                                            Math.toDegrees(
                                                                                angleRad.toDouble()
                                                                            ).toFloat()

                                                                        angleDeg =
                                                                            (angleDeg + 90 + 360) % 360

                                                                        if (selectedDimension.intValue == 1) {
                                                                            val clickedHour =
                                                                                ((angleDeg / 30f).roundToInt() % 12).let {
                                                                                    if (it == 0) 12 else it
                                                                                }
                                                                            selectedHour.intValue =
                                                                                clickedHour
                                                                        } else {
                                                                            val clickedMinute =
                                                                                ((angleDeg / 6f).roundToInt() % 60)
                                                                            selectedMinute.intValue =
                                                                                clickedMinute
                                                                        }
                                                                    }
                                                                }
                                                                .pointerInput(Unit) {
                                                                    detectTapGestures { offset ->
                                                                        val center =
                                                                            Offset(
                                                                                size.width / 2f,
                                                                                size.height / 2f
                                                                            )
                                                                        val dx =
                                                                            offset.x - center.x
                                                                        val dy =
                                                                            offset.y - center.y
                                                                        val angleRad =
                                                                            atan2(
                                                                                dy,
                                                                                dx
                                                                            )
                                                                        var angleDeg =
                                                                            Math.toDegrees(
                                                                                angleRad.toDouble()
                                                                            ).toFloat()

                                                                        angleDeg =
                                                                            (angleDeg + 90 + 360) % 360

                                                                        if (selectedDimension.intValue == 1) {
                                                                            val clickedHour =
                                                                                ((angleDeg / 30f).roundToInt() % 12).let {
                                                                                    if (it == 0) 12 else it
                                                                                }
                                                                            selectedHour.intValue =
                                                                                clickedHour
                                                                        } else {
                                                                            val clickedMinute =
                                                                                ((angleDeg / 6f).roundToInt() % 60)
                                                                            selectedMinute.intValue =
                                                                                clickedMinute
                                                                        }
                                                                    }
                                                                }
                                                        ) {
                                                            val center = size.center
                                                            val radius =
                                                                size.minDimension / 2

                                                            drawCircle(
                                                                color = BottomMenuColor,
                                                                radius = radius,
                                                                center = center,
                                                                style = Stroke(4f)
                                                            )

                                                            drawIntoCanvas { canvas ->
                                                                val angleDegrees =
                                                                    if (selectedDimension.intValue == 1)
                                                                        (selectedHour.intValue % 12) * 30 - 90
                                                                    else
                                                                        (selectedMinute.intValue % 60) * 6 - 90
                                                                val angleRad =
                                                                    Math.toRadians(
                                                                        angleDegrees.toDouble()
                                                                    )
                                                                val handLength =
                                                                    radius * .8f
                                                                val endX =
                                                                    center.x + cos(
                                                                        angleRad
                                                                    ).toFloat() * handLength
                                                                val endY =
                                                                    center.y + sin(
                                                                        angleRad
                                                                    ).toFloat() * handLength

                                                                drawLine(
                                                                    color = SelectedItemColor,
                                                                    start = center,
                                                                    end = Offset(
                                                                        endX,
                                                                        endY
                                                                    ),
                                                                    strokeWidth = 6f
                                                                )

                                                                drawCircle(
                                                                    color = SelectedItemColor,
                                                                    radius = 55f,
                                                                    center = Offset(
                                                                        endX,
                                                                        endY
                                                                    )
                                                                )

                                                                drawCircle(
                                                                    color = SelectedItemColor,
                                                                    radius = 16f,
                                                                    center = center
                                                                )
                                                                val paint =
                                                                    Paint().apply {
                                                                        color =
                                                                            android.graphics
                                                                                .Color.WHITE
                                                                        textSize =
                                                                            40f
                                                                        textAlign =
                                                                            Paint.Align.CENTER
                                                                        isAntiAlias =
                                                                            true
                                                                    }
                                                                if (selectedDimension.intValue == 1) {
                                                                    for (number in 1..12) {
                                                                        val angle =
                                                                            Math.toRadians(
                                                                                ((number * 30) - 90)
                                                                                    .toDouble()
                                                                            )
                                                                        val textRadius =
                                                                            radius * 0.8f
                                                                        val x =
                                                                            center.x + cos(
                                                                                angle
                                                                            ).toFloat() * textRadius
                                                                        val y =
                                                                            center.y + sin(
                                                                                angle
                                                                            ).toFloat() * textRadius + (paint.textSize / 3)
                                                                        canvas.nativeCanvas
                                                                            .drawText(
                                                                                number.toString(),
                                                                                x,
                                                                                y,
                                                                                paint
                                                                            )
                                                                    }
                                                                } else {
                                                                    for (number in 0..55 step 5) {
                                                                        val angle =
                                                                            Math.toRadians(
                                                                                ((number * 6) - 90)
                                                                                    .toDouble()
                                                                            )
                                                                        val textRadius =
                                                                            radius * 0.8f
                                                                        val x =
                                                                            center.x + cos(
                                                                                angle
                                                                            ).toFloat() * textRadius
                                                                        val y =
                                                                            center.y + sin(
                                                                                angle
                                                                            ).toFloat() * textRadius + (paint.textSize / 3)
                                                                        canvas.nativeCanvas
                                                                            .drawText(
                                                                                number.toString(),
                                                                                x,
                                                                                y,
                                                                                paint
                                                                            )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Box(
                                                contentAlignment = Alignment.BottomStart
                                            ) {
                                                Image(
                                                    painter = painterResource(
                                                        id =
                                                            if (keyboard.value)
                                                                R.drawable.ic_reminder
                                                            else
                                                                R.drawable.ic_keyboard
                                                    ),
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(size = 30.dp)
                                                        .clickable {
                                                            keyboard.value =
                                                                !keyboard.value
                                                        },
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 20.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(height = 1.dp)
                                                .background(
                                                    color = IntervalColor
                                                )
                                        ) {}
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 20.dp
                                                ),
                                            horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Spacer(
                                                modifier = Modifier
                                                    .height(height = 83.dp)
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .clickable {
                                                        showDialog7.value = false
                                                    },
                                                text = "Cancel",
                                                fontSize = 25.sp,
                                                color = SelectedItemColor
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(width = 32.dp)
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .clickable {
                                                        showDialog7.value = false
                                                        var h =
                                                            selectedHour.intValue % 12
                                                        if (selectedDayPart.intValue != 1) h += 12
                                                        taskTime.value = LocalTime.of(
                                                            h,
                                                            selectedMinute.intValue
                                                        )
                                                    },
                                                text = "Ok",
                                                fontSize = 25.sp,
                                                color = SelectedItemColor
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    val timeFlag = remember {
                        mutableStateOf(false)
                    }

                    var selectedYearMonth by remember {
                        mutableStateOf(
                            YearMonth.now()
                        )
                    }


                    var lastCheckedDate by remember { mutableStateOf(currentDate) }

                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_RESUME) {
                                val now = LocalDate.now()
                                if (now != lastCheckedDate) {
                                    selectedDate = now
                                    lastCheckedDate = now
                                }
                            }
                        }

                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }

                    LaunchedEffect(Unit) {
                        while (true) {
                            val nowMillis = System.currentTimeMillis()
                            val midnightMillis = LocalDate.now()
                                .plusDays(1)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                                .toEpochMilli()
                            val delayMillis = midnightMillis - nowMillis

                            delay(delayMillis)

                            selectedDate = LocalDate.now()
                        }
                    }
                    if (sheet2State.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheet2State.value = false
                                    sheet1State.value = true
                                }
                            },
                            sheetState = rememberModalBottomSheetState(
                                skipPartiallyExpanded = true
                            ),
                            containerColor = BGColor,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            ),
                                        text = "Date",
                                        fontSize = 25.sp,
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            )
                                    ) {
                                        Image(
                                            painterResource(
                                                id = R.drawable.edit
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .size(30.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(15.dp)
                                        )
                                        Text(
                                            text = "${
                                                today.dayOfMonth
                                            } ${
                                                today.month.name.take(3).lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    }
                                            }.",
                                            color = Color.White,
                                            fontSize = 20.sp
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(
                                                color = IntervalColor
                                            )
                                    ) {}
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value = "Today"
                                                    dateSelection.intValue = 1
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.today_red
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Today",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = today.dayOfWeek
                                                    .name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    },
                                                color = CaptionTextColor,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value = "Tomorrow"
                                                    dateSelection.intValue = 2
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.put_tommorow
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Tomorrow",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = today.plusDays(1)
                                                    .dayOfWeek.name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    },
                                                color = CaptionTextColor,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value =
                                                        when (today.dayOfWeek.name) {
                                                            today.dayOfWeek.name -> "Today"
                                                            today.dayOfWeek.name -> "Tomorrow"
                                                            else -> "Sunday"
                                                        }
                                                    dateSelection.intValue =
                                                        when (today.dayOfWeek.name) {
                                                            today.dayOfWeek.name -> 1
                                                            today.dayOfWeek.name -> 2
                                                            else -> 4
                                                        }
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.put_weekend
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "On Weekends",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = "Sun",
                                                color = CaptionTextColor,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value = "${
                                                        selectedDate.dayOfMonth
                                                    } ${
                                                        selectedDate.month.name.take(3).lowercase()
                                                            .replaceFirstChar { c ->
                                                                c.uppercase()
                                                            }
                                                    }."
                                                    dateSelection.intValue = 3
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painterResource(
                                                    id = R.drawable.today_red
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(30.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(15.dp)
                                            )
                                            Text(
                                                text = "Next Week",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = today.dayOfWeek
                                                    .name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    },
                                                color = CaptionTextColor,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            )
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value = "No Date"
                                                    dateSelection.intValue = 3
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painterResource(
                                                id = R.drawable.put_nodate
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .size(30.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(15.dp)
                                        )
                                        Text(
                                            text = "No Date",
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(
                                                color = IntervalColor
                                            )
                                    ) {}
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                    )
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            ),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        DayOfWeek.entries.forEach {
                                            Text(
                                                text = it.name.take(3),
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.Bold,
                                                color = CaptionTextColor
                                            )
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(5.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "${
                                                today.dayOfWeek
                                                    .name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    }
                                            } ${
                                                today.dayOfMonth
                                            } ${
                                                today.month
                                                    .name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar { c ->
                                                        c.uppercase()
                                                    }
                                            } * ${
                                                list.value.count {
                                                    it.category == "Today" &&
                                                            it.dayAdded == today.toString()
                                                }
                                            } ${
                                                if (
                                                    list.value.count {
                                                        it.category == "Today" &&
                                                                it.dayAdded == today.toString()
                                                    } == 1
                                                ) "Task"
                                                else "Tasks"
                                            }",
                                            fontSize = 15.sp,
                                            color = CaptionTextColor
                                        )
                                    }


                                    val firstDay = selectedYearMonth
                                        .atDay(1)
                                    val lastDay = firstDay
                                        .plusYears(1)
                                        .minusDays(1)
                                    val totalDays = ChronoUnit.DAYS.between(
                                        firstDay,
                                        lastDay
                                    ).toInt() + 1
                                    val startOffset = firstDay
                                        .dayOfWeek.value % 7 - 1

                                    val allDates = (0 until totalDays).map { offset ->
                                        firstDay.plusDays(offset.toLong())
                                    }
                                    val days = List(
                                        startOffset
                                    ) { null } + allDates + List(
                                        (7 - (allDates.size + startOffset) % 7) % 7
                                    ) { null }


                                    val gridState = rememberLazyGridState()
                                    val scrollOffset by remember {
                                        derivedStateOf {
                                            gridState.firstVisibleItemScrollOffset
                                        }
                                    }
                                    val calendarHeight by animateDpAsState(
                                        targetValue =
                                            if (scrollOffset > 0) 200.dp
                                            else 100.dp,
                                        label = "CalendarHeightAnimation"
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(calendarHeight)
                                    ) {
                                        LazyVerticalGrid(
                                            state = gridState,
                                            columns = GridCells.Fixed(7),
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(
                                                    start = 23.dp,
                                                    end = 23.dp
                                                )
                                        ) {
                                            items(days.size) { index ->
                                                val day = days[index]
                                                val dayDate = day

                                                val isFirstDay = day?.dayOfMonth == 1

                                                Box(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .then(
                                                            if (isFirstDay) Modifier
                                                                .aspectRatio(1f)
                                                                .clip(
                                                                    RoundedCornerShape(4.dp)
                                                                )
                                                            else Modifier
                                                                .aspectRatio(1f)
                                                                .clip(
                                                                    RoundedCornerShape(26.dp)
                                                                )
                                                        )
                                                        .background(
                                                            when {
                                                                dayDate == selectedDate -> SelectedItemColor
                                                                else -> BGColor
                                                            }
                                                        )
                                                        .clickable(enabled = day != null) {
                                                            day?.let {
                                                                selectedDate = it
                                                            }
                                                        },
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    days[index]?.let {
                                                        Column(
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            if (it.dayOfMonth == 1) {
                                                                Text(
                                                                    text = it.month
                                                                        .name.take(3)
                                                                        .lowercase()
                                                                        .replaceFirstChar { c ->
                                                                            c.uppercase()
                                                                        },
                                                                    fontSize = 10.sp,
                                                                    color =
                                                                        if (dayDate == selectedDate) CaptionTextColor
                                                                        else SelectedItemColor
                                                                )
                                                            }
                                                            Text(
                                                                text = it.dayOfMonth
                                                                    .toString(),
                                                                color = Color.White,
                                                                fontSize = 14.sp
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(
                                                color = IntervalColor
                                            )
                                    ) {}
                                    val showDialog1 = remember {
                                        mutableStateOf(false)
                                    }
                                    val showDialog2 = remember {
                                        mutableStateOf(false)
                                    }

                                    val timeSelected = remember {
                                        mutableStateOf("Add time")
                                    }

                                    val repeatSelected = remember {
                                        mutableStateOf("Repeat")
                                    }
                                    val repeatFlag = remember {
                                        mutableStateOf(false)
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            )
                                            .clickable {
                                                showDialog2.value = true
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(60.dp)
                                        )
                                        Image(
                                            painterResource(
                                                id = R.drawable.ic_settime
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .size(30.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(15.dp)
                                        )
                                        if (!timeFlag.value) {
                                            Text(
                                                text = "Add Time",
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                        } else {
                                            Row(
                                                modifier = Modifier
                                                    .clip(
                                                        RoundedCornerShape(10.dp)
                                                    )
                                                    .background(
                                                        color = BGColor
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = IntervalColor,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(5.dp)
                                                )
                                                Text(
                                                    modifier = Modifier
                                                        .padding(7.dp),
                                                    text = timeSelected.value,
                                                    fontSize = 20.sp,
                                                    color = Color.White
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(7.dp)
                                                )
                                                Image(
                                                    painterResource(
                                                        id = R.drawable.ic_close
                                                    ),
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(20.dp)
                                                        .clickable {
                                                            timeFlag.value = false
                                                        },
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(5.dp)
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(
                                                color = IntervalColor
                                            )
                                    ) {}
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            )
                                            .clickable {
                                                showDialog1.value = true
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(
                                            modifier = Modifier
                                                .height(60.dp)
                                        )
                                        Image(
                                            painterResource(
                                                id = R.drawable.ic_repeat
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .size(30.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(15.dp)
                                        )
                                        if (!repeatFlag.value) {
                                            Text(
                                                text = "Repeat",
                                                color = Color.White,
                                                fontSize = 20.sp
                                            )
                                        } else {
                                            Row(
                                                modifier = Modifier
                                                    .clip(
                                                        RoundedCornerShape(10.dp)
                                                    )
                                                    .background(
                                                        color = BGColor
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = IntervalColor,
                                                        shape = RoundedCornerShape(10.dp)
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(5.dp)
                                                )
                                                Text(
                                                    modifier = Modifier
                                                        .padding(7.dp),
                                                    text = repeatSelected.value,
                                                    fontSize = 20.sp,
                                                    color = Color.White
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(7.dp)
                                                )
                                                Image(
                                                    painterResource(
                                                        id = R.drawable.ic_close
                                                    ),
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(20.dp)
                                                        .clickable {
                                                            repeatFlag.value = false
                                                        },
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(5.dp)
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(
                                                color = IntervalColor
                                            )
                                    ) {}
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .padding(
                                                start = 23.dp,
                                                end = 23.dp
                                            )
                                            .clip(
                                                RoundedCornerShape(20.dp)
                                            )
                                            .background(
                                                color = ButtonBGColor
                                            )
                                            .clickable {
                                                scope.launch {
                                                    dateSelectionText.value =
                                                        if (selectedDate == today) "Today"
                                                        else if (selectedDate.minusDays(1) == today) "Tomorrow"
                                                        else if (ChronoUnit.DAYS.between(
                                                                today,
                                                                selectedDate
                                                            ) > 6
                                                        )
                                                            "${
                                                                selectedDate.dayOfMonth
                                                            } ${
                                                                selectedDate.month.name.take(3)
                                                                    .lowercase()
                                                                    .replaceFirstChar { c ->
                                                                        c.uppercase()
                                                                    }
                                                            }."
                                                        else selectedDate.dayOfWeek.name
                                                            .lowercase()
                                                            .replaceFirstChar { c ->
                                                                c.uppercase()
                                                            }
                                                    dateSelection.intValue =
                                                        if (selectedDate == today) 1
                                                        else if (selectedDate.minusDays(1) == today) 2
                                                        else if (ChronoUnit.DAYS.between(
                                                                today,
                                                                selectedDate
                                                            ) > 6
                                                        ) 3
                                                        else 4
                                                    sheet2State.value = false
                                                    sheet1State.value = true
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Save",
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(15.dp)
                                    )

                                    if (showDialog1.value) {
                                        Popup(
                                            alignment = Alignment.Center,
                                            onDismissRequest = {
                                                showDialog1.value = false
                                            },
                                            properties = PopupProperties(
                                                focusable = true
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 60.dp,
                                                        top = 90.dp
                                                    )
                                                    .clip(
                                                        RoundedCornerShape(10.dp)
                                                    )
                                                    .border(
                                                        width = 2.dp,
                                                        color = BorderColor,
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                                    .background(
                                                        color = BGColor
                                                    )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 20.dp
                                                        ),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value = "Every day"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(20.dp)
                                                        )
                                                        Text(
                                                            text = "Every day",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value =
                                                                    "Every week at ${
                                                                        LocalDate.now().dayOfWeek
                                                                            .name.take(3)
                                                                            .lowercase()
                                                                            .replaceFirstChar { c ->
                                                                                c.uppercase()
                                                                            }
                                                                    }"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Text(
                                                            text =
                                                                "Every week at ${
                                                                    LocalDate.now().dayOfWeek
                                                                        .name.take(3)
                                                                        .lowercase()
                                                                        .replaceFirstChar { c ->
                                                                            c.uppercase()
                                                                        }
                                                                }",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value =
                                                                    "Every business day (Mon - Fri)"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Text(
                                                            text = "Every business day (Mon - Fri)",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value =
                                                                    "Every month at ${
                                                                        LocalDate.now().dayOfMonth
                                                                    }"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Text(
                                                            text =
                                                                "Every month at ${
                                                                    LocalDate.now().dayOfMonth
                                                                }",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value =
                                                                    "Every year at ${
                                                                        LocalDate.now().month
                                                                            .name.take(3)
                                                                            .lowercase()
                                                                            .replaceFirstChar { c ->
                                                                                c.uppercase()
                                                                            }
                                                                    }. ${
                                                                        LocalDate.now().dayOfMonth
                                                                    }"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Text(
                                                            text =
                                                                "Every year at ${
                                                                    LocalDate.now().month
                                                                        .name.take(3)
                                                                        .lowercase()
                                                                        .replaceFirstChar { c ->
                                                                            c.uppercase()
                                                                        }
                                                                }. ${
                                                                    LocalDate.now().dayOfMonth
                                                                }",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                repeatFlag.value = true
                                                                repeatSelected.value = "Own pattern"
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Text(
                                                            text = "Own pattern",
                                                            color = Color.White,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(20.dp)
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(
                                                                end = 15.dp
                                                            )
                                                            .height(1.dp)
                                                            .background(
                                                                color = IntervalColor
                                                            )
                                                    ) {}
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                showDialog1.value = false
                                                            }
                                                    ) {
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                        Text(
                                                            text = "Cancel",
                                                            color = SelectedItemColor,
                                                            fontSize = 20.sp
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(25.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    val selectedDimension = remember {
                                        mutableIntStateOf(1)
                                    }
                                    val selectedDayPart = remember {
                                        mutableIntStateOf(
                                            if (
                                                LocalTime.now().format(
                                                    DateTimeFormatter.ofPattern("a")
                                                ) == "AM"
                                            ) 1
                                            else 2
                                        )
                                    }
                                    val selectedHour = remember {
                                        mutableIntStateOf(LocalTime.now().hour % 12)
                                    }
                                    val selectedMinute = remember {
                                        mutableIntStateOf(LocalTime.now().minute)
                                    }
                                    val keyboard = remember {
                                        mutableStateOf(false)
                                    }

                                    if (showDialog2.value) {
                                        Popup(
                                            alignment = Alignment.Center,
                                            onDismissRequest = {
                                                showDialog2.value = false
                                            },
                                            properties = PopupProperties(
                                                focusable = true
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        horizontal = 20.dp
                                                    )
                                                    .clip(
                                                        RoundedCornerShape(10.dp)
                                                    )
                                                    .border(
                                                        width = 2.dp,
                                                        color = BorderColor,
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                                    .background(
                                                        color = BGColor
                                                    )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(10.dp)
                                                    )
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = 20.dp
                                                            ),
                                                        text = "Choose your time",
                                                        color = CaptionTextColor,
                                                        fontSize = 15.sp
                                                    )
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(10.dp)
                                                    )
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(
                                                                horizontal = 40.dp
                                                            ),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Column {
                                                            if (!keyboard.value) {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .height(80.dp)
                                                                        .width(100.dp)
                                                                        .clip(
                                                                            RoundedCornerShape(10.dp)
                                                                        )
                                                                        .background(
                                                                            color =
                                                                                if (selectedDimension.intValue == 1) AddingToneColor
                                                                                else BottomMenuColor
                                                                        )
                                                                        .border(
                                                                            width = 2.dp,
                                                                            color = BottomMenuColor,
                                                                            shape = RoundedCornerShape(
                                                                                10.dp
                                                                            )
                                                                        )
                                                                        .clickable {
                                                                            selectedDimension.intValue =
                                                                                1
                                                                        },
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = String.format(
                                                                            "%02d",
                                                                            selectedHour.intValue
                                                                        ),
                                                                        color = Color.White,
                                                                        fontSize = 50.sp
                                                                    )
                                                                }
                                                            } else {
                                                                TextField(
                                                                    value = selectedHour.intValue.toString(),
                                                                    onValueChange = { newValue ->
                                                                        newValue.toIntOrNull()
                                                                            ?.let { number ->
                                                                                if (number in 1..12) {
                                                                                    selectedHour.intValue =
                                                                                        number
                                                                                }
                                                                            }
                                                                    },
                                                                    textStyle = TextStyle(
                                                                        color = Color.White,
                                                                        fontSize = 50.sp,
                                                                        textAlign = TextAlign.Center
                                                                    ),
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    colors = TextFieldDefaults.colors(
                                                                        unfocusedContainerColor = BottomMenuColor,
                                                                        focusedContainerColor = AddingToneColor,
                                                                        unfocusedIndicatorColor = Color.Transparent,
                                                                        focusedIndicatorColor = Color.Transparent,
                                                                    ),
                                                                    modifier = Modifier
                                                                        .width(100.dp)
                                                                        .height(80.dp)
                                                                        .border(
                                                                            width = 2.dp,
                                                                            color = BottomMenuColor,
                                                                            shape = RoundedCornerShape(
                                                                                10.dp
                                                                            )
                                                                        ),
                                                                    singleLine = true,
                                                                    keyboardOptions = KeyboardOptions(
                                                                        keyboardType = KeyboardType.Number
                                                                    )
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Spacer(
                                                                    modifier = Modifier
                                                                        .height(5.dp)
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Text(
                                                                    text = "Hours",
                                                                    color = CaptionTextColor,
                                                                    fontSize = 15.sp
                                                                )
                                                            }
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .width(5.dp)
                                                        )
                                                        Column {
                                                            Text(
                                                                text = ":",
                                                                color = Color.White,
                                                                fontSize = 50.sp
                                                            )
                                                            if (keyboard.value) {
                                                                Spacer(
                                                                    modifier = Modifier
                                                                        .height(5.dp)
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Text(
                                                                    text = " ",
                                                                    color = CaptionTextColor,
                                                                    fontSize = 15.sp
                                                                )
                                                            }
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .width(5.dp)
                                                        )
                                                        Column {
                                                            if (!keyboard.value) {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .height(80.dp)
                                                                        .width(100.dp)
                                                                        .clip(
                                                                            RoundedCornerShape(10.dp)
                                                                        )
                                                                        .background(
                                                                            color =
                                                                                if (selectedDimension.intValue == 2) AddingToneColor
                                                                                else BottomMenuColor
                                                                        )
                                                                        .border(
                                                                            width = 2.dp,
                                                                            color = BottomMenuColor,
                                                                            shape = RoundedCornerShape(
                                                                                10.dp
                                                                            )
                                                                        )
                                                                        .clickable {
                                                                            selectedDimension.intValue =
                                                                                2
                                                                        },
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = String.format(
                                                                            "%02d",
                                                                            selectedMinute.intValue
                                                                        ),
                                                                        color = Color.White,
                                                                        fontSize = 50.sp
                                                                    )
                                                                }
                                                            } else {
                                                                TextField(
                                                                    value = selectedMinute.intValue.toString(),
                                                                    onValueChange = { newValue ->
                                                                        newValue.toIntOrNull()
                                                                            ?.let { number ->
                                                                                if (number in 0..59) {
                                                                                    selectedMinute.intValue =
                                                                                        number
                                                                                }
                                                                            }
                                                                    },
                                                                    textStyle = TextStyle(
                                                                        color = Color.White,
                                                                        fontSize = 50.sp,
                                                                        textAlign = TextAlign.Center
                                                                    ),
                                                                    shape = RoundedCornerShape(10.dp),
                                                                    colors = TextFieldDefaults.colors(
                                                                        unfocusedContainerColor = BottomMenuColor,
                                                                        focusedContainerColor = AddingToneColor,
                                                                        unfocusedIndicatorColor = Color.Transparent,
                                                                        focusedIndicatorColor = Color.Transparent,
                                                                    ),
                                                                    modifier = Modifier
                                                                        .width(100.dp)
                                                                        .height(80.dp)
                                                                        .border(
                                                                            width = 2.dp,
                                                                            color = BottomMenuColor,
                                                                            shape = RoundedCornerShape(
                                                                                10.dp
                                                                            )
                                                                        ),
                                                                    singleLine = true,
                                                                    keyboardOptions = KeyboardOptions(
                                                                        keyboardType = KeyboardType.Number
                                                                    )
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Spacer(
                                                                    modifier = Modifier
                                                                        .height(5.dp)
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Text(
                                                                    text = "Minutes",
                                                                    color = CaptionTextColor,
                                                                    fontSize = 15.sp
                                                                )
                                                            }
                                                        }
                                                        Spacer(
                                                            modifier = Modifier
                                                                .width(10.dp)
                                                        )
                                                        Column {
                                                            Column(
                                                                modifier = Modifier
                                                                    .height(80.dp)
                                                                    .clip(
                                                                        RoundedCornerShape(10.dp)
                                                                    )
                                                                    .border(
                                                                        width = 2.dp,
                                                                        color = BottomMenuColor,
                                                                        shape = RoundedCornerShape(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    .background(
                                                                        color = BottomMenuColor
                                                                    ),
                                                                verticalArrangement = Arrangement.Center,
                                                                horizontalAlignment = Alignment.CenterHorizontally
                                                            ) {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .fillMaxHeight(.5f)
                                                                        .fillMaxWidth(.98f)
                                                                        .clip(
                                                                            RoundedCornerShape(
                                                                                topStart = 10.dp,
                                                                                topEnd = 10.dp
                                                                            )
                                                                        )
                                                                        .background(
                                                                            color =
                                                                                if (selectedDayPart.intValue == 1) AddingToneColor
                                                                                else BottomMenuColor
                                                                        )
                                                                        .clickable {
                                                                            selectedDayPart.intValue =
                                                                                1
                                                                        },
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = "AM",
                                                                        color = Color.White,
                                                                        fontSize = 20.sp
                                                                    )
                                                                }
                                                                Box(
                                                                    modifier = Modifier
                                                                        .fillMaxWidth(.98f)
                                                                        .fillMaxHeight()
                                                                        .clip(
                                                                            RoundedCornerShape(
                                                                                bottomStart = 10.dp,
                                                                                bottomEnd = 10.dp
                                                                            )
                                                                        )
                                                                        .background(
                                                                            color =
                                                                                if (selectedDayPart.intValue == 2) AddingToneColor
                                                                                else BottomMenuColor
                                                                        )
                                                                        .clickable {
                                                                            selectedDayPart.intValue =
                                                                                2
                                                                        },
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = "PM",
                                                                        color = Color.White,
                                                                        fontSize = 20.sp
                                                                    )
                                                                }
                                                            }
                                                            if (keyboard.value) {
                                                                Spacer(
                                                                    modifier = Modifier
                                                                        .height(5.dp)
                                                                )
                                                            }
                                                            if (keyboard.value) {
                                                                Text(
                                                                    text = " ",
                                                                    color = CaptionTextColor,
                                                                    fontSize = 15.sp
                                                                )
                                                            }
                                                        }
                                                    }
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(25.dp)
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = 20.dp
                                                            )
                                                    ) {
                                                        if (!keyboard.value) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxWidth(),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .size(250.dp)
                                                                        .clip(
                                                                            CircleShape
                                                                        )
                                                                        .background(
                                                                            BottomMenuColor
                                                                        )
                                                                        .border(
                                                                            2.dp,
                                                                            BottomMenuColor,
                                                                            CircleShape
                                                                        ),
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Canvas(
                                                                        modifier = Modifier
                                                                            .fillMaxSize()
                                                                            .pointerInput(Unit) {
                                                                                detectDragGestures { change, _ ->
                                                                                    val center =
                                                                                        Offset(
                                                                                            size.width / 2f,
                                                                                            size.height / 2f
                                                                                        )
                                                                                    val dx =
                                                                                        change.position.x - center.x
                                                                                    val dy =
                                                                                        change.position.y - center.y
                                                                                    val angleRad =
                                                                                        atan2(
                                                                                            dy,
                                                                                            dx
                                                                                        )
                                                                                    var angleDeg =
                                                                                        Math.toDegrees(
                                                                                            angleRad.toDouble()
                                                                                        ).toFloat()

                                                                                    angleDeg =
                                                                                        (angleDeg + 90 + 360) % 360

                                                                                    if (selectedDimension.intValue == 1) {
                                                                                        val clickedHour =
                                                                                            ((angleDeg / 30f).roundToInt() % 12).let {
                                                                                                if (it == 0) 12 else it
                                                                                            }
                                                                                        selectedHour.intValue =
                                                                                            clickedHour
                                                                                    } else {
                                                                                        val clickedMinute =
                                                                                            ((angleDeg / 6f).roundToInt() % 60)
                                                                                        selectedMinute.intValue =
                                                                                            clickedMinute
                                                                                    }
                                                                                }
                                                                            }
                                                                            .pointerInput(Unit) {
                                                                                detectTapGestures { offset ->
                                                                                    val center =
                                                                                        Offset(
                                                                                            size.width / 2f,
                                                                                            size.height / 2f
                                                                                        )
                                                                                    val dx =
                                                                                        offset.x - center.x
                                                                                    val dy =
                                                                                        offset.y - center.y
                                                                                    val angleRad =
                                                                                        atan2(
                                                                                            dy,
                                                                                            dx
                                                                                        )
                                                                                    var angleDeg =
                                                                                        Math.toDegrees(
                                                                                            angleRad.toDouble()
                                                                                        ).toFloat()

                                                                                    angleDeg =
                                                                                        (angleDeg + 90 + 360) % 360

                                                                                    if (selectedDimension.intValue == 1) {
                                                                                        val clickedHour =
                                                                                            ((angleDeg / 30f).roundToInt() % 12).let {
                                                                                                if (it == 0) 12 else it
                                                                                            }
                                                                                        selectedHour.intValue =
                                                                                            clickedHour
                                                                                    } else {
                                                                                        val clickedMinute =
                                                                                            ((angleDeg / 6f).roundToInt() % 60)
                                                                                        selectedMinute.intValue =
                                                                                            clickedMinute
                                                                                    }
                                                                                }
                                                                            }
                                                                    ) {
                                                                        val center = size.center
                                                                        val radius =
                                                                            size.minDimension / 2

                                                                        drawCircle(
                                                                            color = BottomMenuColor,
                                                                            radius = radius,
                                                                            center = center,
                                                                            style = Stroke(4f)
                                                                        )

                                                                        drawIntoCanvas { canvas ->
                                                                            val angleDegrees =
                                                                                if (selectedDimension.intValue == 1)
                                                                                    (selectedHour.intValue % 12) * 30 - 90
                                                                                else
                                                                                    (selectedMinute.intValue % 60) * 6 - 90
                                                                            val angleRad =
                                                                                Math.toRadians(
                                                                                    angleDegrees.toDouble()
                                                                                )
                                                                            val handLength =
                                                                                radius * .8f
                                                                            val endX =
                                                                                center.x + cos(
                                                                                    angleRad
                                                                                ).toFloat() * handLength
                                                                            val endY =
                                                                                center.y + sin(
                                                                                    angleRad
                                                                                ).toFloat() * handLength

                                                                            drawLine(
                                                                                color = SelectedItemColor,
                                                                                start = center,
                                                                                end = Offset(
                                                                                    endX,
                                                                                    endY
                                                                                ),
                                                                                strokeWidth = 6f
                                                                            )

                                                                            drawCircle(
                                                                                color = SelectedItemColor,
                                                                                radius = 55f,
                                                                                center = Offset(
                                                                                    endX,
                                                                                    endY
                                                                                )
                                                                            )

                                                                            drawCircle(
                                                                                color = SelectedItemColor,
                                                                                radius = 16f,
                                                                                center = center
                                                                            )
                                                                            val paint =
                                                                                Paint().apply {
                                                                                    color =
                                                                                        android.graphics
                                                                                            .Color.WHITE
                                                                                    textSize =
                                                                                        40f
                                                                                    textAlign =
                                                                                        Paint.Align.CENTER
                                                                                    isAntiAlias =
                                                                                        true
                                                                                }
                                                                            if (selectedDimension.intValue == 1) {
                                                                                for (number in 1..12) {
                                                                                    val angle =
                                                                                        Math.toRadians(
                                                                                            ((number * 30) - 90)
                                                                                                .toDouble()
                                                                                        )
                                                                                    val textRadius =
                                                                                        radius * 0.8f
                                                                                    val x =
                                                                                        center.x + cos(
                                                                                            angle
                                                                                        ).toFloat() * textRadius
                                                                                    val y =
                                                                                        center.y + sin(
                                                                                            angle
                                                                                        ).toFloat() * textRadius + (paint.textSize / 3)
                                                                                    canvas.nativeCanvas
                                                                                        .drawText(
                                                                                            number.toString(),
                                                                                            x,
                                                                                            y,
                                                                                            paint
                                                                                        )
                                                                                }
                                                                            } else {
                                                                                for (number in 0..55 step 5) {
                                                                                    val angle =
                                                                                        Math.toRadians(
                                                                                            ((number * 6) - 90)
                                                                                                .toDouble()
                                                                                        )
                                                                                    val textRadius =
                                                                                        radius * 0.8f
                                                                                    val x =
                                                                                        center.x + cos(
                                                                                            angle
                                                                                        ).toFloat() * textRadius
                                                                                    val y =
                                                                                        center.y + sin(
                                                                                            angle
                                                                                        ).toFloat() * textRadius + (paint.textSize / 3)
                                                                                    canvas.nativeCanvas
                                                                                        .drawText(
                                                                                            number.toString(),
                                                                                            x,
                                                                                            y,
                                                                                            paint
                                                                                        )
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        Box(
                                                            contentAlignment = Alignment.BottomStart
                                                        ) {
                                                            Image(
                                                                painter = painterResource(
                                                                    id =
                                                                        if (keyboard.value)
                                                                            R.drawable.ic_reminder
                                                                        else
                                                                            R.drawable.ic_keyboard
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(size = 30.dp)
                                                                    .clickable {
                                                                        keyboard.value =
                                                                            !keyboard.value
                                                                    },
                                                                contentScale = ContentScale.Crop
                                                            )
                                                        }
                                                    }
                                                    Spacer(
                                                        modifier = Modifier
                                                            .height(height = 20.dp)
                                                    )
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(height = 1.dp)
                                                            .background(
                                                                color = IntervalColor
                                                            )
                                                    ) {}
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(
                                                                horizontal = 20.dp
                                                            ),
                                                        horizontalArrangement = Arrangement.End,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Spacer(
                                                            modifier = Modifier
                                                                .height(height = 83.dp)
                                                        )
                                                        Text(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    showDialog2.value = false
                                                                },
                                                            text = "Cancel",
                                                            fontSize = 25.sp,
                                                            color = SelectedItemColor
                                                        )
                                                        Spacer(
                                                            modifier = Modifier
                                                                .width(width = 32.dp)
                                                        )
                                                        Text(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    showDialog2.value = false
                                                                    timeFlag.value = true
                                                                    timeSelected.value =
                                                                        String.format(
                                                                            "%02d",
                                                                            selectedHour.intValue
                                                                        ) + ":" + String.format(
                                                                            "%02d",
                                                                            selectedMinute.intValue
                                                                        ) + " " +
                                                                                if (selectedDayPart.intValue == 1) "am"
                                                                                else "pm"
                                                                    var h =
                                                                        selectedHour.intValue % 12
                                                                    if (selectedDayPart.intValue != 1) h += 12
                                                                    taskTime.value = LocalTime.of(
                                                                        h,
                                                                        selectedMinute.intValue
                                                                    )
                                                                },
                                                            text = "Ok",
                                                            fontSize = 25.sp,
                                                            color = SelectedItemColor
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    val notificationInterval = remember {
                        mutableStateOf("")
                    }

                    if (sheet3State.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheet3State.value = false
                                    sheet1State.value = true
                                }
                            },
                            sheetState = rememberModalBottomSheetState(
                                skipPartiallyExpanded = true
                            ),
                            containerColor = BGColor,
                            shape = RoundedCornerShape(size = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Spacer(
                                        modifier = Modifier
                                            .height(height = 15.dp)
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 15.dp
                                            ),
                                        text = "Reminders",
                                        fontSize = 25.sp,
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(height = 15.dp)
                                    )
                                    if (!timeFlag.value) {
                                        Box(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .fillMaxWidth()
                                                .clip(
                                                    shape = RoundedCornerShape(size = 10.dp)
                                                )
                                                .background(
                                                    color = WarningColor
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    color = WarningBorderColor,
                                                    shape = RoundedCornerShape(size = 10.dp)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 15.dp,
                                                        vertical = 15.dp
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "!",
                                                    fontSize = 30.sp,
                                                    color = Color.White
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(width = 20.dp)
                                                )
                                                Text(
                                                    text =
                                                        "Add time in the first section in order to add reminders",
                                                    fontSize = 15.sp,
                                                    color = CaptionTextColor
                                                )
                                            }
                                        }
                                    } else {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(
                                                    color = IntervalColor
                                                )
                                        ) {}
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                ),
                                            text = "Recommendations",
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .clickable {
                                                    scope.launch {
                                                        selectedReminder.intValue = 1
                                                        sheet3State.value = false
                                                        sheet1State.value = true
                                                    }
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = R.drawable.ic_time_for_remind
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(size = 30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(SelectedItemColor)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(width = 20.dp)
                                            )
                                            Text(
                                                text = "30 minutes before due time",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .clickable {
                                                    scope.launch {
                                                        selectedReminder.intValue = 2
                                                        sheet3State.value = false
                                                        sheet1State.value = true
                                                    }
                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = R.drawable.ic_location
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(size = 30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(SelectedItemColor)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(width = 20.dp)
                                            )
                                            Text(
                                                text = "Location",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .clickable {

                                                },
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = R.drawable.ic_reminder
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(size = 30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(SelectedItemColor)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(width = 20.dp)
                                            )
                                            Text(
                                                text = "Time",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .clickable {
                                                    showDialog6.value = true
                                                }
                                                .padding(
                                                    horizontal = 15.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = R.drawable.ic_repeat
                                                ),
                                                contentDescription = null,
                                                Modifier
                                                    .size(size = 30.dp),
                                                contentScale = ContentScale.Crop,
                                                colorFilter = ColorFilter.tint(SelectedItemColor)
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .width(width = 20.dp)
                                            )
                                            Text(
                                                text = "Set repeating notifications",
                                                fontSize = 20.sp,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(1.dp)
                                                .background(
                                                    color = IntervalColor
                                                )
                                        ) {}
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                                .clickable {
                                                    scope.launch {
                                                        selectedReminder.intValue = 0
                                                        sheet3State.value = false
                                                        sheet1State.value = true
                                                    }
                                                },
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = "Cancel",
                                                fontSize = 20.sp,
                                                color = SelectedItemColor
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .height(height = 15.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (showDialog6.value) {
                        Popup(
                            alignment = Alignment.Center,
                            onDismissRequest = {
                                showDialog6.value = false
                            },
                            properties = PopupProperties(
                                focusable = true
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(
                                        top = 100.dp
                                    )
                                    .width(400.dp)
                                    .clip(
                                        RoundedCornerShape(15.dp)
                                    )
                                    .background(
                                        color = BGColor
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = BorderColor,
                                        shape = RoundedCornerShape(15.dp)
                                    )
                            ) {
                                val selectedNotificationTimeContent = remember {
                                    mutableIntStateOf(0)
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 18.dp
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextField(
                                            value = notificationInterval.value,
                                            onValueChange = {
                                                notificationInterval.value = it
                                            },
                                            shape = RoundedCornerShape(7.dp),
                                            colors = TextFieldDefaults.colors(
                                                unfocusedContainerColor = BottomMenuColor,
                                                focusedContainerColor = BottomMenuColor,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth(.80f)
                                                .height(60.dp),
                                            label = {
                                                Text(
                                                    text = "Repeat",
                                                    color = CaptionTextColor
                                                )
                                            }
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(10.dp)
                                        )
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .clip(
                                                    RoundedCornerShape(10.dp)
                                                )
                                                .border(
                                                    width = 2.dp,
                                                    color = BottomMenuColor,
                                                    shape = RoundedCornerShape(
                                                        10.dp
                                                    )
                                                )
                                                .background(
                                                    color = BottomMenuColor
                                                ),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(.98f)
                                                    .height(20.dp)
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 10.dp,
                                                            topEnd = 10.dp
                                                        )
                                                    )
                                                    .background(
                                                        color =
                                                            if (selectedNotificationTimeContent.intValue == 0) AddingToneColor
                                                            else BottomMenuColor
                                                    )
                                                    .clickable {
                                                        selectedNotificationTimeContent.intValue =
                                                            0
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Min",
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(.98f)
                                                    .height(20.dp)
                                                    .background(
                                                        color =
                                                            if (selectedNotificationTimeContent.intValue == 1) AddingToneColor
                                                            else BottomMenuColor
                                                    )
                                                    .clickable {
                                                        selectedNotificationTimeContent.intValue =
                                                            1
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Hr",
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(.98f)
                                                    .height(20.dp)
                                                    .clip(
                                                        RoundedCornerShape(
                                                            bottomStart = 10.dp,
                                                            bottomEnd = 10.dp
                                                        )
                                                    )
                                                    .background(
                                                        color =
                                                            if (selectedNotificationTimeContent.intValue == 2) AddingToneColor
                                                            else BottomMenuColor
                                                    )
                                                    .clickable {
                                                        selectedNotificationTimeContent.intValue =
                                                            2
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Day",
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(10.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(75.dp)
                                                .height(45.dp)
                                                .clip(
                                                    RoundedCornerShape(10.dp)
                                                )
                                                .background(
                                                    color = ButtonBGColor
                                                )
                                                .clickable {
                                                    var minutes =
                                                        notificationInterval.value.toInt()

                                                    if (selectedNotificationTimeContent.intValue == 1) {
                                                        minutes = minutes * 60
                                                    } else if (selectedNotificationTimeContent.intValue == 2) {
                                                        minutes = minutes * 60 * 24
                                                    }

                                                    if (minutes > 0) {
                                                        scheduleRepeatingNotification(
                                                            context,
                                                            minutes
                                                        )
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                "Apply",
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        window.navigationBarColor = Color(0xFF262626).toArgb()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "minute_channel",
                "Minute Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun scheduleRepeatingNotification(context: Context, minutes: Int) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val intervalMillis = minutes * 60 * 1000L
        val triggerAtMillis = System.currentTimeMillis() + intervalMillis

        try {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                intervalMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = "minute_channel"
        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentTitle("Minute Alert")
            .setContentText("This is your periodic notification.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        try {
            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, notification)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
