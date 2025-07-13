import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.focusbloom.R // ✅ THIS is your app's R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    totalHours: Int,
    totalTasksCompleted: Int,
    totalMoney: Double
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar with darker background
        TopAppBar(
            title = {
                Text(
                    text = "Your Profile",
                    color = Color.White
                )
            },
            modifier = Modifier.background(Color(0xFF0D47A1))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color(0xFF37474F), // Dark, elegant border
                        shape = CircleShape
                    )
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar), // ✅ this assumes avatar.png is in res/drawable
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize()
                        .clip(CircleShape),

                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = userName,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF263238),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Stats Row with borders
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color(0xFF37474F)), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                StatBox(
                    label = "Hours",
                    value = "$totalHours h",
                    modifier = Modifier.weight(1f)
                )
                DividerBox()
                StatBox(
                    label = "Tasks",
                    value = "$totalTasksCompleted",
                    modifier = Modifier.weight(1f)
                )
                DividerBox()
                StatBoxWithIcon(
                    label = "Money",
                    value = "$${"%.2f".format(totalMoney)}",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            SummaryChart("Your Week in Summary")
            SummaryChart("Your Month in Summary")
            SummaryChart("Your Year in Summary")


            // Chart Placeholder
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color(0xFFF5F5F5))
//                    .border(
//                        BorderStroke(1.dp, Color.LightGray),
//                        shape = RoundedCornerShape(12.dp)
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    "Chart will be here",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//            }
        }
    }
}

@Composable
fun StatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 12.dp)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF37474F)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )
    }
}

@Composable
fun StatBoxWithIcon(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 12.dp)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AttachMoney,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF37474F)
            )
        }
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50)
        )
    }
}

@Composable
fun DividerBox() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Color(0xFF37474F))
    )
}

@Composable
fun SummaryChart(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF263238)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F5))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Chart will be here",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}