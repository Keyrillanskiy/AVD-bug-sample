@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package com.example.avdbugsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.avdbugsample.ui.theme.AVDBugSampleTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AVDBugSampleTheme {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
        ) {
          var size by remember {
            mutableStateOf(LoaderSize.SIZE_500)
          }
          val loader = when (size) {
            LoaderSize.SIZE_200 -> R.drawable.ds_avd_loader_200
            LoaderSize.SIZE_300 -> R.drawable.ds_avd_loader_300
            LoaderSize.SIZE_400 -> R.drawable.ds_avd_loader_400
            LoaderSize.SIZE_500 -> R.drawable.ds_avd_loader_500
            LoaderSize.SIZE_600 -> R.drawable.ds_avd_loader_600
          }.let { AnimatedImageVector.animatedVectorResource(id = it) }

          for (loaderSize in LoaderSize.values()) {
            Button(onClick = { size = loaderSize }) {
              Text(loaderSize.name)
            }
          }

          Loader(
            animatedImageVector = loader,
            size = size.size,
            color = Color.Blue,
          )
        }
      }
    }
  }
}

@Composable
internal fun Loader(
  modifier: Modifier = Modifier,
  animatedImageVector: AnimatedImageVector,
  color: Color,
  size: Dp,
) {
  var atEnd by remember { mutableStateOf(false) }
  val animationPainter = rememberAnimatedVectorPainter(animatedImageVector, atEnd)

  LaunchedEffect(Unit) {
    atEnd = !atEnd
  }

  Image(
    modifier = modifier.size(size),
    painter = animationPainter,
    colorFilter = ColorFilter.tint(color),
    contentDescription = null,
  )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  AVDBugSampleTheme {
  }
}

enum class LoaderSize(internal val size: Dp) {
  SIZE_200(16.dp),
  SIZE_300(24.dp),
  SIZE_400(32.dp),
  SIZE_500(44.dp),
  SIZE_600(56.dp),
}