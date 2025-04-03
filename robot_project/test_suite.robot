*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${URL}     https://www.google.com
${BROWSER}    chrome

*** Test Cases ***
Open Google And Search
    Open Browser    ${URL}    ${BROWSER}
    Input Text      name=q    Robot Framework
    Press Keys      name=q    ENTER
    Sleep           2s
    Capture Page Screenshot
    Close Browser
