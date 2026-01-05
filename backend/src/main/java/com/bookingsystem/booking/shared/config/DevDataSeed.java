package com.bookingsystem.booking.shared.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.bookingsystem.booking.room.data.RoomRepository;
import com.bookingsystem.booking.room.domain.entities.Room;
import com.bookingsystem.booking.room.domain.enums.RoomType;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;
import com.bookingsystem.booking.user.domain.enums.Role;

@Configuration
@Profile("dev") // <-- only active in dev
class DevDataSeed {

  @Bean
  @Transactional
  CommandLineRunner seedUsersAndRooms(UserRepository users, RoomRepository rooms, PasswordEncoder enc) {
    return args -> {
      users.findByEmailIgnoreCase("alice@example.com").orElseGet(() -> {
        User u = new User();
        u.setEmail("alice@example.com");
        u.setUsername("alice");
        u.setRole(Role.USER);
        u.setPassword(enc.encode("P@ssw0rd!123"));
        return users.save(u);
      });

      users.findByEmailIgnoreCase("admin@example.com").orElseGet(() -> {
        User u = new User();
        u.setEmail("admin@example.com");
        u.setUsername("Prompt");
        u.setRole(Role.ADMIN);
        u.setPassword(enc.encode("P@ssw0rd!456"));
        return users.save(u);
      });

      if (!rooms.existsByNameIgnoreCase("Meeting Room A")) {
        Room r = new Room();
        r.setName("Meeting Room A");
        r.setType(RoomType.MEETING);
        r.setCapacity(10);
        r.setDescription("Cozy meeting room with whiteboard");
        rooms.save(r);
      }

      if (!rooms.existsByNameIgnoreCase("Conference Hall")) {
        Room r = new Room();
        r.setName("Conference Hall");
        r.setType(RoomType.MUSIC);
        r.setCapacity(5);
        r.setDescription("Guitar + Amp");
        rooms.save(r);
      }
    };
  }
}
