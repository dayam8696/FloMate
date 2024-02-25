package com.example.flomate.ui.fragments.signUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.flomate.R
import com.example.flomate.database.SharedService
import com.example.flomate.databinding.LogInFragmentBinding
import com.example.flomate.ui.activities.DashboardActivity
import com.example.flomate.ui.fragments.BaseFragment
import com.example.flomate.utils.Constants.RC_SIGN_IN
import com.example.flomate.utils.isValidEmail
import com.example.flomate.viewModels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : BaseFragment() {
    private val binding by lazy { LogInFragmentBinding.inflate(layoutInflater) }

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        setupClickListeners()


        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthViewModel.AuthState.Authenticated -> {
                    hideLoader()
                    openIntent()
                }

                is AuthViewModel.AuthState.GoogleSignIn -> {
                    startActivityForResult(state.intent, RC_SIGN_IN)
                }
            }
        }

    }

    private fun setupClickListeners() {
        binding.signUp.setOnClickListener {
            navigateTo(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.btnlogin.setOnClickListener {
            loginWithFirebase()
        }

        binding.loginWithGoogle.setOnClickListener {
            showLoader()
            viewModel.signInWithGoogle()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task: Task<AuthResult> = viewModel.firebaseAuthWithGoogle(
                GoogleSignIn.getSignedInAccountFromIntent(data).result
            )
            task.addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    hideLoader()
                    val userData = firebaseAuth.currentUser
                    SharedService.isLogin = true
                    SharedService.emailId = userData?.email
                    openIntent()
                    Log.d("loooggg", userData?.email ?: "null")
                } else {
                    // Handle authentication failure
                    hideLoader()
                    Toast.makeText(requireContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun loginWithFirebase() {
        val email = binding.EmailAddress.text.toString().trim()
        val password = binding.TextPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.Empty_fields_are_not_allowed))
            return
        }

        if (!email.isValidEmail()) {
            showToast(getString(R.string.Enter_Valid_Email))
            return
        }
        showLoader()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                hideLoader()
                SharedService.isLogin = true
                SharedService.emailId = email
                openIntent()
//                findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                //open new intent
            } else {
                showToast("Please enter correct credentials")
                hideLoader()
            }
        }

    }

    private fun openIntent() {
        val intent = Intent(context, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
        binding.text.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
        binding.text.visibility = View.VISIBLE
    }


}