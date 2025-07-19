package com.example.focusbloom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.focusbloom.data.RetrofitInstance
import com.example.focusbloom.model.TokenRequest
import com.example.focusbloom.model.UserResponse
import com.example.focusbloom.ui.theme.FocusBloomTheme
import com.example.focusbloom.ui.theme.screens.HomeActivity
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

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

        // Initialize Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id)) // <- Add this to strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set UI
        setContent {
            FocusBloomTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SignInScreen(onSignInClick = { signIn() })
                }
            }
        }
    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        signInLauncher.launch(intent)
    }

    private fun sendTokenToBackend(idToken: String) {
        RetrofitInstance.api.sendGoogleToken(TokenRequest(idToken)).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val body = response.body()
                if (response.isSuccessful && body?.success == true) {
                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
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
fun SignInScreen() {
    val context = LocalContext.current
    val lifecycleScope = rememberCoroutineScope()

    val signInClient = remember {
        Identity.getSignInClient(context)
    }

    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("YOUR-WEB-CLIENT-ID.apps.googleusercontent.com") // <--- replace
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            try {
                val credential = signInClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    // ✅ Send token to your backend
                    lifecycleScope.launch {
                        sendTokenToBackend(idToken)
                    }
                } else {
                    Toast.makeText(context, "No ID Token found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Sign-in failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    val request = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    launcher.launch(request)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Sign-in error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }) {
            Text("Sign in with Google")
        }
    }
}