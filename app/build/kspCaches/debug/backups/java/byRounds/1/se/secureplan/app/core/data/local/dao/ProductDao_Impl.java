package se.secureplan.app.core.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import se.secureplan.app.core.data.local.entity.ProductEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProductEntity> __insertionAdapterOfProductEntity;

  private final EntityDeletionOrUpdateAdapter<ProductEntity> __deletionAdapterOfProductEntity;

  private final EntityDeletionOrUpdateAdapter<ProductEntity> __updateAdapterOfProductEntity;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProductEntity = new EntityInsertionAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`name`,`manufacturer`,`articleNumber`,`category`,`description`,`powerStandbyMa`,`powerAlarmMa`,`voltageV`,`powerWatt`,`widthMm`,`heightMm`,`depthMm`,`weightG`,`certifications`,`specSheetUri`,`imageUri`,`price`,`currency`,`isCustom`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getManufacturer());
        statement.bindString(4, entity.getArticleNumber());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getDescription());
        statement.bindDouble(7, entity.getPowerStandbyMa());
        statement.bindDouble(8, entity.getPowerAlarmMa());
        statement.bindDouble(9, entity.getVoltageV());
        statement.bindDouble(10, entity.getPowerWatt());
        statement.bindDouble(11, entity.getWidthMm());
        statement.bindDouble(12, entity.getHeightMm());
        statement.bindDouble(13, entity.getDepthMm());
        statement.bindDouble(14, entity.getWeightG());
        statement.bindString(15, entity.getCertifications());
        if (entity.getSpecSheetUri() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getSpecSheetUri());
        }
        if (entity.getImageUri() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getImageUri());
        }
        statement.bindDouble(18, entity.getPrice());
        statement.bindString(19, entity.getCurrency());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(20, _tmp);
        statement.bindLong(21, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfProductEntity = new EntityDeletionOrUpdateAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `products` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfProductEntity = new EntityDeletionOrUpdateAdapter<ProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`name` = ?,`manufacturer` = ?,`articleNumber` = ?,`category` = ?,`description` = ?,`powerStandbyMa` = ?,`powerAlarmMa` = ?,`voltageV` = ?,`powerWatt` = ?,`widthMm` = ?,`heightMm` = ?,`depthMm` = ?,`weightG` = ?,`certifications` = ?,`specSheetUri` = ?,`imageUri` = ?,`price` = ?,`currency` = ?,`isCustom` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProductEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getManufacturer());
        statement.bindString(4, entity.getArticleNumber());
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getDescription());
        statement.bindDouble(7, entity.getPowerStandbyMa());
        statement.bindDouble(8, entity.getPowerAlarmMa());
        statement.bindDouble(9, entity.getVoltageV());
        statement.bindDouble(10, entity.getPowerWatt());
        statement.bindDouble(11, entity.getWidthMm());
        statement.bindDouble(12, entity.getHeightMm());
        statement.bindDouble(13, entity.getDepthMm());
        statement.bindDouble(14, entity.getWeightG());
        statement.bindString(15, entity.getCertifications());
        if (entity.getSpecSheetUri() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getSpecSheetUri());
        }
        if (entity.getImageUri() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getImageUri());
        }
        statement.bindDouble(18, entity.getPrice());
        statement.bindString(19, entity.getCurrency());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(20, _tmp);
        statement.bindLong(21, entity.getCreatedAt());
        statement.bindString(22, entity.getId());
      }
    };
  }

  @Override
  public Object insertProduct(final ProductEntity product,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductEntity.insert(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertProducts(final List<ProductEntity> products,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProductEntity.insert(products);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProduct(final ProductEntity product,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProductEntity.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProduct(final ProductEntity product,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProductEntity.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProductEntity>> getAllProducts() {
    final String _sql = "SELECT * FROM products ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfManufacturer = CursorUtil.getColumnIndexOrThrow(_cursor, "manufacturer");
          final int _cursorIndexOfArticleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "articleNumber");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPowerStandbyMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerStandbyMa");
          final int _cursorIndexOfPowerAlarmMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerAlarmMa");
          final int _cursorIndexOfVoltageV = CursorUtil.getColumnIndexOrThrow(_cursor, "voltageV");
          final int _cursorIndexOfPowerWatt = CursorUtil.getColumnIndexOrThrow(_cursor, "powerWatt");
          final int _cursorIndexOfWidthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "widthMm");
          final int _cursorIndexOfHeightMm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightMm");
          final int _cursorIndexOfDepthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "depthMm");
          final int _cursorIndexOfWeightG = CursorUtil.getColumnIndexOrThrow(_cursor, "weightG");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecSheetUri = CursorUtil.getColumnIndexOrThrow(_cursor, "specSheetUri");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpManufacturer;
            _tmpManufacturer = _cursor.getString(_cursorIndexOfManufacturer);
            final String _tmpArticleNumber;
            _tmpArticleNumber = _cursor.getString(_cursorIndexOfArticleNumber);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final float _tmpPowerStandbyMa;
            _tmpPowerStandbyMa = _cursor.getFloat(_cursorIndexOfPowerStandbyMa);
            final float _tmpPowerAlarmMa;
            _tmpPowerAlarmMa = _cursor.getFloat(_cursorIndexOfPowerAlarmMa);
            final float _tmpVoltageV;
            _tmpVoltageV = _cursor.getFloat(_cursorIndexOfVoltageV);
            final float _tmpPowerWatt;
            _tmpPowerWatt = _cursor.getFloat(_cursorIndexOfPowerWatt);
            final float _tmpWidthMm;
            _tmpWidthMm = _cursor.getFloat(_cursorIndexOfWidthMm);
            final float _tmpHeightMm;
            _tmpHeightMm = _cursor.getFloat(_cursorIndexOfHeightMm);
            final float _tmpDepthMm;
            _tmpDepthMm = _cursor.getFloat(_cursorIndexOfDepthMm);
            final float _tmpWeightG;
            _tmpWeightG = _cursor.getFloat(_cursorIndexOfWeightG);
            final String _tmpCertifications;
            _tmpCertifications = _cursor.getString(_cursorIndexOfCertifications);
            final String _tmpSpecSheetUri;
            if (_cursor.isNull(_cursorIndexOfSpecSheetUri)) {
              _tmpSpecSheetUri = null;
            } else {
              _tmpSpecSheetUri = _cursor.getString(_cursorIndexOfSpecSheetUri);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpManufacturer,_tmpArticleNumber,_tmpCategory,_tmpDescription,_tmpPowerStandbyMa,_tmpPowerAlarmMa,_tmpVoltageV,_tmpPowerWatt,_tmpWidthMm,_tmpHeightMm,_tmpDepthMm,_tmpWeightG,_tmpCertifications,_tmpSpecSheetUri,_tmpImageUri,_tmpPrice,_tmpCurrency,_tmpIsCustom,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProductEntity>> getProductsByCategory(final String category) {
    final String _sql = "SELECT * FROM products WHERE category = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfManufacturer = CursorUtil.getColumnIndexOrThrow(_cursor, "manufacturer");
          final int _cursorIndexOfArticleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "articleNumber");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPowerStandbyMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerStandbyMa");
          final int _cursorIndexOfPowerAlarmMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerAlarmMa");
          final int _cursorIndexOfVoltageV = CursorUtil.getColumnIndexOrThrow(_cursor, "voltageV");
          final int _cursorIndexOfPowerWatt = CursorUtil.getColumnIndexOrThrow(_cursor, "powerWatt");
          final int _cursorIndexOfWidthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "widthMm");
          final int _cursorIndexOfHeightMm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightMm");
          final int _cursorIndexOfDepthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "depthMm");
          final int _cursorIndexOfWeightG = CursorUtil.getColumnIndexOrThrow(_cursor, "weightG");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecSheetUri = CursorUtil.getColumnIndexOrThrow(_cursor, "specSheetUri");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpManufacturer;
            _tmpManufacturer = _cursor.getString(_cursorIndexOfManufacturer);
            final String _tmpArticleNumber;
            _tmpArticleNumber = _cursor.getString(_cursorIndexOfArticleNumber);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final float _tmpPowerStandbyMa;
            _tmpPowerStandbyMa = _cursor.getFloat(_cursorIndexOfPowerStandbyMa);
            final float _tmpPowerAlarmMa;
            _tmpPowerAlarmMa = _cursor.getFloat(_cursorIndexOfPowerAlarmMa);
            final float _tmpVoltageV;
            _tmpVoltageV = _cursor.getFloat(_cursorIndexOfVoltageV);
            final float _tmpPowerWatt;
            _tmpPowerWatt = _cursor.getFloat(_cursorIndexOfPowerWatt);
            final float _tmpWidthMm;
            _tmpWidthMm = _cursor.getFloat(_cursorIndexOfWidthMm);
            final float _tmpHeightMm;
            _tmpHeightMm = _cursor.getFloat(_cursorIndexOfHeightMm);
            final float _tmpDepthMm;
            _tmpDepthMm = _cursor.getFloat(_cursorIndexOfDepthMm);
            final float _tmpWeightG;
            _tmpWeightG = _cursor.getFloat(_cursorIndexOfWeightG);
            final String _tmpCertifications;
            _tmpCertifications = _cursor.getString(_cursorIndexOfCertifications);
            final String _tmpSpecSheetUri;
            if (_cursor.isNull(_cursorIndexOfSpecSheetUri)) {
              _tmpSpecSheetUri = null;
            } else {
              _tmpSpecSheetUri = _cursor.getString(_cursorIndexOfSpecSheetUri);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpManufacturer,_tmpArticleNumber,_tmpCategory,_tmpDescription,_tmpPowerStandbyMa,_tmpPowerAlarmMa,_tmpVoltageV,_tmpPowerWatt,_tmpWidthMm,_tmpHeightMm,_tmpDepthMm,_tmpWeightG,_tmpCertifications,_tmpSpecSheetUri,_tmpImageUri,_tmpPrice,_tmpCurrency,_tmpIsCustom,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProductEntity>> getBuiltInProducts() {
    final String _sql = "SELECT * FROM products WHERE isCustom = 0 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfManufacturer = CursorUtil.getColumnIndexOrThrow(_cursor, "manufacturer");
          final int _cursorIndexOfArticleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "articleNumber");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPowerStandbyMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerStandbyMa");
          final int _cursorIndexOfPowerAlarmMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerAlarmMa");
          final int _cursorIndexOfVoltageV = CursorUtil.getColumnIndexOrThrow(_cursor, "voltageV");
          final int _cursorIndexOfPowerWatt = CursorUtil.getColumnIndexOrThrow(_cursor, "powerWatt");
          final int _cursorIndexOfWidthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "widthMm");
          final int _cursorIndexOfHeightMm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightMm");
          final int _cursorIndexOfDepthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "depthMm");
          final int _cursorIndexOfWeightG = CursorUtil.getColumnIndexOrThrow(_cursor, "weightG");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecSheetUri = CursorUtil.getColumnIndexOrThrow(_cursor, "specSheetUri");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpManufacturer;
            _tmpManufacturer = _cursor.getString(_cursorIndexOfManufacturer);
            final String _tmpArticleNumber;
            _tmpArticleNumber = _cursor.getString(_cursorIndexOfArticleNumber);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final float _tmpPowerStandbyMa;
            _tmpPowerStandbyMa = _cursor.getFloat(_cursorIndexOfPowerStandbyMa);
            final float _tmpPowerAlarmMa;
            _tmpPowerAlarmMa = _cursor.getFloat(_cursorIndexOfPowerAlarmMa);
            final float _tmpVoltageV;
            _tmpVoltageV = _cursor.getFloat(_cursorIndexOfVoltageV);
            final float _tmpPowerWatt;
            _tmpPowerWatt = _cursor.getFloat(_cursorIndexOfPowerWatt);
            final float _tmpWidthMm;
            _tmpWidthMm = _cursor.getFloat(_cursorIndexOfWidthMm);
            final float _tmpHeightMm;
            _tmpHeightMm = _cursor.getFloat(_cursorIndexOfHeightMm);
            final float _tmpDepthMm;
            _tmpDepthMm = _cursor.getFloat(_cursorIndexOfDepthMm);
            final float _tmpWeightG;
            _tmpWeightG = _cursor.getFloat(_cursorIndexOfWeightG);
            final String _tmpCertifications;
            _tmpCertifications = _cursor.getString(_cursorIndexOfCertifications);
            final String _tmpSpecSheetUri;
            if (_cursor.isNull(_cursorIndexOfSpecSheetUri)) {
              _tmpSpecSheetUri = null;
            } else {
              _tmpSpecSheetUri = _cursor.getString(_cursorIndexOfSpecSheetUri);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpManufacturer,_tmpArticleNumber,_tmpCategory,_tmpDescription,_tmpPowerStandbyMa,_tmpPowerAlarmMa,_tmpVoltageV,_tmpPowerWatt,_tmpWidthMm,_tmpHeightMm,_tmpDepthMm,_tmpWeightG,_tmpCertifications,_tmpSpecSheetUri,_tmpImageUri,_tmpPrice,_tmpCurrency,_tmpIsCustom,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getProductCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProductById(final String id,
      final Continuation<? super ProductEntity> $completion) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProductEntity>() {
      @Override
      @Nullable
      public ProductEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfManufacturer = CursorUtil.getColumnIndexOrThrow(_cursor, "manufacturer");
          final int _cursorIndexOfArticleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "articleNumber");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPowerStandbyMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerStandbyMa");
          final int _cursorIndexOfPowerAlarmMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerAlarmMa");
          final int _cursorIndexOfVoltageV = CursorUtil.getColumnIndexOrThrow(_cursor, "voltageV");
          final int _cursorIndexOfPowerWatt = CursorUtil.getColumnIndexOrThrow(_cursor, "powerWatt");
          final int _cursorIndexOfWidthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "widthMm");
          final int _cursorIndexOfHeightMm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightMm");
          final int _cursorIndexOfDepthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "depthMm");
          final int _cursorIndexOfWeightG = CursorUtil.getColumnIndexOrThrow(_cursor, "weightG");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecSheetUri = CursorUtil.getColumnIndexOrThrow(_cursor, "specSheetUri");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ProductEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpManufacturer;
            _tmpManufacturer = _cursor.getString(_cursorIndexOfManufacturer);
            final String _tmpArticleNumber;
            _tmpArticleNumber = _cursor.getString(_cursorIndexOfArticleNumber);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final float _tmpPowerStandbyMa;
            _tmpPowerStandbyMa = _cursor.getFloat(_cursorIndexOfPowerStandbyMa);
            final float _tmpPowerAlarmMa;
            _tmpPowerAlarmMa = _cursor.getFloat(_cursorIndexOfPowerAlarmMa);
            final float _tmpVoltageV;
            _tmpVoltageV = _cursor.getFloat(_cursorIndexOfVoltageV);
            final float _tmpPowerWatt;
            _tmpPowerWatt = _cursor.getFloat(_cursorIndexOfPowerWatt);
            final float _tmpWidthMm;
            _tmpWidthMm = _cursor.getFloat(_cursorIndexOfWidthMm);
            final float _tmpHeightMm;
            _tmpHeightMm = _cursor.getFloat(_cursorIndexOfHeightMm);
            final float _tmpDepthMm;
            _tmpDepthMm = _cursor.getFloat(_cursorIndexOfDepthMm);
            final float _tmpWeightG;
            _tmpWeightG = _cursor.getFloat(_cursorIndexOfWeightG);
            final String _tmpCertifications;
            _tmpCertifications = _cursor.getString(_cursorIndexOfCertifications);
            final String _tmpSpecSheetUri;
            if (_cursor.isNull(_cursorIndexOfSpecSheetUri)) {
              _tmpSpecSheetUri = null;
            } else {
              _tmpSpecSheetUri = _cursor.getString(_cursorIndexOfSpecSheetUri);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ProductEntity(_tmpId,_tmpName,_tmpManufacturer,_tmpArticleNumber,_tmpCategory,_tmpDescription,_tmpPowerStandbyMa,_tmpPowerAlarmMa,_tmpVoltageV,_tmpPowerWatt,_tmpWidthMm,_tmpHeightMm,_tmpDepthMm,_tmpWeightG,_tmpCertifications,_tmpSpecSheetUri,_tmpImageUri,_tmpPrice,_tmpCurrency,_tmpIsCustom,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProductEntity>> searchProducts(final String query) {
    final String _sql = "SELECT * FROM products WHERE name LIKE '%' || ? || '%' OR manufacturer LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"products"}, new Callable<List<ProductEntity>>() {
      @Override
      @NonNull
      public List<ProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfManufacturer = CursorUtil.getColumnIndexOrThrow(_cursor, "manufacturer");
          final int _cursorIndexOfArticleNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "articleNumber");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPowerStandbyMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerStandbyMa");
          final int _cursorIndexOfPowerAlarmMa = CursorUtil.getColumnIndexOrThrow(_cursor, "powerAlarmMa");
          final int _cursorIndexOfVoltageV = CursorUtil.getColumnIndexOrThrow(_cursor, "voltageV");
          final int _cursorIndexOfPowerWatt = CursorUtil.getColumnIndexOrThrow(_cursor, "powerWatt");
          final int _cursorIndexOfWidthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "widthMm");
          final int _cursorIndexOfHeightMm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightMm");
          final int _cursorIndexOfDepthMm = CursorUtil.getColumnIndexOrThrow(_cursor, "depthMm");
          final int _cursorIndexOfWeightG = CursorUtil.getColumnIndexOrThrow(_cursor, "weightG");
          final int _cursorIndexOfCertifications = CursorUtil.getColumnIndexOrThrow(_cursor, "certifications");
          final int _cursorIndexOfSpecSheetUri = CursorUtil.getColumnIndexOrThrow(_cursor, "specSheetUri");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProductEntity> _result = new ArrayList<ProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProductEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpManufacturer;
            _tmpManufacturer = _cursor.getString(_cursorIndexOfManufacturer);
            final String _tmpArticleNumber;
            _tmpArticleNumber = _cursor.getString(_cursorIndexOfArticleNumber);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final float _tmpPowerStandbyMa;
            _tmpPowerStandbyMa = _cursor.getFloat(_cursorIndexOfPowerStandbyMa);
            final float _tmpPowerAlarmMa;
            _tmpPowerAlarmMa = _cursor.getFloat(_cursorIndexOfPowerAlarmMa);
            final float _tmpVoltageV;
            _tmpVoltageV = _cursor.getFloat(_cursorIndexOfVoltageV);
            final float _tmpPowerWatt;
            _tmpPowerWatt = _cursor.getFloat(_cursorIndexOfPowerWatt);
            final float _tmpWidthMm;
            _tmpWidthMm = _cursor.getFloat(_cursorIndexOfWidthMm);
            final float _tmpHeightMm;
            _tmpHeightMm = _cursor.getFloat(_cursorIndexOfHeightMm);
            final float _tmpDepthMm;
            _tmpDepthMm = _cursor.getFloat(_cursorIndexOfDepthMm);
            final float _tmpWeightG;
            _tmpWeightG = _cursor.getFloat(_cursorIndexOfWeightG);
            final String _tmpCertifications;
            _tmpCertifications = _cursor.getString(_cursorIndexOfCertifications);
            final String _tmpSpecSheetUri;
            if (_cursor.isNull(_cursorIndexOfSpecSheetUri)) {
              _tmpSpecSheetUri = null;
            } else {
              _tmpSpecSheetUri = _cursor.getString(_cursorIndexOfSpecSheetUri);
            }
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProductEntity(_tmpId,_tmpName,_tmpManufacturer,_tmpArticleNumber,_tmpCategory,_tmpDescription,_tmpPowerStandbyMa,_tmpPowerAlarmMa,_tmpVoltageV,_tmpPowerWatt,_tmpWidthMm,_tmpHeightMm,_tmpDepthMm,_tmpWeightG,_tmpCertifications,_tmpSpecSheetUri,_tmpImageUri,_tmpPrice,_tmpCurrency,_tmpIsCustom,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
