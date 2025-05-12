package org.example.global;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GlobalResvRepository extends JpaRepository<GlobalResv, Long>  {


}
