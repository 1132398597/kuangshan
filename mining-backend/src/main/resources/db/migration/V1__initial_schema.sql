-- 创建数据库
CREATE DATABASE IF NOT EXISTS mining_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE mining_db;

-- 创建矿企表
CREATE TABLE IF NOT EXISTS mine_enterprise (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    enterprise_code VARCHAR(50) NOT NULL UNIQUE,
    enterprise_name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    contact_person VARCHAR(50),
    contact_phone VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_code (enterprise_code),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建矿产表
CREATE TABLE IF NOT EXISTS mineral (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mineral_code VARCHAR(50) NOT NULL UNIQUE,
    mineral_name VARCHAR(100) NOT NULL,
    mineral_type VARCHAR(50),
    enterprise_id BIGINT NOT NULL,
    reserves DECIMAL(18, 2),
    grade DECIMAL(10, 2),
    status VARCHAR(20) NOT NULL DEFAULT 'MINING',
    remarks VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE CASCADE,
    INDEX idx_code (mineral_code),
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建员工表
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    employee_name VARCHAR(50) NOT NULL,
    department VARCHAR(50),
    position VARCHAR(50),
    location VARCHAR(100),
    phone VARCHAR(20),
    join_date DATE,
    enterprise_id BIGINT,
    status VARCHAR(20) NOT NULL DEFAULT 'WORKING',
    remarks VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE SET NULL,
    INDEX idx_code (employee_code),
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_status (status),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建污染数据表
CREATE TABLE IF NOT EXISTS pollution_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    enterprise_id BIGINT NOT NULL,
    pollution_index DECIMAL(10, 2) NOT NULL,
    pm25 DECIMAL(10, 2),
    pm10 DECIMAL(10, 2),
    so2 DECIMAL(10, 2),
    no2 DECIMAL(10, 2),
    level VARCHAR(20) NOT NULL DEFAULT 'LOW',
    record_time DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE CASCADE,
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_level (level),
    INDEX idx_record_time (record_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入示例数据 - 矿企
INSERT INTO mine_enterprise (enterprise_code, enterprise_name, location, contact_person, contact_phone, status) VALUES
('ME001', '中国神华矿业有限公司', '陕西省榆林市', '李明', '029-8765-4321', 'ACTIVE'),
('ME002', '大同煤矿集团', '山西省大同市', '王福成', '0352-5123-456', 'ACTIVE'),
('ME003', '阳泉煤业集团', '山西省阳泉市', '张建华', '0353-2234-567', 'MAINTENANCE'),
('ME004', '兖州煤业股份有限公司', '山东省兖州市', '刘平', '0537-3123-456', 'ACTIVE');

-- 插入示例数据 - 矿产
INSERT INTO mineral (mineral_code, mineral_name, mineral_type, enterprise_id, reserves, grade, status) VALUES
('MN001', '神华煤矿', '煤炭', 1, 5000.00, 72.50, 'MINING'),
('MN002', '大同铁矿', '铁矿', 2, 3500.00, 65.80, 'MINING'),
('MN003', '阳泉铜矿', '铜矿', 3, 1200.00, 0.85, 'EXPLORATION'),
('MN004', '兖州煤质煤', '煤炭', 4, 4200.00, 75.30, 'MINING');

-- 插入示例数据 - 员工
INSERT INTO employee (employee_code, employee_name, department, position, location, phone, join_date, enterprise_id, status) VALUES
('EMP001', '张三', '安全管理', '安全经理', '陕西省榆林市', '13800138000', '2020-01-15', 1, 'WORKING'),
('EMP002', '李四', '开采部', '采矿工程师', '山西省大同市', '13800138001', '2019-06-20', 2, 'WORKING'),
('EMP003', '王五', '运营部', '运营主管', '山西省阳泉市', '13800138002', '2021-03-10', 3, 'WORKING'),
('EMP004', '赵六', '技术部', '技术主管', '山东省兖州市', '13800138003', '2018-09-05', 4, 'LEAVE'),
('EMP005', '孙七', '后勤部', '后勤主管', '陕西省榆林市', '13800138004', '2022-02-14', 1, 'WORKING');

-- 插入示例数据 - 污染数据
INSERT INTO pollution_data (enterprise_id, pollution_index, pm25, pm10, so2, no2, level, record_time) VALUES
(1, 85.00, 68.00, 102.00, 51.00, 59.50, 'HIGH', NOW() - INTERVAL 2 HOUR),
(2, 65.00, 52.00, 78.00, 39.00, 45.50, 'MEDIUM', NOW() - INTERVAL 2 HOUR),
(3, 40.00, 32.00, 48.00, 24.00, 28.00, 'LOW', NOW() - INTERVAL 2 HOUR),
(4, 75.00, 60.00, 90.00, 45.00, 52.50, 'HIGH', NOW() - INTERVAL 2 HOUR),
(1, 82.00, 65.00, 98.00, 49.00, 57.40, 'HIGH', NOW() - INTERVAL 1 HOUR),
(2, 63.00, 50.00, 75.00, 37.00, 43.10, 'MEDIUM', NOW() - INTERVAL 1 HOUR),
(3, 38.00, 30.00, 45.00, 22.00, 26.00, 'LOW', NOW() - INTERVAL 1 HOUR),
(4, 73.00, 58.00, 87.00, 43.00, 50.50, 'HIGH', NOW() - INTERVAL 1 HOUR);
