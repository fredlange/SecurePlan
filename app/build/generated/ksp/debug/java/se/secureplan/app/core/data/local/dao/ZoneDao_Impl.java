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
import se.secureplan.app.core.data.local.entity.ZoneEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ZoneDao_Impl implements ZoneDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ZoneEntity> __insertionAdapterOfZoneEntity;

  private final EntityDeletionOrUpdateAdapter<ZoneEntity> __deletionAdapterOfZoneEntity;

  private final EntityDeletionOrUpdateAdapter<ZoneEntity> __updateAdapterOfZoneEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteZoneById;

  public ZoneDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfZoneEntity = new EntityInsertionAdapter<ZoneEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `zones` (`id`,`drawingId`,`name`,`zoneNumber`,`polygonJson`,`fillColorHex`,`fillAlpha`,`strokeColorHex`,`notes`,`isVisible`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ZoneEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getZoneNumber());
        statement.bindString(5, entity.getPolygonJson());
        statement.bindString(6, entity.getFillColorHex());
        statement.bindDouble(7, entity.getFillAlpha());
        statement.bindString(8, entity.getStrokeColorHex());
        statement.bindString(9, entity.getNotes());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfZoneEntity = new EntityDeletionOrUpdateAdapter<ZoneEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `zones` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ZoneEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfZoneEntity = new EntityDeletionOrUpdateAdapter<ZoneEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `zones` SET `id` = ?,`drawingId` = ?,`name` = ?,`zoneNumber` = ?,`polygonJson` = ?,`fillColorHex` = ?,`fillAlpha` = ?,`strokeColorHex` = ?,`notes` = ?,`isVisible` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ZoneEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getDrawingId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getZoneNumber());
        statement.bindString(5, entity.getPolygonJson());
        statement.bindString(6, entity.getFillColorHex());
        statement.bindDouble(7, entity.getFillAlpha());
        statement.bindString(8, entity.getStrokeColorHex());
        statement.bindString(9, entity.getNotes());
        final int _tmp = entity.isVisible() ? 1 : 0;
        statement.bindLong(10, _tmp);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindString(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteZoneById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM zones WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertZone(final ZoneEntity zone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfZoneEntity.insert(zone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteZone(final ZoneEntity zone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfZoneEntity.handle(zone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateZone(final ZoneEntity zone, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfZoneEntity.handle(zone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteZoneById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteZoneById.acquire();
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
          __preparedStmtOfDeleteZoneById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ZoneEntity>> getZonesForDrawing(final String drawingId) {
    final String _sql = "SELECT * FROM zones WHERE drawingId = ? ORDER BY zoneNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"zones"}, new Callable<List<ZoneEntity>>() {
      @Override
      @NonNull
      public List<ZoneEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfZoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneNumber");
          final int _cursorIndexOfPolygonJson = CursorUtil.getColumnIndexOrThrow(_cursor, "polygonJson");
          final int _cursorIndexOfFillColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "fillColorHex");
          final int _cursorIndexOfFillAlpha = CursorUtil.getColumnIndexOrThrow(_cursor, "fillAlpha");
          final int _cursorIndexOfStrokeColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeColorHex");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ZoneEntity> _result = new ArrayList<ZoneEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ZoneEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpZoneNumber;
            _tmpZoneNumber = _cursor.getInt(_cursorIndexOfZoneNumber);
            final String _tmpPolygonJson;
            _tmpPolygonJson = _cursor.getString(_cursorIndexOfPolygonJson);
            final String _tmpFillColorHex;
            _tmpFillColorHex = _cursor.getString(_cursorIndexOfFillColorHex);
            final float _tmpFillAlpha;
            _tmpFillAlpha = _cursor.getFloat(_cursorIndexOfFillAlpha);
            final String _tmpStrokeColorHex;
            _tmpStrokeColorHex = _cursor.getString(_cursorIndexOfStrokeColorHex);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ZoneEntity(_tmpId,_tmpDrawingId,_tmpName,_tmpZoneNumber,_tmpPolygonJson,_tmpFillColorHex,_tmpFillAlpha,_tmpStrokeColorHex,_tmpNotes,_tmpIsVisible,_tmpCreatedAt);
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
  public Object getZoneById(final String id, final Continuation<? super ZoneEntity> $completion) {
    final String _sql = "SELECT * FROM zones WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ZoneEntity>() {
      @Override
      @Nullable
      public ZoneEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfZoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "zoneNumber");
          final int _cursorIndexOfPolygonJson = CursorUtil.getColumnIndexOrThrow(_cursor, "polygonJson");
          final int _cursorIndexOfFillColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "fillColorHex");
          final int _cursorIndexOfFillAlpha = CursorUtil.getColumnIndexOrThrow(_cursor, "fillAlpha");
          final int _cursorIndexOfStrokeColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "strokeColorHex");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsVisible = CursorUtil.getColumnIndexOrThrow(_cursor, "isVisible");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ZoneEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpDrawingId;
            _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpZoneNumber;
            _tmpZoneNumber = _cursor.getInt(_cursorIndexOfZoneNumber);
            final String _tmpPolygonJson;
            _tmpPolygonJson = _cursor.getString(_cursorIndexOfPolygonJson);
            final String _tmpFillColorHex;
            _tmpFillColorHex = _cursor.getString(_cursorIndexOfFillColorHex);
            final float _tmpFillAlpha;
            _tmpFillAlpha = _cursor.getFloat(_cursorIndexOfFillAlpha);
            final String _tmpStrokeColorHex;
            _tmpStrokeColorHex = _cursor.getString(_cursorIndexOfStrokeColorHex);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsVisible;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVisible);
            _tmpIsVisible = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ZoneEntity(_tmpId,_tmpDrawingId,_tmpName,_tmpZoneNumber,_tmpPolygonJson,_tmpFillColorHex,_tmpFillAlpha,_tmpStrokeColorHex,_tmpNotes,_tmpIsVisible,_tmpCreatedAt);
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
  public Object getMaxZoneNumber(final String drawingId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(zoneNumber) FROM zones WHERE drawingId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
