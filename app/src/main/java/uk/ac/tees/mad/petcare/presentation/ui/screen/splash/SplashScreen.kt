package uk.ac.tees.mad.petcare.presentation.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo.Column
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import uk.ac.tees.mad.petcare.R

@Composable
fun SplashScreen(
    onGoToLogin: () -> Unit,
    onGoToPetProfile: () -> Unit
) {
    LaunchedEffect(true) {
        delay(1200)
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null) {
            onGoToPetProfile()
        }
        else {
            onGoToLogin()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text("PetCare", style = MaterialTheme.typography.headlineLarge, color = Color.Black, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.paw),
            contentDescription = "pet_care",
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview(showBackground = true, name = "PetCare – Splash Screen")
@Composable
fun PetCareSplashExactPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PetCare",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.paw),
            contentDescription = "pet_care",
            modifier = Modifier.size(60.dp)
        )
    }
}