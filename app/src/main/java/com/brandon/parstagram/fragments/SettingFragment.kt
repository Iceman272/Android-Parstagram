package com.brandon.parstagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.brandon.parstagram.R
import com.parse.ParseUser


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.nameSubmitButton).setOnClickListener{
            val user = ParseUser.getCurrentUser()
            val username = view.findViewById<EditText>(R.id.et_new_name).text.toString()

            user.setUsername(username)

            user.saveInBackground()

        }

        view.findViewById<Button>(R.id.passwordSubmitButton).setOnClickListener{
            val user = ParseUser.getCurrentUser()
            val password = view.findViewById<EditText>(R.id.et_new_password).text.toString()

            user.setPassword(password)

            user.saveInBackground()
        }
    }

}