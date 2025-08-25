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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.everydaytasks.ui.theme.AddingToneColor
import com.example.everydaytasks.ui.theme.TomorrowColor
import com.example.everydaytasks.ui.theme.WeekColor
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.text.replaceFirstChar

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        enableEdgeToEdge()
        setContent {
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
                            ProgressPage()
                        }
                        composable<DateScreenDataObject> {
                            DatePage()
                        }
                        composable<SearchScreenDataObject> {
                            SearchPage()
                        }
                        composable<ReviewScreenDataObject> {
                            ReviewPage()
                        }
                    }
                    val newTask = remember {
                        mutableStateOf("")
                    }

                    val fs = Firebase.firestore
                    val list = remember {
                        mutableStateOf(
                            emptyList<ProgressScreenDataObject>()
                        )
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

                    val dayAdding = remember {
                        mutableStateOf("")
                    }
                    val addForToday = remember {
                        mutableStateOf(true)
                    }

                    val filters = listOf(
                        "Today"
                    )
                    val dateSelection = remember {
                        mutableIntStateOf(1)
                    }
                    val dateSelectionText = remember {
                        mutableStateOf("Today")
                    }

                    if (sheet1State.value) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheet1State.value = false
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
                                    if (addForToday.value) {
                                        TextField(
                                            value = dayAdding.value,
                                            onValueChange = {
                                                dayAdding.value = it
                                            },
                                            shape = RoundedCornerShape(7.dp),
                                            colors = TextFieldDefaults.colors(
                                                unfocusedTextColor = Color.White,
                                                focusedTextColor = Color.White,
                                                unfocusedContainerColor = BGColor,
                                                focusedContainerColor = BGColor,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp),
                                            label = {
                                                Text(
                                                    text = "YYYY-DD-MM",
                                                    color = CaptionTextColor
                                                )
                                            }
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                    )
                                    TextField(
                                        value = newTask.value,
                                        onValueChange = {
                                            newTask.value = it
                                        },
                                        shape = RoundedCornerShape(7.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedTextColor = Color.White,
                                            focusedTextColor = Color.White,
                                            unfocusedContainerColor = BGColor,
                                            focusedContainerColor = BGColor,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp),
                                        label = {
                                            Text(
                                                text = "Task",
                                                color = CaptionTextColor
                                            )
                                        }
                                    )
                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    ) {
                                        items(filters) { label ->
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
                                                        sheet1State.value = false
                                                        sheet2State.value = true
                                                    },
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(10.dp)
                                                )
                                                Image(
                                                    painterResource(
                                                        id = R.drawable.ic_today
                                                    ),
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(20.dp),
                                                    contentScale = ContentScale.Crop,
                                                    colorFilter = ColorFilter.tint(
                                                        when (dateSelection.intValue) {
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

                                                    },
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(10.dp)
                                                )
                                                Image(
                                                    painterResource(
                                                        id = R.drawable.ic_priority
                                                    ),
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(20.dp),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(8.dp)
                                                )
                                                Text(
                                                    text = "Priority",
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
                                                    contentScale = ContentScale.Crop
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(8.dp)
                                                )
                                                Text(
                                                    text = "Reminder",
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
                                        Image(
                                            painterResource(
                                                id = if (addForToday.value) R.drawable.completed
                                                else R.drawable.notcompleted
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .padding(5.dp)
                                                .size(30.dp)
                                                .clickable {
                                                    addForToday.value =
                                                        !addForToday.value
                                                },
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(5.dp)
                                        )
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
                                                                    newTask = newTask.value,
                                                                    isCompleted = false,
                                                                    key = key,
                                                                    dayAdded =
                                                                        if (addForToday.value) dayAdding.value
                                                                        else today.toString(),
                                                                    daySelected = today.toString(),
                                                                    category =
                                                                        if (addForToday.value) "Today"
                                                                        else "EveryDay",
                                                                    wasAdded = addForToday.value,
                                                                    lastAdded = today.minusDays(
                                                                        1
                                                                    ).toString(),
                                                                    firstAdd = 1
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
                    }

                    var selectedYearMonth by remember {
                        mutableStateOf(
                            YearMonth.now()
                        )
                    }

                    val currentDate = LocalDate.now()
                    var selectedDate by remember { mutableStateOf(currentDate) }
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
                                    val timeFlag = remember {
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
                                                        .size(20.dp),
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
                                        Text(
                                            text = "Repeat",
                                            color = Color.White,
                                            fontSize = 20.sp
                                        )
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
                                                        color = IntervalColor,
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
                                                        color = IntervalColor,
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
                                                                                android.graphics
                                                                                    .Paint().apply {
                                                                                        color =
                                                                                            android.graphics
                                                                                                .Color.WHITE
                                                                                        textSize =
                                                                                            40f
                                                                                        textAlign =
                                                                                            android.graphics
                                                                                                .Paint.Align.CENTER
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
                                                                painterResource(
                                                                    id =
                                                                        if (keyboard.value)
                                                                            R.drawable.ic_reminder
                                                                        else
                                                                            R.drawable.ic_keyboard
                                                                ),
                                                                contentDescription = null,
                                                                Modifier
                                                                    .size(30.dp)
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
                                                                .height(83.dp)
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
                                                                .width(32.dp)
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
                }
            }
        }

        window.navigationBarColor = Color(0xFF262626).toArgb()

    }
}