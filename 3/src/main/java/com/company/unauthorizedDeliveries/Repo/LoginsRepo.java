package com.company.unauthorizedDeliveries.Repo;

import com.company.unauthorizedDeliveries.domain.Logins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginsRepo extends JpaRepository<Logins,Long>{
    Logins findAllByAppAccountNameAndActive(String name, boolean b);
    //List<Logins> findAllByAppAccountName();
}
