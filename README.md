
# **MetricHubApp**

MetricHubApp is an Android application designed for efficient tracking of utility measurements, built with **Jetpack Compose** and powered by **Room Database** for local data persistence. The app is fully localized in English and Spanish, ensuring usability for a wider audience.

---

## **Features**

- **Measurement Registration**:
  - Add measurements for utilities such as water, electricity, and gas.
  - Auto-fills the current date during registration.
  - Ensures input validation (only positive integers are accepted).
- **Dynamic List Display**:
  - View all registered measurements dynamically with pagination for large datasets.
  - Delete entries directly from the list.
- **Local Persistence**:
  - All data is saved locally using Room and SQLite.
- **Modern Design**:
  - Built with Jetpack Compose for a clean, responsive, and intuitive user interface.
- **Seamless Navigation**:
  - Smooth transitions between screens using Jetpack Navigation Component.
- **Localization**:
  - Text resources are fully localized in English and Spanish.

---

## **Screens**

### **1. Measurement List Screen**
- Displays all registered measurements dynamically.
- Includes:
  - Pagination for large datasets.
  - Floating Action Button (FAB) to navigate to the registration form.
  - Delete functionality for removing entries.

### **2. Measurement Registration Screen**
- Add a new measurement with the following details:
  - **Type**: Select from Water, Electricity, or Gas.
  - **Value**: Only positive integers are accepted.
  - **Date**: Auto-filled with the current date.
- Provides real-time validation:
  - Ensures the value is not empty.
  - Only whole numbers are allowed.

---

## **Technologies Used**

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room with SQLite
- **State Management**: ViewModel and Flow
- **Navigation**: Jetpack Navigation Component
- **Localization**: English and Spanish support

---

## **Installation**

1. Clone the repository:
   ```bash
   git clone https://github.com/Britoshky/MetricHubApp.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on an Android emulator or physical device.

---

## **Usage**

1. Launch the app.
2. Navigate to the **Registration Screen** using the FAB.
3. Add a new measurement:
   - Choose a type (Water, Electricity, Gas).
   - Enter a positive integer for the measurement value.
   - Review the auto-filled date.
4. Save the measurement and return to the **List Screen** to view it.
5. Use the delete button to remove any measurement.

---

## **Localization**

The app supports the following languages:
- **English** (default).
- **Spanish** (based on device locale).

---

## **Code Highlights**

### **1. Validation**
- Ensures only positive integers are accepted for the measurement value.
- Displays specific error messages for invalid input, decimals, or empty fields.

### **2. Dynamic List**
- Built using `LazyColumn` for efficient rendering of large datasets.
- Includes pagination to improve performance.

### **3. Room Database**
- Stores measurement data locally using SQLite.
- Real-time updates with Flow integration.

### **4. Jetpack Compose**
- Fully implemented UI with modern Compose components for layouts, forms, and navigation.

---

## **Contributing**

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature description"
   git push origin feature-name
   ```
4. Open a pull request.

---

## **License**

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

---

## **Contact**

For questions, feedback, or suggestions, feel free to reach out:

- **Author**: Héctor Brito Tapia
- **GitHub**: [Britoshky](https://github.com/Britoshky)
- **Email**: *britoshky@gmail.com*
