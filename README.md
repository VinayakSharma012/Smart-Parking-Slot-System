# Smart Parking Management System

A complete Java Swing-based desktop application for managing parking spaces with user registration, login, slot booking, and payment processing.

## 🚀 Quick Start

```bash
cd /Users/vinayaksharma/Desktop/"Smart parking system"
java -cp "mysql-connector-java-8.0.33.jar:SmartParking.jar" main.MainApp
```

## 📋 Prerequisites

- Java 17+
- MySQL Server running on localhost:3306
- MySQL credentials: root / sparkyop
- Database setup: Run `SampleData.sql`

## 🔐 Default Credentials

**Admin Account:**
- Email: admin@parking.com
- Password: admin123

## ✨ Features

### User Features
- ✅ User Registration with SHA-256 password hashing
- ✅ Email/Password Authentication
- ✅ View 45 Parking Slots (3 areas)
- ✅ Book Parking Slot (1-click)
- ✅ View Booking History
- ✅ Process Payment (₹20/hour auto-calculated)

### Admin Features
- ✅ Manage Parking Areas (Add/Delete)
- ✅ Manage Parking Slots (Toggle Status)
- ✅ View All User Bookings
- ✅ Manage User Accounts

### Navigation
- ✅ Back buttons on every page
- ✅ Reset buttons on all forms
- ✅ Smooth page transitions
- ✅ Session management

## 📊 Database

- **Database:** smart_parking_db
- **Tables:** Users, Parking_Area, Parking_Slot, Booking, Payment
- **Pre-loaded Data:**
  - 3 Parking Areas (Central Park, Mall, Airport)
  - 45 Parking Slots
  - 1 Admin User

## 📁 Project Structure

```
SmartParking/
├── SmartParking.jar              (Executable Application)
├── mysql-connector-java-8.0.33.jar (JDBC Driver)
├── SampleData.sql                (Database Setup)
├── src/                          (Source Code)
│   ├── main/                     (Application Entry)
│   ├── ui/                       (GUI Frames & Panels)
│   ├── dao/                      (Database Access Objects)
│   ├── db/                       (Database Connection)
│   ├── model/                    (Data Models)
│   └── utils/                    (Utilities)
└── out/                          (Compiled Classes)
```

## 🔧 Build

```bash
javac -cp ".:mysql-connector-java-8.0.33.jar" -d out $(find src -name "*.java")
jar cvfm SmartParking.jar MANIFEST.MF -C out .
```

## 📖 Pages & Navigation

1. **RegisterFrame** → Register/Login/Reset/Back
2. **LoginFrame** → Login/Register/Reset/Exit
3. **UserDashboard** (4 Tabs) → Available Slots, Book, History, Payment
4. **AdminDashboard** (4 Tabs) → Areas, Slots, Bookings, Users

---

**Production Ready** ✅ | **Fully Connected** ✅ | **Tested** ✅
