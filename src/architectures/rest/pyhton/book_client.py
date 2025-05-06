import requests
import time


class BookClient:
    def __init__(self, base_url="http://localhost:5001"):
        self.base_url = base_url

    def _request(self, method, endpoint, **kwargs):
        url = f"{self.base_url}{endpoint}"
        print(f"\n=== {method} {endpoint} ===")
        start = time.time()
        response = requests.request(method, url, **kwargs)
        duration = time.time() - start

        print(f"Status: {response.status_code} ({duration:.2f}s)")
        if response.ok:
            print("Response:", response.json())
            return response.json()
        else:
            print("Error:", response.text)
            return None

    def get_all_books(self):
        return self._request("GET", "/books")

    def get_book(self, book_id):
        return self._request("GET", f"/books/{book_id}")

    def create_book(self, title, author, year):
        data = {"title": title, "author": author, "year": year}
        return self._request("POST", "/books", json=data)

    def update_book(self, book_id, title=None, author=None, year=None):
        data = {"title": title, "author": author, "year": year}
        return self._request("PUT", f"/books/{book_id}", json=data)

    def delete_book(self, book_id):
        return self._request("DELETE", f"/books/{book_id}")


def main():
    client = BookClient()

    client.get_all_books()
    client.get_book(1)

    client.create_book("The Sun Also Rises", "Ernest Hemingway", "1926")
    client.update_book(3, "The Catcher in the Rye", "J.D. Salinger", "1951")
    client.delete_book(3)

    client.get_all_books()


if __name__ == "__main__":
    main()
