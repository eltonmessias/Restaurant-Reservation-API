package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.TableDTO;
import com.eltonmessias.Restaurant_Reservation_API.exception.TableAlreadyExistsException;
import com.eltonmessias.Restaurant_Reservation_API.exception.TableNotFoundException;
import com.eltonmessias.Restaurant_Reservation_API.model.Tables;
import com.eltonmessias.Restaurant_Reservation_API.repository.TableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    private TableDTO convertTableToDTO(Tables table) {
        return new TableDTO(table.getId(), table.getName(), table.getCapacity(), table.getStatus());
    }

    public TableDTO createTable(TableDTO tableDTO) {
        Tables newTable = new Tables();
        if(tableRepository.existsByName(tableDTO.name()))
            throw new TableAlreadyExistsException("Table already exists");
        newTable.setName(tableDTO.name());
        newTable.setCapacity(tableDTO.capacity());
        newTable.setStatus(tableDTO.status());
        tableRepository.save(newTable);
        return convertTableToDTO(newTable);
    }

    public List<TableDTO> getAllTables() {
        List<Tables> tables = tableRepository.findAll();
        List<TableDTO> tableDTOs = new ArrayList<>();
        tables.forEach(table -> tableDTOs.add(convertTableToDTO(table)));
        return tableDTOs;
    }

    public TableDTO getTableById(long id) {
        Tables table = tableRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Table not found"));
        return convertTableToDTO(table);
    }

    public TableDTO updateTable(long id, TableDTO tableDTO) {
        try {
            Tables table = tableRepository.findById(id).orElseThrow(()->new TableNotFoundException("Table not found"));
            table.setName(tableDTO.name());
            table.setCapacity(tableDTO.capacity());
            table.setStatus(tableDTO.status());
            tableRepository.save(table);
            return convertTableToDTO(table);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
