package com.evothings.mhand.presentation.feature.profile.ui.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.Referal
import com.evothings.domain.feature.profile.model.ReferalInfo
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.address.ui.components.list.AddressList
import com.evothings.mhand.presentation.feature.profile.ui.ProfileCallback
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Block
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.BlockCashback
import com.evothings.mhand.presentation.feature.profile.ui.state.data.components.Data
import com.evothings.mhand.presentation.feature.profile.ui.state.data.bottomsheet.DeleteAccount
import com.evothings.mhand.presentation.feature.profile.ui.state.data.bottomsheet.RefactorProfile
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.modifier.modalBottomSheetPadding
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDataScreen(
    modifier: Modifier = Modifier,
    profile: Profile,
    referalInfo: ReferalInfo,
    addresses: List<Address>,
    callback: ProfileCallback
) {
    val scope = rememberCoroutineScope()

    val fullName = remember(profile) { "${profile.firstName} ${profile.lastName}" }

    var editProfileBottomSheetExpanded by remember { mutableStateOf(false) }
    val editProfileBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var deleteProfileBottomSheetExpanded by remember { mutableStateOf(false) }

    var selectedAddress by remember {
        val primaryAddressIndex = addresses
            .indexOfFirst { it.main }
            .coerceAtLeast(0)

        mutableIntStateOf(primaryAddressIndex)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.profile_onboarding_banner),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.paddings.extraLarge)
                    .clip(shape = MegahandShapes.large)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            Data(
                nameAndSurName = fullName,
                birthday = profile.birthday,
                city = profile.city,
                phoneNumber = profile.phoneNumber,
                email = profile.email,
                onEditProfile = { editProfileBottomSheetExpanded = true }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
            Referral(
                cashback = profile.cashback,
                referalCode = profile.referalCode,
                referalProfit = referalInfo.balance,
                referals = referalInfo.referalsList,
                list = addresses,
                selected = selectedAddress,
                onAddressChange = callback::reload,
                openAddressMap = callback::openAddressMap,
                onSelect = { index -> selectedAddress = index }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.mega))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row() {
                    Button(
                        text = stringResource(R.string.logout),
                        textColor = colorScheme.secondary,
                        onClick = callback::logout
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.paddings.extraLarge))
                    Button(
                        text = stringResource(R.string.delete_account),
                        textColor = colorScheme.secondary,
                        onClick = { deleteProfileBottomSheetExpanded = true }
                    )

                }
            }
        }
    }
    if (editProfileBottomSheetExpanded) {
        ModalBottomSheet(
            sheetState = editProfileBottomSheetState,
            properties = ModalBottomSheetDefaults.properties(shouldDismissOnBackPress = false),
            onDismissRequest = { editProfileBottomSheetExpanded = false },
        ) {

            fun hide() {
                scope.launch {
                    editProfileBottomSheetState.hide()
                    editProfileBottomSheetExpanded = false
                }
            }

            RefactorProfile(
                modifier = Modifier,
                model = profile,
                onCancel = { hide() },
                onSaveChanges = { profile, changePhone, phone ->
                    hide()
                    callback.updateProfile(profile)
                    if (changePhone) {
                        callback.confirmPhone(phone)
                    }
                },
            )
        }
    }

    if (deleteProfileBottomSheetExpanded) {
        MhandModalBottomSheet(
            onDismissRequest = { deleteProfileBottomSheetExpanded = false },
        ) {
            DeleteAccount(
                onCancel = { deleteProfileBottomSheetExpanded = false },
                onDelete = callback::deleteAccount
            )
        }
    }
}

@Composable
fun Referral(
    modifier: Modifier = Modifier,
    cashback: Int,
    referalCode: String,
    referalProfit: Int,
    referals: List<Referal>,
    list: List<Address>,
    selected: Int,
    onSelect: (Int) -> Unit,
    onAddressChange: () -> Unit,
    openAddressMap: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        BlockCashback(
            cashback = cashback
        )
        Block(
            text = stringResource(R.string.shipping_address),
            content = {
                AddressList(
                    addresses = list,
                    selected = selected,
                    isProfile = true,
                    onSelect = onSelect,
                    onAddressChange = onAddressChange,
                    openAddressMap = openAddressMap
                )
            }
        )
        Block(
            text = referalCode,
            visiblePrize = true,
            visibleText = true,
            content = {
                AnimatedContent(
                    profit = referalProfit,
                    referals = referals
                )
            }
        )

    }
}

@Composable
private fun AnimatedContent(
    profit: Int,
    referals: List<Referal>
) {
    Text(
        text = stringResource(id = R.string.referal_instruction),
        style = typography.bodyMedium,
        color = colorScheme.secondary.copy(0.6f)
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.extraLarge)
    )
    ReferalProfitCard(
        profit = profit
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.extraLarge)
    )
    Text(
        text = stringResource(R.string.referals_count, referals.size),
        style = typography.labelLarge,
        color = colorScheme.secondary
    )
    Spacer(
        modifier = Modifier
            .height(MaterialTheme.spacers.large)
    )
    Column(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        repeat(referals.size) {
            val item = remember { referals[it] }

            ReferalItem(
                name = item.name,
                cashback = item.cashback,
                joinedSince = item.joinDate
            )
        }
    }
}

@Composable
private fun ReferalProfitCard(profit: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.inverseSurface.copy(0.1f),
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacers.medium)
        ) {
            Text(
                text = stringResource(R.string.referal_profit),
                style = typography.bodyLarge,
                color = colorScheme.secondary
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.small)
            )
            Text(
                text = "${profit.splitHundreds(NumberSeparator.SPACE)} ₽",
                style = typography.headlineSmall,
                color = colorScheme.secondary
            )
        }
    }
}

@Composable
private fun ReferalItem(
    name: String,
    cashback: Int,
    joinedSince: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = name,
                style = typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .height(MaterialTheme.spacers.tiny)
            )
            Text(
                text = stringResource(R.string.cashback) + ": $cashback%",
                style = typography.bodyMedium,
                color = colorScheme.secondary.copy(0.4f)
            )
        }
        Text(
            text = joinedSince,
            style = typography.bodyMedium,
            color = colorScheme.secondary.copy(0.4f)
        )
    }
}