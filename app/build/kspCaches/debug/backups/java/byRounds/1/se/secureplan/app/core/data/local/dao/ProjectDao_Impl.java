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
import se.secureplan.app.core.data.local.entity.ProjectEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProjectDao_Impl implements ProjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProjectEntity> __insertionAdapterOfProjectEntity;

  private final EntityDeletionOrUpdateAdapter<ProjectEntity> __deletionAdapterOfProjectEntity;

  private final EntityDeletionOrUpdateAdapter<ProjectEntity> __updateAdapterOfProjectEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProjectById;

  public ProjectDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProjectEntity = new EntityInsertionAdapter<ProjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `projects` (`id`,`name`,`clientName`,`address`,`description`,`systemCategory`,`status`,`createdAt`,`updatedAt`,`installerName`,`installerCompany`,`installerEmail`,`installerPhone`,`coverImageUri`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProjectEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getClientName());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getDescription());
        statement.bindString(6, entity.getSystemCategory());
        statement.bindString(7, entity.getStatus());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getInstallerName());
        statement.bindString(11, entity.getInstallerCompany());
        statement.bindString(12, entity.getInstallerEmail());
        statement.bindString(13, entity.getInstallerPhone());
        if (entity.getCoverImageUri() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getCoverImageUri());
        }
      }
    };
    this.__deletionAdapterOfProjectEntity = new EntityDeletionOrUpdateAdapter<ProjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `projects` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProjectEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfProjectEntity = new EntityDeletionOrUpdateAdapter<ProjectEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `projects` SET `id` = ?,`name` = ?,`clientName` = ?,`address` = ?,`description` = ?,`systemCategory` = ?,`status` = ?,`createdAt` = ?,`updatedAt` = ?,`installerName` = ?,`installerCompany` = ?,`installerEmail` = ?,`installerPhone` = ?,`coverImageUri` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProjectEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getClientName());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getDescription());
        statement.bindString(6, entity.getSystemCategory());
        statement.bindString(7, entity.getStatus());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getInstallerName());
        statement.bindString(11, entity.getInstallerCompany());
        statement.bindString(12, entity.getInstallerEmail());
        statement.bindString(13, entity.getInstallerPhone());
        if (entity.getCoverImageUri() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getCoverImageUri());
        }
        statement.bindString(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteProjectById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM projects WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertProject(final ProjectEntity project,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProjectEntity.insert(project);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProject(final ProjectEntity project,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProjectEntity.handle(project);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProject(final ProjectEntity project,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProjectEntity.handle(project);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProjectById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProjectById.acquire();
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
          __preparedStmtOfDeleteProjectById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProjectEntity>> getAllProjects() {
    final String _sql = "SELECT * FROM projects ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<List<ProjectEntity>>() {
      @Override
      @NonNull
      public List<ProjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfInstallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "installerName");
          final int _cursorIndexOfInstallerCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "installerCompany");
          final int _cursorIndexOfInstallerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "installerEmail");
          final int _cursorIndexOfInstallerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "installerPhone");
          final int _cursorIndexOfCoverImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUri");
          final List<ProjectEntity> _result = new ArrayList<ProjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProjectEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpInstallerName;
            _tmpInstallerName = _cursor.getString(_cursorIndexOfInstallerName);
            final String _tmpInstallerCompany;
            _tmpInstallerCompany = _cursor.getString(_cursorIndexOfInstallerCompany);
            final String _tmpInstallerEmail;
            _tmpInstallerEmail = _cursor.getString(_cursorIndexOfInstallerEmail);
            final String _tmpInstallerPhone;
            _tmpInstallerPhone = _cursor.getString(_cursorIndexOfInstallerPhone);
            final String _tmpCoverImageUri;
            if (_cursor.isNull(_cursorIndexOfCoverImageUri)) {
              _tmpCoverImageUri = null;
            } else {
              _tmpCoverImageUri = _cursor.getString(_cursorIndexOfCoverImageUri);
            }
            _item = new ProjectEntity(_tmpId,_tmpName,_tmpClientName,_tmpAddress,_tmpDescription,_tmpSystemCategory,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallerName,_tmpInstallerCompany,_tmpInstallerEmail,_tmpInstallerPhone,_tmpCoverImageUri);
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
  public Flow<List<ProjectEntity>> getProjectsByCategory(final String category) {
    final String _sql = "SELECT * FROM projects WHERE systemCategory = ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<List<ProjectEntity>>() {
      @Override
      @NonNull
      public List<ProjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfInstallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "installerName");
          final int _cursorIndexOfInstallerCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "installerCompany");
          final int _cursorIndexOfInstallerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "installerEmail");
          final int _cursorIndexOfInstallerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "installerPhone");
          final int _cursorIndexOfCoverImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUri");
          final List<ProjectEntity> _result = new ArrayList<ProjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProjectEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpInstallerName;
            _tmpInstallerName = _cursor.getString(_cursorIndexOfInstallerName);
            final String _tmpInstallerCompany;
            _tmpInstallerCompany = _cursor.getString(_cursorIndexOfInstallerCompany);
            final String _tmpInstallerEmail;
            _tmpInstallerEmail = _cursor.getString(_cursorIndexOfInstallerEmail);
            final String _tmpInstallerPhone;
            _tmpInstallerPhone = _cursor.getString(_cursorIndexOfInstallerPhone);
            final String _tmpCoverImageUri;
            if (_cursor.isNull(_cursorIndexOfCoverImageUri)) {
              _tmpCoverImageUri = null;
            } else {
              _tmpCoverImageUri = _cursor.getString(_cursorIndexOfCoverImageUri);
            }
            _item = new ProjectEntity(_tmpId,_tmpName,_tmpClientName,_tmpAddress,_tmpDescription,_tmpSystemCategory,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallerName,_tmpInstallerCompany,_tmpInstallerEmail,_tmpInstallerPhone,_tmpCoverImageUri);
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
  public Flow<List<ProjectEntity>> getProjectsByStatus(final String status) {
    final String _sql = "SELECT * FROM projects WHERE status = ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<List<ProjectEntity>>() {
      @Override
      @NonNull
      public List<ProjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfInstallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "installerName");
          final int _cursorIndexOfInstallerCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "installerCompany");
          final int _cursorIndexOfInstallerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "installerEmail");
          final int _cursorIndexOfInstallerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "installerPhone");
          final int _cursorIndexOfCoverImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUri");
          final List<ProjectEntity> _result = new ArrayList<ProjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProjectEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpInstallerName;
            _tmpInstallerName = _cursor.getString(_cursorIndexOfInstallerName);
            final String _tmpInstallerCompany;
            _tmpInstallerCompany = _cursor.getString(_cursorIndexOfInstallerCompany);
            final String _tmpInstallerEmail;
            _tmpInstallerEmail = _cursor.getString(_cursorIndexOfInstallerEmail);
            final String _tmpInstallerPhone;
            _tmpInstallerPhone = _cursor.getString(_cursorIndexOfInstallerPhone);
            final String _tmpCoverImageUri;
            if (_cursor.isNull(_cursorIndexOfCoverImageUri)) {
              _tmpCoverImageUri = null;
            } else {
              _tmpCoverImageUri = _cursor.getString(_cursorIndexOfCoverImageUri);
            }
            _item = new ProjectEntity(_tmpId,_tmpName,_tmpClientName,_tmpAddress,_tmpDescription,_tmpSystemCategory,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallerName,_tmpInstallerCompany,_tmpInstallerEmail,_tmpInstallerPhone,_tmpCoverImageUri);
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
  public Flow<ProjectEntity> getProjectById(final String id) {
    final String _sql = "SELECT * FROM projects WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<ProjectEntity>() {
      @Override
      @Nullable
      public ProjectEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfInstallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "installerName");
          final int _cursorIndexOfInstallerCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "installerCompany");
          final int _cursorIndexOfInstallerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "installerEmail");
          final int _cursorIndexOfInstallerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "installerPhone");
          final int _cursorIndexOfCoverImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUri");
          final ProjectEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpInstallerName;
            _tmpInstallerName = _cursor.getString(_cursorIndexOfInstallerName);
            final String _tmpInstallerCompany;
            _tmpInstallerCompany = _cursor.getString(_cursorIndexOfInstallerCompany);
            final String _tmpInstallerEmail;
            _tmpInstallerEmail = _cursor.getString(_cursorIndexOfInstallerEmail);
            final String _tmpInstallerPhone;
            _tmpInstallerPhone = _cursor.getString(_cursorIndexOfInstallerPhone);
            final String _tmpCoverImageUri;
            if (_cursor.isNull(_cursorIndexOfCoverImageUri)) {
              _tmpCoverImageUri = null;
            } else {
              _tmpCoverImageUri = _cursor.getString(_cursorIndexOfCoverImageUri);
            }
            _result = new ProjectEntity(_tmpId,_tmpName,_tmpClientName,_tmpAddress,_tmpDescription,_tmpSystemCategory,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallerName,_tmpInstallerCompany,_tmpInstallerEmail,_tmpInstallerPhone,_tmpCoverImageUri);
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
  public Flow<List<ProjectEntity>> searchProjects(final String query) {
    final String _sql = "SELECT * FROM projects WHERE name LIKE '%' || ? || '%' OR clientName LIKE '%' || ? || '%' ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<List<ProjectEntity>>() {
      @Override
      @NonNull
      public List<ProjectEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSystemCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "systemCategory");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfInstallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "installerName");
          final int _cursorIndexOfInstallerCompany = CursorUtil.getColumnIndexOrThrow(_cursor, "installerCompany");
          final int _cursorIndexOfInstallerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "installerEmail");
          final int _cursorIndexOfInstallerPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "installerPhone");
          final int _cursorIndexOfCoverImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImageUri");
          final List<ProjectEntity> _result = new ArrayList<ProjectEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProjectEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSystemCategory;
            _tmpSystemCategory = _cursor.getString(_cursorIndexOfSystemCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpInstallerName;
            _tmpInstallerName = _cursor.getString(_cursorIndexOfInstallerName);
            final String _tmpInstallerCompany;
            _tmpInstallerCompany = _cursor.getString(_cursorIndexOfInstallerCompany);
            final String _tmpInstallerEmail;
            _tmpInstallerEmail = _cursor.getString(_cursorIndexOfInstallerEmail);
            final String _tmpInstallerPhone;
            _tmpInstallerPhone = _cursor.getString(_cursorIndexOfInstallerPhone);
            final String _tmpCoverImageUri;
            if (_cursor.isNull(_cursorIndexOfCoverImageUri)) {
              _tmpCoverImageUri = null;
            } else {
              _tmpCoverImageUri = _cursor.getString(_cursorIndexOfCoverImageUri);
            }
            _item = new ProjectEntity(_tmpId,_tmpName,_tmpClientName,_tmpAddress,_tmpDescription,_tmpSystemCategory,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallerName,_tmpInstallerCompany,_tmpInstallerEmail,_tmpInstallerPhone,_tmpCoverImageUri);
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
  public Flow<Integer> getProjectCount() {
    final String _sql = "SELECT COUNT(*) FROM projects";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"projects"}, new Callable<Integer>() {
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
