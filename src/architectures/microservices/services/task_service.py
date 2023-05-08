from flask import Flask, jsonify, request

# Requirements:
# To install Flask, run the following command in the terminal or command prompt:
# pip install Flask
# Requests: A popular Python library for making HTTP requests.
# To install Requests, run the following command in the terminal or command prompt:
# pip install requests

app = Flask(__name__)

tasks = []


@app.route("/tasks", methods=["POST"])
def add_task():
    task = request.json
    tasks.append(task)
    return jsonify(task), 201


@app.route("/tasks", methods=["GET"])
def get_tasks():
    return jsonify(tasks)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5001)
