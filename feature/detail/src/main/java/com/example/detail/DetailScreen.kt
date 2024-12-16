package com.example.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    name: String,
    innerPadding: PaddingValues,
    modifier: Modifier,
) {
    Column(
        modifier =
            Modifier
                .padding(innerPadding),
    ) {
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
