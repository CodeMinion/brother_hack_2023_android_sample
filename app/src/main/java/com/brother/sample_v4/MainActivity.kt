package com.brother.sample_v4

import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.brother.sdk.lmprinter.*
import com.brother.sdk.lmprinter.setting.PrintImageSettings
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            var selectedModel by remember { mutableStateOf(PrinterModel.QL_810W)}
            var selectedLabel by remember { mutableStateOf(QLPrintSettings.LabelSize.RollW103)}
            var selectedScale by remember { mutableStateOf(PrintImageSettings.ScaleMode.ActualSize)}
            var selectedHalftone by remember { mutableStateOf(PrintImageSettings.Halftone.PatternDither) }

            Column {
                // Show the model selection on screen.
                PrinterSelectionDropdown(
                    onValueSelected = { printerModel ->
                        // Capture the selected printer model so we can use it during print.
                        selectedModel = printerModel
                    }
                )
                // Show the label selection on screen.
                PaperSelectionDropdown(
                    onValueSelected = { labelSize ->
                        // Capture the selected printer model so we can use it during print.
                        selectedLabel = labelSize
                    }
                )
                // Show the scale options on screen.
                ScaleModesSelectionDropdown(
                    onValueSelected = { scaleMode ->
                        // Capture the selected printer model so we can use it during print.
                        selectedScale = scaleMode
                    }
                )
                // Show the halftone selection.
                HalftoneOptionSelectionDropdown(
                    onValueSelected = { halftone ->
                        selectedHalftone = halftone
                    })
                // Show the button for users to print.
                Button(
                    onClick = { printImage(selectedModel, selectedLabel, selectedScale, selectedHalftone) }) {
                    Text("Print Image")
                }
            }

        }
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PrinterSelectionDropdown(
        // Will define a callback to be notified when a user makes a selection.
        onValueSelected: (PrinterModel) -> Unit
    ) {
        // Add the list of printers to provide the user with.
        val options = listOf(PrinterModel.QL_810W, PrinterModel.QL_820NWB, PrinterModel.QL_1110NWB)
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // Create a label to display the heading of this option.
            TextField(
                readOnly = true,
                value = selectedOptionText.name,
                onValueChange = { },
                label = { Text("Select printer model") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                // For each option we create a menu item based on the options for the user to pick from.
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            // Notify the selection.
                            onValueSelected(selectionOption)
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun PaperSelectionDropdown(
        // Will define a callback to be notified when a user makes a selection.
        onValueSelected: (QLPrintSettings.LabelSize) -> Unit
    ) {
        // Add the list of printers to provide the user with.
        val options = listOf(QLPrintSettings.LabelSize.RollW103, QLPrintSettings.LabelSize.RollW62)
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // Create a label to display the heading of this option.
            TextField(
                readOnly = true,
                value = selectedOptionText.name,
                onValueChange = { },
                label = { Text("Select label") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                // For each option we create a menu item based on the options for the user to pick from.
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            // Notify the selection.
                            onValueSelected(selectionOption)
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ScaleModesSelectionDropdown(
        // Will define a callback to be notified when a user makes a selection.
        onValueSelected: (PrintImageSettings.ScaleMode) -> Unit
    ) {
        // Add the list of printers to provide the user with.
        val options = listOf(
            PrintImageSettings.ScaleMode.ActualSize,
            PrintImageSettings.ScaleMode.FitPageAspect,
            PrintImageSettings.ScaleMode.FitPaperAspect
        )
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // Create a label to display the heading of this option.
            TextField(
                readOnly = true,
                value = selectedOptionText.name,
                onValueChange = { },
                label = { Text("Select Scale Mode") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                // For each option we create a menu item based on the options for the user to pick from.
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            // Notify the selection.
                            onValueSelected(selectionOption)
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun HalftoneOptionSelectionDropdown(
        // Will define a callback to be notified when a user makes a selection.
        onValueSelected: (PrintImageSettings.Halftone) -> Unit
    ) {
        // Add the list of printers to provide the user with.
        val options = listOf(
            PrintImageSettings.Halftone.PatternDither,
            PrintImageSettings.Halftone.ErrorDiffusion
        )
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // Create a label to display the heading of this option.
            TextField(
                readOnly = true,
                value = selectedOptionText.name,
                onValueChange = { },
                label = { Text("Select Halftone") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                // For each option we create a menu item based on the options for the user to pick from.
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            onValueSelected(selectionOption)
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }

    /**
     * Prints an image using the printer configuration
     * made by the user.
     */
    fun printImage(
        selectedModel: PrinterModel,
        selectedLabel: QLPrintSettings.LabelSize,
        selectedScale: PrintImageSettings.ScaleMode,
        selectedHalftone: PrintImageSettings.Halftone
    ) {
        //Input your printer's IP address
        val channel: Channel = Channel.newWifiChannel("IP address")
        //Input your printer's macAddress
        // val channel: Channel = Channel.newBluetoothChannel(macAddress, BluetoothAdapter.getDefaultAdapter())
        val result: PrinterDriverGenerateResult = PrinterDriverGenerator.openChannel(channel)
        if (result.error.code !== OpenChannelError.ErrorCode.NoError) {
            Log.e("", "Error - Open Channel: " + result.error.code)
            return
        }
        val printerDriver = result.driver
        // Set the model to the one the user selected.
        val printSettings = QLPrintSettings(selectedModel)
        // Set the label to the one the user selected.
        printSettings.labelSize = selectedLabel
        // Set the scale to the one the user selected.
        printSettings.scaleMode = selectedScale
        // Set the halftone to the one the user selected.
        printSettings.halftone = selectedHalftone
        printSettings.workPath = filesDir.absolutePath

        var file = File.createTempFile("tmp", ".png", externalCacheDir)
        assets.open("sample.png").use { input ->
            file.outputStream().use { output ->
                FileUtils.copy(input, output)
            }
        }

        val printError: PrintError = printerDriver.printImage(file.path, printSettings)
        if (printError.code != PrintError.ErrorCode.NoError) {
            Log.d("", "Error - Print Image: " + printError.code);
        } else {
            Log.d("", "Success - Print Image");
        }
        printerDriver.closeChannel();
    }
}