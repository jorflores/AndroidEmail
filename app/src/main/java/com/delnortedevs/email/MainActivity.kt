package com.delnortedevs.email


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.delnortedevs.email.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    sendEmail()
                }
            }
        }
    }

    // Titulo
    // TO
    // BODY

     private fun sendEmail() {
        try {

            val props = System.getProperties()

            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                        return javax.mail.PasswordAuthentication(
                            Credentials.EMAIL,
                            Credentials.PASSWORD
                        )
                    }
                })

            //Creating MimeMessage object
            val mm = MimeMessage(session)
            // Destination Email
            val emailTo = "xxxxxx@xxxxx.xxx"

            //Adding receiver
            mm.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(emailTo)
            )
            //Adding subject
            mm.subject = "Prueba de envio de correo"
            //Adding message
            mm.setText("Esto es una prueba.")

            //Sending email
            Transport.send(mm)
        }
        catch (e: Exception) {
            Log.d("EMAIL",e.toString())
        }
    }
}