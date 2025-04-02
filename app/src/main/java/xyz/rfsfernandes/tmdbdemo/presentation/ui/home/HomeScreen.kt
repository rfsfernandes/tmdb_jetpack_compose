package xyz.rfsfernandes.tmdbdemo.presentation.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import xyz.rfsfernandes.tmdbdemo.data.local.model.MovieHomeType
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDataModel
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.BoxVignetting
import xyz.rfsfernandes.tmdbdemo.presentation.ui.theme.DarkRed
import xyz.rfsfernandes.tmdbdemo.presentation.ui.theme.Red


@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vm.getMovies(context.resources.configuration.locales[0].language)
    }
    LazyColumn {
        items(MovieHomeType.entries) {
            MovieCategorySection(it.name, vm, it)
        }
    }
}

@Composable
fun FeaturedMoviesPager(movies: LazyPagingItems<MovieDataModel>?) {
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / LocalContext.current.resources.displayMetrics.density
    val cardHeight = screenHeight * 0.50f
    movies?. let {
        val pagerState = rememberPagerState(
            pageCount = { it.itemCount }
        )

        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight.dp)
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
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
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 14.dp)
                    .animateContentSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    when {
                        pagerState.currentPage == iteration -> {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                                    .clip(CircleShape)
                                    .background(DarkRed)
                                    .width(24.dp)
                                    .height(16.dp)
                                    .animateContentSize()
                            )
                        }
                        else -> {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp, vertical = 2.dp)
                                    .clip(CircleShape)
                                    .background(Red)
                                    .size(16.dp)
                                    .animateContentSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCategorySection(title: String, vm: HomeViewModel, movieHomeType: MovieHomeType) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / LocalContext.current.resources.displayMetrics.density
    val cardWidth = screenWidth * 0.65f
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels / LocalContext.current.resources.displayMetrics.density
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
            FeaturedMoviesPager(movies)
        }
        else -> {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                movies?.let { movies ->
                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { it.movieId },
                        contentType = movies.itemContentType { movieHomeType.name }
                    ) { index ->
                        val movie = movies[index]
                        movie?.let { MovieItem(it, cardWidth, cardHeight) }
                    }
                }
            }
        }
        }
    }

}

@Composable
fun MovieItem(movie: MovieDataModel, cardWidth: Float = 150f, cardHeight: Float = 220f) {
    Box(
        modifier = Modifier
            .width(cardWidth.dp)
            .height(cardHeight.dp)
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = MaterialTheme.shapes.medium,
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        BoxVignetting(cardHeight)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
