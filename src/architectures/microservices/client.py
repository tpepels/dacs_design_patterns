import requests


# A simple microservices application using Python and Flask to create two services and one client.
# The first service is responsible for managing a list of users, while the second service manages a list of tasks.
#
# The client in this file communicates with both services using RESTful APIs.

# To run this example, you will need Python and Flask installed.
# Requirements:
# To install Flask, run the following command in the terminal or command prompt:
# pip install Flask
# Requests: A popular Python library for making HTTP requests.
# To install Requests, run the following command in the terminal or command prompt:
# pip install requests

# First, start both services by running python user_service.py and python task_service.py in separate terminals.
# This is a simulation of running the services on different hosts in a network or on the internet.
#
# Then, run the client by executing python client.py.
# The client will interact with both services, adding a user and a task, and then retrieving the lists of users and tasks.


# Add a new user
user = {"name": "John Doe", "email": "john.doe@example.com"}
response = requests.post("http://localhost:5002/users", json=user)
print(f"User added: {response.json()}")

# Get all users
response = requests.get("http://localhost:5002/users")
print(f"Users: {response.json()}")

# Add a new task
task = {"description": "Buy groceries", "completed": False}
response = requests.post("http://localhost:5001/tasks", json=task)
print(f"Task added: {response.json()}")

# Get all tasks
response = requests.get("http://localhost:5001/tasks")
print(f"Tasks: {response.json()}")
