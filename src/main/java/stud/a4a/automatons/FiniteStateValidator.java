package stud.a4a.automatons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FiniteStateValidator {
    private final Map<Integer, Set<Character>> allowedSymbols;
    private final Map<Integer, Map<Character, Integer>> transitions;
    private int currentState;

    public FiniteStateValidator() {
        this.allowedSymbols = new HashMap<>();
        this.transitions = new HashMap<>();
        setupStates();
        reset();
    }

    private void setupStates() {
        // Состояние 1: Первый набор символов
        allowedSymbols.put(1, Set.of('S', 'A', 'B', 'C', 'a', 'b', 'c'));
        transitions.put(1, Map.of(
                ' ', 2  // Пробел -> переход в состояние 2 (замена на "->")
        ));

        // Состояние 2: Первый пробел (автозамена на "->")
        transitions.put(2, new HashMap<>()); // Автопереход в 3 после замены

        // Состояние 3: Другие наборы символов
        allowedSymbols.put(3, Set.of('S', 'A', 'B', 'C', 'a', 'b', 'c'));
        transitions.put(3, Map.of(
                ' ', 4,   // Пробел -> переход в состояние 4 (замена на "|")
                '\n', 5  // Enter -> переход в состояние 5
        ));

        // Состояние 4: Пробел между правилами (автозамена на "|")
        transitions.put(4, new HashMap<>()); // Автопереход в 3 после замены

        // Состояние 5: Конец строки
        transitions.put(5, Map.of()); // Сброс в 1 при новом вводе
    }

    public boolean validate(char c) {
        // Проверка допустимости символа для текущего состояния
        if (currentState == 1 || currentState == 3) {
            if (!allowedSymbols.get(currentState).contains(c)) return false;
        }

        // Обработка специальных переходов
        if (transitions.get(currentState).containsKey(c)) {
            currentState = transitions.get(currentState).get(c);
            return true;
        }

        // Автоматические переходы без символа
        if (currentState == 2 || currentState == 4) {
            currentState = currentState == 2 ? 3 : 3; // Переход после замены пробела
            return true;
        }

        return false;
    }

    public void reset() {
        currentState = 1;
    }

    public int getCurrentState() {
        return currentState;
    }
}