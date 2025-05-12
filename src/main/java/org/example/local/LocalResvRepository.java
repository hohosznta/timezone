package org.example.local;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LocalResvRepository extends JpaRepository<LocalResv, Long> {

}
