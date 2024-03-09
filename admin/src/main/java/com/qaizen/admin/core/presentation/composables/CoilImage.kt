package com.qaizen.admin.core.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.qaizen.admin.utils.openImage

/**
 * A Composable function that displays an image from a URL.
 *
 * @param modifier The modifier to be applied to the image.
 * @param imageUrl The URL of the image to be displayed.
 * @param applyCircleShape Whether to apply a circular shape to the image.
 * @param showOpenImageButton Whether to show a button to open the image in a new window.
 * @param openImageButtonContent The content of the open image button.
 * @param errorContent The content to display if the image fails to load.
 * @param loadingContent The content to display while the image is loading.
 * @param emptyContent The content to display if the image is empty.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://picsum.photos/200",
    applyCircleShape: Boolean = false,
    showOpenImageButton: Boolean = false,
    openImageButtonContent: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Outlined.OpenInFull,
            contentDescription = "Expand image",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(4.dp)
        )
    },
    errorContent: @Composable (() -> Unit)? = null,
    loadingContent: @Composable (() -> Unit)? = null,
    emptyContent: @Composable (() -> Unit)? = null,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        val painter = rememberAsyncImagePainter(
            model = if (applyCircleShape) {
                ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .transformations(CircleCropTransformation()).build()
            } else {
                ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .transformations(
                        RoundedCornersTransformation(
                            topLeft = 26f,
                            topRight = 26f,
                            bottomLeft = 26f,
                            bottomRight = 26f
                        )
                    )
                    .build()
            }
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            if (loadingContent == null) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                loadingContent()
            }
        }


        if (painter.state is AsyncImagePainter.State.Empty) {
            if (emptyContent == null) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = "Image URL has error",
                    modifier = Modifier
                        .alpha(0.5f)
                        .then(modifier),
                )
            } else {
                emptyContent()
            }
        }

        if (painter.state is AsyncImagePainter.State.Error) {
            if (errorContent == null) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = "Image URL has error",
                    modifier = Modifier
                        .alpha(0.5f)
                        .then(modifier),
                )
            } else {
                errorContent()
            }

        }

        Image(
            painter = painter,
            contentDescription = "Avatar image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (showOpenImageButton)
            Card(
                onClick = {
                    openImage(
                        context = context,
                        uri = imageUrl.toUri(),
                        onError = {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    )
                }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                openImageButtonContent()
            }

    }
}