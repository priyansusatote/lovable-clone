package com.priyansu.project.lovable_clone.mapper;

import com.priyansu.project.lovable_clone.dto.project.FileNode;
import com.priyansu.project.lovable_clone.entity.ProjectFile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-25T18:37:07+0530",
    comments = "version: 1.6.0, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ProjectFileMapperImpl implements ProjectFileMapper {

    @Override
    public List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList) {
        if ( projectFileList == null ) {
            return null;
        }

        List<FileNode> list = new ArrayList<FileNode>( projectFileList.size() );
        for ( ProjectFile projectFile : projectFileList ) {
            list.add( projectFileToFileNode( projectFile ) );
        }

        return list;
    }

    protected FileNode projectFileToFileNode(ProjectFile projectFile) {
        if ( projectFile == null ) {
            return null;
        }

        String path = null;

        path = projectFile.getPath();

        FileNode fileNode = new FileNode( path );

        return fileNode;
    }
}
