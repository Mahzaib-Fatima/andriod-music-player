package com.meh.musicPlayer//package com.meh.musicPlayer

//Not Used Now

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.meh.musicPlayer.databinding.ActivityFeedbackBinding
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class FeedbackActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Feedback"
        binding.sendFA.setOnClickListener {
            val feedbackMsg =
                binding.feedbackMsgFA.text.toString() + "\n" + binding.emailFA.text.toString()
            val subject = binding.topicFA.text.toString()
            val userName = "mahzaibfatima001@gmail.com"
            val pass = "mehro"
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (feedbackMsg.isNotEmpty() && subject.isNotEmpty() && (cm.activeNetworkInfo?.isConnectedOrConnecting == true)) {
                Thread {
                    try {
                        val properties = Properties()
                        properties["mail.smtp.auth"] = "true"
                        properties["mail.smtp.starttls.enable"] = "true"
                        properties["mail.smtp.host"] = "smtp.gmail.com"
                        properties["mail.smtp.port"] = "587"
                        val session = Session.getInstance(properties, object : Authenticator() {
                            override fun getPasswordAuthentication(): PasswordAuthentication {
                                return PasswordAuthentication(userName, pass)
                            }
                        })
                        val mail = MimeMessage(session)
                        mail.subject = subject
                        mail.setText(feedbackMsg)
                        mail.setFrom(InternetAddress(userName))
                        mail.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(userName)
                        )
                        Transport.send(mail)
                        runOnUiThread {
                            Toast.makeText(this, "Thanks For Feedback!!", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    } catch (e: Exception) {
                        // Error - back to UI thread
                        runOnUiThread {
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }.start()
            }
        }
    }
}
//                    }catch (e: Exception){Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()}
//                }.start()
//                Toast.makeText(this, "Thanks For Feedback!!", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//            else Toast.makeText(this, "Went Something Wrong!!", Toast.LENGTH_SHORT).show()
//        }
