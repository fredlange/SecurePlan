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
import se.secureplan.app.core.data.local.entity.SymbolEntity;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SymbolDao_Impl implements SymbolDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SymbolEntity> __insertionAdapterOfSymbolEntity;

  private final EntityDeletionOrUpdateAdapter<SymbolEntity> __deletionAdapterOfSymbolEntity;

  private final EntityDeletionOrUpdateAdapter<SymbolEntity> __updateAdapterOfSymbolEntity;

  public SymbolDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSymbolEntity = new EntityInsertionAdapter<SymbolEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `symbols` (`id`,`name`,`category`,`svgData`,`iconResName`,`color`,`isCustom`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCategory());
        if (entity.getSvgData() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSvgData());
        }
        if (entity.getIconResName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getIconResName());
        }
        statement.bindLong(6, entity.getColor());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfSymbolEntity = new EntityDeletionOrUpdateAdapter<SymbolEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `symbols` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfSymbolEntity = new EntityDeletionOrUpdateAdapter<SymbolEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `symbols` SET `id` = ?,`name` = ?,`category` = ?,`svgData` = ?,`iconResName` = ?,`color` = ?,`isCustom` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymbolEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCategory());
        if (entity.getSvgData() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSvgData());
        }
        if (entity.getIconResName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getIconResName());
        }
        statement.bindLong(6, entity.getColor());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindString(8, entity.getId());
      }
    };
  }

  @Override
  public Object insertSymbol(final SymbolEntity symbol,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSymbolEntity.insert(symbol);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSymbols(final List<SymbolEntity> symbols,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSymbolEntity.insert(symbols);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSymbol(final SymbolEntity symbol,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSymbolEntity.handle(symbol);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSymbol(final SymbolEntity symbol,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSymbolEntity.handle(symbol);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SymbolEntity>> getAllSymbols() {
    final String _sql = "SELECT * FROM symbols ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbols"}, new Callable<List<SymbolEntity>>() {
      @Override
      @NonNull
      public List<SymbolEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSvgData = CursorUtil.getColumnIndexOrThrow(_cursor, "svgData");
          final int _cursorIndexOfIconResName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResName");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<SymbolEntity> _result = new ArrayList<SymbolEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSvgData;
            if (_cursor.isNull(_cursorIndexOfSvgData)) {
              _tmpSvgData = null;
            } else {
              _tmpSvgData = _cursor.getString(_cursorIndexOfSvgData);
            }
            final String _tmpIconResName;
            if (_cursor.isNull(_cursorIndexOfIconResName)) {
              _tmpIconResName = null;
            } else {
              _tmpIconResName = _cursor.getString(_cursorIndexOfIconResName);
            }
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new SymbolEntity(_tmpId,_tmpName,_tmpCategory,_tmpSvgData,_tmpIconResName,_tmpColor,_tmpIsCustom);
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
  public Flow<List<SymbolEntity>> getSymbolsByCategory(final String category) {
    final String _sql = "SELECT * FROM symbols WHERE category = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbols"}, new Callable<List<SymbolEntity>>() {
      @Override
      @NonNull
      public List<SymbolEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSvgData = CursorUtil.getColumnIndexOrThrow(_cursor, "svgData");
          final int _cursorIndexOfIconResName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResName");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<SymbolEntity> _result = new ArrayList<SymbolEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSvgData;
            if (_cursor.isNull(_cursorIndexOfSvgData)) {
              _tmpSvgData = null;
            } else {
              _tmpSvgData = _cursor.getString(_cursorIndexOfSvgData);
            }
            final String _tmpIconResName;
            if (_cursor.isNull(_cursorIndexOfIconResName)) {
              _tmpIconResName = null;
            } else {
              _tmpIconResName = _cursor.getString(_cursorIndexOfIconResName);
            }
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new SymbolEntity(_tmpId,_tmpName,_tmpCategory,_tmpSvgData,_tmpIconResName,_tmpColor,_tmpIsCustom);
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
  public Flow<List<SymbolEntity>> getCustomSymbols() {
    final String _sql = "SELECT * FROM symbols WHERE isCustom = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symbols"}, new Callable<List<SymbolEntity>>() {
      @Override
      @NonNull
      public List<SymbolEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSvgData = CursorUtil.getColumnIndexOrThrow(_cursor, "svgData");
          final int _cursorIndexOfIconResName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResName");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<SymbolEntity> _result = new ArrayList<SymbolEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymbolEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSvgData;
            if (_cursor.isNull(_cursorIndexOfSvgData)) {
              _tmpSvgData = null;
            } else {
              _tmpSvgData = _cursor.getString(_cursorIndexOfSvgData);
            }
            final String _tmpIconResName;
            if (_cursor.isNull(_cursorIndexOfIconResName)) {
              _tmpIconResName = null;
            } else {
              _tmpIconResName = _cursor.getString(_cursorIndexOfIconResName);
            }
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new SymbolEntity(_tmpId,_tmpName,_tmpCategory,_tmpSvgData,_tmpIconResName,_tmpColor,_tmpIsCustom);
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
  public Object getSymbolById(final String id,
      final Continuation<? super SymbolEntity> $completion) {
    final String _sql = "SELECT * FROM symbols WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SymbolEntity>() {
      @Override
      @Nullable
      public SymbolEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSvgData = CursorUtil.getColumnIndexOrThrow(_cursor, "svgData");
          final int _cursorIndexOfIconResName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconResName");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final SymbolEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpSvgData;
            if (_cursor.isNull(_cursorIndexOfSvgData)) {
              _tmpSvgData = null;
            } else {
              _tmpSvgData = _cursor.getString(_cursorIndexOfSvgData);
            }
            final String _tmpIconResName;
            if (_cursor.isNull(_cursorIndexOfIconResName)) {
              _tmpIconResName = null;
            } else {
              _tmpIconResName = _cursor.getString(_cursorIndexOfIconResName);
            }
            final long _tmpColor;
            _tmpColor = _cursor.getLong(_cursorIndexOfColor);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _result = new SymbolEntity(_tmpId,_tmpName,_tmpCategory,_tmpSvgData,_tmpIconResName,_tmpColor,_tmpIsCustom);
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
  public Object getSymbolCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM symbols";
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
