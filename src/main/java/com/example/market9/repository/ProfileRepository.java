package com.example.market9.repository;

import com.example.market9.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

     Optional<Profile>  findByUsername(String userName); //넵 !
}
