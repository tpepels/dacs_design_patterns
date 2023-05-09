# API Gateway

This API Gateway is a simple example of a gateway that proxies requests to different microservices. It is implemented using Python and the Flask web framework.

## Requirements

- Python 3.6 or later
- Flask
- Requests

## Installation

1. Download and install the latest version of Python (3.6 or later) from the official website: https://www.python.org/downloads/

2. Clone this repository or download the source files.

3. Navigate to the project directory and install the required dependencies using the provided `requirements.txt` file:

`pip install -r requirements.txt`

## Running the API Gateway

1. From the project directory, run the following command to start the API gateway server:

`python api_gateway.py`

This will start the server on port 3000.

2. You can now interact with the API gateway using the specified routes:

- `/catalog/`
- `/inventory/`
- `/orders/`

These routes will proxy requests to the appropriate microservices.

## Note

Please ensure that the microservices for the book catalog, inventory, and orders are running on their respective ports (3001, 3002, and 3003) before interacting with the API gateway.

## Java Version

### Requirements

- Java 8 or later
- Maven

### Installation

1. Download and install the latest version of Java (8 or later) from the official website: https://adoptium.net/releases.html

2. Download and install Maven from the official website: https://maven.apache.org/download.cgi

3. Clone this repository or download the source files.

4. Navigate to the Java project directory and build the project using Maven:

`mvn clean install`

### Running the API Gateway

1. From the Java project directory, run the following command to start the API gateway server:

`mvn spring-boot:run`

This will start the server on port 8080.

## Usage

You can now interact with the API gateway using the specified routes:

- `/catalog/`
- `/inventory/`
- `/orders/`

These routes will proxy requests to the appropriate microservices.

## Note

Please ensure that the microservices for the book catalog, inventory, and orders are running on their respective ports (3001, 3002, and 3003) before interacting with the API gateway.
