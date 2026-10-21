
package com.example.main.controller;

import com.example.main.payload.GovernmentServiceDto;
import com.example.main.service.Interface.GovernmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schema")
public class GovernmentServiceController {

    private final GovernmentService governmentService;

    public GovernmentServiceController(GovernmentService governmentService) {
        this.governmentService = governmentService;
    }

    // Public: all published services
    @GetMapping("/government-services")
    public ResponseEntity<List<GovernmentServiceDto>> getAllPublished() {
        return ResponseEntity.ok(governmentService.getPublishedServices());
    }

    // Public: filter by category, e.g. CERTIFICATE
    @GetMapping("/government-services/category/{category}")
    public ResponseEntity<List<GovernmentServiceDto>> getByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(
                governmentService.getPublishedByCategory(category));
    }

    // Public: details of one published service
    @GetMapping("/government-services/{id}")
    public ResponseEntity<GovernmentServiceDto> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                governmentService.getPublishedById(id));
    }

    // Admin: create a service
    @PostMapping("/admin/government-services")
    public ResponseEntity<GovernmentServiceDto> create(
            @RequestBody GovernmentServiceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(governmentService.create(dto));
    }

    // Admin: update a service
    @PutMapping("/admin/government-services/{id}")
    public ResponseEntity<GovernmentServiceDto> update(
            @PathVariable Long id,
            @RequestBody GovernmentServiceDto dto) {
        return ResponseEntity.ok(
                governmentService.update(id, dto));
    }

    // Admin: delete a service
    @DeleteMapping("/admin/government-services/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        governmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
