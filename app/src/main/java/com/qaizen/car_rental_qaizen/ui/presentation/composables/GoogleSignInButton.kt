package com.qaizen.car_rental_qaizen.ui.presentation.composables
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qaizen.car_rental_qaizen.R

@Composable
fun GoogleSignInButton(
    modifier: Modifier,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.animateContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_g_logo),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google", fontSize = 18.sp)
            if (isLoading) Spacer(modifier = Modifier.width(16.dp))
            if (isLoading) CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleSignInButtonPreview() {
    GoogleSignInButton(
        modifier = Modifier,
        isLoading = false,
        onClick = {}
    )
}