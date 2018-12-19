package com.example.user.android

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NonNull
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableButtons()

        profileEditButton!!.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_profileFragment_to_profileEditFragment)
        )
        profileLogoutButton!!.setOnClickListener(logoutButtonListener)

        val userEmail = "dasdasd@mail.ru"
       // val userEmail = FirebaseAuth.getInstance().getCurrentUser()!!.getEmail()
        profileEmailView.setText(userEmail)

//        val user = FirebaseAuth.getInstance().getCurrentUser()
//        val reference = FirebaseStorage.getInstance().getReference().child(user!!.getUid())
//        reference.getBytes(java.lang.Long.MAX_VALUE)
//                .addOnSuccessListener(successImageLoadListener)
//                .addOnFailureListener(failureImageLoadListener)
//
//        val dbReference = FirebaseDatabase.getInstance().getReference()
//                .child("userProfiles").child(user!!.getUid())
//        dbReference.addValueEventListener(profileEventListener)
    }

    private val logoutButtonListener = View.OnClickListener {
        (activity as NavigationDrawerActivity).cleanArticlesCache()
        FirebaseAuth.getInstance().signOut()
        (activity as NavigationDrawerActivity).startAuthActivity()
    }

    private val successImageLoadListener = object : OnSuccessListener<ByteArray>{
        override fun onSuccess(bytes: ByteArray) {
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            profileImageView!!.setImageBitmap(bmp)
            enableButtons()
        }
    }

    private val failureImageLoadListener = object : OnFailureListener{
        override fun onFailure(exception: Exception) {
            Log.d("ProfileImage", exception.message)
            enableButtons()
        }
    }

    private val profileEventListener = object : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val userProfile = dataSnapshot.getValue(UserProfile::class.java)
            if (userProfile != null) {
                profileFullNameView!!.setText(userProfile!!.fullName)
                profilePhoneNumberView!!.setText(userProfile!!.phone)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.d("ProfileImage", databaseError.getMessage())
            Toast.makeText(context, R.string.profile_show_error_message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun disableButtons() {
        profileEditButton!!.setEnabled(false)
        profileLogoutButton!!.setEnabled(false)
        progressBar!!.visibility = ProgressBar.VISIBLE
    }

    private fun enableButtons() {
        profileEditButton!!.setEnabled(true)
        profileLogoutButton!!.setEnabled(true)
        progressBar!!.visibility = ProgressBar.INVISIBLE
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
