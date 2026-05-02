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
import se.secureplan.app.core.data.local.entity.ProtocolTemplateEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProtocolTemplateDao_Impl implements ProtocolTemplateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProtocolTemplateEntity> __insertionAdapterOfProtocolTemplateEntity;

  private final EntityDeletionOrUpdateAdapter<ProtocolTemplateEntity> __deletionAdapterOfProtocolTemplateEntity;

  private final EntityDeletionOrUpdateAdapter<ProtocolTemplateEntity> __updateAdapterOfProtocolTemplateEntity;

  public ProtocolTemplateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProtocolTemplateEntity = new EntityInsertionAdapter<ProtocolTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `protocol_templates` (`id`,`name`,`systemCategory`,`description`,`fieldsJson`,`version`,`isBuiltIn`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolTemplateEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getSystemCategory());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getFieldsJson());
        statement.bindLong(6, entity.getVersion());
        final int _tmp = entity.isBuiltIn() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfProtocolTemplateEntity = new EntityDeletionOrUpdateAdapter<ProtocolTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `protocol_templates` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolTemplateEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfProtocolTemplateEntity = new EntityDeletionOrUpdateAdapter<ProtocolTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `protocol_templates` SET `id` = ?,`name` = ?,`systemCategory` = ?,`description` = ?,`fieldsJson` = ?,`version` = ?,`isBuiltIn` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtocolTemplateEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getSystemCategory());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getFieldsJson());
        statement.bindLong(6, entity.getVersion());
        final int _tmp = entity.isBuiltIn() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindString(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertTemplate(final ProtocolTemplateEntity template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProtocolTemplateEntity.insert(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTemplates(final List<ProtocolTemplateEntity> templates,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProtocolTemplateEntity.insert(templates);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTemplate(final ProtocolTemplateEntity template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProtocolTemplateEntity.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTemplate(final ProtocolTemplateEntity template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProtocolTemplateEntity.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProtocolTemplateEntity>> getAllTemplates() {
    final String _sql = "SELECT * FROM protocol_templates ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_templates"}, new Callable<List<ProtocolTemplateEntity>>() {
      @Override
      @NonNull
      public List<ProtocolTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuiltIn");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProtocolTemplateEntity> _result = new ArrayList<ProtocolTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtocolTemplateEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final int _tmpVersion;
            _tmpVersion = _cursor.getInt(_cursorIndexOfVersion);
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProtocolTemplateEntity(_tmpId,_tmpName,_tmpSystemCategory,_tmpDescription,_tmpFieldsJson,_tmpVersion,_tmpIsBuiltIn,_tmpCreatedAt);
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
  public Flow<List<ProtocolTemplateEntity>> getTemplatesByCategory(final String category) {
    final String _sql = "SELECT * FROM protocol_templates WHERE systemCategory = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_templates"}, new Callable<List<ProtocolTemplateEntity>>() {
      @Override
      @NonNull
      public List<ProtocolTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuiltIn");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProtocolTemplateEntity> _result = new ArrayList<ProtocolTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtocolTemplateEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final int _tmpVersion;
            _tmpVersion = _cursor.getInt(_cursorIndexOfVersion);
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProtocolTemplateEntity(_tmpId,_tmpName,_tmpSystemCategory,_tmpDescription,_tmpFieldsJson,_tmpVersion,_tmpIsBuiltIn,_tmpCreatedAt);
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
  public Flow<List<ProtocolTemplateEntity>> getBuiltInTemplates() {
    final String _sql = "SELECT * FROM protocol_templates WHERE isBuiltIn = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"protocol_templates"}, new Callable<List<ProtocolTemplateEntity>>() {
      @Override
      @NonNull
      public List<ProtocolTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuiltIn");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProtocolTemplateEntity> _result = new ArrayList<ProtocolTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtocolTemplateEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final int _tmpVersion;
            _tmpVersion = _cursor.getInt(_cursorIndexOfVersion);
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProtocolTemplateEntity(_tmpId,_tmpName,_tmpSystemCategory,_tmpDescription,_tmpFieldsJson,_tmpVersion,_tmpIsBuiltIn,_tmpCreatedAt);
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
  public Object getTemplateById(final String id,
      final Continuation<? super ProtocolTemplateEntity> $completion) {
    final String _sql = "SELECT * FROM protocol_templates WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ProtocolTemplateEntity>() {
      @Override
      @Nullable
      public ProtocolTemplateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfFieldsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "fieldsJson");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsBuiltIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuiltIn");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ProtocolTemplateEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpFieldsJson;
            _tmpFieldsJson = _cursor.getString(_cursorIndexOfFieldsJson);
            final int _tmpVersion;
            _tmpVersion = _cursor.getInt(_cursorIndexOfVersion);
            final boolean _tmpIsBuiltIn;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuiltIn);
            _tmpIsBuiltIn = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ProtocolTemplateEntity(_tmpId,_tmpName,_tmpSystemCategory,_tmpDescription,_tmpFieldsJson,_tmpVersion,_tmpIsBuiltIn,_tmpCreatedAt);
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
  public Object getTemplateCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM protocol_templates";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
