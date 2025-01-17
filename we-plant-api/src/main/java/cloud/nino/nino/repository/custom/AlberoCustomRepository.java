package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Albero;
import cloud.nino.nino.domain.User;
import cloud.nino.nino.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import java.math.BigInteger;

/**
 * Spring Data  repository for the Albero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlberoCustomRepository extends JpaRepository<Albero, Long> {
    Page<Albero> findAllByEssenzaIsNull(Pageable pageable);

    Optional<Albero> findFirstByMain_IdOrderByDataUltimoAggiornamentoDesc(Long id);

    @Query("SELECT DISTINCT main.id FROM Albero WHERE idPianta is null")
    List<Long> findDistinctByMain();

    long countByEssenzaIsNull();

    Optional<Albero> findFirstByIdPiantaOrderByDataUltimoAggiornamentoDesc(Integer idPianta);

    Optional<Albero> findFirstByIdPianta(Integer idPianta);

    @Query("SELECT DISTINCT main.id FROM Albero WHERE idPianta is not null and codiceArea =  ?1")
    List<Long> findDistinctByMainAndCodiceArea(Integer codiceArea);

    List<Albero> findAllByCodiceAreaAndIdPiantaNotNull(Integer codiceArea);

    List<Albero> findAllByModificatoDaNotNullAndDataUltimoAggiornamentoIsGreaterThanEqualAndDataUltimoAggiornamentoIsLessThanEqual(ZonedDateTime from, ZonedDateTime to);

    Optional<Albero> findFirstByMainIdOrderByDataUltimoAggiornamentoDesc(Long mainId);

    List<Albero> findByMain_IdOrderByDataUltimoAggiornamento(Long mainId);

    @Query("SELECT DISTINCT main.id FROM Albero where data_ultimo_aggiornamento > '2019-01-01'")
    List<Long> findDistinctByMainModified();

    @Query("SELECT a FROM Albero a where a.main.id = id")
    List<Albero> findAllByMainIdEqualsToId(Pageable pageable);
    
    @Query(value = "SELECT count(a.id) FROM Albero a where a.main_id = a.id", nativeQuery = true)
    long getTotalNumberTrees();

    @Query(value = "SELECT id, entityid, id_pianta, codice_area, nome_comune, classe_altezza, altezza, diametro_fusto, diametro, wkt, aggiornamento, nota, tipo_di_suolo, data_impianto, data_abbattimento, ( SELECT max(b.data_ultimo_aggiornamento) AS data_ultimo_aggiornamento FROM Albero b WHERE b.main_id = a.main_id ), data_prima_rilevazione, note_tecniche, posizione, deleted, essenza_id, modificato_da_id, main_id FROM Albero a WHERE a.main_id = a.id ORDER BY a.data_ultimo_aggiornamento desc", nativeQuery = true)
    List<Albero> findAllAlberosSortedByLastUpdate(Pageable pageable);
    
    @Query(value = "SELECT modificato_da_id FROM Albero WHERE id_pianta = ?1", nativeQuery = true)
    List<BigInteger> findAllUsersByIdPianta(Long idPianta);
}
