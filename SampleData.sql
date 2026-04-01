-- Smart Parking System Database Setup
-- Run this file in MySQL to initialize the database

DROP DATABASE IF EXISTS smart_parking_db;
CREATE DATABASE smart_parking_db;
USE smart_parking_db;

-- Users Table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(100),
    role ENUM('user','admin') DEFAULT 'user'
);

-- Parking Area Table
CREATE TABLE Parking_Area (
    area_id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    total_slots INT NOT NULL
);

-- Parking Slot Table
CREATE TABLE Parking_Slot (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    area_id INT NOT NULL,
    slot_number VARCHAR(10) NOT NULL,
    status ENUM('Free','Occupied') DEFAULT 'Free',
    FOREIGN KEY (area_id) REFERENCES Parking_Area(area_id) ON DELETE CASCADE
);

-- Booking Table
CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    slot_id INT NOT NULL,
    booking_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    exit_time DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (slot_id) REFERENCES Parking_Slot(slot_id) ON DELETE CASCADE
);

-- Payment Table
CREATE TABLE Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) DEFAULT 0.00,
    payment_status ENUM('Pending','Paid','Failed') DEFAULT 'Pending',
    payment_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE
);

-- Insert Default Admin User (plaintext password: admin123)
INSERT INTO Users (name, email, phone, password, role)
VALUES ('Admin', 'admin@parking.com', '9999999999', 'admin123', 'admin');

-- Insert Sample Parking Areas
INSERT INTO Parking_Area (location, total_slots) 
VALUES 
    ('Central Park', 10),
    ('Mall Entrance', 15),
    ('Airport Terminal', 20);

-- Insert Parking Slots for Central Park (area_id = 1)
INSERT INTO Parking_Slot (area_id, slot_number, status)
VALUES 
    (1, 'A1', 'Free'),
    (1, 'A2', 'Free'),
    (1, 'A3', 'Free'),
    (1, 'A4', 'Free'),
    (1, 'A5', 'Free'),
    (1, 'A6', 'Free'),
    (1, 'A7', 'Free'),
    (1, 'A8', 'Free'),
    (1, 'A9', 'Free'),
    (1, 'A10', 'Free');

-- Insert Parking Slots for Mall Entrance (area_id = 2)
INSERT INTO Parking_Slot (area_id, slot_number, status)
VALUES 
    (2, 'B1', 'Free'),
    (2, 'B2', 'Free'),
    (2, 'B3', 'Free'),
    (2, 'B4', 'Free'),
    (2, 'B5', 'Free'),
    (2, 'B6', 'Free'),
    (2, 'B7', 'Free'),
    (2, 'B8', 'Free'),
    (2, 'B9', 'Free'),
    (2, 'B10', 'Free'),
    (2, 'B11', 'Free'),
    (2, 'B12', 'Free'),
    (2, 'B13', 'Free'),
    (2, 'B14', 'Free'),
    (2, 'B15', 'Free');

-- Insert Parking Slots for Airport Terminal (area_id = 3)
INSERT INTO Parking_Slot (area_id, slot_number, status)
VALUES 
    (3, 'C1', 'Free'),
    (3, 'C2', 'Free'),
    (3, 'C3', 'Free'),
    (3, 'C4', 'Free'),
    (3, 'C5', 'Free'),
    (3, 'C6', 'Free'),
    (3, 'C7', 'Free'),
    (3, 'C8', 'Free'),
    (3, 'C9', 'Free'),
    (3, 'C10', 'Free'),
    (3, 'C11', 'Free'),
    (3, 'C12', 'Free'),
    (3, 'C13', 'Free'),
    (3, 'C14', 'Free'),
    (3, 'C15', 'Free'),
    (3, 'C16', 'Free'),
    (3, 'C17', 'Free'),
    (3, 'C18', 'Free'),
    (3, 'C19', 'Free'),
    (3, 'C20', 'Free');
