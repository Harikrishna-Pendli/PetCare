package uk.ac.tees.mad.petcare.presentation.ui.screen.tips

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.presentation.FakeTipsViewModel
import uk.ac.tees.mad.petcare.presentation.ui.components.ProfileHeader
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetTipsViewModel
import uk.ac.tees.mad.petcare.presentation.viewmodel.PetType

@Composable
fun TipsScreen(
    viewModel: PetTipsViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val facts by viewModel.facts.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Trigger API only once
    LaunchedEffect(Unit) {
        viewModel.fetchTips()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(12.dp)
        ) {
            ProfileHeader("Tips")
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                FilterChip(
                    selected = (viewModel.selectedType.collectAsState().value == PetType.DOG),
                    onClick = { viewModel.changeType(PetType.DOG) },
                    label = { Text("Dog") }
                )
                FilterChip(
                    selected = (viewModel.selectedType.collectAsState().value == PetType.CAT),
                    onClick = { viewModel.changeType(PetType.CAT) },
                    label = { Text("Cat") }
                )
            }


            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Failed to load tips.", color = MaterialTheme.colorScheme.error)
                    }
                }

                facts.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No tips available!")
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(facts) { fact ->
                            Surface(
                                tonalElevation = 4.dp,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "🐾 Pet Health Tip",
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = fact.fact)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTipsScreen() {
    MaterialTheme {
        TipsScreen(
//            viewModel = FakeTipsViewModel(),
            onBack = {}
        )
    }
}

