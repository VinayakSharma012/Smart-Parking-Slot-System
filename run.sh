#!/bin/bash

# ============================================================================
# SMART PARKING MANAGEMENT SYSTEM - LAUNCHER
# ============================================================================
# This is the ONLY file you need to run to start the entire application
# Just execute: ./run.sh
# ============================================================================

set -e

PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JAR_FILE="$PROJECT_DIR/SmartParking.jar"
JDBC_JAR="$PROJECT_DIR/mysql-connector-java-8.0.33.jar"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "╔════════════════════════════════════════════════════════════════════════╗"
echo "║                                                                        ║"
echo "║          🚀 SMART PARKING MANAGEMENT SYSTEM - LAUNCHER 🚀            ║"
echo "║                                                                        ║"
echo "╚════════════════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo -e "${RED}❌ Error: SmartParking.jar not found!${NC}"
    echo "Location: $JAR_FILE"
    exit 1
fi

# Check if JDBC driver exists
if [ ! -f "$JDBC_JAR" ]; then
    echo -e "${RED}❌ Error: MySQL JDBC driver not found!${NC}"
    echo "Location: $JDBC_JAR"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Error: Java is not installed!${NC}"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Get Java version
JAVA_VERSION=$(java -version 2>&1 | head -1)
echo -e "${GREEN}✅ Java found: $JAVA_VERSION${NC}"

# Check MySQL connection
echo ""
echo -e "${YELLOW}📊 Checking database connection...${NC}"
if mysql -h localhost -u root -psparkyop -e "SELECT 1" &> /dev/null; then
    echo -e "${GREEN}✅ MySQL connection successful${NC}"
else
    echo -e "${RED}❌ Warning: Could not connect to MySQL${NC}"
    echo "Make sure MySQL is running with credentials: root/sparkyop"
    echo "Database: smart_parking_db"
fi

# Check if database exists
echo -e "${YELLOW}📊 Checking database...${NC}"
if mysql -h localhost -u root -psparkyop -e "USE smart_parking_db; SELECT 1;" &> /dev/null; then
    SLOT_COUNT=$(mysql -h localhost -u root -psparkyop -D smart_parking_db -se "SELECT COUNT(*) FROM Parking_Slot;" 2>/dev/null || echo "0")
    echo -e "${GREEN}✅ Database 'smart_parking_db' found with $SLOT_COUNT parking slots${NC}"
else
    echo -e "${YELLOW}⚠️  Database not found. Setting up database...${NC}"
    if [ -f "$PROJECT_DIR/SampleData.sql" ]; then
        mysql -h localhost -u root -psparkyop < "$PROJECT_DIR/SampleData.sql"
        echo -e "${GREEN}✅ Database setup complete${NC}"
    else
        echo -e "${RED}❌ Error: SampleData.sql not found!${NC}"
        exit 1
    fi
fi

# Launch the application
echo ""
echo -e "${BLUE}🚀 Starting Smart Parking System...${NC}"
echo ""

java -cp "$JDBC_JAR:$JAR_FILE" main.MainApp

