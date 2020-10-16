package com.company.unauthorizedDeliveries.Repo;

import com.company.unauthorizedDeliveries.domein.Logins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginsRepo extends JpaRepository<Logins,Long>{
    Logins findAllByAppAccountNameAndActive(String name, boolean b);
    //List<Logins> findAllByAppAccountName();
}
