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
import androidx.room.SharedSQLiteStatement;
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
import se.secureplan.app.core.data.local.entity.SymbolPlacementEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SymbolPlacementDao_Impl implements SymbolPlacementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SymbolPlacementEntity> __insertionAdapterOfSymbolPlacementEntity;

  private final EntityDeletionOrUpdateAdapter<SymbolPlacementEntity> __deletionAdapterOfSymbolPlacementEntity;

  private final EntityDeletionOrUpdateAdapter<SymbolPlacementEntity> __updateAdapterOfSymbolPlacementEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlacementById;

  public SymbolPlacementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSymbolPlacementEntity = new EntityInsertionAdapter<SymbolPlacementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `symbol_placements` (`id`,`drawingId`,`symbolId`,`productId`,`xNorm`,`yNorm`,`rotation`,`label`,`notes`,`layerType`,`isVisible`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolPlacementEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getSymbolId());
        if (entity.getProductId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductId());
        }
        statement.bindDouble(5, entity.getXNorm());
        statement.bindDouble(6, entity.getYNorm());
        statement.bindDouble(7, entity.getRotation());
        statement.bindString(8, entity.getLabel());
        statement.bindString(9, entity.getNotes());
        statement.bindString(10, entity.getLayerType());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfSymbolPlacementEntity = new EntityDeletionOrUpdateAdapter<SymbolPlacementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `symbol_placements` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolPlacementEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfSymbolPlacementEntity = new EntityDeletionOrUpdateAdapter<SymbolPlacementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `symbol_placements` SET `id` = ?,`drawingId` = ?,`symbolId` = ?,`productId` = ?,`xNorm` = ?,`yNorm` = ?,`rotation` = ?,`label` = ?,`notes` = ?,`layerType` = ?,`isVisible` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolPlacementEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getSymbolId());
        if (entity.getProductId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductId());
        }
        statement.bindDouble(5, entity.getXNorm());
        statement.bindDouble(6, entity.getYNorm());
        statement.bindDouble(7, entity.getRotation());
        statement.bindString(8, entity.getLabel());
        statement.bindString(9, entity.getNotes());
        statement.bindString(10, entity.getLayerType());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindString(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeletePlacementById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM symbol_placements WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPlacement(final SymbolPlacementEntity placement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSymbolPlacementEntity.insert(placement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPlacements(final List<SymbolPlacementEntity> placements,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSymbolPlacementEntity.insert(placements);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlacement(final SymbolPlacementEntity placement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSymbolPlacementEntity.handle(placement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlacement(final SymbolPlacementEntity placement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSymbolPlacementEntity.handle(placement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlacementById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlacementById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePlacementById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SymbolPlacementEntity>> getPlacementsForProject(final String projectId) {
    final String _sql = "SELECT sp.* FROM symbol_placements sp INNER JOIN drawings d ON sp.drawingId = d.id WHERE d.projectId = ? ORDER BY sp.createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbol_placements",
        "drawings"}, new Callable<List<SymbolPlacementEntity>>() {
      @Override
      @NonNull
      public List<SymbolPlacementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfSymbolId = CursorUtil.getColumnIndexOrThrow(_cursor, "symbolId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final int _cursorIndexOfRotation = CursorUtil.getColumnIndexOrThrow(_cursor, "rotation");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<SymbolPlacementEntity> _result = new ArrayList<SymbolPlacementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolPlacementEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpSymbolId;
            _tmpSymbolId = _cursor.getString(_cursorIndexOfSymbolId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final float _tmpXNorm;
            _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            final float _tmpYNorm;
            _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            final float _tmpRotation;
            _tmpRotation = _cursor.getFloat(_cursorIndexOfRotation);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpLayerType;
            _tmpLayerType = _cursor.getString(_cursorIndexOfLayerType);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new SymbolPlacementEntity(_tmpId,_tmpDrawingId,_tmpSymbolId,_tmpProductId,_tmpXNorm,_tmpYNorm,_tmpRotation,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Flow<List<SymbolPlacementEntity>> getPlacementsForDrawing(final String drawingId) {
    final String _sql = "SELECT * FROM symbol_placements WHERE drawingId = ? ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbol_placements"}, new Callable<List<SymbolPlacementEntity>>() {
      @Override
      @NonNull
      public List<SymbolPlacementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfSymbolId = CursorUtil.getColumnIndexOrThrow(_cursor, "symbolId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final int _cursorIndexOfRotation = CursorUtil.getColumnIndexOrThrow(_cursor, "rotation");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<SymbolPlacementEntity> _result = new ArrayList<SymbolPlacementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolPlacementEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpSymbolId;
            _tmpSymbolId = _cursor.getString(_cursorIndexOfSymbolId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final float _tmpXNorm;
            _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            final float _tmpYNorm;
            _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            final float _tmpRotation;
            _tmpRotation = _cursor.getFloat(_cursorIndexOfRotation);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpLayerType;
            _tmpLayerType = _cursor.getString(_cursorIndexOfLayerType);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new SymbolPlacementEntity(_tmpId,_tmpDrawingId,_tmpSymbolId,_tmpProductId,_tmpXNorm,_tmpYNorm,_tmpRotation,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Flow<List<SymbolPlacementEntity>> getPlacementsForDrawingByLayer(final String drawingId,
      final String layerType) {
    final String _sql = "SELECT * FROM symbol_placements WHERE drawingId = ? AND layerType = ? ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    _argIndex = 2;
    _statement.bindString(_argIndex, layerType);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbol_placements"}, new Callable<List<SymbolPlacementEntity>>() {
      @Override
      @NonNull
      public List<SymbolPlacementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfSymbolId = CursorUtil.getColumnIndexOrThrow(_cursor, "symbolId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final int _cursorIndexOfRotation = CursorUtil.getColumnIndexOrThrow(_cursor, "rotation");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<SymbolPlacementEntity> _result = new ArrayList<SymbolPlacementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolPlacementEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpSymbolId;
            _tmpSymbolId = _cursor.getString(_cursorIndexOfSymbolId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final float _tmpXNorm;
            _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            final float _tmpYNorm;
            _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            final float _tmpRotation;
            _tmpRotation = _cursor.getFloat(_cursorIndexOfRotation);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpLayerType;
            _tmpLayerType = _cursor.getString(_cursorIndexOfLayerType);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new SymbolPlacementEntity(_tmpId,_tmpDrawingId,_tmpSymbolId,_tmpProductId,_tmpXNorm,_tmpYNorm,_tmpRotation,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Object getPlacementById(final String id,
      final Continuation<? super SymbolPlacementEntity> $completion) {
    final String _sql = "SELECT * FROM symbol_placements WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SymbolPlacementEntity>() {
      @Override
      @Nullable
      public SymbolPlacementEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfSymbolId = CursorUtil.getColumnIndexOrThrow(_cursor, "symbolId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final int _cursorIndexOfRotation = CursorUtil.getColumnIndexOrThrow(_cursor, "rotation");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final SymbolPlacementEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpSymbolId;
            _tmpSymbolId = _cursor.getString(_cursorIndexOfSymbolId);
            final String _tmpProductId;
            if (_cursor.isNull(_cursorIndexOfProductId)) {
              _tmpProductId = null;
            } else {
              _tmpProductId = _cursor.getString(_cursorIndexOfProductId);
            }
            final float _tmpXNorm;
            _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            final float _tmpYNorm;
            _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            final float _tmpRotation;
            _tmpRotation = _cursor.getFloat(_cursorIndexOfRotation);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpLayerType;
            _tmpLayerType = _cursor.getString(_cursorIndexOfLayerType);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new SymbolPlacementEntity(_tmpId,_tmpDrawingId,_tmpSymbolId,_tmpProductId,_tmpXNorm,_tmpYNorm,_tmpRotation,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Flow<Integer> getPlacementCountForDrawing(final String drawingId) {
    final String _sql = "SELECT COUNT(*) FROM symbol_placements WHERE drawingId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbol_placements"}, new Callable<Integer>() {
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
