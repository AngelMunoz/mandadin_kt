package me.tunaxor.apps.mandadin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.tunaxor.apps.mandadin.pages.NotesPage
import me.tunaxor.apps.mandadin.ui.theme.MandadinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDb("${filesDir.path}/mandadin")
        setContent {
            MandadinTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "notes") {
                        composable("notes") { NotesPage() }
                        // TODO: 12/14/21 Add The Category and Category Details pages 
                    }
                }
            }
        }
    }
}
