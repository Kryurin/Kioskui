# Kioskui

Kioskui is a simple, modern user interface project designed for kiosk systems.
This guide explains how to clone the project and create a new branch using the Windows Command Prompt (CMD).

---

## Table of Contents

* [How to Clone the Repository](#how-to-clone-the-repository)
* [How to Create a Branch](#how-to-create-a-branch)
* [Troubleshooting](#troubleshooting)

---

## How to Clone the Repository

Follow these steps to download the project to your computer:

1. Open **Command Prompt (CMD)**.
2. Navigate to the folder where you want to save the project:

   ```bash
   cd path\to\your\folder
   ```
3. Clone the repository using:

   ```bash
   git clone https://github.com/Kryurin/Kioskui.git
   ```
4. Move into the newly created folder:

   ```bash
   cd Kioskui
   ```

You now have a local copy of the repository.

---

## How to Create a Branch

A branch allows you to work on new features or changes without affecting the main project.

1. Check which branch you are currently on:

   ```bash
   git branch
   ```
2. Create a new branch (replace `feature-name` with your desired branch name):

   ```bash
   git branch feature-name
   ```
3. Switch to the new branch:

   ```bash
   git checkout feature-name
   ```

   You are now working on the new branch.
4. Make your changes, then add and commit them:

   ```bash
   git add .
   git commit -m "Describe your changes here"
   ```
5. Push your new branch to GitHub:

   ```bash
   git push -u origin feature-name
   ```

---

## Troubleshooting

* **Error: 'git' is not recognized**
  Git is not installed or not added to PATH. Reinstall Git and check "Add Git to PATH" during installation.

* **Line ending warnings (LF/CRLF)**
  These are safe to ignore. They indicate differences in line-ending formats between Windows and Unix systems.
