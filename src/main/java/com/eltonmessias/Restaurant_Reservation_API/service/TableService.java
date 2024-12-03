package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.TableDTO;
import com.eltonmessias.Restaurant_Reservation_API.model.Tables;
import com.eltonmessias.Restaurant_Reservation_API.repository.TableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    private TableDTO convertTableToDTO(Tables table) {
        return new TableDTO(table.getId(), table.getName(), table.getCapacity(), table.getStatus());
    }

    public TableDTO createTable(TableDTO tableDTO) {
        Tables newTable = new Tables();
        newTable.setName(tableDTO.name());
        newTable.setCapacity(tableDTO.capacity());
        newTable.setStatus(tableDTO.status());
        tableRepository.save(newTable);
        return convertTableToDTO(newTable);
    }
}
