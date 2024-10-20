# Visa Appointment Finder Bot

## Project Overview

The **Visa Appointment Finder Bot** is designed to automatically locate the earliest available visa appointment from a specified date range, without requiring active user input. This project is compatible with both Windows and Mac operating systems and is built to run seamlessly across these platforms.

## Features

- Automatically finds the earliest available visa appointment.
- Operates from a user-defined starting date.
- Requires no user interaction once configured.
- Cross-platform compatibility (Windows and Mac).

## Requirements

- Windows or Mac operating system.
- Any necessary dependencies or frameworks (to be listed here).

## USED TECHNOLOGIES
- Java 22
- Selenium 4.23.0
- Bonigercia 5.9.0
- Log4j 2.17.1
- Lombok 2.15.2

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <https://github.com/MehmetEminHan/visa-interview-finder.git>

2. Install required libraries
    ```terminal 
   maven clean install 
3. Specify the user credentials on configuration properties 
   ```properties
   username= example@gmail.com
   password= examplePassword

4. Specify the OS [Windows or Macbook]
   ```properties
   OS="\"Windows\"" 
   
5. Specify the desired date
   ```properties
   desiredMonth= 5
   desiredYear= 2025

6. Specify desired retry count 
NOTE: if you pass 100 application will run 100 times
   ```properties
   retryCount = 100
   
## Logging

This project uses the **Log4j** library for logging purposes. All logs are stored in the `logs` folder within the application directory. Each log file is generated with a timestamp, ensuring proper tracking of log events.

The log file provides valuable information, including:

- All available early appointment dates.
- Pages that do not contain the desired appointment date.

