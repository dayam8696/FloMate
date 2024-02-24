package com.example.caringcatalysts.ui.fragments.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.caringcatalysts.R
import com.example.caringcatalysts.databinding.SignUpFragmentBinding
import com.example.caringcatalysts.ui.fragments.BaseFragment
import com.example.flomate.utils.isValidEmail
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : BaseFragment() {
    private val binding by lazy { SignUpFragmentBinding.inflate(layoutInflater) }
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

    }

    private fun setupClickListeners() {
        binding.alreadyUser.setOnClickListener {
            navigateTo(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.btnsignUp.setOnClickListener {
            signUpWithFirebase()
        }
    }

    private fun signUpWithFirebase() {
        val email = binding.EmailAddress.text.toString().trim()
        val password = binding.TextPassword.text.toString().trim()
        val confirmPass = binding.reTextPassword.text.toString().trim()

        if (!email.isValidEmail()) {
            showToast(getString(R.string.Enter_Valid_Email))
            return
        }

        if (email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            showToast(getString(R.string.Empty_fields_are_not_allowed))
            return
        }

        if (password != confirmPass) {
            showToast(getString(R.string.Password_is_not_matching))
            return
        }
        showLoader()


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                hideLoader()
                findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
            } else {
                hideLoader()
                showToast(it.exception.toString())
            }
        }
    }

    private fun showLoader(){
        binding.progressBar.visibility =View.VISIBLE
        binding.text.visibility =View.GONE
    }

    private fun hideLoader(){
        binding.progressBar.visibility =View.GONE
        binding.text.visibility =View.VISIBLE
    }

}