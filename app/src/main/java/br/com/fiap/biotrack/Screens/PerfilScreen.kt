package br.com.fiap.biotrack.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.biotrack.R


//class MainPerfilScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            PerfilScreen(navController)
//        }
//    }
//}

@Composable
fun PerfilScreen(navController: NavHostController) {
    var notificacoesAtivadas by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.avatar_placeholder),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        
        Text(stringResource(id = R.string.nome_exemplo), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(stringResource(id = R.string.email_exemplo), fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            onClick = { /* Ação para editar perfil */ }) {
            Text(stringResource(id = R.string.edit_perfil))
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.notificacoes), modifier = Modifier.weight(1f))
            Switch(
                colors = SwitchDefaults.colors(
//
                    checkedTrackColor = Color(0xFF4CAF50)),
                checked = notificacoesAtivadas,
                onCheckedChange = { notificacoesAtivadas = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exemplo de Contribuições do usuário (exemplo de avistamentos)
        Text(stringResource(id = R.string.contribuicoes), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            ContribuicaoItem("Arara Azul", "Parque Nacional", "10/03/2025")
            ContribuicaoItem("Tamanduá-bandeira", "Reserva Ecológica", "15/03/2025")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                onClick = { navController.navigate("login") },

            ) {
                Text(text = stringResource(id = R.string.sair))
            }
        }



    }
}

@Composable
fun ContribuicaoItem(nome: String, local: String, data: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(nome, fontWeight = FontWeight.Bold)
            Text(local, color = Color.Gray)
            Text(data, fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPerfilScreen() {
    PerfilScreen(rememberNavController())
}
