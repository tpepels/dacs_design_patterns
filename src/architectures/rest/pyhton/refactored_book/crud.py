import requests
import time


class CrudResource:
    endpoint = ""  # override in subclass

    def __init__(self, base_url):
        self.base_url = base_url.rstrip("/")

    def _url(self, path=""):
        return f"{self.base_url}/{self.endpoint}/{path}".rstrip("/")

    def _request(self, method, path="", **kwargs):
        url = self._url(path)
        print(f"\n{method} {url}")
        start = time.time()
        response = requests.request(method, url, **kwargs)
        duration = time.time() - start
        print(f"Status {response.status_code} ({duration:.2f}s)")
        if response.ok:
            return response.json()
        print("Error:", response.text)
        return None

    def get_all(self):
        return self._request("GET")

    def get(self, id):
        return self._request("GET", str(id))

    def create(self, data):
        return self._request("POST", json=data)

    def update(self, id, data):
        return self._request("PUT", str(id), json=data)

    def delete(self, id):
        return self._request("DELETE", str(id))
