/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.SdkTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreens(
    helpScreenPages: List<HelpScreenPage>,
    onHelpScreensCloseRequested: (allPagesDisplayed: Boolean) -> Unit
) {
    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val allPagesVisited = rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            onHelpScreensCloseRequested(allPagesVisited.value)
        },
        sheetMaxWidth = 800.dp,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {
            BottomSheetDefaults.DragHandle(modifier = Modifier.clearAndSetSemantics { })
        }
    ) {
        val pagerState = rememberPagerState(pageCount = {
            helpScreenPages.size
        })

        val visitedPages = rememberSaveable { mutableSetOf<Int>() }
        LaunchedEffect(pagerState.currentPage) {
            visitedPages.add(pagerState.currentPage)
            if (visitedPages.size == helpScreenPages.size) {
                allPagesVisited.value = true
            }
        }

        Column(modifier = Modifier.height(if (orientation == Configuration.ORIENTATION_PORTRAIT) 520.dp else 240.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.semantics {
                        traversalIndex = 2f
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        if (pagerState.canScrollBackward) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        } else onHelpScreensCloseRequested(allPagesVisited.value)

                    }) {
                    Text(
                        text = if (pagerState.canScrollBackward) stringResource(R.string.mb_blinkid_dialog_back_button) else stringResource(
                            R.string.mb_blinkid_dialog_skip_button
                        ),
                        style = SdkTheme.sdkTypography.helpDialogButton
                    )
                }
                Button(
                    modifier = Modifier.semantics {
                        traversalIndex = 2f
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        if (pagerState.canScrollForward) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else onHelpScreensCloseRequested(allPagesVisited.value)
                    }) {
                    Text(
                        text = if (pagerState.canScrollForward) stringResource(R.string.mb_blinkid_dialog_next_button) else stringResource(
                            R.string.mb_blinkid_dialog_done_button
                        ),
                        style = SdkTheme.sdkTypography.helpDialogButton
                    )
                }
            }

            // Prevent bottom sheet from closing when scrolling
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPostScroll(
                        consumed: Offset,
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        // Consume any remaining scroll to prevent bottom sheet from closing
                        return available
                    }
                }
            }

            when (orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    HelpScreensContentLandscape(
                        modifier = Modifier.semantics {
                            traversalIndex = 1f
                        },
                        pagerState = pagerState,
                        helpScreens = helpScreenPages,
                        nestedScrollConnection = nestedScrollConnection
                    )
                }

                else -> {
                    HelpScreensContentPortrait(
                        modifier = Modifier.semantics {
                            traversalIndex = 1f
                        },
                        pagerState = pagerState,
                        helpScreens = helpScreenPages,
                        nestedScrollConnection = nestedScrollConnection
                    )
                }
            }
        }
    }
}

@Composable
fun HelpScreensContentPortrait(
    modifier: Modifier,
    pagerState: PagerState,
    helpScreens: List<HelpScreenPage>,
    nestedScrollConnection: NestedScrollConnection? = null
) {
    Column {
        HorizontalPager(
            modifier = modifier
                .weight(0.80f)
                .fillMaxWidth(),
            state = pagerState
        ) { pageNum ->
            val page = helpScreens[pageNum]
            Column(Modifier.fillMaxHeight()) {
                Column(Modifier.weight(0.4f)) {
                    Image(
                        bitmap = ContextCompat.getDrawable(
                            LocalContext.current,
                            page.pageImage
                        )?.toBitmap()?.asImageBitmap()!!,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .clearAndSetSemantics {},
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(bottom = 20.dp),
                ) {
                    Spacer(Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .padding(start = 40.dp, end = 40.dp)
                            .drawScrollbar(rememberScrollState(), nestedScrollConnection = nestedScrollConnection)
                            .weight(weight = 0.6f, fill = false)
                    ) {
                        Text(
                            modifier = Modifier.semantics {
                                heading()
                            },
                            text = stringResource(page.pageTitle),
                            style = SdkTheme.sdkTypography.helpDialogTitle,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            modifier = modifier.fillMaxHeight(),
                            text = stringResource(page.pageMessage),
                            style = SdkTheme.sdkTypography.helpDialogText,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.15f)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.4f
                    )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Composable
fun HelpScreensContentLandscape(
    modifier: Modifier,
    pagerState: PagerState,
    helpScreens: List<HelpScreenPage>,
    nestedScrollConnection: NestedScrollConnection? = null
) {
    Column(modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier
                .weight(0.80f)
                .fillMaxWidth(),
            state = pagerState
        ) { pageNum ->
            val page = helpScreens[pageNum]
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
            ) {
                Column(Modifier.weight(0.35f)) {
                    Image(
                        ContextCompat.getDrawable(
                            LocalContext.current,
                            page.pageImage
                        )?.toBitmap()?.asImageBitmap()!!,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(0.65f)
                        .then(
                            nestedScrollConnection?.let { Modifier.nestedScroll(it) } ?: Modifier
                        )
                        .drawScrollbar(rememberScrollState(), nestedScrollConnection = nestedScrollConnection)
                ) {
                    Text(
                        modifier = Modifier.semantics {
                            heading()
                        },
                        text = stringResource(page.pageTitle),
                        style = SdkTheme.sdkTypography.helpDialogTitle,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Column(
                        modifier = Modifier
                    ) {
                        Spacer(Modifier.height(16.dp))
                        Text(
                            modifier = modifier.fillMaxHeight(),
                            text = stringResource(page.pageMessage),
                            style = SdkTheme.sdkTypography.helpDialogText,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.20f)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.4f
                    )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

data class HelpScreens(
    val onboardingDialogPage: HelpScreenPage,
    val helpDialogPages: List<HelpScreenPage>
)

data class HelpScreenPage(
    @param:DrawableRes val pageImage: Int,
    @param:StringRes val pageTitle: Int,
    @param:StringRes val pageMessage: Int
)
