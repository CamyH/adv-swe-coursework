# Advanced Software Engineering Coursework
F21AS (Group 3)

_*This repository contains the codebase for the Advanced Software Engineering Coursework project._

The Coffee Shop Simulation project is designed to simulate the opening, operation, and growth of a coffee shop through the development of a software system. The project focuses on creating a functional and efficient system that models key processes such as order management, item inventory, customer interactions, and business operations. The team responsible for this project is made up of five members, each contributing to different aspects of the development process, ranging from system design and implementation to testing and version control.

### Features
* Order Management: Create, update, and remove orders.
* Item Management: Add and remove items from an order.
* Discount Functionality: Apply discounts to orders.
* Exception Handling: Custom exceptions to handle invalid orders, items, and operations.
* Order List Management: Maintain a list of orders and manage them efficiently.

### Technologies Used
* Java (Core functionality)
* JUnit (Unit testing framework)
* Maven or Gradle (Build and dependency management)
* Git (Version control)

### Prerequisites
Ensure you have the following tools installed before you begin:

* Java 8 or later: [Download here](https://www.oracle.com/java/technologies/downloads/#java11?er=221886)
* Maven or Gradle:
  * [Maven Installation Guide](https://maven.apache.org/install.html)
  * [Gradle Installation Guide](https://gradle.org/install/)
* Git: [Download here](https://git-scm.com/)


### Clone the Repository
Clone the project to your local machine:
git clone [https://github.com/CamyH/adv-swe-coursework.git](https://github.com/CamyH/adv-swe-coursework.git)

### Build the Project
After cloning the repository, navigate into the project directory and run the following commands to build the project.

#### Using Maven
cd adv-swe-coursework
mvn clean install

#### Using Gradle
cd adv-swe-coursework
gradle build

_This will download necessary dependencies and compile the project._

## Repository Structure

The repository is split mainly into doc and src files. The src files contain both the arduino .ino code and espressif .c and .h files for the working waveform. The doc files contain documents in relation to the assignment.

* [Coursework](https://github.com/CamyH/adv-swe-coursework)
  * [CoffeeShop](CoffeeShop/src/main/java)Codebase
  * [README.md](https://github.com/CamyH/adv-swe-coursework/blob/main/README.md)
  * [menu.txt](../docs/normalWaveform.PNG)
  * [demonstration.mp4](../docs/demonstration.mp4)
  * [README.md](../docs/README.md)  # read me
* [src/](../src)    # source files
  * [arduino/](../src/arduino)
    * [main/](../src/arduino/main)
      * [main.ino/](../src/arduino/main/main.ino)   # arduino code
  * [espressifIDF/](../src/espressifIDF)
    * [main/](../src/espressifIDF/main) # ESP-IDF code
      * [main.c/](../src/espressifIDF/main/main.c)
      * [main.h/](../src/espressifIDF/main/main.h)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Data Structures Used 

**Queues:** A Queue in Java is a FIFO (First-In-First-Out) data structure, meaning elements are added at the rear and removed from the front. The queue will be used within the OrderList class and holds all the current orders placed in the system. Since queues operate on the First In, First Out (FIFO) principle, the earliest received order is processed first, ensuring timely preparation and delivery to the customer. The queue will be implemented using an ArrayDeque due to its O(1) time complexity for every operation. 

**ArrayList:** ArrayList is a resizable array implementation of the List interface in Java. Unlike arrays, which have a fixed size, an ArrayList can grow and shrink dynamically. In our system the ArrayList is implemented within the order class to hold a list of item IDs for the current order. To add elements dynamically when the exact size is unknown, a flexible data structure is required. ArrayList in Java provides this flexibility by allowing dynamic resizing, making it a suitable alternative to fixed-size arrays for managing dynamic collections. 

**HashMap:** HashMap is used to store key-value pairs. It is based on hashing and provides constant-time performance (O(1)) for basic operations like put(), get(), and remove(), assuming a good hash function. This is implemented within the ItemList class to establish a connection between the ItemID and the corresponding item. This ensures that when an ItemID is referenced, the associated item object is also retrieved. Therefore, incorporating this structure within the item list facilitates efficient data management and retrieval. 

**Enum:** Enums were chosen for storing the fixed discounts and item categories. Enums are most suitable for these attributes as they only need to be set once at compile time and will never need to be changed.

### Exception Handling
This project defines custom exceptions to handle various error cases:

**InvalidItemIDException:** Thrown when an invalid item ID is used.
**InvalidOrderException:** Thrown when an order is invalid.

### Tests
This project includes junit tests for all the classes to ensure that the core functionalities work correctly.
To run the tests, use Maven or Gradle commands listed above.

### Contributors
*	__Akash Poonia:__ Responsible for developing the Item class and handling its associated exceptions.
*	__Mohd Faiz:__ Oversees the creation of the Order class and its exception handling.
*	__Fraser Holman:__ Develops the ItemList and OrderList classes, including their associated interfaces.
*	__Cameron Hunt:__ Manages the FileManager component and implements discount logic.
*	__Caelan Mackenzie:__ Leads the development of the demo, console interface, and initial UI design.





