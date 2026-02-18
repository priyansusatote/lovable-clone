package com.priyansu.project.lovable_clone.service;

import com.priyansu.project.lovable_clone.dto.deploy.DeployResponse;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);
}
