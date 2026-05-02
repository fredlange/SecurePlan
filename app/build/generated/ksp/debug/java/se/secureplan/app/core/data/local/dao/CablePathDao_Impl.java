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
import se.secureplan.app.core.data.local.entity.CablePathEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CablePathDao_Impl implements CablePathDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CablePathEntity> __insertionAdapterOfCablePathEntity;

  private final EntityDeletionOrUpdateAdapter<CablePathEntity> __deletionAdapterOfCablePathEntity;

  private final EntityDeletionOrUpdateAdapter<CablePathEntity> __updateAdapterOfCablePathEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCablePathById;

  public CablePathDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCablePathEntity = new EntityInsertionAdapter<CablePathEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cable_paths` (`id`,`drawingId`,`pointsJson`,`cableType`,`colorHex`,`strokeWidth`,`label`,`notes`,`layerType`,`isVisible`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CablePathEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getPointsJson());
        statement.bindString(4, entity.getCableType());
        statement.bindString(5, entity.getColorHex());
        statement.bindDouble(6, entity.getStrokeWidth());
        statement.bindString(7, entity.getLabel());
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getLayerType());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfCablePathEntity = new EntityDeletionOrUpdateAdapter<CablePathEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `cable_paths` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CablePathEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfCablePathEntity = new EntityDeletionOrUpdateAdapter<CablePathEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cable_paths` SET `id` = ?,`drawingId` = ?,`pointsJson` = ?,`cableType` = ?,`colorHex` = ?,`strokeWidth` = ?,`label` = ?,`notes` = ?,`layerType` = ?,`isVisible` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CablePathEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getPointsJson());
        statement.bindString(4, entity.getCableType());
        statement.bindString(5, entity.getColorHex());
        statement.bindDouble(6, entity.getStrokeWidth());
        statement.bindString(7, entity.getLabel());
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getLayerType());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindString(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteCablePathById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cable_paths WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCablePath(final CablePathEntity path,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCablePathEntity.insert(path);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCablePath(final CablePathEntity path,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCablePathEntity.handle(path);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCablePath(final CablePathEntity path,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCablePathEntity.handle(path);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCablePathById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCablePathById.acquire();
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
          __preparedStmtOfDeleteCablePathById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CablePathEntity>> getCablePathsForDrawing(final String drawingId) {
    final String _sql = "SELECT * FROM cable_paths WHERE drawingId = ? ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cable_paths"}, new Callable<List<CablePathEntity>>() {
      @Override
      @NonNull
      public List<CablePathEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfPointsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "pointsJson");
          final int _cursorIndexOfCableType = CursorUtil.getColumnIndexOrThrow(_cursor, "cableType");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfStrokeWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeWidth");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CablePathEntity> _result = new ArrayList<CablePathEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CablePathEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpPointsJson;
            _tmpPointsJson = _cursor.getString(_cursorIndexOfPointsJson);
            final String _tmpCableType;
            _tmpCableType = _cursor.getString(_cursorIndexOfCableType);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final float _tmpStrokeWidth;
            _tmpStrokeWidth = _cursor.getFloat(_cursorIndexOfStrokeWidth);
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
            _item = new CablePathEntity(_tmpId,_tmpDrawingId,_tmpPointsJson,_tmpCableType,_tmpColorHex,_tmpStrokeWidth,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Object getCablePathById(final String id,
      final Continuation<? super CablePathEntity> $completion) {
    final String _sql = "SELECT * FROM cable_paths WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CablePathEntity>() {
      @Override
      @Nullable
      public CablePathEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfPointsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "pointsJson");
          final int _cursorIndexOfCableType = CursorUtil.getColumnIndexOrThrow(_cursor, "cableType");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfStrokeWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeWidth");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfLayerType = CursorUtil.getColumnIndexOrThrow(_cursor, "layerType");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final CablePathEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpPointsJson;
            _tmpPointsJson = _cursor.getString(_cursorIndexOfPointsJson);
            final String _tmpCableType;
            _tmpCableType = _cursor.getString(_cursorIndexOfCableType);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final float _tmpStrokeWidth;
            _tmpStrokeWidth = _cursor.getFloat(_cursorIndexOfStrokeWidth);
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
            _result = new CablePathEntity(_tmpId,_tmpDrawingId,_tmpPointsJson,_tmpCableType,_tmpColorHex,_tmpStrokeWidth,_tmpLabel,_tmpNotes,_tmpLayerType,_tmpIsVisible,_tmpCreatedAt);
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
  public Flow<Integer> getCablePathCountForDrawing(final String drawingId) {
    final String _sql = "SELECT COUNT(*) FROM cable_paths WHERE drawingId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cable_paths"}, new Callable<Integer>() {
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
