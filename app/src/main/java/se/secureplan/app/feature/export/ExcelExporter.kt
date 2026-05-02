package se.secureplan.app.feature.export

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class ExcelExporter {

    fun export(bundle: ExportBundle, file: File) {
        val workbook = XSSFWorkbook()
        try {
            createComponentSheet(workbook, bundle)
            createPowerSheet(workbook, bundle)
            createMaterialSheet(workbook, bundle)
            FileOutputStream(file).use { workbook.write(it) }
        } finally {
            workbook.close()
        }
    }

    private fun createComponentSheet(workbook: XSSFWorkbook, bundle: ExportBundle) {
        val sheet = workbook.createSheet("Komponentlista")
        val headerStyle = createHeaderStyle(workbook)

        val headers = listOf(
            "Systemtyp", "Komponent", "Fabrikat", "Artnr", "Antal",
            "Vilström (mA)", "Larmström (mA)", "Spänning (V)", "Total vilström", "Total larmström"
        )
        val headerRow = sheet.createRow(0)
        headers.forEachIndexed { i, h ->
            headerRow.createCell(i).apply { setCellValue(h); cellStyle = headerStyle }
        }

        var rowIdx = 1
        bundle.placements.values.flatten()
            .groupBy { it.productId }
            .forEach { (productId, placements) ->
                val product = bundle.products[productId ?: return@forEach] ?: return@forEach
                val row = sheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(product.category)
                row.createCell(1).setCellValue(product.name)
                row.createCell(2).setCellValue(product.manufacturer)
                row.createCell(3).setCellValue(product.articleNumber)
                row.createCell(4).setCellValue(placements.size.toDouble())
                row.createCell(5).setCellValue(product.powerStandbyMa.toDouble())
                row.createCell(6).setCellValue(product.powerAlarmMa.toDouble())
                row.createCell(7).setCellValue(product.voltageV.toDouble())
                row.createCell(8).setCellValue((product.powerStandbyMa * placements.size).toDouble())
                row.createCell(9).setCellValue((product.powerAlarmMa * placements.size).toDouble())
            }
        (0..9).forEach { sheet.autoSizeColumn(it) }
    }

    private fun createPowerSheet(workbook: XSSFWorkbook, bundle: ExportBundle) {
        val sheet = workbook.createSheet("Strömbudget SSF 130")
        val headerStyle = createHeaderStyle(workbook)
        val boldStyle = createBoldStyle(workbook)

        val headers = listOf("Komponent", "Antal", "mA/st (vila)", "mA/st (larm)", "Summa vila", "Summa larm")
        val headerRow = sheet.createRow(0)
        headers.forEachIndexed { i, h ->
            headerRow.createCell(i).apply { setCellValue(h); cellStyle = headerStyle }
        }

        val result = bundle.ssf130Result
        var rowIdx = 1
        result?.componentLoads?.forEach { comp ->
            val row = sheet.createRow(rowIdx++)
            row.createCell(0).setCellValue(comp.name)
            row.createCell(1).setCellValue(comp.count.toDouble())
            row.createCell(2).setCellValue(comp.standbyMaEach.toDouble())
            row.createCell(3).setCellValue(comp.alarmMaEach.toDouble())
            row.createCell(4).setCellValue((comp.standbyMaEach * comp.count).toDouble())
            row.createCell(5).setCellValue((comp.alarmMaEach * comp.count).toDouble())
        }

        if (result != null) {
            rowIdx++ // blank row
            fun addSummaryRow(label: String, value: String, bold: Boolean = false) {
                val row = sheet.createRow(rowIdx++)
                row.createCell(0).apply {
                    setCellValue(label)
                    if (bold) cellStyle = boldStyle
                }
                row.createCell(1).apply {
                    setCellValue(value)
                    if (bold) cellStyle = boldStyle
                }
            }
            addSummaryRow("Total vilström (mA):", "${result.totalStandbyMa.toInt()}")
            addSummaryRow("Total larmström (mA):", "${result.totalAlarmMa.toInt()}")
            addSummaryRow("Batteri Alt 1 (60h+30min) Ah:", "${"%.1f".format(result.batteryCapacityAh_Alt1)}")
            addSummaryRow("Batteri Alt 2 (12h+3min) Ah:", "${"%.1f".format(result.batteryCapacityAh_Alt2)}")
            addSummaryRow("Rekommenderat batteri Ah:", "${"%.1f".format(result.recommendedBatteryAh)}", bold = true)
            addSummaryRow("Nätdel A:", "${"%.2f".format(result.powerSupplyCurrentA)}")
        }
        (0..5).forEach { sheet.autoSizeColumn(it) }
    }

    private fun createMaterialSheet(workbook: XSSFWorkbook, bundle: ExportBundle) {
        val sheet = workbook.createSheet("Materialförteckning")
        val headerStyle = createHeaderStyle(workbook)

        val headers = listOf(
            "Komponent", "Fabrikat", "Artnr", "Kategori", "Total antal", "Á-pris (SEK)", "Totalt pris (SEK)"
        )
        val headerRow = sheet.createRow(0)
        headers.forEachIndexed { i, h ->
            headerRow.createCell(i).apply { setCellValue(h); cellStyle = headerStyle }
        }

        var rowIdx = 1
        bundle.placements.values.flatten()
            .groupBy { it.productId }
            .forEach { (productId, placements) ->
                val product = bundle.products[productId ?: return@forEach] ?: return@forEach
                val row = sheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(product.name)
                row.createCell(1).setCellValue(product.manufacturer)
                row.createCell(2).setCellValue(product.articleNumber)
                row.createCell(3).setCellValue(product.category)
                row.createCell(4).setCellValue(placements.size.toDouble())
                row.createCell(5).setCellValue(product.price)
                row.createCell(6).setCellValue(product.price * placements.size)
            }
        (0..6).forEach { sheet.autoSizeColumn(it) }
    }

    private fun createHeaderStyle(workbook: XSSFWorkbook): XSSFCellStyle {
        val style = workbook.createCellStyle() as XSSFCellStyle
        val font = workbook.createFont()
        font.bold = true
        font.color = IndexedColors.WHITE.index
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.CORNFLOWER_BLUE.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        return style
    }

    private fun createBoldStyle(workbook: XSSFWorkbook): XSSFCellStyle {
        val style = workbook.createCellStyle() as XSSFCellStyle
        val font = workbook.createFont()
        font.bold = true
        style.setFont(font)
        return style
    }
}
