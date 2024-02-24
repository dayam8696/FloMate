package com.example.flomate.viewModels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flomate.ApplicationClass
import com.example.flomate.utils.Constants.SERVER_CLIENT_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVER_CLIENT_ID)
            .requestEmail()
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(ApplicationClass.instance!!.baseContext, gso)
    }


    fun signInWithGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            _authState.postValue(AuthState.GoogleSignIn(signInIntent))
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): Task<AuthResult> {
        val idToken =
            account?.idToken ?: throw IllegalArgumentException("Account or ID token is null")

        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }


    sealed class AuthState {
        object Authenticated : AuthState()
        data class GoogleSignIn(val intent: Intent) : AuthState()
    }
}
