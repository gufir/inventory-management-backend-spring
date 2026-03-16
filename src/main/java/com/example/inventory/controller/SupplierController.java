package com.example.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.SupplierResponse;
import com.example.inventory.dto.CreateSupplierRequest;
import com.example.inventory.dto.UpdateSupplierRequest;
import com.example.inventory.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;


    // Find All
    @GetMapping
    public List<SupplierResponse> findAllSupplier() {
        return supplierService.findAllSupplier();
    }
    

    //Create Supplier
    @PostMapping
    public SupplierResponse createSupplier(@Valid @RequestBody CreateSupplierRequest request) {
        return supplierService.createSupplier(request);
    }


    //Find By Id
    @GetMapping("/{supplierId}")
    public SupplierResponse findById(@PathVariable UUID supplierId) {
        return supplierService.findbyId(supplierId);
    }

    //Update Supplier
    @PutMapping("/{supplierId}")
    public SupplierResponse updateSupplier(@PathVariable UUID supplierId, @Valid @RequestBody UpdateSupplierRequest request) {
        return supplierService.updateSupplier(supplierId, request);
    }

    //Delete Supplier
    @DeleteMapping("/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable UUID supplierId) {
        supplierService.deleteSupplier(supplierId);;
    }
}
