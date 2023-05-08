import requests

# Requirements:
# Requests: A popular Python library for making HTTP requests. To install Requests, run the following command in the terminal or command prompt:
# Copy code
# pip install requests

# define the base URL for the API
base_url = "http://localhost:5001"

# GET /books
response = requests.get(f"{base_url}/books")
print(response.json())

# GET /books/{id}
response = requests.get(f"{base_url}/books/1")
print(response.json())

# POST /books
data = {"title": "The Sun Also Rises", "author": "Ernest Hemingway", "year": "1926"}
response = requests.post(f"{base_url}/books", json=data)
print(response.json())

# PUT /books/{id}
data = {"title": "The Catcher in the Rye", "author": "J.D. Salinger", "year": "1951"}
response = requests.put(f"{base_url}/books/3", json=data)
print(response.json())

# DELETE /books/{id}
response = requests.delete(f"{base_url}/books/3")
print(response.json())

# GET /books (again to show the updated list)
response = requests.get(f"{base_url}/books")
print(response.json())
