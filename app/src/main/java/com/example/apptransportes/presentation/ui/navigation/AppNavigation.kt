package com.example.apptransportes.presentation.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apptransportes.R
import com.example.apptransportes.presentation.ui.screens.auth.LoginScreen
import com.example.apptransportes.presentation.ui.screens.home.HomeScreen
import com.example.apptransportes.presentation.ui.screens.loading.LoadingScreen
import com.example.apptransportes.presentation.ui.screens.register.RegisterScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentStack = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        NavigationDataClass(Routes.HomeScreen.route, Icons.Default.Home, "Configuración Inicial"),
        NavigationDataClass(Routes.RegisterScreen.route, Icons.Default.AppRegistration, "Registro de Personal"),
        NavigationDataClass(Routes.RegisterScreen.route, Icons.Default.ReceiptLong, "Reporte de Personal")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                drawerItems = drawerItems,
                menuState = drawerState,
                currentStack = currentStack,
                scope = scope,
                navController = navController
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                when (currentStack) {
                    Routes.HomeScreen.route -> AppTopBar(
                        title = "Configuración Inicial",
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )

                    Routes.RegisterScreen.route -> AppTopBar(
                        title = "Registro de Personal",
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
            },
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.LoginScreen.route) {
            LoginScreen {
                navController.navigate(Routes.LoadingScreen.route) {
                    popUpTo(Routes.LoginScreen.route) { inclusive = true }
                }
            }
        }
        composable(Routes.LoadingScreen.route) {
            LoadingScreen {
                navController.navigate(Routes.HomeScreen.route) {
                    popUpTo(Routes.LoadingScreen.route) { inclusive = true }
                }
            }
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen()
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen()
        }
    }
}

@Composable
private fun AppDrawer(
    drawerItems: List<NavigationDataClass>,
    menuState: DrawerState,
    currentStack: String?,
    scope: CoroutineScope,
    navController: NavHostController
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_app),
                    contentDescription = "Logo Principal de Empresa",
                    modifier = Modifier
                        .padding(20.dp)
                        .size(70.dp)
                )
                Text("Menú de Navegación", modifier = Modifier.padding(16.dp))
                IconButton(onClick = { scope.launch { menuState.close() } }) {
                    Icon(imageVector = Icons.Default.MenuOpen, contentDescription = "Cerrar menú")
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Column {
                    Text("Bienvenid@", modifier = Modifier.padding(horizontal = 16.dp))
                    Text("Marlon Alva", modifier = Modifier.padding(horizontal = 16.dp))
                }
                
                Spacer(Modifier.height(30.dp))

                Column {
                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentStack == item.route,
                            onClick = {
                                scope.launch { menuState.close() }
                                if (currentStack != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Routes.HomeScreen.route) { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                    }

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.PowerSettingsNew, contentDescription = "Cerrar Sesión") },
                        label = { Text("Cerrar Sesión") },
                        selected = false,
                        onClick = {
                            scope.launch { menuState.close() }
                            navController.navigate(Routes.LoginScreen.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
                Spacer(Modifier.weight(1f))
                Column(Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Transporte de Personal", modifier = Modifier.padding(horizontal = 16.dp))
                    Text("Marlon Alva v 1.0.0", modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(title: String, onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú"
                )
            }
        }
    )
}
