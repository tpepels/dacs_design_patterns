from crud import CrudResource
from book import Book


class BookClient(CrudResource):
    endpoint = "books"

    def get_books(self):
        data = self.get_all()
        return [Book.from_dict(book) for book in data.get("books", [])]

    def get_book(self, id):
        data = self.get(id)
        return Book.from_dict(data["book"]) if data else None

    def add_book(self, title, author, year):
        data = self.create({"title": title, "author": author, "year": year})
        return Book.from_dict(data["book"]) if data else None

    def update_book(self, book_id, **kwargs):
        data = self.update(book_id, kwargs)
        return Book.from_dict(data["book"]) if data else None

    def delete_book(self, book_id):
        return self.delete(book_id)
