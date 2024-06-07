package ua.gov.diia.ui_base.components.atom.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.gov.diia.core.models.common_compose.atm.button.BtnPrimaryLargeAtm
import ua.gov.diia.core.models.common_compose.general.ButtonStates
import ua.gov.diia.ui_base.components.infrastructure.DataActionWrapper
import ua.gov.diia.ui_base.components.infrastructure.event.UIAction
import ua.gov.diia.ui_base.components.infrastructure.event.UIActionKeysCompose
import ua.gov.diia.ui_base.components.infrastructure.state.UIState
import ua.gov.diia.ui_base.components.infrastructure.utils.resource.UiText
import ua.gov.diia.ui_base.components.infrastructure.utils.resource.toDynamicString
import ua.gov.diia.ui_base.components.subatomic.loader.LoaderCircularEclipse23Subatomic
import ua.gov.diia.ui_base.components.theme.Black
import ua.gov.diia.ui_base.components.theme.BlackAlpha10
import ua.gov.diia.ui_base.components.theme.DiiaTextStyle
import ua.gov.diia.ui_base.components.theme.White
import ua.gov.diia.ui_base.util.toDataActionWrapper


@Composable
fun BtnPrimaryLargeAtm(
    modifier: Modifier = Modifier,
    data: BtnPrimaryLargeAtmData,
    progressIndicator: Pair<String, Boolean> = Pair("", false),
    onUIAction: (UIAction) -> Unit
) {
    Button(
        modifier = modifier
            .padding(top = 16.dp)
            .defaultMinSize(minWidth = 160.dp)
            .testTag(data.componentId?.asString() ?: ""),
        colors = ButtonDefaults.buttonColors(
            containerColor = Black,
            disabledContainerColor = BlackAlpha10
        ),
        enabled = data.interactionState == UIState.Interaction.Enabled,
        onClick = {
            onUIAction(
                UIAction(
                    actionKey = data.actionKey,
                    data = data.id,
                    action = data.action
                )
            )
        }
    ) {
        AnimatedVisibility(visible = data.id == progressIndicator.first && progressIndicator.second) {
            Row {
                LoaderCircularEclipse23Subatomic(modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = data.title.asString(),
            color = White,
            style = DiiaTextStyle.h4ExtraSmallHeading
        )
    }
}

data class BtnPrimaryLargeAtmData(
    val actionKey: String = UIActionKeysCompose.BUTTON_REGULAR,
    val id: String,
    val componentId: UiText? = null,
    val title: UiText,
    val action: DataActionWrapper? = null,
    val interactionState: UIState.Interaction
)

fun BtnPrimaryLargeAtm.toUIModel(
    id: String = "",
    componentIdExternal: UiText? = null
): BtnPrimaryLargeAtmData {
    return BtnPrimaryLargeAtmData(
        title = label.toDynamicString(),
        id = id,
        componentId = componentIdExternal ?: componentId.orEmpty().toDynamicString(),
        interactionState = state?.let {
            when (state) {
                ButtonStates.enabled -> UIState.Interaction.Enabled
                ButtonStates.disabled -> UIState.Interaction.Disabled
                ButtonStates.invisible -> UIState.Interaction.Disabled
                else -> UIState.Interaction.Enabled
            }
        } ?: UIState.Interaction.Enabled,
        action = action?.toDataActionWrapper()
    )
}

@Composable
@Preview
fun BtnPrimaryLargeAtmPreview_EnabledState() {
    val buttonStateEnabled = BtnPrimaryLargeAtmData(
        title = "Label".toDynamicString(),
        id = "",
        interactionState = UIState.Interaction.Enabled
    )
    BtnPrimaryLargeAtm(data = buttonStateEnabled) {
    }
}

@Composable
@Preview
fun BtnPrimaryLargeAtmPreview_DisabledState() {
    val buttonStateDisabled = BtnPrimaryLargeAtmData(
        title = "Label".toDynamicString(),
        id = "",
        interactionState = UIState.Interaction.Disabled
    )
    BtnPrimaryLargeAtm(data = buttonStateDisabled) {
    }
}

@Composable
@Preview
fun BtnPrimaryLargeAtmPreview_LoadingState() {
    val buttonStateLoading = BtnPrimaryLargeAtmData(
        title = "Label".toDynamicString(),
        id = "id",
        interactionState = UIState.Interaction.Enabled
    )
    BtnPrimaryLargeAtm(
        data = buttonStateLoading,
        progressIndicator = Pair("id", true)
    ) {
    }
}
