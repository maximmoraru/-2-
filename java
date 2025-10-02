Бинарная куча
import java.util.Arrays;
import java.util.PriorityQueue;

class CustomBinaryHeap {
    private int[] heap;
    private int size;
    private int capacity;
    
    public CustomBinaryHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }
    
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }
    
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public void push(int key) {
        if (size == capacity) {
            System.out.println("Куча переполнена");
            return;
        }
        
        heap[size] = key;
        heapifyUp(size);
        size++;
    }
    
    private void heapifyUp(int i) {
        while (i > 0 && heap[i] < heap[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    public int pop() {
        if (size == 0) return -1;
        
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return min;
    }
    
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < size && heap[left] < heap[smallest])
            smallest = left;
        if (right < size && heap[right] < heap[smallest])
            smallest = right;
        
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }
    
    public int top() {
        return size == 0 ? -1 : heap[0];
    }
    
    public void print() {
        int[] display = Arrays.copyOf(heap, size);
        System.out.println(Arrays.toString(display));
    }
}

public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("=== Бинарная куча на Java ===");
        
        // Собственная реализация
        CustomBinaryHeap heap = new CustomBinaryHeap(10);
        heap.push(8);
        heap.push(3);
        heap.push(5);
        heap.push(1);
        heap.push(6);
        
        System.out.print("Куча: ");
        heap.print();
        System.out.println("Минимальный элемент: " + heap.top());
        System.out.println("Извлечение: " + heap.pop());
        System.out.print("Куча после извлечения: ");
        heap.print();
        
        // Стандартная PriorityQueue
        System.out.println("\n=== Стандартная PriorityQueue ===");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(8);
        pq.add(3);
        pq.add(5);
        pq.add(1);
        
        System.out.println("Минимальный элемент: " + pq.peek());
        System.out.println("Извлечение: " + pq.poll());
        System.out.println("Следующий минимальный: " + pq.peek());
    }
}




Хеш-таблица
import java.util.LinkedList;

class HashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }
    
    private LinkedList<Entry<K, V>>[] table;
    private int capacity;
    private int size;
    private final double loadFactor;
    
    @SuppressWarnings("unchecked")
    public HashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }
    
    public HashTable() {
        this(10, 0.75);
    }
    
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[newCapacity];
        
        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new LinkedList<>();
        }
        
        // Перехеширование всех элементов
        for (LinkedList<Entry<K, V>> bucket : table) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = Math.abs(entry.key.hashCode()) % newCapacity;
                newTable[newIndex].add(entry);
            }
        }
        
        table = newTable;
        capacity = newCapacity;
    }
    
    public void put(K key, V value) {
        if ((double) size / capacity >= loadFactor) {
            resize();
        }
        
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        
        // Проверяем, есть ли уже ключ
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        
        // Добавляем новую запись
        bucket.add(new Entry<>(key, value));
        size++;
    }
    
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        
        throw new RuntimeException("Key not found: " + key);
    }
    
    public void remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                bucket.remove(entry);
                size--;
                return;
            }
        }
        
        throw new RuntimeException("Key not found: " + key);
    }
    
    public boolean contains(K key) {
        try {
            get(key);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
    
    public int size() {
        return size;
    }
    
    public void print() {
        for (int i = 0; i < capacity; i++) {
            if (!table[i].isEmpty()) {
                System.out.println("Bucket " + i + ": " + table[i]);
            }
        }
    }
}

public class HashTableDemo {
    public static void main(String[] args) {
        System.out.println("=== Хеш-таблица на Java ===");
        
        HashTable<String, Integer> hashTable = new HashTable<>();
        
        // Добавление элементов
        hashTable.put("apple", 5);
        hashTable.put("banana", 3);
        hashTable.put("orange", 7);
        hashTable.put("grape", 2);
        hashTable.put("kiwi", 8);
        
        System.out.println("Хеш-таблица после добавления:");
        hashTable.print();
        System.out.println("Количество элементов: " + hashTable.size());
        
        // Поиск элементов
        System.out.println("\nЗначение для 'apple': " + hashTable.get("apple"));
        System.out.println("Содержит 'banana': " + hashTable.contains("banana"));
        
        // Обновление значения
        hashTable.put("apple", 10);
        System.out.println("Обновлённое значение для 'apple': " + hashTable.get("apple"));
        
        // Удаление элемента
        hashTable.remove("banana");
        System.out.println("\nПосле удаления 'banana', количество элементов: " + hashTable.size());
        System.out.println("Содержит 'banana': " + hashTable.contains("banana"));
        
        // Демонстрация стандартной HashMap
        System.out.println("\n=== Стандартная HashMap ===");
        java.util.HashMap<String, Integer> stdMap = new java.util.HashMap<>();
        stdMap.put("apple", 5);
        stdMap.put("banana", 3);
        stdMap.put("orange", 7);
        
        System.out.println("Стандартная HashMap: " + stdMap);
        System.out.println("Значение для 'apple': " + stdMap.get("apple"));
    }
}
