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
import se.secureplan.app.core.data.local.entity.DrawingEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DrawingDao_Impl implements DrawingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DrawingEntity> __insertionAdapterOfDrawingEntity;

  private final EntityDeletionOrUpdateAdapter<DrawingEntity> __deletionAdapterOfDrawingEntity;

  private final EntityDeletionOrUpdateAdapter<DrawingEntity> __updateAdapterOfDrawingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDrawingById;

  public DrawingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDrawingEntity = new EntityInsertionAdapter<DrawingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `drawings` (`id`,`projectId`,`name`,`floor`,`backgroundUri`,`backgroundPageIndex`,`scaleMetersPerUnit`,`createdAt`,`updatedAt`,`width`,`height`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DrawingEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getFloor());
        if (entity.getBackgroundUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBackgroundUri());
        }
        statement.bindLong(6, entity.getBackgroundPageIndex());
        statement.bindDouble(7, entity.getScaleMetersPerUnit());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindDouble(10, entity.getWidth());
        statement.bindDouble(11, entity.getHeight());
      }
    };
    this.__deletionAdapterOfDrawingEntity = new EntityDeletionOrUpdateAdapter<DrawingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `drawings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DrawingEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfDrawingEntity = new EntityDeletionOrUpdateAdapter<DrawingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `drawings` SET `id` = ?,`projectId` = ?,`name` = ?,`floor` = ?,`backgroundUri` = ?,`backgroundPageIndex` = ?,`scaleMetersPerUnit` = ?,`createdAt` = ?,`updatedAt` = ?,`width` = ?,`height` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DrawingEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getFloor());
        if (entity.getBackgroundUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBackgroundUri());
        }
        statement.bindLong(6, entity.getBackgroundPageIndex());
        statement.bindDouble(7, entity.getScaleMetersPerUnit());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindDouble(10, entity.getWidth());
        statement.bindDouble(11, entity.getHeight());
        statement.bindString(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteDrawingById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM drawings WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertDrawing(final DrawingEntity drawing,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDrawingEntity.insert(drawing);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDrawing(final DrawingEntity drawing,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDrawingEntity.handle(drawing);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDrawing(final DrawingEntity drawing,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDrawingEntity.handle(drawing);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDrawingById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDrawingById.acquire();
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
          __preparedStmtOfDeleteDrawingById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DrawingEntity>> getDrawingsForProject(final String projectId) {
    final String _sql = "SELECT * FROM drawings WHERE projectId = ? ORDER BY floor ASC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"drawings"}, new Callable<List<DrawingEntity>>() {
      @Override
      @NonNull
      public List<DrawingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfBackgroundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundUri");
          final int _cursorIndexOfBackgroundPageIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundPageIndex");
          final int _cursorIndexOfScaleMetersPerUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "scaleMetersPerUnit");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final List<DrawingEntity> _result = new ArrayList<DrawingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DrawingEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            final String _tmpBackgroundUri;
            if (_cursor.isNull(_cursorIndexOfBackgroundUri)) {
              _tmpBackgroundUri = null;
            } else {
              _tmpBackgroundUri = _cursor.getString(_cursorIndexOfBackgroundUri);
            }
            final int _tmpBackgroundPageIndex;
            _tmpBackgroundPageIndex = _cursor.getInt(_cursorIndexOfBackgroundPageIndex);
            final float _tmpScaleMetersPerUnit;
            _tmpScaleMetersPerUnit = _cursor.getFloat(_cursorIndexOfScaleMetersPerUnit);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final float _tmpWidth;
            _tmpWidth = _cursor.getFloat(_cursorIndexOfWidth);
            final float _tmpHeight;
            _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            _item = new DrawingEntity(_tmpId,_tmpProjectId,_tmpName,_tmpFloor,_tmpBackgroundUri,_tmpBackgroundPageIndex,_tmpScaleMetersPerUnit,_tmpCreatedAt,_tmpUpdatedAt,_tmpWidth,_tmpHeight);
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
  public Flow<DrawingEntity> getDrawingById(final String id) {
    final String _sql = "SELECT * FROM drawings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"drawings"}, new Callable<DrawingEntity>() {
      @Override
      @Nullable
      public DrawingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfBackgroundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundUri");
          final int _cursorIndexOfBackgroundPageIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundPageIndex");
          final int _cursorIndexOfScaleMetersPerUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "scaleMetersPerUnit");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final DrawingEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            final String _tmpBackgroundUri;
            if (_cursor.isNull(_cursorIndexOfBackgroundUri)) {
              _tmpBackgroundUri = null;
            } else {
              _tmpBackgroundUri = _cursor.getString(_cursorIndexOfBackgroundUri);
            }
            final int _tmpBackgroundPageIndex;
            _tmpBackgroundPageIndex = _cursor.getInt(_cursorIndexOfBackgroundPageIndex);
            final float _tmpScaleMetersPerUnit;
            _tmpScaleMetersPerUnit = _cursor.getFloat(_cursorIndexOfScaleMetersPerUnit);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final float _tmpWidth;
            _tmpWidth = _cursor.getFloat(_cursorIndexOfWidth);
            final float _tmpHeight;
            _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            _result = new DrawingEntity(_tmpId,_tmpProjectId,_tmpName,_tmpFloor,_tmpBackgroundUri,_tmpBackgroundPageIndex,_tmpScaleMetersPerUnit,_tmpCreatedAt,_tmpUpdatedAt,_tmpWidth,_tmpHeight);
          } else {
            _result = null;
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
  public Object getDrawingByIdOnce(final String id,
      final Continuation<? super DrawingEntity> $completion) {
    final String _sql = "SELECT * FROM drawings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DrawingEntity>() {
      @Override
      @Nullable
      public DrawingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
          final int _cursorIndexOfBackgroundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundUri");
          final int _cursorIndexOfBackgroundPageIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "backgroundPageIndex");
          final int _cursorIndexOfScaleMetersPerUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "scaleMetersPerUnit");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWidth = CursorUtil.getColumnIndexOrThrow(_cursor, "width");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final DrawingEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpFloor;
            _tmpFloor = _cursor.getInt(_cursorIndexOfFloor);
            final String _tmpBackgroundUri;
            if (_cursor.isNull(_cursorIndexOfBackgroundUri)) {
              _tmpBackgroundUri = null;
            } else {
              _tmpBackgroundUri = _cursor.getString(_cursorIndexOfBackgroundUri);
            }
            final int _tmpBackgroundPageIndex;
            _tmpBackgroundPageIndex = _cursor.getInt(_cursorIndexOfBackgroundPageIndex);
            final float _tmpScaleMetersPerUnit;
            _tmpScaleMetersPerUnit = _cursor.getFloat(_cursorIndexOfScaleMetersPerUnit);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final float _tmpWidth;
            _tmpWidth = _cursor.getFloat(_cursorIndexOfWidth);
            final float _tmpHeight;
            _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            _result = new DrawingEntity(_tmpId,_tmpProjectId,_tmpName,_tmpFloor,_tmpBackgroundUri,_tmpBackgroundPageIndex,_tmpScaleMetersPerUnit,_tmpCreatedAt,_tmpUpdatedAt,_tmpWidth,_tmpHeight);
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
  public Flow<Integer> getDrawingCountForProject(final String projectId) {
    final String _sql = "SELECT COUNT(*) FROM drawings WHERE projectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"drawings"}, new Callable<Integer>() {
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
