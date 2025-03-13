public interface SimpleList<T> {

    static <T> SimpleList<T> fromArrayToList(T[] array) {
        return new SimpleArrayList<>(array);
    }

    static <T extends Number> double sum(SimpleList<T> elements) {
        double total = 0.0;
        for (int i = 0; i < elements.size(); i++) {
            total += elements.get(i).doubleValue();
        }
        return total;
    }

    static <T extends Number> SimpleList<T> filterNegative(SimpleList<T> elements) {
        SimpleList<T> result = new SimpleArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).doubleValue() < 0) {
                result.add(elements.get(i));
            }
        }

        return result;
    }

    static <T> void copy(SimpleList<? extends T> origin, SimpleList<? super T> target) {
        for (int i = 0; i < origin.size(); i++) {
            target.add(origin.get(i));
        }
    }

    boolean add(T value);

    void add(int index, T value);

    T set(int index, T value);

    T get(int index);

    boolean contains(T value);

    int indexOf(T value);

    int size();

    boolean isEmpty();

    boolean remove(T value);

    T remove(int index);

    void clear();
}
