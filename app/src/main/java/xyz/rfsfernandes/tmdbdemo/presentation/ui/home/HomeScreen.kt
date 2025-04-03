package xyz.rfsfernandes.tmdbdemo.presentation.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import xyz.rfsfernandes.tmdbdemo.domain.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.BoxVignetting
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.ErrorModal
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.PageIndicator
import xyz.rfsfernandes.tmdbdemo.presentation.ui.theme.Red

@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vm.getMovies(context.resources.configuration.locales[0].language)
    }
    val scrollState = vm.homeList
    val state = vm.viewState.collectAsState().value
    state.error?.let {
        ErrorModal(it)
    } ?: run {
        LazyColumn(
            state = scrollState
        ) {
            items(MovieHomeType.entries) {
                HomeScreenSections(it.title, vm, it, onMovieClick)
                if (it.ordinal == MovieHomeType.entries.size - 1) {
                    Spacer(
                        modifier = Modifier.height(32.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun FeaturedMoviesPager(movies: LazyPagingItems<MovieDataModel>?, onMovieClick: (Int) -> Unit) {
    val screenHeight =
        LocalContext.current.resources.displayMetrics.heightPixels / LocalContext.current.resources.displayMetrics.density
    val cardHeight = screenHeight * 0.60f

    movies?.let {
        val pagerState = rememberPagerState(
            pageCount = { it.itemCount })

        if (it.itemCount > 0) {
            val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
            val pageInteractionSource = remember { MutableInteractionSource() }
            val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

            // Stop auto-advancing when the pager is dragged or a page is pressed
            val autoAdvance = !pagerIsDragged && !pageIsPressed

            LaunchedEffect(autoAdvance) {
                while (autoAdvance) {
                    delay(4000)
                    val nextPage = (pagerState.currentPage + 1) % it.itemCount
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        Box(
            modifier = Modifier.clickable {
                onMovieClick(it[pagerState.currentPage]?.movieId ?: 0)
            }
        ) {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight.dp)
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${it[page]?.posterPath}",
                        contentDescription = it[page]?.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    BoxVignetting(cardHeight)
                }
            }
            // Pager Indicators
            PageIndicator(
                numberOfPages = it.itemCount,
                selectedPage = pagerState.currentPage,
                selectedColor = Red,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 14.dp)
            )
        }
    }
}

@Composable
fun HomeScreenSections(
    @StringRes title: Int,
    vm: HomeViewModel,
    movieHomeType: MovieHomeType,
    onMovieClick: (Int) -> Unit
) {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels / LocalContext.current.resources.displayMetrics.density
    val cardWidth = screenWidth * 0.65f
    val screenHeight =
        LocalContext.current.resources.displayMetrics.heightPixels / LocalContext.current.resources.displayMetrics.density
    val cardHeight = screenHeight * 0.45f

    val viewState = vm.viewState.collectAsState().value
    val movies = when (movieHomeType) {
        MovieHomeType.POPULAR -> viewState.popularMovies?.collectAsLazyPagingItems()
        MovieHomeType.NOW_PLAYING -> viewState.nowPlayingMovies?.collectAsLazyPagingItems()
        MovieHomeType.TOP_RATED -> viewState.topRatedMovies?.collectAsLazyPagingItems()
        MovieHomeType.UPCOMING -> viewState.upcomingMovies?.collectAsLazyPagingItems()
        MovieHomeType.FEATURED -> viewState.featuredMovies?.collectAsLazyPagingItems()
    }

    when (movieHomeType) {
        MovieHomeType.FEATURED -> {
            FeaturedMoviesPager(movies, onMovieClick)
        }

        MovieHomeType.NOW_PLAYING -> {
            Spacer(
                Modifier.height(12.dp)
            )
            MovieCategorySection(
                title,
                movies,
                movieHomeType,
                cardWidth,
                cardHeight,
                vm.categoriesListState.find { it.first == movieHomeType }!!.second.value,
                onMovieClick
            )
        }

        else -> {
            MovieCategorySection(
                title,
                movies,
                movieHomeType,
                cardWidth,
                cardHeight,
                vm.categoriesListState.find { it.first == movieHomeType }!!.second.value,
                onMovieClick
            )
        }
    }
}

@Composable
fun MovieCategorySection(
    @StringRes title: Int,
    movies: LazyPagingItems<MovieDataModel>?,
    movieHomeType: MovieHomeType,
    cardWidth: Float,
    cardHeight: Float,
    scrollState: LazyListState,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(
            Modifier.height(12.dp)
        )
        Text(
            text = LocalContext.current.getString(title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(
            Modifier.height(6.dp)
        )

        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movies?.let { movies ->
                items(
                    count = movies.itemCount,
                    key = movies.itemKey { it.movieId },
                    contentType = movies.itemContentType { movieHomeType.name }) { index ->
                    val movie = movies[index]
                    movie?.let { MovieItem(it, cardWidth, cardHeight, onMovieClick) }
                }
            }
        }
        Spacer(
            Modifier.height(6.dp)
        )
    }
}

@Composable
fun MovieItem(
    movie: MovieDataModel,
    cardWidth: Float = 150f,
    cardHeight: Float = 220f,
    onMovieClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .width(cardWidth.dp)
            .height(cardHeight.dp)
            .padding(8.dp)
            .clickable {
                onMovieClick(movie.movieId)
            }
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.medium,
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        BoxVignetting(cardHeight, true)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen {

    }
}
