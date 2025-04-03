*** Settings ***
Library    ../resources/script.py

*** Test Cases ***
Run CSV Update Script
    Update CSVs From Notepad    C:/robot_project/resources/TestData    C:/robot_project/resources/TestData    5
