package com.example.focusbloom

import RetrofitInstance
import TokenRequest
import UserResponse
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.focusbloom.ui.theme.FocusBloomTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    // Handle result of Google Sign-In
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                sendTokenToBackend(idToken)
            } else {
                showToast("ID token is null")
            }
        } catch (e: ApiException) {
            showToast("Sign-in failed: ${e.statusCode}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id)) // in strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Show Sign-In UI
        setContent {
            FocusBloomTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SignInScreen(onSignInClick = { signIn() })
                }
            }
        }
    }

    // Launch Google Sign-In
    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        signInLauncher.launch(intent)
    }

    // Send Google token to backend
    private fun sendTokenToBackend(idToken: String) {
        RetrofitInstance.api.sendGoogleToken(TokenRequest(idToken)).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val body = response.body()
                if (response.isSuccessful && body?.success == true) {
                    // ✅ Move to MainActivity after login success
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Authentication failed. Try again.")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showToast("Network error: ${t.localizedMessage}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SignInScreen(onSignInClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
        ) {
            Text("Sign in with Google")
        }
    }
}