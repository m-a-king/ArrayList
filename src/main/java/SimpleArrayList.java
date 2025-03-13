import java.util.Arrays;

public class SimpleArrayList<T> implements SimpleList<T> {

    private final static int DEFAULT_CAPACITY = 10; // 기본 용량
    private T[] elements;
    private int size; // 실제 저장된 요소 개수

    public SimpleArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }

        /**
         * T[]처럼 보이지만, 내부적으로는 Object[]가 생성됨.
         * 제네릭을 통해 T[]로 표현했을 뿐, 런타임에서 Object[]임.
        */
        this.elements = (T[]) new Object[capacity]; // 실질적으로 Object[]에 불과하기에 경고 발생
        this.size = 0;
    }

    @SafeVarargs
    public SimpleArrayList(T... elements) {
        this.elements = elements;
        this.size = elements.length;
    }

    public SimpleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public T[] forceGetInnerArrayForTest() {
        return elements;
    }

    public void forceSetSizeForTest(int size) {
        this.size = size;
    }

    @Override
    public boolean add(T value) {
        add(size, value);
        return true;
    }

    @Override
    public void add(int index, T value) {
        validateAddableIndex(index);
        ensureCapacity();
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1]; // 오른쪽으로 시프트
        }
        elements[index] = value; // 덮어쓰기
        size++;
    }

    private void ensureCapacity() {
        if (size >= elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    @Override
    public T set(int index, T value) {
        validateAccessibleIndex(index);
        T oldValue = elements[index];
        elements[index] = value;
        return oldValue;
    }

    @Override
    public T get(int index) {
        validateAccessibleIndex(index);
        return elements[index];
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (value.equals(elements[i])) {
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
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public T remove(int index) {
        validateAccessibleIndex(index);
        T removed = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1]; // 왼쪽으로 시프트
        }
        elements[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
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
