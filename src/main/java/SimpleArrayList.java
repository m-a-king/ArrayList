import java.util.Arrays;

public class SimpleArrayList implements SimpleList {

    private final static int DEFAULT_CAPACITY = 10; // 기본 용량
    private String[] strings;
    private int size; // 실제 저장된 요소 개수

    public SimpleArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.strings = new String[capacity];
        this.size = 0;
    }

    public SimpleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(String value) {
        add(size, value);
        return true;
    }

    @Override
    public void add(int index, String value) {
        validateAddableIndex(index);
        ensureCapacity();
        for (int i = size; i > index; i--) {
            strings[i] = strings[i - 1]; // 오른쪽으로 시프트
        }
        strings[index] = value; // 덮어쓰기
        size++;
    }

    private void ensureCapacity() {
        if (size >= strings.length) {
            strings = Arrays.copyOf(strings, strings.length * 2);
        }
    }

    @Override
    public String set(int index, String value) {
        validateAccessibleIndex(index);
        String oldValue = strings[index];
        strings[index] = value;
        return oldValue;
    }

    @Override
    public String get(int index) {
        validateAccessibleIndex(index);
        return strings[index];
    }

    @Override
    public boolean contains(String value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(String value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (strings[i] == null) {
                    return i;
                }
            }
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (value.equals(strings[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(String value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public String remove(int index) {
        validateAccessibleIndex(index);
        String removed = strings[index];
        for (int i = index; i < size - 1; i++) {
            strings[i] = strings[i + 1]; // 왼쪽으로 시프트
        }
        strings[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            strings[i] = null;
        }
        size = 0;
        this.strings = new String[DEFAULT_CAPACITY];
    }

    private void validateAddableIndex(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    private void validateAccessibleIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }
}
