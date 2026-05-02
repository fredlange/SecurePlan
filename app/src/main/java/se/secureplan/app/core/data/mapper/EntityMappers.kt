package se.secureplan.app.core.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.secureplan.app.core.data.local.entity.*
import se.secureplan.app.core.domain.model.*

private val gson = Gson()

// ─── Project ────────────────────────────────────────────────────────────────
fun ProjectEntity.toDomain() = Project(
    id = id, name = name, clientName = clientName, address = address,
    description = description,
    systemCategory = SystemCategory.valueOf(systemCategory),
    status = ProjectStatus.valueOf(status),
    createdAt = createdAt, updatedAt = updatedAt,
    installerName = installerName, installerCompany = installerCompany,
    installerEmail = installerEmail, installerPhone = installerPhone,
    coverImageUri = coverImageUri
)

fun Project.toEntity() = ProjectEntity(
    id = id, name = name, clientName = clientName, address = address,
    description = description, systemCategory = systemCategory.name,
    status = status.name, createdAt = createdAt, updatedAt = updatedAt,
    installerName = installerName, installerCompany = installerCompany,
    installerEmail = installerEmail, installerPhone = installerPhone,
    coverImageUri = coverImageUri
)

// ─── Drawing ─────────────────────────────────────────────────────────────────
fun DrawingEntity.toDomain() = Drawing(
    id = id, projectId = projectId, name = name, floor = floor,
    backgroundUri = backgroundUri, backgroundPageIndex = backgroundPageIndex,
    scaleMetersPerUnit = scaleMetersPerUnit, createdAt = createdAt, updatedAt = updatedAt,
    width = width, height = height
)

fun Drawing.toEntity() = DrawingEntity(
    id = id, projectId = projectId, name = name, floor = floor,
    backgroundUri = backgroundUri, backgroundPageIndex = backgroundPageIndex,
    scaleMetersPerUnit = scaleMetersPerUnit, createdAt = createdAt, updatedAt = updatedAt,
    width = width, height = height
)

// ─── Product ─────────────────────────────────────────────────────────────────
fun ProductEntity.toDomain() = Product(
    id = id, name = name, manufacturer = manufacturer, articleNumber = articleNumber,
    category = category, description = description,
    powerStandbyMa = powerStandbyMa, powerAlarmMa = powerAlarmMa,
    voltageV = voltageV, powerWatt = powerWatt,
    widthMm = widthMm, heightMm = heightMm, depthMm = depthMm, weightG = weightG,
    certifications = certifications, specSheetUri = specSheetUri, imageUri = imageUri,
    price = price, currency = currency, isCustom = isCustom, createdAt = createdAt
)

fun Product.toEntity() = ProductEntity(
    id = id, name = name, manufacturer = manufacturer, articleNumber = articleNumber,
    category = category, description = description,
    powerStandbyMa = powerStandbyMa, powerAlarmMa = powerAlarmMa,
    voltageV = voltageV, powerWatt = powerWatt,
    widthMm = widthMm, heightMm = heightMm, depthMm = depthMm, weightG = weightG,
    certifications = certifications, specSheetUri = specSheetUri, imageUri = imageUri,
    price = price, currency = currency, isCustom = isCustom, createdAt = createdAt
)

// ─── Symbol ──────────────────────────────────────────────────────────────────
fun SymbolEntity.toDomain() = Symbol(
    id = id, name = name, category = category, svgData = svgData,
    iconResName = iconResName, color = color, isCustom = isCustom
)

fun Symbol.toEntity() = SymbolEntity(
    id = id, name = name, category = category, svgData = svgData,
    iconResName = iconResName, color = color, isCustom = isCustom
)

// ─── NormPoint helpers ───────────────────────────────────────────────────────
private val normPointListType = object : TypeToken<List<NormPoint>>() {}.type

fun String.toNormPointList(): List<NormPoint> =
    gson.fromJson(this, normPointListType) ?: emptyList()

fun List<NormPoint>.toJson(): String = gson.toJson(this)

// ─── SymbolPlacement ─────────────────────────────────────────────────────────
fun SymbolPlacementEntity.toDomain() = SymbolPlacement(
    id = id, drawingId = drawingId, symbolId = symbolId, productId = productId,
    xNorm = xNorm, yNorm = yNorm, rotation = rotation, label = label, notes = notes,
    layerType = LayerType.valueOf(layerType), isVisible = isVisible, createdAt = createdAt
)

fun SymbolPlacement.toEntity() = SymbolPlacementEntity(
    id = id, drawingId = drawingId, symbolId = symbolId, productId = productId,
    xNorm = xNorm, yNorm = yNorm, rotation = rotation, label = label, notes = notes,
    layerType = layerType.name, isVisible = isVisible, createdAt = createdAt
)

// ─── CablePath ───────────────────────────────────────────────────────────────
fun CablePathEntity.toDomain() = CablePath(
    id = id, drawingId = drawingId, points = pointsJson.toNormPointList(),
    cableType = cableType, colorHex = colorHex, strokeWidth = strokeWidth,
    label = label, notes = notes, layerType = LayerType.valueOf(layerType),
    isVisible = isVisible, createdAt = createdAt
)

fun CablePath.toEntity() = CablePathEntity(
    id = id, drawingId = drawingId, pointsJson = points.toJson(),
    cableType = cableType, colorHex = colorHex, strokeWidth = strokeWidth,
    label = label, notes = notes, layerType = layerType.name,
    isVisible = isVisible, createdAt = createdAt
)

// ─── Zone ────────────────────────────────────────────────────────────────────
fun ZoneEntity.toDomain() = Zone(
    id = id, drawingId = drawingId, name = name, zoneNumber = zoneNumber,
    polygon = polygonJson.toNormPointList(), fillColorHex = fillColorHex,
    fillAlpha = fillAlpha, strokeColorHex = strokeColorHex, notes = notes,
    isVisible = isVisible, createdAt = createdAt
)

fun Zone.toEntity() = ZoneEntity(
    id = id, drawingId = drawingId, name = name, zoneNumber = zoneNumber,
    polygonJson = polygon.toJson(), fillColorHex = fillColorHex,
    fillAlpha = fillAlpha, strokeColorHex = strokeColorHex, notes = notes,
    isVisible = isVisible, createdAt = createdAt
)

// ─── GeoPhoto ────────────────────────────────────────────────────────────────
fun GeoPhotoEntity.toDomain() = GeoPhoto(
    id = id, projectId = projectId, drawingId = drawingId, photoUri = photoUri,
    latitude = latitude, longitude = longitude, caption = caption, takenAt = takenAt,
    xNorm = xNorm, yNorm = yNorm
)

fun GeoPhoto.toEntity() = GeoPhotoEntity(
    id = id, projectId = projectId, drawingId = drawingId, photoUri = photoUri,
    latitude = latitude, longitude = longitude, caption = caption, takenAt = takenAt,
    xNorm = xNorm, yNorm = yNorm
)

// ─── ProtocolTemplate ────────────────────────────────────────────────────────
fun ProtocolTemplateEntity.toDomain() = ProtocolTemplate(
    id = id, name = name, systemCategory = systemCategory, description = description,
    fieldsJson = fieldsJson, version = version, isBuiltIn = isBuiltIn, createdAt = createdAt
)

fun ProtocolTemplate.toEntity() = ProtocolTemplateEntity(
    id = id, name = name, systemCategory = systemCategory, description = description,
    fieldsJson = fieldsJson, version = version, isBuiltIn = isBuiltIn, createdAt = createdAt
)

// ─── ProtocolInstance ────────────────────────────────────────────────────────
fun ProtocolInstanceEntity.toDomain() = ProtocolInstance(
    id = id, projectId = projectId, templateId = templateId, valuesJson = valuesJson,
    status = status, signedBy = signedBy, signedAt = signedAt,
    createdAt = createdAt, updatedAt = updatedAt
)

fun ProtocolInstance.toEntity() = ProtocolInstanceEntity(
    id = id, projectId = projectId, templateId = templateId, valuesJson = valuesJson,
    status = status, signedBy = signedBy, signedAt = signedAt,
    createdAt = createdAt, updatedAt = updatedAt
)

// ─── Calculation ─────────────────────────────────────────────────────────────
fun CalculationEntity.toDomain() = Calculation(
    id = id, projectId = projectId, title = title, type = type,
    inputsJson = inputsJson, result = result, unit = unit, notes = notes,
    createdAt = createdAt, updatedAt = updatedAt
)

fun Calculation.toEntity() = CalculationEntity(
    id = id, projectId = projectId, title = title, type = type,
    inputsJson = inputsJson, result = result, unit = unit, notes = notes,
    createdAt = createdAt, updatedAt = updatedAt
)

// ─── ProjectFile ─────────────────────────────────────────────────────────────
fun ProjectFileEntity.toDomain() = ProjectFile(
    id = id, projectId = projectId, name = name, mimeType = mimeType,
    filePath = filePath, sizeBytes = sizeBytes, addedAt = addedAt, notes = notes
)

fun ProjectFile.toEntity() = ProjectFileEntity(
    id = id, projectId = projectId, name = name, mimeType = mimeType,
    filePath = filePath, sizeBytes = sizeBytes, addedAt = addedAt, notes = notes
)
