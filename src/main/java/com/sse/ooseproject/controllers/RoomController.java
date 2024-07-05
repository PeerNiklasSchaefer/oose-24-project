package com.sse.ooseproject.controllers;

import com.sse.ooseproject.models.Room;
import com.sse.ooseproject.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoomController {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    public String rooms(Model model, @RequestParam(value = "sort_by", defaultValue = "number") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        Sort.Direction direction = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        List<Room> rooms = roomRepository.findAll(sort);
        model.addAttribute("rooms", rooms);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "rooms";
    }
}
