package com.priyansu.project.lovable_clone.controller;

import com.priyansu.project.lovable_clone.dto.project.FileContentResponse;
import com.priyansu.project.lovable_clone.dto.project.FileNode;
import com.priyansu.project.lovable_clone.dto.project.FileTreeResponse;
import com.priyansu.project.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/files")
@RequiredArgsConstructor
public class FileController {
    //DI
    private final ProjectFileService projectFileService;

    @GetMapping
    public ResponseEntity<FileTreeResponse> getFileTree(@PathVariable Long projectId){
        Long userId = 1L;

        return ResponseEntity.ok(projectFileService.getFileTree(projectId));
    }

    @GetMapping("/content")
    public ResponseEntity<FileContentResponse> getFile(
            @PathVariable Long projectId,
            @RequestParam String path){
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }
}
