package com.example.everydaytasks

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import java.time.ZoneId
import kotlin.text.replaceFirstChar

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

                    fs.collection("tasks").addSnapshotListener{snapshot, exception ->
                        list.value = snapshot?.toObjects(ProgressScreenDataObject::class.java) ?: emptyList()
                    }
                    val key = fs.collection("tasks")
                        .document().id
                    val today = remember {
                        LocalDate.now()
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

                    if (sheet1State.value == true) {
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
                                                Icon(
                                                    Icons.Default.DateRange,
                                                    contentDescription = "",
                                                    tint = TodayColor,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(8.dp)
                                                )
                                                Text(
                                                    text = "Today",
                                                    color = TodayColor,
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
                                                        id = R.drawable.ic_edit
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
                                                .clip(
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .background(
                                                    color = GreyButtonBGColor
                                                )
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
                    if (sheet2State.value == true) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheet2State.value = false
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
                                        .padding(
                                            start = 15.dp,
                                            end = 15.dp
                                        )
                                ) {
                                    Text(
                                        text = "Date",
                                        fontSize = 25.sp,
                                        color = Color.White
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .height(20.dp)
                                    )
                                    Row{
                                        Image(
                                            painterResource(
                                                id = R.drawable.edit
                                            ),
                                            contentDescription = null,
                                            Modifier
                                                .size(20.dp),
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
                                                    .replaceFirstChar {
                                                        c -> c.uppercase()
                                                    }
                                            }.",
                                            color = Color.White,
                                            fontSize = 15.sp
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
                                            .height(20.dp)
                                    )
                                    Row(
                                        Modifier
                                            .fillMaxWidth(),
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
                                                    .replaceFirstChar {
                                                            c -> c.uppercase()
                                                    }
                                            } ${
                                                today.dayOfMonth
                                            } ${
                                                today.month
                                                    .name.take(3)
                                                    .lowercase()
                                                    .replaceFirstChar {
                                                            c -> c.uppercase()
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
                                    val totalDays = java.time.temporal
                                        .ChronoUnit.DAYS.between(
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
                                            if (scrollOffset > 0) 250.dp
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
                                    Spacer(
                                        modifier = Modifier
                                            .height(100.dp)
                                    )
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