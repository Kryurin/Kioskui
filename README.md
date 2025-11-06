# Kioskui

Kioskui is a simple, modern user interface project designed for kiosk systems.
This guide explains how to clone the project, create and push a branch, make commits, submit a pull request (PR), and properly add the included MySQL Connector JAR file using IntelliJ IDEA or Eclipse.

---

## Table of Contents

* [How to Clone the Repository](#how-to-clone-the-repository)
* [How to Create a Branch](#how-to-create-a-branch)
* [How to Commit and Push Changes](#how-to-commit-and-push-changes)
* [How to Create a Pull Request and Merge](#how-to-create-a-pull-request-and-merge)
* [How to Add the Included MySQL Connector (lib folder)](#how-to-add-the-included-mysql-connector-lib-folder)
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
4. Confirm you are on the correct branch:

   ```bash
   git branch
   ```

   The current branch will be marked with an asterisk (*).

---

## How to Commit and Push Changes

Once you’ve made edits or added new files, follow these steps to save and upload your changes:

1. Add all modified or new files to staging:

   ```bash
   git add .
   ```
2. Commit your changes with a short message describing what you did:

   ```bash
   git commit -m "Describe your changes here"
   ```
3. Push the branch (and your commits) to GitHub:

   ```bash
   git push -u origin feature-name
   ```
4. If you make more commits later, you can push them with:

   ```bash
   git push
   ```

Your branch and commits are now saved to GitHub.

---

## How to Create a Pull Request and Merge

After pushing your branch, follow these steps to request a merge into the main branch:

1. Go to your GitHub repository page:
   [https://github.com/Kryurin/Kioskui](https://github.com/Kryurin/Kioskui)

2. You’ll see a message suggesting your recently pushed branch. Click **Compare & pull request**.

3. Add a title and description for your pull request, explaining what changes you made.

4. Click **Create pull request**.

5. Once reviewed, click **Merge pull request**, then **Confirm merge**.

6. (Optional) Delete the branch after merging to keep the repository clean:

   ```bash
   git branch -d feature-name
   git push origin --delete feature-name
   ```

Your branch is now merged into the main branch.

---

## How to Add the Included MySQL Connector (lib folder)

The MySQL Connector JAR (`mysql-connector-j.jar`) is already included in the `lib` folder of this project.
You only need to link it to your project settings in IntelliJ or Eclipse.

### In IntelliJ IDEA

1. Open the project in IntelliJ.
2. Go to **File → Project Structure → Modules → Dependencies**.
3. Click the **+** icon → **JARs or directories...**.
4. Navigate to the project’s `lib` folder and select `mysql-connector-j.jar`.
5. Click **Apply**, then **OK**.
6. IntelliJ will now include the JAR in your project classpath.

### In Eclipse

1. Open the project in Eclipse.
2. Right-click the project in **Project Explorer**.
3. Select **Build Path → Configure Build Path**.
4. Go to the **Libraries** tab.
5. Click **Add JARs...** (not “Add External JARs”).
6. Navigate to your project’s `lib` folder and select `mysql-connector-j.jar`.
7. Click **Apply and Close**.
8. Eclipse will now recognize the connector automatically.

---

## Troubleshooting

* **Error: 'git' is not recognized**
  Git is not installed or not added to PATH. Reinstall Git and check "Add Git to PATH" during installation.

* **Line ending warnings (LF/CRLF)**
  These are safe to ignore. They indicate differences in line-ending formats between Windows and Unix systems.

* **Error: failed to push some refs**
  This can occur if the remote branch has new commits. Fix it by running:

  ```bash
  git pull origin feature-name --rebase
  git push origin feature-name
  ```

* **MySQL Driver Not Found**
  Ensure that IntelliJ or Eclipse has the `lib/mysql-connector-j.jar` file properly linked under your project’s dependencies.
