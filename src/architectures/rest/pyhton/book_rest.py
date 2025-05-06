from flask import Flask, jsonify, request, abort

app = Flask(__name__)

# REST Constraints:
# - Stateless: Each request contains all necessary information
# - Resource-based: Books are the core resource, identified by URIs
# - Uniform Interface: GET, POST, PUT, DELETE for interacting with resources

# Example data as a dictionary for efficient lookup
books = {
    1: {"title": "The Great Gatsby", "author": "F. Scott Fitzgerald", "year": "1925"},
    2: {"title": "To Kill a Mockingbird", "author": "Harper Lee", "year": "1960"},
    3: {"title": "1984", "author": "George Orwell", "year": "1949"},
    4: {"title": "Brave New World", "author": "Aldous Huxley", "year": "1932"},
    5: {"title": "The Catcher in the Rye", "author": "J.D. Salinger", "year": "1951"},
    6: {"title": "Moby-Dick", "author": "Herman Melville", "year": "1851"},
    7: {"title": "The Brothers Karamazov", "author": "Fyodor Dostoevsky", "year": "1880"},
    8: {"title": "One Hundred Years of Solitude", "author": "Gabriel García Márquez", "year": "1967"},
    9: {"title": "Pride and Prejudice", "author": "Jane Austen", "year": "1813"},
    10: {"title": "Beloved", "author": "Toni Morrison", "year": "1987"},
}


# GET /books
@app.route("/books", methods=["GET"])
def get_books():
    return jsonify({"books": [{**{"id": id}, **data} for id, data in books.items()]})


# GET /books/{id}
@app.route("/books/<int:book_id>", methods=["GET"])
def get_book(book_id):
    book = books.get(book_id)
    if book is None:
        abort(404)
    return jsonify({"book": {**{"id": book_id}, **book}})


# POST /books
@app.route("/books", methods=["POST"])
def create_book():
    if not request.json or "title" not in request.json:
        abort(400)
    new_id = max(books.keys(), default=0) + 1
    book = {
        "title": request.json["title"],
        "author": request.json.get("author", ""),
        "year": request.json.get("year", ""),
    }
    books[new_id] = book
    return jsonify({"book": {**{"id": new_id}, **book}}), 201


# PUT /books/{id}
@app.route("/books/<int:book_id>", methods=["PUT"])
def update_book(book_id):
    if book_id not in books:
        abort(404)
    if not request.json:
        abort(400)
    book = books[book_id]
    book["title"] = request.json.get("title", book["title"])
    book["author"] = request.json.get("author", book["author"])
    book["year"] = request.json.get("year", book["year"])
    return jsonify({"book": {**{"id": book_id}, **book}})


# DELETE /books/{id}
@app.route("/books/<int:book_id>", methods=["DELETE"])
def delete_book(book_id):
    if book_id not in books:
        abort(404)
    del books[book_id]
    return jsonify({"result": True})


if __name__ == "__main__":
    app.run(debug=True, port=5001)
