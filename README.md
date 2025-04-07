# Advanced Software Engineering Coursework
F21AS (Group 3)

_*This repository contains the codebase for the Advanced Software Engineering Coursework project._

<h1 align="center">Coffee Shop Simulation</h1>


The Coffee Shop Simulation project is designed to simulate the opening, operation, and growth of a coffee shop through the development of a software system. The project focuses on creating a functional and efficient system that models key processes such as order management, item inventory, customer interactions, and business operations. The team responsible for this project is made up of five members, each contributing to different aspects of the development process, ranging from system design and implementation to testing and version control.#


<h2 align="center">Features</h2>

* Order Management: Create, update, and remove orders.
* Item Management: Add and remove items from an order.
* Discount Functionality: Apply discounts to orders.
* Exception Handling: Custom exceptions to handle invalid orders, items, and operations.
* Order List Management: Maintain a list of orders and manage them efficiently.
* Real-time order.
* Client-Server Design Implementaion.
* Thread safe programming.
* Networked multi-station support.
* Real-time Loggers to keep  track of whats happening.
* Daily Specials Feature with its discount as top priority,
* Auditory feedback system (custom sounds for actions).


  

<h2 align="center">Technologies Used</h2>

* Java (Core functionality)
* JUnit (Unit testing framework)
* Maven or Gradle (Build and dependency management)
* Git (Version control)

<h2 align="center">Prerequisites</h2>

Ensure you have the following tools installed before you begin:

* Java 8 or later: [Download here](https://www.oracle.com/java/technologies/downloads/#java11?er=221886)
* Maven or Gradle:
  * [Maven Installation Guide](https://maven.apache.org/install.html)
  * [Gradle Installation Guide](https://gradle.org/install/)
* Git: [Download here](https://git-scm.com/)

<h2 align="center">Clone the Repository</h2>

Clone the project to your local machine:

git clone [https://github.com/CamyH/adv-swe-coursework.git](https://github.com/CamyH/adv-swe-coursework.git)

<h2 align="center">Build the Project</h2>

After cloning the repository, navigate into the project directory and run the following commands to build the project.

#### Using Maven
cd adv-swe-coursework
mvn clean install

#### Using Gradle
cd adv-swe-coursework
gradle build

_This will download necessary dependencies and compile the project._

<h2 align="center">Running the Project</h2>

Once the project is built, you can run the main application:

java -jar CoffeeShop/out/artifacts/CoffeeShop_jar/CoffeeShop.jar

<h2 align="center">How to Run the System?</h2>

<h3>Single-Machine Setup (Testing/Local Use)</h2>

1. #### Run the Server:
    Launch CoffeeShop.jar first to start the server.

2. #### Run the Client:
    Launch ClientApp.jar to connect the client interface to the local server.

<h3>Multi-Machine Setup (Live Environment)</h2>

1. #### On the Server Machine:
    Launch CoffeeShop.jar - this will act as the central order-processing system.

2. #### On Client Machines:
     Launch ClientApp.jar on each device; they will automatically detect and connect to the server over the network.

<h3> Notes </h3>

* Ensure all machines are on the same network for automatic server detection.
* If connection issues arise, verify firewall settings and IP configurations.

<h2 align="center">Repository Structure</h2>

The repository is split mainly into coursework and src files. The src files contain both the main and testing code.

* [Coursework](https://github.com/CamyH/adv-swe-coursework)
  * [CoffeeShop/](CoffeeShop)  # Codebase
    * [out/artifacts/CoffeeShop_jar/](CoffeeShop/out/artifacts/CoffeeShop_jar)   # jar file to run the server
      * [files/](CoffeeShop/out/artifacts/CoffeeShop_jar/files)  # txt files
     * [src/](CoffeeShop/src)    # source files
       * [main/](CoffeeShop/src/main)
         * [java/](CoffeeShop/src/main/java)
           * [client/](CoffeeShop/src/main/java/client) # GUI files
           * [customer/](CoffeeShop/src/main/java/customer)
           * [exceptions/](CoffeeShop/src/main/java/exceptions)
           * [files/](CoffeeShop/src/main/java/files)  # txt files
           * [interfaces/](CoffeeShop/src/main/java/interfaces) 
           * [item/](CoffeeShop/src/main/java/item)
           * [logs/](CoffeeShop/src/main/java/logs)
           * [message/](CoffeeShop/src/main/java/message)
           * [order/](CoffeeShop/src/main/java/order)
           * [server/](CoffeeShop/src/main/java/server)
           * [services/](CoffeeShop/src/main/java/services)
           * [utils/](CoffeeShop/src/main/java/utils)
           * [workers/](CoffeeShop/src/main/java/workers)
         * [resources/files/](CoffeeShop/src/main/resources/files)
       * [test/java/](CoffeeShop/src/test/java)   # All test classes
         * [client/](CoffeeShop/src/test/java/client)  # GUI and Console Test classes
         * [item](CoffeeShop/src/test/java/item)  # Item test classes
         * [order](CoffeeShop/src/test/java/order)  # Order test classes
         * [utils](CoffeeShop/src/test/java/utils)
         * [workers/](CoffeeShop/src/test/java/workers)
  * [README.md](README.md)
  * [menu.txt](menu.txt)



<h2 align="center">Data Structures Used </h2>

**Queues:** A Queue in Java is a FIFO (First-In-First-Out) data structure, meaning elements are added at the rear and removed from the front. The queue will be used within the OrderList class and holds all the current orders placed in the system. Since queues operate on the First In, First Out (FIFO) principle, the earliest received order is processed first, ensuring timely preparation and delivery to the customer. The queue will be implemented using an ArrayDeque due to its O(1) time complexity for every operation. 

**ArrayList:** ArrayList is a resizable array implementation of the List interface in Java. Unlike arrays, which have a fixed size, an ArrayList can grow and shrink dynamically. In our system the ArrayList is implemented within the order class to hold a list of item IDs for the current order. To add elements dynamically when the exact size is unknown, a flexible data structure is required. ArrayList in Java provides this flexibility by allowing dynamic resizing, making it a suitable alternative to fixed-size arrays for managing dynamic collections. 

**HashMap:** HashMap is used to store key-value pairs. It is based on hashing and provides constant-time performance (O(1)) for basic operations like put(), get(), and remove(), assuming a good hash function. This is implemented within the ItemList class to establish a connection between the ItemID and the corresponding item. This ensures that when an ItemID is referenced, the associated item object is also retrieved. Therefore, incorporating this structure within the item list facilitates efficient data management and retrieval. 

**Enum:** Enums were chosen for storing the fixed discounts and item categories. Enums are most suitable for these attributes as they only need to be set once at compile time and will never need to be changed. Enums were also chosen to store the different sound effects and play those at different part of the application to improve user experience and give appropriate feedback.

<h2 align="center">Exception Handling</h2>

This project defines custom exceptions to handle various error cases: <br/>
1. **DuplicateOrderException:** Thrown when a duplicate Order is added to the OrderList.
2. **InvalidItemIDException:** Thrown when an invalid item ID is used.
3. **InvalidOrderException:** Thrown when an order is invalid.
4. **StaffNullNameException:** Thrown when a Staff is created with no name.
</p>

<h2 align="center">Tests</h2>

This project includes junit tests for all the classes to ensure that the core functionalities work correctly.

_To run the tests, use Maven or Gradle commands listed above._

<h2 align="center">Design Patterns Used</h2>

1. Singleton
2. Dependency Injection
3. Retry Pattern
4. Factory
5. Model-View-Controller (MVC)
6. Observer
7. Producer/Consumer
8. Client/Server
9. Object Pool



<h2 align="center">Contributors</h2>

*	__Akash Poonia__ 
*	__Mohd Faiz__ 
*	__Fraser Holman__ 
*	__Cameron Hunt__ 
*	__Caelan Mackenzie__ 





