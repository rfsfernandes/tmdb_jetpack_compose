package xyz.rfsfernandes.tmdbdemo.presentation.ui.details

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import xyz.rfsfernandes.tmdbdemo.R
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieCollectionDataModel
import xyz.rfsfernandes.tmdbdemo.domain.model.MovieDetailsDataModel
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.BoxVignetting
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.ErrorModal
import xyz.rfsfernandes.tmdbdemo.presentation.ui.composables.ThemedButton
import xyz.rfsfernandes.tmdbdemo.presentation.ui.utils.formatDuration

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(movieId, context.resources.configuration.locales[0].language)
    }
    val viewState = viewModel.viewState.collectAsState().value
    val movie = viewState.movieDetails
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var backgroundColor = Color.White.copy(alpha = 0.2f)
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                viewState.isLoading -> {
                    DetailsLoadingScreen()
                }
                viewState.error != null -> {
                    ErrorModal(viewState.error)
                    backgroundColor = Color.Black.copy(alpha = 0.2f)
                }
                movie != null -> {
                    DetailsContent(movie, onBackClick)
                }
            }
        }
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 50.dp)
                .background(
                    color = backgroundColor, shape = MaterialTheme.shapes.extraLarge
                ),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun DetailsContent(movie: MovieDetailsDataModel, onBackClick: () -> Unit) {
    val context = LocalContext.current
    val screenHeight =
        context.resources.displayMetrics.heightPixels / context.resources.displayMetrics.density
    val screenWidth =
        context.resources.displayMetrics.widthPixels / context.resources.displayMetrics.density
    val cardHeight = screenHeight * 0.60f
    val textWidth = screenWidth * 0.70f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        movie.let {
            MoviePosterSection(
                movie = it,
                cardHeight = cardHeight,
                textWidth = textWidth,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.padding(horizontal = 30.dp),
            ) {
                if (!it.imdbId.isNullOrEmpty()) {
                    ThemedButton(
                        modifier = Modifier.weight(0.5f),
                        text = "IMDB",
                        tertiary = true
                    ) {
                        val url = "https://www.imdb.com/title/${it.imdbId}"
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                }
                Spacer(modifier = Modifier.width(if (!it.imdbId.isNullOrEmpty() && !it.homepage.isNullOrEmpty()) 12.dp else 0.dp))
                if (!it.homepage.isNullOrEmpty()) {
                    ThemedButton(
                        modifier = Modifier.weight(0.5f),
                        text = "Webpage",
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, it.homepage.toUri())
                        context.startActivity(intent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Movie Description
            it.overview?.let { overview ->
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

            // Movie Collection
            it.belongsToCollection?.let { collection ->
                Spacer(Modifier.height(30.dp))
                CollectionSection(collection = collection)
            }

            // Languages
            if (it.spokenLanguages.isNotEmpty()) {
                Spacer(Modifier.height(30.dp))
                InfoRow(
                    title = context.getString(R.string.languages),
                    info = it.spokenLanguages.joinToString(", ") { lang -> lang.name.toString() })
            }

            // Production Companies
            if (it.productionCompanies.isNotEmpty()) {
                Spacer(Modifier.height(30.dp))
                InfoRow(
                    title = context.getString(R.string.production_of),
                    info = it.productionCompanies.joinToString(", ") { company -> company.name.toString() })
            }

            Spacer(Modifier.height(50.dp))
        }
    }
}



@Composable
fun DetailsLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
fun MoviePosterSection(
    movie: MovieDetailsDataModel,
    cardHeight: Float,
    textWidth: Float,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        BoxVignetting(cardHeight)
        Column(
            modifier = Modifier.width(textWidth.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = movie.title ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(12.dp))
            val smallInfoString = buildString {
                append(movie.releaseDate?.split("-")?.firstOrNull() ?: "")
                append(" · ")
                append(movie.genres.joinToString(" · ") { it.name })
                append(" · ")
                append(formatDuration(movie.runtime))
            }
            Text(
                text = smallInfoString,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Back",
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.width(8.dp))
                val rateInfo = buildString {
                    append(movie.voteAverage)
                    append(" (${movie.voteCount})")
                }
                Text(
                    text = rateInfo,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun InfoRow(title: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
    ) {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = info, style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun CollectionSection(collection: MovieCollectionDataModel) {
    Text(
        text = LocalContext.current.getString(R.string.collection),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 20.dp)
    )
    Card(modifier = Modifier.padding(24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${collection.posterPath}",
                        contentDescription = collection.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                BoxVignetting(80f, true)
            }
            Spacer(Modifier.width(8.dp))
            collection.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}