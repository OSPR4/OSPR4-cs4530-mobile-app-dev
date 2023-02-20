package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val submitButton = findViewById<Button>(R.id.submitButton)
        val buttonTakeProfilePicture = findViewById<Button>(R.id.buttonTakeProfilePicture)

        if (savedInstanceState != null){
            val imageThumbnail = findViewById<ImageView>(R.id.imageThumbnail)
            val byteArray = savedInstanceState.getByteArray("thumbnail")
            if (byteArray != null) {
                val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                imageThumbnail!!.setImageBitmap(image)
            }

            val firstName = savedInstanceState.getString("firstName")
            val middleName = savedInstanceState.getString("middleName")
            val lastName = savedInstanceState.getString("lastName")

            findViewById<EditText>(R.id.editTextFirstName).setText(firstName)
            findViewById<EditText>(R.id.editTextMiddleName).setText(middleName)
            findViewById<EditText>(R.id.editTextLastName).setText(lastName)


        }
        submitButton.setOnClickListener {
            val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString()
            val middleName = findViewById<EditText>(R.id.editTextMiddleName).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString()

            if (firstName.isEmpty() || lastName.isEmpty()){
                Snackbar.make(it, "Please Enter First and Last Name", Snackbar.LENGTH_SHORT).show()
            }else{
                val user = "$firstName $lastName"

                val intent = Intent(this, LoggedInActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
                findViewById<EditText>(R.id.editTextFirstName).setText("")
                findViewById<EditText>(R.id.editTextMiddleName).setText("")
                findViewById<EditText>(R.id.editTextLastName).setText("")
                findViewById<ImageView>(R.id.imageThumbnail).setImageDrawable(null)
            }
        }

        buttonTakeProfilePicture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try{
                takePicture.launch(intent)
            }
            catch(ex:ActivityNotFoundException){
                Snackbar.make(it, "Camera functionality unavailable", Snackbar.LENGTH_SHORT).show()
            }
        }

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private var takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val imageThumbnail = findViewById<ImageView>(R.id.imageThumbnail)
            val data = it.data!!.getParcelableExtra("data", Bitmap::class.java)
            imageThumbnail!!.setImageBitmap(data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val imageThumbnail = findViewById<ImageView>(R.id.imageThumbnail)
        val bitmapDrawable = imageThumbnail.drawable

        val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString()
        val middleName = findViewById<EditText>(R.id.editTextMiddleName).text.toString()
        val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString()


        if (bitmapDrawable != null){
            val image = (bitmapDrawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            outState.putByteArray("thumbnail", byteArray)// won't work if app is paused or restored
        }

        outState.putString("firstName", firstName)
        outState.putString("middleName", middleName)
        outState.putString("lastName", lastName)


    }

    override fun onStop() {
        super.onStop()
        onSaveInstanceState(Bundle())

    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}