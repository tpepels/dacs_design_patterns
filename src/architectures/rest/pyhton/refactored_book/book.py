class Book:
    def __init__(self, id, title, author, year):
        self.id = id
        self.title = title
        self.author = author
        self.year = year

    @classmethod
    def from_dict(cls, data):
        return cls(id=data["id"], title=data["title"], author=data["author"], year=data["year"])

    def to_dict(self):
        return {"title": self.title, "author": self.author, "year": self.year}

    def __str__(self):
        return f"{self.id}: {self.title} by {self.author} ({self.year})"
