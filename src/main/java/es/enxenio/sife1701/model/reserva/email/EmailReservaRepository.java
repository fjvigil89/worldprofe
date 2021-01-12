package es.enxenio.sife1701.model.reserva.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jlosa on 04/10/2017.
 */
public interface EmailReservaRepository extends JpaRepository<EmailReserva, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM EmailReserva WHERE reserva.id = :id")
    void deleteByReserva(@Param("id") Long id);

}
