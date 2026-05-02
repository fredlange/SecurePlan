package se.secureplan.app.core.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import se.secureplan.app.core.data.local.dao.CablePathDao;
import se.secureplan.app.core.data.local.dao.CablePathDao_Impl;
import se.secureplan.app.core.data.local.dao.CalculationDao;
import se.secureplan.app.core.data.local.dao.CalculationDao_Impl;
import se.secureplan.app.core.data.local.dao.DrawingDao;
import se.secureplan.app.core.data.local.dao.DrawingDao_Impl;
import se.secureplan.app.core.data.local.dao.GeoPhotoDao;
import se.secureplan.app.core.data.local.dao.GeoPhotoDao_Impl;
import se.secureplan.app.core.data.local.dao.ProductDao;
import se.secureplan.app.core.data.local.dao.ProductDao_Impl;
import se.secureplan.app.core.data.local.dao.ProjectDao;
import se.secureplan.app.core.data.local.dao.ProjectDao_Impl;
import se.secureplan.app.core.data.local.dao.ProtocolInstanceDao;
import se.secureplan.app.core.data.local.dao.ProtocolInstanceDao_Impl;
import se.secureplan.app.core.data.local.dao.ProtocolTemplateDao;
import se.secureplan.app.core.data.local.dao.ProtocolTemplateDao_Impl;
import se.secureplan.app.core.data.local.dao.SymbolDao;
import se.secureplan.app.core.data.local.dao.SymbolDao_Impl;
import se.secureplan.app.core.data.local.dao.SymbolPlacementDao;
import se.secureplan.app.core.data.local.dao.SymbolPlacementDao_Impl;
import se.secureplan.app.core.data.local.dao.ZoneDao;
import se.secureplan.app.core.data.local.dao.ZoneDao_Impl;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProjectDao _projectDao;

  private volatile DrawingDao _drawingDao;

  private volatile ProductDao _productDao;

  private volatile SymbolDao _symbolDao;

  private volatile SymbolPlacementDao _symbolPlacementDao;

  private volatile CablePathDao _cablePathDao;

  private volatile ZoneDao _zoneDao;

  private volatile GeoPhotoDao _geoPhotoDao;

  private volatile ProtocolTemplateDao _protocolTemplateDao;

  private volatile ProtocolInstanceDao _protocolInstanceDao;

  private volatile CalculationDao _calculationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `projects` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `clientName` TEXT NOT NULL, `address` TEXT NOT NULL, `description` TEXT NOT NULL, `systemCategory` TEXT NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `installerName` TEXT NOT NULL, `installerCompany` TEXT NOT NULL, `installerEmail` TEXT NOT NULL, `installerPhone` TEXT NOT NULL, `coverImageUri` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `drawings` (`id` TEXT NOT NULL, `projectId` TEXT NOT NULL, `name` TEXT NOT NULL, `floor` INTEGER NOT NULL, `backgroundUri` TEXT, `backgroundPageIndex` INTEGER NOT NULL, `scaleMetersPerUnit` REAL NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `width` REAL NOT NULL, `height` REAL NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`projectId`) REFERENCES `projects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_drawings_projectId` ON `drawings` (`projectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `manufacturer` TEXT NOT NULL, `articleNumber` TEXT NOT NULL, `category` TEXT NOT NULL, `description` TEXT NOT NULL, `powerStandbyMa` REAL NOT NULL, `powerAlarmMa` REAL NOT NULL, `voltageV` REAL NOT NULL, `powerWatt` REAL NOT NULL, `widthMm` REAL NOT NULL, `heightMm` REAL NOT NULL, `depthMm` REAL NOT NULL, `weightG` REAL NOT NULL, `certifications` TEXT NOT NULL, `specSheetUri` TEXT, `imageUri` TEXT, `price` REAL NOT NULL, `currency` TEXT NOT NULL, `isCustom` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `symbols` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `svgData` TEXT, `iconResName` TEXT, `color` INTEGER NOT NULL, `isCustom` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `symbol_placements` (`id` TEXT NOT NULL, `drawingId` TEXT NOT NULL, `symbolId` TEXT NOT NULL, `productId` TEXT, `xNorm` REAL NOT NULL, `yNorm` REAL NOT NULL, `rotation` REAL NOT NULL, `label` TEXT NOT NULL, `notes` TEXT NOT NULL, `layerType` TEXT NOT NULL, `isVisible` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`drawingId`) REFERENCES `drawings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_symbol_placements_drawingId` ON `symbol_placements` (`drawingId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cable_paths` (`id` TEXT NOT NULL, `drawingId` TEXT NOT NULL, `pointsJson` TEXT NOT NULL, `cableType` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `strokeWidth` REAL NOT NULL, `label` TEXT NOT NULL, `notes` TEXT NOT NULL, `layerType` TEXT NOT NULL, `isVisible` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`drawingId`) REFERENCES `drawings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_cable_paths_drawingId` ON `cable_paths` (`drawingId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `zones` (`id` TEXT NOT NULL, `drawingId` TEXT NOT NULL, `name` TEXT NOT NULL, `zoneNumber` INTEGER NOT NULL, `polygonJson` TEXT NOT NULL, `fillColorHex` TEXT NOT NULL, `fillAlpha` REAL NOT NULL, `strokeColorHex` TEXT NOT NULL, `notes` TEXT NOT NULL, `isVisible` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`drawingId`) REFERENCES `drawings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_zones_drawingId` ON `zones` (`drawingId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `geo_photos` (`id` TEXT NOT NULL, `projectId` TEXT NOT NULL, `drawingId` TEXT, `photoUri` TEXT NOT NULL, `latitude` REAL, `longitude` REAL, `caption` TEXT NOT NULL, `takenAt` INTEGER NOT NULL, `xNorm` REAL, `yNorm` REAL, PRIMARY KEY(`id`), FOREIGN KEY(`projectId`) REFERENCES `projects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_geo_photos_projectId` ON `geo_photos` (`projectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `protocol_templates` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `systemCategory` TEXT NOT NULL, `description` TEXT NOT NULL, `fieldsJson` TEXT NOT NULL, `version` INTEGER NOT NULL, `isBuiltIn` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `protocol_instances` (`id` TEXT NOT NULL, `projectId` TEXT NOT NULL, `templateId` TEXT NOT NULL, `valuesJson` TEXT NOT NULL, `status` TEXT NOT NULL, `signedBy` TEXT, `signedAt` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`projectId`) REFERENCES `projects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`templateId`) REFERENCES `protocol_templates`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_protocol_instances_projectId` ON `protocol_instances` (`projectId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_protocol_instances_templateId` ON `protocol_instances` (`templateId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `calculations` (`id` TEXT NOT NULL, `projectId` TEXT NOT NULL, `title` TEXT NOT NULL, `type` TEXT NOT NULL, `inputsJson` TEXT NOT NULL, `result` REAL NOT NULL, `unit` TEXT NOT NULL, `notes` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`projectId`) REFERENCES `projects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_calculations_projectId` ON `calculations` (`projectId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b10313eddf78b5a5f6b41b19372cec84')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `projects`");
        db.execSQL("DROP TABLE IF EXISTS `drawings`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `symbols`");
        db.execSQL("DROP TABLE IF EXISTS `symbol_placements`");
        db.execSQL("DROP TABLE IF EXISTS `cable_paths`");
        db.execSQL("DROP TABLE IF EXISTS `zones`");
        db.execSQL("DROP TABLE IF EXISTS `geo_photos`");
        db.execSQL("DROP TABLE IF EXISTS `protocol_templates`");
        db.execSQL("DROP TABLE IF EXISTS `protocol_instances`");
        db.execSQL("DROP TABLE IF EXISTS `calculations`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProjects = new HashMap<String, TableInfo.Column>(14);
        _columnsProjects.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("clientName", new TableInfo.Column("clientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("systemCategory", new TableInfo.Column("systemCategory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("installerName", new TableInfo.Column("installerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("installerCompany", new TableInfo.Column("installerCompany", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("installerEmail", new TableInfo.Column("installerEmail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("installerPhone", new TableInfo.Column("installerPhone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProjects.put("coverImageUri", new TableInfo.Column("coverImageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProjects = new TableInfo("projects", _columnsProjects, _foreignKeysProjects, _indicesProjects);
        final TableInfo _existingProjects = TableInfo.read(db, "projects");
        if (!_infoProjects.equals(_existingProjects)) {
          return new RoomOpenHelper.ValidationResult(false, "projects(se.secureplan.app.core.data.local.entity.ProjectEntity).\n"
                  + " Expected:\n" + _infoProjects + "\n"
                  + " Found:\n" + _existingProjects);
        }
        final HashMap<String, TableInfo.Column> _columnsDrawings = new HashMap<String, TableInfo.Column>(11);
        _columnsDrawings.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("floor", new TableInfo.Column("floor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("backgroundUri", new TableInfo.Column("backgroundUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("backgroundPageIndex", new TableInfo.Column("backgroundPageIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("scaleMetersPerUnit", new TableInfo.Column("scaleMetersPerUnit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("width", new TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDrawings.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDrawings = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDrawings.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("projectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDrawings = new HashSet<TableInfo.Index>(1);
        _indicesDrawings.add(new TableInfo.Index("index_drawings_projectId", false, Arrays.asList("projectId"), Arrays.asList("ASC")));
        final TableInfo _infoDrawings = new TableInfo("drawings", _columnsDrawings, _foreignKeysDrawings, _indicesDrawings);
        final TableInfo _existingDrawings = TableInfo.read(db, "drawings");
        if (!_infoDrawings.equals(_existingDrawings)) {
          return new RoomOpenHelper.ValidationResult(false, "drawings(se.secureplan.app.core.data.local.entity.DrawingEntity).\n"
                  + " Expected:\n" + _infoDrawings + "\n"
                  + " Found:\n" + _existingDrawings);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(21);
        _columnsProducts.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("manufacturer", new TableInfo.Column("manufacturer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("articleNumber", new TableInfo.Column("articleNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("powerStandbyMa", new TableInfo.Column("powerStandbyMa", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("powerAlarmMa", new TableInfo.Column("powerAlarmMa", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("voltageV", new TableInfo.Column("voltageV", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("powerWatt", new TableInfo.Column("powerWatt", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("widthMm", new TableInfo.Column("widthMm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("heightMm", new TableInfo.Column("heightMm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("depthMm", new TableInfo.Column("depthMm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("weightG", new TableInfo.Column("weightG", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("certifications", new TableInfo.Column("certifications", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("specSheetUri", new TableInfo.Column("specSheetUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("imageUri", new TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(se.secureplan.app.core.data.local.entity.ProductEntity).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsSymbols = new HashMap<String, TableInfo.Column>(7);
        _columnsSymbols.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("svgData", new TableInfo.Column("svgData", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("iconResName", new TableInfo.Column("iconResName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbols.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSymbols = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSymbols = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSymbols = new TableInfo("symbols", _columnsSymbols, _foreignKeysSymbols, _indicesSymbols);
        final TableInfo _existingSymbols = TableInfo.read(db, "symbols");
        if (!_infoSymbols.equals(_existingSymbols)) {
          return new RoomOpenHelper.ValidationResult(false, "symbols(se.secureplan.app.core.data.local.entity.SymbolEntity).\n"
                  + " Expected:\n" + _infoSymbols + "\n"
                  + " Found:\n" + _existingSymbols);
        }
        final HashMap<String, TableInfo.Column> _columnsSymbolPlacements = new HashMap<String, TableInfo.Column>(12);
        _columnsSymbolPlacements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("drawingId", new TableInfo.Column("drawingId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("symbolId", new TableInfo.Column("symbolId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("productId", new TableInfo.Column("productId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("xNorm", new TableInfo.Column("xNorm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("yNorm", new TableInfo.Column("yNorm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("rotation", new TableInfo.Column("rotation", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("layerType", new TableInfo.Column("layerType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("isVisible", new TableInfo.Column("isVisible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymbolPlacements.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSymbolPlacements = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSymbolPlacements.add(new TableInfo.ForeignKey("drawings", "CASCADE", "NO ACTION", Arrays.asList("drawingId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSymbolPlacements = new HashSet<TableInfo.Index>(1);
        _indicesSymbolPlacements.add(new TableInfo.Index("index_symbol_placements_drawingId", false, Arrays.asList("drawingId"), Arrays.asList("ASC")));
        final TableInfo _infoSymbolPlacements = new TableInfo("symbol_placements", _columnsSymbolPlacements, _foreignKeysSymbolPlacements, _indicesSymbolPlacements);
        final TableInfo _existingSymbolPlacements = TableInfo.read(db, "symbol_placements");
        if (!_infoSymbolPlacements.equals(_existingSymbolPlacements)) {
          return new RoomOpenHelper.ValidationResult(false, "symbol_placements(se.secureplan.app.core.data.local.entity.SymbolPlacementEntity).\n"
                  + " Expected:\n" + _infoSymbolPlacements + "\n"
                  + " Found:\n" + _existingSymbolPlacements);
        }
        final HashMap<String, TableInfo.Column> _columnsCablePaths = new HashMap<String, TableInfo.Column>(11);
        _columnsCablePaths.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("drawingId", new TableInfo.Column("drawingId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("pointsJson", new TableInfo.Column("pointsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("cableType", new TableInfo.Column("cableType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("strokeWidth", new TableInfo.Column("strokeWidth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("layerType", new TableInfo.Column("layerType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("isVisible", new TableInfo.Column("isVisible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCablePaths.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCablePaths = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCablePaths.add(new TableInfo.ForeignKey("drawings", "CASCADE", "NO ACTION", Arrays.asList("drawingId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCablePaths = new HashSet<TableInfo.Index>(1);
        _indicesCablePaths.add(new TableInfo.Index("index_cable_paths_drawingId", false, Arrays.asList("drawingId"), Arrays.asList("ASC")));
        final TableInfo _infoCablePaths = new TableInfo("cable_paths", _columnsCablePaths, _foreignKeysCablePaths, _indicesCablePaths);
        final TableInfo _existingCablePaths = TableInfo.read(db, "cable_paths");
        if (!_infoCablePaths.equals(_existingCablePaths)) {
          return new RoomOpenHelper.ValidationResult(false, "cable_paths(se.secureplan.app.core.data.local.entity.CablePathEntity).\n"
                  + " Expected:\n" + _infoCablePaths + "\n"
                  + " Found:\n" + _existingCablePaths);
        }
        final HashMap<String, TableInfo.Column> _columnsZones = new HashMap<String, TableInfo.Column>(11);
        _columnsZones.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("drawingId", new TableInfo.Column("drawingId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("zoneNumber", new TableInfo.Column("zoneNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("polygonJson", new TableInfo.Column("polygonJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("fillColorHex", new TableInfo.Column("fillColorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("fillAlpha", new TableInfo.Column("fillAlpha", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("strokeColorHex", new TableInfo.Column("strokeColorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("isVisible", new TableInfo.Column("isVisible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsZones.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysZones = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysZones.add(new TableInfo.ForeignKey("drawings", "CASCADE", "NO ACTION", Arrays.asList("drawingId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesZones = new HashSet<TableInfo.Index>(1);
        _indicesZones.add(new TableInfo.Index("index_zones_drawingId", false, Arrays.asList("drawingId"), Arrays.asList("ASC")));
        final TableInfo _infoZones = new TableInfo("zones", _columnsZones, _foreignKeysZones, _indicesZones);
        final TableInfo _existingZones = TableInfo.read(db, "zones");
        if (!_infoZones.equals(_existingZones)) {
          return new RoomOpenHelper.ValidationResult(false, "zones(se.secureplan.app.core.data.local.entity.ZoneEntity).\n"
                  + " Expected:\n" + _infoZones + "\n"
                  + " Found:\n" + _existingZones);
        }
        final HashMap<String, TableInfo.Column> _columnsGeoPhotos = new HashMap<String, TableInfo.Column>(10);
        _columnsGeoPhotos.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("drawingId", new TableInfo.Column("drawingId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("photoUri", new TableInfo.Column("photoUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("caption", new TableInfo.Column("caption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("takenAt", new TableInfo.Column("takenAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("xNorm", new TableInfo.Column("xNorm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGeoPhotos.put("yNorm", new TableInfo.Column("yNorm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGeoPhotos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGeoPhotos.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("projectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGeoPhotos = new HashSet<TableInfo.Index>(1);
        _indicesGeoPhotos.add(new TableInfo.Index("index_geo_photos_projectId", false, Arrays.asList("projectId"), Arrays.asList("ASC")));
        final TableInfo _infoGeoPhotos = new TableInfo("geo_photos", _columnsGeoPhotos, _foreignKeysGeoPhotos, _indicesGeoPhotos);
        final TableInfo _existingGeoPhotos = TableInfo.read(db, "geo_photos");
        if (!_infoGeoPhotos.equals(_existingGeoPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "geo_photos(se.secureplan.app.core.data.local.entity.GeoPhotoEntity).\n"
                  + " Expected:\n" + _infoGeoPhotos + "\n"
                  + " Found:\n" + _existingGeoPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsProtocolTemplates = new HashMap<String, TableInfo.Column>(8);
        _columnsProtocolTemplates.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("systemCategory", new TableInfo.Column("systemCategory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("fieldsJson", new TableInfo.Column("fieldsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("isBuiltIn", new TableInfo.Column("isBuiltIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolTemplates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProtocolTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProtocolTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProtocolTemplates = new TableInfo("protocol_templates", _columnsProtocolTemplates, _foreignKeysProtocolTemplates, _indicesProtocolTemplates);
        final TableInfo _existingProtocolTemplates = TableInfo.read(db, "protocol_templates");
        if (!_infoProtocolTemplates.equals(_existingProtocolTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "protocol_templates(se.secureplan.app.core.data.local.entity.ProtocolTemplateEntity).\n"
                  + " Expected:\n" + _infoProtocolTemplates + "\n"
                  + " Found:\n" + _existingProtocolTemplates);
        }
        final HashMap<String, TableInfo.Column> _columnsProtocolInstances = new HashMap<String, TableInfo.Column>(9);
        _columnsProtocolInstances.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("templateId", new TableInfo.Column("templateId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("valuesJson", new TableInfo.Column("valuesJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("signedBy", new TableInfo.Column("signedBy", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("signedAt", new TableInfo.Column("signedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProtocolInstances.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProtocolInstances = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysProtocolInstances.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("projectId"), Arrays.asList("id")));
        _foreignKeysProtocolInstances.add(new TableInfo.ForeignKey("protocol_templates", "RESTRICT", "NO ACTION", Arrays.asList("templateId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesProtocolInstances = new HashSet<TableInfo.Index>(2);
        _indicesProtocolInstances.add(new TableInfo.Index("index_protocol_instances_projectId", false, Arrays.asList("projectId"), Arrays.asList("ASC")));
        _indicesProtocolInstances.add(new TableInfo.Index("index_protocol_instances_templateId", false, Arrays.asList("templateId"), Arrays.asList("ASC")));
        final TableInfo _infoProtocolInstances = new TableInfo("protocol_instances", _columnsProtocolInstances, _foreignKeysProtocolInstances, _indicesProtocolInstances);
        final TableInfo _existingProtocolInstances = TableInfo.read(db, "protocol_instances");
        if (!_infoProtocolInstances.equals(_existingProtocolInstances)) {
          return new RoomOpenHelper.ValidationResult(false, "protocol_instances(se.secureplan.app.core.data.local.entity.ProtocolInstanceEntity).\n"
                  + " Expected:\n" + _infoProtocolInstances + "\n"
                  + " Found:\n" + _existingProtocolInstances);
        }
        final HashMap<String, TableInfo.Column> _columnsCalculations = new HashMap<String, TableInfo.Column>(10);
        _columnsCalculations.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("projectId", new TableInfo.Column("projectId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("inputsJson", new TableInfo.Column("inputsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("result", new TableInfo.Column("result", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCalculations.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCalculations = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCalculations.add(new TableInfo.ForeignKey("projects", "CASCADE", "NO ACTION", Arrays.asList("projectId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCalculations = new HashSet<TableInfo.Index>(1);
        _indicesCalculations.add(new TableInfo.Index("index_calculations_projectId", false, Arrays.asList("projectId"), Arrays.asList("ASC")));
        final TableInfo _infoCalculations = new TableInfo("calculations", _columnsCalculations, _foreignKeysCalculations, _indicesCalculations);
        final TableInfo _existingCalculations = TableInfo.read(db, "calculations");
        if (!_infoCalculations.equals(_existingCalculations)) {
          return new RoomOpenHelper.ValidationResult(false, "calculations(se.secureplan.app.core.data.local.entity.CalculationEntity).\n"
                  + " Expected:\n" + _infoCalculations + "\n"
                  + " Found:\n" + _existingCalculations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b10313eddf78b5a5f6b41b19372cec84", "0ad5637b4ca62e10cf945e225a7ebd5b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "projects","drawings","products","symbols","symbol_placements","cable_paths","zones","geo_photos","protocol_templates","protocol_instances","calculations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `projects`");
      _db.execSQL("DELETE FROM `drawings`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `symbols`");
      _db.execSQL("DELETE FROM `symbol_placements`");
      _db.execSQL("DELETE FROM `cable_paths`");
      _db.execSQL("DELETE FROM `zones`");
      _db.execSQL("DELETE FROM `geo_photos`");
      _db.execSQL("DELETE FROM `protocol_instances`");
      _db.execSQL("DELETE FROM `protocol_templates`");
      _db.execSQL("DELETE FROM `calculations`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProjectDao.class, ProjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DrawingDao.class, DrawingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SymbolDao.class, SymbolDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SymbolPlacementDao.class, SymbolPlacementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CablePathDao.class, CablePathDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ZoneDao.class, ZoneDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GeoPhotoDao.class, GeoPhotoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProtocolTemplateDao.class, ProtocolTemplateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProtocolInstanceDao.class, ProtocolInstanceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CalculationDao.class, CalculationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProjectDao projectDao() {
    if (_projectDao != null) {
      return _projectDao;
    } else {
      synchronized(this) {
        if(_projectDao == null) {
          _projectDao = new ProjectDao_Impl(this);
        }
        return _projectDao;
      }
    }
  }

  @Override
  public DrawingDao drawingDao() {
    if (_drawingDao != null) {
      return _drawingDao;
    } else {
      synchronized(this) {
        if(_drawingDao == null) {
          _drawingDao = new DrawingDao_Impl(this);
        }
        return _drawingDao;
      }
    }
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public SymbolDao symbolDao() {
    if (_symbolDao != null) {
      return _symbolDao;
    } else {
      synchronized(this) {
        if(_symbolDao == null) {
          _symbolDao = new SymbolDao_Impl(this);
        }
        return _symbolDao;
      }
    }
  }

  @Override
  public SymbolPlacementDao symbolPlacementDao() {
    if (_symbolPlacementDao != null) {
      return _symbolPlacementDao;
    } else {
      synchronized(this) {
        if(_symbolPlacementDao == null) {
          _symbolPlacementDao = new SymbolPlacementDao_Impl(this);
        }
        return _symbolPlacementDao;
      }
    }
  }

  @Override
  public CablePathDao cablePathDao() {
    if (_cablePathDao != null) {
      return _cablePathDao;
    } else {
      synchronized(this) {
        if(_cablePathDao == null) {
          _cablePathDao = new CablePathDao_Impl(this);
        }
        return _cablePathDao;
      }
    }
  }

  @Override
  public ZoneDao zoneDao() {
    if (_zoneDao != null) {
      return _zoneDao;
    } else {
      synchronized(this) {
        if(_zoneDao == null) {
          _zoneDao = new ZoneDao_Impl(this);
        }
        return _zoneDao;
      }
    }
  }

  @Override
  public GeoPhotoDao geoPhotoDao() {
    if (_geoPhotoDao != null) {
      return _geoPhotoDao;
    } else {
      synchronized(this) {
        if(_geoPhotoDao == null) {
          _geoPhotoDao = new GeoPhotoDao_Impl(this);
        }
        return _geoPhotoDao;
      }
    }
  }

  @Override
  public ProtocolTemplateDao protocolTemplateDao() {
    if (_protocolTemplateDao != null) {
      return _protocolTemplateDao;
    } else {
      synchronized(this) {
        if(_protocolTemplateDao == null) {
          _protocolTemplateDao = new ProtocolTemplateDao_Impl(this);
        }
        return _protocolTemplateDao;
      }
    }
  }

  @Override
  public ProtocolInstanceDao protocolInstanceDao() {
    if (_protocolInstanceDao != null) {
      return _protocolInstanceDao;
    } else {
      synchronized(this) {
        if(_protocolInstanceDao == null) {
          _protocolInstanceDao = new ProtocolInstanceDao_Impl(this);
        }
        return _protocolInstanceDao;
      }
    }
  }

  @Override
  public CalculationDao calculationDao() {
    if (_calculationDao != null) {
      return _calculationDao;
    } else {
      synchronized(this) {
        if(_calculationDao == null) {
          _calculationDao = new CalculationDao_Impl(this);
        }
        return _calculationDao;
      }
    }
  }
}
