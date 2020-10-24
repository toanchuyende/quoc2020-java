package vn.agileviet.quoc2020.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.agileviet.quoc2020.models.ERole;
import vn.agileviet.quoc2020.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	Optional<Role> findByName(ERole name);
}