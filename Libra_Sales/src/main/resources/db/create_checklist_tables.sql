CREATE TABLE IF NOT EXISTS opportunity_checklist (
    id INT NOT NULL AUTO_INCREMENT,
    module_id INT NOT NULL,
    stage_id INT NOT NULL,
    checked_data JSON NULL,
    updated_by INT NULL,
    updated_at DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_opportunity_checklist_module_stage (module_id, stage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS support_ticket_checklist (
    id INT NOT NULL AUTO_INCREMENT,
    module_id INT NOT NULL,
    stage_id INT NOT NULL,
    checked_data JSON NULL,
    updated_by INT NULL,
    updated_at DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_support_ticket_checklist_module_stage (module_id, stage_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
