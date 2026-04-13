package com.wigell.sushi.service;

import com.wigell.sushi.entity.Room;
import com.wigell.sushi.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room createRoom(Room room) {
        Room saved = roomRepository.save(room);
        logger.info("admin created room {}", saved.getId());
        return saved;
    }

    public Room updateRoom(int id, Room room) {
        room.setId(id);
        Room saved = roomRepository.save(room);
        logger.info("admin updated room {}", id);
        return saved;
    }

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
        logger.info("admin deleted room {}", id);
    }
}