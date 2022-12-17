package com.avangarde.polis.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.avangarde.polis.common.Constants
import com.google.android.material.textfield.TextInputLayout

class RegistrationViewModel : ViewModel() {
    fun validateEmail(text: CharSequence?, emailTI: TextInputLayout): Boolean {
        var emailCondition: Boolean? = null
        text?.let {
            emailTI.run {
                if (it.toString() matches Regex(Constants.EMAIL_PATTERN))
                    isErrorEnabled = false
                else
                    error = "This email isn't correct"
                emailCondition = ! (this.isErrorEnabled)
            }
        }
        return emailCondition!!
    }
    fun validatePassword(text: CharSequence?, passTI: TextInputLayout): Boolean {
        var passCondition: Boolean? = null
        text?.let {
            passTI.run {
                endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                if (it.length <8)
                    error = "Password must be at least 8 characters long"
                else if (!(it matches Regex(".*[a-z].*")))
                    error = "Password must contain at least one lowercase letter"
                else if (!(it matches Regex(".*[A-Z].*")))
                    error = "Password must contain at least one uppercase letter"
                else if (!(it matches Regex(".*\\d.*")))
                    error = "Password must contain at least one digit"
                else if (!(it matches Regex(".*[-+_!@#\$%^&*.,?].*")))
                    error = "Password must contain at least one symbol"
                else if (it.length> 30)
                    error = "Password can't be longer than 30 characters"
                else
                    isErrorEnabled = false
                passCondition = !(this.isErrorEnabled)
            }
        }
        return passCondition!!
    }
}
