package uk.ac.tees.mad.petcare.presentation.ui.screen.tips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.petcare.presentation.viewmodel.DogFactsViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TipsScreen(
    viewModel: DogFactsViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val facts by viewModel.facts.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Trigger API only once
    LaunchedEffect(Unit) {
        viewModel.fetchDogFacts()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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
                    Text("Failed to load tips.", color = Color.Red)
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


@Composable
fun TipsScreenPreview(
    facts: List<String> = listOf(
        "Daily walks improve your dog’s digestion.",
        "Regular vet check-ups help spot issues early.",
        "Hydration is essential for healthy joints."
    ),
    loading: Boolean = false,
    error: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
                    Text("Failed to load tips.", color = Color.Red)
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
                                Text(text = fact)
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
fun TipsScreenPreview_UI() {
    TipsScreenPreview()
}
