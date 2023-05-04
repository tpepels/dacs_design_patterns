from flask import Flask, jsonify, request

app = Flask(__name__)

# define some example data
books = [
    {"id": 1, "title": "The Great Gatsby", "author": "F. Scott Fitzgerald", "year": "1925"},
    {"id": 2, "title": "To Kill a Mockingbird", "author": "Harper Lee", "year": "1960"},
]


# GET /books
@app.route("/books", methods=["GET"])
def get_books():
    return jsonify({"books": books})


# GET /books/{id}
@app.route("/books/<int:book_id>", methods=["GET"])
def get_book(book_id):
    book = [book for book in books if book["id"] == book_id]
    if len(book) == 0:
        abort(404)
    return jsonify({"book": book[0]})


# POST /books
@app.route("/books", methods=["POST"])
def create_book():
    if not request.json or not "title" in request.json:
        abort(400)
    book = {
        "id": books[-1]["id"] + 1,
        "title": request.json["title"],
        "author": request.json.get("author", ""),
        "year": request.json.get("year", ""),
    }
    books.append(book)
    return jsonify({"book": book}), 201


# PUT /books/{id}
@app.route("/books/<int:book_id>", methods=["PUT"])
def update_book(book_id):
    book = [book for book in books if book["id"] == book_id]
    if len(book) == 0:
        abort(404)
    if not request.json:
        abort(400)
    book[0]["title"] = request.json.get("title", book[0]["title"])
    book[0]["author"] = request.json.get("author", book[0]["author"])
    book[0]["year"] = request.json.get("year", book[0]["year"])
    return jsonify({"book": book[0]})


# DELETE /books/{id}
@app.route("/books/<int:book_id>", methods=["DELETE"])
def delete_book(book_id):
    book = [book for book in books if book["id"] == book_id]
    if len(book) == 0:
        abort(404)
    books.remove(book[0])
    return jsonify({"result": True})


if __name__ == "__main__":
    app.run(debug=True, port=5001)
