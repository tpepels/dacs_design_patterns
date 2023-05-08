from flask import Flask, request, Response
import requests

# Requirements:
# To install Flask, run the following command in the terminal or command prompt:
# pip install Flask
# Requests: A popular Python library for making HTTP requests.
# To install Requests, run the following command in the terminal or command prompt:
# pip install requests


#  To run the program, you should navigate to the directory containing the Python script (let's say the script is named api_gateway.py) and execute the following command:
# python api_gateway.py
# This will start the API gateway server on port 3000, and users can interact with it using the specified routes.

app = Flask(__name__)


# Base URLs for the microservices
BOOK_CATALOG_SERVICE = "http://localhost:3001"
BOOK_INVENTORY_SERVICE = "http://localhost:3002"
BOOK_ORDER_SERVICE = "http://localhost:3003"


def proxy_request(service_url):
    resp = requests.request(
        method=request.method,
        url=f"{service_url}{request.path}",
        headers={key: value for (key, value) in request.headers if key != "Host"},
        data=request.get_data(),
        cookies=request.cookies,
        allow_redirects=False,
    )

    excluded_headers = ["content-encoding", "content-length", "transfer-encoding", "connection"]
    headers = [
        (name, value) for (name, value) in resp.raw.headers.items() if name.lower() not in excluded_headers
    ]

    response = Response(resp.content, resp.status_code, headers)
    return response


@app.route("/catalog/<path:path>", methods=["GET", "POST", "PUT", "DELETE"])
def catalog_service(path):
    return proxy_request(BOOK_CATALOG_SERVICE)


@app.route("/inventory/<path:path>", methods=["GET", "POST", "PUT", "DELETE"])
def inventory_service(path):
    return proxy_request(BOOK_INVENTORY_SERVICE)


@app.route("/orders/<path:path>", methods=["GET", "POST", "PUT", "DELETE"])
def orders_service(path):
    return proxy_request(BOOK_ORDER_SERVICE)


if __name__ == "__main__":
    app.run(port=3000)
