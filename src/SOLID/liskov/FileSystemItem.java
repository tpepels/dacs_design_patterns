package SOLID.liskov;

/*
 * In this example, the base class FileSystemItem has a method getSize(), which
 * throws an UnsupportedOperationException by default. When we use a File or
 * Directory object as a FileSystemItem, the Liskov Substitution Principle is
 * violated, as calling the getSize() method on a FileSystemItem object is not
 * guaranteed to work properly.
 */
class BadFileSystemItem {
    public String name;

    public BadFileSystemItem(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

class BadFile extends BadFileSystemItem {
    private int size;

    public BadFile(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }
}

class BadDirectory extends BadFileSystemItem {
    public BadDirectory(String name) {
        super(name);
    }

    @Override
    public int getSize() {
        // Return the total size of all items in the directory
        return 0;
    }
}

/*
 * In this solution, we make FileSystemItem an abstract class and define
 * getSize() as an abstract method. This way, we ensure that every subclass of
 * FileSystemItem provides a proper implementation of the getSize() method,
 * adhering to the Liskov Substitution Principle. Now, when we use a File or
 * Directory object as a FileSystemItem, we can be confident that calling the
 * getSize() method will work as expected.
 */
abstract class FileSystemItem {
    private String name;

    public FileSystemItem(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getSize();
}

class File extends FileSystemItem {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }
}

class Directory extends FileSystemItem {
    public Directory(String name) {
        super(name);
    }

    @Override
    public int getSize() {
        // Return the total size of all items in the directory
        return 0;
    }
}
