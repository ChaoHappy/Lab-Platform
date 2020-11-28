package com.chp.labsystem.modules.system.repository;

import com.chp.labsystem.modules.system.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
