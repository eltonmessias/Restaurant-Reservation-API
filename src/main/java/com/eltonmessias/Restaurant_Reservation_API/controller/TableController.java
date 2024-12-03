package com.eltonmessias.Restaurant_Reservation_API.controller;

import com.eltonmessias.Restaurant_Reservation_API.dto.TableDTO;
import com.eltonmessias.Restaurant_Reservation_API.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @PostMapping("")
    public ResponseEntity<TableDTO> createTable(@RequestBody TableDTO tableDTO) {
        return new ResponseEntity<>(tableService.createTable(tableDTO), HttpStatus.CREATED);
    }
}
