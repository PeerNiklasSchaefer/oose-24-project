package com.sse.ooseproject;

import com.sse.ooseproject.controllers.RoomController;
import com.sse.ooseproject.controllers.StudentController;
import com.sse.ooseproject.models.*;
import com.sse.ooseproject.repositories.*;
import com.sse.ooseproject.validators.StudentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RoomTests {
	@InjectMocks
	RoomController roomController;

	@Mock
	RoomRepository roomRepository;

	@Mock
	Model model;

	Room validRoom;
	Room validRoom2;

	@BeforeEach
	public void init() {
		roomRepository = Mockito.mock(RoomRepository.class);

		roomController = new RoomController(roomRepository);

		validRoom = new Room("1", 100, true, new ArrayList<>(), new Building());
		validRoom2 = new Room("2", 200, false, new ArrayList<>(), new Building());
	}

	@Test
	public void GetRoomNumberAscTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "number");
		Mockito.when(roomRepository.findAll(sort))
				.thenReturn(List.of(validRoom, validRoom2));

		var viewName  = roomController.rooms(model, "number", true);

		assertEquals("rooms", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("rooms"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validRoom);
	}

	@Test
	public void GetRoomSeatsDescTest() {
		Sort sort = Sort.by(Sort.Direction.DESC, "seats");
		Mockito.when(roomRepository.findAll(sort))
				.thenReturn(List.of(validRoom2, validRoom));

		var viewName  = roomController.rooms(model, "seats", false);

		assertEquals("rooms", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("rooms"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validRoom2);
	}
}
