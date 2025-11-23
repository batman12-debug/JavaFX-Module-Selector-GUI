# JavaFX-Module-Selector-GUI

A JavaFX desktop application for selecting final year modules for Computer Science and Software Engineering students. Built using the Model-View-Controller (MVC) design pattern.

## 📋 Table of Contents

- [Features](#-features)
- [Requirements](#-requirements)
- [Project Structure](#-project-structure)
- [Installation](#-installation)
- [Usage](#-usage)
- [Screenshots](#-screenshots)
- [Technical Details](#-technical-details)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)

## ✨ Features

- **Profile Management**: Create and manage student profiles with course selection
- **Module Selection**: Interactive GUI for selecting modules with credit tracking
- **Reserve Modules**: Select backup modules for your course
- **Data Persistence**: Save and load student profiles in binary format
- **Overview Export**: Export module selections to text file
- **Keyboard Shortcuts**: Quick access to common operations
- **Input Validation**: Ensures correct data entry and credit requirements

## 🔧 Requirements

- **Java JDK 21** or higher
- **Eclipse IDE** (recommended) or any Java IDE
- **JavaFX 21.0.3** (automatically managed via Gradle)

## 📁 Project Structure

```
CTEC2710Assignment/
├── src/
│   └── main/
│       └── java/
│           ├── controller/     # Event handlers and application logic
│           │   └── ModuleSelectorController.java
│           ├── model/          # Data models
│           │   ├── Block.java
│           │   ├── Course.java
│           │   ├── Module.java
│           │   ├── Name.java
│           │   └── StudentProfile.java
│           ├── view/           # UI components
│           │   ├── CreateProfilePane.java
│           │   ├── ModuleSelectorRootPane.java
│           │   ├── OverviewSelectionPane.java
│           │   ├── ReserveModulesPane.java
│           │   └── SelectModulesPane.java
│           └── main/           # Application entry point
│               └── ApplicationLoader.java
├── build.gradle                # Gradle build configuration
├── settings.gradle             # Gradle settings
├── .project                    # Eclipse project file
├── .classpath                  # Eclipse classpath configuration
└── README.md                   # This file
```

## 🚀 Installation

### Prerequisites

1. **Install Java JDK 21**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/#java21) or [Adoptium](https://adoptium.net/)
   - Verify installation: `java -version`

2. **Install Eclipse IDE** (Optional)
   - Download [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/)

### Clone or Download

```bash
# If using Git
git clone <repository-url>
cd CTEC2710Assignment

# Or download and extract the ZIP file
```

## 💻 Usage

### Running with Gradle (Recommended)

```bash
# Navigate to project directory
cd CTEC2710Assignment

# Run the application
./gradlew run

# On Windows
gradlew.bat run
```

### Running in Eclipse

1. **Import Project**
   - Open Eclipse IDE
   - Go to `File → Import...`
   - Select `General → Existing Projects into Workspace`
   - Browse to your folder
   - Click `Finish`

2. **Run Application**
   - Right-click on `ApplicationLoader.java`
   - Select `Run As → Java Application`

### Application Workflow

1. **Create Profile Tab**
   - Select your course (Computer Science or Software Engineering)
   - Enter student details:
     - P Number
     - First Name
     - Family Name
     - Email Address
     - Date
   - Click "Create Profile"

2. **Select Modules Tab**
   - View compulsory modules (automatically selected)
   - Choose optional Block 3/4 modules
   - Monitor current credits (must total 120)
   - Click "Submit" when done

3. **Reserve Modules Tab**
   - Select one reserve module from available options
   - Confirm selection

4. **Overview Selection Tab**
   - Review complete profile and module selections
   - Save overview to text file if needed

### Menu Options

| Menu Item | Shortcut | Description |
|-----------|----------|-------------|
| Save Student Data | `Ctrl+S` | Save profile as binary `.dat` file |
| Load Student Data | `Ctrl+L` | Load previously saved profile |
| Exit | `Ctrl+X` | Close application |
| About | - | Application information |

## 📸 Screenshots

*Add screenshots of your application here*

## 🔍 Technical Details

### Architecture

- **MVC Pattern**: Clear separation of concerns
  - **Model**: Data classes (`StudentProfile`, `Module`, `Course`, etc.)
  - **View**: JavaFX UI components (panes and controls)
  - **Controller**: Event handling and business logic

### Module Requirements

#### Computer Science Course
- **Compulsory Modules**:
  - CTEC3701 - Software Development: Methods & Standards (Block 1, 30 credits)
  - CTEC3702 - Advanced Software Development (Block 2, 30 credits)
  - CTEC3451 - Individual Project (Block 3/4, 30 credits)
- **Optional Modules** (Block 3/4):
  - CTEC3704 - Advanced Web Technologies (30 credits)
  - CTEC3705 - Mobile Application Development (30 credits)
  - IMAT3711 - Data Mining (30 credits)
  - IMAT3722 - Machine Learning (30 credits)

#### Software Engineering Course
- **Compulsory Modules**:
  - CTEC3701 - Software Development: Methods & Standards (Block 1, 30 credits)
  - CTEC3703 - Advanced Software Engineering (Block 2, 30 credits)
  - CTEC3451 - Individual Project (Block 3/4, 30 credits)
- **Optional Modules** (Block 3/4):
  - CTEC3704 - Advanced Web Technologies (30 credits)
  - CTEC3705 - Mobile Application Development (30 credits)
  - CTEC3706 - Enterprise Systems Development (30 credits)

### File Formats

- **Student Data** (`.dat`): Binary serialization format - can be loaded back into the application
- **Overview** (`.dat`): Plain text format - human-readable, cannot be loaded

### Dependencies

- JavaFX 21.0.3 (via Gradle)
- Java 21

## 🐛 Troubleshooting

### JavaFX Not Found Error

```
Error: Module javafx.controls not found
```

**Solution**:
- Ensure JDK 21 is installed and configured
- Run `gradle clean build` to refresh dependencies
- Check Eclipse Java Build Path settings

### Eclipse Import Issues

**Problem**: Gradle import hangs or fails

**Solution**:
- Use "Existing Projects into Workspace" instead of Gradle import
- Ensure Eclipse has Java 21 configured: `Window → Preferences → Java → Installed JREs`
- Refresh project: Right-click project → `Refresh`

### Compilation Errors

**Problem**: Red X marks on project files

**Solution**:
1. Clean project: `Project → Clean → Clean all projects`
2. Refresh: Right-click project → `Refresh`
3. Check Java version: Ensure project uses Java 21
4. Rebuild: `Project → Build Project`

### File Save Issues

**Problem**: Files saving with wrong extension (e.g., `.dat.txt`)

**Solution**:
- The application automatically corrects file extensions
- Ensure you're using the latest version
- Check file permissions in the save directory

## 📝 License

This project is part of a university assignment for CTEC2710 - Object-Oriented Design and Development.

## 👤 Author

**Your Name**
- M. Mohsin Alam
- Academic Year: 2025/26

## 🙏 Acknowledgments

- JavaFX Team for the excellent GUI framework
- Eclipse Foundation for the IDE
- Gradle Team for the build automation tool

---

⭐ If you find this project helpful, please consider giving it a star!
