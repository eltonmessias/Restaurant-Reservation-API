package com.eltonmessias.Restaurant_Reservation_API.controller;

import com.eltonmessias.Restaurant_Reservation_API.dto.TableDTO;
import com.eltonmessias.Restaurant_Reservation_API.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @PostMapping("")
    public ResponseEntity<TableDTO> createTable(@RequestBody TableDTO tableDTO) {
        return new ResponseEntity<>(tableService.createTable(tableDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TableDTO>> getTables() {
        return new ResponseEntity<>(tableService.getAllTables(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<TableDTO> getTableById(@RequestParam int id) {
        return new ResponseEntity<>(tableService.getTableById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableDTO> updateTable(@PathVariable long id, @RequestBody TableDTO tableDTO) {
        return new ResponseEntity<>(tableService.updateTable(id, tableDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TableDTO> updateStatus(@PathVariable long id, @RequestBody TableDTO tableDTO) {
        return new ResponseEntity<>(tableService.changeStatus(id, tableDTO), HttpStatus.OK);
    }
}
