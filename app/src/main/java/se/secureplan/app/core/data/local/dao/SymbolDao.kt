package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.SymbolEntity

@Dao
interface SymbolDao {
    @Query("SELECT * FROM symbols ORDER BY name ASC")
    fun getAllSymbols(): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbols WHERE category = :category ORDER BY name ASC")
    fun getSymbolsByCategory(category: String): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbols WHERE isCustom = 1 ORDER BY name ASC")
    fun getCustomSymbols(): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbols WHERE id = :id")
    suspend fun getSymbolById(id: String): SymbolEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbol(symbol: SymbolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSymbols(symbols: List<SymbolEntity>)

    @Update
    suspend fun updateSymbol(symbol: SymbolEntity)

    @Delete
    suspend fun deleteSymbol(symbol: SymbolEntity)

    @Query("SELECT COUNT(*) FROM symbols")
    suspend fun getSymbolCount(): Int
}
