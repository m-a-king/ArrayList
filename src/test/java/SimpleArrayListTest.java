import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleArrayListTest {

    @Test
    @DisplayName("add(String) - 요소를 추가하면 크기가 증가해야 한다")
    void add_ShouldIncreaseSize_WhenElementIsAdded() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();

        // When
        list.add("A");
        list.add("B");
        list.add("C");

        // Then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("B");
        assertThat(list.get(2)).isEqualTo("C");
    }

    @Test
    @DisplayName("add(index, value) - 특정 인덱스에 요소를 삽입하면 기존 요소들이 오른쪽으로 이동해야 한다")
    void addAtIndex_ShouldShiftElements_WhenInsertedInBetween() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("C");

        // When
        list.add(1, "B");

        // Then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("B");
        assertThat(list.get(2)).isEqualTo("C");
    }

    @Test
    @DisplayName("set(index, value) - 지정한 인덱스의 값을 변경하면 기존 값을 반환해야 한다")
    void set_ShouldReplaceValue_WhenIndexIsValid() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");

        // When
        String oldValue = list.set(1, "X");

        // Then
        assertThat(oldValue).isEqualTo("B");
        assertThat(list.get(1)).isEqualTo("X");
    }

    @Test
    @DisplayName("get(index) - 존재하는 요소를 가져올 수 있어야 한다")
    void get_ShouldReturnElement_WhenIndexIsValid() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");

        // When & Then
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("B");
    }

    @Test
    @DisplayName("contains(value) - 리스트에 존재하는 요소는 true를 반환해야 한다")
    void contains_ShouldReturnTrue_WhenElementExists() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");

        // When & Then
        assertThat(list.contains("B")).isTrue();
        assertThat(list.contains("C")).isFalse();
    }

    @Test
    @DisplayName("indexOf(value) - 존재하는 요소의 인덱스를 반환해야 한다")
    void indexOf_ShouldReturnCorrectIndex_WhenElementExists() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // When & Then
        assertThat(list.indexOf("B")).isEqualTo(1);
        assertThat(list.indexOf("D")).isEqualTo(-1);
    }

    @Test
    @DisplayName("size() - 리스트의 크기를 반환해야 한다")
    void size_ShouldReturnNumberOfElements() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");

        // When & Then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("isEmpty() - 요소가 없을 때 true를 반환해야 한다")
    void isEmpty_ShouldReturnTrue_WhenListIsEmpty() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();

        // When & Then
        assertThat(list.isEmpty()).isTrue();

        // 요소 추가 후 다시 확인
        list.add("A");
        assertThat(list.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("remove(value) - 요소를 삭제하면 크기가 감소해야 한다")
    void remove_ShouldDecreaseSize_WhenElementIsRemoved() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // When
        boolean removed = list.remove("B");

        // Then
        assertThat(removed).isTrue();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("C");
    }

    @Test
    @DisplayName("remove(index) - 특정 인덱스의 요소를 삭제하면 크기가 감소해야 한다")
    void removeAtIndex_ShouldDecreaseSize_WhenElementIsRemoved() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // When
        String removed = list.remove(1);

        // Then
        assertThat(removed).isEqualTo("B");
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo("A");
        assertThat(list.get(1)).isEqualTo("C");
    }

    @Test
    @DisplayName("clear() - 모든 요소를 제거한 후 size가 0이어야 한다")
    void clear_ShouldRemoveAllElements() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // When
        list.clear();

        // Then
        assertThat(list.size()).isEqualTo(0);
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("예외 처리 - 존재하지 않는 인덱스에 접근 시 예외가 발생해야 한다")
    void shouldThrowException_WhenIndexIsInvalid() {
        // Given
        SimpleList<String> list = new SimpleArrayList<>();
        list.add("A");

        // When & Then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> list.get(-1)),
                () -> assertThrows(IllegalArgumentException.class, () -> list.get(2)),
                () -> assertThrows(IllegalArgumentException.class, () -> list.set(2, "X")),
                () -> assertThrows(IllegalArgumentException.class, () -> list.add(3, "Z")),
                () -> assertThrows(IllegalArgumentException.class, () -> list.remove(2))
        );
    }

    /**
     * Generic SimpleArrayList에서 배열 타입 변환 과정에서
     * 컴파일리가 경고를 띄우는 이유를 시각화하는 테스트
     */
    @Test
    @DisplayName("잘못된 타입을 넣고 get() 호출 시 - ClassCastException 발생")
    void shouldThrowException_WhenWrongTypeIsRetrieved() {
        // Given
        SimpleArrayList<Integer> list = new SimpleArrayList<>(10);
        list.add(100);
        list.add(200);

        // 강제로 size 증가 (원래는 add()를 통해 증가해야 하지만, 테스트를 위해 강제 조작)
        Object[] elementsInGenericList = list.forceGetInnerArrayForTest();
        elementsInGenericList[2] = "Hello"; // Object[]이므로 문제 없음!
        list.forceSetSizeForTest(3); // 강제로 size 증가 (원래는 이렇게 하면 안 되지만 테스트 목적)

        // When & Then
        assertThatThrownBy(() -> {
            Integer value = list.get(2);
            System.out.println("value = " + value);
        }).isInstanceOf(ClassCastException.class);
    }
}
