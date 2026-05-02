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
import java.lang.Double;
import java.lang.Exception;
import java.lang.Float;
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
import se.secureplan.app.core.data.local.entity.GeoPhotoEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GeoPhotoDao_Impl implements GeoPhotoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GeoPhotoEntity> __insertionAdapterOfGeoPhotoEntity;

  private final EntityDeletionOrUpdateAdapter<GeoPhotoEntity> __deletionAdapterOfGeoPhotoEntity;

  private final EntityDeletionOrUpdateAdapter<GeoPhotoEntity> __updateAdapterOfGeoPhotoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeletePhotoById;

  public GeoPhotoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGeoPhotoEntity = new EntityInsertionAdapter<GeoPhotoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `geo_photos` (`id`,`projectId`,`drawingId`,`photoUri`,`latitude`,`longitude`,`caption`,`takenAt`,`xNorm`,`yNorm`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeoPhotoEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        if (entity.getDrawingId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDrawingId());
        }
        statement.bindString(4, entity.getPhotoUri());
        if (entity.getLatitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLongitude());
        }
        statement.bindString(7, entity.getCaption());
        statement.bindLong(8, entity.getTakenAt());
        if (entity.getXNorm() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getXNorm());
        }
        if (entity.getYNorm() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getYNorm());
        }
      }
    };
    this.__deletionAdapterOfGeoPhotoEntity = new EntityDeletionOrUpdateAdapter<GeoPhotoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `geo_photos` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeoPhotoEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfGeoPhotoEntity = new EntityDeletionOrUpdateAdapter<GeoPhotoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `geo_photos` SET `id` = ?,`projectId` = ?,`drawingId` = ?,`photoUri` = ?,`latitude` = ?,`longitude` = ?,`caption` = ?,`takenAt` = ?,`xNorm` = ?,`yNorm` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GeoPhotoEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        if (entity.getDrawingId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDrawingId());
        }
        statement.bindString(4, entity.getPhotoUri());
        if (entity.getLatitude() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLongitude());
        }
        statement.bindString(7, entity.getCaption());
        statement.bindLong(8, entity.getTakenAt());
        if (entity.getXNorm() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getXNorm());
        }
        if (entity.getYNorm() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getYNorm());
        }
        statement.bindString(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeletePhotoById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM geo_photos WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPhoto(final GeoPhotoEntity photo,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGeoPhotoEntity.insert(photo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhoto(final GeoPhotoEntity photo,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfGeoPhotoEntity.handle(photo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePhoto(final GeoPhotoEntity photo,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGeoPhotoEntity.handle(photo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhotoById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePhotoById.acquire();
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
          __preparedStmtOfDeletePhotoById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<GeoPhotoEntity>> getPhotosForProject(final String projectId) {
    final String _sql = "SELECT * FROM geo_photos WHERE projectId = ? ORDER BY takenAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"geo_photos"}, new Callable<List<GeoPhotoEntity>>() {
      @Override
      @NonNull
      public List<GeoPhotoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfTakenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "takenAt");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final List<GeoPhotoEntity> _result = new ArrayList<GeoPhotoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeoPhotoEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpDrawingId;
            if (_cursor.isNull(_cursorIndexOfDrawingId)) {
              _tmpDrawingId = null;
            } else {
              _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            }
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCaption;
            _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            final long _tmpTakenAt;
            _tmpTakenAt = _cursor.getLong(_cursorIndexOfTakenAt);
            final Float _tmpXNorm;
            if (_cursor.isNull(_cursorIndexOfXNorm)) {
              _tmpXNorm = null;
            } else {
              _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            }
            final Float _tmpYNorm;
            if (_cursor.isNull(_cursorIndexOfYNorm)) {
              _tmpYNorm = null;
            } else {
              _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            }
            _item = new GeoPhotoEntity(_tmpId,_tmpProjectId,_tmpDrawingId,_tmpPhotoUri,_tmpLatitude,_tmpLongitude,_tmpCaption,_tmpTakenAt,_tmpXNorm,_tmpYNorm);
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
  public Flow<List<GeoPhotoEntity>> getPhotosForDrawing(final String drawingId) {
    final String _sql = "SELECT * FROM geo_photos WHERE drawingId = ? ORDER BY takenAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, drawingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"geo_photos"}, new Callable<List<GeoPhotoEntity>>() {
      @Override
      @NonNull
      public List<GeoPhotoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfTakenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "takenAt");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final List<GeoPhotoEntity> _result = new ArrayList<GeoPhotoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GeoPhotoEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpDrawingId;
            if (_cursor.isNull(_cursorIndexOfDrawingId)) {
              _tmpDrawingId = null;
            } else {
              _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            }
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCaption;
            _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            final long _tmpTakenAt;
            _tmpTakenAt = _cursor.getLong(_cursorIndexOfTakenAt);
            final Float _tmpXNorm;
            if (_cursor.isNull(_cursorIndexOfXNorm)) {
              _tmpXNorm = null;
            } else {
              _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            }
            final Float _tmpYNorm;
            if (_cursor.isNull(_cursorIndexOfYNorm)) {
              _tmpYNorm = null;
            } else {
              _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            }
            _item = new GeoPhotoEntity(_tmpId,_tmpProjectId,_tmpDrawingId,_tmpPhotoUri,_tmpLatitude,_tmpLongitude,_tmpCaption,_tmpTakenAt,_tmpXNorm,_tmpYNorm);
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
  public Object getPhotoById(final String id,
      final Continuation<? super GeoPhotoEntity> $completion) {
    final String _sql = "SELECT * FROM geo_photos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GeoPhotoEntity>() {
      @Override
      @Nullable
      public GeoPhotoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfDrawingId = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingId");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfTakenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "takenAt");
          final int _cursorIndexOfXNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "xNorm");
          final int _cursorIndexOfYNorm = CursorUtil.getColumnIndexOrThrow(_cursor, "yNorm");
          final GeoPhotoEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpDrawingId;
            if (_cursor.isNull(_cursorIndexOfDrawingId)) {
              _tmpDrawingId = null;
            } else {
              _tmpDrawingId = _cursor.getString(_cursorIndexOfDrawingId);
            }
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpCaption;
            _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            final long _tmpTakenAt;
            _tmpTakenAt = _cursor.getLong(_cursorIndexOfTakenAt);
            final Float _tmpXNorm;
            if (_cursor.isNull(_cursorIndexOfXNorm)) {
              _tmpXNorm = null;
            } else {
              _tmpXNorm = _cursor.getFloat(_cursorIndexOfXNorm);
            }
            final Float _tmpYNorm;
            if (_cursor.isNull(_cursorIndexOfYNorm)) {
              _tmpYNorm = null;
            } else {
              _tmpYNorm = _cursor.getFloat(_cursorIndexOfYNorm);
            }
            _result = new GeoPhotoEntity(_tmpId,_tmpProjectId,_tmpDrawingId,_tmpPhotoUri,_tmpLatitude,_tmpLongitude,_tmpCaption,_tmpTakenAt,_tmpXNorm,_tmpYNorm);
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
  public Flow<Integer> getPhotoCountForProject(final String projectId) {
    final String _sql = "SELECT COUNT(*) FROM geo_photos WHERE projectId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"geo_photos"}, new Callable<Integer>() {
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
