package com.example.everydaytasks.ui.theme.taskfunc

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.example.everydaytasks.R
import com.example.everydaytasks.ui.theme.AddActionsColor
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.BorderColor
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.ButtonBGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
import com.example.everydaytasks.ui.theme.IntervalColor
import com.example.everydaytasks.ui.theme.SelectedItemColor
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import kotlin.math.roundToInt

@Composable
fun TaskFunctionsPage(
    onNavigationToProgressPage: (ProgressScreenDataObject) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = BGColor
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp,
                            start = 5.dp,
                            end = 5.dp
                        )
                        .clickable {
                            onNavigationToProgressPage(
                                ProgressScreenDataObject()
                            )
                        },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Add",
                    tint = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .width(25.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp,
                            start = 5.dp,
                            end = 5.dp
                        ),
                    text = "Fast Adding",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            var isOn by remember {
                mutableStateOf(true)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Show names of actions",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                    )
                    Text(
                        text =
                            if (isOn)
                                "All actions text are shown"
                            else
                                "All actions text are hidden",
                        fontSize = 10.sp,
                        color = CaptionTextColor
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    val offset by animateDpAsState(
                        targetValue =
                            if (isOn) 15.dp
                            else 3.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    val knobSize by animateDpAsState(
                        targetValue =
                            if (isOn) 16.dp
                            else 12.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    val borderWidth by animateDpAsState(
                        targetValue = if (isOn) 0.dp else 2.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(25.dp)
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                            .background(
                                color =
                                    if (isOn)
                                        ButtonBGColor
                                    else
                                        BottomMenuColor
                            )
                            .border(
                                width = borderWidth,
                                color =
                                    if (isOn)
                                        Color.Transparent
                                    else
                                        Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                isOn = !isOn
                            }
                            .padding(4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(x = offset)
                                .size(knobSize)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    color =
                                        if (isOn)
                                            BottomMenuColor
                                        else
                                            Color.White
                                )
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(35.dp)
            )

            val actions = remember {
                mutableStateListOf(
                    "Actions Available",
                    "Date",
                    "Priority",
                    "Reminder",
                    "Executor",
                    "Tags",
                    "Deadline",
                    "Location",
                )
            }
            val dividerIndex = remember {
                mutableIntStateOf(actions.indexOf("Actions Available"))
            }

            val draggedItem = remember { mutableStateOf<String?>(null) }
            val itemOffsets = remember { mutableStateMapOf<String, Float>() }
            val itemBounds = remember { mutableStateMapOf<String, Pair<Float, Float>>() }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 38.dp
                    ),
                text = "Preview",
                fontSize = 15.sp,
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            LazyRow(
                modifier = Modifier
                    .padding(
                        horizontal = 25.dp
                    )
                    .offset(y = (-2).dp)
            ) {
                items(actions) { label ->
                    if (actions.indexOf(label) <= dividerIndex.intValue) {
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
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (label != "Actions Available") {
                                Spacer(
                                    modifier = Modifier
                                        .width(width = 10.dp)
                                )
                                Image(
                                    painter = painterResource(
                                        id =
                                            when (label) {
                                                "Date" -> R.drawable.ic_today
                                                "Priority" -> R.drawable.ic_priority
                                                "Reminder" -> R.drawable.ic_reminder
                                                "Executor" -> R.drawable.ic_executor
                                                "Tags" -> R.drawable.ic_tags
                                                "Deadline" -> R.drawable.ic_deadline
                                                else -> R.drawable.ic_location
                                            }
                                    ),
                                    contentDescription = null,
                                    Modifier
                                        .size(size = 20.dp),
                                    contentScale = ContentScale.Crop,
                                    colorFilter = ColorFilter.tint(
                                        color = Color.White
                                    )
                                )
                                if (isOn) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(8.dp)
                                    )
                                    Text(
                                        text = label,
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .width(10.dp)
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(40.dp)
                                )
                            } else {
                                Spacer(
                                    modifier = Modifier
                                        .width(width = 10.dp)
                                )
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.three_dots
                                    ),
                                    contentDescription = null,
                                    Modifier
                                        .size(size = 20.dp),
                                    contentScale = ContentScale.Crop,
                                    colorFilter = ColorFilter.tint(
                                        color = Color.White
                                    )
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
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 1.dp)
                    .background(
                        color = IntervalColor
                    )
            ) {}

            val all = remember {
                mutableStateOf(true)
            }

            all.value = when (dividerIndex.intValue) {
                1 -> true
                actions.size - 1 -> false
                else -> all.value
            }

            Spacer(
                modifier = Modifier
                    .height(
                        20.dp
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Order of actions in the task",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (all.value) {
                                actions.remove("Actions Available")
                                actions.add(actions.size, "Actions Available")
                            } else {
                                actions.remove("Actions Available")
                                actions.add(0, "Actions Available")
                            }
                            dividerIndex.intValue = actions.indexOf("Actions Available")
                            all.value = !all.value
                        },
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text =
                            if (all.value) "Show all"
                            else "Hide all",
                        fontSize = 15.sp,
                        color = SelectedItemColor
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(
                        25.dp
                    )
            )
            actions.forEachIndexed { index, label ->
                if (label != "...") {
                    val animatedOffset by animateFloatAsState(
                        targetValue = itemOffsets[label] ?: 0f,
                        animationSpec = tween(durationMillis = 200),
                        label = "offsetAnim"
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp
                            )
                            .then(
                                if (draggedItem.value == label) {
                                    Modifier.offset { IntOffset(0, itemOffsets[label]?.roundToInt() ?: 0) }
                                } else {
                                    Modifier.offset { IntOffset(0, animatedOffset.roundToInt()) }
                                }
                            )
                            .zIndex(if (draggedItem.value == label) 10f else 0f)
                            .shadow(
                                elevation = if (draggedItem.value == label) 20.dp else 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(
                                color = BGColor
                            )
                            .onGloballyPositioned { coordinates ->
                                if (draggedItem.value == null) {
                                    val y = coordinates.positionInParent().y
                                    val h = coordinates.size.height.toFloat()
                                    itemBounds[label] = y to h
                                }
                            }
                            .pointerInput(label) {
                                detectDragGestures(
                                    onDragStart = {
                                        if (label != "Actions Available") {
                                            draggedItem.value = label
                                        }
                                    },
                                    onDrag = { change, dragAmount ->
                                        if (draggedItem.value != null) {
                                            change.consume()
                                            val currentOffset = itemOffsets[label] ?: 0f
                                            itemOffsets[label] = currentOffset + dragAmount.y

                                            val draggedTop = (itemBounds[label]?.first ?: 0f) + (itemOffsets[label] ?: 0f)
                                            val draggedHeight = itemBounds[label]?.second ?: 0f
                                            val draggedCenter = draggedTop + draggedHeight / 2

                                            val fromIndex = actions.indexOf(label)

                                            itemOffsets.keys.forEach { key ->
                                                if (key != label) itemOffsets[key] = 0f
                                            }

                                            for (i in fromIndex + 1 until actions.size) {
                                                val other = actions[i]
                                                val (y, h) = itemBounds[other] ?: continue
                                                val otherCenter = y + h / 2f

                                                if (draggedCenter > otherCenter) {
                                                    itemOffsets[other] = -draggedHeight
                                                } else break
                                            }

                                            for (i in (0 until fromIndex).reversed()) {
                                                val other = actions[i]
                                                val (y, h) = itemBounds[other] ?: continue
                                                val otherCenter = y + h / 2f

                                                if (draggedCenter < otherCenter) {
                                                    itemOffsets[other] = draggedHeight
                                                } else break
                                            }
                                        }
                                    },
                                    onDragEnd = {
                                        if (draggedItem.value != null) {
                                            val fromIndex = actions.indexOf(label)

                                            val draggedTop = (itemBounds[label]?.first ?: 0f) + (itemOffsets[label] ?: 0f)
                                            val draggedHeight = itemBounds[label]?.second ?: 0f
                                            val draggedCenter = draggedTop + draggedHeight / 2f

                                            val otherShifted = itemOffsets.any { (key, value) ->
                                                key != label && value != 0f
                                            }
                                            val isDraggingUp = (itemOffsets[label] ?: 0f) < 0f

                                            var targetIndex = 0
                                            for (i in actions.indices) {
                                                if (i == fromIndex) continue
                                                val (y, h) = itemBounds[actions[i]] ?: continue
                                                val otherCenter = y + h / 2f
                                                if (draggedCenter > otherCenter) {
                                                    targetIndex =
                                                        if (!otherShifted || isDraggingUp) i + 1
                                                        else i
                                                }
                                            }

                                            if (targetIndex != fromIndex) {
                                                actions.add(targetIndex, actions.removeAt(fromIndex))
                                            }

                                            dividerIndex.intValue = actions.indexOf("Actions Available")
                                            itemOffsets.keys.forEach { key -> itemOffsets[key] = 0f }
                                            draggedItem.value = null
                                        }
                                    },
                                    onDragCancel = {
                                        itemOffsets.keys.forEach { key -> itemOffsets[key] = 0f }
                                        draggedItem.value = null
                                    }
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (label != "Actions Available") {
                            Image(
                                painter = painterResource(
                                    id =
                                        if (index < dividerIndex.intValue)
                                            R.drawable.ic_remove_action
                                        else
                                            R.drawable.ic_add_action
                                ),
                                contentDescription = null,
                                Modifier
                                    .size(size = 30.dp)
                                    .clickable {
                                        actions.remove(label)
                                        if (index < dividerIndex.intValue) {
                                            actions.add(actions.size, label)
                                        } else {
                                            actions.add(0, label)
                                        }
                                        dividerIndex.intValue =
                                            actions.indexOf("Actions Available")
                                    },
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    color =
                                        if (index < dividerIndex.intValue)
                                            SelectedItemColor
                                        else
                                            AddActionsColor
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )
                            Image(
                                painter = painterResource(
                                    id =
                                        when (label) {
                                            "Date" -> R.drawable.ic_today
                                            "Priority" -> R.drawable.ic_priority
                                            "Reminder" -> R.drawable.ic_reminder
                                            "Executor" -> R.drawable.ic_executor
                                            "Tags" -> R.drawable.ic_tags
                                            "Deadline" -> R.drawable.ic_deadline
                                            else -> R.drawable.ic_location
                                        }
                                ),
                                contentDescription = null,
                                Modifier
                                    .size(size = 20.dp),
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(
                                    color = Color.White
                                )
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(22.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .height(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ic_drag
                                    ),
                                    contentDescription = null,
                                    Modifier
                                        .size(size = 20.dp),
                                    contentScale = ContentScale.Crop,
                                    colorFilter = ColorFilter.tint(
                                        color = Color.White
                                    )
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "Actions available",
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                                Spacer(
                                    modifier = Modifier
                                        .height(50.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}