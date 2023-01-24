package com.example.market9.user.profile.repository;

import com.example.market9.user.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

     Optional<Profile> findByUsername(String userName);
}
