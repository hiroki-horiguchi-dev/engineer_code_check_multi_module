package com.example.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    name: String,
    avatarUrl: String,
    innerPadding: PaddingValues,
    modifier: Modifier,
) {
    Column(
        modifier =
            Modifier
                .padding(innerPadding)
                .padding(16.dp),
    ) {
        GlideImage(
            model = avatarUrl,
            contentDescription = "Company Image for Recruitment",
            modifier =
                Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        PairText(Pair(stringResource(R.string.title), name))
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun PairText(title: Pair<String, String>) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        Text(
            text = title.first,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = DividerDefaults.color,
            modifier = Modifier.padding(vertical = 1.dp),
        )
        Text(
            text = title.second,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
