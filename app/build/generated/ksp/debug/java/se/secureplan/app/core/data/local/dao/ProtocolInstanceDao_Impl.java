package se.secureplan.app.core.data.local.dao;

import android.database.Cursor;
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
import java.lang.Long;
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
import se.secureplan.app.core.data.local.entity.ProtocolInstanceEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProtocolInstanceDao_Impl implements ProtocolInstanceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProtocolInstanceEntity> __insertionAdapterOfProtocolInstanceEntity;

  private final EntityDeletionOrUpdateAdapter<ProtocolInstanceEntity> __deletionAdapterOfProtocolInstanceEntity;

  private final EntityDeletionOrUpdateAdapter<ProtocolInstanceEntity> __updateAdapterOfProtocolInstanceEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteInstanceById;

  public ProtocolInstanceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProtocolInstanceEntity = new EntityInsertionAdapter<ProtocolInstanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `protocol_instances` (`id`,`projectId`,`templateId`,`valuesJson`,`status`,`signedBy`,`signedAt`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolInstanceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        statement.bindString(3, entity.getTemplateId());
        statement.bindString(4, entity.getValuesJson());
        statement.bindString(5, entity.getStatus());
        if (entity.getSignedBy() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSignedBy());
        }
        if (entity.getSignedAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getSignedAt());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfProtocolInstanceEntity = new EntityDeletionOrUpdateAdapter<ProtocolInstanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `protocol_instances` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolInstanceEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfProtocolInstanceEntity = new EntityDeletionOrUpdateAdapter<ProtocolInstanceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `protocol_instances` SET `id` = ?,`projectId` = ?,`templateId` = ?,`valuesJson` = ?,`status` = ?,`signedBy` = ?,`signedAt` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolInstanceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getProjectId());
        statement.bindString(3, entity.getTemplateId());
        statement.bindString(4, entity.getValuesJson());
        statement.bindString(5, entity.getStatus());
        if (entity.getSignedBy() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSignedBy());
        }
        if (entity.getSignedAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getSignedAt());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteInstanceById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM protocol_instances WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertInstance(final ProtocolInstanceEntity instance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProtocolInstanceEntity.insert(instance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteInstance(final ProtocolInstanceEntity instance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProtocolInstanceEntity.handle(instance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateInstance(final ProtocolInstanceEntity instance,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProtocolInstanceEntity.handle(instance);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteInstanceById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteInstanceById.acquire();
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
          __preparedStmtOfDeleteInstanceById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProtocolInstanceEntity>> getInstancesForProject(final String projectId) {
    final String _sql = "SELECT * FROM protocol_instances WHERE projectId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_instances"}, new Callable<List<ProtocolInstanceEntity>>() {
      @Override
      @NonNull
      public List<ProtocolInstanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfValuesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "valuesJson");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSignedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "signedBy");
          final int _cursorIndexOfSignedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "signedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ProtocolInstanceEntity> _result = new ArrayList<ProtocolInstanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtocolInstanceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpValuesJson;
            _tmpValuesJson = _cursor.getString(_cursorIndexOfValuesJson);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpSignedBy;
            if (_cursor.isNull(_cursorIndexOfSignedBy)) {
              _tmpSignedBy = null;
            } else {
              _tmpSignedBy = _cursor.getString(_cursorIndexOfSignedBy);
            }
            final Long _tmpSignedAt;
            if (_cursor.isNull(_cursorIndexOfSignedAt)) {
              _tmpSignedAt = null;
            } else {
              _tmpSignedAt = _cursor.getLong(_cursorIndexOfSignedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ProtocolInstanceEntity(_tmpId,_tmpProjectId,_tmpTemplateId,_tmpValuesJson,_tmpStatus,_tmpSignedBy,_tmpSignedAt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<ProtocolInstanceEntity>> getInstancesByStatus(final String projectId,
      final String status) {
    final String _sql = "SELECT * FROM protocol_instances WHERE projectId = ? AND status = ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, projectId);
    _argIndex = 2;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_instances"}, new Callable<List<ProtocolInstanceEntity>>() {
      @Override
      @NonNull
      public List<ProtocolInstanceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfValuesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "valuesJson");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSignedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "signedBy");
          final int _cursorIndexOfSignedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "signedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<ProtocolInstanceEntity> _result = new ArrayList<ProtocolInstanceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtocolInstanceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpValuesJson;
            _tmpValuesJson = _cursor.getString(_cursorIndexOfValuesJson);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpSignedBy;
            if (_cursor.isNull(_cursorIndexOfSignedBy)) {
              _tmpSignedBy = null;
            } else {
              _tmpSignedBy = _cursor.getString(_cursorIndexOfSignedBy);
            }
            final Long _tmpSignedAt;
            if (_cursor.isNull(_cursorIndexOfSignedAt)) {
              _tmpSignedAt = null;
            } else {
              _tmpSignedAt = _cursor.getLong(_cursorIndexOfSignedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ProtocolInstanceEntity(_tmpId,_tmpProjectId,_tmpTemplateId,_tmpValuesJson,_tmpStatus,_tmpSignedBy,_tmpSignedAt,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<ProtocolInstanceEntity> getInstanceById(final String id) {
    final String _sql = "SELECT * FROM protocol_instances WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_instances"}, new Callable<ProtocolInstanceEntity>() {
      @Override
      @Nullable
      public ProtocolInstanceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "projectId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfValuesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "valuesJson");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSignedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "signedBy");
          final int _cursorIndexOfSignedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "signedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final ProtocolInstanceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpProjectId;
            _tmpProjectId = _cursor.getString(_cursorIndexOfProjectId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpValuesJson;
            _tmpValuesJson = _cursor.getString(_cursorIndexOfValuesJson);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpSignedBy;
            if (_cursor.isNull(_cursorIndexOfSignedBy)) {
              _tmpSignedBy = null;
            } else {
              _tmpSignedBy = _cursor.getString(_cursorIndexOfSignedBy);
            }
            final Long _tmpSignedAt;
            if (_cursor.isNull(_cursorIndexOfSignedAt)) {
              _tmpSignedAt = null;
            } else {
              _tmpSignedAt = _cursor.getLong(_cursorIndexOfSignedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ProtocolInstanceEntity(_tmpId,_tmpProjectId,_tmpTemplateId,_tmpValuesJson,_tmpStatus,_tmpSignedBy,_tmpSignedAt,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
