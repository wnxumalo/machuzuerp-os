# MachuzuERP

MachuzuERP is a Java EE–based ERP application running on **WildFly** with a **MySQL** backend. This repository provides setup instructions to get the application running locally.

---

## 🚀 Prerequisites

- **[JDK 14](ca://s?q=Install_JDK_14_on_Windows)**  
- **[MySQL Server 8.0.25](ca://s?q=Install_MySQL_Server_8.0.25)**  
- **[MySQL Workbench](ca://s?q=Install_MySQL_Workbench)**  
- **[WildFly 24.0.1](ca://s?q=Download_WildFly_24.0.1)**
- Netbeans 12.01

---

## 🛠 Installation Steps

1. **Install JDK 14**  
   - Download `jdk-14_windows-x64_bin` and set `JAVA_HOME`.  
   - Add `%JAVA_HOME%\bin` to your system PATH.

2. **Install MySQL Server**  
   - Use `mysql-installer-community-8.0.25.0`.  
   - Configure with:  
     - Database: `erp`  
     - User: `root`  
     - Password: `admin`  
     - Port: `3307`

3. **Install MySQL Workbench**  
   - Connect to the server using the above credentials.  

4. **Restore Database**  
   - In Workbench:  
     - Go to *Server → Data Import*.  
     - Select dump file `machuzuerp24122025`.  
     - Import into the `erp` schema.

5. **Download and Configure WildFly**  
   - Extract `wildfly-24.0.1.Final`.  
   - Start server with `standalone.bat` (Windows) inside the `bin` folder.  
   - Configure datasource to connect to the `erp` database.

---

## 🌐 Accessing the Application

- Open browser:  
  `http://127.0.0.1:8080/MachuzuERP/`  

- Default credentials:  
  - Username: `main@machuzu.com`  
  - Password: `12345`

---

## 📂 Project Structure

- `src
