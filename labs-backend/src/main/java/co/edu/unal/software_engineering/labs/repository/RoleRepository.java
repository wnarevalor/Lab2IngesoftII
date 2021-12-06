package co.edu.unal.software_engineering.labs.repository;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.pojo.RolesOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
  List<RolesOnly> findBy();
}
