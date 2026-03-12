-- 矿山检测系统 - MySQL 数据库初始化脚本
-- 使用说明：在 MySQL 命令行或工具中直接运行此脚本

-- 创建数据库
DROP DATABASE IF EXISTS mining_db;
CREATE DATABASE mining_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mining_db;

-- 创建矿企表
CREATE TABLE mine_enterprise (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '企业ID',
    enterprise_code VARCHAR(50) NOT NULL UNIQUE COMMENT '企业编码',
    enterprise_name VARCHAR(100) NOT NULL COMMENT '企业名称',
    location VARCHAR(100) COMMENT '所在地区',
    contact_person VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status ENUM('ACTIVE', 'INACTIVE', 'MAINTENANCE') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/INACTIVE/MAINTENANCE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_code (enterprise_code),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='矿企表';

-- 创建矿产表
CREATE TABLE mineral (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '矿产ID',
    mineral_code VARCHAR(50) NOT NULL UNIQUE COMMENT '矿产编码',
    mineral_name VARCHAR(100) NOT NULL COMMENT '矿产名称',
    mineral_type VARCHAR(50) COMMENT '矿产类型',
    enterprise_id BIGINT NOT NULL COMMENT '所属企业ID',
    reserves DECIMAL(18, 2) COMMENT '储量(万吨)',
    grade DECIMAL(10, 2) COMMENT '品位(%)',
    status ENUM('MINING', 'EXPLORATION', 'CLOSED') NOT NULL DEFAULT 'MINING' COMMENT '状态: MINING/EXPLORATION/CLOSED',
    remarks VARCHAR(500) COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE CASCADE,
    INDEX idx_code (mineral_code),
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='矿产表';

-- 创建员工表
CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '员工ID',
    employee_code VARCHAR(50) NOT NULL UNIQUE COMMENT '员工编码',
    employee_name VARCHAR(50) NOT NULL COMMENT '员工名称',
    department VARCHAR(50) COMMENT '所属部门',
    position VARCHAR(50) COMMENT '职位',
    location VARCHAR(100) COMMENT '工作地点',
    phone VARCHAR(20) COMMENT '联系电话',
    join_date DATE COMMENT '入职日期',
    enterprise_id BIGINT COMMENT '所属企业ID',
    status ENUM('WORKING', 'LEAVE', 'RESIGNED') NOT NULL DEFAULT 'WORKING' COMMENT '状态: WORKING/LEAVE/RESIGNED',
    remarks VARCHAR(500) COMMENT '备注',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE SET NULL,
    INDEX idx_code (employee_code),
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_status (status),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- 创建污染数据表
CREATE TABLE pollution_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '污染数据ID',
    enterprise_id BIGINT NOT NULL COMMENT '所属企业ID',
    pollution_index DECIMAL(10, 2) NOT NULL COMMENT '污染指数',
    pm25 DECIMAL(10, 2) COMMENT 'PM2.5浓度',
    pm10 DECIMAL(10, 2) COMMENT 'PM10浓度',
    so2 DECIMAL(10, 2) COMMENT 'SO2浓度',
    no2 DECIMAL(10, 2) COMMENT 'NO2浓度',
    level ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL DEFAULT 'LOW' COMMENT '污染等级: LOW/MEDIUM/HIGH',
    record_time DATETIME NOT NULL COMMENT '记录时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (enterprise_id) REFERENCES mine_enterprise(id) ON DELETE CASCADE,
    INDEX idx_enterprise_id (enterprise_id),
    INDEX idx_level (level),
    INDEX idx_record_time (record_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='污染数据表';

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
(1, 85.00, 68.00, 102.00, 51.00, 59.50, 'HIGH', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(2, 65.00, 52.00, 78.00, 39.00, 45.50, 'MEDIUM', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(3, 40.00, 32.00, 48.00, 24.00, 28.00, 'LOW', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(4, 75.00, 60.00, 90.00, 45.00, 52.50, 'HIGH', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(1, 82.00, 65.00, 98.00, 49.00, 57.40, 'HIGH', DATE_SUB(NOW(), INTERVAL 1 HOUR)),
(2, 63.00, 50.00, 75.00, 37.00, 43.10, 'MEDIUM', DATE_SUB(NOW(), INTERVAL 1 HOUR)),
(3, 38.00, 30.00, 45.00, 22.00, 26.00, 'LOW', DATE_SUB(NOW(), INTERVAL 1 HOUR)),
(4, 73.00, 58.00, 87.00, 43.00, 50.50, 'HIGH', DATE_SUB(NOW(), INTERVAL 1 HOUR));

-- Database initialization completed
SELECT 'Database initialized successfully!' AS message;
