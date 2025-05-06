from book_client import BookClient


def main():
    client = BookClient("http://localhost:5001")

    print("\n--- All books ---")
    for book in client.get_books():
        print(book)

    print("\n--- Add book ---")
    new_book = client.add_book("The Sun Also Rises", "Ernest Hemingway", "1926")
    print(new_book)

    print("\n--- Update book ---")
    updated = client.update_book(new_book.id, title="The Sun Also Rises (Revised)")
    print(updated)

    print("\n--- Delete book ---")
    client.delete_book(updated.id)

    print("\n--- Final list ---")
    for book in client.get_books():
        print(book)


if __name__ == "__main__":
    main()
