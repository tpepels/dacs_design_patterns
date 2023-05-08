from flask import Flask, jsonify, request

app = Flask(__name__)

users = []


@app.route("/users", methods=["POST"])
def add_user():
    user = request.json
    users.append(user)
    return jsonify(user), 201


@app.route("/users", methods=["GET"])
def get_users():
    return jsonify(users)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5002)
