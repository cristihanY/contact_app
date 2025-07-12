package com.example.dbapp.ui.cart


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.dbapp.ui.client.component.TitleForm
import com.example.dbapp.ui.client.component.TopBarClientComponent
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun PayOptionScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: CartViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.initCart(context)
    }
    val cart by viewModel.cart.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else {
        if (cart.id != 0L) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TopBarClientComponent(
                    navController = navController,
                    onSave = {   },
                    textOnClick = "Aceptar",
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                )

                TitleForm(titlePrimary = "PEN ${cart.summary.total}", titleSecondary = "Aceptar dinero en efectivo")

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Monto recibido",
                            style = MaterialTheme.typography.titleMedium
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        val amounts = generateSuggestedAmounts(cart.summary.total)

                        amounts.forEach { amount ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable { /*onMontoSelected(monto)*/ },
                                shape = RoundedCornerShape(12.dp),
                                tonalElevation = 4.dp,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = amount,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                        var customAmount by remember { mutableStateOf("") }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = customAmount,
                            onValueChange = { value ->
                                if (value.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                                    customAmount = value
                                }
                            },
                            label = { Text("Otro monto") },
                            placeholder = { Text("Ingrese monto manual") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                        )

                    }
                }
            }
        }
    }
}

fun generateSuggestedAmounts(
    total: BigDecimal,
    count: Int = 3
): List<String> {
    val suggestions = mutableListOf<BigDecimal>()
    var next = total.setScale(0, RoundingMode.UP)
    val remainder = next.remainder(BigDecimal(5))
    if (remainder != BigDecimal.ZERO) {
        next += BigDecimal(5) - remainder
    }

    while (suggestions.size < count) {
        suggestions.add(next)
        next += BigDecimal(5)
    }

    return suggestions.map { "PEN %.2f".format(it) }
}

